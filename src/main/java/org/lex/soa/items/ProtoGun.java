package org.lex.soa.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.lex.soa.Soa;


public class ProtoGun extends Item {

    public ProtoGun(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        //return super.use(level, player, usedHand);

        return this.shoot(level, player, usedHand, false);
    }

    public InteractionResultHolder<ItemStack> shoot(Level world, Player player, InteractionHand hand, Boolean left) {
        ItemStack item = player.getItemInHand(hand);

        // Raytrace - Find the target
        Vec3 start = player.position()
                .add(0, player.getEyeHeight(), 0);
        Vec3 range = player.getLookAngle()
                .scale(64);
        BlockHitResult raytrace =
                world.clip(new ClipContext(start, start.add(range), Block.OUTLINE, Fluid.NONE, player));
        BlockPos pos = raytrace.getBlockPos();
        BlockState stateReplaced = world.getBlockState(pos);


        // No target
        if (pos == null || stateReplaced.getBlock() == Blocks.AIR) {
            return new InteractionResultHolder<>(InteractionResult.CONSUME, item);
        }

        // Client side
        if (world.isClientSide) {
            return new InteractionResultHolder<>(InteractionResult.CONSUME, item);
        }

        // Server side
        // todo: turn this into a packet and send to client
        if (left) {
            Creeper mob = EntityType.CREEPER.create(world);
            if (mob != null) {
                mob.moveTo(pos.getX(), pos.getY() + 2, pos.getZ(), 0.0F, 0.0F);
                world.addFreshEntity(mob);
            }

        } else {
            Spider mob = EntityType.SPIDER.create(world);
            if (mob != null) {
                mob.moveTo(pos.getX(), pos.getY() + 2, pos.getZ(), 0.0F, 0.0F);
                world.addFreshEntity(mob);
            }
        }

        player.getCooldowns().addCooldown(item.getItem(), 20);
        player.stopUsingItem();

        return new InteractionResultHolder<>(InteractionResult.CONSUME, item);
    }

    @Override
    public boolean onEntitySwing(ItemStack pStack, LivingEntity pEntity, InteractionHand pHand) {
        if(!(pEntity instanceof Player pPlayer)){
            return true;
        }
        if((pPlayer.getCooldowns().isOnCooldown(pStack.getItem()))){
            return true;
        }

        this.shoot(
                pPlayer.level(),
                pPlayer,
                //get main arm interaction hand
                pPlayer.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
                true
        );

        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        //InteractionResultHolder<ItemStack> ShootResultHolder =

        return false;
    }

}
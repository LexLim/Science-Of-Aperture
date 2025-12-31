package org.lex.soa.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.lex.soa.networking.SoaMessages;
import org.lex.soa.networking.packets.ShootPackets;
import org.lex.soa.utils.PortalLevelUtils;


public class ProtoGun extends Item {

    public ProtoGun(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        //return super.use(level, player, usedHand);
        if(player.isShiftKeyDown()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(usedHand));
        }
        return this.shoot(level, player, usedHand, false);
    }

    public InteractionResultHolder<ItemStack> shoot(Level world, Player player, InteractionHand hand, Boolean left) {
        ItemStack item = player.getItemInHand(hand);

        if (world.isClientSide) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, item);
        }

        // Raytrace - Find the target
        Vec3 start = player.position()
                .add(0, player.getEyeHeight(), 0);
        Vec3 range = player.getLookAngle()
                .scale(64);
        BlockHitResult raytrace =
                world.clip(new ClipContext(start, start.add(range), Block.OUTLINE, Fluid.NONE, player));
        BlockPos pos = raytrace.getBlockPos().relative(raytrace.getDirection());
        BlockState stateReplaced = world.getBlockState(raytrace.getBlockPos());


        // No target
        if (pos == null || stateReplaced.getBlock() == Blocks.AIR) {
            return new InteractionResultHolder<>(InteractionResult.CONSUME, item);
        }

        // Server side
        // TODO: do the animation and segment shooting
        SoaMessages.fuckingAnnounce(
                new ShootPackets.ClientboundAnimatePortalPacket(
                        new CompoundTag()
                ),
                player
        );


        PortalLevelUtils.TraceAndCreatePortal(
                world,
                player,
                item,
                left
        );

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
            //stop swinging animation forcefully
            pPlayer.swingTime = 0;
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
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return true;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        //InteractionResultHolder<ItemStack> ShootResultHolder =

        return false;
    }

}
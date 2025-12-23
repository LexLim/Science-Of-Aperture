package org.lex.soa.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.lex.soa.entity.ProtoPortal;
import org.lex.soa.networking.SoaMessages;
import org.lex.soa.networking.packets.ShootPacket;

public class PortalGunUtils {

    /** create a portal
     *
     * @param world
     * @param pPlayer

     * @return false if portal was created successfully, true if there were issues
     */
    public static boolean FormulatePortal(Level world, Player pPlayer, boolean primary){
        // Raytrace - Find the target
        Vec3 start = pPlayer.position()
                .add(0, pPlayer.getEyeHeight(), 0);
        Vec3 range = pPlayer.getLookAngle()
                .scale(64);
        BlockHitResult raytrace =
                world.clip(new ClipContext(start, start.add(range), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pPlayer));
        BlockPos pos = raytrace.getBlockPos().relative(raytrace.getDirection());
        BlockState stateReplaced = world.getBlockState(raytrace.getBlockPos());


        if (pos == null || stateReplaced.getBlock() == Blocks.AIR) {

            // No target
            return true;
        }

        // Server side
        // TODO: do the animation and segment shooting
        SoaMessages.fuckingAnnounce(
                new ShootPacket.ClientboundAnimatePortalPacket(
                        new CompoundTag()
                ),
                pPlayer
        );


        if (primary) {
            ProtoPortal pp = new ProtoPortal(world, pos, raytrace.getDirection());

            if (pp!= null) {
                pp.playPlacementSound();

                //mob.moveTo(pos.getX(), pos.getY() + 2, pos.getZ(), 0.0F, 0.0F);
                world.addFreshEntity(pp);
            }
            return false;
        } else {
            ProtoPortal pp = new ProtoPortal(world, pos, raytrace.getDirection());

            if (pp!= null) {
                pp.playPlacementSound();

                //mob.moveTo(pos.getX(), pos.getY() + 2, pos.getZ(), 0.0F, 0.0F);
                world.addFreshEntity(pp);
            }
            return false;
        }
    }
}

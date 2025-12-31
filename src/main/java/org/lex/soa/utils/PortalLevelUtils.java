package org.lex.soa.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.lex.soa.Soa;
import org.lex.soa.entity.ProtoPortal;
import org.lex.soa.registery.SoaSounds;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;

public class PortalLevelUtils {

    /**
     * get portal entity in the world
     *
     * @param pLevel world
     * @param dat    data
     * @return <T> portal entity or null
     */
    @Nullable
    public static <T extends ProtoPortal> T getPortalEntity(Level pLevel, PortalData dat) {
        AABB range = AABB.encapsulatingFullBlocks(dat.getPosition(), dat.getPosition()).inflate(1);
        List<Entity> list = pLevel.getEntities((Entity) null, range, ((b) -> b instanceof ProtoPortal));
        if (list.isEmpty()) return null;

        if (list.size() > 1) {
            Soa.LOGGER.warn("too many portals found at blockpos " + dat.getPosition().toString() + " . Getting the first entity");
        }

        return (T) list.getFirst();
    }

    ;

    /**
     * get portal entity in the world
     *
     * @param pLevel world
     * @param dat    data
     * @return <T> portal entity or null
     */
    @Nullable
    public static <T extends ProtoPortal> T getPortalEntity(Level pLevel, BlockPos dat) {
        AABB range = AABB.encapsulatingFullBlocks(dat, dat).inflate(1);
        List<Entity> list = pLevel.getEntities((Entity) null, range, ((b) -> b instanceof ProtoPortal));
        if (list.isEmpty()) return null;

        if (list.size() > 1) {
            Soa.LOGGER.warn("too many portals found at blockpos " + dat.toString() + " . Getting the first entity");
        }

        return (T) list.getFirst();
    }

    ;

    /**
     * returns if the portal exists according to dat
     *
     * @param pLevel world
     * @param dat    data
     * @return bool
     */
    public static boolean portalExists(Level pLevel, PortalData dat) {
        ProtoPortal portal = getPortalEntity(pLevel, dat);
        return portal != null;
    }

    /**
     * returns if the portal exists at the specified position
     *
     * @param pLevel world
     * @param dat    data
     * @return bool
     */
    public static boolean portalExists(Level pLevel, BlockPos dat) {
        ProtoPortal portal = getPortalEntity(pLevel, dat);
        return portal != null;
    }

    /**
     * returns linked portal or null
     *
     * @param thisPortal portal
     * @param pLevel     level
     * @return entity
     */
    @Nullable
    public static <T extends ProtoPortal> T getLinkedPortal(ProtoPortal thisPortal, Level pLevel) {
        if (thisPortal.getDest().isEmpty())
            return null;

        return getPortalEntity(pLevel, thisPortal.getDest().get());
    }

    /**
     * Teleport player to linked portal
     *
     * @param pPlayer    player to teleport
     * @param thisPortal portal entity
     * @param pLevel     world
     */
    public static void TpPlayerToLinkedPortal(Player pPlayer, ProtoPortal thisPortal, Level pLevel) {
        ProtoPortal linkedPortal = getLinkedPortal(thisPortal, pLevel);

        if (linkedPortal != null) {
            Vec2 linkeddir = new Vec2(linkedPortal.getYRot(), linkedPortal.getXRot());
            Vec2 thisdir = new Vec2(thisPortal.getYRot(), thisPortal.getXRot());
            Vec2 playerdir = new Vec2(pPlayer.getYRot(), pPlayer.getXRot());

            //Soa.LOGGER.info("player = "+playerdir.x+" "+playerdir.y);
            //Soa.LOGGER.info("portal = "+thisdir.x+" "+thisdir.y);
            //Soa.LOGGER.info("linked = "+linkeddir.x+" "+linkeddir.y);

            double facingDirX = Mth.wrapDegrees(thisdir.x + 180.0F);
            //behind the goddamn portal
            double diff = Mth.wrapDegrees(playerdir.x - facingDirX);

            Vec2 finrot = new Vec2(
                    (float) Mth.wrapDegrees(linkeddir.x + diff),
                    //diff used to offset
                    playerdir.y
            );

            //Soa.LOGGER.info("playerdir = "+facingDirX+", diff = "+diff);


            Vec3 spe = pPlayer.getDeltaMovement().scale(2.5);
            BlockPos bps = thisPortal.getDest().get();

            /*
            Vec3 bpsvec = bps.getCenter().add(
                    new Vec3(linkednor.getX(), linkednor.getY(), linkednor.getZ()).scale(0.01)
            );
             */

            Vec3 norpos = bps.getBottomCenter().add(
                    new Vec3(
                            linkedPortal.getDirection().getNormal().getX(),
                            linkedPortal.getDirection().getNormal().getY(),
                            linkedPortal.getDirection().getNormal().getZ()
                    )
                            //.scale(0.1)
                            .scale(-0.1)
            );


            //Soa.LOGGER.info("fin = "+finn.x+" "+finn.y);
            pPlayer.teleportTo(
                    (ServerLevel) linkedPortal.level(),
                    norpos.x(),
                    norpos.y(),
                    norpos.z(),
                    Set.of(),
                    finrot.x,
                    finrot.y
            );
            pPlayer.hurtMarked = true;
            pPlayer.setDeltaMovement(spe);

            Soa.LOGGER.info("Teleported Player To The Linked Portal");
        }
    }


    public static void TraceAndCreatePortal(Level pLevel, Player pPlayer, ItemStack pStack, boolean leftclick) {
        Vec3 start = pPlayer.position()
                .add(0, pPlayer.getEyeHeight(), 0);
        Vec3 range = pPlayer.getLookAngle()
                .scale(64);
        BlockHitResult raytrace =
                pLevel.clip(new ClipContext(start, start.add(range), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, pPlayer));
        BlockPos pos = raytrace.getBlockPos().relative(raytrace.getDirection());
        BlockState stateReplaced = pLevel.getBlockState(raytrace.getBlockPos());


        // No target
        if (pos == null || stateReplaced.getBlock() == Blocks.AIR) {
            return;
        }

        boolean issues = PortalShooterUtil.CreatePortalOnItem(
                leftclick,
                pStack,
                pos,
                pLevel,
                MiscUtils.getDirectionToRotInt(
                        raytrace.getDirection()
                )
        );

        if (!issues) pLevel.playSound(
                (Player) null,
                (double) pos.getX(),
                (double) pos.getY(),
                (double) pos.getZ(),
                leftclick ? SoaSounds.GUN_DEPLETE_PRIMARY.get() : SoaSounds.GUN_DEPLETE_SECONDARY.get(),
                SoundSource.BLOCKS,
                0.5F,
                1F);

    }
}

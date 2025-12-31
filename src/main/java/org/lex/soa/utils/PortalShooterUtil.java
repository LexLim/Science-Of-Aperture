package org.lex.soa.utils;

import com.jcraft.jorbis.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.C;
import org.lex.soa.Soa;
import org.lex.soa.entity.ProtoPortal;
import org.lex.soa.registery.SoaEntities;

import java.util.Optional;

public class PortalShooterUtil {

    /**
     * LOAD PORTAL DATA
     * only to be called upon player joining
     *
     * @param pStack
     * @param world
     */
    public static void load(ItemStack pStack, Level world) {
        PortalShooterData dat = PortalShooterData.fromCompound(
                PortalShooterData.getItemCompound(pStack)
        );
        if (dat.isEmpty()) return;

        PortalData tmpPrimary = dat.getPrimaryPortal();
        PortalData tmpSecondary = dat.getSecondaryPortal();

        if (!tmpPrimary.isEmpty()) {
            boolean CreationResultprim = PlacePortalEntity(dat.getPrimaryColour(), tmpPrimary, world);

            Soa.LOGGER.info(
                    "PLAYER JOIN: " + (
                            CreationResultprim ?
                                    "Failed to Load Primary Portal." :
                                    "Successfully Created Primary Portal."
                    )
            );
        }

        if (!tmpSecondary.isEmpty()) {
            boolean CreationResultsec = PlacePortalEntity(dat.getSecondaryColour(), tmpSecondary, world);

            Soa.LOGGER.info(
                    "PLAYER JOIN: " + (
                            CreationResultsec ?
                                    "Failed to Load Secondary Portal." :
                                    "Successfully Created Secondary Portal."
                    )
            );

        }


        dat.applyToItem(pStack);
        AttemptToLinkPortals(pStack, world);
        dat.applyToItem(pStack);

    }

    /**
     * UNLOAD PORTAL DATA
     * only to be called upon player leaving
     *
     * @param pStack
     * @param world
     */
    public static void unload(ItemStack pStack, Level world) {
        PortalShooterData dat = PortalShooterData.fromCompound(
                PortalShooterData.getItemCompound(pStack)
        );
        if (dat.isEmpty()) return;

        PortalData tmpPrimary = dat.getPrimaryPortal();
        PortalData tmpSecondary = dat.getSecondaryPortal();

        if (!tmpPrimary.isEmpty() && tmpPrimary.existsInWorld()) {
            boolean RemovalResultprim = RemovePortalEntity(tmpPrimary, world);

            Soa.LOGGER.info(
                    "PLAYER LEAVE: " + (
                            RemovalResultprim ?
                                    "Failed to Remove Primary Portal." :
                                    "Successfully Removed Primary Portal."
                    )
            );
        }

        if (!tmpSecondary.isEmpty()) {
            boolean RemovalResultsec = RemovePortalEntity(tmpSecondary, world);

            Soa.LOGGER.info(
                    "PLAYER LEAVE: " + (
                            RemovalResultsec ?
                                    "Failed to Remove Secondary Portal." :
                                    "Successfully Removed Secondary Portal."
                    )
            );

        }

    }


    /**
     * create a portal entity from data
     *
     * @param dat   portal data
     * @param world level
     * @return false if there is no issues, true if there is issues
     */
    public static boolean PlacePortalEntity(int colour, PortalData dat, Level world) {
        boolean isPosTaken = PortalLevelUtils.portalExists(world, dat);

        if (isPosTaken) {
            dat.setExists(false);
            Soa.LOGGER.info("PORTAL PLACEMENT: space is already occupied.");
            return true;
        } else {
            world.addFreshEntity(new ProtoPortal(SoaEntities.PROTO_PORTAL.get(), world, dat));
            dat.setExists(true);
            return false;
        }
    }

    /**
     * remove existing portal
     *
     * @param dat   portal data
     * @param world level
     * @return false if there is no issues, true if there is issues
     */
    public static boolean RemovePortalEntity(PortalData dat, Level world) {
        ProtoPortal entity = PortalLevelUtils.getPortalEntity(world, dat);
        if (entity != null && dat.existsInWorld()) {
            entity.kill();
            dat.setExists(false);
            return false;
        }
        Soa.LOGGER.info("PORTAL REMOVAL: portal did not exist.");
        return true;
    }

    /**
     * try to link two portals
     *
     * @param stacc item
     * @param world level
     * @return false if portals are properly linked, true if there is issues
     */
    public static boolean AttemptToLinkPortals(ItemStack stacc, Level world) {
        PortalShooterData dat = PortalShooterData.fromItem(stacc);

        if (dat.isEmpty()) {
            Soa.LOGGER.info("PORTAL LINK: no portals to link.");
            return true;
        }

        if (!(dat.getPrimaryPortal().existsInWorld() && dat.getSecondaryPortal().existsInWorld())) {
            Soa.LOGGER.info(
                    "PORTAL LINK: missing " +
                            (dat.getPrimaryPortal().existsInWorld() ?
                                    "secondary " :
                                    "primary "
                            ) +
                            "portal to link."

            );
            Soa.LOGGER.info("PORTAL LINK: one or the other portal missing, setting destination to null.");
            if (dat.getPrimaryPortal().existsInWorld()) {
                dat.getPrimaryPortal().UpdateDestination(null);
            }
            if (dat.getSecondaryPortal().existsInWorld()) {
                dat.getSecondaryPortal().UpdateDestination(null);
            }

            return true;
        }
        PortalData primdat = dat.getPrimaryPortal();
        ProtoPortal primportal = PortalLevelUtils.getPortalEntity(world, primdat);
        if (primportal != null) {
            PortalData secdat = dat.getSecondaryPortal();
            ProtoPortal secportal = PortalLevelUtils.getPortalEntity(world, secdat);
            if (secportal != null) {
                primportal.setDestination(
                        MiscUtils.ToOptionalBlockpos(secdat.getPosition())
                );

                secportal.setDestination(
                        MiscUtils.ToOptionalBlockpos(primdat.getPosition())
                );
                Soa.LOGGER.info("PORTAL LINK: portals linked");
                return false;
            }
            Soa.LOGGER.info("PORTAL LINK: where the fuck is the secondary portal???");
            return true;

        }

        Soa.LOGGER.info("PORTAL LINK: where the fuck is the primary portal???");
        return true;
    }

    public static boolean CreatePortalOnItem(boolean primary, ItemStack pStack, BlockPos pos, Level world,int rot) {
        PortalShooterData dat = PortalShooterData.fromCompound(
                PortalShooterData.getItemCompound(pStack)
        );
        boolean issues = false;


        PortalData existingPortal = primary ? dat.getPrimaryPortal() : dat.getSecondaryPortal();
        int pcolour = primary ? dat.getPrimaryColour() : dat.getSecondaryColour();

        if (existingPortal.existsInWorld()) {
            RemovePortalEntity(existingPortal, world);
        }

        existingPortal.UpdatePosition(pos);
        existingPortal.setRotation(rot);

        boolean CreationResultprim = PlacePortalEntity(
                pcolour,
                existingPortal,
                world
        );

        Soa.LOGGER.info(
                "PLAYER " + (primary ? "LEFT" : "RIGHT") + " CLICK: " + (
                        CreationResultprim ?
                                "Failed to Create " + (primary ? "Primary" : "Secondary") + " Portal." :
                                "Successfully Create " + (primary ? "Primary" : "Secondary") + " Portal."
                )
        );

        issues = CreationResultprim;
        if (issues) {
            Soa.LOGGER.info("PLAYER CLICK: Issues found, ceasing to link portals and update data on item in case of desync");
            return true;
        }
        ;

        dat.applyToItem(pStack);
        issues = AttemptToLinkPortals(pStack, world);
        dat.applyToItem(pStack);

        return issues;
    }

}

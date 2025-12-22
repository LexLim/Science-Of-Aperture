package org.lex.soa.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lex.soa.entity.ProtoPortal;

public class PortalServerUtils {
    /**
     * Teleport Player to Destination which the original portal stores
     * @param pPlayer player to teleport
     * @param OriginPortal original portal (it probably has destination coords)

     */
    public static void Teleport(Player pPlayer, ProtoPortal OriginPortal){};

    /**
     * Return Associated Portal which the original portal stores or maybe the player does, if not find it
     * @param pPlayer subject or helper
     * @param OriginPortal subject probably

     */
    @Nullable
    public static ProtoPortal SearchOtherPortal(Player pPlayer, ProtoPortal OriginPortal){ return null;};

    /**
     * UPdate Portal and its counterpart
     * @param pPlayer aid for search
     * @param pPortal subject

     */
    public static void SyncToOtherPortal(Player pPlayer, ProtoPortal pPortal){};

    /**
     * check if portal exists or loaded in, also check for edge cases
     * @param pLevel
     * @param pPortal

     * @return boolean
     */
    public static boolean ValidatePortal(Level pLevel,ProtoPortal pPortal){ return false;};

}

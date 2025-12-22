package org.lex.soa.networking;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.lex.soa.networking.packets.ShootPacket;

public class SoaMessages {

    public static void register(PayloadRegistrar registrar){
            registrar.playToClient(
                    ShootPacket.ClientboundAnimatePortalPacket.TYPE,
                    ShootPacket.ClientboundAnimatePortalPacket.STREAM_CODEC,
                    ClientPayloadHandler::AnimateShoot
            );

            /*
            registrar.playToServer(
                ShootPacket.ShootInClientData.TYPE,
                ShootPacket.ShootInClientData.STREAM_CODEC,
                ServerPayloadHandler::CreatePortal
            );
            */

    }

    public static <MSG> void sendToServer(CustomPacketPayload message){
        PacketDistributor.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(CustomPacketPayload message, ServerPlayer player){
        PacketDistributor.sendToPlayer(player,message);
    }
    public static <MSG> void fuckingAnnounce(CustomPacketPayload message, Player player){
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, message);
    }

}
package org.lex.soa.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.lex.soa.Soa;

public class DebugPackets {

    public record ServerBoundChangeDebugModePacket(boolean scrollUp) implements CustomPacketPayload {

        public static final Type<ServerBoundChangeDebugModePacket> TYPE = new Type<>(
                ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "debug_mode_scroll"));

        public static final StreamCodec<ByteBuf, ServerBoundChangeDebugModePacket> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                ServerBoundChangeDebugModePacket::scrollUp,

                ServerBoundChangeDebugModePacket::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ServerBoundChangeInvDebugModePacket(boolean scrollUp, int id) implements CustomPacketPayload {

        public static final Type<ServerBoundChangeInvDebugModePacket> TYPE = new Type<>(
                ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "debug_mode_scroll_inv"));

        public static final StreamCodec<ByteBuf,  ServerBoundChangeInvDebugModePacket> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.BOOL,
                ServerBoundChangeInvDebugModePacket::scrollUp,
                ByteBufCodecs.INT,
                ServerBoundChangeInvDebugModePacket::id,
                ServerBoundChangeInvDebugModePacket::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

}
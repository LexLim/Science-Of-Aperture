package org.lex.soa.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.lex.soa.Soa;

public class ShootPackets {
    public record ClientboundAnimatePortalPacket(CompoundTag tag) implements CustomPacketPayload {

        public static final CustomPacketPayload.Type<ClientboundAnimatePortalPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation
                .fromNamespaceAndPath(Soa.MOD_ID, "clientboundanimateportalpacket"));

        public static final StreamCodec<ByteBuf, ClientboundAnimatePortalPacket> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.COMPOUND_TAG,
                ClientboundAnimatePortalPacket::tag,

                ClientboundAnimatePortalPacket::new
        );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ServerboundCreatePortalPacket(CompoundTag tag) implements CustomPacketPayload {

        public static final CustomPacketPayload.Type<ServerboundCreatePortalPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation
                .fromNamespaceAndPath(Soa.MOD_ID, "serverboundcreateportalpacket"));

        public static final StreamCodec<ByteBuf, ServerboundCreatePortalPacket> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.COMPOUND_TAG,
                ServerboundCreatePortalPacket::tag,

                ServerboundCreatePortalPacket::new
        );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

}

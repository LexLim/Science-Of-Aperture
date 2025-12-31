package org.lex.soa.networking;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.lex.soa.networking.packets.ShootPackets;

public class ClientPayloadHandler {
    public static void AnimateShoot(final ShootPackets.ClientboundAnimatePortalPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            //blah(data.age());
        });
    }
}

package org.lex.soa.networking;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.lex.soa.networking.packets.ShootPacket;

public class ClientPayloadHandler {
    public static void AnimateShoot(final ShootPacket.ClientboundAnimatePortalPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            //blah(data.age());
        });
    }
}

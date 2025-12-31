package org.lex.soa.networking;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.lex.soa.items.PortalDebugger;
import org.lex.soa.networking.packets.DebugPackets;
import org.lex.soa.networking.packets.ShootPackets;

public class ServerPayloadHandler {
    public static void CreatePortal(final ShootPackets.ServerboundCreatePortalPacket data, final IPayloadContext context) {

    }
    public static void Scroller(final DebugPackets.ServerBoundChangeDebugModePacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                ItemStack stack = serverPlayer.getMainHandItem();
                if (stack.getItem() instanceof PortalDebugger) {
                    int current = PortalDebugger.getDebuggerDataOn(stack).getInt("MODE");
                    int next = data.scrollUp() ? current + 1 : current - 1;
                    if (next > PortalDebugger.MODE.totalModes()) next = 0;
                    if (next < 0) next = PortalDebugger.MODE.totalModes();

                    PortalDebugger.setDebuggerDataOn(stack, next);

                    serverPlayer.displayClientMessage(
                            Component.literal(PortalDebugger.getDebuggerMode(stack)).withStyle(ChatFormatting.BOLD, ChatFormatting.WHITE),
                            true
                    );
                }
            }
        });
    }
    public static void InvScroller(final DebugPackets.ServerBoundChangeInvDebugModePacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                ItemStack stack = serverPlayer.getInventory().getItem(data.id());
                if (stack.getItem() instanceof PortalDebugger) {
                    int current = PortalDebugger.getDebuggerDataOn(stack).getInt("MODE");
                    int next = data.scrollUp() ? current + 1 : current - 1;
                    if (next > PortalDebugger.MODE.totalModes()) next = 0;
                    if (next < 0) next = PortalDebugger.MODE.totalModes();

                    PortalDebugger.setDebuggerDataOn(stack, next);
                }
            }
        });
    }

}

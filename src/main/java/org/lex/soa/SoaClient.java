package org.lex.soa;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.lex.soa.items.PortalDebugger;
import org.lex.soa.networking.SoaMessages;
import org.lex.soa.networking.packets.DebugPackets;
import org.lex.soa.registery.SoaEntities;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = Soa.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Soa.MOD_ID, value = Dist.CLIENT)
public class SoaClient {
    public SoaClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        SoaEntities.registerEntityRenderers();

    }
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.NETWORK);
        SoaMessages.register(registrar);

    }



    @SubscribeEvent
    public static void onScreenMouseScroll(ScreenEvent.MouseScrolled.Pre event) {
        Screen screen = event.getScreen();
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        if(screen instanceof AbstractContainerScreen aurr) {
            Slot hoveredSlot = aurr.getSlotUnderMouse();
            if (hoveredSlot != null && hoveredSlot.getItem().getItem() instanceof PortalDebugger) {
                event.setCanceled(true);
                SoaMessages.sendToServer(new DebugPackets.ServerBoundChangeInvDebugModePacket(event.getScrollDeltaY() > 0, hoveredSlot.getSlotIndex()));
            }
        }
    }
    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player == null || event.getScrollDeltaY() == 0) return;

        ItemStack mainHand = player.getMainHandItem();

        ItemStack heldStack = null;
        if (mainHand.getItem() instanceof PortalDebugger) {
            heldStack = mainHand;
        }

        if (heldStack != null && player.isShiftKeyDown() == false) {
            event.setCanceled(true);

            boolean scrollUp = event.getScrollDeltaY() > 0;
            SoaMessages.sendToServer(new DebugPackets.ServerBoundChangeDebugModePacket(scrollUp));
        }
    }

    @SubscribeEvent
    public static void onRegisterTooltipFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(
                PortalDebugger.DebugToolTooltip.class,
                PortalDebugger.ClientDebugToolTooltip::new
        );
    }

}

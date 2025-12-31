package org.lex.soa;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.lex.soa.items.ProtoGun;
import org.lex.soa.networking.SoaMessages;
import org.lex.soa.registery.ModItems;
import org.lex.soa.utils.PortalShooterUtil;

@EventBusSubscriber(modid = Soa.MOD_ID, value = Dist.DEDICATED_SERVER)
public class SoaEvents {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.NETWORK);
        SoaMessages.register(registrar);

    }


    @EventBusSubscriber(modid=Soa.MOD_ID)
    public class SoaNotServerEvents {
        @SubscribeEvent
        public static void PlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
            Player player = event.getEntity();
            Inventory inv = player.getInventory();
            Soa.LOGGER.info("Player Join: placing portals");
            for (int i = 0; i < inv.items.size(); i++) {
                if(inv.items.get(i).getItem() instanceof ProtoGun){
                    Soa.LOGGER.info("Player Join: found gun at index " + i);
                    PortalShooterUtil.load(inv.items.get(i).copy(), player.level());
                }
            }
        }
        @SubscribeEvent
        public static void PlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
            Player player = event.getEntity();
            Inventory inv = player.getInventory();
            Soa.LOGGER.info("Player Leave: clearing portals");
            for (int i = 0; i < inv.items.size(); i++) {
                if(inv.items.get(i).getItem() instanceof ProtoGun){
                    Soa.LOGGER.info("Player Leave: found gun at index " + i);
                    PortalShooterUtil.unload(inv.items.get(i).copy(), player.level());
                }
            }
        }
    }


}

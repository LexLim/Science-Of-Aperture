package org.lex.soa;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.lex.soa.networking.SoaMessages;
import org.lex.soa.registery.*;
import org.slf4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.LoggerFactory;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@EventBusSubscriber(modid = Soa.MOD_ID, value = Dist.DEDICATED_SERVER)
@Mod(Soa.MOD_ID)
public class Soa {
    public static final String MOD_ID = "soa";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public Soa(IEventBus eventBus, ModContainer modContainer) {
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        SoaSounds.register(eventBus);
        SoaEntities.register(eventBus);
        SOACreativeTab.register(eventBus);
    }
}
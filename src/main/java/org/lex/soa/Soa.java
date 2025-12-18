package org.lex.soa;

import org.lex.soa.registery.ModBlocks;
import org.lex.soa.registery.ModItems;
import org.lex.soa.registery.SOACreativeTab;
import org.slf4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.LoggerFactory;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Soa.MOD_ID)
public class Soa {
    public static final String MOD_ID = "soa";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public Soa(IEventBus eventBus, ModContainer modContainer) {

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        SOACreativeTab.register(eventBus);
    }
}
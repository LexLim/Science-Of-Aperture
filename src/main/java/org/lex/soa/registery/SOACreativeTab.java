package org.lex.soa.registery;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.lex.soa.Soa;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SOACreativeTab {

        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Soa.MOD_ID);

        public static final Supplier<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TAB.register("soa_main_tab",
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.END_CRYSTAL))
                        .title(Component.literal("SOA content"))
                        .displayItems((pParameters, pOutput) -> {
                            //pOutput.accept(ModItems.PLACHOLDER_ITEM.get());

                            ModBlocks.BLOCKS.getEntries().forEach(
                                    (block ) -> {
                                        pOutput.accept(block.get().asItem());
                                    }
                            );
                        })
                        .build());


        public static void register(IEventBus eventBus) {
            CREATIVE_MODE_TAB.register(eventBus);
        }
}

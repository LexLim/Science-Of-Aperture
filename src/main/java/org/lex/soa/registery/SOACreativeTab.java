package org.lex.soa.registery;

import net.minecraft.resources.ResourceLocation;
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
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PROTOGUN.get()))
                        .title(Component.literal("SOA content"))
                        .displayItems((pParameters, pOutput) -> {

                            ModItems.ITEMS.getEntries().forEach(
                                    (block ) -> {
                                        pOutput.accept(block.get().asItem());
                                    }
                            );

                            ModBlocks.BLOCKS.getEntries().forEach(
                                    (block ) -> {
                                        pOutput.accept(block.get().asItem());
                                    }
                            );

                        })
                        .build());

    public static final Supplier<CreativeModeTab> BUILDING_TAB = CREATIVE_MODE_TAB.register("soa_building_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.STEEL_BLOCK.get()))
                    .title(Component.literal("SOA Building Blocks"))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "soa_main_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModBlocks.STEEL_BLOCK.get());
                        pOutput.accept(ModBlocks.CUT_STEEL_BLOCK.get());
                        pOutput.accept(ModBlocks.CUT_STEEL_STAIRS.get());
                        pOutput.accept(ModBlocks.CUT_STEEL_SLAB.get());
                        pOutput.accept(ModBlocks.STEEL_GRATE.get());
                        pOutput.accept(ModBlocks.STEEL_DOOR.get());
                        pOutput.accept(ModBlocks.STEEL_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.FRAMED_CARBONITE.get());
                        pOutput.accept(ModBlocks.PACKED_FRAMED_CARBONITE.get());

                    })
                    .build());

    public static final Supplier<CreativeModeTab> PANEL_TAB = CREATIVE_MODE_TAB.register("soa_panels_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.COATED_WOODEN_PANEL_SIGNAL.get()))
                    .title(Component.literal("SOA Panels"))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "soa_building_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModBlocks.DARK_PANEL.get());
                        pOutput.accept(ModBlocks.DARK_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.TILED_DARK_PANEL.get());
                        pOutput.accept(ModBlocks.TILED_DARK_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.DOUBLE_DARK_PANEL.get());

                        pOutput.accept(ModBlocks.DECAYED_DARK_PANEL.get());
                        pOutput.accept(ModBlocks.DECAYED_DARK_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.TILED_DECAYED_DARK_PANEL.get());
                        pOutput.accept(ModBlocks.TILED_DECAYED_DARK_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.DOUBLE_DECAYED_DARK_PANEL.get());


                        pOutput.accept(ModBlocks.LUNAR_PANEL.get());
                        pOutput.accept(ModBlocks.LUNAR_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.TILED_LUNAR_PANEL.get());
                        pOutput.accept(ModBlocks.TILED_LUNAR_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.DOUBLE_LUNAR_PANEL.get());

                        pOutput.accept(ModBlocks.OVERGROWN_LUNAR_PANEL.get());
                        pOutput.accept(ModBlocks.OVERGROWN_LUNAR_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL.get());
                        pOutput.accept(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL_SLAB.get());
                        pOutput.accept(ModBlocks.DOUBLE_OVERGROWN_LUNAR_PANEL.get());


                        pOutput.accept(ModBlocks.COLD_WOODEN_PANEL.get());
                        pOutput.accept(ModBlocks.FRAMED_COLD_WOODEN_PANEL.get());
                        pOutput.accept(ModBlocks.DOUBLE_COLD_WOODEN_PANEL.get());

                        pOutput.accept(ModBlocks.LUSH_WOODEN_PANEL.get());
                        pOutput.accept(ModBlocks.FRAMED_LUSH_WOODEN_PANEL.get());
                        pOutput.accept(ModBlocks.DOUBLE_LUSH_WOODEN_PANEL.get());

                        pOutput.accept(ModBlocks.COATED_WOODEN_PANEL.get());
                        pOutput.accept(ModBlocks.COATED_WOODEN_PANEL_SIGNAL.get());

                    })
                    .build());

    public static final Supplier<CreativeModeTab> TESTING_ELEMENTS_TAB = CREATIVE_MODE_TAB.register("soa_testing_elements_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PLACHOLDER_ITEM.get()))
                    .title(Component.literal("SOA Portal Gun"))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "soa_panels_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.PLACHOLDER_ITEM.get());

                    })
                    .build());

    public static final Supplier<CreativeModeTab> DECORATION_TAB = CREATIVE_MODE_TAB.register("soa_decoration_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STEEL_INGOT.get()))
                    .title(Component.literal("SOA Deco"))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "soa_testing_elements_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.PLACHOLDER_ITEM.get());
                    })
                    .build());

    public static final Supplier<CreativeModeTab> PORTAL_GUN_TAB = CREATIVE_MODE_TAB.register("soa_portal_gun_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PROTOGUN.get()))
                    .title(Component.literal("SOA Portal Gun"))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "soa_decoration_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(ModItems.PROTOGUN.get());

                    })
                    .build());

    public static final Supplier<CreativeModeTab> INGREDIENTS_TAB = CREATIVE_MODE_TAB.register("soa_ingredients_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STEEL_INGOT.get()))
                    .title(Component.literal("SOA Ingredients"))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Soa.MOD_ID, "soa_portal_gun_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        ModItems.ITEMS.getEntries().forEach(
                                (item ) -> {
                                    pOutput.accept(item.get());
                                }
                        );
                    })
                    .build());


        public static void register(IEventBus eventBus) {
            CREATIVE_MODE_TAB.register(eventBus);
        }
}

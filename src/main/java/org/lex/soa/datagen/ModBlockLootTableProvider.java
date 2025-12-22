package org.lex.soa.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.lex.soa.registery.ModBlocks;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.STEEL_BLOCK.get());
        dropSelf(ModBlocks.CUT_STEEL_BLOCK.get());
        dropSelf(ModBlocks.CUT_STEEL_STAIRS.get());
        dropSelf(ModBlocks.CUT_STEEL_SLAB.get());
        dropSelf(ModBlocks.STEEL_GRATE.get());
        add(ModBlocks.STEEL_DOOR.get(),
                block -> createDoorTable(ModBlocks.STEEL_DOOR.get()));
        dropSelf(ModBlocks.STEEL_TRAPDOOR.get());
        dropSelf(ModBlocks.DARK_PANEL.get());
        add(ModBlocks.DARK_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.DARK_PANEL_SLAB.get()));
        dropSelf(ModBlocks.TILED_DARK_PANEL.get());
        add(ModBlocks.TILED_DARK_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TILED_DARK_PANEL_SLAB.get()));
        dropSelf(ModBlocks.DOUBLE_DARK_PANEL.get());
        dropSelf(ModBlocks.LUNAR_PANEL.get());
        add(ModBlocks.LUNAR_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.LUNAR_PANEL_SLAB.get()));
        dropSelf(ModBlocks.TILED_LUNAR_PANEL.get());
        add(ModBlocks.TILED_LUNAR_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TILED_LUNAR_PANEL_SLAB.get()));
        dropSelf(ModBlocks.DOUBLE_LUNAR_PANEL.get());
        dropSelf(ModBlocks.COLD_WOODEN_PANEL.get());
        dropSelf(ModBlocks.FRAMED_COLD_WOODEN_PANEL.get());
        dropSelf(ModBlocks.DOUBLE_COLD_WOODEN_PANEL.get());
        dropSelf(ModBlocks.LUSH_WOODEN_PANEL.get());
        dropSelf(ModBlocks.FRAMED_LUSH_WOODEN_PANEL.get());
        dropSelf(ModBlocks.DOUBLE_LUSH_WOODEN_PANEL.get());
        dropSelf(ModBlocks.COATED_WOODEN_PANEL.get());
        dropSelf(ModBlocks.COATED_WOODEN_PANEL_SIGNAL.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}

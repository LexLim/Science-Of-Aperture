package org.lex.soa.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.lex.soa.Soa;
import org.lex.soa.registery.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Soa.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.DARK_PANEL.get())
                .add(ModBlocks.DARK_PANEL_SLAB.get())
                .add(ModBlocks.TILED_DARK_PANEL.get())
                .add(ModBlocks.TILED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_DARK_PANEL.get())
                .add(ModBlocks.LUNAR_PANEL.get())
                .add(ModBlocks.LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.TILED_LUNAR_PANEL.get())
                .add(ModBlocks.TILED_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_LUNAR_PANEL.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DARK_PANEL.get())
                .add(ModBlocks.DARK_PANEL_SLAB.get())
                .add(ModBlocks.TILED_DARK_PANEL.get())
                .add(ModBlocks.TILED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_DARK_PANEL.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.LUNAR_PANEL.get())
                .add(ModBlocks.LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.TILED_LUNAR_PANEL.get())
                .add(ModBlocks.TILED_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_LUNAR_PANEL.get());
    }
}

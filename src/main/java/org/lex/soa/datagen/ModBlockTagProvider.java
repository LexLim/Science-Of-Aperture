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
                .add(ModBlocks.STEEL_BLOCK.get())
                .add(ModBlocks.CUT_STEEL_BLOCK.get())
                .add(ModBlocks.CUT_STEEL_STAIRS.get())
                .add(ModBlocks.CUT_STEEL_SLAB.get())
                .add(ModBlocks.STEEL_GRATE.get())
                .add(ModBlocks.STEEL_DOOR.get())
                .add(ModBlocks.STEEL_TRAPDOOR.get())
                .add(ModBlocks.STEEL_PLATING.get())
                .add(ModBlocks.LARGE_STEEL_PLATING.get())
                .add(ModBlocks.MOON_ROCK.get())
                .add(ModBlocks.POLISHED_MOON_ROCK.get())
                .add(ModBlocks.POLISHED_MOON_ROCK_STAIRS.get())
                .add(ModBlocks.POLISHED_MOON_ROCK_SLAB.get())
                .add(ModBlocks.MOON_ROCK_BRICKS.get())
                .add(ModBlocks.MOON_ROCK_BRICK_STAIRS.get())
                .add(ModBlocks.MOON_ROCK_BRICK_SLAB.get())
                .add(ModBlocks.CARBONITE.get())
                .add(ModBlocks.POLISHED_CARBONITE.get())
                .add(ModBlocks.POLISHED_CARBONITE_STAIRS.get())
                .add(ModBlocks.POLISHED_CARBONITE_SLAB.get())
                .add(ModBlocks.FRAMED_CARBONITE.get())
                .add(ModBlocks.PACKED_FRAMED_CARBONITE.get())
                .add(ModBlocks.POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.LARGE_POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.COATED_POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.LARGE_COATED_POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.SUN_TILES.get())
                .add(ModBlocks.LARGE_SUN_TILES.get())
                .add(ModBlocks.DARK_PANEL.get())
                .add(ModBlocks.DARK_PANEL_SLAB.get())
                .add(ModBlocks.TILED_DARK_PANEL.get())
                .add(ModBlocks.TILED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_DARK_PANEL.get())
                .add(ModBlocks.DECAYED_DARK_PANEL.get())
                .add(ModBlocks.DECAYED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.TILED_DECAYED_DARK_PANEL.get())
                .add(ModBlocks.TILED_DECAYED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_DECAYED_DARK_PANEL.get())
                .add(ModBlocks.LUNAR_PANEL.get())
                .add(ModBlocks.LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.TILED_LUNAR_PANEL.get())
                .add(ModBlocks.TILED_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_LUNAR_PANEL.get())
                .add(ModBlocks.OVERGROWN_LUNAR_PANEL.get())
                .add(ModBlocks.OVERGROWN_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL.get())
                .add(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_OVERGROWN_LUNAR_PANEL.get())
                .add(ModBlocks.PITCH_FRAMED_PADDING.get())
                .add(ModBlocks.DARK_FRAMED_PADDING.get())
                .add(ModBlocks.CLOUDY_FRAMED_PADDING.get())
                .add(ModBlocks.BRIGHT_FRAMED_PADDING.get())
                .add(ModBlocks.CONCRETE_SUPPORT_BEAM.get())
                .add(ModBlocks.WARM_CONCRETE_SUPPORT_BEAM.get())
                .add(ModBlocks.COLD_CONCRETE_SUPPORT_BEAM.get())
                .add(ModBlocks.CONCRETE_WALL.get())
                .add(ModBlocks.COATED_CONCRETE_WALL.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.COLD_WOODEN_PANEL.get())
                .add(ModBlocks.COLD_WOODEN_PANEL_SLAB.get())
                .add(ModBlocks.FRAMED_COLD_WOODEN_PANEL.get())
                .add(ModBlocks.DOUBLE_COLD_WOODEN_PANEL.get())
                .add(ModBlocks.LUSH_WOODEN_PANEL.get())
                .add(ModBlocks.LUSH_WOODEN_PANEL_SLAB.get())
                .add(ModBlocks.FRAMED_LUSH_WOODEN_PANEL.get())
                .add(ModBlocks.DOUBLE_LUSH_WOODEN_PANEL.get())
                .add(ModBlocks.COATED_WOODEN_PANEL.get())
                .add(ModBlocks.COATED_WOODEN_PANEL_SLAB.get())
                .add(ModBlocks.COATED_WOODEN_PANEL_TILES.get())
                .add(ModBlocks.COATED_WOODEN_PANEL_TILES_SLAB.get())
                .add(ModBlocks.COATED_WOODEN_PANEL_TILES_SIGNAL.get())
                .add(ModBlocks.DOUBLE_COATED_WOODEN_PANEL.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.DARK_PANEL.get())
                .add(ModBlocks.DARK_PANEL_SLAB.get())
                .add(ModBlocks.TILED_DARK_PANEL.get())
                .add(ModBlocks.TILED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_DARK_PANEL.get())
                .add(ModBlocks.DECAYED_DARK_PANEL.get())
                .add(ModBlocks.DECAYED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.TILED_DECAYED_DARK_PANEL.get())
                .add(ModBlocks.TILED_DECAYED_DARK_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_DECAYED_DARK_PANEL.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.STEEL_BLOCK.get())
                .add(ModBlocks.CUT_STEEL_BLOCK.get())
                .add(ModBlocks.CUT_STEEL_STAIRS.get())
                .add(ModBlocks.CUT_STEEL_SLAB.get())
                .add(ModBlocks.STEEL_GRATE.get())
                .add(ModBlocks.STEEL_DOOR.get())
                .add(ModBlocks.STEEL_TRAPDOOR.get())
                .add(ModBlocks.LUNAR_PANEL.get())
                .add(ModBlocks.LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.TILED_LUNAR_PANEL.get())
                .add(ModBlocks.TILED_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_LUNAR_PANEL.get())
                .add(ModBlocks.OVERGROWN_LUNAR_PANEL.get())
                .add(ModBlocks.OVERGROWN_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL.get())
                .add(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL_SLAB.get())
                .add(ModBlocks.DOUBLE_OVERGROWN_LUNAR_PANEL.get())
                .add(ModBlocks.MOON_ROCK.get())
                .add(ModBlocks.POLISHED_MOON_ROCK.get())
                .add(ModBlocks.POLISHED_MOON_ROCK_STAIRS.get())
                .add(ModBlocks.POLISHED_MOON_ROCK_SLAB.get())
                .add(ModBlocks.MOON_ROCK_BRICKS.get())
                .add(ModBlocks.MOON_ROCK_BRICK_STAIRS.get())
                .add(ModBlocks.MOON_ROCK_BRICK_SLAB.get())
                .add(ModBlocks.CARBONITE.get())
                .add(ModBlocks.POLISHED_CARBONITE.get())
                .add(ModBlocks.POLISHED_CARBONITE_STAIRS.get())
                .add(ModBlocks.POLISHED_CARBONITE_SLAB.get())
                .add(ModBlocks.POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.LARGE_POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.COATED_POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.LARGE_COATED_POLISHED_CARBONITE_TILES.get())
                .add(ModBlocks.SUN_TILES.get())
                .add(ModBlocks.LARGE_SUN_TILES.get())
                .add(ModBlocks.FRAMED_CARBONITE.get())
                .add(ModBlocks.PACKED_FRAMED_CARBONITE.get())
                .add(ModBlocks.CONCRETE_WALL.get())
                .add(ModBlocks.COATED_CONCRETE_WALL.get());
    }
}

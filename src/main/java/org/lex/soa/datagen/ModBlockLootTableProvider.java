package org.lex.soa.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.lex.soa.registery.ModBlocks;
import org.lex.soa.registery.ModItems;

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
        add(ModBlocks.MOON_ROCK.get(),
                block -> createRandomItemDrops(ModBlocks.MOON_ROCK.get(), ModItems.MOON_DUST.get(), 1, 4));
        dropSelf(ModBlocks.STEEL_PLATING.get());
        dropSelf(ModBlocks.LARGE_STEEL_PLATING.get());
        dropSelf(ModBlocks.POLISHED_MOON_ROCK.get());
        dropSelf(ModBlocks.POLISHED_MOON_ROCK_STAIRS.get());
        dropSelf(ModBlocks.POLISHED_MOON_ROCK_SLAB.get());
        dropSelf(ModBlocks.MOON_ROCK_BRICKS.get());
        dropSelf(ModBlocks.MOON_ROCK_BRICK_STAIRS.get());
        dropSelf(ModBlocks.MOON_ROCK_BRICK_SLAB.get());
        dropSelf(ModBlocks.CARBONITE.get());
        dropSelf(ModBlocks.POLISHED_CARBONITE.get());
        dropSelf(ModBlocks.POLISHED_CARBONITE_STAIRS.get());
        dropSelf(ModBlocks.POLISHED_CARBONITE_SLAB.get());
        dropSelf(ModBlocks.FRAMED_CARBONITE.get());
        dropSelf(ModBlocks.PACKED_FRAMED_CARBONITE.get());
        dropSelf(ModBlocks.POLISHED_CARBONITE_TILES.get());
        dropSelf(ModBlocks.LARGE_POLISHED_CARBONITE_TILES.get());
        dropSelf(ModBlocks.COATED_POLISHED_CARBONITE_TILES.get());
        dropSelf(ModBlocks.LARGE_COATED_POLISHED_CARBONITE_TILES.get());
        dropSelf(ModBlocks.SUN_TILES.get());
        dropSelf(ModBlocks.LARGE_SUN_TILES.get());
        dropSelf(ModBlocks.DARK_PANEL.get());
        add(ModBlocks.DARK_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.DARK_PANEL_SLAB.get()));
        dropSelf(ModBlocks.TILED_DARK_PANEL.get());
        add(ModBlocks.TILED_DARK_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TILED_DARK_PANEL_SLAB.get()));
        dropSelf(ModBlocks.DOUBLE_DARK_PANEL.get());
        dropSelf(ModBlocks.DECAYED_DARK_PANEL.get());
        add(ModBlocks.DECAYED_DARK_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.DECAYED_DARK_PANEL_SLAB.get()));
        dropSelf(ModBlocks.TILED_DECAYED_DARK_PANEL.get());
        add(ModBlocks.TILED_DECAYED_DARK_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TILED_DECAYED_DARK_PANEL_SLAB.get()));
        dropSelf(ModBlocks.DOUBLE_DECAYED_DARK_PANEL.get());
        dropSelf(ModBlocks.LUNAR_PANEL.get());
        add(ModBlocks.LUNAR_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.LUNAR_PANEL_SLAB.get()));
        dropSelf(ModBlocks.TILED_LUNAR_PANEL.get());
        add(ModBlocks.TILED_LUNAR_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TILED_LUNAR_PANEL_SLAB.get()));
        dropSelf(ModBlocks.DOUBLE_LUNAR_PANEL.get());
        dropSelf(ModBlocks.OVERGROWN_LUNAR_PANEL.get());
        add(ModBlocks.OVERGROWN_LUNAR_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.OVERGROWN_LUNAR_PANEL_SLAB.get()));
        dropSelf(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL.get());
        add(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.TILED_OVERGROWN_LUNAR_PANEL_SLAB.get()));
        dropSelf(ModBlocks.DOUBLE_OVERGROWN_LUNAR_PANEL.get());
        dropSelf(ModBlocks.COLD_WOODEN_PANEL.get());
        add(ModBlocks.COLD_WOODEN_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.COLD_WOODEN_PANEL_SLAB.get()));
        dropSelf(ModBlocks.FRAMED_COLD_WOODEN_PANEL.get());
        dropSelf(ModBlocks.DOUBLE_COLD_WOODEN_PANEL.get());
        dropSelf(ModBlocks.LUSH_WOODEN_PANEL.get());
        add(ModBlocks.LUSH_WOODEN_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.LUSH_WOODEN_PANEL_SLAB.get()));
        dropSelf(ModBlocks.FRAMED_LUSH_WOODEN_PANEL.get());
        dropSelf(ModBlocks.DOUBLE_LUSH_WOODEN_PANEL.get());
        dropSelf(ModBlocks.COATED_WOODEN_PANEL.get());
        add(ModBlocks.COATED_WOODEN_PANEL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.COATED_WOODEN_PANEL_SLAB.get()));
        dropSelf(ModBlocks.COATED_WOODEN_PANEL_TILES.get());
        add(ModBlocks.COATED_WOODEN_PANEL_TILES_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.COATED_WOODEN_PANEL_TILES_SLAB.get()));
        dropSelf(ModBlocks.COATED_WOODEN_PANEL_TILES_SIGNAL.get());
        dropSelf(ModBlocks.DOUBLE_COATED_WOODEN_PANEL.get());
        dropSelf(ModBlocks.PITCH_FRAMED_PADDING.get());
        dropSelf(ModBlocks.DARK_FRAMED_PADDING.get());
        dropSelf(ModBlocks.CLOUDY_FRAMED_PADDING.get());
        dropSelf(ModBlocks.BRIGHT_FRAMED_PADDING.get());
        dropSelf(ModBlocks.CONCRETE_SUPPORT_BEAM.get());
        dropSelf(ModBlocks.WARM_CONCRETE_SUPPORT_BEAM.get());
        dropSelf(ModBlocks.COLD_CONCRETE_SUPPORT_BEAM.get());
        dropSelf(ModBlocks.CONCRETE_WALL.get());
        dropSelf(ModBlocks.COATED_CONCRETE_WALL.get());

    }

    protected LootTable.Builder createRandomItemDrops(Block block, Item item, float minItems, float maxItems) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minItems, maxItems)))
                        .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                ));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}

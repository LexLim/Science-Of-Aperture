package org.lex.soa.registery;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lex.soa.Soa;
import org.lex.soa.blocks.CatwalkBlock;
import org.lex.soa.blocks.DoublePanelBlock;
import org.lex.soa.blocks.PaddedBlock;
import org.lex.soa.blocks.RailingBlock;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Soa.MOD_ID);

    public static final DeferredBlock<Block> STEEL_BLOCK = registerBlockWithItem("steel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.NETHERITE_BLOCK)
                    .strength(6f, 5f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> CUT_STEEL_BLOCK = registerBlockWithItem("cut_steel_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<StairBlock> CUT_STEEL_STAIRS = registerBlockWithItem("cut_steel_stairs",
            () -> new StairBlock(ModBlocks.STEEL_BLOCK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<SlabBlock> CUT_STEEL_SLAB = registerBlockWithItem("cut_steel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<TransparentBlock> STEEL_GRATE = registerBlockWithItem("steel_grate",
            () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get()).noOcclusion()));

    public static final DeferredBlock<DoorBlock> STEEL_DOOR = registerBlockWithItem("steel_door",
            () -> new DoorBlock(BlockSetType.COPPER,BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<TrapDoorBlock> STEEL_TRAPDOOR = registerBlockWithItem("steel_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.COPPER,BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<Block> STEEL_PLATING = registerBlockWithItem("steel_plating",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<Block> LARGE_STEEL_PLATING = registerBlockWithItem("large_steel_plating",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(STEEL_BLOCK.get())));

    public static final DeferredBlock<Block> MOON_ROCK = registerBlockWithItem("moon_rock",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.BASALT)
                    .strength(3f, 6f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> POLISHED_MOON_ROCK = registerBlockWithItem("polished_moon_rock",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MOON_ROCK.get())));

    public static final DeferredBlock<StairBlock> POLISHED_MOON_ROCK_STAIRS = registerBlockWithItem("polished_moon_rock_stairs",
            () -> new StairBlock(ModBlocks.MOON_ROCK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(MOON_ROCK.get())));

    public static final DeferredBlock<SlabBlock> POLISHED_MOON_ROCK_SLAB = registerBlockWithItem("polished_moon_rock_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOON_ROCK.get())));

    public static final DeferredBlock<Block> MOON_ROCK_BRICKS = registerBlockWithItem("moon_rock_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MOON_ROCK.get())));

    public static final DeferredBlock<StairBlock> MOON_ROCK_BRICK_STAIRS = registerBlockWithItem("moon_rock_brick_stairs",
            () -> new StairBlock(ModBlocks.MOON_ROCK.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(MOON_ROCK.get())));

    public static final DeferredBlock<SlabBlock> MOON_ROCK_BRICK_SLAB = registerBlockWithItem("moon_rock_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOON_ROCK.get())));

    public static final DeferredBlock<Block> CARBONITE = registerBlockWithItem("carbonite",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(1.5f, 6f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> POLISHED_CARBONITE = registerBlockWithItem("polished_carbonite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<StairBlock> POLISHED_CARBONITE_STAIRS = registerBlockWithItem("polished_carbonite_stairs",
            () -> new StairBlock(ModBlocks.CARBONITE.get().defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<SlabBlock> POLISHED_CARBONITE_SLAB = registerBlockWithItem("polished_carbonite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> FRAMED_CARBONITE = registerBlockWithItem("framed_carbonite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> PACKED_FRAMED_CARBONITE = registerBlockWithItem("packed_framed_carbonite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> POLISHED_CARBONITE_TILES = registerBlockWithItem("polished_carbonite_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> LARGE_POLISHED_CARBONITE_TILES = registerBlockWithItem("large_polished_carbonite_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> COATED_POLISHED_CARBONITE_TILES = registerBlockWithItem("coated_polished_carbonite_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> LARGE_COATED_POLISHED_CARBONITE_TILES = registerBlockWithItem("large_coated_polished_carbonite_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> SUN_TILES = registerBlockWithItem("sun_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> LARGE_SUN_TILES = registerBlockWithItem("large_sun_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CARBONITE.get())));

    public static final DeferredBlock<Block> DARK_PANEL = registerBlockWithItem("steel_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.COPPER)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> DARK_PANEL_SLAB = registerBlockWithItem("steel_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<Block> TILED_DARK_PANEL = registerBlockWithItem("tiled_steel_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<SlabBlock> TILED_DARK_PANEL_SLAB = registerBlockWithItem("tiled_steel_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_DARK_PANEL = registerBlockWithItem("double_steel_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<Block> DECAYED_DARK_PANEL = registerBlockWithItem("decayed_steel_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.COPPER)
                    .strength(3.4f, 4f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> DECAYED_DARK_PANEL_SLAB = registerBlockWithItem("decayed_steel_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DECAYED_DARK_PANEL.get())));

    public static final DeferredBlock<Block> TILED_DECAYED_DARK_PANEL = registerBlockWithItem("tiled_decayed_steel_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DECAYED_DARK_PANEL.get())));

    public static final DeferredBlock<SlabBlock> TILED_DECAYED_DARK_PANEL_SLAB = registerBlockWithItem("tiled_decayed_steel_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DECAYED_DARK_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_DECAYED_DARK_PANEL = registerBlockWithItem("double_decayed_steel_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(DECAYED_DARK_PANEL.get())));

    /*
    * test block to get default behaviour or DoublePanelBlock class
    public static final DeferredBlock<DoublePanelBlock> DOUBLE_TEST_PANEL = registerBlockWithItem("double_test_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));
     */

    public static final DeferredBlock<Block> LUNAR_PANEL = registerBlockWithItem("moon_rock_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.DEEPSLATE)
                    .strength(3f, 6f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> LUNAR_PANEL_SLAB = registerBlockWithItem("moon_rock_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<Block> TILED_LUNAR_PANEL = registerBlockWithItem("tiled_moon_rock_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<SlabBlock> TILED_LUNAR_PANEL_SLAB = registerBlockWithItem("tiled_moon_rock_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_LUNAR_PANEL = registerBlockWithItem("double_moon_rock_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<Block> OVERGROWN_LUNAR_PANEL = registerBlockWithItem("overgrown_moon_rock_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.DEEPSLATE)
                    .strength(2.4f, 4f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> OVERGROWN_LUNAR_PANEL_SLAB = registerBlockWithItem("overgrown_moon_rock_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(OVERGROWN_LUNAR_PANEL.get())));

    public static final DeferredBlock<Block> TILED_OVERGROWN_LUNAR_PANEL = registerBlockWithItem("tiled_overgrown_moon_rock_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(OVERGROWN_LUNAR_PANEL.get())));

    public static final DeferredBlock<SlabBlock> TILED_OVERGROWN_LUNAR_PANEL_SLAB = registerBlockWithItem("tiled_overgrown_moon_rock_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(OVERGROWN_LUNAR_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_OVERGROWN_LUNAR_PANEL = registerBlockWithItem("double_overgrown_moon_rock_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(OVERGROWN_LUNAR_PANEL.get())));

    public static final DeferredBlock<Block> COLD_WOODEN_PANEL = registerBlockWithItem("cold_wooden_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.WOOD)
                    .strength(2f, 3f)));

    public static final DeferredBlock<SlabBlock> COLD_WOODEN_PANEL_SLAB = registerBlockWithItem("cold_wooden_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COLD_WOODEN_PANEL.get())));

    public static final DeferredBlock<Block> FRAMED_COLD_WOODEN_PANEL = registerBlockWithItem("framed_cold_wooden_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(COLD_WOODEN_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_COLD_WOODEN_PANEL = registerBlockWithItem("double_cold_wooden_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(COLD_WOODEN_PANEL.get())));

    public static final DeferredBlock<Block> LUSH_WOODEN_PANEL = registerBlockWithItem("lush_wooden_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.WOOD)
                    .strength(1.4f, 2.8f)));

    public static final DeferredBlock<SlabBlock> LUSH_WOODEN_PANEL_SLAB = registerBlockWithItem("lush_wooden_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LUSH_WOODEN_PANEL.get())));

    public static final DeferredBlock<Block> FRAMED_LUSH_WOODEN_PANEL = registerBlockWithItem("framed_lush_wooden_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(LUSH_WOODEN_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_LUSH_WOODEN_PANEL = registerBlockWithItem("double_lush_wooden_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(LUSH_WOODEN_PANEL.get())));

    public static final DeferredBlock<Block> COATED_WOODEN_PANEL = registerBlockWithItem("coated_wooden_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.WOOD)
                    .strength(2.5f, 2.5f)));

    public static final DeferredBlock<SlabBlock> COATED_WOODEN_PANEL_SLAB = registerBlockWithItem("coated_wooden_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COATED_WOODEN_PANEL.get())));

    public static final DeferredBlock<Block> COATED_WOODEN_PANEL_TILES = registerBlockWithItem("coated_wooden_panel_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(COATED_WOODEN_PANEL.get())));

    public static final DeferredBlock<SlabBlock> COATED_WOODEN_PANEL_TILES_SLAB = registerBlockWithItem("coated_wooden_panel_tiles_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COATED_WOODEN_PANEL.get())));

    public static final DeferredBlock<Block> COATED_WOODEN_PANEL_TILES_SIGNAL = registerBlockWithItem("coated_wooden_panel_tiles_signal",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(COATED_WOODEN_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_COATED_WOODEN_PANEL = registerBlockWithItem("double_coated_wooden_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(COATED_WOODEN_PANEL.get())));

    public static final DeferredBlock<PaddedBlock> PITCH_FRAMED_PADDING = registerBlockWithItem("pitch_framed_padding",
            () -> new PaddedBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.FUNGUS)
                    .strength(1f, 0.3f)
                    .noOcclusion()));

    public static final DeferredBlock<PaddedBlock> DARK_FRAMED_PADDING = registerBlockWithItem("dark_framed_padding",
            () -> new PaddedBlock(BlockBehaviour.Properties.ofFullCopy(PITCH_FRAMED_PADDING.get())));

    public static final DeferredBlock<PaddedBlock> CLOUDY_FRAMED_PADDING = registerBlockWithItem("cloudy_framed_padding",
            () -> new PaddedBlock(BlockBehaviour.Properties.ofFullCopy(PITCH_FRAMED_PADDING.get())));

    public static final DeferredBlock<PaddedBlock> BRIGHT_FRAMED_PADDING = registerBlockWithItem("bright_framed_padding",
            () -> new PaddedBlock(BlockBehaviour.Properties.ofFullCopy(PITCH_FRAMED_PADDING.get())));

    public static final DeferredBlock<RotatedPillarBlock> CONCRETE_SUPPORT_BEAM = registerBlockWithItem("concrete_support_beam",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.METAL)
                    .strength(1.8f, 1.8f)));

    public static final DeferredBlock<RotatedPillarBlock> WARM_CONCRETE_SUPPORT_BEAM = registerBlockWithItem("warm_concrete_support_beam",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(CONCRETE_SUPPORT_BEAM.get())));

    public static final DeferredBlock<RotatedPillarBlock> COLD_CONCRETE_SUPPORT_BEAM = registerBlockWithItem("cold_concrete_support_beam",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(CONCRETE_SUPPORT_BEAM.get())));

    public static final DeferredBlock<Block> CONCRETE_WALL = registerBlockWithItem("concrete_wall",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(1.5f, 6f)));

    public static final DeferredBlock<Block> COATED_CONCRETE_WALL = registerBlockWithItem("coated_concrete_wall",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CONCRETE_WALL.get())));


    private static <T extends Block> DeferredBlock<T> registerBlockWithItem(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void  registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}

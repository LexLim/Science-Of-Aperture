package org.lex.soa.registery;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.lex.soa.Soa;
import org.lex.soa.blocks.DoublePanelBlock;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Soa.MOD_ID);

    public static final DeferredBlock<Block> DARK_PANEL = registerBlockWithItem("dark_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.COPPER)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> DARK_PANEL_SLAB = registerBlockWithItem("dark_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<Block> TILED_DARK_PANEL = registerBlockWithItem("tiled_dark_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<SlabBlock> TILED_DARK_PANEL_SLAB = registerBlockWithItem("tiled_dark_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_DARK_PANEL = registerBlockWithItem("double_dark_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_TEST_PANEL = registerBlockWithItem("double_test_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(DARK_PANEL.get())));

    public static final DeferredBlock<Block> LUNAR_PANEL = registerBlockWithItem("lunar_panel",
            () -> new Block(BlockBehaviour.Properties.of()
                    .sound(SoundType.DEEPSLATE)
                    .strength(3f, 6f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<SlabBlock> LUNAR_PANEL_SLAB = registerBlockWithItem("lunar_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<Block> TILED_LUNAR_PANEL = registerBlockWithItem("tiled_lunar_panel",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<SlabBlock> TILED_LUNAR_PANEL_SLAB = registerBlockWithItem("tiled_lunar_panel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));

    public static final DeferredBlock<DoublePanelBlock> DOUBLE_LUNAR_PANEL = registerBlockWithItem("double_lunar_panel",
            () -> new DoublePanelBlock(BlockBehaviour.Properties.ofFullCopy(LUNAR_PANEL.get())));
    

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

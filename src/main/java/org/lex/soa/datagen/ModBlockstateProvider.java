package org.lex.soa.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.lex.soa.Soa;
import org.lex.soa.registery.ModBlocks;

public class ModBlockstateProvider extends BlockStateProvider {
    public ModBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Soa.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock((RotatedPillarBlock) ModBlocks.DARK_PANEL.get());
        slabBlock(ModBlocks.DARK_PANEL_SLAB.get(), blockTexture(ModBlocks.DARK_PANEL_SLAB.get()), blockTexture(ModBlocks.DARK_PANEL_SLAB.get()));
        blockWithItem(ModBlocks.TILED_DARK_PANEL);
        slabBlock(ModBlocks.TILED_DARK_PANEL_SLAB.get(), blockTexture(ModBlocks.TILED_DARK_PANEL.get()), blockTexture(ModBlocks.TILED_DARK_PANEL.get()));
        logBlock((RotatedPillarBlock) ModBlocks.LUNAR_PANEL.get());
        slabBlock(ModBlocks.LUNAR_PANEL_SLAB.get(), blockTexture(ModBlocks.LUNAR_PANEL_SLAB.get()), blockTexture(ModBlocks.LUNAR_PANEL_SLAB.get()));
        blockWithItem(ModBlocks.TILED_LUNAR_PANEL);
        slabBlock(ModBlocks.TILED_LUNAR_PANEL_SLAB.get(), blockTexture(ModBlocks.TILED_LUNAR_PANEL.get()), blockTexture(ModBlocks.TILED_LUNAR_PANEL.get()));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("soa:block/" + deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockWithItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("soa:block/" + deferredBlock.getId().getPath() + appendix));
    }
}

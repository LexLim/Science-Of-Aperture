package org.lex.soa.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.client.model.generators.*;

import org.lex.soa.registery.ModBlocks;
import org.lex.soa.blocks.CubeDispenserBlock;
import org.lex.soa.blocks.enums.CubeDispenserPart;

public class CubeDispenserBlockStateProvider
        extends BlockStateProvider {

    public CubeDispenserBlockStateProvider(
            PackOutput output,
            ExistingFileHelper helper
    ) {
        super(output, "soa", helper);
    }

    @Override
    protected void registerStatesAndModels() {

        Block block =
                ModBlocks.CUBE_DISPENSER.get();

        getVariantBuilder(block)
                .forAllStates(state -> {

                    CubeDispenserPart part =
                            state.getValue(
                                    CubeDispenserBlock.PART
                            );

                    Direction facing =
                            state.getValue(
                                    CubeDispenserBlock.FACING
                            );

                    boolean open =
                            state.getValue(
                                    CubeDispenserBlock.OPEN
                            );

                    String partName =
                            part.getSerializedName();

                    String modelName =
                            "cube_dispenser_" +
                                    partName +
                                    (open ? "_open" : "");

                    ModelFile model =
                            models().getExistingFile(
                                    modLoc("block/" + modelName)
                            );

                    return ConfiguredModel.builder()
                            .modelFile(model)
                            .rotationY(getYRotation(facing))
                            .build();

                });

    }

    private int getYRotation(
            Direction facing
    ) {

        return switch (facing) {

            case NORTH -> 0;
            case EAST  -> 90;
            case SOUTH -> 180;
            case WEST  -> 270;

            default -> 0;
        };

    }

}
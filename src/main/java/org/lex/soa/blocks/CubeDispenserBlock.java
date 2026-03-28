package org.lex.soa.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;

import org.lex.soa.blocks.enums.CubeDispenserPart;

public class CubeDispenserBlock extends Block {

    public static final DirectionProperty FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    public static final EnumProperty<AttachFace> FACE =
            BlockStateProperties.ATTACH_FACE;

    public static final EnumProperty<CubeDispenserPart> PART =
            EnumProperty.create("part", CubeDispenserPart.class);

    public static final BooleanProperty OPEN =
            BooleanProperty.create("open");

    private static boolean isDestroying = false;
    private static boolean isPlacingStructure = false;

    public CubeDispenserBlock(Properties properties) {

        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(FACE, AttachFace.CEILING)
                        .setValue(
                                PART,
                                CubeDispenserPart.TOP_BACK_LEFT
                        )
                        .setValue(OPEN, false)
        );

    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {

        builder.add(FACING, FACE, PART, OPEN);

    }

    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {

        if (context.getClickedFace()
                != Direction.DOWN)
            return null;

        Direction facing =
                context.getHorizontalDirection();

        Level level =
                context.getLevel();

        BlockPos origin =
                context.getClickedPos();

        if (!canPlaceStructure(
                level,
                origin,
                facing
        )) return null;

        if (!hasSupport(level, origin))
            return null;

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(FACE, AttachFace.CEILING)
                .setValue(OPEN, false);

    }

    private BlockPos[] getAllPositions(
            BlockPos origin,
            Direction facing
    ) {

        Direction right =
                facing.getCounterClockWise();

        BlockPos front =
                origin.relative(facing);

        BlockPos bottom =
                origin.below();

        BlockPos frontBottom =
                front.below();

        return new BlockPos[] {

                /* Bottom layer */

                bottom,
                bottom.relative(right),

                frontBottom,
                frontBottom.relative(right),

                /* Top layer */

                origin,
                origin.relative(right),

                front,
                front.relative(right)

        };

    }

    private boolean canPlaceStructure(
            LevelReader level,
            BlockPos origin,
            Direction facing
    ) {

        for (BlockPos pos :
                getAllPositions(origin, facing)) {

            if (!level.getBlockState(pos)
                    .canBeReplaced())
                return false;

        }

        return true;

    }

    private boolean hasSupport(
            LevelReader level,
            BlockPos origin
    ) {

        BlockPos support =
                origin.above();

        return level.getBlockState(support)
                .isFaceSturdy(
                        level,
                        support,
                        Direction.DOWN
                );

    }

    @Override
    public void onPlace(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState oldState,
            boolean isMoving
    ) {

        if (level.isClientSide) return;

        if (isPlacingStructure) return;

        if (state.getValue(PART)
                != CubeDispenserPart.TOP_BACK_LEFT)
            return;

        isPlacingStructure = true;

        placeStructure(
                level,
                pos,
                state.getValue(FACING)
        );

        isPlacingStructure = false;

        level.scheduleTick(pos, this, 1);

    }

    private void placeStructure(
            Level level,
            BlockPos origin,
            Direction facing
    ) {

        BlockPos[] positions =
                getAllPositions(origin, facing);

        for (int i = 0; i < positions.length; i++) {

            level.setBlock(
                    positions[i],
                    this.defaultBlockState()
                            .setValue(FACING, facing)
                            .setValue(
                                    PART,
                                    CubeDispenserPart.byIndex(i)
                            )
                            .setValue(OPEN, false),
                    Block.UPDATE_ALL
            );

        }

    }

    private void destroyStructure(
            Level level,
            BlockPos origin,
            Direction facing
    ) {

        if (isDestroying) return;

        isDestroying = true;

        for (BlockPos pos :
                getAllPositions(origin, facing)) {

            if (level.getBlockState(pos)
                    .getBlock()
                    instanceof CubeDispenserBlock) {

                level.setBlock(
                        pos,
                        Blocks.AIR.defaultBlockState(),
                        Block.UPDATE_ALL
                );

            }

        }

        isDestroying = false;

    }

    @Override
    public void tick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {

        if (!hasSupport(level, pos)) {

            destroyStructure(
                    level,
                    pos,
                    state.getValue(FACING)
            );

        }

    }

}
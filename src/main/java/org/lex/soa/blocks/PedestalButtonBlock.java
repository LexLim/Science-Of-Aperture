package org.lex.soa.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PedestalButtonBlock extends Block {

    public static final EnumProperty<AttachFace> FACE =
            BlockStateProperties.ATTACH_FACE;

    public static final DirectionProperty FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    public static final BooleanProperty POWERED =
            BlockStateProperties.POWERED;

    private static final int PRESS_DURATION = 20;

    private static final VoxelShape FLOOR_SHAPE =
            Block.box(5.75, 0, 5.75, 10.25, 20, 10.25);

    private static final VoxelShape CEILING_SHAPE =
            Block.box(5.75, -4, 5.75, 10.25, 16, 10.25);

    private static final VoxelShape NORTH_SHAPE =
            Block.box(5.75, 5.75, -4, 10.25, 10.25, 16);

    private static final VoxelShape SOUTH_SHAPE =
            Block.box(5.75, 5.75, 0, 10.25, 10.25, 20);

    private static final VoxelShape WEST_SHAPE =
            Block.box(-4, 5.75, 5.75, 16, 10.25, 10.25);

    private static final VoxelShape EAST_SHAPE =
            Block.box(0, 5.75, 5.75, 20, 10.25, 10.25);

    public PedestalButtonBlock(Properties properties) {

        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACE, AttachFace.FLOOR)
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false)
        );

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction clickedFace = context.getClickedFace();

        Direction horizontal =
                context.getHorizontalDirection().getOpposite();

        AttachFace face;

        if (clickedFace == Direction.UP) {

            face = AttachFace.FLOOR;

        } else if (clickedFace == Direction.DOWN) {

            face = AttachFace.CEILING;

        } else {

            face = AttachFace.WALL;

        }

        return this.defaultBlockState()
                .setValue(FACE, face)
                .setValue(FACING, horizontal);

    }

    @Override
    public boolean canSurvive(
            BlockState state,
            LevelReader level,
            BlockPos pos
    ) {

        Direction supportDirection =
                getSupportDirection(state);

        BlockPos supportPos =
                pos.relative(supportDirection);

        return level.getBlockState(supportPos)
                .isSolid();

    }

    private Direction getSupportDirection(BlockState state) {

        AttachFace face =
                state.getValue(FACE);

        Direction facing =
                state.getValue(FACING);

        return switch (face) {

            case FLOOR -> Direction.DOWN;
            case CEILING -> Direction.UP;
            case WALL -> facing.getOpposite();

        };

    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    ) {

        if (!state.canSurvive(level, pos)) {

            return Blocks.AIR.defaultBlockState();

        }

        return state;

    }
    @Override
    public InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {

        if (!state.getValue(POWERED)) {

            if (!level.isClientSide) {

                level.setBlock(
                        pos,
                        state.setValue(POWERED, true),
                        3
                );

                level.updateNeighborsAt(pos, this);

                level.scheduleTick(
                        pos,
                        this,
                        PRESS_DURATION
                );

            }

            return InteractionResult.SUCCESS;

        }

        return InteractionResult.CONSUME;

    }
    @Override
    public void tick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random
    ) {

        if (state.getValue(POWERED)) {

            level.setBlock(
                    pos,
                    state.setValue(POWERED, false),
                    3
            );

            level.updateNeighborsAt(pos, this);

        }

    }

    @Override
    public boolean isSignalSource(BlockState state) {

        return true;

    }

    @Override
    public int getSignal(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            Direction direction
    ) {

        return state.getValue(POWERED) ? 15 : 0;

    }
    @Override
    public VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {

        AttachFace face =
                state.getValue(FACE);

        Direction facing =
                state.getValue(FACING);

        if (face == AttachFace.FLOOR) {

            return FLOOR_SHAPE;

        }

        if (face == AttachFace.CEILING) {

            return CEILING_SHAPE;

        }

        return switch (facing) {

            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> FLOOR_SHAPE;

        };

    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {

        builder.add(
                FACE,
                FACING,
                POWERED
        );

    }

}
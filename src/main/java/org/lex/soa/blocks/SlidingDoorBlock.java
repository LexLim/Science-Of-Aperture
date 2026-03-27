package org.lex.soa.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;

import org.lex.soa.blocks.enums.SlidingDoorPart;
import org.lex.soa.registery.SoaSounds;

public class SlidingDoorBlock extends Block {

    public static final BooleanProperty OPEN =
            BooleanProperty.create("open");

    public static final EnumProperty<SlidingDoorPart> PART =
            EnumProperty.create("part", SlidingDoorPart.class);

    public static final DirectionProperty FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape CLOSED_EW =
            Block.box(3.5, 0, 0, 12.5, 16, 16);

    private static final VoxelShape CLOSED_NS =
            Block.box(0, 0, 3.5, 16, 16, 12.5);

    private static final VoxelShape OPEN_EAST =
            Block.box(3.5, 0, 12, 12.5, 16, 16);

    private static final VoxelShape OPEN_WEST =
            Block.box(3.5, 0, 0, 12.5, 16, 4);

    private static final VoxelShape OPEN_NORTH =
            Block.box(12, 0, 3.5, 16, 16, 12.5);

    private static final VoxelShape OPEN_SOUTH =
            Block.box(0, 0, 3.5, 4, 16, 12.5);

    public SlidingDoorBlock(Properties properties) {

        super(properties
                .strength(3.0f)
                .sound(SoundType.METAL)
                .noOcclusion());

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(OPEN, false)
                        .setValue(PART, SlidingDoorPart.BOTTOM_LEFT)
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(OPEN, PART, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        Direction facing =
                context.getHorizontalDirection();

        Direction rightDir =
                facing.getClockWise();

        BlockPos right =
                pos.relative(rightDir);

        BlockPos top =
                pos.above();

        BlockPos topRight =
                top.relative(rightDir);

        if (!level.getBlockState(right).canBeReplaced(context)
                || !level.getBlockState(top).canBeReplaced(context)
                || !level.getBlockState(topRight).canBeReplaced(context)) {

            return null;
        }

        level.setBlock(
                right,
                defaultBlockState()
                        .setValue(PART, SlidingDoorPart.BOTTOM_RIGHT)
                        .setValue(FACING, facing),
                3);

        level.setBlock(
                top,
                defaultBlockState()
                        .setValue(PART, SlidingDoorPart.TOP_LEFT)
                        .setValue(FACING, facing),
                3);

        level.setBlock(
                topRight,
                defaultBlockState()
                        .setValue(PART, SlidingDoorPart.TOP_RIGHT)
                        .setValue(FACING, facing),
                3);

        return defaultBlockState()
                .setValue(PART, SlidingDoorPart.BOTTOM_LEFT)
                .setValue(FACING, facing);
    }

    private BlockPos getBasePos(BlockPos pos, BlockState state) {

        Direction facing =
                state.getValue(FACING);

        Direction rightDir =
                facing.getClockWise();

        return switch (state.getValue(PART)) {

            case BOTTOM_LEFT -> pos;

            case BOTTOM_RIGHT ->
                    pos.relative(rightDir.getOpposite());

            case TOP_LEFT ->
                    pos.below();

            case TOP_RIGHT ->
                    pos.below()
                            .relative(rightDir.getOpposite());
        };
    }

    private boolean isComplete(
            LevelAccessor level,
            BlockPos base,
            Direction facing) {

        Direction rightDir =
                facing.getClockWise();

        return level.getBlockState(base).getBlock() == this
                && level.getBlockState(base.relative(rightDir)).getBlock() == this
                && level.getBlockState(base.above()).getBlock() == this
                && level.getBlockState(base.above().relative(rightDir)).getBlock() == this;
    }

    private void destroyStructure(
            LevelAccessor level,
            BlockPos base,
            Direction facing) {

        Direction rightDir =
                facing.getClockWise();

        level.setBlock(base, Blocks.AIR.defaultBlockState(), 35);
        level.setBlock(base.relative(rightDir), Blocks.AIR.defaultBlockState(), 35);
        level.setBlock(base.above(), Blocks.AIR.defaultBlockState(), 35);
        level.setBlock(base.above().relative(rightDir), Blocks.AIR.defaultBlockState(), 35);
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos) {

        Direction facing =
                state.getValue(FACING);

        BlockPos base =
                getBasePos(pos, state);

        if (!isComplete(level, base, facing)) {

            destroyStructure(level, base, facing);
            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }

    @Override
    public void neighborChanged(
            BlockState state,
            Level level,
            BlockPos pos,
            Block block,
            BlockPos fromPos,
            boolean isMoving) {

        if (level.isClientSide) return;

        Direction facing =
                state.getValue(FACING);

        BlockPos base =
                getBasePos(pos, state);

        if (!isComplete(level, base, facing)) {

            destroyStructure(level, base, facing);
            return;
        }

        Direction rightDir =
                facing.getClockWise();

        boolean powered =
                level.hasNeighborSignal(base)
                        || level.hasNeighborSignal(base.relative(rightDir))
                        || level.hasNeighborSignal(base.above())
                        || level.hasNeighborSignal(base.above().relative(rightDir));

        boolean open =
                level.getBlockState(base).getValue(OPEN);

        if (powered != open) {

            setOpen(level, base, facing, powered);
        }
    }

    private void setOpen(
            Level level,
            BlockPos base,
            Direction facing,
            boolean open) {

        Direction rightDir =
                facing.getClockWise();

        updatePart(level, base, open);
        updatePart(level, base.relative(rightDir), open);
        updatePart(level, base.above(), open);
        updatePart(level, base.above().relative(rightDir), open);

        level.playSound(
                null,
                base,
                open
                        ? SoaSounds.SLIDING_DOOR_OPEN.get()
                        : SoaSounds.SLIDING_DOOR_CLOSE.get(),
                SoundSource.BLOCKS,
                0.48F,
                1.0F
        );
    }

    private void updatePart(
            Level level,
            BlockPos pos,
            boolean open) {

        BlockState state =
                level.getBlockState(pos);

        if (state.getBlock() == this) {

            level.setBlock(
                    pos,
                    state.setValue(OPEN, open),
                    3);
        }
    }

    @Override
    public VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        return state.getValue(FACING)
                .getAxis() == Direction.Axis.X
                ? CLOSED_EW
                : CLOSED_NS;
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        Direction facing =
                state.getValue(FACING);

        SlidingDoorPart part =
                state.getValue(PART);

        boolean open =
                state.getValue(OPEN);

        boolean isLeft =
                part == SlidingDoorPart.BOTTOM_LEFT
                        || part == SlidingDoorPart.TOP_LEFT;

        if (!open) {

            return facing.getAxis() == Direction.Axis.X
                    ? CLOSED_EW
                    : CLOSED_NS;
        }

        return switch (facing) {

            case EAST ->
                    isLeft ? OPEN_WEST : OPEN_EAST;

            case WEST ->
                    isLeft ? OPEN_EAST : OPEN_WEST;

            case NORTH ->
                    isLeft ? OPEN_SOUTH : OPEN_NORTH;

            case SOUTH ->
                    isLeft ? OPEN_NORTH : OPEN_SOUTH;

            default ->
                    CLOSED_EW;
        };
    }
}
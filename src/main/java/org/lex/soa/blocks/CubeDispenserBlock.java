package org.lex.soa.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;

import org.lex.soa.blocks.enums.CubeDispenserPart;

public class CubeDispenserBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
    public static final EnumProperty<CubeDispenserPart> PART = EnumProperty.create("part", CubeDispenserPart.class);
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    private static boolean placing = false;
    private static boolean updating = false;
    private static boolean destroying = false;

    public CubeDispenserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.CEILING)
                .setValue(PART, CubeDispenserPart.TOP_BACK_LEFT)
                .setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE, PART, OPEN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace() != Direction.DOWN) return null;
        Direction facing = context.getHorizontalDirection();
        BlockPos origin = context.getClickedPos();
        Level level = context.getLevel();
        if (!canPlace(level, origin, facing)) return null;
        if (!hasSupport(level, origin, facing)) return null;
        return defaultBlockState()
                .setValue(FACING, facing)
                .setValue(FACE, AttachFace.CEILING)
                .setValue(PART, CubeDispenserPart.TOP_BACK_LEFT)
                .setValue(OPEN, false);
    }

    private BlockPos partPos(BlockPos origin, Direction facing, CubeDispenserPart part) {
        Direction right = facing.getClockWise();
        BlockPos o = part.getOffset();
        return origin.relative(right, o.getX())
                .relative(Direction.UP, o.getY())
                .relative(facing, o.getZ());
    }

    private BlockPos originPos(BlockPos pos, Direction facing, CubeDispenserPart part) {
        Direction right = facing.getClockWise();
        BlockPos o = part.getOffset();
        return pos.relative(right.getOpposite(), o.getX())
                .relative(Direction.DOWN, o.getY())
                .relative(facing.getOpposite(), o.getZ());
    }

    private boolean canPlace(LevelReader level, BlockPos origin, Direction facing) {
        for (CubeDispenserPart part : CubeDispenserPart.values()) {
            if (!level.getBlockState(partPos(origin, facing, part)).canBeReplaced()) return false;
        }
        return true;
    }

    private boolean hasSupport(LevelReader level, BlockPos origin, Direction facing) {
        CubeDispenserPart[] topParts = new CubeDispenserPart[]{
                CubeDispenserPart.TOP_FRONT_LEFT,
                CubeDispenserPart.TOP_FRONT_RIGHT,
                CubeDispenserPart.TOP_BACK_LEFT,
                CubeDispenserPart.TOP_BACK_RIGHT
        };
        for (CubeDispenserPart part : topParts) {
            BlockPos topPos = partPos(origin, facing, part);
            BlockPos support = topPos.above();
            if (!level.getBlockState(support).isFaceSturdy(level, support, Direction.DOWN)) return false;
        }
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
        if (level.isClientSide) return;
        if (placing) return;
        if (state.getValue(PART) != CubeDispenserPart.TOP_BACK_LEFT) return;
        placing = true;
        Direction facing = state.getValue(FACING);
        for (CubeDispenserPart part : CubeDispenserPart.values()) {
            BlockPos p = partPos(pos, facing, part);
            level.setBlock(p, defaultBlockState().setValue(FACING, facing).setValue(PART, part).setValue(OPEN, false), Block.UPDATE_ALL);
        }
        placing = false;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean moving) {
        if (level.isClientSide) return;
        level.scheduleTick(pos, this, 1);
        if (updating) return;
        Direction facing = state.getValue(FACING);
        CubeDispenserPart part = state.getValue(PART);
        BlockPos origin = originPos(pos, facing, part);
        BlockState originState = level.getBlockState(origin);
        if (!(originState.getBlock() instanceof CubeDispenserBlock)) return;
        boolean powered = false;
        for (CubeDispenserPart p : CubeDispenserPart.values()) {
            BlockPos check = partPos(origin, facing, p);
            if (level.hasNeighborSignal(check)) {
                powered = true;
                break;
            }
        }
        boolean open = originState.getValue(OPEN);
        if (powered != open) {
            updating = true;
            for (CubeDispenserPart p : CubeDispenserPart.values()) {
                BlockPos update = partPos(origin, facing, p);
                BlockState s = level.getBlockState(update);
                if (s.getBlock() instanceof CubeDispenserBlock) {
                    level.setBlock(update, s.setValue(OPEN, powered), Block.UPDATE_CLIENTS);
                }
            }
            updating = false;
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (destroying) return;
        Direction facing = state.getValue(FACING);
        CubeDispenserPart part = state.getValue(PART);
        BlockPos origin = originPos(pos, facing, part);
        if (!hasSupport(level, origin, facing)) {
            destroying = true;
            for (CubeDispenserPart p : CubeDispenserPart.values()) {
                BlockPos destroy = partPos(origin, facing, p);
                if (level.getBlockState(destroy).getBlock() instanceof CubeDispenserBlock) {
                    level.setBlock(destroy, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
            destroying = false;
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && !destroying) {
            destroying = true;
            Direction facing = state.getValue(FACING);
            CubeDispenserPart part = state.getValue(PART);
            BlockPos origin = originPos(pos, facing, part);
            for (CubeDispenserPart p : CubeDispenserPart.values()) {
                BlockPos destroy = partPos(origin, facing, p);
                if (!destroy.equals(pos)) {
                    BlockState s = level.getBlockState(destroy);
                    if (s.getBlock() instanceof CubeDispenserBlock) {
                        level.setBlock(destroy, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                    }
                }
            }
            destroying = false;
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState other, Direction side) {
        return other.getBlock() instanceof CubeDispenserBlock;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }
}
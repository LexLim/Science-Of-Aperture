package org.lex.soa.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.lex.soa.blocks.enums.FloorButtonPart;

public class FloorButtonBlock extends Block {

    public static final BooleanProperty PRESSED =
            BooleanProperty.create("pressed");

    public static final EnumProperty<FloorButtonPart> PART =
            EnumProperty.create("part", FloorButtonPart.class);

    private static final VoxelShape SW_SHAPE =
            Block.box(8, 0, 0, 16, 5, 8);

    private static final VoxelShape SE_SHAPE =
            Block.box(0, 0, 0, 8, 5, 8);

    private static final VoxelShape NW_SHAPE =
            Block.box(8, 0, 8, 16, 5, 16);

    private static final VoxelShape NE_SHAPE =
            Block.box(0, 0, 8, 8, 5, 16);

    private static final VoxelShape SW_SHAPE_PRESSED =
            Block.box(8, 0, 0, 16, 4, 8);

    private static final VoxelShape SE_SHAPE_PRESSED =
            Block.box(0, 0, 0, 8, 4, 8);

    private static final VoxelShape NW_SHAPE_PRESSED =
            Block.box(8, 0, 8, 16, 4, 16);

    private static final VoxelShape NE_SHAPE_PRESSED =
            Block.box(0, 0, 8, 8, 4, 16);

    public FloorButtonBlock(Properties properties) {

        super(Properties.of()
                .strength(2.0f)
                .sound(SoundType.METAL)
                .noOcclusion());

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(PRESSED, false)
                        .setValue(PART, FloorButtonPart.SOUTHWEST)
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(PRESSED, PART);
    }

    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos northeast = north.east();

        if (!level.getBlockState(north).canBeReplaced(context)
                || !level.getBlockState(east).canBeReplaced(context)
                || !level.getBlockState(northeast).canBeReplaced(context)) {

            return null;
        }

        if (!hasSupport(level, pos)
                || !hasSupport(level, north)
                || !hasSupport(level, east)
                || !hasSupport(level, northeast)) {

            return null;
        }

        AABB placementBox = new AABB(
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                pos.getX() + 2,
                pos.getY() + 1,
                pos.getZ() + 2
        );

        if (!level.getEntities(null, placementBox).isEmpty()) {
            return null;
        }

        level.setBlock(
                north,
                defaultBlockState()
                        .setValue(PART, FloorButtonPart.NORTHWEST),
                3);

        level.setBlock(
                east,
                defaultBlockState()
                        .setValue(PART, FloorButtonPart.SOUTHEAST),
                3);

        level.setBlock(
                northeast,
                defaultBlockState()
                        .setValue(PART, FloorButtonPart.NORTHEAST),
                3);

        return defaultBlockState()
                .setValue(PART, FloorButtonPart.SOUTHWEST);
    }

    private boolean hasSupport(LevelAccessor level, BlockPos pos) {

        return level.getBlockState(pos.below())
                .isFaceSturdy(level, pos.below(), Direction.UP);
    }

    private boolean isValidPart(LevelAccessor level, BlockPos pos) {

        return level.getBlockState(pos).getBlock() == this;
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos) {

        BlockPos base = getBasePos(pos, state);

        BlockPos north = base.north();
        BlockPos east = base.east();
        BlockPos northeast = north.east();

        if (!isValidPart(level, base)
                || !isValidPart(level, north)
                || !isValidPart(level, east)
                || !isValidPart(level, northeast)
                || !hasSupport(level, base)
                || !hasSupport(level, north)
                || !hasSupport(level, east)
                || !hasSupport(level, northeast)) {

            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }

    @Override
    public BlockState playerWillDestroy(
            Level level,
            BlockPos pos,
            BlockState state,
            Player player) {

        BlockPos base = getBasePos(pos, state);

        removeAllParts(level, base);

        return super.playerWillDestroy(
                level,
                pos,
                state,
                player);
    }

    private void removeAllParts(
            Level level,
            BlockPos base) {

        level.removeBlock(base, false);
        level.removeBlock(base.north(), false);
        level.removeBlock(base.east(), false);
        level.removeBlock(base.north().east(), false);
    }

    private boolean hasPlayerOnButton(
            ServerLevel level,
            BlockPos base) {

        return checkPart(level, base)
                || checkPart(level, base.north())
                || checkPart(level, base.east())
                || checkPart(level, base.north().east());
    }

    private boolean checkPart(
            ServerLevel level,
            BlockPos pos) {

        BlockState state =
                level.getBlockState(pos);

        if (state.getBlock() != this) return false;

        VoxelShape shape =
                getCollisionShape(
                        state,
                        level,
                        pos,
                        CollisionContext.empty());

        AABB box =
                shape.bounds()
                        .move(pos)
                        .inflate(0, 0.1, 0);

        return !level.getEntitiesOfClass(
                Player.class,
                box).isEmpty();
    }

    @Override
    protected void tick(
            BlockState state,
            ServerLevel level,
            BlockPos pos,
            RandomSource random) {

        BlockPos base =
                getBasePos(pos, state);

        boolean hasPlayer =
                hasPlayerOnButton(level, base);

        boolean pressed =
                level.getBlockState(base)
                        .getValue(PRESSED);

        if (hasPlayer != pressed) {

            setPressed(level, base, hasPlayer);
        }

        if (hasPlayer) {

            level.scheduleTick(base, this, 1);
        }
    }

    @Override
    public void stepOn(
            Level level,
            BlockPos pos,
            BlockState state,
            Entity entity) {

        if (!(entity instanceof Player)) return;

        if (!level.isClientSide) {

            BlockPos base =
                    getBasePos(pos, state);

            level.scheduleTick(
                    base,
                    this,
                    1);
        }

        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void entityInside(
            BlockState state,
            Level level,
            BlockPos pos,
            Entity entity) {

        if (!(entity instanceof Player)) return;

        if (!level.isClientSide) {

            BlockPos base =
                    getBasePos(pos, state);

            level.scheduleTick(
                    base,
                    this,
                    1);
        }
    }

    private void setPressed(
            Level level,
            BlockPos base,
            boolean pressed) {

        BlockState baseState =
                level.getBlockState(base);

        boolean wasPressed =
                baseState.getValue(PRESSED);

        if (wasPressed == pressed) return;

        updatePart(level, base, pressed);
        updatePart(level, base.north(), pressed);
        updatePart(level, base.east(), pressed);
        updatePart(level, base.north().east(), pressed);

        if (!level.isClientSide) {

            if (pressed) {

                level.playSound(
                        null,
                        base,
                        org.lex.soa.registery.SoaSounds
                                .FLOOR_BUTTON_PRESS.get(),
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        0.4F,
                        1.0F
                );

            } else {

                level.playSound(
                        null,
                        base,
                        org.lex.soa.registery.SoaSounds
                                .FLOOR_BUTTON_RELEASE.get(),
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        0.4F,
                        1.0F
                );
            }
        }

        level.updateNeighborsAt(base, this);
        level.updateNeighborsAt(base.north(), this);
        level.updateNeighborsAt(base.east(), this);
        level.updateNeighborsAt(base.north().east(), this);
    }

    private void updatePart(
            Level level,
            BlockPos pos,
            boolean pressed) {

        BlockState state =
                level.getBlockState(pos);

        if (state.getBlock() == this) {

            level.setBlock(
                    pos,
                    state.setValue(PRESSED, pressed),
                    3);
        }
    }

    private BlockPos getBasePos(
            BlockPos pos,
            BlockState state) {

        return switch (
                state.getValue(PART)) {

            case SOUTHWEST -> pos;
            case SOUTHEAST -> pos.west();
            case NORTHWEST -> pos.south();
            case NORTHEAST -> pos.south().west();
        };
    }

    @Override
    public VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        return getCollisionShape(
                state,
                level,
                pos,
                context);
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        boolean pressed =
                state.getValue(PRESSED);

        return switch (state.getValue(PART)) {

            case SOUTHWEST ->
                    pressed ? SW_SHAPE_PRESSED : SW_SHAPE;

            case SOUTHEAST ->
                    pressed ? SE_SHAPE_PRESSED : SE_SHAPE;

            case NORTHWEST ->
                    pressed ? NW_SHAPE_PRESSED : NW_SHAPE;

            case NORTHEAST ->
                    pressed ? NE_SHAPE_PRESSED : NE_SHAPE;
        };
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
            Direction direction) {

        return state.getValue(PRESSED)
                ? 15
                : 0;
    }
}
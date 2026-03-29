package fuzs.additionalsubtractions.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneCrossingBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final VoxelShape SHAPE = Shapes.or(Block.box(5.0, 0.0, 0.0, 11.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 5.0, 16.0, 3.0, 11.0));

    public RedstoneCrossingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition()
                .any()
                .setValue(NORTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // respect south-east rule: https://minecraft.wiki/w/South-east_rule
        state = this.updateSignalOutput(state, level, pos, Direction.NORTH);
        state = this.updateSignalOutput(state, level, pos, Direction.SOUTH);
        state = this.updateSignalOutput(state, level, pos, Direction.WEST);
        state = this.updateSignalOutput(state, level, pos, Direction.EAST);
        level.setBlock(pos, state, 2);
        this.updateNeighbors(level, pos, state, false);
    }

    private BlockState updateSignalOutput(BlockState state, Level level, BlockPos pos, Direction direction) {
        if (!state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction)) &&
                level.hasSignal(pos.relative(direction), direction)) {
            return state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()), Boolean.TRUE);
        } else {
            return state;
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (this.shouldTurnOn(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
    }

    protected boolean shouldTurnOn(SignalGetter level, BlockPos pos) {
        if (level.hasSignal(pos.north(), Direction.NORTH)) {
            return true;
        } else if (level.hasSignal(pos.south(), Direction.SOUTH)) {
            return true;
        } else if (level.hasSignal(pos.west(), Direction.WEST)) {
            return true;
        } else if (level.hasSignal(pos.east(), Direction.EAST)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        BlockPos blockPos = pos.subtract(neighborPos);
        Direction direction = Direction.fromDelta(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (direction != null && direction.getAxis().isHorizontal() &&
                !state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()))) {
            level.setBlock(pos, state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction), Boolean.FALSE), 2);
            level.scheduleTick(pos, this, 2);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction)) ||
                    state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()))) {
                this.spawnParticlesAlongLine(level,
                        random,
                        pos,
                        new Vec3(1.0, 0.2, 0.0),
                        Direction.DOWN,
                        direction,
                        0.0F,
                        0.5F,
                        direction.getAxis() == Direction.Axis.X ? 0.1875F : 0.125F);
            }
        }
    }

    /**
     * Copied from
     * {@link net.minecraft.world.level.block.RedStoneWireBlock#spawnParticlesAlongLine(Level, RandomSource, BlockPos,
     * Vec3, Direction, Direction, float, float)} with additional y-offset parameter.
     */
    private void spawnParticlesAlongLine(Level level, RandomSource random, BlockPos pos, Vec3 particleVec, Direction xDirection, Direction zDirection, float min, float max, float offsetY) {
        float f = max - min;
        if (!(random.nextFloat() >= 0.2F * f)) {
            float h = min + f * random.nextFloat();
            double d = 0.5 + (double) (0.4375F * (float) xDirection.getStepX()) +
                    (double) (h * (float) zDirection.getStepX());
            double e = 0.5 + (double) (0.4375F * (float) xDirection.getStepY()) +
                    (double) (h * (float) zDirection.getStepY());
            double i = 0.5 + (double) (0.4375F * (float) xDirection.getStepZ()) +
                    (double) (h * (float) zDirection.getStepZ());
            level.addParticle(new DustParticleOptions(particleVec.toVector3f(), 1.0F),
                    (double) pos.getX() + d,
                    (double) pos.getY() + e + offsetY,
                    (double) pos.getZ() + i,
                    0.0,
                    0.0,
                    0.0);
        }
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return direction.getAxis().isHorizontal() &&
                state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite())) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getSignal(level, pos, direction);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        this.updateNeighbors(level, pos, state, true);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!movedByPiston && !state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, movedByPiston);
            this.updateNeighbors(level, pos, state, true);
        }
    }

    protected void updateNeighbors(Level level, BlockPos pos, BlockState state, boolean poweredOnly) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!poweredOnly || state.getValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction))) {
                // direction seems to be inverted, but makes the crossing go wild
                BlockPos blockPos = pos.relative(direction);
                level.neighborChanged(blockPos, this, pos);
                level.updateNeighborsAtExceptFromFacing(blockPos, this, direction.getOpposite());
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }
}

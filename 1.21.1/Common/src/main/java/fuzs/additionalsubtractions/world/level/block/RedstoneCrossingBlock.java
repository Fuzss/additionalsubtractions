package fuzs.additionalsubtractions.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneCrossingBlock extends Block {
    public static final BooleanProperty POWERED_X = BooleanProperty.create("powered_x");
    public static final BooleanProperty POWERED_Z = BooleanProperty.create("powered_z");
    private static final VoxelShape SHAPE = Shapes.or(Block.box(5.0, 0.0, 0.0, 11.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 5.0, 16.0, 3.0, 11.0));

    public RedstoneCrossingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition()
                .any()
                .setValue(POWERED_X, Boolean.FALSE)
                .setValue(POWERED_Z, Boolean.FALSE));
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
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        state = state.setValue(POWERED_Z, level.hasSignal(pos.north(), Direction.NORTH) || level.hasSignal(pos.south(), Direction.SOUTH));
        state = state.setValue(POWERED_X, level.hasSignal(pos.east(), Direction.EAST) || level.hasSignal(pos.west(), Direction.WEST));

    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(POWER);
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return direction == (state.getValue(HANGING) ? Direction.DOWN : Direction.UP) ?
                state.getSignal(level, pos, direction) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED_X, POWERED_Z);
    }
}

package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class RedstoneCarvedPumpkinBlock extends CarvedPumpkinBlock {
    public static final MapCodec<RedstoneCarvedPumpkinBlock> CODEC = simpleCodec(RedstoneCarvedPumpkinBlock::new);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public RedstoneCarvedPumpkinBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(POWERED, Boolean.FALSE)
                .setValue(LIT, Boolean.FALSE));
    }

    @Override
    public MapCodec<? extends RedstoneCarvedPumpkinBlock> codec() {
        return CODEC;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.getBlock() != state.getBlock() && level instanceof ServerLevel serverLevel) {
            this.checkAndFlip(state, serverLevel, pos);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel) {
            this.checkAndFlip(state, serverLevel, pos);
        }
    }

    public void checkAndFlip(BlockState state, ServerLevel level, BlockPos pos) {
        boolean bl = this.hasNeighborSignal(state, level, pos);
        if (bl != state.getValue(POWERED)) {
            BlockState blockState = state;
            if (!state.getValue(POWERED)) {
                blockState = state.cycle(LIT);
            }

            level.setBlockAndUpdate(pos, blockState.setValue(POWERED, bl));
            level.updateNeighborsAt(pos, this);
        }
    }

    public boolean hasNeighborSignal(BlockState state, ServerLevel level, BlockPos pos) {
        Direction facingDirection = state.getValue(FACING);
        for (Direction direction : Direction.values()) {
            if (direction != facingDirection && level.hasSignal(pos.relative(direction), direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.getValue(FACING) == direction.getOpposite() && state.getValue(LIT) ? 15 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT, POWERED);
    }
}

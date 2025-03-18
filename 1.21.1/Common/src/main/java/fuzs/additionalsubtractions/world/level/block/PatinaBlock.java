package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.additionalsubtractions.world.entity.item.PatinaBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Mostly copied from {@link net.minecraft.world.level.block.CopperBulbBlock}.
 */
public class PatinaBlock extends FallingBlock {
    public static final MapCodec<PatinaBlock> CODEC = simpleCodec(PatinaBlock::new);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

    public PatinaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(INVERTED, Boolean.FALSE)
                .setValue(POWERED, Boolean.FALSE));
    }

    @Override
    public MapCodec<? extends PatinaBlock> codec() {
        return CODEC;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.isFree(state, level, pos)) {
            this.falling(PatinaBlockEntity.fall(level, pos, state));
        }
    }

    protected boolean isFree(BlockState state, ServerLevel level, BlockPos pos) {
        if (state.getValue(INVERTED)) {
            return isFree(level.getBlockState(pos.above())) && pos.getY() <= level.getMaxBuildHeight();
        } else {
            return isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight();
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (oldState.getBlock() != state.getBlock() && level instanceof ServerLevel serverLevel) {
            this.checkAndFlip(state, serverLevel, pos);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel) {
            this.checkAndFlip(state, serverLevel, pos);
        }
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    public void checkAndFlip(BlockState state, ServerLevel serverLevel, BlockPos pos) {
        boolean bl = serverLevel.hasNeighborSignal(pos);
        if (bl != state.getValue(POWERED)) {
            BlockState blockState = state;
            if (!state.getValue(POWERED)) {
                blockState = state.cycle(INVERTED);
            }

            serverLevel.setBlock(pos, blockState.setValue(POWERED, Boolean.valueOf(bl)), 3);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // NO-OP
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INVERTED, POWERED);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return 15;
    }
}

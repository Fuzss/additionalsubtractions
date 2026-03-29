package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.additionalsubtractions.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;

public class ModFireBlock extends FireBlock {
    public static final MapCodec<FireBlock> CODEC = simpleCodec(ModFireBlock::new);

    public ModFireBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<FireBlock> codec() {
        return CODEC;
    }

    @Override
    public int getBurnOdds(BlockState state) {
        // this is unused on NeoForge, but does not hurt to also have there
        return ((FireBlock) Blocks.FIRE).getBurnOdds(state);
    }

    @Override
    public int getIgniteOdds(BlockState state) {
        // this is unused on NeoForge, but does not hurt to also have there
        return ((FireBlock) Blocks.FIRE).getIgniteOdds(state);
    }

    @Override
    protected BlockState getStateWithAge(LevelAccessor level, BlockPos pos, int age) {
        BlockState blockState = super.getStateWithAge(level, pos, age);
        if (blockState.is(Blocks.FIRE)) {
            return copyBlockStateProperties(blockState, ModBlocks.COPPER_SULFATE_FIRE.value().defaultBlockState());
        } else {
            return blockState;
        }
    }

    public static <T extends Comparable<T>> BlockState copyBlockStateProperties(BlockState fromBlockState, BlockState toBlockState) {
        for (Map.Entry<Property<?>, Comparable<?>> entry : fromBlockState.getValues().entrySet()) {
            toBlockState = toBlockState.trySetValue((Property<T>) entry.getKey(), (T) entry.getValue());
        }
        return toBlockState;
    }
}

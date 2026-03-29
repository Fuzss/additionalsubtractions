package fuzs.additionalsubtractions.neoforge.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.additionalsubtractions.world.level.block.ModFireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NeoForgeFireBlock extends ModFireBlock {
    public static final MapCodec<FireBlock> CODEC = simpleCodec(NeoForgeFireBlock::new);

    public NeoForgeFireBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<FireBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isBurning(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}

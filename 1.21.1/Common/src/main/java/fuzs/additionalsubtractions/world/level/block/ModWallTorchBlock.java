package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Allows for using a flame particle wrapped in a holder, as NeoForge registers particles only after blocks.
 */
@Deprecated
public class ModWallTorchBlock extends WallTorchBlock {
    public static final MapCodec<? extends WallTorchBlock> CODEC = RecordCodecBuilder.<ModWallTorchBlock>mapCodec(
            instance -> instance.group(ModTorchBlock.PARTICLE_OPTIONS_FIELD.forGetter((ModWallTorchBlock block) -> block.flameParticle),
                    propertiesCodec()).apply(instance, ModWallTorchBlock::new));

    protected final Holder<SimpleParticleType> flameParticle;

    public ModWallTorchBlock(Holder<SimpleParticleType> flameParticle, Properties properties) {
        super(ParticleTypes.FLAME, properties);
        this.flameParticle = flameParticle;
    }

    @Override
    public MapCodec<WallTorchBlock> codec() {
        return (MapCodec<WallTorchBlock>) CODEC;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        double d = (double) pos.getX() + 0.5;
        double e = (double) pos.getY() + 0.7;
        double f = (double) pos.getZ() + 0.5;
        Direction direction2 = direction.getOpposite();
        level.addParticle(ParticleTypes.SMOKE,
                d + 0.27 * (double) direction2.getStepX(),
                e + 0.22,
                f + 0.27 * (double) direction2.getStepZ(),
                0.0,
                0.0,
                0.0);
        level.addParticle(this.flameParticle.value(),
                d + 0.27 * (double) direction2.getStepX(),
                e + 0.22,
                f + 0.27 * (double) direction2.getStepZ(),
                0.0,
                0.0,
                0.0);
    }
}

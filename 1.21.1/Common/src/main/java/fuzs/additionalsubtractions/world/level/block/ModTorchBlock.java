package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Allows for using a flame particle wrapped in a holder, as NeoForge registers particles only after blocks.
 */
@Deprecated
public class ModTorchBlock extends TorchBlock {
    protected static final MapCodec<Holder<SimpleParticleType>> PARTICLE_OPTIONS_FIELD = BuiltInRegistries.PARTICLE_TYPE.holderByNameCodec()
            .comapFlatMap((Holder<ParticleType<?>> particleType) -> particleType.value() instanceof SimpleParticleType ?
                            DataResult.success((Holder<SimpleParticleType>) (Holder<?>) particleType) :
                            DataResult.error(() -> "Not a SimpleParticleType: " + particleType),
                    (Holder<SimpleParticleType> simpleParticleType) -> (Holder<ParticleType<?>>) (Holder<?>) simpleParticleType)
            .fieldOf("particle_options");
    public static final MapCodec<ModTorchBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PARTICLE_OPTIONS_FIELD.forGetter((ModTorchBlock block) -> block.flameParticle),
            propertiesCodec()).apply(instance, ModTorchBlock::new));

    protected final Holder<SimpleParticleType> flameParticle;

    public ModTorchBlock(Holder<SimpleParticleType> flameParticle, Properties properties) {
        super(ParticleTypes.FLAME, properties);
        this.flameParticle = flameParticle;
    }

    @Override
    public MapCodec<? extends TorchBlock> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d = (double) pos.getX() + 0.5;
        double e = (double) pos.getY() + 0.7;
        double f = (double) pos.getZ() + 0.5;
        level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
        level.addParticle(this.flameParticle.value(), d, e, f, 0.0, 0.0, 0.0);
    }
}

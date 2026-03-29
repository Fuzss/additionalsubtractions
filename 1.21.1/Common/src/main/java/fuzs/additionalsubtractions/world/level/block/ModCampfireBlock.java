package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModCampfireBlock extends CampfireBlock {
    public static final MapCodec<? extends CampfireBlock> CODEC = RecordCodecBuilder.<ModCampfireBlock>mapCodec(instance -> instance.group(
            ModTorchBlock.PARTICLE_OPTIONS_FIELD.forGetter((ModCampfireBlock block) -> block.flameParticle),
            Codec.intRange(0, 1000).fieldOf("fire_damage").forGetter((ModCampfireBlock block) -> block.fireDamage),
            propertiesCodec()).apply(instance, ModCampfireBlock::new));

    protected final Holder<SimpleParticleType> flameParticle;
    protected final int fireDamage;

    public ModCampfireBlock(Holder<SimpleParticleType> flameParticle, int fireDamage, Properties properties) {
        super(false, fireDamage, properties);
        this.flameParticle = flameParticle;
        this.fireDamage = fireDamage;
    }

    @Override
    public MapCodec<CampfireBlock> codec() {
        return (MapCodec<CampfireBlock>) CODEC;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT) && random.nextInt(5) == 0) {
            for (int i = 0; i < random.nextInt(1) + 1; i++) {
                level.addParticle(this.flameParticle.value(),
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        random.nextFloat() / 2.0F,
                        5.0E-5,
                        random.nextFloat() / 2.0F);
            }
        }
    }
}

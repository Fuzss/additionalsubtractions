package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BrazierBlock extends Block {
    public static final MapCodec<? extends BrazierBlock> CODEC = RecordCodecBuilder.mapCodec((RecordCodecBuilder.Instance<BrazierBlock> instance) -> instance.group(
            ModTorchBlock.PARTICLE_OPTIONS_FIELD.forGetter((BrazierBlock block) -> block.flameParticle),
            Codec.intRange(0, 1000).fieldOf("fire_damage").forGetter((BrazierBlock block) -> block.fireDamage),
            propertiesCodec()).apply(instance, BrazierBlock::new));
    private static final VoxelShape INSIDE = box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(),
            Shapes.or(box(0.0, 0.0, 4.0, 16.0, 4.0, 12.0),
                    box(4.0, 0.0, 0.0, 12.0, 4.0, 16.0),
                    box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0),
                    INSIDE),
            BooleanOp.ONLY_FIRST);

    protected final Holder<SimpleParticleType> flameParticle;
    protected final int fireDamage;

    public BrazierBlock(Holder<SimpleParticleType> flameParticle, int fireDamage, Properties properties) {
        super(properties);
        this.flameParticle = flameParticle;
        this.fireDamage = fireDamage;
    }

    @Override
    protected MapCodec<? extends BrazierBlock> codec() {
        return CODEC;
    }

    protected boolean isEntityInsideContent(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < pos.getY() + this.getContentHeight(state) &&
                entity.getBoundingBox().maxY > pos.getY() + 0.25;
    }

    protected double getContentHeight(BlockState state) {
        return 1.0;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (this.isEntityInsideContent(state, pos, entity)) {
            entity.hurt(level.damageSources().campfire(), this.fireDamage);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0) {
            level.playLocalSound(pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    SoundEvents.CAMPFIRE_CRACKLE,
                    SoundSource.BLOCKS,
                    0.5F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.6F,
                    false);
        }

        if (random.nextInt(5) == 0) {
            for (int i = 0; i < random.nextInt(1) + 1; i++) {
                level.addParticle(this.flameParticle.value(),
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        (random.nextFloat() / 2.0F),
                        5.0E-5,
                        (random.nextFloat() / 2.0F));
            }
        }

        for (int i = 0; i < level.getRandom().nextInt(2) + 2; i++) {
            CampfireBlock.makeParticles(level, pos, false, false);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return INSIDE;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }
}

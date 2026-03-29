package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.puzzleslib.api.shape.v1.ShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SpikesBlock extends PistonHeadBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<PistonHeadBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.BLOCK.holderByNameCodec()
                    .fieldOf("spike_trap_block")
                    .forGetter((PistonHeadBlock block) -> ((SpikesBlock) block).spikeTrapBlock),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("spike_damage")
                    .forGetter((PistonHeadBlock block) -> ((SpikesBlock) block).spikeDamage),
            Codec.intRange(0, 255)
                    .fieldOf("dig_slowdown_amplifier")
                    .forGetter((PistonHeadBlock block) -> ((SpikesBlock) block).digSlowdownAmplifier),
            propertiesCodec()).apply(instance, SpikesBlock::new));
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 6.0, 15.0);
    static final Map<Direction, VoxelShape> SHAPES = ShapesHelper.rotate(SHAPE);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final Holder<Block> spikeTrapBlock;
    private final int spikeDamage;
    private final int digSlowdownAmplifier;

    public SpikesBlock(Holder<Block> spikeTrapBlock, int spikeDamage, int digSlowdownAmplifier, Properties properties) {
        super(properties);
        this.spikeTrapBlock = spikeTrapBlock;
        this.spikeDamage = spikeDamage;
        this.digSlowdownAmplifier = digSlowdownAmplifier;
        // TODO this should not have to include piston properties
        this.registerDefaultState(this.defaultBlockState()
                .setValue(WATERLOGGED, Boolean.FALSE)
                .setValue(FACING, Direction.UP));
    }

    @Override
    protected MapCodec<PistonHeadBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 40, this.digSlowdownAmplifier));
            entity.makeStuckInBlock(state, new Vec3(0.75, 1.0, 0.75));
            // TODO proper damage source
            entity.hurt(level.damageSources().campfire(), this.spikeDamage);
        }

        super.entityInside(state, level, pos, entity);
    }

    @Override
    protected boolean isFittingBase(BlockState extendedState, BlockState baseState) {
        return baseState.is(this.spikeTrapBlock) && baseState.getValue(SpikeTrapBlock.EXTENDED) != 0 &&
                baseState.getValue(FACING) == extendedState.getValue(FACING);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(this.spikeTrapBlock.value());
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // TODO this should not include piston properties
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
//        builder.add(WATERLOGGED, FACING);
    }
}

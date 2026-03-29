package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.ticks.TickPriority;

public class SpikeTrapBlock extends DirectionalBlock {
    public static final MapCodec<SpikeTrapBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.BLOCK.holderByNameCodec()
                    .fieldOf("spike_trap_block")
                    .forGetter((SpikeTrapBlock block) -> block.spikesBlock),
            Codec.floatRange(0.0F, 255.0F)
                    .fieldOf("pushing_strength")
                    .forGetter((SpikeTrapBlock block) -> block.pushingStrength),
            propertiesCodec()).apply(instance, SpikeTrapBlock::new));
    public static final IntegerProperty EXTENDED = IntegerProperty.create("extended", 0, 5);

    private final Holder<Block> spikesBlock;
    private final float pushingStrength;

    public SpikeTrapBlock(Holder<Block> spikesBlock, float pushingStrength, Properties properties) {
        super(properties);
        this.spikesBlock = spikesBlock;
        this.pushingStrength = pushingStrength;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(EXTENDED, 0));
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            this.checkIfExtend(level, pos, state);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (!level.isClientSide) {
            this.checkIfExtend(level, pos, state);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(state.getBlock()) && !level.isClientSide) {
            this.checkIfExtend(level, pos, state);
        }
    }

    private void checkIfExtend(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        boolean bl = this.getNeighborSignal(level, pos, direction);
        if (bl && state.getValue(EXTENDED) < 5) {
            this.triggerEvent(state, level, pos, true);
        } else if (!bl && state.getValue(EXTENDED) != 0) {
            this.triggerEvent(state, level, pos, false);
        }
    }

    protected void triggerEvent(BlockState state, Level level, BlockPos pos, boolean isExtending) {
        Direction direction = state.getValue(FACING);
        if (!level.isClientSide) {
            boolean bl = this.getNeighborSignal(level, pos, direction);
            if (bl && !isExtending) {
                level.setBlock(pos, state.setValue(EXTENDED, 5), 2);
                return;
            }

            if (!bl && isExtending) {
                return;
            }
        }

        BlockPos neighborPos = pos.relative(direction);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (isExtending) {
            if (isPushable(neighborState, level, neighborPos, direction)) {
                int extended = state.getValue(EXTENDED) + 1;

                if (extended == 5) {
                    this.destroyBlock(level, neighborPos);
                    BlockState blockstate = Block.updateFromNeighbourShapes(this.spikesBlock.value().defaultBlockState().setValue(FACING, direction), level, neighborPos);
                    if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED) &&
                            blockstate.getValue(BlockStateProperties.WATERLOGGED)) {
                        blockstate = blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE);
                    }

                    level.setBlock(neighborPos, blockstate, 2);
                    level.neighborChanged(neighborPos, blockstate.getBlock(), neighborPos);

                    int intVar = (int) Math.min(Math.max(neighborPos.asLong(), Integer.MIN_VALUE),
                            Integer.MAX_VALUE);
                    level.destroyBlockProgress(intVar, neighborPos, -1);
                } else {
                    int intVar = (int) Math.min(Math.max(neighborPos.asLong(), Integer.MIN_VALUE),
                            Integer.MAX_VALUE);
                    level.destroyBlockProgress(intVar, neighborPos, 10 / 5 * extended);
                    level.scheduleTick(pos, this, 20);
                }

                BlockState blockState = state.setValue(EXTENDED, extended);
                level.setBlock(pos, blockState, 2);
                level.playSound(null,
                        pos,
                        SoundEvents.PISTON_EXTEND,
                        SoundSource.BLOCKS,
                        0.5F,
                        level.random.nextFloat() * 0.25F + 0.6F);
                level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(blockState));
            }
        } else {
            if (neighborState.is(ModTags.SPIKES_BLOCK_TAG) && neighborState.getValue(FACING) == direction) {
                level.removeBlock(neighborPos, false);
                level.playSound(null,
                        pos,
                        ModRegistry.SPIKE_TRAP_RETRACT_SOUND_EVENT.value(),
                        SoundSource.BLOCKS,
                        0.5F,
                        level.random.nextFloat() * 0.15F + 0.4F);
                level.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(neighborState));
            }
            level.setBlock(pos, state.setValue(EXTENDED, 0), 2);
        }
    }

    protected boolean getNeighborSignal(Level level, BlockPos blockPos, Direction direction) {
        for (Direction currentDirection : Direction.values()) {
            if (currentDirection != direction &&
                    level.hasSignal(blockPos.relative(currentDirection), currentDirection)) {
                return true;
            }
        }

        return level.hasSignal(blockPos, Direction.DOWN);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        this.checkIfExtend(level, pos, state);
    }

    protected void destroyBlock(Level level, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(blockPos) : null;
        dropResources(blockState, level, blockPos, blockEntity);
        // TODO replace with Neo onDestroyedByPushReaction
        level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 18);
        level.gameEvent(GameEvent.BLOCK_DESTROY, blockPos, GameEvent.Context.of(blockState));
        if (!blockState.is(BlockTags.FIRE)) {
//            level.addDestroyBlockEffect(blockPos, blockState);
            level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
        }
    }

    public static boolean isPushable(BlockState blockState, Level level, BlockPos pos, Direction direction) {
        return PistonBaseBlock.isPushable(blockState, level, pos, direction, true, direction);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(EXTENDED) * 3;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, EXTENDED);
    }
}

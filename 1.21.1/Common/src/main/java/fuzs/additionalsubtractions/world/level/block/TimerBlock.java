package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.world.level.block.entity.TimerBlockEntity;
import fuzs.puzzleslib.api.block.v1.entity.TickingEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;

public class TimerBlock extends DiodeBlock implements TickingEntityBlock<TimerBlockEntity> {
    public static final MapCodec<TimerBlock> CODEC = simpleCodec(TimerBlock::new);
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;

    public TimerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, Boolean.FALSE)
                .setValue(LOCKED, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }

    @Override
    protected int getDelay(BlockState state) {
        return 2;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!this.isLocked(level, pos, state)) {
            boolean bl = state.getValue(POWERED);
            boolean bl2 = this.shouldTurnOn(level, pos, state);
            if (bl && !bl2) {
                level.setBlock(pos, state.setValue(POWERED, Boolean.FALSE).setValue(LOCKED, Boolean.FALSE), 2);
                // schedule timer delay
                this.scheduleTimedTick(level, pos);
            } else if (!bl) {
                boolean bl3 = state.getValue(LOCKED);
                level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE).setValue(LOCKED, bl2), 2);
                if (!bl2) {
                    // play sound for timer pulse
                    if (!bl3) {
                        level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, 0.5F);
                    }
                    level.scheduleTick(pos, this, this.getDelay(state), TickPriority.VERY_HIGH);
                }
            }
        }
    }

    protected void scheduleTimedTick(LevelAccessor level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof TimerBlockEntity blockEntity) {
            level.scheduleTick(pos, this, blockEntity.getDelay(), TickPriority.VERY_HIGH);
        }
    }

    @Override
    protected void checkTickOnNeighbor(Level level, BlockPos pos, BlockState state) {
        if (!this.isLocked(level, pos, state)) {
            boolean bl = state.getValue(POWERED);
            boolean bl2 = this.shouldTurnOn(level, pos, state);
            if (bl != bl2 && !level.getBlockTicks().willTickThisTick(pos, this)) {
                TickPriority tickPriority = TickPriority.HIGH;
                if (this.shouldPrioritize(level, pos, state)) {
                    tickPriority = TickPriority.EXTREMELY_HIGH;
                } else if (bl) {
                    tickPriority = TickPriority.VERY_HIGH;
                }

                // clear scheduled ticks if present, otherwise copied from super
                this.clearScheduledTicks(level, pos);
                level.scheduleTick(pos, this, this.getDelay(state), tickPriority);
            }
        }
    }

    @Override
    public boolean shouldPrioritize(BlockGetter level, BlockPos pos, BlockState state) {
        if (super.shouldPrioritize(level, pos, state)) {
            return true;
        } else {
            // get high priority so that another timer locking this one is still powered when this block's tick runs,
            // as both the other timer firing its unpowering tick and this one ticking happens in the same tick (2 ticks from now)
            Direction direction = state.getValue(FACING);
            BlockState blockstate = level.getBlockState(pos.relative(direction));
            return isDiode(blockstate) && blockstate.getValue(FACING).getOpposite() != direction;
        }
    }

    public void clearScheduledTicks(Level level, BlockPos pos) {
        if (level.getBlockTicks().hasScheduledTick(pos, this)) {
            ((ServerLevel) level).getBlockTicks().clearArea(new BoundingBox(pos));
        }
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter level, BlockPos blockPos, Direction direction) {
        if (!blockState.getValue(POWERED) || blockState.getValue(LOCKED)) {
            return 0;
        } else if (direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL &&
                blockState.getValue(FACING).getOpposite() != direction) {
            return this.getOutputSignal(level, blockPos, blockState);
        } else {
            return 0;
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!state.getValue(POWERED)) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            double y = pos.getY() + 0.9 + (random.nextDouble() - 0.5) * 0.2;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            level.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (this.shouldTurnOn(level, pos, state)) {
            level.scheduleTick(pos, this, 1);
        } else {
            this.scheduleTimedTick(level, pos);
        }
    }

    @Override
    public BlockEntityType<? extends TimerBlockEntity> getBlockEntityType() {
        return ModBlocks.TIMER_BLOCK_ENTITY_TYPE.value();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, LOCKED);
    }

    @Override
    protected void updateNeighborsInFront(Level level, BlockPos blockPos, BlockState blockState) {
        Direction facingDirection = blockState.getValue(FACING);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != facingDirection) {
                BlockPos blockpos = blockPos.relative(direction);
                level.neighborChanged(blockpos, this, blockPos);
                level.updateNeighborsAtExceptFromFacing(blockpos, this, direction.getOpposite());
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canSurviveOn(level, neighborPos, neighborState) ?
                Blocks.AIR.defaultBlockState() :
                super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        this.clearScheduledTicks(level, pos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.mayBuild()) {
            if (!level.isClientSide) {
                if (level.getBlockEntity(pos) instanceof TimerBlockEntity blockEntity) {
                    player.openMenu(blockEntity);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useWithoutItem(state, level, pos, player, hitResult);
        }
    }
}

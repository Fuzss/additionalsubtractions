package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * Adapted from {@link net.minecraft.world.level.block.LeverBlock}.
 */
public class BookshelfSwitchBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<BookshelfSwitchBlock> CODEC = simpleCodec(BookshelfSwitchBlock::new);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public BookshelfSwitchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, Boolean.FALSE));
    }

    @Override
    public MapCodec<? extends BookshelfSwitchBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            BlockState blockState = state.cycle(POWERED);
            if (blockState.getValue(POWERED)) {
                RedStoneOreBlock.spawnParticles(level, pos);
            }

            return InteractionResult.SUCCESS;
        } else {
            this.pull(state, level, pos, null);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (explosion.canTriggerBlocks()) {
            this.pull(state, level, pos, null);
        }

        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }

    public void pull(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        state = state.cycle(POWERED);
        level.setBlock(pos, state, 3);
        playSound(player, level, pos, state);
        level.gameEvent(player, state.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
    }

    protected static void playSound(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState state) {
        float f = state.getValue(POWERED) ? 0.6F : 0.5F;
        level.playSound(player, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED) && random.nextFloat() < 0.25F) {
            RedStoneOreBlock.spawnParticles(level, pos);
        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return direction.getAxis().isHorizontal() && state.getValue(POWERED) &&
                state.getValue(FACING).getOpposite() != direction ? Redstone.SIGNAL_MAX : Redstone.SIGNAL_NONE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }
}

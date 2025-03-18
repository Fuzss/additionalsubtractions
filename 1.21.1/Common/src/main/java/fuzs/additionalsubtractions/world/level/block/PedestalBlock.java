package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.world.level.block.entity.PedestalBlockEntity;
import fuzs.puzzleslib.api.block.v1.entity.TickingEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, TickingEntityBlock<PedestalBlockEntity> {
    public static final MapCodec<PedestalBlock> CODEC = simpleCodec(PedestalBlock::new);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TOP_SHAPE = Block.box(1.0, 13.0, 1.0, 15.0, 16.0, 15.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BOTTOM_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 3.0, 15.0);
    private static final VoxelShape SHAPE = Shapes.or(TOP_SHAPE, MIDDLE_SHAPE, BOTTOM_SHAPE);

    public PedestalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends PedestalBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntityType<PedestalBlockEntity> getBlockEntityType() {
        return ModBlocks.PEDESTAL_BLOCK_ENTITY.value();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
            if (stack.isEmpty() || !blockEntity.getItem(0).isEmpty()) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else {
                if (!level.isClientSide) {
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    blockEntity.setItem(0, stack.consumeAndReturn(1, player));
                    this.playInteractionSound(level, pos, player);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        } else {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
            if (blockEntity.getItem(0).isEmpty()) {
                return InteractionResult.CONSUME;
            } else {
                if (!level.isClientSide) {
                    ItemStack itemStack = blockEntity.removeItem(0, 1);
                    this.playInteractionSound(level, pos, player);
                    if (!player.getInventory().add(itemStack)) {
                        player.drop(itemStack, false);
                    }
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private void playInteractionSound(Level level, BlockPos pos, Player player) {
        level.playSound(null,
                pos,
                SoundEvents.ITEM_PICKUP,
                SoundSource.BLOCKS,
                0.2F,
                ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(bl));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity &&
                !blockEntity.getItem(0).isEmpty() ? 15 : 0;
    }
}

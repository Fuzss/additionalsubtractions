package dqu.additionaladditions.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;

public class RopeBlock extends Block {
    public static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty UP = BlockStateProperties.UP;

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition()
                .any()
                .setValue(NORTH, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE)
                .setValue(UP, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        if (this.isFaceSturdy(level, blockPos, Direction.UP, SupportType.CENTER)) {
            return true;
        } else if (this.isFaceSturdy(level, blockPos, Direction.NORTH)) {
            return true;
        } else if (this.isFaceSturdy(level, blockPos, Direction.EAST)) {
            return true;
        } else if (this.isFaceSturdy(level, blockPos, Direction.SOUTH)) {
            return true;
        } else if (this.isFaceSturdy(level, blockPos, Direction.WEST)) {
            return true;
        } else {
            return super.canSurvive(blockState, level, blockPos);
        }
    }

    private boolean isFaceSturdy(LevelReader level, BlockPos blockPos, Direction direction) {
        return this.isFaceSturdy(level, blockPos, direction, SupportType.FULL);
    }

    private boolean isFaceSturdy(LevelReader level, BlockPos blockPos, Direction direction, SupportType supportType) {
        return level.getBlockState(blockPos.relative(direction))
                .isFaceSturdy(level, blockPos.relative(direction), direction.getOpposite(), supportType);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos blockPos, BlockState oldState, boolean notify) {
        if (!level.isClientSide()) {
            level.getBlockTicks().schedule(ScheduledTick.probe(this, blockPos));
        }
    }

    /*
     * If there is a rope above, don't connect to the side blocks
     * If there is no rope above, but it were earlier, break
     * If there is no rope above, and it wasn't here earlier, try connecting to nearby solid blocks
     * And if none of the nearby blocks are solid, break
     */
    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        BlockPos up = pos.relative(Direction.UP);
        BlockState bup = serverLevel.getBlockState(up);

        boolean n = false;
        boolean e = false;
        boolean s = false;
        boolean w = false;
        boolean u = blockState.getValue(UP); // We need the old up value too

        if (bup.is(this)) {
            u = true;
        } else if (u) {
            u = false;
        } else {
            BlockPos npos = pos.relative(Direction.NORTH);
            BlockPos epos = pos.relative(Direction.EAST);
            BlockPos spos = pos.relative(Direction.SOUTH);
            BlockPos wpos = pos.relative(Direction.WEST);

            BlockState north = serverLevel.getBlockState(npos);
            BlockState east = serverLevel.getBlockState(epos);
            BlockState south = serverLevel.getBlockState(spos);
            BlockState west = serverLevel.getBlockState(wpos);

            if (north.isFaceSturdy(serverLevel, npos, Direction.SOUTH)) n = true;
            if (east.isFaceSturdy(serverLevel, epos, Direction.WEST)) e = true;
            if (south.isFaceSturdy(serverLevel, spos, Direction.NORTH)) s = true;
            if (west.isFaceSturdy(serverLevel, wpos, Direction.EAST)) w = true;
        }

        if (!bup.is(this) && (!n && !e && !s && !w) && !bup.isFaceSturdy(serverLevel, up, Direction.DOWN) && !u) {
            serverLevel.destroyBlock(pos, true);
        } else {
            serverLevel.setBlockAndUpdate(pos,
                    blockState.setValue(NORTH, n)
                            .setValue(EAST, e)
                            .setValue(SOUTH, s)
                            .setValue(WEST, w)
                            .setValue(UP, u));
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClientSide()) {
            world.getBlockTicks().schedule(ScheduledTick.probe(this, pos));
        }
        return state;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!itemStack.is(this.asItem())) {
            return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, hitResult);
        } else {
            BlockPos blockPosBelow = blockPos.below();
            BlockState blockStateBelow = level.getBlockState(blockPosBelow);
            if (blockStateBelow.is(this)) {
                return this.useItemOn(itemStack,
                        blockStateBelow,
                        level,
                        blockPosBelow,
                        player,
                        interactionHand,
                        hitResult);
            } else if (!level.isOutsideBuildHeight(blockPosBelow.getY())) {
                InteractionResult interactionResult = this.asItem()
                        .useOn(new UseOnContext(level,
                                player,
                                interactionHand,
                                itemStack,
                                hitResult.withDirection(Direction.DOWN)));
                if (interactionResult.consumesAction()) {
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                } else {
                    return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, hitResult);
                }
            } else {
                if (player instanceof ServerPlayer serverPlayer && blockPosBelow.getY() >= level.getMaxBuildHeight()) {
                    serverPlayer.sendSystemMessage(Component.translatable("build.tooHigh",
                            level.getMaxBuildHeight() - 1).withStyle(ChatFormatting.RED), true);
                }
                return ItemInteractionResult.FAIL;
            }
        }
    }
}

package fuzs.additionalsubtractions.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Adapted from {@link net.minecraft.world.item.ScaffoldingBlockItem}.
 */
public class RopeItem extends BlockItem {

    public RopeItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {

        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);

        if (!blockState.is(this.getBlock())) {

            return super.updatePlacementContext(context);
        } else {

            Direction direction;
            if (context.isSecondaryUseActive()) {
                direction = context.isInside() ? context.getClickedFace().getOpposite() : context.getClickedFace();
            } else {
                direction = Direction.DOWN;
            }
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable().move(direction);

            while (true) {

                if (!level.isClientSide && !level.isInWorldBounds(mutableBlockPos)) {
                    Player player = context.getPlayer();
                    int minBuildHeight = level.getMinBuildHeight();
                    if (player instanceof ServerPlayer serverPlayer && mutableBlockPos.getY() <= minBuildHeight) {
                        serverPlayer.sendSystemMessage(Component.translatable("build.tooHigh", minBuildHeight + 1)
                                .withStyle(ChatFormatting.RED), true);
                    }
                    break;
                }

                blockState = level.getBlockState(mutableBlockPos);
                if (!blockState.is(this.getBlock())) {
                    if (blockState.canBeReplaced(context)) {
                        return BlockPlaceContext.at(context, mutableBlockPos, direction);
                    }
                    break;
                }

                mutableBlockPos.move(direction);
            }

            return null;
        }
    }
}

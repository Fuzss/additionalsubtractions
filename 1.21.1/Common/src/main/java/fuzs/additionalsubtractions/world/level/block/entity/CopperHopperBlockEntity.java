package fuzs.additionalsubtractions.world.level.block.entity;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.puzzleslib.api.block.v1.entity.TickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CopperHopperBlockEntity extends HopperBlockEntity implements TickingBlockEntity {

    public CopperHopperBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlocks.COPPER_HOPPER_BLOCK_ENTITY_TYPE.value();
    }

    @Override
    public void serverTick() {
        pushItemsTick(this.getLevel(), this.getBlockPos(), this.getBlockState(), this);
    }

    public static void pushItemsTick(Level level, BlockPos pos, BlockState state, HopperBlockEntity blockEntity) {
        blockEntity.cooldownTime--;
        blockEntity.tickedGameTime = level.getGameTime();
        if (!blockEntity.isOnCooldown()) {
            blockEntity.setCooldown(0);
            tryMoveItems(level, pos, state, blockEntity, () -> suckInItemsFromContainer(level, blockEntity));
        }
    }

    /**
     * Copied from {@link #suckInItems(Level, Hopper)}, but does not search for
     * {@link net.minecraft.world.entity.item.ItemEntity ItemEntitys} above.
     */
    public static boolean suckInItemsFromContainer(Level level, Hopper hopper) {
        BlockPos blockPos = BlockPos.containing(hopper.getLevelX(), hopper.getLevelY() + 1.0, hopper.getLevelZ());
        BlockState blockState = level.getBlockState(blockPos);
        Container container = getSourceContainer(level, hopper, blockPos, blockState);
        if (container != null) {
            Direction direction = Direction.DOWN;

            for (int i : getSlots(container, direction)) {
                if (tryTakeInItemFromSlot(hopper, container, i, direction)) {
                    return true;
                }
            }

            return false;
        } else {

            return false;
        }
    }
}

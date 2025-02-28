package fuzs.additionalsubtractions.world.item;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.apache.commons.lang3.function.Consumers;

public class WrenchItem extends Item {

    public WrenchItem(Properties properties) {
        super(properties);
    }

    private boolean tryPlacing(BlockPos blockPos, BlockState blockState, Level level, ItemStack itemStack, Runnable hurtAndBreakItem) {
        if (blockState.canSurvive(level, blockPos) && blockState.getBlock().defaultDestroyTime() >= 0) {
            level.setBlockAndUpdate(blockPos, blockState);

            if (level.isClientSide()) {
                return true;
            }

            hurtAndBreakItem.run();
            level.playSound(null, blockPos, SoundEvents.SPYGLASS_USE, SoundSource.AMBIENT, 2.0F, 1.0F);

            return true;
        }
        return false;
    }

    private InteractionResult rotate(BlockPos pos, BlockState blockState, Level level, ItemStack itemStack, Runnable hurtAndBreakItem) {
        // TODO sort out new state at once, only one call to tryPlacing, only call when state has actually changed
        if (level.isClientSide()) return InteractionResult.PASS;
        if (blockState.getBlock() instanceof ChestBlock || blockState.getBlock() instanceof BedBlock) {
            return InteractionResult.PASS;
        }

        if (blockState.hasProperty(BlockStateProperties.FACING)) {
            if (this.tryPlacing(pos,
                    blockState.cycle(BlockStateProperties.FACING),
                    level,
                    itemStack,
                    hurtAndBreakItem)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.hasProperty(BlockStateProperties.FACING_HOPPER)) {
            BlockState newstate = blockState.cycle(BlockStateProperties.FACING_HOPPER);
            if (this.tryPlacing(pos, newstate, level, itemStack, hurtAndBreakItem)) {
                if (FabricLoader.getInstance().isModLoaded("lithium") && !level.isClientSide()) {
                    /*
                     * Lithium mod caches hopper's output and input inventories
                     * Which causes an issue where the hopper keeps transferring to the old location
                     * This replaces the block entity, which fixes that.
                     */
                    HopperBlockEntity hopperBlockEntity = (HopperBlockEntity) level.getBlockEntity(pos);
                    CompoundTag nbt = hopperBlockEntity.saveWithoutMetadata(level.registryAccess());
                    level.removeBlockEntity(pos);
                    HopperBlockEntity blockEntity = new HopperBlockEntity(pos, newstate);
                    blockEntity.loadWithComponents(nbt, level.registryAccess());
                    level.setBlockEntity(blockEntity);
                }

                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            if (this.tryPlacing(pos,
                    blockState.cycle(BlockStateProperties.HORIZONTAL_FACING),
                    level,
                    itemStack,
                    hurtAndBreakItem)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.hasProperty(BlockStateProperties.AXIS)) {
            if (this.tryPlacing(pos, blockState.cycle(BlockStateProperties.AXIS), level, itemStack, hurtAndBreakItem)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
            if (this.tryPlacing(pos,
                    blockState.cycle(BlockStateProperties.HORIZONTAL_AXIS),
                    level,
                    itemStack,
                    hurtAndBreakItem)) {
                return InteractionResult.SUCCESS;
            }
        }

        if (blockState.getBlock() instanceof SlabBlock) {
            BlockState newState = blockState;
            if (blockState.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.DOUBLE)) {
                return InteractionResult.PASS;
            }
            if (blockState.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.BOTTOM)) {
                newState = blockState.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP);
            }
            if (blockState.getValue(BlockStateProperties.SLAB_TYPE).equals(SlabType.TOP)) {
                newState = blockState.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM);
            }

            if (this.tryPlacing(pos, newState, level, itemStack, hurtAndBreakItem)) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return this.rotate(context.getClickedPos(),
                context.getLevel().getBlockState(context.getClickedPos()),
                context.getLevel(),
                context.getItemInHand(),
                () -> {
                    if (context.getPlayer() != null) {
                        context.getItemInHand()
                                .hurtAndBreak(1, context.getPlayer(), LivingEntity.getSlotForHand(context.getHand()));
                    }
                });
    }

    public void dispenserUse(Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        this.rotate(blockPos, blockState, level, itemStack, () -> {
            if (level instanceof ServerLevel serverLevel) {
                itemStack.hurtAndBreak(1, serverLevel, null, Consumers.nop());
            }
        });
    }
}
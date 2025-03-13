package fuzs.additionalsubtractions.world.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class WateringCanItem extends Item {

    public WateringCanItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos posBelow = blockPos.relative(Direction.DOWN);
            BlockState stateBelow = level.getBlockState(posBelow);

            if (itemInHand.getDamageValue() > 0 || player.isCreative()) {
                if (blockState.getBlock() instanceof BonemealableBlock bonemealableBlock &&
                        !(blockState.getBlock() instanceof GrassBlock)) {
                    player.playSound(SoundEvents.BONE_MEAL_USE, 1.0F, 1.5F);
                    if (level.isClientSide()) return InteractionResultHolder.success(itemInHand);
                    if (bonemealableBlock.isBonemealSuccess(level, level.random, blockPos, blockState)) {
                        if (level.random.nextFloat() < 0.25) {
                            bonemealableBlock.performBonemeal((ServerLevel) level, level.random, blockPos, blockState);
                        }
                    }

                    if (stateBelow.getBlock() instanceof FarmBlock) {
                        level.setBlockAndUpdate(posBelow,
                                stateBelow.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE));
                    }

                    itemInHand.setDamageValue(itemInHand.getDamageValue() - 10);
                    return InteractionResultHolder.success(itemInHand);
                }

                if (blockState.getBlock() instanceof FarmBlock) {
                    if (level.isClientSide()) return InteractionResultHolder.success(itemInHand);
                    level.setBlockAndUpdate(blockPos,
                            blockState.setValue(BlockStateProperties.MOISTURE, FarmBlock.MAX_MOISTURE));
                    itemInHand.setDamageValue(itemInHand.getDamageValue() - 10);
                    return InteractionResultHolder.success(itemInHand);
                }
            }

            if (blockState.getBlock() instanceof BucketPickup fluid && blockState.getBlock() == Blocks.WATER) {
                if (itemInHand.getDamageValue() == 100) return InteractionResultHolder.fail(itemInHand);
                fluid.getPickupSound().ifPresent((sound) -> player.playSound(sound, 1.0F, 1.0F));
                if (!level.isClientSide()) {
                    itemInHand.setDamageValue(100);
                    fluid.pickupBlock(player, level, blockPos, blockState);
                    player.swing(interactionHand);
                }
                return InteractionResultHolder.success(itemInHand);
            }

            return InteractionResultHolder.fail(itemInHand);
        } else {
            return InteractionResultHolder.fail(itemInHand);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return FastColor.ARGB32.color(65, 135, 235);
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        return Math.min(Math.round(13.0F * itemStack.getDamageValue() / 100.0F), 13);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, tooltipComponents, tooltipFlag);
        int waterLevel = (int) (itemStack.getDamageValue() * 0.1);
        tooltipComponents.add(Component.translatable("block.minecraft.water")
                .append(": " + waterLevel + " / 10")
                .withStyle(ChatFormatting.AQUA));
    }
}

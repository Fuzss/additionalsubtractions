package fuzs.additionalsubtractions.world.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;

public class CrossbowWithSpyglassItem extends CrossbowItem {

    public CrossbowWithSpyglassItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (isCharged(itemInHand)) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemInHand);
        } else {
            return super.use(level, player, usedHand);
        }
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeCharged) {
        ChargedProjectiles chargedProjectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            this.performShooting(level,
                    livingEntity,
                    livingEntity.getUsedItemHand(),
                    itemStack,
                    getShootingPower(chargedProjectiles),
                    1.0F,
                    null);
        } else {
            super.releaseUsing(itemStack, level, livingEntity, timeCharged);
        }
    }

    private static float getShootingPower(ChargedProjectiles projectile) {
        return projectile.contains(Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!isCharged(stack)) {
            super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return isCharged(stack) ? 72000 : super.getUseDuration(stack, entity);
    }

    public boolean isScoping(Level level, LivingEntity livingEntity, ItemStack itemStack, int remainingUseDuration) {
        return isCharged(itemStack) && remainingUseDuration > 0 &&
                this.getUseDuration(itemStack, livingEntity) - remainingUseDuration >= 5;
    }
}

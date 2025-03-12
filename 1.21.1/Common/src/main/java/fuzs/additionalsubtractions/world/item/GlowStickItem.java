package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.world.entity.projectile.GlowStick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class GlowStickItem extends BlockItem {

    public GlowStickItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {

            int chargeAmount = this.getUseDuration(stack, livingEntity) - timeCharged;
            float powerForTime = BowItem.getPowerForTime(chargeAmount);
            if (powerForTime >= 0.1) {
                level.playSound(null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.SNOWBALL_THROW,
                        SoundSource.NEUTRAL,
                        0.5F,
                        0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

                if (level instanceof ServerLevel serverLevel) {
                    GlowStick glowStick = new GlowStick(serverLevel, player);
                    glowStick.setItem(stack);
                    glowStick.shootFromRotation(player,
                            player.getXRot(),
                            player.getYRot(),
                            0.0F,
                            1.5F + powerForTime,
                            0.0F);
                    serverLevel.addFreshEntity(glowStick);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
                stack.consume(1, player);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
    }
}

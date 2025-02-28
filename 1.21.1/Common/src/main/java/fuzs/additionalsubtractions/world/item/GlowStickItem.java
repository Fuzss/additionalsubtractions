package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.world.entity.projectile.GlowStickEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GlowStickItem extends Item {

    public GlowStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        if (!level.isClientSide()) {
            GlowStickEntity glowStickEntity = new GlowStickEntity(level, player);
            glowStickEntity.setItem(itemInHand);
            glowStickEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
            level.addFreshEntity(glowStickEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.isCreative()) {
            itemInHand.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }
}

package dqu.additionaladditions.item;

import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DepthMeterItem extends Item {

    public DepthMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways")) {
            ItemStack itemStack = player.getItemInHand(interactionHand);
            player.displayClientMessage(this.getElevationComponent(player.getBlockY()), true);
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        } else {
            return super.use(level, player, interactionHand);
        }
    }

    public Component getElevationComponent(int blockY) {
        return Component.translatable(this.getDescriptionId() + ".elevation", blockY);
    }
}

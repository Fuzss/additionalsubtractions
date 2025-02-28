package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DepthMeterItem extends Item {

    public DepthMeterItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemInHand = player.getItemInHand(interactionHand);
        player.displayClientMessage(getElevationComponent(player.getBlockY()), true);
        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            player.displayClientMessage(getElevationComponent(context.getClickedPos().getY()), true);
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        } else {
            return super.useOn(context);
        }
    }

    public static Component getElevationComponent(int blockY) {
        return Component.translatable(ModItems.DEPTH_METER.value().getDescriptionId() + ".elevation", blockY);
    }
}

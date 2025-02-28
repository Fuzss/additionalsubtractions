package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;

import java.util.List;
import java.util.Optional;

public class NewPocketJukeboxItem extends BundleItem {

    public NewPocketJukeboxItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (slot.getItem().is(ModRegistry.MUSIC_DISCS_ITEM_TAG)) {
            return super.overrideStackedOnOther(stack, slot, action, player);
        } else {
            return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.is(ModRegistry.MUSIC_DISCS_ITEM_TAG)) {
            return super.overrideOtherStackedOnMe(stack, other, slot, action, player, access);
        } else {
            return false;
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BundleContents bundleContents = stack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        if (bundleContents.isEmpty()) {
            tooltipComponents.add(getDescriptionComponent());
        } else {
            for (ItemStack itemStack : bundleContents.items()) {
                tooltipComponents.add(getStyledHoverName(itemStack));
            }
        }
    }

    public static Component getDescriptionComponent() {
        return Component.translatable(ModItems.POCKET_JUKEBOX.value().getDescriptionId() + ".description");
    }

    @Deprecated(forRemoval = true)
    public static Component getStyledHoverName(ItemStack itemStack) {
        MutableComponent mutableComponent = Component.empty()
                .append(itemStack.getHoverName())
                .withStyle(itemStack.getRarity().color());
        if (itemStack.has(DataComponents.CUSTOM_NAME)) {
            mutableComponent.withStyle(ChatFormatting.ITALIC);
        }

        return mutableComponent;
    }
}

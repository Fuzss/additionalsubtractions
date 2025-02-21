package dqu.additionaladditions.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public final class SmithingTemplateItemHelper {

    private SmithingTemplateItemHelper() {
        // NO-OP
    }

    public static SmithingTemplateItem createUpgradeTemplate(ResourceLocation resourceLocation) {
        return createUpgradeTemplate(resourceLocation, SmithingTemplateItem.createNetheriteUpgradeMaterialList());
    }

    public static SmithingTemplateItem createUpgradeTemplate(ResourceLocation resourceLocation, List<ResourceLocation> additionalSlotEmptyIcons) {
        return createUpgradeTemplate(resourceLocation,
                SmithingTemplateItem.createNetheriteUpgradeIconList(),
                additionalSlotEmptyIcons);
    }

    public static SmithingTemplateItem createUpgradeTemplate(ResourceLocation resourceLocation, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons) {
        return new SmithingTemplateItem(getAppliesToComponent(resourceLocation),
                getIngredientsComponent(resourceLocation),
                getUpgradeDescriptionComponent(resourceLocation),
                getBaseSlotDescriptionComponent(resourceLocation),
                getAdditionsSlotDescriptionComponent(resourceLocation),
                baseSlotEmptyIcons,
                additionalSlotEmptyIcons);
    }

    static Component getAppliesToComponent(ResourceLocation resourceLocation) {
        return Component.translatable(Util.makeDescriptionId("item",
                        resourceLocation.withPath((String s) -> "smithing_template." + s + ".applies_to")))
                .withStyle(ChatFormatting.BLUE);
    }

    static Component getIngredientsComponent(ResourceLocation resourceLocation) {
        return Component.translatable(Util.makeDescriptionId("item",
                        resourceLocation.withPath((String s) -> "smithing_template." + s + ".ingredients")))
                .withStyle(ChatFormatting.BLUE);
    }

    static Component getUpgradeDescriptionComponent(ResourceLocation resourceLocation) {
        return Component.translatable(Util.makeDescriptionId("upgrade", resourceLocation))
                .withStyle(ChatFormatting.GRAY);
    }

    static Component getBaseSlotDescriptionComponent(ResourceLocation resourceLocation) {
        return Component.translatable(Util.makeDescriptionId("item",
                resourceLocation.withPath((String s) -> "smithing_template." + s + ".base_slot_description")));
    }

    static Component getAdditionsSlotDescriptionComponent(ResourceLocation resourceLocation) {
        return Component.translatable(Util.makeDescriptionId("item",
                resourceLocation.withPath((String s) -> "smithing_template." + s + ".additions_slot_description")));
    }
}

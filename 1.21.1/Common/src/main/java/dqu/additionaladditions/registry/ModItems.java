package dqu.additionaladditions.registry;

import dqu.additionaladditions.item.*;
import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.item.v2.ItemEquipmentFactories;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collections;

public class ModItems {
    static final Tier ROSE_GOLD_TIER = ItemEquipmentFactories.registerTier(2,
            900,
            9.0F,
            2.0F,
            17,
            () -> Ingredient.of(Items.COPPER_INGOT));
    static final Holder.Reference<ArmorMaterial> ROSE_GOLD_ARMOR_MATERIAL = ModRegistry.REGISTRIES.registerArmorMaterial(
            "rose_gold",
            ItemEquipmentFactories.toArmorTypeMap(2, 6, 7, 2),
            17,
            SoundEvents.ARMOR_EQUIP_GOLD,
            () -> Ingredient.of(Items.COPPER_INGOT),
            1.0F,
            0.0F);
    static final FoodProperties FRIED_EGG_FOOD = new FoodProperties.Builder().nutrition(6)
            .saturationModifier(FoodConstants.FOOD_SATURATION_GOOD)
            .build();
    static final FoodProperties BERRY_PIE_FOOD = new FoodProperties.Builder().nutrition(8)
            .saturationModifier(FoodConstants.FOOD_SATURATION_NORMAL)
            .build();
    static final FoodProperties HONEYED_APPLE_FOOD = new FoodProperties.Builder().nutrition(8)
            .saturationModifier(FoodConstants.FOOD_SATURATION_LOW)
            .build();
    static final FoodProperties CHICKEN_NUGGET_FOOD = new FoodProperties.Builder().nutrition(1)
            .saturationModifier(FoodConstants.FOOD_SATURATION_POOR)
            .build();

    public static final Holder.Reference<Item> ROPE = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.ROPE);
    public static final Holder.Reference<Item> AMETHYST_LAMP = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.AMETHYST_LAMP);
    public static final Holder.Reference<Item> COPPER_PATINA = ModRegistry.REGISTRIES.registerItem(ModBlocks.COPPER_PATINA.key()
            .location()
            .getPath(), () -> new CopperPatinaItem(ModBlocks.COPPER_PATINA.value(), new Item.Properties()));
    public static final Holder.Reference<Item> PATINA_BLOCK = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.PATINA_BLOCK);

    public static final Holder.Reference<Item> WATERING_CAN = ModRegistry.REGISTRIES.registerItem("watering_can",
            () -> new WateringCanItem(new Item.Properties().stacksTo(1).durability(101)));
    public static final Holder.Reference<Item> WRENCH = ModRegistry.REGISTRIES.registerItem("wrench",
            () -> new WrenchItem(new Item.Properties().stacksTo(1).durability(256)));
    public static final Holder.Reference<Item> CROSSBOW_WITH_SPYGLASS = ModRegistry.REGISTRIES.registerItem(
            "crossbow_with_spyglass",
            () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(350)));
    public static final Holder.Reference<Item> TRIDENT_SHARD = ModRegistry.REGISTRIES.registerItem("trident_shard",
            () -> new Item(new Item.Properties()));
    public static final Holder.Reference<Item> GLOW_STICK = ModRegistry.REGISTRIES.registerItem("glow_stick",
            () -> new GlowStickItem(new Item.Properties()));
    public static final Holder.Reference<Item> DEPTH_METER = ModRegistry.REGISTRIES.registerItem("depth_meter",
            () -> new DepthMeterItem(new Item.Properties()));
    public static final Holder.Reference<Item> MYSTERIOUS_BUNDLE = ModRegistry.REGISTRIES.registerItem(
            "mysterious_bundle",
            () -> new MysteriousBundleItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final Holder.Reference<Item> GOLDEN_RING = ModRegistry.REGISTRIES.registerItem("golden_ring",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Holder.Reference<Item> POCKET_JUKEBOX = ModRegistry.REGISTRIES.registerItem("pocket_jukebox",
            () -> new PocketJukeboxItem(new Item.Properties().stacksTo(1)));
    public static final Holder.Reference<Item> ROSE_GOLD_ALLOY = ModRegistry.REGISTRIES.registerItem("rose_gold_alloy",
            () -> new Item(new Item.Properties()));
    public static final Holder.Reference<Item> FRIED_EGG = ModRegistry.REGISTRIES.registerItem("fried_egg",
            () -> new Item(new Item.Properties().food(FRIED_EGG_FOOD)));
    public static final Holder.Reference<Item> BERRY_PIE = ModRegistry.REGISTRIES.registerItem("berry_pie",
            () -> new Item(new Item.Properties().food(BERRY_PIE_FOOD)));
    public static final Holder.Reference<Item> HONEYED_APPLE = ModRegistry.REGISTRIES.registerItem("honeyed_apple",
            () -> new Item(new Item.Properties().food(HONEYED_APPLE_FOOD)));
    public static final Holder.Reference<Item> CHICKEN_NUGGET = ModRegistry.REGISTRIES.registerItem("chicken_nugget",
            () -> new Item(new Item.Properties().food(CHICKEN_NUGGET_FOOD)));
    public static final Holder.Reference<Item> ROSE_GOLD_UPGRADE = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_upgrade",
            () -> SmithingTemplateItemHelper.createUpgradeTemplate(AdditionalSubtractions.id("rose_gold_upgrade"),
                    Collections.singletonList(AdditionalSubtractions.id("item/empty_slot_alloy"))));

    public static final Holder.Reference<Item> ROSE_GOLD_SWORD = ModRegistry.REGISTRIES.registerItem("rose_gold_sword",
            () -> new SwordItem(ROSE_GOLD_TIER,
                    new Item.Properties().attributes(SwordItem.createAttributes(ROSE_GOLD_TIER, 4, -2.4F))));
    public static final Holder.Reference<Item> ROSE_GOLD_SHOVEL = ModRegistry.REGISTRIES.registerItem("rose_gold_shovel",
            () -> new ShovelItem(ROSE_GOLD_TIER,
                    new Item.Properties().attributes(ShovelItem.createAttributes(ROSE_GOLD_TIER, 1.5F, -3.0F))));
    public static final Holder.Reference<Item> ROSE_GOLD_PICKAXE = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_pickaxe",
            () -> new PickaxeItem(ROSE_GOLD_TIER,
                    new Item.Properties().attributes(PickaxeItem.createAttributes(ROSE_GOLD_TIER, 1.0F, -2.8F))));
    public static final Holder.Reference<Item> ROSE_GOLD_AXE = ModRegistry.REGISTRIES.registerItem("rose_gold_axe",
            () -> new AxeItem(ROSE_GOLD_TIER,
                    new Item.Properties().attributes(AxeItem.createAttributes(ROSE_GOLD_TIER, 6.0F, -3.1F))));
    public static final Holder.Reference<Item> ROSE_GOLD_HOE = ModRegistry.REGISTRIES.registerItem("rose_gold_hoe",
            () -> new HoeItem(ROSE_GOLD_TIER,
                    new Item.Properties().attributes(HoeItem.createAttributes(ROSE_GOLD_TIER, -2.0F, -1.0F))));
    public static final Holder.Reference<Item> ROSE_GOLD_HELMET = ModRegistry.REGISTRIES.registerItem("rose_gold_helmet",
            () -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(24))));
    public static final Holder.Reference<Item> ROSE_GOLD_CHESTPLATE = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_chestplate",
            () -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(24))));
    public static final Holder.Reference<Item> ROSE_GOLD_LEGGINGS = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_leggings",
            () -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(24))));
    public static final Holder.Reference<Item> ROSE_GOLD_BOOTS = ModRegistry.REGISTRIES.registerItem("rose_gold_boots",
            () -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(24))));

    public static void bootstrap() {
        // NO-OP
    }
}

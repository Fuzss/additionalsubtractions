package fuzs.additionalsubtractions.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.world.item.*;
import fuzs.puzzleslib.api.item.v2.ItemEquipmentFactories;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;

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
    static final FoodProperties SWEET_BERRY_PIE_FOOD = new FoodProperties.Builder().nutrition(8)
            .saturationModifier(FoodConstants.FOOD_SATURATION_NORMAL)
            .build();
    static final FoodProperties HONEYED_APPLE_FOOD = new FoodProperties.Builder().nutrition(8)
            .saturationModifier(FoodConstants.FOOD_SATURATION_LOW)
            .build();
    static final FoodProperties CHICKEN_NUGGET_FOOD = new FoodProperties.Builder().nutrition(1)
            .saturationModifier(FoodConstants.FOOD_SATURATION_POOR)
            .build();
    static final FoodProperties HEARTBEET_FOOD = new FoodProperties.Builder().nutrition(2)
            .saturationModifier(0.6F)
            .build();

    public static final Holder.Reference<Item> ROPE = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.ROPE,
            RopeItem::new);
    public static final Holder.Reference<Item> AMETHYST_LAMP = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.AMETHYST_LAMP);
    public static final Holder.Reference<Item> PATINA_BLOCK = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.PATINA_BLOCK);
    public static final Holder.Reference<Item> OBSIDIAN_PRESSURE_PLATE = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.OBSIDIAN_PRESSURE_PLATE);
    public static final Holder.Reference<Item> COPPER_RAIL = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.COPPER_RAIL);
    public static final Holder.Reference<Item> COPPER_HOPPER = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.COPPER_HOPPER,
            () -> new Item.Properties().component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    public static final Holder.Reference<Item> SOUL_JACK_O_LANTERN = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.SOUL_JACK_O_LANTERN);
    public static final Holder.Reference<Item> REDSTONE_JACK_O_LANTERN = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.REDSTONE_JACK_O_LANTERN);
    public static final Holder.Reference<Item> REDSTONE_LANTERN = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.REDSTONE_LANTERN);
    public static final Holder.Reference<Item> POLISHED_ANDESITE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.POLISHED_ANDESITE_PEDESTAL);
    public static final Holder.Reference<Item> POLISHED_GRANITE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.POLISHED_GRANITE_PEDESTAL);
    public static final Holder.Reference<Item> POLISHED_DIORITE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.POLISHED_DIORITE_PEDESTAL);
    public static final Holder.Reference<Item> STONE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.STONE_BRICK_PEDESTAL);
    public static final Holder.Reference<Item> MOSSY_STONE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.MOSSY_STONE_BRICK_PEDESTAL);
    public static final Holder.Reference<Item> CRACKED_STONE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CRACKED_STONE_BRICK_PEDESTAL);
    public static final Holder.Reference<Item> CUT_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CUT_SANDSTONE_PEDESTAL);
    public static final Holder.Reference<Item> CUT_RED_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CUT_RED_SANDSTONE_PEDESTAL);
    public static final Holder.Reference<Item> CHISELED_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CHISELED_SANDSTONE_PEDESTAL);
    public static final Holder.Reference<Item> CHISELED_RED_SANDSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.CHISELED_RED_SANDSTONE_PEDESTAL);
    public static final Holder.Reference<Item> PRISMARINE_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.PRISMARINE_BRICK_PEDESTAL);
    public static final Holder.Reference<Item> BLACKSTONE_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.BLACKSTONE_PEDESTAL);
    public static final Holder.Reference<Item> NETHER_BRICK_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.NETHER_BRICK_PEDESTAL);
    public static final Holder.Reference<Item> PURPUR_BLOCK_PEDESTAL = ModRegistry.REGISTRIES.registerBlockItem(
            ModBlocks.PURPUR_BLOCK_PEDESTAL);
    public static final Holder.Reference<Item> TIMER = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.TIMER);
    public static final Holder.Reference<Item> BOOKSHELF_SWITCH = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.BOOKSHELF_SWITCH);

    public static final Holder.Reference<Item> COPPER_PATINA = ModRegistry.REGISTRIES.registerItem("copper_patina",
            CopperPatinaItem::new);
    public static final Holder.Reference<Item> WATERING_CAN = ModRegistry.REGISTRIES.registerItem("watering_can",
            WateringCanItem::new,
            () -> new Item.Properties().stacksTo(1));
    public static final Holder.Reference<Item> COPPER_WRENCH = ModRegistry.REGISTRIES.registerItem("copper_wrench",
            WrenchItem::new,
            () -> new Item.Properties().stacksTo(1));
    public static final Holder.Reference<Item> CROSSBOW_WITH_SPYGLASS = ModRegistry.REGISTRIES.registerItem(
            "crossbow_with_spyglass",
            CrossbowWithSpyglassItem::new,
            () -> new Item.Properties().stacksTo(1)
                    .durability(465)
                    .component(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY));
    public static final Holder.Reference<Item> TRIDENT_SHARD = ModRegistry.REGISTRIES.registerItem("trident_shard");
    public static final Holder.Reference<Item> GLOW_STICK = ModRegistry.REGISTRIES.registerBlockItem(ModBlocks.GLOW_STICK,
            GlowStickItem::new);
    public static final Holder.Reference<Item> DEPTH_METER = ModRegistry.REGISTRIES.registerItem("depth_meter",
            DepthMeterItem::new);
    public static final Holder.Reference<Item> MYSTERIOUS_BUNDLE = ModRegistry.REGISTRIES.registerItem(
            "mysterious_bundle",
            MysteriousBundleItem::new,
            () -> new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    public static final Holder.Reference<Item> GOLDEN_RING = ModRegistry.REGISTRIES.registerSimpleItem("golden_ring",
            () -> new Item.Properties().stacksTo(1));
    public static final Holder.Reference<Item> POCKET_JUKEBOX = ModRegistry.REGISTRIES.registerItem("pocket_jukebox",
            PocketJukeboxItem::new,
            () -> new Item.Properties().stacksTo(1)
                    .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)
                    .component(ModRegistry.POCKET_JUKEBOX_UUID_DATA_COMPONENT_TYPE.value(), Util.NIL_UUID));
    public static final Holder.Reference<Item> ROSE_GOLD_ALLOY = ModRegistry.REGISTRIES.registerItem("rose_gold_alloy");
    public static final Holder.Reference<Item> FRIED_EGG = ModRegistry.REGISTRIES.registerSimpleItem("fried_egg",
            () -> new Item.Properties().food(FRIED_EGG_FOOD));
    public static final Holder.Reference<Item> SWEET_BERRY_PIE = ModRegistry.REGISTRIES.registerSimpleItem(
            "sweet_berry_pie",
            () -> new Item.Properties().food(SWEET_BERRY_PIE_FOOD));
    public static final Holder.Reference<Item> HONEYED_APPLE = ModRegistry.REGISTRIES.registerSimpleItem("honeyed_apple",
            () -> new Item.Properties().food(HONEYED_APPLE_FOOD));
    public static final Holder.Reference<Item> CHICKEN_NUGGET = ModRegistry.REGISTRIES.registerSimpleItem(
            "chicken_nugget",
            () -> new Item.Properties().food(CHICKEN_NUGGET_FOOD));
    public static final Holder.Reference<Item> HEARTBEET = ModRegistry.REGISTRIES.registerSimpleItem("heartbeet",
            () -> new Item.Properties().food(HEARTBEET_FOOD));
    public static final Holder.Reference<Item> ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_upgrade_smithing_template",
            (Item.Properties properties) -> SmithingTemplateItemHelper.createUpgradeTemplate(AdditionalSubtractions.id(
                            "rose_gold_upgrade_smithing_template"),
                    Collections.singletonList(AdditionalSubtractions.id("item/empty_slot_alloy"))));
    public static final Holder.Reference<Item> MUSIC_DISC_0308 = ModRegistry.REGISTRIES.registerSimpleItem(
            "music_disc_0308",
            () -> new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ModJukeboxSongs.ZERO_THREE_ZERO_EIGHT_JUKEBOX_SONG));
    public static final Holder.Reference<Item> MUSIC_DISC_1007 = ModRegistry.REGISTRIES.registerSimpleItem(
            "music_disc_1007",
            () -> new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ModJukeboxSongs.ONE_ZERO_ZERO_SEVEN_JUKEBOX_SONG));
    public static final Holder.Reference<Item> MUSIC_DISC_1507 = ModRegistry.REGISTRIES.registerSimpleItem(
            "music_disc_1507",
            () -> new Item.Properties().stacksTo(1)
                    .rarity(Rarity.RARE)
                    .jukeboxPlayable(ModJukeboxSongs.ONE_FIVE_ZERO_SEVEN_JUKEBOX_SONG));

    public static final Holder.Reference<Item> ROSE_GOLD_SWORD = ModRegistry.REGISTRIES.registerItem("rose_gold_sword",
            (Item.Properties properties) -> new SwordItem(ROSE_GOLD_TIER, properties),
            () -> new Item.Properties().attributes(SwordItem.createAttributes(ROSE_GOLD_TIER, 4, -2.4F)));
    public static final Holder.Reference<Item> ROSE_GOLD_SHOVEL = ModRegistry.REGISTRIES.registerItem("rose_gold_shovel",
            (Item.Properties properties) -> new ShovelItem(ROSE_GOLD_TIER, properties),
            () -> new Item.Properties().attributes(ShovelItem.createAttributes(ROSE_GOLD_TIER, 1.5F, -3.0F)));
    public static final Holder.Reference<Item> ROSE_GOLD_PICKAXE = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_pickaxe",
            (Item.Properties properties) -> new PickaxeItem(ROSE_GOLD_TIER, properties),
            () -> new Item.Properties().attributes(PickaxeItem.createAttributes(ROSE_GOLD_TIER, 1.0F, -2.8F)));
    public static final Holder.Reference<Item> ROSE_GOLD_AXE = ModRegistry.REGISTRIES.registerItem("rose_gold_axe",
            (Item.Properties properties) -> new AxeItem(ROSE_GOLD_TIER, properties),
            () -> new Item.Properties().attributes(AxeItem.createAttributes(ROSE_GOLD_TIER, 6.0F, -3.1F)));
    public static final Holder.Reference<Item> ROSE_GOLD_HOE = ModRegistry.REGISTRIES.registerItem("rose_gold_hoe",
            (Item.Properties properties) -> new HoeItem(ROSE_GOLD_TIER, properties),
            () -> new Item.Properties().attributes(HoeItem.createAttributes(ROSE_GOLD_TIER, -2.0F, -1.0F)));
    public static final Holder.Reference<Item> ROSE_GOLD_HELMET = ModRegistry.REGISTRIES.registerItem("rose_gold_helmet",
            (Item.Properties properties) -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET, properties),
            () -> new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(24)));
    public static final Holder.Reference<Item> ROSE_GOLD_CHESTPLATE = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_chestplate",
            (Item.Properties properties) -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    properties),
            () -> new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(24)));
    public static final Holder.Reference<Item> ROSE_GOLD_LEGGINGS = ModRegistry.REGISTRIES.registerItem(
            "rose_gold_leggings",
            (Item.Properties properties) -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    properties),
            () -> new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(24)));
    public static final Holder.Reference<Item> ROSE_GOLD_BOOTS = ModRegistry.REGISTRIES.registerItem("rose_gold_boots",
            (Item.Properties properties) -> new ArmorItem(ROSE_GOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, properties),
            () -> new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(24)));
    public static final Holder.Reference<Item> NETHERITE_HORSE_ARMOR = ModRegistry.REGISTRIES.registerItem(
            "netherite_horse_armor",
            (Item.Properties properties) -> {
                return new ModAnimalArmorItem(ArmorMaterials.NETHERITE,
                        AnimalArmorItem.BodyType.EQUESTRIAN,
                        false,
                        properties);
            },
            () -> new Item.Properties().stacksTo(1));
    public static final Holder.Reference<Item> BAT_BUCKET = ModRegistry.REGISTRIES.registerItem("bat_bucket",
            (Item.Properties properties) -> new BatBucketItem(EntityType.BAT,
                    Fluids.WATER,
                    SoundEvents.BAT_AMBIENT,
                    properties

            ),
            () -> new Item.Properties().stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY));
    public static final Holder.Reference<Item> COPPER_HOPPER_MINECART = ModRegistry.REGISTRIES.registerItem(
            "copper_hopper_minecart",
            (Item.Properties properties) -> new MinecartItem(AbstractMinecart.Type.HOPPER, properties),
            () -> new Item.Properties().stacksTo(1));

    public static void bootstrap() {
        // NO-OP
    }
}

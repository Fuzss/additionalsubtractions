package fuzs.additionalsubtractions.data.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.data.ModAdvancementProvider;
import fuzs.additionalsubtractions.init.*;
import fuzs.additionalsubtractions.world.item.DepthMeterItem;
import fuzs.additionalsubtractions.world.item.PocketJukeboxItem;
import fuzs.puzzleslib.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.init.v3.registry.ResourceKeyHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SmithingTemplateItem;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder translationBuilder) {

        translationBuilder.add(ModRegistry.CREATIVE_MODE_TAB.value(), AdditionalSubtractions.MOD_NAME);
        translationBuilder.addBlock(ModBlocks.ROPE, "Rope");
        translationBuilder.addBlock(ModBlocks.AMETHYST_LAMP, "Amethyst Lamp");
        translationBuilder.addItem(ModItems.COPPER_PATINA, "Copper Patina");
        translationBuilder.addBlock(ModBlocks.GLOW_STICK, "Glow Stick");
        translationBuilder.addBlock(ModBlocks.PATINA_BLOCK, "Patina Block");
        translationBuilder.addBlock(ModBlocks.OBSIDIAN_PRESSURE_PLATE, "Obsidian Pressure Plate");
        translationBuilder.add(ModBlocks.COPPER_RAIL.value(), "Copper Rail");
        translationBuilder.add(ModBlocks.COPPER_HOPPER.value(), "Copper Hopper");
        translationBuilder.add(ModRegistry.GLOW_STICK_ENTITY_TYPE.value(), "Glow Stick");
        translationBuilder.add(ModRegistry.PATINA_BLOCK_ENTITY_TYPE.value(), "Patina Block");
        translationBuilder.add(ModRegistry.COPPER_HOPPER_MINECART_ENTITY_TYPE.value(), "Minecart with Copper Hopper");

        translationBuilder.addItem(ModItems.SWEET_BERRY_PIE, "Sweet Berry Pie");
        translationBuilder.addItem(ModItems.WATERING_CAN, "Watering Can");
        translationBuilder.addItem(ModItems.CROSSBOW_WITH_SPYGLASS, "Crossbow with Spyglass");
        translationBuilder.addItem(ModItems.COPPER_WRENCH, "Copper Wrench");
        translationBuilder.addItem(ModItems.FRIED_EGG, "Fried Egg");
        translationBuilder.addItem(ModItems.HEARTBEET, "Heartbeet");
        translationBuilder.addItem(ModItems.TRIDENT_SHARD, "Trident Shard");
        translationBuilder.addItem(ModItems.HONEYED_APPLE, "Honeyed Apple");
        translationBuilder.addItem(ModItems.DEPTH_METER, "Depth Meter");
        translationBuilder.addItem(ModItems.MUSIC_DISC_0308, "Music Disc");
        translationBuilder.addItem(ModItems.MUSIC_DISC_1007, "Music Disc");
        translationBuilder.addItem(ModItems.MUSIC_DISC_1507, "Music Disc");
        translationBuilder.addItem(ModItems.MYSTERIOUS_BUNDLE, "Mysterious Bundle");
        translationBuilder.addItem(ModItems.POCKET_JUKEBOX, "Pocket Jukebox");
        translationBuilder.addItem(ModItems.CHICKEN_NUGGET, "Chicken Nugget");
        translationBuilder.addItem(ModItems.GOLDEN_RING, "Golden Ring");
        translationBuilder.addItem(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE, "Smithing Template");
        translationBuilder.addItem(ModItems.ROSE_GOLD_ALLOY, "Rose Gold Alloy");
        translationBuilder.addItem(ModItems.ROSE_GOLD_SWORD, "Rose Gold Sword");
        translationBuilder.addItem(ModItems.ROSE_GOLD_AXE, "Rose Gold Axe");
        translationBuilder.addItem(ModItems.ROSE_GOLD_PICKAXE, "Rose Gold Pickaxe");
        translationBuilder.addItem(ModItems.ROSE_GOLD_SHOVEL, "Rose Gold Shovel");
        translationBuilder.addItem(ModItems.ROSE_GOLD_HOE, "Rose Gold Hoe");
        translationBuilder.addItem(ModItems.ROSE_GOLD_HELMET, "Rose Gold Helmet");
        translationBuilder.addItem(ModItems.ROSE_GOLD_CHESTPLATE, "Rose Gold Chestplate");
        translationBuilder.addItem(ModItems.ROSE_GOLD_LEGGINGS, "Rose Gold Leggings");
        translationBuilder.addItem(ModItems.ROSE_GOLD_BOOTS, "Rose Gold Boots");
        translationBuilder.addItem(ModItems.NETHERITE_HORSE_ARMOR, "Netherite Horse Armor");
        translationBuilder.addItem(ModItems.BAT_BUCKET, "Bat Bucket");
        translationBuilder.addItem(ModItems.COPPER_HOPPER_MINECART, "Minecart with Copper Hopper");

        translationBuilder.addPotion(ModRegistry.HURRY_POTION, "Hurry");
        translationBuilder.add(((SmithingTemplateItem) ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()).appliesTo,
                "Iron Equipment");
        translationBuilder.add(((SmithingTemplateItem) ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()).ingredients,
                "Rose Gold Alloy");
        translationBuilder.add(((SmithingTemplateItem) ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()).upgradeDescription,
                "Rose Gold Upgrade");
        translationBuilder.add(((SmithingTemplateItem) ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()).getBaseSlotDescription(),
                "Add iron armor, weapon, or tool");
        translationBuilder.add(((SmithingTemplateItem) ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()).getAdditionSlotDescription(),
                "Add Rose Gold Alloy");
        translationBuilder.add(DepthMeterItem.getElevationComponent(-1), "Altitude: %s");
        translationBuilder.add(PocketJukeboxItem.getDescriptionComponent(), "Can hold a music disc");
        translationBuilder.add(Items.FIREWORK_STAR,
                "shape." + ModEnumConstants.BOLT_EXPLOSION_SHAPE.getSerializedName(),
                "Bolt");
        translationBuilder.add(Items.FIREWORK_STAR,
                "shape." + ModEnumConstants.HEART_EXPLOSION_SHAPE.getSerializedName(),
                "Heart");

        translationBuilder.add(ResourceKeyHelper.getTranslationKey(ModJukeboxSongs.ZERO_THREE_ZERO_EIGHT_JUKEBOX_SONG),
                "Adoghr - 0308");
        translationBuilder.add(ResourceKeyHelper.getTranslationKey(ModJukeboxSongs.ONE_ZERO_ZERO_SEVEN_JUKEBOX_SONG),
                "Adoghr - 1007");
        translationBuilder.add(ResourceKeyHelper.getTranslationKey(ModJukeboxSongs.ONE_FIVE_ZERO_SEVEN_JUKEBOX_SONG),
                "Adoghr - 1507");

        translationBuilder.add(ModAdvancementProvider.ROOT_ADVANCEMENT.title(), AdditionalSubtractions.MOD_NAME);
        translationBuilder.add(ModAdvancementProvider.ROOT_ADVANCEMENT.description(), "To vanilla and beyond!");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_MUSIC_DISCS_ADVANCEMENT.title(), "Music To My Ears");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_MUSIC_DISCS_ADVANCEMENT.description(),
                "Obtain all of Adoghr's music discs");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_GLOW_STICK_ADVANCEMENT.title(), "Light In The Dark");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_GLOW_STICK_ADVANCEMENT.description(),
                "Obtain a Glow Stick to light up caves");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_MYSTERIOUS_BUNDLE_ADVANCEMENT.title(), "A Pig In A Poke");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_MYSTERIOUS_BUNDLE_ADVANCEMENT.description(),
                "Purchase a Mysterious Bundle from the Wandering Trader");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_POCKET_JUKEBOX_ADVANCEMENT.title(), "What A Nugget!");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_POCKET_JUKEBOX_ADVANCEMENT.description(),
                "Craft a Pocket Jukebox to listen to music on the go");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_ROSE_GOLD_ADVANCEMENT.title(), "Gold N' Roses");
        translationBuilder.add(ModAdvancementProvider.OBTAIN_ROSE_GOLD_ADVANCEMENT.description(),
                "Obtain all Rose Gold armor pieces");
        translationBuilder.add(ModAdvancementProvider.SHOOT_SCOPED_CROSSBOW_ADVANCEMENT.title(), "Hitman");
        translationBuilder.add(ModAdvancementProvider.SHOOT_SCOPED_CROSSBOW_ADVANCEMENT.description(),
                "Obtain a Crossbow with Spyglass");
        translationBuilder.add(ModAdvancementProvider.SHOOT_SELF_SCOPED_CROSSBOW_ADVANCEMENT.title(),
                "Self-Employed Hitman");
        translationBuilder.add(ModAdvancementProvider.SHOOT_SELF_SCOPED_CROSSBOW_ADVANCEMENT.description(),
                "Hit yourself with your own arrow using a Crossbow with Spyglass");
        translationBuilder.add(ModAdvancementProvider.USE_WATERING_CAN_ADVANCEMENT.title(), "Water It Can");
        translationBuilder.add(ModAdvancementProvider.USE_WATERING_CAN_ADVANCEMENT.description(),
                "Obtain and use a Watering Can to fertilize your crops");
    }
}

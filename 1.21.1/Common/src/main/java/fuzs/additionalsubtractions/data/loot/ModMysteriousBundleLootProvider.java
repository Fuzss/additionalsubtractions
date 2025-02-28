package fuzs.additionalsubtractions.data.loot;

import fuzs.additionalsubtractions.init.ModLootTables;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModMysteriousBundleLootProvider extends AbstractLootProvider.Simple {

    public ModMysteriousBundleLootProvider(DataProviderContext context) {
        super(ModRegistry.MYSTERIOUS_BUNDLE_LOOT_CONTEXT_PARAM_SET, context);
    }

    @Override
    public void addLootTables() {
        this.add(ModLootTables.MYSTERIOUS_BUNDLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0F, 5.0F))
                                .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.PRISMARINE).setWeight(3))
                                .add(LootItem.lootTableItem(Items.SADDLE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(4))
                                .add(LootItem.lootTableItem(Items.CAKE).setWeight(4))
                                .add(LootItem.lootTableItem(Items.LEAD).setWeight(4))
                                .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(2))
                                .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(2))
                                .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(2))
                                .add(LootItem.lootTableItem(Items.CHAINMAIL_BOOTS).setWeight(2))
                                .add(LootItem.lootTableItem(Items.CHAIN).setWeight(6))
                                .add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(6))
                                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5))
                                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(3))
                                .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(4))
                                .add(LootItem.lootTableItem(Items.MUSHROOM_STEW).setWeight(4))
                                .add(LootItem.lootTableItem(Items.BOW).setWeight(3))
                                .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))
                                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                                .add(LootItem.lootTableItem(Items.SPYGLASS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.COMPASS).setWeight(3))
                                .add(LootItem.lootTableItem(Items.CLOCK).setWeight(3))
                                .add(LootItem.lootTableItem(Items.SLIME_BALL).setWeight(2))
                                .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(1))
                                .add(LootItem.lootTableItem(Items.NAUTILUS_SHELL).setWeight(1))));
    }
}

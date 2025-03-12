package fuzs.additionalsubtractions.data.loot;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModLootTables;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModChestInjectionLootProvider extends AbstractLootProvider.Simple {

    public ModChestInjectionLootProvider(DataProviderContext context) {
        super(LootContextParamSets.CHEST, context);
    }

    @Override
    public void addLootTables() {
        this.add(ModLootTables.SIMPLE_DUNGEON_INJECTION,
                LootTable.lootTable()
                        .withPool(this.rope())
                        .withPool(this.glowStick())
                        .withPool(this.depthMeter())
                        .withPool(this.musicDiscs()));
        this.add(ModLootTables.ABANDONED_MINESHAFT_INJECTION,
                LootTable.lootTable().withPool(this.rope()).withPool(this.glowStick()).withPool(this.depthMeter()));
        this.add(ModLootTables.STRONGHOLD_CORRIDOR_INJECTION,
                LootTable.lootTable().withPool(this.rope()).withPool(this.glowStick()).withPool(this.depthMeter()));
        this.add(ModLootTables.WOODLAND_MANSION_INJECTION, LootTable.lootTable().withPool(this.musicDiscs()));
        this.add(ModLootTables.SHIPWRECK_SUPPLY_INJECTION,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition.randomChance(0.5F))
                                .add(LootItem.lootTableItem(Items.SPYGLASS))));
        this.add(ModLootTables.PIGLIN_BARTERING_INJECTION,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(LootItemRandomChanceCondition.randomChance(0.015F))
                                .add(LootItem.lootTableItem(ModItems.GOLDEN_RING.value()))));
        this.add(ModLootTables.PILLAGER_OUTPOST_INJECTION, LootTable.lootTable().withPool(this.roseGoldUpgrade()));
        this.add(ModLootTables.UNDERWATER_RUIN_SMALL_INJECTION, LootTable.lootTable().withPool(this.roseGoldUpgrade()));
        this.add(ModLootTables.UNDERWATER_RUIN_BIG_INJECTION, LootTable.lootTable().withPool(this.roseGoldUpgrade()));
        this.add(ModLootTables.SHIPWRECK_TREASURE_INJECTION, LootTable.lootTable().withPool(this.roseGoldUpgrade()));
    }

    private LootPool.Builder rope() {
        return LootPool.lootPool()
                .setRolls(UniformGenerator.between(1.0F, 4.0F))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 16.0F)))
                .add(LootItem.lootTableItem(ModItems.ROPE.value()));
    }

    private LootPool.Builder glowStick() {
        return LootPool.lootPool()
                .setRolls(UniformGenerator.between(0.0F, 4.0F))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                .add(LootItem.lootTableItem(ModItems.GLOW_STICK.value()));
    }

    private LootPool.Builder depthMeter() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                .add(LootItem.lootTableItem(ModItems.DEPTH_METER.value()));
    }

    private LootPool.Builder musicDiscs() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LootItemRandomChanceCondition.randomChance(0.25F))
                .add(LootItem.lootTableItem(ModItems.MUSIC_DISC_0308.value()))
                .add(LootItem.lootTableItem(ModItems.MUSIC_DISC_1007.value()))
                .add(LootItem.lootTableItem(ModItems.MUSIC_DISC_1507.value()));
    }

    private LootPool.Builder roseGoldUpgrade() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .when(LootItemRandomChanceCondition.randomChance(0.25F))
                .add(LootItem.lootTableItem(ModItems.ROSE_GOLD_UPGRADE_SMITHING_TEMPLATE.value()));
    }
}

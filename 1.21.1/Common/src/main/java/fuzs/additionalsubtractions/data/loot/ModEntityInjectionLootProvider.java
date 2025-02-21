package fuzs.additionalsubtractions.data.loot;

import dqu.additionaladditions.registry.ModItems;
import dqu.additionaladditions.registry.ModLootTables;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ModEntityInjectionLootProvider extends AbstractLootProvider.Simple {

    public ModEntityInjectionLootProvider(DataProviderContext context) {
        super(LootContextParamSets.ENTITY, context);
    }

    @Override
    public void addLootTables() {
        this.add(ModLootTables.ELDER_GUARDIAN_INJECTION_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.TRIDENT_SHARD.value()))));
    }
}

package fuzs.additionalsubtractions.data.loot;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModLootTables;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ModBlockInjectionLootProvider extends AbstractLootProvider.Simple {

    public ModBlockInjectionLootProvider(DataProviderContext context) {
        super(LootContextParamSets.BLOCK, context);
    }

    @Override
    public void addLootTables() {
        this.add(ModLootTables.BEETROOTS_INJECTION,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.BEETROOTS)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BeetrootBlock.AGE, 3)))
                                .add(LootItem.lootTableItem(ModItems.HEARTBEET.value())
                                        .when(LootItemRandomChanceCondition.randomChance(0.02F)))));
    }
}

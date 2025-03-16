package fuzs.additionalsubtractions.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class ModLootTables {
    static final Map<ResourceLocation, ResourceKey<LootTable>> LOOT_TABLE_INJECTIONS = new HashMap<>();
    public static final ResourceKey<LootTable> MYSTERIOUS_BUNDLE = ModRegistry.REGISTRIES.registerLootTable(
            "gameplay/mysterious_bundle");
    public static final ResourceKey<LootTable> BEETROOTS_INJECTION = registerLootTableInjection(Blocks.BEETROOTS.getLootTable());
    public static final ResourceKey<LootTable> ELDER_GUARDIAN_INJECTION = registerLootTableInjection(EntityType.ELDER_GUARDIAN.getDefaultLootTable());
    public static final ResourceKey<LootTable> SIMPLE_DUNGEON_INJECTION = registerLootTableInjection(BuiltInLootTables.SIMPLE_DUNGEON);
    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT_INJECTION = registerLootTableInjection(
            BuiltInLootTables.ABANDONED_MINESHAFT);
    public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR_INJECTION = registerLootTableInjection(
            BuiltInLootTables.STRONGHOLD_CORRIDOR);
    public static final ResourceKey<LootTable> WOODLAND_MANSION_INJECTION = registerLootTableInjection(BuiltInLootTables.WOODLAND_MANSION);
    public static final ResourceKey<LootTable> SHIPWRECK_SUPPLY_INJECTION = registerLootTableInjection(BuiltInLootTables.SHIPWRECK_SUPPLY);
    public static final ResourceKey<LootTable> PIGLIN_BARTERING_INJECTION = registerLootTableInjection(BuiltInLootTables.PIGLIN_BARTERING);
    public static final ResourceKey<LootTable> PILLAGER_OUTPOST_INJECTION = registerLootTableInjection(BuiltInLootTables.PILLAGER_OUTPOST);
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_SMALL_INJECTION = registerLootTableInjection(
            BuiltInLootTables.UNDERWATER_RUIN_SMALL);
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_BIG_INJECTION = registerLootTableInjection(
            BuiltInLootTables.UNDERWATER_RUIN_BIG);
    public static final ResourceKey<LootTable> SHIPWRECK_TREASURE_INJECTION = registerLootTableInjection(
            BuiltInLootTables.SHIPWRECK_TREASURE);

    public static void bootstrap() {
        // NO-OP
    }

    static ResourceKey<LootTable> registerLootTableInjection(ResourceKey<LootTable> resourceKey) {
        ResourceKey<LootTable> newResourceKey = ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
                "inject/" + resourceKey.location().getPath());
        LOOT_TABLE_INJECTIONS.put(resourceKey.location(), newResourceKey);
        return newResourceKey;
    }

    public static void onModifyLootTable(ResourceLocation resourceLocation, Consumer<LootPool> addLootPool, IntPredicate removeLootPool) {
        if (LOOT_TABLE_INJECTIONS.containsKey(resourceLocation)) {
            addLootPool.accept(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(NestedLootTable.lootTableReference(LOOT_TABLE_INJECTIONS.get(resourceLocation)))
                    .build());
        }
    }
}

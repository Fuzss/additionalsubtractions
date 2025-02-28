package fuzs.additionalsubtractions.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModLootTables {
    public static final ResourceKey<LootTable> MYSTERIOUS_BUNDLE = ModRegistry.REGISTRIES.registerLootTable(
            "gameplay/mysterious_bundle");
    public static final ResourceKey<LootTable> ELDER_GUARDIAN_INJECTION = registerLootTableInjection(
            EntityType.ELDER_GUARDIAN);
    public static final ResourceKey<LootTable> SIMPLE_DUNGEON_INJECTION = registerLootTableInjection(
            BuiltInLootTables.SIMPLE_DUNGEON);
    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT_INJECTION = registerLootTableInjection(
            BuiltInLootTables.ABANDONED_MINESHAFT);
    public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR_INJECTION = registerLootTableInjection(
            BuiltInLootTables.STRONGHOLD_CORRIDOR);
    public static final ResourceKey<LootTable> WOODLAND_MANSION_INJECTION = registerLootTableInjection(
            BuiltInLootTables.WOODLAND_MANSION);
    public static final ResourceKey<LootTable> SHIPWRECK_SUPPLY_INJECTION = registerLootTableInjection(
            BuiltInLootTables.SHIPWRECK_SUPPLY);
    public static final ResourceKey<LootTable> PIGLIN_BARTERING_INJECTION = registerLootTableInjection(
            BuiltInLootTables.PIGLIN_BARTERING);
    public static final ResourceKey<LootTable> PILLAGER_OUTPOST_INJECTION = registerLootTableInjection(
            BuiltInLootTables.PILLAGER_OUTPOST);
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
        return ModRegistry.REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
                resourceKey.location().getPath() + "_injection");
    }

    static ResourceKey<LootTable> registerLootTableInjection(EntityType<?> entityType) {
        return registerLootTableInjection(getEntityTypeLootTable(entityType));
    }

    static ResourceKey<LootTable> getEntityTypeLootTable(EntityType<?> entityType) {
        ResourceLocation resourceLocation = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
        return ResourceKey.create(Registries.LOOT_TABLE, resourceLocation.withPrefix("entities/"));
    }
}

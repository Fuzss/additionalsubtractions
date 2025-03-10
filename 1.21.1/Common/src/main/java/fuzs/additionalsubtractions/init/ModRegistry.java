package fuzs.additionalsubtractions.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.world.entity.projectile.GlowStickEntity;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.function.Consumer;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(AdditionalSubtractions.MOD_ID);
    public static final Holder.Reference<EntityType<GlowStickEntity>> GLOW_STICK_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "glow_stick",
            () -> EntityType.Builder.<GlowStickEntity>of(GlowStickEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10));
    public static final Holder.Reference<Potion> HURRY_POTION = REGISTRIES.registerPotion("hurry",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3600)));
    public static final Holder.Reference<Potion> STRONG_HURRY_POTION = REGISTRIES.registerPotion("strong_hurry",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 1600, 1)));
    public static final Holder.Reference<Potion> LONG_HURRY_POTION = REGISTRIES.registerPotion("long_hurry",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 9600)));
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(
            ModItems.CROSSBOW_WITH_SPYGLASS);

    public static final LootContextParamSet MYSTERIOUS_BUNDLE_LOOT_CONTEXT_PARAM_SET = registerLootContextParamSet(
            AdditionalSubtractions.id("mysterious_bundle"),
            (LootContextParamSet.Builder builder) -> builder.required(LootContextParams.ORIGIN)
                    .optional(LootContextParams.THIS_ENTITY));

    static final TagFactory TAGS = TagFactory.make(AdditionalSubtractions.MOD_ID);
    public static final TagKey<Item> MUSIC_DISCS_ITEM_TAG = TagFactory.COMMON.registerItemTag("music_discs");

    public static void bootstrap() {
        ModBlocks.bootstrap();
        ModItems.bootstrap();
        ModSoundEvents.bootstrap();
        ModLootTables.bootstrap();
    }

    @Deprecated
    public static LootContextParamSet registerLootContextParamSet(ResourceLocation resourceLocation, Consumer<LootContextParamSet.Builder> builderConsumer) {
        LootContextParamSet.Builder builder = new LootContextParamSet.Builder();
        builderConsumer.accept(builder);
        LootContextParamSet lootContextParamSet = builder.build();
        if (LootContextParamSets.REGISTRY.put(resourceLocation, lootContextParamSet) != null) {
            throw new IllegalStateException("Loot table parameter set " + resourceLocation + " is already registered");
        } else {
            return lootContextParamSet;
        }
    }
}

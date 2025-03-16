package fuzs.additionalsubtractions.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.world.entity.item.PatinaBlockEntity;
import fuzs.additionalsubtractions.world.entity.projectile.GlowStick;
import fuzs.additionalsubtractions.world.entity.vehicle.MinecartCopperHopper;
import fuzs.additionalsubtractions.world.item.PocketJukeboxSongPlayer;
import fuzs.additionalsubtractions.world.item.crafting.ModFireworkStarRecipe;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentRegistry;
import fuzs.puzzleslib.api.attachment.v4.DataAttachmentType;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder().add(Registries.ENCHANTMENT,
            ModEnchantments::bootstrap).add(Registries.JUKEBOX_SONG, ModJukeboxSongs::bootstrap);

    static final RegistryManager REGISTRIES = RegistryManager.from(AdditionalSubtractions.MOD_ID);
    public static final Holder.Reference<DataComponentType<PocketJukeboxSongPlayer>> POCKET_JUKEBOX_SONG_PLAYER_DATA_COMPONENT_TYPE = REGISTRIES.registerDataComponentType(
            "pocket_jukebox_song_player",
            (DataComponentType.Builder<PocketJukeboxSongPlayer> builder) -> builder.networkSynchronized(
                    PocketJukeboxSongPlayer.STREAM_CODEC));
    public static final Holder.Reference<EntityType<GlowStick>> GLOW_STICK_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "glow_stick",
            () -> EntityType.Builder.<GlowStick>of(GlowStick::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10));
    public static final Holder.Reference<EntityType<PatinaBlockEntity>> PATINA_BLOCK_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "patina_block",
            () -> EntityType.Builder.<PatinaBlockEntity>of(PatinaBlockEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(20));
    public static final Holder.Reference<EntityType<MinecartCopperHopper>> COPPER_HOPPER_MINECART_ENTITY_TYPE = REGISTRIES.registerEntityType(
            "copper_hopper_minecart",
            () -> EntityType.Builder.<MinecartCopperHopper>of(MinecartCopperHopper::new, MobCategory.MISC)
                    .sized(0.98F, 0.7F)
                    .passengerAttachments(0.1875F)
                    .clientTrackingRange(8));
    public static final Holder.Reference<Potion> HURRY_POTION = REGISTRIES.registerPotion("hurry",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 3600)));
    public static final Holder.Reference<Potion> STRONG_HURRY_POTION = REGISTRIES.registerPotion("strong_hurry",
            () -> new Potion("hurry", new MobEffectInstance(MobEffects.DIG_SPEED, 1600, 1)));
    public static final Holder.Reference<Potion> LONG_HURRY_POTION = REGISTRIES.registerPotion("long_hurry",
            () -> new Potion("hurry", new MobEffectInstance(MobEffects.DIG_SPEED, 9600)));
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(
            ModItems.CROSSBOW_WITH_SPYGLASS);
    public static final Holder.Reference<RecipeSerializer<ModFireworkStarRecipe>> FIREWORK_STAR_RECIPE_SERIALIZER = REGISTRIES.register(
            Registries.RECIPE_SERIALIZER,
            "crafting_special_firework_star",
            () -> new SimpleCraftingRecipeSerializer<>(ModFireworkStarRecipe::new));

    public static final LootContextParamSet MYSTERIOUS_BUNDLE_LOOT_CONTEXT_PARAM_SET = registerLootContextParamSet(
            AdditionalSubtractions.id("mysterious_bundle"),
            (LootContextParamSet.Builder builder) -> builder.required(LootContextParams.ORIGIN)
                    .optional(LootContextParams.THIS_ENTITY));

    static final TagFactory TAGS = TagFactory.make(AdditionalSubtractions.MOD_ID);
    public static final TagKey<Block> ROTATABLE_BLOCK_TAG = TAGS.registerBlockTag("rotatable");
    public static final TagKey<Item> MUSIC_DISCS_ITEM_TAG = TagFactory.COMMON.registerItemTag("music_discs");

    public static final DataAttachmentType<Entity, Map<UUID, SoundInstance>> PLAYING_POCKET_JUKEBOX_SONGS_ATTACHMENT_TYPE = DataAttachmentRegistry.<Map<UUID, SoundInstance>>entityBuilder()
            .defaultValue(Collections.emptyMap())
            .build(AdditionalSubtractions.id("playing_pocket_jukebox_songs"));

    public static void bootstrap() {
        ModBlocks.bootstrap();
        ModItems.bootstrap();
        ModSoundEvents.bootstrap();
        ModLootTables.bootstrap();
        ModEnumConstants.bootstrap();
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

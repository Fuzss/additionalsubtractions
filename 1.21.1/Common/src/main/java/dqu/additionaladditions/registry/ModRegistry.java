package dqu.additionaladditions.registry;

import dqu.additionaladditions.entity.GlowStickEntity;
import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.alchemy.Potion;

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

    public static void bootstrap() {
        ModBlocks.bootstrap();
        ModItems.bootstrap();
        ModLootTables.bootstrap();
    }
}

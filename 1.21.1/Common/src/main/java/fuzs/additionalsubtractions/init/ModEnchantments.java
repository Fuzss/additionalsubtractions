package fuzs.additionalsubtractions.init;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> POTENCY_ENCHANTMENT = ModRegistry.REGISTRIES.registerEnchantment(
            "potency");
    public static final ResourceKey<Enchantment> SUSTAINABILITY_ENCHANTMENT = ModRegistry.REGISTRIES.registerEnchantment(
            "sustainability");
    public static final ResourceKey<Enchantment> FERTILITY_ENCHANTMENT = ModRegistry.REGISTRIES.registerEnchantment(
            "fertility");

    public static void bootstrap(BootstrapContext<Enchantment> context) {

    }
}

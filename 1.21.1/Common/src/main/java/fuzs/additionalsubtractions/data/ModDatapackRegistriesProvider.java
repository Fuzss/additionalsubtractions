package fuzs.additionalsubtractions.data;

import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModDatapackRegistriesProvider extends AbstractDatapackRegistriesProvider {

    public ModDatapackRegistriesProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBootstrap(RegistryBoostrapConsumer consumer) {
        consumer.add(Registries.ENCHANTMENT, ModDatapackRegistriesProvider::bootstrapEnchantments);
    }

    static void bootstrapEnchantments(BootstrapContext<Enchantment> context) {

    }
}

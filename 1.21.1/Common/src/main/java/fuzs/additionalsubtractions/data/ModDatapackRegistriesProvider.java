package fuzs.additionalsubtractions.data;

import fuzs.additionalsubtractions.init.ModEnchantments;
import fuzs.additionalsubtractions.init.ModJukeboxSongs;
import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.registries.Registries;

public class ModDatapackRegistriesProvider extends AbstractDatapackRegistriesProvider {

    public ModDatapackRegistriesProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBootstrap(RegistryBoostrapConsumer consumer) {
        consumer.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
        consumer.add(Registries.JUKEBOX_SONG, ModJukeboxSongs::bootstrap);
    }
}

package fuzs.additionalsubtractions.neoforge.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.client.AdditionalSubtractionsClient;
import fuzs.additionalsubtractions.data.client.ModLanguageProvider;
import fuzs.additionalsubtractions.data.client.ModModelProvider;
import fuzs.additionalsubtractions.neoforge.data.client.ModSoundProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = AdditionalSubtractions.MOD_ID, dist = Dist.CLIENT)
public class AdditionalSubtractionsNeoForgeClient {

    public AdditionalSubtractionsNeoForgeClient() {
        ClientModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractionsClient::new);
        DataProviderHelper.registerDataProviders(AdditionalSubtractions.MOD_ID,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModSoundProvider::new);
    }
}

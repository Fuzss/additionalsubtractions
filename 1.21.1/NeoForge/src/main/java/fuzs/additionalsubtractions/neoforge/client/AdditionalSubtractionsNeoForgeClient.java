package fuzs.additionalsubtractions.neoforge.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.client.AdditionalSubtractionsClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = AdditionalSubtractions.MOD_ID, dist = Dist.CLIENT)
public class AdditionalSubtractionsNeoForgeClient {

    public AdditionalSubtractionsNeoForgeClient() {
        ClientModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractionsClient::new);
    }
}

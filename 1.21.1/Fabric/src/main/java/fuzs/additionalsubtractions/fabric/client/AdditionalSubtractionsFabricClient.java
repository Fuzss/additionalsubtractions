package fuzs.additionalsubtractions.fabric.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.client.AdditionalSubtractionsClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class AdditionalSubtractionsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractionsClient::new);
    }
}

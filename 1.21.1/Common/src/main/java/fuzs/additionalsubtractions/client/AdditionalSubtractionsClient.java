package fuzs.additionalsubtractions.client;

import fuzs.additionalsubtractions.client.handler.CrossbowScopingHandler;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.event.v1.entity.player.PlayerTickEvents;

public class AdditionalSubtractionsClient implements ClientModConstructor {

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        PlayerTickEvents.END.register(CrossbowScopingHandler::onEndPlayerTick);
    }
}

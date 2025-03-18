package fuzs.additionalsubtractions.client.init;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayerLocations {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(AdditionalSubtractions.MOD_ID);
    public static final ModelLayerLocation COPPER_HOPPER_MINECART = MODEL_LAYERS.register("copper_hopper_minecart");
    public static final ModelLayerLocation TIMER = MODEL_LAYERS.register("timer");
}

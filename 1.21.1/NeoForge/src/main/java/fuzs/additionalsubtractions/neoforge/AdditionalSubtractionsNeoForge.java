package fuzs.additionalsubtractions.neoforge;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.neoforged.fml.common.Mod;

@Mod(AdditionalSubtractions.MOD_ID)
public class AdditionalSubtractionsNeoForge {

    public AdditionalSubtractionsNeoForge() {
        ModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractions::new);
    }
}

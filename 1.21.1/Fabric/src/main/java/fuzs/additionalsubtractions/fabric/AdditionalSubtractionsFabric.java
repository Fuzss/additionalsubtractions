package fuzs.additionalsubtractions.fabric;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.world.item.Items;

public class AdditionalSubtractionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractions::new);
        // TODO move this to common registration once added to Puzzles Lib
        CompostingChanceRegistry.INSTANCE.add(Items.ROTTEN_FLESH, 0.3F);
    }
}

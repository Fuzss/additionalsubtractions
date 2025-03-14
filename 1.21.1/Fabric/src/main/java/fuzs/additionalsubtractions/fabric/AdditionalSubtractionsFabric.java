package fuzs.additionalsubtractions.fabric;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.List;

public class AdditionalSubtractionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractions::new);
        TradeOfferHelper.registerWanderingTraderOffers(2, (List<VillagerTrades.ItemListing> itemListings) -> {
            itemListings.add(new VillagerTrades.ItemsForEmeralds(ModItems.MYSTERIOUS_BUNDLE.value(), 6, 1, 6, 1));
        });
    }
}

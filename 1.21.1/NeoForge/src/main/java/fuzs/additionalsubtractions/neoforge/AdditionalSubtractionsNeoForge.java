package fuzs.additionalsubtractions.neoforge;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.data.ModAdvancementProvider;
import fuzs.additionalsubtractions.data.ModDatapackRegistriesProvider;
import fuzs.additionalsubtractions.data.ModRecipeProvider;
import fuzs.additionalsubtractions.data.loot.*;
import fuzs.additionalsubtractions.data.tags.ModBlockTagProvider;
import fuzs.additionalsubtractions.data.tags.ModItemTagProvider;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@Mod(AdditionalSubtractions.MOD_ID)
public class AdditionalSubtractionsNeoForge {

    public AdditionalSubtractionsNeoForge() {
        ModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractions::new);
        DataProviderHelper.registerDataProviders(AdditionalSubtractions.MOD_ID,
                ModDatapackRegistriesProvider::new,
                ModBlockLootProvider::new,
                ModChestInjectionLootProvider::new,
                ModBlockInjectionLootProvider::new,
                ModEntityInjectionLootProvider::new,
                ModMysteriousBundleLootProvider::new,
                ModBlockTagProvider::new,
                ModItemTagProvider::new,
                ModAdvancementProvider::new,
                ModRecipeProvider::new);
        registerEventHandlers(NeoForge.EVENT_BUS);
    }

    private static void registerEventHandlers(IEventBus eventBus) {
        eventBus.addListener((final WandererTradesEvent evt) -> {
            evt.getRareTrades().add(new BasicItemListing(6, new ItemStack(ModItems.MYSTERIOUS_BUNDLE.value()), 6, 1));
        });
    }
}

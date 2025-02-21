package dqu.additionaladditions;

import dqu.additionaladditions.misc.LootHandler;
import dqu.additionaladditions.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalAdditions implements ModInitializer {
    public static final String namespace = "additionaladditions";
    public static final Logger LOGGER = LoggerFactory.getLogger(namespace);
    public static boolean zoom = false;

    @Override
    public void onInitialize() {
        ModItems.registerAll();
        ModBlocks.registerAll();
        AdditionalEntities.registerAll();
        AdditionalEnchantments.registerAll();
        AdditionalMaterials.registerAll();
        AdditionalPotions.registerAll();
        AdditionalMusicDiscs.registerAll();

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, t) -> {
            LootHandler.handle(resourceManager.location(), lootManager);
        }));
    }
}

package fuzs.additionalsubtractions.neoforge.client;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.client.AdditionalSubtractionsClient;
import fuzs.additionalsubtractions.client.handler.FireworkExplosionShapeClientHelper;
import fuzs.additionalsubtractions.data.client.ModLanguageProvider;
import fuzs.additionalsubtractions.data.client.ModModelProvider;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.neoforge.data.client.ModSoundProvider;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.particle.FireworkParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.FireworkShapeFactoryRegistry;

@Mod(value = AdditionalSubtractions.MOD_ID, dist = Dist.CLIENT)
public class AdditionalSubtractionsNeoForgeClient {

    public AdditionalSubtractionsNeoForgeClient(ModContainer modContainer) {
        ClientModConstructor.construct(AdditionalSubtractions.MOD_ID, AdditionalSubtractionsClient::new);
        DataProviderHelper.registerDataProviders(AdditionalSubtractions.MOD_ID,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModSoundProvider::new);
        registerLoadingHandlers(modContainer.getEventBus());
    }

    private static void registerLoadingHandlers(IEventBus eventBus) {
        eventBus.addListener((final FMLClientSetupEvent evt) -> {
            evt.enqueueWork(() -> {
                FireworkShapeFactoryRegistry.register(ModRegistry.BOLT_EXPLOSION_SHAPE.get(),
                        (FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) -> {
                            FireworkExplosionShapeClientHelper.createBoltParticleShape(starter,
                                    IntArrayList.of(colors),
                                    IntArrayList.of(fadeColors),
                                    trail,
                                    flicker);
                        });
                FireworkShapeFactoryRegistry.register(ModRegistry.HEART_EXPLOSION_SHAPE.get(),
                        (FireworkParticles.Starter starter, boolean trail, boolean flicker, int[] colors, int[] fadeColors) -> {
                            FireworkExplosionShapeClientHelper.createHeartParticleShape(starter,
                                    IntArrayList.of(colors),
                                    IntArrayList.of(fadeColors),
                                    trail,
                                    flicker);
                        });
            });
        });
    }
}

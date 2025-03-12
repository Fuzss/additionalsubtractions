package fuzs.additionalsubtractions.neoforge.data.client;

import fuzs.additionalsubtractions.init.ModSoundEvents;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractSoundDefinitionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition;

public class ModSoundProvider extends AbstractSoundDefinitionProvider {

    public ModSoundProvider(NeoForgeDataProviderContext context) {
        super(context);
    }

    @Override
    public void addSoundDefinitions() {
        this.addRecord(ModSoundEvents.MUSIC_DISC_0308_SOUND_EVENT, "0308");
        this.addRecord(ModSoundEvents.MUSIC_DISC_1007_SOUND_EVENT, "1007");
        this.addRecord(ModSoundEvents.MUSIC_DISC_1507_SOUND_EVENT, "1507");
    }

    protected void addRecord(Holder<SoundEvent> soundEvent, String fileName) {
        ResourceLocation resourceLocation = this.id("records/" + fileName);
        SoundDefinition soundDefinition = definition().with(sound(resourceLocation).stream());
        this.add(soundEvent.value(), soundDefinition);
        soundDefinition.subtitle(null);
    }
}

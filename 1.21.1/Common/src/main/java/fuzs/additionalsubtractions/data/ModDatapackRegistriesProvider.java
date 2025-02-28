package fuzs.additionalsubtractions.data;

import fuzs.additionalsubtractions.init.ModSoundEvents;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModDatapackRegistriesProvider extends AbstractDatapackRegistriesProvider {

    public ModDatapackRegistriesProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBootstrap(RegistryBoostrapConsumer consumer) {
        consumer.add(Registries.ENCHANTMENT, ModDatapackRegistriesProvider::bootstrapEnchantments);
        consumer.add(Registries.JUKEBOX_SONG, ModDatapackRegistriesProvider::bootstrapJukeboxSongs);
    }

    static void bootstrapEnchantments(BootstrapContext<Enchantment> context) {

    }

    static void bootstrapJukeboxSongs(BootstrapContext<JukeboxSong> context) {
        registerJukeboxSong(context,
                ModSoundEvents.ZERO_THREE_ZERO_EIGHT_JUKEBOX_SONG,
                ModSoundEvents.MUSIC_DISC_0308_SOUND_EVENT,
                83.0F,
                15);
        registerJukeboxSong(context,
                ModSoundEvents.ONE_ZERO_ZERO_SEVEN_JUKEBOX_SONG,
                ModSoundEvents.MUSIC_DISC_1007_SOUND_EVENT,
                102.0F,
                15);
        registerJukeboxSong(context,
                ModSoundEvents.ONE_FIVE_ZERO_SEVEN_JUKEBOX_SONG,
                ModSoundEvents.MUSIC_DISC_1507_SOUND_EVENT,
                214.0F,
                15);
    }
}

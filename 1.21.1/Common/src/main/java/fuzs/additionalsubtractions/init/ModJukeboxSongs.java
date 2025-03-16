package fuzs.additionalsubtractions.init;

import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class ModJukeboxSongs {
    public static final ResourceKey<JukeboxSong> ZERO_THREE_ZERO_EIGHT_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "0308");
    public static final ResourceKey<JukeboxSong> ONE_ZERO_ZERO_SEVEN_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "1007");
    public static final ResourceKey<JukeboxSong> ONE_FIVE_ZERO_SEVEN_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "1507");

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        AbstractDatapackRegistriesProvider.registerJukeboxSong(context,
                ZERO_THREE_ZERO_EIGHT_JUKEBOX_SONG,
                ModSoundEvents.MUSIC_DISC_0308_SOUND_EVENT,
                83.0F,
                15);
        AbstractDatapackRegistriesProvider.registerJukeboxSong(context,
                ONE_ZERO_ZERO_SEVEN_JUKEBOX_SONG,
                ModSoundEvents.MUSIC_DISC_1007_SOUND_EVENT,
                102.0F,
                15);
        AbstractDatapackRegistriesProvider.registerJukeboxSong(context,
                ONE_FIVE_ZERO_SEVEN_JUKEBOX_SONG,
                ModSoundEvents.MUSIC_DISC_1507_SOUND_EVENT,
                214.0F,
                15);
    }
}

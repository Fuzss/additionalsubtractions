package fuzs.additionalsubtractions.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class ModSoundEvents {
    public static final Holder.Reference<SoundEvent> MUSIC_DISC_0308_SOUND_EVENT = ModRegistry.REGISTRIES.registerSoundEvent(
            "music_disc.0308");
    public static final Holder.Reference<SoundEvent> MUSIC_DISC_1007_SOUND_EVENT = ModRegistry.REGISTRIES.registerSoundEvent(
            "music_disc.1007");
    public static final Holder.Reference<SoundEvent> MUSIC_DISC_1507_SOUND_EVENT = ModRegistry.REGISTRIES.registerSoundEvent(
            "music_disc.1507");

    public static final ResourceKey<JukeboxSong> ZERO_THREE_ZERO_EIGHT_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "0308");
    public static final ResourceKey<JukeboxSong> ONE_ZERO_ZERO_SEVEN_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "1007");
    public static final ResourceKey<JukeboxSong> ONE_FIVE_ZERO_SEVEN_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "1507");

    public static void bootstrap() {
        // NO-OP
    }
}

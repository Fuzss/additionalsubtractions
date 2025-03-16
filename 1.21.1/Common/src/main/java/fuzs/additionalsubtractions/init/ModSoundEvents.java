package fuzs.additionalsubtractions.init;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final Holder.Reference<SoundEvent> MUSIC_DISC_0308_SOUND_EVENT = ModRegistry.REGISTRIES.registerSoundEvent(
            "music_disc.0308");
    public static final Holder.Reference<SoundEvent> MUSIC_DISC_1007_SOUND_EVENT = ModRegistry.REGISTRIES.registerSoundEvent(
            "music_disc.1007");
    public static final Holder.Reference<SoundEvent> MUSIC_DISC_1507_SOUND_EVENT = ModRegistry.REGISTRIES.registerSoundEvent(
            "music_disc.1507");

    public static void bootstrap() {
        // NO-OP
    }
}

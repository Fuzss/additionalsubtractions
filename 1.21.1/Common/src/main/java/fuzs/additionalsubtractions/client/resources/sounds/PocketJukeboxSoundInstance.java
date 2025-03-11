package fuzs.additionalsubtractions.client.resources.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class PocketJukeboxSoundInstance extends AbstractTickableSoundInstance {
    private final Entity entity;

    public PocketJukeboxSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, RandomSource randomSource, boolean looping, int delay, SoundInstance.Attenuation attenuation, Entity entity, boolean relative) {
        super(soundEvent, source, randomSource);
        this.volume = volume;
        this.pitch = pitch;
        this.looping = looping;
        this.delay = delay;
        this.attenuation = attenuation;
        this.relative = relative;
        this.entity = entity;
    }

    public static PocketJukeboxSoundInstance forJukeboxSong(SoundEvent soundEvent, Entity entity) {
        return new PocketJukeboxSoundInstance(soundEvent,
                SoundSource.RECORDS,
                4.0F,
                1.0F,
                SoundInstance.createUnseededRandom(),
                false,
                0,
                SoundInstance.Attenuation.LINEAR,
                entity,
                false);
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }

    @Override
    public void tick() {
        if (!this.entity.isRemoved()) {
            this.x = this.entity.getX();
            this.y = this.entity.getY();
            this.z = this.entity.getZ();
        } else {
            this.stop();
        }
    }
}

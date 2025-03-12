package fuzs.additionalsubtractions.world.item;

import com.google.common.util.concurrent.Runnables;
import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.network.ClientboundJukeboxSongMessage;
import fuzs.puzzleslib.api.network.v3.PlayerSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class PocketJukeboxSongPlayer extends JukeboxSongPlayer {
    private final UUID uuid;

    public PocketJukeboxSongPlayer(UUID uuid) {
        super(Runnables::doNothing, BlockPos.ZERO);
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PocketJukeboxSongPlayer jukeboxSongPlayer && jukeboxSongPlayer.uuid.equals(this.uuid);
    }

    public void play(LevelAccessor level, Entity entity, Holder<JukeboxSong> song) {
        Objects.requireNonNull(song, "song is null");
        this.song = song;
        this.ticksSinceSongStarted = 0L;
        if (level.isClientSide()) {
            ClientboundJukeboxSongMessage.playJukeboxSong(entity, this.uuid, this.song);
        } else {
            PlayerSet playerSet = entity instanceof ServerPlayer serverPlayer ? PlayerSet.nearPlayer(serverPlayer) :
                    PlayerSet.nearEntity(entity);
            AdditionalSubtractions.NETWORK.sendMessage(playerSet,
                    new ClientboundJukeboxSongMessage(entity.getId(), this.uuid, Optional.of(this.song)));
        }
    }

    public void stop(LevelAccessor level, Entity entity) {
        if (this.song != null) {
            this.song = null;
            this.ticksSinceSongStarted = 0L;
            level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, entity.blockPosition(), GameEvent.Context.of(entity));
            if (level.isClientSide()) {
                ClientboundJukeboxSongMessage.stopJukeboxSong(entity, this.uuid);
            } else {
                PlayerSet playerSet = entity instanceof ServerPlayer serverPlayer ? PlayerSet.nearPlayer(serverPlayer) :
                        PlayerSet.nearEntity(entity);
                AdditionalSubtractions.NETWORK.sendMessage(playerSet,
                        new ClientboundJukeboxSongMessage(entity.getId(), this.uuid, Optional.empty()));
            }
        }
    }

    public void tick(LevelAccessor level, Entity entity) {
        if (this.song != null) {
            if (this.song.value().hasFinished(this.ticksSinceSongStarted)) {
                this.stop(level, entity);
            } else {
                if (this.shouldEmitJukeboxPlayingEvent()) {
                    level.gameEvent(GameEvent.JUKEBOX_PLAY, entity.blockPosition(), GameEvent.Context.of(entity));
                    // allow particle spawning all around the entity up to one block away for a height of two blocks
                    int offset = entity.getRandom().nextInt(18);
                    spawnMusicParticles(level,
                            entity.blockPosition().offset(offset / 6 - 1, offset / 9 - 1, offset % 3 - 1));
                }

                this.ticksSinceSongStarted++;
            }
        }
    }
}

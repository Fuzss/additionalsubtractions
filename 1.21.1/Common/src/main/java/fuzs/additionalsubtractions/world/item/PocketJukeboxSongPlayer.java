package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.network.ClientboundJukeboxSongMessage;
import fuzs.additionalsubtractions.network.ClientboundJukeboxSongPlayingMessage;
import fuzs.puzzleslib.api.network.v3.PlayerSet;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapted from {@link net.minecraft.world.item.JukeboxSongPlayer}.
 */
public final class PocketJukeboxSongPlayer {

    private PocketJukeboxSongPlayer() {
        // NO-OP
    }

    public static void play(ItemStack itemStack, ServerLevel serverLevel, Entity entity, Optional<Holder<JukeboxSong>> optional) {
        itemStack.set(ModRegistry.POCKET_JUKEBOX_SONG_STARTED_TIME_DATA_COMPONENT_TYPE.value(),
                serverLevel.getGameTime());
        UUID uuid = itemStack.get(ModRegistry.POCKET_JUKEBOX_UUID_DATA_COMPONENT_TYPE.value());
        AdditionalSubtractions.NETWORK.sendMessage(PlayerSet.nearEntity(entity),
                new ClientboundJukeboxSongMessage(entity.getId(), uuid, optional));
    }

    public static void stop(ItemStack itemStack, ServerLevel serverLevel, Entity entity, boolean hasFinished) {
        serverLevel.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, entity.blockPosition(), GameEvent.Context.of(entity));
        if (hasFinished) {
            itemStack.set(ModRegistry.POCKET_JUKEBOX_SONG_STARTED_TIME_DATA_COMPONENT_TYPE.value(), -1L);
        } else {
            itemStack.remove(ModRegistry.POCKET_JUKEBOX_SONG_STARTED_TIME_DATA_COMPONENT_TYPE.value());
        }
        UUID uuid = itemStack.get(ModRegistry.POCKET_JUKEBOX_UUID_DATA_COMPONENT_TYPE.value());
        AdditionalSubtractions.NETWORK.sendMessage(PlayerSet.nearEntity(entity),
                new ClientboundJukeboxSongMessage(entity.getId(), uuid, Optional.empty()));
    }

    public static void tick(ItemStack itemStack, ServerLevel serverLevel, Entity entity, long ticksSinceSongStarted) {
        if (ticksSinceSongStarted % 20L == 0L) {
            serverLevel.gameEvent(GameEvent.JUKEBOX_PLAY, entity.blockPosition(), GameEvent.Context.of(entity));
            serverLevel.sendParticles(ParticleTypes.NOTE,
                    entity.getRandomX(1.0),
                    entity.getRandomY(),
                    entity.getRandomZ(1.0),
                    0,
                    serverLevel.getRandom().nextInt(4) / 24.0,
                    0.0,
                    0.0,
                    1.0);
        }
        UUID uuid = itemStack.get(ModRegistry.POCKET_JUKEBOX_UUID_DATA_COMPONENT_TYPE.value());
        AdditionalSubtractions.NETWORK.sendMessage(PlayerSet.nearEntity(entity),
                new ClientboundJukeboxSongPlayingMessage(entity.getId(), uuid));
    }
}

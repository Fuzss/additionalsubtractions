package fuzs.additionalsubtractions.network;

import com.google.common.collect.MapMaker;
import fuzs.additionalsubtractions.client.resources.sounds.PocketJukeboxSoundInstance;
import fuzs.puzzleslib.api.network.v3.ClientMessageListener;
import fuzs.puzzleslib.api.network.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.JukeboxSong;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public record ClientboundJukeboxSongMessage(int entityId,
                                            UUID uuid,
                                            Optional<Holder<JukeboxSong>> jukeboxSong) implements ClientboundMessage<ClientboundJukeboxSongMessage> {
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundJukeboxSongMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ClientboundJukeboxSongMessage::entityId,
            UUIDUtil.STREAM_CODEC,
            ClientboundJukeboxSongMessage::uuid,
            ByteBufCodecs.holderRegistry(Registries.JUKEBOX_SONG).apply(ByteBufCodecs::optional),
            ClientboundJukeboxSongMessage::jukeboxSong,
            ClientboundJukeboxSongMessage::new);
    static final Map<UUID, PocketJukeboxSoundInstance> PLAYING_POCKET_JUKEBOX_SONGS = new MapMaker().concurrencyLevel(1)
            .weakValues()
            .makeMap();

    @Override
    public ClientMessageListener<ClientboundJukeboxSongMessage> getHandler() {
        return new ClientMessageListener<>() {
            @Override
            public void handle(ClientboundJukeboxSongMessage message, Minecraft minecraft, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
                Entity entity = level.getEntity(message.entityId);
                if (entity != null) {
                    if (message.jukeboxSong.isPresent()) {
                        playJukeboxSong(minecraft, entity, message.uuid, message.jukeboxSong.get());
                    } else {
                        stopJukeboxSong(minecraft, message.uuid);
                    }
                }
            }

            private static void playJukeboxSong(Minecraft minecraft, Entity entity, UUID uuid, Holder<JukeboxSong> jukeboxSong) {
                stopJukeboxSong(minecraft, uuid);
                SoundEvent soundEvent = jukeboxSong.value().soundEvent().value();
                PocketJukeboxSoundInstance soundInstance = PocketJukeboxSoundInstance.forJukeboxSong(soundEvent,
                        entity);
                PLAYING_POCKET_JUKEBOX_SONGS.put(uuid, soundInstance);
                minecraft.getSoundManager().play(soundInstance);
                minecraft.gui.setNowPlaying(jukeboxSong.value().description());
            }

            private static void stopJukeboxSong(Minecraft minecraft, UUID uuid) {
                PocketJukeboxSoundInstance soundInstance = PLAYING_POCKET_JUKEBOX_SONGS.remove(uuid);
                if (soundInstance != null) {
                    minecraft.getSoundManager().stop(soundInstance);
                }
            }
        };
    }
}

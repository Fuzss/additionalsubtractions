package fuzs.additionalsubtractions.network;

import com.google.common.collect.ImmutableMap;
import fuzs.additionalsubtractions.client.resources.sounds.PocketJukeboxSoundInstance;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.network.v3.ClientMessageListener;
import fuzs.puzzleslib.api.network.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.JukeboxSong;

import java.util.*;

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

    @Override
    public ClientMessageListener<ClientboundJukeboxSongMessage> getHandler() {
        return new ClientMessageListenerImpl();
    }

    public static void playJukeboxSong(Entity entity, UUID uuid, Holder<JukeboxSong> jukeboxSong) {
        ClientMessageListenerImpl.playJukeboxSong(Minecraft.getInstance(), entity, uuid, jukeboxSong);
    }

    public static void stopJukeboxSong(Entity entity, UUID uuid) {
        ClientMessageListenerImpl.stopJukeboxSong(Minecraft.getInstance(), entity, uuid);
    }

    private static class ClientMessageListenerImpl extends ClientMessageListener<ClientboundJukeboxSongMessage> {

        @Override
        public void handle(ClientboundJukeboxSongMessage message, Minecraft minecraft, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
            Entity entity = level.getEntity(message.entityId);
            if (entity != null) {
                if (message.jukeboxSong.isPresent()) {
                    playJukeboxSong(minecraft, entity, message.uuid, message.jukeboxSong.get());
                } else {
                    stopJukeboxSong(minecraft, entity, message.uuid);
                }
            }
        }

        private static void playJukeboxSong(Minecraft minecraft, Entity entity, UUID uuid, Holder<JukeboxSong> jukeboxSong) {
            stopJukeboxSong(minecraft, entity, uuid);
            SoundEvent soundEvent = jukeboxSong.value().soundEvent().value();
            SoundInstance soundInstance = PocketJukeboxSoundInstance.forJukeboxSong(soundEvent, entity);
            Map<UUID, SoundInstance> map = ModRegistry.PLAYING_POCKET_JUKEBOX_SONGS_ATTACHMENT_TYPE.getOrDefault(entity,
                    Collections.emptyMap());
            map = ImmutableMap.<UUID, SoundInstance>builder().putAll(map).put(uuid, soundInstance).buildKeepingLast();
            ModRegistry.PLAYING_POCKET_JUKEBOX_SONGS_ATTACHMENT_TYPE.set(entity, map);
            minecraft.getSoundManager().play(soundInstance);
            minecraft.gui.setNowPlaying(jukeboxSong.value().description());
        }

        private static void stopJukeboxSong(Minecraft minecraft, Entity entity, UUID uuid) {
            Map<UUID, SoundInstance> map = ModRegistry.PLAYING_POCKET_JUKEBOX_SONGS_ATTACHMENT_TYPE.getOrDefault(entity,
                    Collections.emptyMap());
            if (map.containsKey(uuid)) {
                map = new HashMap<>(map);
                minecraft.getSoundManager().stop(map.remove(uuid));
                ModRegistry.PLAYING_POCKET_JUKEBOX_SONGS_ATTACHMENT_TYPE.set(entity, ImmutableMap.copyOf(map));
            }
        }
    }
}

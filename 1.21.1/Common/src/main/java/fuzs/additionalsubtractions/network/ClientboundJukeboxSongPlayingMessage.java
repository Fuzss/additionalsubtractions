package fuzs.additionalsubtractions.network;

import fuzs.additionalsubtractions.client.resources.sounds.PocketJukeboxSoundInstance;
import fuzs.puzzleslib.api.network.v3.ClientMessageListener;
import fuzs.puzzleslib.api.network.v3.ClientboundMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public record ClientboundJukeboxSongPlayingMessage(int entityId,
                                                   UUID uuid) implements ClientboundMessage<ClientboundJukeboxSongPlayingMessage> {
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundJukeboxSongPlayingMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ClientboundJukeboxSongPlayingMessage::entityId,
            UUIDUtil.STREAM_CODEC,
            ClientboundJukeboxSongPlayingMessage::uuid,
            ClientboundJukeboxSongPlayingMessage::new);

    @Override
    public ClientMessageListener<ClientboundJukeboxSongPlayingMessage> getHandler() {
        return new ClientMessageListener<>() {
            @Override
            public void handle(ClientboundJukeboxSongPlayingMessage message, Minecraft minecraft, ClientPacketListener handler, LocalPlayer player, ClientLevel level) {
                Entity entity = level.getEntity(message.entityId());
                if (entity != null && !entity.isRemoved()) {
                    PocketJukeboxSoundInstance soundInstance = ClientboundJukeboxSongMessage.PLAYING_POCKET_JUKEBOX_SONGS.get(
                            message.uuid());
                    if (soundInstance != null) {
                        soundInstance.setEntity(entity);
                    }
                }
            }
        };
    }
}

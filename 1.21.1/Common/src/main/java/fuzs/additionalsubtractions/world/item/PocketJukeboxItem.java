package fuzs.additionalsubtractions.world.item;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PocketJukeboxItem extends BundleItem {
    /**
     * This would have ideally been a volatile component on the item itself, but that can only be handled by the server
     * and would not work for the creative mode screen, since that is not synced.
     * <p>
     * So we have to use this hacky approach with a map for both client & server sides.
     */
    public static final Map<UUID, PocketJukeboxSongPlayer> POCKET_JUKEBOX_SONG_PLAYERS = new ConcurrentHashMap<>();

    public PocketJukeboxItem(Properties properties) {
        super(properties);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack) {
        // we need something to identify each item for the jukebox song players
        // a random uuid is ideal, there are so many it will never collide, vanilla uses this for entities also
        // there is no need to save this to the item though, everything related is reset upon restarting / relogging
        if (itemStack.getOrDefault(ModRegistry.POCKET_JUKEBOX_SONG_PLAYER_DATA_COMPONENT_TYPE.value(), Util.NIL_UUID)
                .equals(Util.NIL_UUID)) {
            itemStack.set(ModRegistry.POCKET_JUKEBOX_SONG_PLAYER_DATA_COMPONENT_TYPE.value(), UUID.randomUUID());
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slotId, boolean isSelected) {
        UUID uuid = itemStack.getOrDefault(ModRegistry.POCKET_JUKEBOX_SONG_PLAYER_DATA_COMPONENT_TYPE.value(),
                Util.NIL_UUID);
        PocketJukeboxSongPlayer jukeboxSongPlayer = POCKET_JUKEBOX_SONG_PLAYERS.get(uuid);
        if (jukeboxSongPlayer != null) {
            jukeboxSongPlayer.tick(level, entity);
        }
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction action, Player player) {
        if (!slot.hasItem() || JukeboxSong.fromStack(player.registryAccess(), slot.getItem()).isPresent()) {
            if (super.overrideStackedOnOther(itemStack, slot, action, player)) {
                this.triggerJukeboxSongPlayer(itemStack, player);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack itemStack, ItemStack itemStackOnMe, Slot slot, ClickAction action, Player player, SlotAccess slotAccess) {
        if (itemStackOnMe.isEmpty() || JukeboxSong.fromStack(player.registryAccess(), itemStackOnMe).isPresent()) {
            if (super.overrideOtherStackedOnMe(itemStack, itemStackOnMe, slot, action, player, slotAccess)) {
                this.triggerJukeboxSongPlayer(itemStack, player);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void triggerJukeboxSongPlayer(ItemStack itemStack, Player player) {
        UUID uuid = itemStack.get(ModRegistry.POCKET_JUKEBOX_SONG_PLAYER_DATA_COMPONENT_TYPE.value());
        if (uuid != null) {
            Optional<Holder<JukeboxSong>> optional = JukeboxSong.fromStack(player.registryAccess(),
                    getJukeboxPlayableItem(itemStack));
            if (optional.isPresent()) {
                PocketJukeboxSongPlayer jukeboxSongPlayer = POCKET_JUKEBOX_SONG_PLAYERS.computeIfAbsent(uuid,
                        PocketJukeboxSongPlayer::new);
                jukeboxSongPlayer.play(player.level(), player, optional.get());
            } else {
                PocketJukeboxSongPlayer jukeboxSongPlayer = POCKET_JUKEBOX_SONG_PLAYERS.remove(uuid);
                if (jukeboxSongPlayer != null) {
                    jukeboxSongPlayer.stop(player.level(), player);
                }
            }
        }
    }

    public static ItemStack getJukeboxPlayableItem(ItemStack itemStack) {
        BundleContents bundleContents = itemStack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        return bundleContents.isEmpty() ? ItemStack.EMPTY : bundleContents.items().iterator().next();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.empty();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        JukeboxPlayable jukeboxPlayable = getJukeboxPlayableItem(itemStack).get(DataComponents.JUKEBOX_PLAYABLE);
        if (jukeboxPlayable != null) {
            jukeboxPlayable.addToTooltip(context, tooltipComponents::add, tooltipFlag);
        } else {
            tooltipComponents.addAll(Proxy.INSTANCE.splitTooltipLines(getDescriptionComponent()));
        }
    }

    public static Component getDescriptionComponent() {
        return Component.translatable(ModItems.POCKET_JUKEBOX.value().getDescriptionId() + ".description")
                .withStyle(ChatFormatting.GRAY);
    }
}

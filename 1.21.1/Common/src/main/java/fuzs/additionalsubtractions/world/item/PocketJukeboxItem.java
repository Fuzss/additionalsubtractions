package fuzs.additionalsubtractions.world.item;

import com.google.common.collect.ImmutableSet;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.puzzleslib.api.core.v1.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Level;

import java.util.*;

public class PocketJukeboxItem extends BundleItem {

    public PocketJukeboxItem(Properties properties) {
        super(properties);
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack itemStack) {
        // we need something to identify each item for the jukebox song players
        // a random uuid is ideal, there are so many it will never collide, vanilla uses this for entities also
        // there is no need to save this to the item though, everything related is reset upon restarting / relogging
        if (itemStack.getOrDefault(ModRegistry.POCKET_JUKEBOX_UUID_DATA_COMPONENT_TYPE.value(), Util.NIL_UUID)
                .equals(Util.NIL_UUID)) {
            itemStack.set(ModRegistry.POCKET_JUKEBOX_UUID_DATA_COMPONENT_TYPE.value(), UUID.randomUUID());
        }
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (level instanceof ServerLevel serverLevel) {
            tick(itemStack, serverLevel, entity);
        }
    }

    public static void tick(ItemStack itemStack, ServerLevel serverLevel, Entity entity) {
        Long songStartedTime = itemStack.get(ModRegistry.POCKET_JUKEBOX_SONG_STARTED_TIME_DATA_COMPONENT_TYPE.value());
        Optional<Holder<JukeboxSong>> optional = JukeboxSong.fromStack(serverLevel.registryAccess(),
                getJukeboxPlayableItem(itemStack));
        if (songStartedTime != null) {
            if (optional.isPresent()) {
                if (songStartedTime != -1L) {
                    long ticksSinceSongStarted = serverLevel.getGameTime() - songStartedTime;
                    if (optional.get().value().hasFinished(ticksSinceSongStarted)) {
                        PocketJukeboxSongPlayer.stop(itemStack, serverLevel, entity, true);
                    } else {
                        PocketJukeboxSongPlayer.tick(itemStack, serverLevel, entity, ticksSinceSongStarted);
                    }
                }
            } else {
                PocketJukeboxSongPlayer.stop(itemStack, serverLevel, entity, false);
            }
        } else if (optional.isPresent()) {
            PocketJukeboxSongPlayer.play(itemStack, serverLevel, entity, optional);
        }
    }

    public static Collection<ItemStack> getPocketJukeboxFromEntity(Entity entity) {
        return switch (entity) {
            case ItemEntity itemEntity -> Collections.singleton(itemEntity.getItem());
            // only need to handle carried, normal inventory is ticked by vanilla
            // decided against also ticking other slots in open container menus
            case Player player -> Collections.singleton(player.containerMenu.getCarried());
            case Mob mob -> ImmutableSet.copyOf(mob.getHandSlots());
            default -> Collections.emptySet();
        };
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack itemStack, Slot slot, ClickAction action, Player player) {
        if (!slot.hasItem() || JukeboxSong.fromStack(player.registryAccess(), slot.getItem()).isPresent()) {
            return super.overrideStackedOnOther(itemStack, slot, action, player);
        } else {
            return false;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack itemStack, ItemStack itemStackOnMe, Slot slot, ClickAction action, Player player, SlotAccess slotAccess) {
        if (itemStackOnMe.isEmpty() || JukeboxSong.fromStack(player.registryAccess(), itemStackOnMe).isPresent()) {
            return super.overrideOtherStackedOnMe(itemStack, itemStackOnMe, slot, action, player, slotAccess);
        } else {
            return false;
        }
    }

    public static ItemStack getJukeboxPlayableItem(ItemStack itemStack) {
        BundleContents bundleContents = itemStack.getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);
        return bundleContents.isEmpty() ? ItemStack.EMPTY : bundleContents.items().iterator().next();
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return false;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
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

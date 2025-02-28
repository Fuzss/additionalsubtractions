package fuzs.additionalsubtractions.client.handler;

import fuzs.additionalsubtractions.init.ModItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class CrossbowScopingHandler {
    private static boolean isCurrentlyScoping;

    public static boolean isCurrentlyScoping() {
        return isCurrentlyScoping;
    }

    public static boolean isPlayerScoping(Player player) {
        return player.isShiftKeyDown() && player.isHolding(ModItems.CROSSBOW_WITH_SPYGLASS.value());
    }

    public static void onEndPlayerTick(Player player) {
        if (player instanceof LocalPlayer) {
            boolean isScoping = isPlayerScoping(player);
            if (isScoping != isCurrentlyScoping()) {
                if (isScoping) {
                    player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
                } else {
                    player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
                }
            }
            isCurrentlyScoping = isScoping;
        }
    }
}

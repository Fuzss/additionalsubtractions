package fuzs.additionalsubtractions.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
abstract class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "itemUsed", at = @At("HEAD"), cancellable = true)
    public void itemUsed(InteractionHand interactionHand, CallbackInfo callback) {
        // don't play the reequip animation when beginning to use an item, like shield or bow
        if (this.minecraft.player.isUsingItem() && this.minecraft.player.getUsedItemHand() == interactionHand) {
            if (this.minecraft.player.getUseItem().getItem() instanceof CrossbowItem) {
                callback.cancel();
            }
        }
    }
}

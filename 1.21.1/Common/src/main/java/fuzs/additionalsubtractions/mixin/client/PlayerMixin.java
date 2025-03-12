package fuzs.additionalsubtractions.mixin.client;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.world.item.CrossbowWithSpyglassItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "isScoping", at = @At("HEAD"), cancellable = true)
    public void isScoping(CallbackInfoReturnable<Boolean> callback) {
        if (this.isUsingItem() && this.getUseItem().is(ModItems.CROSSBOW_WITH_SPYGLASS)) {
            if (((CrossbowWithSpyglassItem) this.getUseItem().getItem()).isScoping(this.level(),
                    this,
                    this.getUseItem(),
                    this.getUseItemRemainingTicks())) {
                callback.setReturnValue(true);
            }
        }
    }
}


package fuzs.additionalsubtractions.fabric.mixin.client;

import fuzs.additionalsubtractions.client.handler.FireworkExplosionShapeClientHelper;
import fuzs.additionalsubtractions.init.ModEnumConstants;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.FireworkExplosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FireworkParticles.Starter.class)
abstract class FireworkParticles$StarterFabricMixin extends NoRenderParticle {
    @Shadow
    private int life;
    @Shadow
    @Final
    private List<FireworkExplosion> explosions;

    protected FireworkParticles$StarterFabricMixin(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo callback) {
        if (this.life % 2 == 0 && this.life / 2 < this.explosions.size()) {
            int i = this.life / 2;
            FireworkExplosion fireworkExplosion = this.explosions.get(i);
            boolean trail = fireworkExplosion.hasTrail();
            boolean twinkle = fireworkExplosion.hasTwinkle();
            IntList colors = fireworkExplosion.colors();
            IntList fadeColors = fireworkExplosion.fadeColors();
            if (colors.isEmpty()) {
                colors = IntList.of(DyeColor.BLACK.getFireworkColor());
            }
            if (fireworkExplosion.shape() == ModEnumConstants.BOLT_EXPLOSION_SHAPE) {
                FireworkExplosionShapeClientHelper.createBoltParticleShape(FireworkParticles.Starter.class.cast(this),
                        colors,
                        fadeColors,
                        trail,
                        twinkle);
            } else if (fireworkExplosion.shape() == ModEnumConstants.HEART_EXPLOSION_SHAPE) {
                FireworkExplosionShapeClientHelper.createHeartParticleShape(FireworkParticles.Starter.class.cast(this),
                        colors,
                        fadeColors,
                        trail,
                        twinkle);
            }
        }
    }
}

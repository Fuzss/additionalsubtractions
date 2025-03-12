package fuzs.additionalsubtractions.mixin;

import fuzs.additionalsubtractions.world.entity.item.GravitationalBlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FallingBlockEntity.class)
abstract class FallingBlockEntityMixin extends Entity {
    @Shadow
    public BlockState blockState;

    public FallingBlockEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 0)
    public boolean tick$0(boolean canBlockStateBeHydrated) {
        // just a hack to get the onCeiling check in here to work just as onGround does, so we do not have to copy the full vanilla implementation
        return canBlockStateBeHydrated || this instanceof GravitationalBlockEntity gravitationalBlockEntity &&
                gravitationalBlockEntity.onCeiling();
    }

    @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 1)
    public boolean tick(boolean canBlockStateBeHydrated) {
        // just a hack to get the onCeiling check in here to work just as onGround does, so we do not have to copy the full vanilla implementation
        return canBlockStateBeHydrated || this instanceof GravitationalBlockEntity gravitationalBlockEntity &&
                gravitationalBlockEntity.onCeiling();
    }
}

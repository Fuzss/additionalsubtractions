package fuzs.additionalsubtractions.mixin;

import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.world.entity.vehicle.MinecartCopperHopper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractMinecart.class)
abstract class AbstractMinecartMixin extends VehicleEntity {

    public AbstractMinecartMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(method = "createMinecart", at = @At("STORE"))
    private static AbstractMinecart createMinecart(AbstractMinecart abstractMinecart, ServerLevel serverLevel, double x, double y, double z, AbstractMinecart.Type type, ItemStack itemStack, @Nullable Player player) {
        return itemStack.is(ModItems.COPPER_HOPPER_MINECART) ? new MinecartCopperHopper(serverLevel, x, y, z) :
                abstractMinecart;
    }
}

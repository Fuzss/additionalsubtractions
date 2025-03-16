package fuzs.additionalsubtractions.fabric.mixin;

import fuzs.additionalsubtractions.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractMinecart.class)
abstract class AbstractMinecartFabricMixin extends VehicleEntity {

    public AbstractMinecartFabricMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(method = "moveAlongTrack", at = @At("STORE"), ordinal = 0)
    protected boolean moveAlongTrack$0(boolean isPoweredRailPowered, BlockPos blockPos, BlockState blockState) {
        if (blockState.is(ModBlocks.COPPER_RAIL)) {
            return blockState.getValue(PoweredRailBlock.POWERED);
        } else {
            return isPoweredRailPowered;
        }
    }

    @ModifyVariable(method = "moveAlongTrack", at = @At("STORE"), ordinal = 1)
    protected boolean moveAlongTrack$1(boolean isPoweredRailNotPowered, BlockPos blockPos, BlockState blockState) {
        if (blockState.is(ModBlocks.COPPER_RAIL)) {
            return !blockState.getValue(PoweredRailBlock.POWERED);
        } else {
            return isPoweredRailNotPowered;
        }
    }
}

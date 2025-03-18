package fuzs.additionalsubtractions.mixin;

import fuzs.additionalsubtractions.init.ModBlocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CarvedPumpkinBlock.class)
abstract class CarvedPumpkinBlockMixin extends HorizontalDirectionalBlock {

    protected CarvedPumpkinBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "lambda$static$0(Lnet/minecraft/world/level/block/state/BlockState;)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void isPumpkinState(BlockState blockState, CallbackInfoReturnable<Boolean> callback) {
        if (blockState != null &&
                (blockState.is(ModBlocks.SOUL_JACK_O_LANTERN) || blockState.is(ModBlocks.REDSTONE_JACK_O_LANTERN))) {
            callback.setReturnValue(true);
        }
    }
}

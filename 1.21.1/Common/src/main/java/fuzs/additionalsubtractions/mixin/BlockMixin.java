package fuzs.additionalsubtractions.mixin;

import fuzs.additionalsubtractions.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
abstract class BlockMixin extends BlockBehaviour {

    public BlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "isExceptionForConnection", at = @At("HEAD"), cancellable = true)
    private static void isExceptionForConnection(BlockState state, CallbackInfoReturnable<Boolean> callback) {
        if (state.is(ModBlocks.SOUL_JACK_O_LANTERN) || state.is(ModBlocks.REDSTONE_JACK_O_LANTERN)) {
            callback.setReturnValue(true);
        }
    }
}

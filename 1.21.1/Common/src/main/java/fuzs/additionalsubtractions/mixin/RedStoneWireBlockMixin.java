package fuzs.additionalsubtractions.mixin;

import fuzs.additionalsubtractions.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
abstract class RedStoneWireBlockMixin extends Block {

    public RedStoneWireBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "canSurviveOn", at = @At("HEAD"), cancellable = true)
    private void canSurviveOn(BlockGetter level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> callback) {
        if (state.is(ModBlocks.COPPER_HOPPER)) callback.setReturnValue(true);
    }
}

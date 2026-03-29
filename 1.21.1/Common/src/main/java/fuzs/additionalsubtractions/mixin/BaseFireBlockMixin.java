package fuzs.additionalsubtractions.mixin;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.world.level.block.ModFireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFireBlock.class)
abstract class BaseFireBlockMixin extends Block {

    public BaseFireBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "entityInside", at = @At("HEAD"))
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo callback) {
        if (BaseFireBlock.class.cast(this) == Blocks.FIRE && entity instanceof ItemEntity itemEntity) {
            if (itemEntity.getItem().is(ModItems.COPPER_PATINA) && !itemEntity.fireImmune() &&
                    !itemEntity.isRemoved()) {
                // deal large damage amount, so the item is guaranteed to be removed, so that only one fire can be converted
                if (entity.hurt(level.damageSources().inFire(), 1000.0F)) {
                    BlockState blockState = ModBlocks.COPPER_SULFATE_FIRE.value().defaultBlockState();
                    blockState = ModFireBlock.copyBlockStateProperties(state, blockState);
                    level.setBlock(pos, blockState, 3);
                    callback.cancel();
                }
            }
        }
    }
}

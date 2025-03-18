package fuzs.additionalsubtractions.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.world.level.block.entity.CopperHopperBlockEntity;
import fuzs.puzzleslib.api.block.v1.entity.TickingEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CopperHopperBlock extends HopperBlock implements TickingEntityBlock<CopperHopperBlockEntity> {
    public static final MapCodec<HopperBlock> CODEC = simpleCodec(CopperHopperBlock::new);

    public CopperHopperBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<HopperBlock> codec() {
        return CODEC;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        // NO-OP
    }

    @Override
    public BlockEntityType<? extends CopperHopperBlockEntity> getBlockEntityType() {
        return ModBlocks.COPPER_HOPPER_BLOCK_ENTITY_TYPE.value();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TickingEntityBlock.super.newBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return TickingEntityBlock.super.getTicker(level, state, blockEntityType);
    }
}

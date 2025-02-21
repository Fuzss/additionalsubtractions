package dqu.additionaladditions.block;

import com.mojang.serialization.MapCodec;
import dqu.additionaladditions.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowStickBlock extends FallingBlock {
    public static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0);
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");
    public static final MapCodec<GlowStickBlock> CODEC = simpleCodec(GlowStickBlock::new);

    public GlowStickBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FLIPPED, false));
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(FLIPPED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public Item asItem() {
        return ModItems.GLOW_STICK.value();
    }
}

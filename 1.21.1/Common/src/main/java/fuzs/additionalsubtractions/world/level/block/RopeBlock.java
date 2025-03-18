package fuzs.additionalsubtractions.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import fuzs.puzzleslib.api.shape.v1.ShapesHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Quaternionf;

import java.util.Map;

public class RopeBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<RopeBlock> CODEC = simpleCodec(RopeBlock::new);
    public static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    public static final VoxelShape SIDE_SHAPE = Block.box(7.0, 13.0, 0.0, 9.0, 15.0, 6.0);
    public static final Map<Direction, VoxelShape> SIDE_SHAPES = Maps.immutableEnumMap(ImmutableMap.of(Direction.NORTH,
            SIDE_SHAPE,
            Direction.SOUTH,
            ShapesHelper.rotate(new Quaternionf().rotationY(Mth.PI), SIDE_SHAPE),
            Direction.WEST,
            ShapesHelper.rotate(new Quaternionf().rotationY(Mth.HALF_PI), SIDE_SHAPE),
            Direction.EAST,
            ShapesHelper.rotate(new Quaternionf().rotationY(-Mth.HALF_PI), SIDE_SHAPE)));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;

    protected final VoxelShape[] shapeByIndex;
    private final Object2IntMap<BlockState> stateToIndex = new Object2IntOpenHashMap<>();

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition()
                .any()
                .setValue(WATERLOGGED, Boolean.FALSE)
                .setValue(NORTH, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE));
        this.shapeByIndex = this.makeShapes();
        for (BlockState blockState : this.stateDefinition.getPossibleStates()) {
            this.getAABBIndex(blockState);
        }
    }

    @Override
    public MapCodec<? extends RopeBlock> codec() {
        return CODEC;
    }

    private VoxelShape[] makeShapes() {
        VoxelShape[] shapeByIndex = new VoxelShape[16];
        for (int i = 0; i < shapeByIndex.length; i++) {
            VoxelShape voxelShape = SHAPE;
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if ((indexFor(direction) & i) == indexFor(direction)) {
                    voxelShape = Shapes.or(voxelShape, SIDE_SHAPES.get(direction));
                }
            }
            shapeByIndex[i] = voxelShape;
        }
        return shapeByIndex;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.shapeByIndex[this.getAABBIndex(state)];
    }

    protected static int indexFor(Direction facing) {
        return 1 << facing.get2DDataValue();
    }

    protected int getAABBIndex(BlockState blockState) {
        return this.stateToIndex.computeIfAbsent(blockState, (BlockState missingState) -> {
            int i = 0;
            if (missingState.getValue(NORTH)) {
                i |= indexFor(Direction.NORTH);
            }

            if (missingState.getValue(EAST)) {
                i |= indexFor(Direction.EAST);
            }

            if (missingState.getValue(SOUTH)) {
                i |= indexFor(Direction.SOUTH);
            }

            if (missingState.getValue(WEST)) {
                i |= indexFor(Direction.WEST);
            }

            return i;
        });
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return useContext.getItemInHand().is(this.asItem());
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        if (this.hasSupportAbove(level, blockPos)) {
            return true;
        } else {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (this.isFaceSturdy(level, blockPos, direction)) {
                    return true;
                }
            }

            return false;
        }
    }

    protected boolean hasSupportAbove(LevelReader level, BlockPos blockPos) {
        return this.hasSupportAbove(level, level.getBlockState(blockPos.above()), blockPos.above());
    }

    protected boolean hasSupportAbove(LevelReader level, BlockState blockState, BlockPos blockPos) {
        return blockState.is(this) || this.isFaceSturdy(level, blockState, blockPos, Direction.UP, SupportType.CENTER);
    }

    protected boolean isFaceSturdy(LevelReader level, BlockPos blockPos, Direction direction) {
        return this.isFaceSturdy(level,
                level.getBlockState(blockPos.relative(direction)),
                blockPos.relative(direction),
                direction,
                SupportType.FULL);
    }

    protected boolean isFaceSturdy(LevelReader level, BlockState blockState, BlockPos blockPos, Direction direction, SupportType supportType) {
        return blockState.isFaceSturdy(level, blockPos, direction.getOpposite(), supportType);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = level.getFluidState(blockPos);
        BlockState blockState = super.getStateForPlacement(context)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        if (!this.hasSupportAbove(level, blockPos)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (this.isFaceSturdy(level, blockPos, direction)) {
                    blockState = blockState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction), Boolean.TRUE);
                }
            }
        }
        return blockState;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }

        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction == Direction.UP) {
            boolean hasSupportAbove = this.hasSupportAbove(level, neighborState, neighborPos);
            for (Direction horizontalDirection : Direction.Plane.HORIZONTAL) {
                state = state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(horizontalDirection),
                        !hasSupportAbove && this.isFaceSturdy(level, pos, horizontalDirection));
            }
            return state;
        } else if (direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            return state.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(direction),
                    !this.hasSupportAbove(level, pos) &&
                            this.isFaceSturdy(level, neighborState, neighborPos, direction, SupportType.FULL));
        } else {
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}

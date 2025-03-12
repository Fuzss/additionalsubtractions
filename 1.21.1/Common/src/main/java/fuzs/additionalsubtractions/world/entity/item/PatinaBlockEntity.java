package fuzs.additionalsubtractions.world.entity.item;

import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.world.level.block.PatinaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class PatinaBlockEntity extends FallingBlockEntity implements GravitationalBlockEntity {
    public boolean verticalCollisionAbove;

    public PatinaBlockEntity(EntityType<? extends FallingBlockEntity> entityType, Level level) {
        super(entityType, level);
    }

    private PatinaBlockEntity(Level level, double x, double y, double z, BlockState state) {
        this(ModRegistry.PATINA_BLOCK_ENTITY_TYPE.value(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    public static FallingBlockEntity fall(Level level, BlockPos pos, BlockState blockState) {
        FallingBlockEntity fallingBlockEntity = new PatinaBlockEntity(level,
                (double) pos.getX() + 0.5,
                pos.getY(),
                (double) pos.getZ() + 0.5,
                blockState.hasProperty(PatinaBlock.POWERED) ?
                        blockState.setValue(BlockStateProperties.POWERED, Boolean.FALSE) : blockState);
        level.setBlock(pos, blockState.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(fallingBlockEntity);
        return fallingBlockEntity;
    }

    @Override
    protected double getDefaultGravity() {
        return super.getDefaultGravity() * (this.isInverted() ? -1.0 : 1.0);
    }

    private boolean isInverted() {
        return this.getBlockState().getOptionalValue(PatinaBlock.INVERTED).orElse(false);
    }

    @Override
    public void move(MoverType type, Vec3 pos) {
        super.move(type, pos);
        this.verticalCollisionAbove = this.verticalCollision && pos.y > 0.0;
    }

    @Override
    public boolean onCeiling() {
        return this.isInverted() && this.verticalCollisionAbove;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("OnCeiling", this.verticalCollisionAbove);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.verticalCollisionAbove = compound.getBoolean("OnCeiling");
    }
}

package fuzs.additionalsubtractions.world.level.block.entity;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.puzzleslib.api.block.v1.entity.TickingBlockEntity;
import fuzs.puzzleslib.api.container.v1.ListBackedContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PedestalBlockEntity extends BlockEntity implements ListBackedContainer, TickingBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    public final RandomSource random = RandomSource.create();
    public final float bobOffs = this.random.nextFloat() * (float) Math.PI * 2.0F;
    private int age;

    public PedestalBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.PEDESTAL_BLOCK_ENTITY.value(), pos, blockState);
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public void clientTick() {
        this.age++;
    }

    public float getSpin(float partialTicks) {
        return (this.getAge() + partialTicks) / 20.0F + this.bobOffs;
    }

    @Override
    public NonNullList<ItemStack> getContainerItems() {
        return this.items;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items, registries);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }
}

package fuzs.additionalsubtractions.world.level.block.entity;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.world.inventory.TimerMenu;
import fuzs.additionalsubtractions.world.level.block.TimerBlock;
import fuzs.puzzleslib.api.block.v1.entity.TickingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TimerBlockEntity extends BlockEntity implements MenuProvider, TickingBlockEntity {
    private static final String KEY_DELAY = AdditionalSubtractions.id("delay").toString();
    public static final int MIN_INTERVAL = 4;
    public static final int MAX_INTERVAL = 72_000;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return index == 0 ? TimerBlockEntity.this.delay : 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                TimerBlockEntity.this.setDelay(value);
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    private int delay = 40;
    private int age;

    public TimerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlocks.TIMER_BLOCK_ENTITY_TYPE.value(), blockPos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return ModBlocks.TIMER.value().getName();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.delay = tag.getInt(KEY_DELAY);
        this.age = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(KEY_DELAY, this.delay);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public int getDelay() {
        return this.delay;
    }

    private void setDelay(int delay) {
        delay = Mth.clamp(delay, MIN_INTERVAL, MAX_INTERVAL);
        if (this.delay != delay) {
            this.delay = delay;
            if (this.level != null) {
                this.setChanged();
                this.level.setBlock(this.getBlockPos(),
                        this.getBlockState()
                                .setValue(TimerBlock.POWERED, Boolean.TRUE)
                                .setValue(TimerBlock.LOCKED, Boolean.TRUE),
                        2);
                ((TimerBlock) this.getBlockState().getBlock()).clearScheduledTicks(this.level, this.getBlockPos());
                this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 1);
            }
        }
    }

    public float getRotationAngle(float partialTick) {
        float rotationAngle = 180.0F;
        if (!this.getBlockState().getValue(TimerBlock.POWERED)) {
            float animationProgress = Mth.clamp((this.age + partialTick) / this.delay, 0.0F, 1.0F);
            rotationAngle += animationProgress * -360.0F;
        }
        return rotationAngle % 360.0F;
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new TimerMenu(containerId, this, this.dataAccess);
    }

    @Override
    public void clientTick() {
        if (this.getBlockState().getValue(TimerBlock.POWERED) || this.getBlockState().getValue(TimerBlock.LOCKED)) {
            this.age = 0;
        } else {
            this.age++;
        }
    }
}

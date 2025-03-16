package fuzs.additionalsubtractions.world.entity.vehicle;

import fuzs.additionalsubtractions.init.ModBlocks;
import fuzs.additionalsubtractions.init.ModItems;
import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.world.level.block.entity.CopperHopperBlockEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MinecartCopperHopper extends MinecartHopper {

    public MinecartCopperHopper(EntityType<? extends MinecartHopper> entityType, Level level) {
        super(entityType, level);
    }

    public MinecartCopperHopper(Level level, double x, double y, double z) {
        super(ModRegistry.COPPER_HOPPER_MINECART_ENTITY_TYPE.value(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public BlockState getDefaultDisplayBlockState() {
        return ModBlocks.COPPER_HOPPER.value().defaultBlockState();
    }

    @Override
    public boolean suckInItems() {
        return CopperHopperBlockEntity.suckInItemsFromContainer(this.level(), this);
    }

    @Override
    protected Item getDropItem() {
        return ModItems.COPPER_HOPPER_MINECART.value();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.COPPER_HOPPER_MINECART);
    }
}

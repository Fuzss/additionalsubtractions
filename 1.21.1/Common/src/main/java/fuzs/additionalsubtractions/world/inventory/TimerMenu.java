package fuzs.additionalsubtractions.world.inventory;

import fuzs.additionalsubtractions.init.ModRegistry;
import fuzs.additionalsubtractions.world.level.block.entity.TimerBlockEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class TimerMenu extends AbstractContainerMenu {
    private final TimerBlockEntity timer;
    private final ContainerData timerData;

    public TimerMenu(int containerId, Inventory inventory) {
        this(containerId, null, new SimpleContainerData(1));
    }

    public TimerMenu(int containerId, TimerBlockEntity timer, ContainerData containerData) {
        super(ModRegistry.TIMER_MENU_TYPE.value(), containerId);
        this.timer = timer;
        this.timerData = containerData;
        this.addDataSlots(containerData);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.timer == null || !this.timer.isRemoved();
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (player.mayBuild()) {
            int delay = this.timerData.get(0);
            int newDelay = delay + this.getDelayChange(id);
            newDelay = Mth.clamp(newDelay, TimerBlockEntity.MIN_INTERVAL, TimerBlockEntity.MAX_INTERVAL);
            if (newDelay != delay) {
                this.setData(0, newDelay);
                return true;
            }
        }

        return false;
    }

    private int getDelayChange(int buttonId) {
        return (int) Math.signum(buttonId) * switch (Math.abs(buttonId)) {
            // in-game ticks
            case 1 -> 2;
            case 2 -> 20;
            case 3 -> 200;
            case 4 -> 1200;
            default -> 0;
        };
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setData(int id, int data) {
        super.setData(id, data);
        this.broadcastChanges();
    }

    public int getDelayTime() {
        return this.timerData.get(0);
    }
}

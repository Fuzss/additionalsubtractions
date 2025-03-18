package fuzs.additionalsubtractions.client.gui.screens.inventory;

import fuzs.additionalsubtractions.AdditionalSubtractions;
import fuzs.additionalsubtractions.world.inventory.TimerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class TimerScreen extends Screen implements MenuAccess<TimerMenu> {
    private static final ResourceLocation TEXTURE_LOCATION = AdditionalSubtractions.id("textures/gui/timer.png");
    public static final String KEY_TIMER_INTERVAL = "gui.timer.interval";
    public static final Component COMPONENT_MINUS_MILLI_100 = Component.translatable("gui.timer.minus.milli", 100);
    public static final Component COMPONENT_MINUS_SECOND_1 = Component.translatable("gui.timer.minus.second", 1);
    public static final Component COMPONENT_MINUS_SECOND_10 = Component.translatable("gui.timer.minus.second", 10);
    public static final Component COMPONENT_MINUS_MINUTE_1 = Component.translatable("gui.timer.minus.minute", 1);
    public static final Component COMPONENT_PLUS_MILLI_100 = Component.translatable("gui.timer.plus.milli", 100);
    public static final Component COMPONENT_PLUS_SECOND_1 = Component.translatable("gui.timer.plus.second", 1);
    public static final Component COMPONENT_PLUS_SECOND_10 = Component.translatable("gui.timer.plus.second", 10);
    public static final Component COMPONENT_PLUS_MINUTE_1 = Component.translatable("gui.timer.plus.minute", 1);

    private final TimerMenu timerMenu;

    public TimerScreen(TimerMenu timerMenu, Inventory inventory, Component component) {
        super(CommonComponents.EMPTY);
        this.timerMenu = timerMenu;
    }

    @Override
    public TimerMenu getMenu() {
        return this.timerMenu;
    }

    @Override
    public void onClose() {
        this.minecraft.player.closeContainer();
        super.onClose();
    }

    @Override
    protected void init() {
        super.init();
        this.addTimerIntervalButton(-1, COMPONENT_MINUS_MILLI_100);
        this.addTimerIntervalButton(-2, COMPONENT_MINUS_SECOND_1);
        this.addTimerIntervalButton(-3, COMPONENT_MINUS_SECOND_10);
        this.addTimerIntervalButton(-4, COMPONENT_MINUS_MINUTE_1);
        this.addTimerIntervalButton(1, COMPONENT_PLUS_MILLI_100);
        this.addTimerIntervalButton(2, COMPONENT_PLUS_SECOND_1);
        this.addTimerIntervalButton(3, COMPONENT_PLUS_SECOND_10);
        this.addTimerIntervalButton(4, COMPONENT_PLUS_MINUTE_1);
    }

    private void addTimerIntervalButton(int buttonId, Component component) {
        this.addRenderableWidget(Button.builder(component, (Button button) -> {
                    if (this.timerMenu.clickMenuButton(this.minecraft.player, buttonId)) {
                        this.minecraft.gameMode.handleInventoryButtonClick(this.timerMenu.containerId, buttonId);
                    }
                })
                .bounds(this.width / 2 - 83 + 42 * (Math.abs(buttonId) - 1),
                        this.height / 2 + (12 * Mth.sign(buttonId)),
                        40,
                        20)
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        Component component = Component.translatable(KEY_TIMER_INTERVAL,
                String.format("%.3f", this.getMenu().getDelayTime() / 20.0F));
        guiGraphics.drawString(this.font,
                component,
                (this.width - this.font.width(component)) / 2,
                this.height / 2 - 32,
                0x404040,
                false);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        guiGraphics.blit(TEXTURE_LOCATION, this.width / 2 - 88, this.height / 2 - 43, 0, 0, 176, 86);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if (this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        } else {
            return false;
        }
    }
}

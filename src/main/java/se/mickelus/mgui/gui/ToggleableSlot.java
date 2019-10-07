package se.mickelus.mgui.gui;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ToggleableSlot extends Slot {

    private boolean isEnabled = true;
    private int realX, realY;

    public ToggleableSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);

        realX = xPosition;
        realY = yPosition;
    }

    public void toggle(boolean enabled) {
        isEnabled = enabled;

        if (enabled) {
            xPos = realX;
            yPos = realY;
        } else {
            xPos = -10000;
            yPos = -10000;
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        return isEnabled;
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        return isEnabled;
    }
}

package se.mickelus.mgui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.stream.Collectors;

public class GuiItem extends GuiElement {

    private Minecraft mc;
    private FontRenderer fontRenderer;

    private ItemStack itemStack;

    private boolean showTooltip = true;

    public GuiItem(int x, int y) {
        super(x, y, 16, 16);

        mc = Minecraft.getInstance();

        setVisible(false);
    }

    public GuiItem setTooltip(boolean showTooltip) {
        this.showTooltip = showTooltip;
        return this;
    }

    public GuiItem setItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        setVisible(itemStack != null);

        if (itemStack != null) {
            fontRenderer = itemStack.getItem().getFontRenderer(itemStack);

            if (fontRenderer == null) {
                fontRenderer = mc.fontRenderer;
            }
        }

        return this;
    }

    @Override
    public void draw(int refX, int refY, int screenWidth, int screenHeight, int mouseX, int mouseY, float opacity) {
        super.draw(refX, refY, screenWidth, screenHeight, mouseX, mouseY, opacity);

        GlStateManager.pushMatrix();
        GlStateManager.enableDepthTest();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderHelper.enableGUIStandardItemLighting();

        mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, refX + x, refY + y);
        mc.getItemRenderer().renderItemOverlayIntoGUI(fontRenderer, itemStack, refX + x, refY + y, itemStack.getCount() + "");

        GlStateManager.disableDepthTest();
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public List<String> getTooltipLines() {
        if (showTooltip && itemStack != null && hasFocus()) {
            return itemStack.getTooltip(Minecraft.getInstance().player,
                    this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL)
                    .stream()
                    .map(ITextComponent::getFormattedText)
                    .collect(Collectors.toList());
        }

        return null;
    }
}

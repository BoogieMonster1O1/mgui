package se.mickelus.mgui.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import se.mickelus.mgui.MGuiMod;

/**
 * Texture with half "pixel" offset
 */
public class GuiTextureOffset extends GuiElement {

    private ResourceLocation textureLocation;

    private int textureX;
    private int textureY;

    protected int color = 0xffffff;

    public GuiTextureOffset(int x, int y, int width, int height, String texture) {
        this(x, y, width, height, new ResourceLocation(MGuiMod.MOD_ID, texture));
    }

    public GuiTextureOffset(int x, int y, int width, int height, int textureX, int textureY, String texture) {
        this(x, y, width, height, textureX, textureY, new ResourceLocation(MGuiMod.MOD_ID, texture));
    }

    public GuiTextureOffset(int x, int y, int width, int height, ResourceLocation textureLocation) {
        this(x, y, width, height, 0, 0, textureLocation);
    }

    public GuiTextureOffset(int x, int y, int width, int height, int textureX, int textureY, ResourceLocation textureLocation) {
        super(x, y, width + 1, height + 1);

        this.textureX = textureX;
        this.textureY = textureY;

        this.textureLocation = textureLocation;
    }

    @Override
    public void draw(int refX, int refY, int screenWidth, int screenHeight, int mouseX, int mouseY, float opacity) {
        super.draw(refX, refY, screenWidth, screenHeight, mouseX, mouseY, opacity);

        RenderSystem.pushMatrix();
        Minecraft.getInstance().getTextureManager().bindTexture(textureLocation);

        RenderSystem.color4f(
            (color >> 16 & 255) / 255f,
            (color >> 8 & 255) / 255f,
            (color & 255) / 255f,
            opacity * getOpacity() * 255f);
        RenderSystem.enableBlend();
        RenderSystem.translatef(0.5F, 0.5F, 0);
        blit(refX + x, refY + y, textureX, textureY, width - 1, height - 1);
        RenderSystem.popMatrix();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

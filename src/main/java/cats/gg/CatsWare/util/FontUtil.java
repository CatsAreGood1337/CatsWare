package cats.gg.CatsWare.util;

import cats.gg.CatsWare.gui.FontRenderer;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class FontUtil {

    private static final FontRenderer cfr = new FontRenderer(true, true);

    private FontUtil() {
    }

    public static FontRenderer getCustomFR() {
        return cfr;
    }

    public static void drawString(boolean custom, int x, int y, String text, Color color) {
        if (custom) cfr.drawString(text, x, y, color);
        else Minecraft.getMinecraft().fontRenderer.drawString(text, x, y, color.getRGB());
    }

    public static void drawStringWithShadow(boolean custom, int x, int y, String text, Color color) {
        if (custom) cfr.drawStringWithShadow(text, x, y, color);
        else Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
    }

    public static void drawCenteredString(boolean custom, Rectangle rect, String text, Color color) {
        if (custom) cfr.drawCenteredString(text, rect, color);
        else Minecraft.getMinecraft().fontRenderer.drawString(text, rect.x + ((rect.width - Minecraft.getMinecraft().fontRenderer.getStringWidth(text)) / 2), rect.y, color.getRGB());
    }

    public static void drawCenteredStringWithShadow(boolean custom, Rectangle rect, String text, Color color) {
        if (custom) cfr.drawCenteredStringWithShadow(text, rect, color);
        else Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, rect.x + ((rect.width - Minecraft.getMinecraft().fontRenderer.getStringWidth(text)) / 2F), rect.y, color.getRGB());
    }

    public static int getStringWidth(boolean custom, String text) {
        if (custom) return cfr.getStringWidth(text);
        else return Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    public static int getFontHeight(boolean custom) {
        if (custom) return cfr.getFontHeight() + 2;
        else return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2;
    }
}

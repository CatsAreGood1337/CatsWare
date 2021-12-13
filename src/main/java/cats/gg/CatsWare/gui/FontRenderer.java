package cats.gg.CatsWare.gui;

import cats.gg.CatsWare.Client;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class FontRenderer {

    private final boolean antiAlias, fractionalMetrics;
    private final float imgSize = 512;
    private final int charOffset = 0;
    private final int[] colorCode = new int[32];
    private final CharData[] charData = new CharData[256],
            boldChars = new CharData[256],
            italicChars = new CharData[256],
            boldItalicChars = new CharData[256];
    private final DynamicTexture texBold, texItalic, texItalicBold;
    private Font font;
    private int fontHeight = -1;
    private DynamicTexture tex;

    public FontRenderer(boolean antiAlias, boolean fractionalMetrics) {
        this.font = getCustomFont(17);
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
        this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
        this.texBold = setupTexture(font.deriveFont(Font.BOLD), antiAlias, fractionalMetrics, this.boldChars);
        this.texItalic = setupTexture(font.deriveFont(Font.ITALIC), antiAlias, fractionalMetrics, this.italicChars);
        this.texItalicBold = setupTexture(font.deriveFont(Font.BOLD | Font.ITALIC), antiAlias, fractionalMetrics, this.boldItalicChars);
        setupMinecraftColorCodes();
    }

    public void drawStringWithShadow(String text, double x, double y, Color color) {
        drawString(text, x + 1D, y + 1D, color, true);
        drawString(text, x, y, color, false);
    }

    public void drawString(String text, float x, float y, Color color) {
        drawString(text, x, y, color, false);
    }

    public void drawCenteredStringWithShadow(String text, Rectangle rect, Color color) {
        drawStringWithShadow(text, rect.x + ((rect.width - getStringWidth(text)) / 2F), rect.y, color);
    }

    public void drawCenteredString(String text, Rectangle rect, Color color) {
        drawString(text, rect.x + ((rect.width - getStringWidth(text)) / 2F), rect.y, color);
    }

    public void drawString(String text, double x, double y, Color dColor, boolean shadow) {
        x -= 1;
        y -= 2;
        Color color = dColor;
        if (text == null) return;
        if (color.getRed() == 255 && color.getGreen() == 255 && color.getBlue() == 255 && color.getAlpha() == 32)
            color = new Color(255, 255, 255);
        if (color.getAlpha() < 4) color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 255);
        if (shadow) color = new Color(color.getRed() / 4, color.getGreen() / 4, color.getBlue() / 4, color.getAlpha());

        CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        x *= 2.0D;
        y *= 2.0D;
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(tex.getGlTextureId());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());//
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (character == '\u00A7') {
                int colorIndex = 21;
                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(tex.getGlTextureId());
                    currentData = this.charData;
                    if (colorIndex < 0) colorIndex = 15;
                    if (shadow) colorIndex += 16;
                    int colorCode = this.colorCode[colorIndex];
                    GlStateManager.color((colorCode >> 16 & 0xFF) / 255.0F, (colorCode >> 8 & 0xFF) / 255.0F, (colorCode & 0xFF) / 255.0F, color.getAlpha());
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(texBold.getGlTextureId());
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 18) strikethrough = true;
                else if (colorIndex == 19) underline = true;
                else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                        currentData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(texItalic.getGlTextureId());
                        currentData = this.italicChars;
                    }
                } else {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                    GlStateManager.bindTexture(tex.getGlTextureId());
                    currentData = this.charData;
                }
                i++;
            } else if (character < currentData.length) {
                GlStateManager.glBegin(GL11.GL_TRIANGLES);
                drawChar(currentData, character, (float) x, (float) y);
                GlStateManager.glEnd();
                if (strikethrough)
                    drawLine(x, y + currentData[character].height / 2D, x + currentData[character].width - 8.0D, y + currentData[character].height / 2D);
                if (underline)
                    drawLine(x, y + currentData[character].height - 2.0D, x + currentData[character].width - 8.0D, y + currentData[character].height - 2.0D);
                x += currentData[character].width - 8 + this.charOffset;
            }
        }
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GlStateManager.popMatrix();
    }

    public void drawLine(double x, double y, double x1, double y1) {
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth((float) 1.0);
        GlStateManager.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GlStateManager.glEnd();
        GlStateManager.enableTexture2D();
    }

    public int getStringWidth(String text) {
        if (text == null) return 0;
        int width = 0;
        CharData[] currentData = this.charData;
        int size = text.length();

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);
            if (character == '\u00A7')
                i++;
            else if (character < currentData.length)
                width += currentData[character].width - 8 + this.charOffset;
        }
        return width / 2;
    }

    public int getFontHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public void setFont(Font font) {
        this.font = font;
        tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
    }

    public void setSize(int size) {
        setFont(getCustomFont(size));
    }

    private DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
        BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
        try {
            return new DynamicTexture(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
        int imgSize = (int) this.imgSize;
        BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, imgSize, imgSize);
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        FontMetrics fontMetrics = g.getFontMetrics();
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;
        for (int i = 0; i < chars.length; i++) {
            char ch = (char) i;
            CharData charData = new CharData();
            Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
            charData.width = (dimensions.getBounds().width + 8);
            charData.height = dimensions.getBounds().height;
            if (positionX + charData.width >= imgSize) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }
            if (charData.height > charHeight) charHeight = charData.height;
            charData.storedX = positionX;
            charData.storedY = positionY;
            if (charData.height > this.fontHeight) this.fontHeight = charData.height;
            chars[i] = charData;
            g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
            positionX += charData.width;
        }
        return bufferedImage;
    }

    private void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
        try {
            drawQuad(x, y, chars[c].width, chars[c].height, chars[c].storedX, chars[c].storedY, chars[c].width, chars[c].height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
        float renderSRCX = srcX / imgSize;
        float renderSRCY = srcY / imgSize;
        float renderSRCWidth = srcWidth / imgSize;
        float renderSRCHeight = srcHeight / imgSize;
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
        GL11.glVertex2d(x + width, y);
    }

    private void setupMinecraftColorCodes() {
        for (int index = 0; index < 32; index++) {
            int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index & 0x1) * 170 + noClue;
            if (index == 6) red += 85;
            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }
            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }

    private Font getCustomFont(int size) {
        try {
            Font f = Font.createFont(Font.TRUETYPE_FONT, Client.loadResource("gui/font/notosans.ttf"));
            return f.deriveFont(Font.PLAIN, size);
        } catch (Exception e) {
            return new Font("Verdana", Font.PLAIN, size + 2);
        }
    }

    private static class CharData {

        public int width;
        public int height;
        public int storedX;
        public int storedY;

        protected CharData() {
        }
    }
}

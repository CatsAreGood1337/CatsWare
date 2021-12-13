package cats.gg.CatsWare.gui;

import cats.gg.CatsWare.modules.HUD.MClickGUI;
import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.DescriptionRenderer;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.RendererBase;
import cats.gg.CatsWare.util.FontUtil;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;

public class  DefaultDarkRenderer {

    public DefaultDarkRenderer() {
    }

    public Renderer getRenderer(int lvl) {
        return new ComponentRenderer(lvl);
    }

    public DescriptionRenderer getDescriptionRenderer() {
        return context -> {
            String desc = context.getDescription();
            if (desc != null) {
                Point pos = new Point(context.getPos().x + context.getSize().width + 3, context.getInterface().getMouse().y - 4);
                Color c = ComponentRenderer.instance.getBackgroundColor(true);
                context.getInterface().fillRect(new Rectangle(
                        new Point(pos),
                        new Dimension(FontUtil.getStringWidth(MClickGUI.customFont.isOn(), desc) + 10, FontUtil.getFontHeight(MClickGUI.customFont.isOn()) + 10)
                ), c, c, c, c);
                context.getInterface().drawString(new Point(pos.x + 3, pos.y + 3), desc, ComponentRenderer.getFontColor());
            }
        };
    }

    protected static class ComponentRenderer extends RendererBase {

        public static RendererBase instance = new ComponentRenderer(-1);
        private final int level;

        public ComponentRenderer(int level) {
            super(17, 0, 0, 0, 0);
            this.level = level;
        }

        public static Color getFontColor() {
            return MClickGUI.fontColor.getValue();
        }

        @Override
        public void renderRect(Context context, String text, boolean focus, boolean active, Rectangle rectangle, boolean overlay) {
            Color c = getMainColor(focus, active);
            if (active) context.getInterface().fillRect(rectangle, c, c, c, c);
            else context.getInterface().fillRect(context.getRect(), c, c, c, c);
            if (overlay && level != 0) {
                Color ov;
                if (context.isHovered()) ov = new Color(150, 150, 150, 65);
                else if (context.isClicked()) ov = new Color(150, 150, 150, 90);
                else ov = new Color(0, 0, 0, 0);
                context.getInterface().fillRect(rectangle, ov, ov, ov, ov);
            }
            if (level == 0)
                context.getInterface().drawString(new Point(rectangle.x + 7, rectangle.y + 4), TextFormatting.BOLD + text, getFontColor());
            else
                context.getInterface().drawString(new Point(rectangle.x + level + 3, rectangle.y + 4), text, getFontColor());
        }

        @Override
        public void renderBackground(Context context, boolean focus) {
            Color c = getBackgroundColor(focus);
            context.getInterface().fillRect(context.getRect(), c, c, c, c);
        }

        @Override
        public void renderBorder(Context context, boolean focus, boolean active, boolean open) {
            if (getOutlineColor() != null) {
                if (level == 1) {
                    Color c = getOutlineColor();
                    context.getInterface().fillRect(new Rectangle(
                            context.getPos(),
                            new Dimension(1, context.getSize().height)
                    ), c, c, c, c);
                    context.getInterface().fillRect(new Rectangle(
                            new Point(context.getPos().x + context.getSize().width - 1, context.getPos().y),
                            new Dimension(1, context.getSize().height)
                    ), c, c, c, c);
                }
            }
        }

        // return color if you like borders
        public Color getOutlineColor() {
            return null;
        }

        @Override
        public Color getMainColor(boolean focus, boolean active) {
            if (level == 1)
                if (active) return new Color(80, 80, 80);
                else return new Color(45, 45, 45);
            else if (level == 2)
                return new Color(65, 65, 65);
            else if (level >= 3)
                if (active) return new Color(60, 60, 60);
                else return getBackgroundColor(focus);
            else
                return getBackgroundColor(focus);
        }

        @Override
        public Color getBackgroundColor(boolean focus) {
            Color bc = new Color(40, 40, 40);
            if (focus || (bc.getRed() == 0 || bc.getGreen() == 0 || bc.getBlue() == 0)) return bc;
            else return new Color(bc.getRed() - 2, bc.getGreen() - 2, bc.getBlue() - 2);
        }

        @Override
        public ColorScheme getDefaultColorScheme() {
            return null;
        }
    }
}
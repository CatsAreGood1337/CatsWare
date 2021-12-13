package cats.gg.CatsWare.gui;

import cats.gg.CatsWare.modules.HUD.MClickGUI;
import cats.gg.CatsWare.settings.*;
import com.lukflug.panelstudio.CollapsibleContainer;
import com.lukflug.panelstudio.Component;
import com.lukflug.panelstudio.DraggableContainer;
import com.lukflug.panelstudio.SettingsAnimation;
import com.lukflug.panelstudio.hud.HUDClickGUI;
import com.lukflug.panelstudio.hud.HUDPanel;
import com.lukflug.panelstudio.mc12.GLInterface;
import com.lukflug.panelstudio.mc12.MinecraftGUI;
import com.lukflug.panelstudio.mc12.MinecraftHUDGUI;
import com.lukflug.panelstudio.settings.ColorSetting;
import com.lukflug.panelstudio.settings.*;
import cats.gg.CatsWare.modules.HUDModule;
import cats.gg.CatsWare.modules.Module;
import cats.gg.CatsWare.util.FontUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;


public class GUI extends MinecraftHUDGUI {
    private final GUIInterface inter;
    private final HUDClickGUI gui;
    private static GUI INSTANCE;

    public GUI() {
        inter = new MinecraftGUI.GUIInterface(true) {

            @Override
            protected String getResourcePrefix() {
                return "gui/";
            }

            @Override
            public void drawString(Point pos, String s, Color c) {
                GLInterface.end();
                FontUtil.drawStringWithShadow(MClickGUI.customFont.isOn(), pos.x, pos.y, s, c);
                GLInterface.begin();
            }

            @Override
            public int getFontWidth(String s) {
                return FontUtil.getStringWidth(MClickGUI.customFont.isOn(), s) + 4;
            }

            @Override
            public int getFontHeight() {
                return FontUtil.getFontHeight(MClickGUI.customFont.isOn()) + 2;
            }
        };
        DefaultDarkRenderer renderer = new DefaultDarkRenderer();
        gui = new HUDClickGUI(inter, renderer.getDescriptionRenderer());
        Toggleable colorToggle = new Toggleable() {
            @Override
            public void toggle() {
                MClickGUI.colorModel.increment();
            }

            @Override
            public boolean isOn() {
                return MClickGUI.colorModel.equalsValue("HSB");
            }
        }, hudToggle = new SimpleToggleable(false);
        Point pos = new Point(8, 8);
        for (Module.Category c : Module.Category.values()) {
            DraggableContainer gCategory = new DraggableContainer(
                    c.name(),
                    null,
                    renderer.getRenderer(0),
                    new SimpleToggleable(false),
                    new SettingsAnimation(MClickGUI.animationSpeed),
                    null,
                    new Point(pos),
                    125
            );
            pos.translate(125 + 10, 0);
            for (Module m : Module.getModulesByCategory(c)) {
                CollapsibleContainer gModule = new CollapsibleContainer(
                        m.getName(),
                        m.getDescription(),
                        renderer.getRenderer(1),
                        new SimpleToggleable(false),
                        new SettingsAnimation(MClickGUI.animationSpeed),
                        new Toggleable() {
                            @Override
                            public void toggle() {
                                if (!m.getCategory().equals(Module.Category.HUD)) m.toggle();
                            }

                            @Override
                            public boolean isOn() {
                                return !m.getCategory().equals(Module.Category.HUD) && m.isOn();
                            }
                        }
                );
                if (m.getCategory().equals(Module.Category.HUD)) {
                    ((HUDModule) m).apply(renderer);
                    gui.addHUDComponent(new HUDPanel(((HUDModule) m).getComponent(), renderer.getRenderer(0), m, new SettingsAnimation(MClickGUI.animationSpeed), hudToggle, 2));
                }
                for (Setting<?> s : Setting.getSettingsByMod(m)) {
                    Component component = null;


                    if (s.getType().equals(Setting.Type.INTEGER))
                        component = new NumberComponent(s.getName(), null, renderer.getRenderer(3), (IntegerSetting) s, ((IntegerSetting) s).getMinimumValue(), ((IntegerSetting) s).getMaximumValue());
                    else if (s.getType().equals(Setting.Type.DOUBLE))
                        component = new NumberComponent(s.getName(), null, renderer.getRenderer(3), (DoubleSetting) s, ((DoubleSetting) s).getMinimumValue(), ((DoubleSetting) s).getMaximumValue());
                    else if (s.getType().equals(Setting.Type.BOOLEAN))
                        component = new BooleanComponent(s.getName(), null, renderer.getRenderer(3), (BooleanSetting) s);
                    else if (s.getType().equals(Setting.Type.MODE))
                        component = new EnumComponent(s.getName(), null, renderer.getRenderer(3), (ModeSetting) s);
                    else if (s.getType().equals(Setting.Type.COLOR))
                        component = new ColorComponent(s.getName(), null, renderer.getRenderer(2), new SettingsAnimation(MClickGUI.animationSpeed), renderer.getRenderer(3), (ColorSetting) s, false, true, colorToggle);
                    gModule.addComponent(component);
                }
                if (!m.getCategory().equals(Module.Category.HUD)) {
                    gModule.addComponent(new KeybindComponent(renderer.getRenderer(3), new KeybindSetting() {
                        @Override
                        public int getKey() {
                            return m.getBind();
                        }

                        @Override
                        public void setKey(int key) {
                            m.setBind(key);
                        }

                        @Override
                        public String getKeyName() {
                            return Keyboard.getKeyName(m.getBind());
                        }
                    }));
                }
                gCategory.addComponent(gModule);
            }
            gui.addComponent(gCategory);
        }
    }

    public void updateModule(Module module) {
    }

    public static GUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GUI();
        }
        return INSTANCE;
    }

    @Override
    protected HUDClickGUI getHUDGUI() {
        return gui;
    }


    @Override
    protected GUIInterface getInterface() {
        return inter;
    }

    @Override
    protected int getScrollSpeed() {
        //return (int) MClickGUI.scrollSpeed.getNumber();
        return 0;
    }

}
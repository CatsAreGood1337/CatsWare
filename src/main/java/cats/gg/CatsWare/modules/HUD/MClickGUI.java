package cats.gg.CatsWare.modules.HUD;

import cats.gg.CatsWare.Client;
import cats.gg.CatsWare.modules.Module;
import cats.gg.CatsWare.settings.BooleanSetting;
import cats.gg.CatsWare.settings.ColorSetting;
import cats.gg.CatsWare.settings.IntegerSetting;
import cats.gg.CatsWare.settings.ModeSetting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class MClickGUI extends Module {

    public static ColorSetting activeColor, inactiveColor, backgroundColor, outlineColor, fontColor;
    public static IntegerSetting opacity, animationSpeed, scrollSpeed;
    public static BooleanSetting customFont;
    public static ModeSetting colorModel;

    public MClickGUI() {
        super("Click GUI", "clickGUI", "ClickGui", Category.Client);
        setBind(Keyboard.KEY_RSHIFT);
    }

    public static Object getInstance() {
        return null;
    }

    @Override
    public void setup() {
        fontColor = property("Font Color", "FontColor", Color.WHITE, false);
        animationSpeed = property("AnimationSpeed", "AnimationSpeed", 30, 0, 100);
        customFont = property("Custom font", "CustomFont", true);
        colorModel = property("Color Model", "ColorModel", "HSB", "RGB");
    }

    @Override
    public void onEnable() {
        Client.getInstance().gui.enterGUI();
    }

    @Override
    public void onDisable() {
        Client.getInstance().gui.exitGUI();
    }
}

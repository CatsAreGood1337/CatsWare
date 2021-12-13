package cats.gg.CatsWare.settings;

import cats.gg.CatsWare.modules.Module;

import java.awt.*;

public class ColorSetting extends Setting implements com.lukflug.panelstudio.settings.ColorSetting {

    private Color value;
    private boolean rainbow;

    public ColorSetting(String name, String configName, Module parent, Color value, boolean rainbow) {
        super(name, configName, parent, Type.COLOR);
        this.rainbow = rainbow;
        this.value = value;
    }

    @Override
    public Color getValue() {
        if (getRainbow()) return Color.getHSBColor((System.currentTimeMillis() % (360 * 32)) / (360f * 32), 1, 1);
        else return value;
    }

    @Override
    public void setValue(Color value) {
        this.value = value;
    }

    @Override
    public Color getColor() {
        return value;
    }

    @Override
    public boolean getRainbow() {
        return rainbow;
    }

    @Override
    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }
}

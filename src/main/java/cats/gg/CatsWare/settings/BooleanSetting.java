package cats.gg.CatsWare.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import cats.gg.CatsWare.modules.Module;

public class BooleanSetting extends Setting implements Toggleable {

    private boolean value;

    public BooleanSetting(String name, String configName, Module parent, boolean value) {
        super(name, configName, parent, Type.BOOLEAN);
        this.value = value;
    }

    @Override
    public boolean isOn() {
        return value;
    }

    @Override
    public void toggle() {
        value = !value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

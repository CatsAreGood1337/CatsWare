package cats.gg.CatsWare.settings;

import com.lukflug.panelstudio.settings.EnumSetting;
import cats.gg.CatsWare.modules.Module;

import java.util.List;

public class ModeSetting extends Setting implements EnumSetting {

    private final List<String> modes;
    private String value;

    public ModeSetting(String name, String configName, Module parent, List<String> modes, String value) {
        super(name, configName, parent, Type.MODE);
        this.modes = modes;
        this.value = value;
    }

    @Override
    public void increment() {
        int inx = modes.indexOf(value);
        inx = (inx + 1) % modes.size();
        value = modes.get(inx);
    }

    public void decrement() {
        int inx = modes.indexOf(value);
        if (inx - 1 < 0) inx = modes.size() - 1;
        else inx -= 1;
        value = modes.get(inx);
    }

    @Override
    public String getValueName() {
        return value;
    }

    public boolean equalsValue(String value) {
        return this.value.equalsIgnoreCase(value);
    }

}

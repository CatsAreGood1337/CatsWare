package cats.gg.CatsWare.settings;

import com.lukflug.panelstudio.settings.NumberSetting;
import cats.gg.CatsWare.modules.Module;

public class IntegerSetting extends Setting implements NumberSetting {

    private final int min, max;
    private int value;

    public IntegerSetting(String name, String configName, Module parent, int value, int min, int max) {
        super(name, configName, parent, Type.INTEGER);
        this.min = min;
        this.max = max;
        this.value = value;
    }

    @Override
    public double getNumber() {
        return value;
    }

    @Override
    public void setNumber(double value) {
        this.value = (int) value;
    }

    @Override
    public double getMaximumValue() {
        return max;
    }

    @Override
    public double getMinimumValue() {
        return min;
    }

    @Override
    public int getPrecision() {
        return 0;
    }
}


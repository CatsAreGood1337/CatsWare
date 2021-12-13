package cats.gg.CatsWare.settings;

import com.lukflug.panelstudio.settings.NumberSetting;
import cats.gg.CatsWare.modules.Module;

public class DoubleSetting extends Setting implements NumberSetting {

    private final double min, max;
    private double value;

    public DoubleSetting(String name, String configName, Module parent, double value, double min, double max) {
        super(name, configName, parent, Type.DOUBLE);
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
        this.value = value;
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
        return 2;
    }
}


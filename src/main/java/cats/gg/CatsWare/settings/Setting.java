package cats.gg.CatsWare.settings;

import cats.gg.CatsWare.modules.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Setting<T> {

    private T value;
    private final String name, configName;
    private final Type type;
    private final Module parent;
    private T plannedValue;
    private boolean hasRestriction;
    private T max;
    private T min;

    private static final ArrayList<Setting<?>> settingsList = new ArrayList<>();
    private T defaultValue;

    public Setting(T value, String name, Module parent, Type type) {
        this.value = value;
        this.name = name;
        this.configName = name.replace(" ", "");
        this.parent = parent;
        this.type = type;
        settingsList.add(this);
    }

    public static ArrayList<Setting<?>> getAllSettings() {
        return settingsList;
    }

    public static Setting<?> getSettingByNameAndMod(String name, Module mod) {
        return settingsList.stream().filter(s -> s.getParent().equals(mod)).filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Setting<?> getSettingByCfgNameAndMod(String configName, Module mod) {
        return settingsList.stream().filter(s -> s.getParent().equals(mod)).filter(s -> s.getConfigName().equalsIgnoreCase(configName)).findFirst().orElse(null);
    }

    public static List<Setting<?>> getSettingsByMod(Module mod) {
        return settingsList.stream().filter(s -> s.getParent().equals(mod)).collect(Collectors.toList());
    }

    public static List<Setting> getSettingsByType(Setting.Type type) {
        return settingsList.stream().filter(s -> s.getType().equals(type)).collect(Collectors.toList());
    }

    public void setValue(T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number) this.min).floatValue() > ((Number) value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number) this.max).floatValue() < ((Number) value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }
    }


    public T getPlannedValue() {
        return this.plannedValue;
    }

    public void setPlannedValue(T value) {
        this.plannedValue = value;
    }

    public T getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getConfigName() {
        return this.configName;
    }

    public Module getParent() {
        return this.parent;
    }

    public Type getType() {
        return this.type;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public enum Type {BOOLEAN, COLOR, INTEGER, DOUBLE, MODE}
}

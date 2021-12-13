package cats.gg.CatsWare.modules;

import cats.gg.CatsWare.modules.Other.Timestamps;
import cats.gg.CatsWare.modules.HUD.MClickGUI;
import cats.gg.CatsWare.modules.PVP.FastBow;
import cats.gg.CatsWare.modules.PVP.InstantBurrow;
import cats.gg.CatsWare.modules.Other.*;
import cats.gg.CatsWare.modules.Move.*;
import cats.gg.CatsWare.modules.Visuals.FullBright;
import cats.gg.CatsWare.modules.Visuals.NoFog;
import cats.gg.CatsWare.modules.Visuals.NoWeather;
import cats.gg.CatsWare.modules.Visuals.OffhandSwing;
import cats.gg.CatsWare.settings.*;
import com.lukflug.panelstudio.settings.Toggleable;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Module implements Toggleable {

    private final String name, configName, description;
    private final Category category;
    private boolean toggled = false;
    private int bind = Keyboard.KEY_NONE;

    private static final ArrayList<Module> modulesList = new ArrayList<>();
    private static final ArrayList<HUDModule> hudModulesList = new ArrayList<>();

    protected static final Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String configName, String description, Category category) {
        this.name = name;
        this.configName = configName;
        this.description = description;
        this.category = category;
        setup();
    }

    public static void start() {
        add(new Sprint());
        add(new MClickGUI());
        add(new NoVoid());
        add(new FullBright());
        add(new FastBow());
        add(new NoSlow());
        add(new FakePlayer());
        add(new AutoWalk());
        add(new NoFog());
        add(new ReverseStep());
        add(new RPC());
        add(new NoWeather());
        add(new InstantBurrow());
        add(new Timestamps());
        add(new DonkeyFinder());
        add(new GhastFinder());
        add(new HoleKill());
        add(new OffhandSwing());

    }

    public static ArrayList<Module> getAllModules() {
        return modulesList;
    }

    public static ArrayList<HUDModule> getAllHUDModules() {
        return hudModulesList;
    }

    public static boolean checkIsOn(String name) {
        return Objects.requireNonNull(getModuleByName(name)).isOn();
    }

    public static boolean checkIsOn(Class<? extends Module> clazz) {
        return Objects.requireNonNull(getModuleByClass(clazz)).isOn();
    }

    public static ArrayList<Module> getModulesByCategory(Module.Category category) {
        return modulesList.stream().filter(m -> m.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
    }

    public static Module getModuleByName(String name) {
        return modulesList.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Module getModuleByClass(Class<? extends Module> clazz) {
        return modulesList.stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
    }

    private static void add(Module module) {
        modulesList.add(module);
        if (module instanceof HUDModule)
            hudModulesList.add((HUDModule) module);
    }

    public String getName() {
        return name;
    }

    public String getConfigName() {
        return configName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean isOn() {
        return toggled;
    }

    @Override
    public void toggle() {
        toggled = !toggled;
        if (toggled) onEnable();
        else onDisable();
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
    }

    public Category getCategory() {
        return category;
    }

    public void setup() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onUpdate() {
    }

    public void onToggled() {
    }

    public float arrayListOffset = 0.0f;
    public float arrayLstVOffset = 0.0f;
    public float offset;
    public float vOffset;

    public IntegerSetting property(String name, String configName, int value, int min, int max) {
        return new IntegerSetting(name, configName, this, value, min, max);
    }

    public DoubleSetting property(String name, String configName, double value, double min, double max) {
        return new DoubleSetting(name, configName, this, value, min, max);
    }

    public BooleanSetting property(String name, String configName, boolean value) {
        return new BooleanSetting(name, configName, this, value);
    }

    public ModeSetting property(String name, String configName, String defaultMode, String... otherModes) {
        ArrayList<String> arrayModes = new ArrayList<>(Arrays.asList(otherModes));
        if (!arrayModes.contains(defaultMode)) arrayModes.add(defaultMode);
        return new ModeSetting(name, configName, this, arrayModes, defaultMode);
    }

    public ColorSetting property(String name, String configName, java.awt.Color color, boolean rainbow) {
        return new ColorSetting(name, configName, this, color, rainbow);
    }

    protected void onLoad() {
    }

    public enum Category {
        PVP, Visuals, Other, Move, Client, HUD;
    }
}

package cats.gg.CatsWare.modules.Visuals;

import cats.gg.CatsWare.modules.Module;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather", "NoWeather", "Rain rain go away", Category.Visuals);
    }

    public void onUpdate() {
        if (mc.world == null) return;
        if (mc.world.isRaining()) {
            mc.world.setRainStrength(0);
        }
    }
}
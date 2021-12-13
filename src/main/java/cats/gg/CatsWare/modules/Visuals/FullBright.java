package cats.gg.CatsWare.modules.Visuals;

import cats.gg.CatsWare.modules.Module;

public class FullBright extends Module
{
    float oldBrightness;

    public FullBright() {
        super("FullBright", "fullbright", "Now the night does not exist", Category.Visuals);
    }

    @Override
    public void onEnable() {
        this.oldBrightness = FullBright.mc.gameSettings.gammaSetting;
        FullBright.mc.gameSettings.gammaSetting = 1000.0f;
    }

    @Override
    public void onDisable() {
        FullBright.mc.gameSettings.gammaSetting = this.oldBrightness;
    }

    @Override
    public void onUpdate() {
        if (FullBright.mc.gameSettings.gammaSetting != 1000.0f) {
            FullBright.mc.gameSettings.gammaSetting = 1000.0f;
        }
    }
}
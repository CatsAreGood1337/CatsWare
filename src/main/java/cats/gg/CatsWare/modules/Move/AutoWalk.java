package cats.gg.CatsWare.modules.Move;

import cats.gg.CatsWare.modules.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoWalk extends Module
{
    public AutoWalk() {
        super("AutoWalk", "Automatically walks", "Automatically walks", Category.Move);
    }

    @Override
    public void onUpdate() {
        KeyBinding.setKeyBindState(AutoWalk.mc.gameSettings.keyBindForward.getKeyCode(), true);
    }
}

package cats.gg.CatsWare.modules.Move;

import cats.gg.CatsWare.modules.Module;

public class NoVoid extends Module {
    public NoVoid() {
        super("NoVoid", "novoid", "Glitches you up from void", Category.Move);
    }

    @Override
    public void onUpdate() {
        double yLevel = mc.player.posY;
        if (yLevel <= .5) {
            mc.player.moveVertical = 10;
            mc.player.jump();
        } else {
            mc.player.moveVertical = 0;
        }
    }

    @Override
    public void onDisable () {
        mc.player.moveVertical = 0;
    }
}

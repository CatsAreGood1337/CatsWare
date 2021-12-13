package cats.gg.CatsWare.modules.Move;

import cats.gg.CatsWare.modules.Module;

public class ReverseStep extends Module {
    private static ReverseStep INSTANCE = new ReverseStep();

    public ReverseStep() {
        super("ReverseStep", "ReverseStep.", "Now your reverse steps are faster", Category.Move);
        setInstance();
    }

    public static ReverseStep getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReverseStep();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null || mc.player.isInWater() || mc.player.isInLava()) {
            return;
        }
        if (mc.player.onGround) {
            --mc.player.motionY;
        }
    }
}
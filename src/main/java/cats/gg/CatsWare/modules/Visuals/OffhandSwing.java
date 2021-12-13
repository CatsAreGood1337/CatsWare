package cats.gg.CatsWare.modules.Visuals;

import cats.gg.CatsWare.modules.Module;
import net.minecraft.util.EnumHand;

public class OffhandSwing extends Module {
    public OffhandSwing() {
        super("OffhandSwing", "OffhandSwing", "Changes the hand you swing with", Category.Visuals);
    }

    public void onUpdate() {
        if (mc.world == null)
            return;
        mc.player.swingingHand = EnumHand.OFF_HAND;
    }
}




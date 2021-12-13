package cats.gg.CatsWare.modules.Move;

import cats.gg.CatsWare.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "sprint", "Automatic sprints", Category.Move);
    }

    private static final Minecraft mc = Minecraft.getMinecraft();
    public void onUpdate() {
        EntityPlayerSP player = mc.player;

        if (player != null) {
            player.setSprinting(shouldSprint(mc.player));
        }
    }

    public boolean shouldSprint(EntityPlayerSP player) {
        return !mc.gameSettings.keyBindSneak.isKeyDown()
                && player.getFoodStats().getFoodLevel() > 6
                && !player.isElytraFlying()
                && !mc.player.capabilities.isFlying;
    }
}

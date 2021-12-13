package cats.gg.CatsWare.modules.Visuals;

import cats.gg.CatsWare.modules.Module;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoFog extends Module {
    public NoFog() {
        super("NoFog", "NoFog", "Removes fog", Category.Visuals);
    }
    @SubscribeEvent
    public void fog_density(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
}

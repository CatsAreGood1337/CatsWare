package cats.gg.CatsWare.events;

import cats.gg.CatsWare.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor {
    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().player == null || !Keyboard.isCreated() || !Keyboard.getEventKeyState()) return;
        Module.getAllModules().stream().filter(m -> m.getBind() == Keyboard.getEventKey()).forEach(Module::toggle);
    }
    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) return;

        if (event.getEntity().getEntityWorld().isRemote && event.getEntityLiving() == Minecraft.getMinecraft().player) {
            for (Module module : Module.getAllModules()) {
                if (!module.isOn()) continue;
                module.onUpdate();
            }
        }
    }
}
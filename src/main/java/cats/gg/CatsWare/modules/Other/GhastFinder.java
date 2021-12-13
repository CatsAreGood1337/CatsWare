package cats.gg.CatsWare.modules.Other;

import cats.gg.CatsWare.commands.Messages;
import cats.gg.CatsWare.modules.Module;
import cats.gg.CatsWare.settings.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.SoundEvents;

import java.util.HashSet;
import java.util.Set;

public class GhastFinder extends Module {
    public GhastFinder() {
        super("GhastFinder", "GhastFinder", "Helps you find ghasts", Category.Other);
    }

    private Set<Entity> ghasts = new HashSet<Entity>();
    int delay;
    public BooleanSetting Chat = property("Chat", "Chat", true);
    public BooleanSetting Sound = property("Sound", "Sound", true);

    @Override
    public void onEnable() {
        this.ghasts.clear();
        {
            delay = 0;
        }
    }

    @Override
    public void onUpdate() {
        if (delay > 0) --delay;
        for (Entity entity : GhastFinder.mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityGhast & delay == 0) || this.ghasts.contains(entity)) continue;
            {
                Messages.sendMessages("Ghast detected at: " + entity.getPosition().getX() + "x, " + entity.getPosition().getY() + "y, " + entity.getPosition().getZ() + "z.");
                GhastFinder.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                delay = 300;
            }
        }
    }
}





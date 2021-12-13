package cats.gg.CatsWare.modules.Other;

import cats.gg.CatsWare.commands.Messages;
import cats.gg.CatsWare.modules.Module;
import cats.gg.CatsWare.settings.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.init.SoundEvents;

import java.util.HashSet;
import java.util.Set;

public class DonkeyFinder extends Module {
    public DonkeyFinder() {
        super("DonkeyFinder", "DonkeyFinder", "Helps you find donkey", Category.Other);
    }

    private Set<Entity> donkey = new HashSet<Entity>();

    int delay;
    public BooleanSetting Chat = property("Chat","Chat",true);
    public BooleanSetting Sound = property("Sound","Sound",true);

    @Override
    public void onEnable() {
        this.donkey.clear();
        {
            delay = 0;
        }
    }

    @Override
    public void onUpdate() {
        if (delay > 0) --delay;
        for (Entity entity : DonkeyFinder.mc.world.getLoadedEntityList()) {
            if (!(entity instanceof EntityDonkey & delay == 0) || this.donkey.contains(entity)) continue;
            {
                Messages.sendMessages("Donkey detected at: " + entity.getPosition().getX() + "x, " + entity.getPosition().getY() + "y, " + entity.getPosition().getZ() + "z.");
                DonkeyFinder.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1.0f);
                delay = 300;
            }
        }
    }
}

package cats.gg.CatsWare.modules.Other;

import com.mojang.authlib.GameProfile;
import cats.gg.CatsWare.modules.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.UUID;

public class FakePlayer extends Module
{
    public FakePlayer() {
        super("FakePlayer", "FakePlayer", "Spawn fake player", Category.Other);
    }

    @Override
    public void onEnable() {
        if (FakePlayer.mc.world == null) {
            return;
        }
        final EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("4b7ed64e-747e-4ee0-a9d8-0349ae33e7e7"), "Bot"));
        fakePlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        fakePlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        FakePlayer.mc.world.addEntityToWorld(-100, (Entity)fakePlayer);
    }

    @Override
    public void onDisable() {
        FakePlayer.mc.world.removeEntityFromWorld(-100);
    }
}

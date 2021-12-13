package cats.gg.CatsWare.modules.Other;

import cats.gg.CatsWare.Discord;
import cats.gg.CatsWare.modules.Module;

public class RPC extends Module {
    public static RPC INSTANCE;
    //public Setting<Boolean> ign = register(new Setting("ign", false));

    public RPC() {
        super("RPC","Rpc", "Discord rich presence", Category.Other);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Discord.start();
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        Discord.stop();
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        Discord.start();
    }
}
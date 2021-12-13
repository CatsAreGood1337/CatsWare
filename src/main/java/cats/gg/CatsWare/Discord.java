package cats.gg.CatsWare;

import cats.gg.CatsWare.modules.Other.RPC;
import cats.gg.CatsWare.rpc.DiscordEventHandlers;
import cats.gg.CatsWare.rpc.DiscordRPC;
import cats.gg.CatsWare.rpc.DiscordRichPresence;

public class Discord
{
    public static DiscordRichPresence presence;
    private static final DiscordRPC rpc;
    private static RPC discord;
    private static Thread thread;

    public static void start() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        rpc.Discord_Initialize("838742062860861460", handlers, true, "");
        Discord.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        Discord.presence.state = "Chilling";
        Discord.presence.largeImageKey = "CatsWare";
        Discord.presence.largeImageText = "Beta-v1.2.0";
        Discord.presence.smallImageKey = "image2";
        Discord.presence.smallImageText = "Hello niggers";
        rpc.Discord_UpdatePresence(presence);
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                rpc.Discord_RunCallbacks();
                Discord.presence.details = "discord.gg/W8aESyTNvn";
                Discord.presence.state = "Chilling";
                rpc.Discord_UpdatePresence(presence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }, "RPC-Callback-Handler");
        thread.start();
    }

    public static void stop() {
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        rpc.Discord_Shutdown();
    }

    static {
        rpc = DiscordRPC.INSTANCE;
        presence = new DiscordRichPresence();
    }
}
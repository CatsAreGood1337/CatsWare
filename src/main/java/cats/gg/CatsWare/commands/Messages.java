package cats.gg.CatsWare.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import cats.gg.CatsWare.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Messages {
    public static void sendMessagesAtPlayer(String... messages) {
        for (String m : messages) Minecraft.getMinecraft().player.sendChatMessage(m);
    }

    public static void sendMessagesToPanel(boolean error, String... messages) {
        for (String m : messages) {
            String prefix = ChatFormatting.DARK_GRAY + "[" + ChatFormatting.DARK_RED + "catsaware" + ChatFormatting.RESET + ChatFormatting.DARK_GRAY + "] " + (error ? ChatFormatting.RED : ChatFormatting.GREEN);
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefix + m));
        }
    }
    public static void sendMessages(String... messages) {
        for (String m : messages) {
            String prefix = ChatFormatting.LIGHT_PURPLE + "[" + ChatFormatting.LIGHT_PURPLE + "catsware" + ChatFormatting.RESET + ChatFormatting.LIGHT_PURPLE + "] " + (ChatFormatting.DARK_GRAY);
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(prefix + m));
        }
    }
}


package cats.gg.CatsWare;

import cats.gg.CatsWare.events.EventProcessor;
import cats.gg.CatsWare.gui.GUI;
import cats.gg.CatsWare.modules.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import java.io.InputStream;
import java.util.Objects;

@Mod(modid = "cc", name = "catsware", version = "0.1")
public class Client {

    @Mod.Instance
    private static Client instance;

    public static final Logger LOGGER = LogManager.getLogger("catsware");

    public GUI gui;

    @Mod.EventHandler
    public void init(FMLInitializationEvent cat) {
        Display.setTitle("catsware 0.1");
        MinecraftForge.EVENT_BUS.register(instance);
        Module.start();
        gui = new GUI();
        new EventProcessor();
    }

    public synchronized static InputStream loadResource(String name) throws NullPointerException {
        InputStream inputStream = Client.class.getClassLoader().getResourceAsStream("assets/cc/" + name);
        return Objects.requireNonNull(inputStream);
    }
    public static Client getInstance() {
        return instance;
    }
}


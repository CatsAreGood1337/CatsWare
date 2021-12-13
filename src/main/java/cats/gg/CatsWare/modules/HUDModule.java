package cats.gg.CatsWare.modules;

import cats.gg.CatsWare.gui.DefaultDarkRenderer;
import com.lukflug.panelstudio.FixedComponent;

import java.awt.*;

public abstract class HUDModule extends Module {

    private Point position;
    private FixedComponent component;

    public HUDModule(String name, String configName, String description, Category category) {
        super(name, configName, description, category);
    }

    public abstract void apply(DefaultDarkRenderer renderer);

    public FixedComponent getComponent() {
        return component;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}


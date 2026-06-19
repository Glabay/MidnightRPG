package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public abstract class Inventory extends UIPanel  {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected final Player player;

    protected Font font;
    protected Color fontColor;

    public Inventory(Player player) {
        this(
            player,
            new Vec2i(
                WindowConfig.getWindowWidth() - 228,
                WindowConfig.getWindowHeight() - 342
            ),
            new Vec2i(218, 305)
        );
    }

    protected Inventory(Player player, Vec2i position, Vec2i size) {
        super(position, size);
        this.player = player;
    }

    protected abstract int getInventoryIndex();

    @Override
    public void update() {}

    @Override
    public void render(Renderer renderer) {}

    @Override
    public UIPanel display() {
        var curUi = player.getCurrentInventoryView();
        // if we have an open interface and it's not this one; close it first
        if (curUi != null && curUi != this) {
            log.info("Closing current inventory: {}", curUi);
            curUi.visible = false;
        }

        super.display(); // Toggles the visibility of this panel

        if (this.isVisible()) {
            log.info("Opened inventory: {}", this);
            player.setCurrentInventoryView(this);
        } else {
            log.info("Closed inventory: {}", this);
            player.setCurrentInventoryView(null);
        }
        return this;
    }
}

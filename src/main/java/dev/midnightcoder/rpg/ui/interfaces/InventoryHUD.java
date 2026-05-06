package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.inventory.Item;
import dev.midnightcoder.rpg.inventory.container.Backpack;
import dev.midnightcoder.rpg.ui.container.Slot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class InventoryHUD extends UIPanel  {
    private final Backpack backpack;
    private final List<Slot> slots;
    private final int rows;
    private final int cols;
    private final Font font;

    public InventoryHUD(Player player) {
        super(
            new Vec2i(
                WindowConfig.getWindowWidth() - 228,
                WindowConfig.getWindowHeight() - 342
            ),
            new Vec2i(305, 218)
        );
        slots = new ArrayList<>();
        font = new Font("Arial", Font.PLAIN, 18);
        this.player = player.getAvatar();
        this.backpack = player.getBackpack();
        rows = 4;
        cols = 5;
        generateSlots();
        background = TextureFactory.createFromImageFile("/ui/inventory.png").image();
    }

    @Override
    public void update() {
        if (visible) {
            slots.forEach(slot -> slot.update(this));
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            renderer.renderImage(background, position.getX(), position.getY(), size.getHeight(), size.getWidth());
            slots.forEach(slot -> slot.render(renderer));
            renderer.setFont(font);
            renderer.setColor(Color.BLACK);
            var string = getSelectedGameItem() == null ? "" :
                ((Item) getSelectedGameItem()).getDefinition().name();
            renderer.renderText(string, position.getX() + 10, position.getY() + size.getHeight() - 26);
        }
    }

    private void generateSlots() {
        var padding = 8;
        var slotSize = 40;
        var spacing = slotSize + padding;
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                var x = position.getX() + padding + (spacing * row) + padding;
                var y = position.getY() + padding + (spacing * col) + padding;
                slots.add(new Slot(new Vec2i(x, y), slotSize, slotSize));
            }
        }
    }

}

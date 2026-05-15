package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.rpg.entity.mob.player.Player;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public class AudioHUD extends Inventory {
    private final Font titleFont;
    private final Font trackFont;
    private final Color fontColor;

    public AudioHUD(Player player) {
        super(player);

        titleFont = new Font("Arial", Font.BOLD, 20);
        trackFont = new Font("Arial", Font.PLAIN, 16);
        fontColor = Color.BLACK;

        background = TextureFactory.createFromImageFile("/ui/inventory.png").image();
    }

    @Override
    protected int getInventoryIndex() {
        return BottomHUD.Tabs.MUSIC.getSlotId();
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            renderer.renderImage(background, position.getX(), position.getY(), size.getWidth(), size.getHeight());
            renderer.setFont(titleFont);
            renderer.setColor(fontColor);
            var title = "Audio Settings";
            renderer.renderText(title, getTextCentered(renderer, title), position.getY() + size.getHeight() - 20);
        }
    }
}

package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.ui.container.AudioContainer;
import dev.midnightcoder.rpg.ui.container.VolumeControl;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-15
 */
public class AudioHUD extends Inventory {
    private final Font titleFont;
    private final Color fontColor;
    private final List<AudioContainer> audioContainers;
    private final VolumeControl volumeControl;

    public AudioHUD(Player player) {
        super(player);
        audioContainers = new ArrayList<>();
        volumeControl = new VolumeControl(position, new Vec2i(size.getWidth(), 32));

        var cachedDefinitions = CacheReader.getInstance().getCacheManager().getAudio();

        for (var def : cachedDefinitions) {
            var container = new AudioContainer(this, def, true);
            audioContainers.add(container);
        }

        titleFont = new Font("Arial", Font.BOLD, 20);
        fontColor = Color.BLACK;

        background = TextureFactory.createFromImageFile("/ui/inventory.png").image();
    }

    @Override
    protected int getInventoryIndex() {
        return BottomHUD.Tabs.MUSIC.getSlotId();
    }

    @Override
    public void update() {
        if (visible) {
            for (var container : audioContainers)
                container.update(this);

            volumeControl.update(this);
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            renderer.renderImage(background, position.getX(), position.getY(), size.getWidth(), size.getHeight());
            renderer.setFont(titleFont);
            renderer.setColor(fontColor);
            var title = "Audio Settings";
            renderer.renderText(title, getTextCentered(renderer, title), position.getY() + size.getHeight() - 20);

            volumeControl.render(renderer);

            renderer.drawRectangle(new Vec2i(position.getX() + 6, position.getY() + 45), size.getWidth() - 14, 1, Color.WHITE);
            for (var container : audioContainers) {
                container.render(renderer);
            }
        }
    }
}

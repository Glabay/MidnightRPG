package dev.midnightcoder.rpg.util;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-14
 */
public class TextUtils {

    public static int getTextCentered(Renderer renderer, String text, Vec2i position, Vec2i size) {
        var textWidth = getTextWidth(renderer, text);
        return position.getX() + (size.getWidth() - textWidth) / 2;
    }


    public static int getTextWidth(Renderer renderer, String text) {
        var graphics = renderer.getGraphics2D();
        var fontMetrics = graphics.getFontMetrics();
        return fontMetrics.stringWidth(text);
    }
}

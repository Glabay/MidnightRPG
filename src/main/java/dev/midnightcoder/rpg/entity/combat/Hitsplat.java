package dev.midnightcoder.rpg.entity.combat;

import dev.midnightcoder.engine.renderer.Renderer;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-06
 */
public class Hitsplat {

    /**
     * Represents a visual combat hit indicator (hitsplat) displayed over an entity.
     * The red circle indicates damage dealt (> 0).
     * The blue circle indicates a 0 or blocked hit (== 0).
     */
    public enum HitsplatType {
        DAMAGE(new Color(204, 30, 30), new Color(110, 10, 10)),
        BLOCK(new Color(30, 100, 220), new Color(15, 45, 110));

        private final Color fillColor;
        private final Color borderColor;

        HitsplatType(Color fillColor, Color borderColor) {
            this.fillColor = fillColor;
            this.borderColor = borderColor;
        }

        public Color getFillColor() {
            return fillColor;
        }

        public Color getBorderColor() {
            return borderColor;
        }
    }

    public static final int DEFAULT_LIFETIME_TICKS = 60;
    public static final int DEFAULT_RADIUS = 10;
    private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);

    private final int damage;
    private final HitsplatType type;
    private int durationTicks;
    private final int maxDurationTicks;

    public Hitsplat(int damage) {
        this(damage, damage > 0 ? HitsplatType.DAMAGE : HitsplatType.BLOCK, DEFAULT_LIFETIME_TICKS);
    }

    public Hitsplat(int damage, HitsplatType type) {
        this(damage, type, DEFAULT_LIFETIME_TICKS);
    }

    public Hitsplat(int damage, HitsplatType type, int durationTicks) {
        this.damage = damage;
        this.type = type != null ? type : (damage > 0 ? HitsplatType.DAMAGE : HitsplatType.BLOCK);
        this.durationTicks = Math.max(1, durationTicks);
        this.maxDurationTicks = this.durationTicks;
    }

    public static Hitsplat damage(int amount) {
        return new Hitsplat(amount, HitsplatType.DAMAGE);
    }

    public static Hitsplat block() {
        return new Hitsplat(0, HitsplatType.BLOCK);
    }

    public static Hitsplat block(int amount) {
        return new Hitsplat(amount, HitsplatType.BLOCK);
    }

    /**
     * Updates the hitsplat duration every tick.
     */
    public void update(double delta) {
        int ticks = Math.max(1, (int) Math.round(delta));
        durationTicks -= ticks;
    }

    public void tick() {
        durationTicks--;
    }

    public boolean isExpired() {
        return durationTicks <= 0;
    }

    public int getDamage() {
        return damage;
    }

    public HitsplatType getType() {
        return type;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public int getMaxDurationTicks() {
        return maxDurationTicks;
    }

    public Color getColor() {
        return type.getFillColor();
    }

    public Color getBorderColor() {
        return type.getBorderColor();
    }

    public Color getTextColor() {
        return Color.WHITE;
    }

    public int getRadius() {
        return DEFAULT_RADIUS;
    }

    /**
     * Renders the hitsplat centered at the given screen coordinates (centerX, centerY).
     */
    public void render(Renderer renderer, int centerX, int centerY) {
        if (renderer == null)
            return;

        var g2d = renderer.getGraphics2D();
        if (g2d == null)
            return;

        var oldColor = g2d.getColor();
        var oldFont = g2d.getFont();
        var oldStroke = g2d.getStroke();
        var oldAntialias = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        var oldTextAntialias = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);

        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int radius = getRadius();
            int diameter = radius * 2;
            int drawX = centerX - radius;
            int drawY = centerY - radius;

            // Subtle drop shadow
            g2d.setColor(new Color(0, 0, 0, 110));
            g2d.fillOval(drawX + 1, drawY + 1, diameter, diameter);

            // Filled circle
            g2d.setColor(getColor());
            g2d.fillOval(drawX, drawY, diameter, diameter);

            // Border outline
            g2d.setColor(getBorderColor());
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.drawOval(drawX, drawY, diameter, diameter);

            // Text
            String damageText = String.valueOf(damage);
            g2d.setFont(DEFAULT_FONT);
            FontMetrics fm = g2d.getFontMetrics(DEFAULT_FONT);
            int textWidth = fm.stringWidth(damageText);
            int textAscent = fm.getAscent();
            int textDescent = fm.getDescent();
            int textX = centerX - (textWidth / 2);
            int textY = centerY + (textAscent - textDescent) / 2;

            // Text shadow for contrast
            g2d.setColor(Color.BLACK);
            g2d.drawString(damageText, textX + 1, textY + 1);

            // Text foreground
            g2d.setColor(getTextColor());
            g2d.drawString(damageText, textX, textY);
        }
        finally {
            g2d.setColor(oldColor);
            g2d.setFont(oldFont);
            g2d.setStroke(oldStroke);
            if (oldAntialias != null)
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntialias);
            if (oldTextAntialias != null)
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, oldTextAntialias);
        }
    }
}

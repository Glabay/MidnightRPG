package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.rpg.entity.mob.player.Player;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class SkillsHUD extends Inventory {
    public SkillsHUD(Player player) {
        super(player);
        font = new Font("Verdana", Font.PLAIN, 12);
        fontColor = new Color(0, 255, 255);
        background = TextureFactory.createFromImageFile("/ui/inventory.png").image();
    }

    @Override
    protected int getInventoryIndex() {
        return BottomHUD.Tabs.SKILL.getSlotId();
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            renderer.renderImage(background, position.getX(), position.getY(), size.getWidth(), size.getHeight());
            renderer.setFont(font);
            renderer.setColor(new Color(0, 255, 255));

            var skillSet = player.getSkillSet();
            if (skillSet == null) return;
            renderer.renderText("Skills", getTextCentered(renderer, "Skills"), position.getY() + 24);
            var skills = skillSet.getSkills();

            var cols = 2;
            var rows = (int) Math.ceil(skills.size() / (double) cols);
            var skillIndex = 0;

            for (int col = 0; col < cols; col++) {
                for (int row = 0; row < rows; row++) {
                    var skill = skills.get(skillIndex++);

                    var strX = position.getX() + 16 + col * 100;
                    var strY = position.getY() + 42 + row * 42;
                    renderer.setFont(font);
                    renderer.renderText(skill.getSkillType().getDisplayName(), strX, strY);

                    var offset = getTextWidth(renderer, skill.getSkillType().getDisplayName());
                    strX += offset;
                    renderer.renderText(": " + skill.getLevel(), strX, strY);

                    strX -= offset;
                    strY += 18;
                    renderer.setFont(font.deriveFont(10f));
                    renderer.renderText("Exp: " + skill.getExperience(), strX, strY);

                }
            }
        }
    }
}

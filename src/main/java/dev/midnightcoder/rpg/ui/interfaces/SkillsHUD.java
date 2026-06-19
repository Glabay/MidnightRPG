package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.skill.Skill;
import dev.midnightcoder.rpg.entity.skill.SkillSet;
import dev.midnightcoder.rpg.entity.skill.SkillSlot;
import dev.midnightcoder.rpg.entity.skill.SkillType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class SkillsHUD extends Inventory {
    private final Font symbol;
    private final SkillSet skillSet;

    private final List<Skill> skills;
    private final List<SkillSlot> skillSlots;

    private final int cols = 3;
    private final int rows;

    private Skill selectedSkill = null;

    public SkillsHUD(Player player) {
        super(player);
        font = new Font("Verdana", Font.PLAIN, 12);
        symbol = new Font("Segoe UI Symbol", Font.PLAIN, 18);
        fontColor = new Color(0, 255, 255);
        background = TextureFactory.createFromImageFile("/ui/inventory.png").image();
        skillSlots = new ArrayList<>(SkillType.values().length);
        this.skillSet = player.getSkillSet();
        this.skills = skillSet.getSkills();
        rows = (int) Math.ceil(skills.size() / (double) cols);
        panel = this;
        initializeSlots();
    }

    private void initializeSlots() {
        var skillIndex = 0;

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (skillIndex >= skills.size()) break;

                var strX = position.getX() + 20 + col * 64;
                var strY = position.getY() + 32 + row * 32;
                var rec = new SkillSlot(new Vec2i(strX, strY), 48, skillIndex, skills.get(skillIndex));
                skillSlots.add(rec);

                skillIndex++;
            }
        }

    }

    @Override
    public void update() {
        if (visible) {
            // TODO: Check the resource usage here... could be expensive
            skills.forEach(Skill::recalculateLevel);

            skillSlots.forEach(slot -> slot.update(this));
        }
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
            renderer.renderText("Skills", getTextCentered(renderer, "Skills"), position.getY() + 24);

            if (skillSet == null) return;
            var skillIndex = 0;

            for (int col = 0; col < cols; col++) {
                for (int row = 0; row < rows; row++) {
                    if (skillIndex >= skills.size()) break;
                    var skill = skills.get(skillIndex);
                    skillIndex++;

                    var strX = position.getX() + 24 + col * 64;
                    var strY = position.getY() + 50 + row * 32;

                    renderer.setFont(symbol);
                    renderer.renderText(skill.getSkillType().getSkillIcon(), strX, strY);

                    var offset = getTextWidth(renderer, skill.getSkillType().getSkillIcon());
                    strX += offset;
                    renderer.renderText(": " + skill.getLevel(), strX, strY);
                }
            }

            // Skill Exp info
            if (selectedSkill != null) {
                var skillName = selectedSkill.getSkillType().getDisplayName();
                renderer.renderText(skillName, position.getX() + 8, position.getY() + size.getHeight() - 20);
                var xOffset = getTextWidth(renderer, skillName) + 16;
                renderer.setFont(font.deriveFont(Font.PLAIN, 10.0f));
                renderer.renderText("Exp: ".concat(selectedSkill.getExperience()), position.getX() + xOffset, position.getY() + size.getHeight() - 30);
                renderer.renderText("Next Level: ".concat(selectedSkill.getNextLevel()), position.getX() + xOffset, position.getY() + size.getHeight() - 14);
            }
        }
    }

    public void setSelectedSkill(Skill skill) {
        this.selectedSkill = skill;
    }

    public void resetSelectedSkill() {
        this.selectedSkill = null;
    }
}

package dev.midnightcoder.rpg.entity.skill;

import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.ui.container.Slot;
import dev.midnightcoder.rpg.ui.interfaces.SkillsHUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-06-19
 */
public class SkillSlot extends Slot {
    private static final Logger log = LoggerFactory.getLogger(SkillSlot.class);
    private final Skill skill;

    public SkillSlot(Vec2i position, int size, int id, Skill skill) {
        super(position, size, id);
        this.skill = skill;
        setBounds(position.getX(), position.getY(), 48, 28);
    }

    public void update(UIPanel panel) {
        var mouse = MidnightRPG.getInstance().getMouse();
        var leftMouseButtonDown = mouse.getButton() == MouseEvent.BUTTON1;
        if (contains(new Point(mouse.getX(), mouse.getY()))) {
            if (!inside)
                ignorePressed = leftMouseButtonDown;
            this.inside = true;
            if (leftMouseButtonDown && !panel.isMousePressed() && !ignorePressed) {
                panel.setMousePressed(true);
            } else if (!leftMouseButtonDown && panel.isMousePressed() && !ignorePressed) {
                if (panel instanceof SkillsHUD skillPane) {
                    skillPane.setSelectedSkill(skill);
                }
                panel.setMousePressed(false);
            }
        }
        else {
            if (inside) {
                panel.setSelectedItem(null);
                panel.setMousePressed(false);
            }
            inside = false;
        }
    }
}

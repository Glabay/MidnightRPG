package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.ui.components.UILabel;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.renderer.ui.components.UIProgressBar;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;

import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.player.Player;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public class TopHUD extends UIPanel {
    private final Player player;

    private UIProgressBar uiHealthBar;
    private UILabel uiHealthBarLabel;
    private UILabel mobInfo;
    private UILabel mobInfo2;


    public TopHUD(Player player) {
        super(new Vec2i(), new Vec2i(WindowConfig.getWindowWidth(), 32));
        this.player = player;
        addTopPanel();
        display();
    }

    private void addTopPanel() {
        setColor(0x4f4f4f);

        uiHealthBar = new UIProgressBar(new Vec2i(5, 8), new Vec2i(200, 16));
        uiHealthBar.setColor(0x5a5a5a);
        uiHealthBar.setForgroundColor(new Color(0xff5a5a));

        addComponent(uiHealthBar);

        uiHealthBarLabel = (UILabel) new UILabel(new Vec2i(uiHealthBar.position).addX(2).addY(14), "Health:").setColor(0xFFFFFF);
        uiHealthBarLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        uiHealthBarLabel.dropShadow = false;

        addComponent(uiHealthBarLabel);

        mobInfo = (UILabel) new UILabel(new Vec2i((int) WindowConfig.getWindowWidth() / 2, 0).addY(14), "").setColor(0xFFFFFF);
        mobInfo.setFont(new Font("Verdana", Font.BOLD, 16));
        mobInfo.dropShadow = false;

        mobInfo2 = (UILabel) new UILabel(new Vec2i((int) WindowConfig.getWindowWidth() / 2, 0).addY(30), "").setColor(0xFFFFFF);
        mobInfo2.setFont(new Font("Verdana", Font.PLAIN, 12));
        mobInfo2.dropShadow = false;

        addComponent(mobInfo);
        addComponent(mobInfo2);
    }

    public void update() {
        if (visible) {
            if (player != null) {
                uiHealthBar.setProgress(player.getCurrentHealth() / (double) player.getMaxHealth());
                uiHealthBarLabel.updateText("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());

                if (player.getSelectedEntity() != null) {
                    if (entityWithinDist(player.getSelectedEntity())) {
//                        if (player.getSelectedEntity() instanceof NPC) {
//                            NPC target = (NPC) player.getSelectedEntity();
//                            mobInfo.setColor(0xFF13FF);
//                            mobInfo.updateText(player.getSelectedEntity().getName());
//                            mobInfo2.updateText("Level - " + (int) target.getCombatLevel());
//                        }
                    } else player.setSelectedEntity(null);
                } else clearMobInfo();
            }
        }
    }

    private void clearMobInfo() {
        mobInfo.updateText("");
        mobInfo2.updateText("");
    }

    private boolean entityWithinDist(Entity entity) {
        double dist = Vec2i.getDistance(
            new Vec2i(player.getAvatar().getX(), player.getAvatar().getY()),
            new Vec2i(entity.getX(), entity.getY())
        );
        // return true if we're within 11 tiles
        return dist <= (11 << 5);
    }
}

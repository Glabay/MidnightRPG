package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.input.mouse.AWTMouseInputHandler;
import dev.midnightcoder.engine.renderer.ui.components.UIAction;
import dev.midnightcoder.engine.renderer.ui.components.UIButton;
import dev.midnightcoder.engine.renderer.ui.components.UILabel;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.mob.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class BottomHUD extends UIPanel {
    public enum Tabs {
        EQUIPMENT	(1, "equipTab", () -> IO.println("Equipment Tab pressed")),
        SKILL		(2, "skillTab", () -> MidnightRPG.getInstance().getGameScreen().getSkillsHUD().display()),
        QUEST		(3, "questTab", () -> IO.println("Quest Tab pressed.")),
        MUSIC		(4, "musicTab", () -> MidnightRPG.getInstance().getGameScreen().getMusicHUD().display()),
        SETTINGS	(5, "optionsTab", () -> IO.println("Settings Tab pressed.")),
        SPELLBOOK	(6, "spellbookTab", () -> IO.println("Spellbook Tab pressed")),
        COMBAT		(7, "combatTab", () -> IO.println("Combat Tab pressed")),
        INVENTORY	(8, "backpackTab", () -> MidnightRPG.getInstance().getGameScreen().getInventoryHUD().display());

        private final int slotId;
        private final UIAction action;
        private final BufferedImage icon;

        Tabs(int slotId, String imageName, UIAction action) {
            this.slotId = slotId;
            this.action = action;
            icon = getButtonImage(imageName);
        }

        private BufferedImage getButtonImage(String imageName) {
            var imageFile = getClass().getResourceAsStream("/ui/icons/" + imageName + ".png");
            if (imageFile == null)
                throw new IllegalArgumentException("Image file not found: " + imageName);
            try {
                return ImageIO.read(imageFile);
            }
            catch (IOException _) {
                return null;
            }
        }

        public int getSlotId() {
            return slotId;
        }

        public Vec2i getSlot() {
            return new Vec2i(WindowConfig.getWindowWidth() - 32 * slotId, 0);
        }

        public BufferedImage getIcon() {
            return icon;
        }

        public UIAction getAction() {
            return action;
        }
    }

    private final Player player;
    private final AWTMouseInputHandler mouseInputHandler;

    public BottomHUD(Player player, AWTMouseInputHandler mouseInputHandler) {
        super(new Vec2i(0, WindowConfig.getWindowHeight() - 32),
            new Vec2i(WindowConfig.getWindowWidth(), 32));
        this.player = player;
        this.mouseInputHandler = mouseInputHandler;

        addBottomPanel();
        display();
    }

    private void addBottomPanel() {
        setColor(0x4f4f4f);

        var nameLabel = new UILabel(new Vec2i(42, 24), player.getProfile().getUsername());
            nameLabel.setTextColor(new Color(0x5f5f5f));
            nameLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
            nameLabel.dropShadow = true;

        addComponent(nameLabel);
        addComponent(getTabButton(Tabs.SKILL, mouseInputHandler));
        addComponent(getTabButton(Tabs.EQUIPMENT, mouseInputHandler));
        addComponent(getTabButton(Tabs.QUEST, mouseInputHandler));
        addComponent(getTabButton(Tabs.MUSIC, mouseInputHandler));
        addComponent(getTabButton(Tabs.SETTINGS, mouseInputHandler));
        addComponent(getTabButton(Tabs.SPELLBOOK, mouseInputHandler));
        addComponent(getTabButton(Tabs.COMBAT, mouseInputHandler));
        addComponent(getTabButton(Tabs.INVENTORY, mouseInputHandler));
    }

    private UIButton getTabButton(Tabs tab, AWTMouseInputHandler mouse) {
        return new UIButton(mouse, tab.getSlot(), tab.getIcon(), tab.getAction());
    }
}

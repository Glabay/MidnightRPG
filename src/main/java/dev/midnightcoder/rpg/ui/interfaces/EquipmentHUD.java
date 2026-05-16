package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.item.EquipmentManager;
import dev.midnightcoder.rpg.item.EquipmentSlot;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.ui.container.Slot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class EquipmentHUD extends UIPanel {
    public static List<Slot> equipmentSlots = new ArrayList<>();

    private final EquipmentManager equipment = new EquipmentManager(this);

    private final int freeSlots = EquipmentSlot.values().length;

    private final int[] offensive = new int[3];
    private final int[] defStats = new int[freeSlots];

    private final Font font10;
    private final Font font16;
    private final Font font24;
    private final Color color;

    private int	x = 0;
    private int	y = 0;


    public EquipmentHUD(Player player) {
        super(new Vec2i(320, 130), new Vec2i(350, 360));
        background = TextureFactory.createFromImageFile("/ui/equipment_inter.png").image();
        font10 = new Font("Verdana", Font.PLAIN, 10);
        font16 = new Font("Verdana", Font.BOLD, 16);
        font24 = new Font("Verdana", Font.PLAIN, 24);
        color = Color.BLACK;
        initValues();
        setPosition();
        addSlots();
    }

    public void addItemToSlot(Item item, int slot) {
        if (equipmentSlots.get(slot).getItem() == null) {
            equipmentSlots.get(slot).addItem(item);
            equipment.getEquippedItems()[slot] = item;
        }
    }

    private void initValues() {
        Arrays.fill(offensive, 0);
        Arrays.fill(defStats, 0);
    }

    private void setPosition() {
        int resultX;
        int resultY;

        int midGameWinW = WindowConfig.getWindowWidth() / 2;
        int midInterW = (getSize().getWidth() / 2);
        int midGameWinH = WindowConfig.getWindowHeight() / 2;
        int midInterH = (getSize().getHeight() / 2);

        resultX = midGameWinW - midInterW;
        resultY = midGameWinH - midInterH;

        x = resultX;
        y = resultY;
    }

    private void addSlots() {
        int padding = 14;
        int slotSize = 32;
        int spacing = padding + slotSize;
        int slotId = 0;
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 3; r++) {
                equipment.getEquippedItems()[slotId] = null;
                equipmentSlots.add(new Slot(new Vec2i(x + 10 + (spacing * r) + padding, y + 32 + (spacing * c) + padding), slotSize, ++slotId));
            }
        }
    }

    @Override
    public void update() {
        if (visible) {
            for (Slot slot : equipmentSlots) {
                slot.update(this);
            }
            updateInfo();
        }
    }

    private void updateInfo() {
        offensive[0] = equipment.getMeleeBonuses();
        offensive[1] = equipment.getMagicBonuses();
        offensive[2] = equipment.getRangeBonuses();

        for (int i = 0; i < equipment.getEquippedItems().length; i++) {
            defStats[i] = equipment.getDefBonuses(i);
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            renderer.renderImage(background, position.getX() + 17, position.getY() + 74);
            equipmentSlots.forEach(slot -> slot.render(renderer));
            renderer.setColor(color);
            renderer.setFont(font10);
            var slots = "%s/%s".formatted(equipment.getFreeSlots(), freeSlots);
            renderer.renderText(slots, x + 70, y + 230);

            // Titles
            renderer.setFont(font24);
            renderer.renderText("Equipment", x + 36, y + 28);
            renderer.renderText("Defence", x + 215, y + 28);
            renderer.renderText("Combat Stats", x + 8, y + 260);

            renderer.setFont(font16);
            renderer.renderText("Melee:", x + 8, y + 290);
            renderer.renderText("Magic:", x + 8, y + 316);
            renderer.renderText("Range:", x + 8, y + 342);
            renderer.renderText(String.valueOf(offensive[0]), x + 124, y + 290); // Melee
            renderer.renderText(String.valueOf(offensive[1]), x + 124, y + 316); // Magic
            renderer.renderText(String.valueOf(offensive[2]), x + 124, y + 342); // Range

            // Indavidual Equipment Stats
            int spacing = 31;
            int name_col = 64;

            int title_col = 185;
            int offense_col = 300;

            renderer.renderText("Cape", x + title_col, y + name_col);
            renderer.renderText("Head", x + title_col, y + name_col + spacing);
            renderer.renderText("Amulet", x + title_col, y + name_col + spacing * 2);
            renderer.renderText("Weapon", x + title_col, y + name_col + spacing * 3);
            renderer.renderText("Chest", x + title_col, y + name_col + spacing * 4);
            renderer.renderText("Shield", x + title_col, y + name_col + spacing * 5);
            renderer.renderText("Quiver", x + title_col, y + name_col + spacing * 6);
            renderer.renderText("Legs", x + title_col, y + name_col + spacing * 7);
            renderer.renderText("Gloves", x + title_col, y + name_col + spacing * 8);
            renderer.renderText("Boots", x + title_col, y + name_col + spacing * 9);

            renderer.renderText(String.valueOf(defStats[EquipmentSlot.CAPE.ordinal()]), x + offense_col, y + name_col);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.HEAD.ordinal()]), x + offense_col, y + name_col + spacing);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.AMULET.ordinal()]), x + offense_col, y + name_col + spacing * 2);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.WEAPON.ordinal()]), x + offense_col, y + name_col + spacing * 3);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.CHEST.ordinal()]), x + offense_col, y + name_col + spacing * 4);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.SHIELD.ordinal()]), x + offense_col, y + name_col + spacing * 5);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.HANDS.ordinal()]), x + offense_col, y + name_col + spacing * 6);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.LEGS.ordinal()]), x + offense_col, y + name_col + spacing * 7);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.RING.ordinal()]), x + offense_col, y + name_col + spacing * 8);
            renderer.renderText(String.valueOf(defStats[EquipmentSlot.FEET.ordinal()]), x + offense_col, y + name_col + spacing * 9);
        }
    }
}

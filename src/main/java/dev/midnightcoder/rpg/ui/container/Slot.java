package dev.midnightcoder.rpg.ui.container;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.ui.interfaces.InventoryHUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-06
 */
public class Slot extends Rectangle {
    private static final Logger log = LoggerFactory.getLogger(Slot.class);
    private final int slotIndex;
    private final int size;

    private final BufferedImage blankSlot;
    private final Vec2i position;
    private final Font slotFont;
    private final Color itemQuantityColor;

    private BufferedImage icon;

    private Item item;

    private boolean inside = false;
    private boolean ignorePressed = false;

    public Slot(Vec2i position, int size, int id) {
        this.position = position;
        this.size = size;
        this.slotIndex = id;
        this.blankSlot = TextureFactory.createFromImageFile("/items/blankInvSlot.png").image();
        this.slotFont = new Font("Arial", Font.BOLD, 11);
        this.itemQuantityColor = new Color(0, 150, 200);

        setBounds(position.getX(), position.getY(), 32, 32);
    }

    public void update(UIPanel panel) {
        if (item != null)
            icon = item.getIcon().image();
        var mouse = MidnightRPG.getInstance().getMouse();
        var leftMouseButtonDown = mouse.getButton() == MouseEvent.BUTTON1;
        if (contains(new Point(mouse.getX(), mouse.getY()))) {
            if (!inside)
                ignorePressed = leftMouseButtonDown;
            // The Mouse is inside the slot
            panel.setSelectedItem(getItem());
            this.inside = true;
            if (getItem() != null) {
                // Slot does have an item
                if (leftMouseButtonDown && !panel.isMousePressed() && !ignorePressed) {
                    // TODO: Handle Dragging items from one slot to another
                    // System.out.println("Clicked Slot: " + slotID);
                    panel.setMousePressed(true);
                }
                else if (!leftMouseButtonDown && panel.isMousePressed()) {
                    // TODO: Handle Default left click action
                    if (panel instanceof InventoryHUD) {
                        item.handleDefaultOption(this, getItem().getItemDefaultAction());
                    }
//                    if (panel instanceof EquipmentHUD) {
//                        item.unEquipItem(this);
//                    }
                    log.info("Default left click action not implemented for slot: {}", slotIndex);
                    panel.setMousePressed(false);
                }
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

    public void render(Renderer renderer) {
        renderer.drawRectangle(position, size, size, Color.WHITE);
        renderer.setFont(slotFont);
        if (item != null) {
            renderer.renderImage(item.getIcon().image(), (int) getX(), (int) getY());
            if (item.getQuantity() > 1) {
                renderer.renderText(String.valueOf(item.getQuantity()), (int) getX() + 26, (int) getY() + 36);
            }
        }
        else renderer.renderImage(blankSlot, (int) getX(), (int) getY());
    }

    public void addItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public int getSlotId() {
        return slotIndex -1;
    }

    public int getSlotSize() {
        return this.size;
    }

    public boolean hasAnItem() {
        return getItem() != null;
    }

    public Vec2i getPos() {
        return position;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void clear() {
        new Slot(position, size, slotIndex);
    }
}

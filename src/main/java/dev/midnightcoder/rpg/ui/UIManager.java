package dev.midnightcoder.rpg.ui;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class UIManager {

    private final List<UIPanel> panels = new ArrayList<>();

    public void addPanel(UIPanel panel) {
        panels.add(panel);
    }

    public void update() {
        for (var panel : panels) {
            panel.update();
        }
    }

    public void render(Renderer renderer) {
        for (var panel : panels) {
            panel.render(renderer);
        }
    }

    public List<UIPanel> getPanels() {
        return panels;
    }

    /**
     * Removes all UI panels and clears the internal list.
     * Call this when the UI manager is no longer needed (e.g., on scene unload).
     */
    public void clearPanels() {
        panels.clear();
    }
}

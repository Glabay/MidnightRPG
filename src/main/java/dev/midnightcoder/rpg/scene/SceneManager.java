package dev.midnightcoder.rpg.scene;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.scene.Scene;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class SceneManager {
    private Scene currentScene;

    public void setScene(Scene newScene) {
        if (currentScene != null) {
            currentScene.onUnload();
        }

        currentScene = newScene;

        if (currentScene != null) {
            currentScene.onLoad();
        }
    }

    public void update(double deltaTime) {
        if (currentScene != null) {
            currentScene.update(deltaTime);
        }
    }

    public void render(Renderer renderer) {
        if (currentScene != null) {
            currentScene.render(renderer);
        }
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}

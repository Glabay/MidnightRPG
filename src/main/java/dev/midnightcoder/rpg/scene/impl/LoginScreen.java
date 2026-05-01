package dev.midnightcoder.rpg.scene.impl;

import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.scene.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightEngine
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class LoginScreen extends Scene {
    private final Runnable onNewGame;
    private final Runnable onLoadGame;
    private final Runnable onQuit;
    private final InputManager input;

    private int selectedOption = 0;

    public LoginScreen(InputManager input, Runnable... args) {
        if (args.length != 3)
            throw new IllegalArgumentException("Expected 3 arguments for LoginScreen constructor");
        this.input = input;
        this.onNewGame = args[0];
        this.onLoadGame = args[1];
        this.onQuit = args[2];
    }

    @Override
    public void onLoad() {
        IO.println("Loading login screen...");
    }

    @Override
    public void onUnload() {
        IO.println("Unloading login screen...");
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        if (input.isKeyPressed(KeyEvent.VK_UP)) {
            selectedOption--;
            if (selectedOption < 0)
                selectedOption = 2;
        }
        else if (input.isKeyPressed(KeyEvent.VK_DOWN)) {
            selectedOption++;
            if (selectedOption > 2)
                selectedOption = 0;
        }
        else if (input.isKeyPressed(KeyEvent.VK_ENTER))
            onAction();
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        renderer.setFont(new Font("Arial", Font.BOLD, 32));
        renderer.renderText("Welcome to Midnight-RPG", 320, 128);

        renderer.setFont(new Font("Arial", Font.PLAIN, 24));
        renderer.renderText("%sNew Game".formatted(selectedOption == 0 ? "> ": ""), 420, 256);
        renderer.renderText("%sLoad Game".formatted(selectedOption == 1 ? "> ": ""), 418, 288);
        renderer.renderText("%sQuit".formatted(selectedOption == 2 ? "> ": ""), 450, 320);
    }

    public void onAction() {
        switch (selectedOption) {
            case 0 -> onNewGame.run();
            case 1 -> onLoadGame.run();
            case 2 -> onQuit.run();
        }
    }

}

package dev.midnightcoder.rpg.scene.impl;

import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.scene.Scene;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.tile.Tile;

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
    private final KeyboardInputManager input;

    private Font fontBold;
    private Font fontPlain;

    private int selectedOption = 0;

    public LoginScreen(KeyboardInputManager input, Runnable... args) {
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
        fontBold = new Font("Arial", Font.BOLD, 80);
        fontPlain = new Font("Arial", Font.PLAIN, 42);
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
        renderer.setColor(Color.BLUE);
        renderer.setFont(fontBold);
        var title = "Midnight-RPG";
        renderer.renderText(title, getTextCentered(renderer, title), Tile.TILE_SIZE * 4);

        renderer.setFont(fontPlain);
        renderer.renderText("%sNEW GAME".formatted(selectedOption == 0 ? "> ": ""), getTextCentered(renderer, "NEW GAME"), Tile.TILE_SIZE * 14);
        renderer.renderText("%sLOAD GAME".formatted(selectedOption == 1 ? "> ": ""), getTextCentered(renderer, "LOAD GAME"), Tile.TILE_SIZE * 16);
        renderer.renderText("%sQUIT".formatted(selectedOption == 2 ? "> ": ""), getTextCentered(renderer, "QUIT"), Tile.TILE_SIZE * 18);
    }

    private int getTextCentered(Renderer renderer, String text) {
        var graphics = renderer.getGraphics2D();
        var fontMetrics = graphics.getFontMetrics();
        var textWidth = fontMetrics.stringWidth(text);
        var screenWidth = WindowConfig.getWindowWidth();

        return (screenWidth - textWidth) / 2;
    }

    public void onAction() {
        switch (selectedOption) {
            case 0 -> onNewGame.run();
            case 1 -> onLoadGame.run();
            case 2 -> onQuit.run();
        }
    }

}

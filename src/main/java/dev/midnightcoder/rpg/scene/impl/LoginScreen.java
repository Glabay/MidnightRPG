package dev.midnightcoder.rpg.scene.impl;

import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.scene.Scene;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.assets.audio.MusicTrack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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

    private final MusicTrack music;
    private final BufferedImage background;
    private final BufferedImage wizard;
    private final BufferedImage loginBox;

    private int selectedOption = 0;

    public LoginScreen(KeyboardInputManager input, Runnable... args) {
        if (args.length != 3)
            throw new IllegalArgumentException("Expected 3 arguments for LoginScreen constructor");
        this.input = input;
        this.onNewGame = args[0];
        this.onLoadGame = args[1];
        this.onQuit = args[2];
        this.music = new MusicTrack();
        this.background = TextureFactory.createFromImageFile("/ui/title-banner.png").image();
        this.wizard = TextureFactory.createFromImageFile("/texture/entity/player/wizard/wizard.png").image();
        this.loginBox = TextureFactory.createFromImageFile("/ui/center-login.png").image();
    }

    @Override
    public void onLoad() {
        IO.println("Loading login screen...");
        fontBold = new Font("Arial", Font.BOLD, 80);
        fontPlain = new Font("Arial", Font.PLAIN, 42);

        music.loadAudioFiles();
        music.setTrack(0);
        music.play();
        music.loop();
    }

    @Override
    public void onUnload() {
        IO.println("Unloading login screen...");
        music.stop();
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
        renderer.renderImage(background, Tile.TILE_SIZE * 5, 42, (int) (background.getWidth() * 1.7D), background.getHeight() + 32);
        renderer.setFont(fontBold);
        var title = "Midnight-RPG";
        renderer.renderText(title, getTextCentered(renderer, title), Tile.TILE_SIZE * 4);

        renderer.renderImage(wizard, Tile.TILE_SIZE * 14, 196, 128, 128);
        renderer.renderImage(loginBox, (WindowConfig.getWindowWidth() - loginBox.getWidth()) / 2, 300);

        renderer.setFont(fontPlain);
        var newGame = getFormattedText(0, "NEW GAME");
        var loadGame = getFormattedText(1, "LOAD GAME");
        var quit = getFormattedText(2, "QUIT");

        renderer.renderText(newGame, getTextCentered(renderer, newGame), Tile.TILE_SIZE * 14);
        renderer.renderText(loadGame, getTextCentered(renderer, loadGame), Tile.TILE_SIZE * 16);
        renderer.renderText(quit, getTextCentered(renderer, quit), Tile.TILE_SIZE * 18);
    }

    private String getFormattedText(int index, String string) {
        return "%s%s".formatted(selectedOption == index ? "> ": "", string);
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

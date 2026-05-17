package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.DelayedTask;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.rpg.MidnightRPG;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class DialogueInterface extends UIPanel {
    private final Font font18;
    private final Font font14B;
    private final Font font12B;

    private Rectangle c2c; // Click to continue box

    private String dialogueTitle;
    private String[] dialogueLines;

    private boolean inside = false;
    private boolean pressed = false;
    private boolean winOpen = false;
    private boolean ignorePressed = false;

    public DialogueInterface() {
        super(new Vec2i((WindowConfig.getWindowWidth() - 400) / 2, WindowConfig.getWindowHeight() - 175), new Vec2i(400, 145));
        background = TextureFactory.createFromImageFile("/ui/dialogue_background.png").image();
        this.font18 = new Font("Verdana", Font.PLAIN, 18);
        this.font14B = new Font("Verdana", Font.BOLD, 14);
        this.font12B = new Font("Verdana", Font.BOLD, 10);
    }

    public void sendDialogue(String title, String... lines) {
        this.dialogueTitle = title;
        this.dialogueLines = lines;
        display();
    }

    public void update() {
        if(visible) {
            var mouse = MidnightRPG.getInstance().getMouse();
            var leftMouseButtonDown = mouse.getButton() == MouseEvent.BUTTON1;
            if (c2c != null && c2c.contains(new Point(mouse.getX(), mouse.getY()))) {
                if (!inside)
                    ignorePressed = leftMouseButtonDown;
                inside = true;
                if (!pressed && !ignorePressed && leftMouseButtonDown)
                    pressed = true;
                else if (mouse.getButton() == MouseEvent.NOBUTTON) {
                    if (pressed) {
                        pressed = false;
                        display();
                    }
                    ignorePressed = false;
                }
            }
            else {
                if (inside)
                    pressed = false;
                inside = false;
            }
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (visible) {
            int x = position.getX();
            int y = position.getY();
            int width = size.getWidth();
            int height = size.getHeight();

            renderer.renderImage(background, x, y, width, height);
            renderer.setColor(Color.BLACK);

            // Title - Rendered above the box as in original intention but centered
            renderer.setFont(font12B);
            int titleX = getTextCentered(renderer, dialogueTitle);
            renderer.renderText(dialogueTitle, titleX, y + 14);

            renderer.setFont(font14B);
            int startY = y + 48;
            if (dialogueLines != null) {
                for (int i = 0; i < dialogueLines.length; i++) {
                    String line = dialogueLines[i];
                    int lx = getTextCentered(renderer, line);
                    int ly = startY + (i * 22);
                    renderer.renderText(line, lx, ly);
                }
            }

            // Click to close - Centered at the bottom of the box
            String ctc = "Click to close.";
            int ctcX = getTextCentered(renderer, ctc);
            int ctcY = y + height - 15;
            renderer.setColor(Color.BLUE);
            renderer.setFont(font18);
            renderer.renderText(ctc, ctcX, ctcY);

            // Update click-to-continue rectangle for interaction
            c2c = new Rectangle(ctcX, ctcY - 12, getTextWidth(renderer, ctc), 16);
        }
    }

    public void sendInfoInter(String title, String... info) {
        if (winOpen) return;
        winOpen = true;
        sendDialogue(title, info);
        // after 2 seconds, close the window
        DelayedTask.schedule(() -> {
            display();
            winOpen = false;
        }, 2000);
    }
}

package dev.midnightcoder.rpg.ui.interfaces;

import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.renderer.ui.components.UIPanel;
import dev.midnightcoder.engine.util.DelayedTask;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class DialogueInterface extends UIPanel {
    private static final Logger log = LoggerFactory.getLogger(DialogueInterface.class);

    private final Player player;

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


    public DialogueInterface(Player player) {
        super(new Vec2i(345, WindowConfig.getWindowHeight() - 175), new Vec2i(400, 145));
        this.player = player;
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

    private String getLongestString(String... strings) {
        var longest = "";
        if (strings == null || strings.length > 3) {
            log.debug("Messages are not within the range, must have 1-3 lines");
            return longest;
        }
        for (var str : strings) {
            if (str.length() > longest.length())
                longest = str;
        }
        return longest;
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
            renderer.setColor(Color.BLACK);
            renderer.setFont(font18);

            var winX = position.getX() + (WindowConfig.getWindowWidth() / 2) - (size.getWidth() / 2);
            var winY = WindowConfig.getWindowHeight() - size.getHeight();

            var msgX = (winX + (size.getWidth() / 2) - 64) - (getTextCentered(renderer, getLongestString(dialogueLines)));
            var msgY = winY + (size.getHeight() / 2);

            var titleX = ((winX + (size.getWidth() / 2) + getTextWidth(renderer, dialogueTitle) / 2)) - (getTextCentered(renderer, dialogueTitle));
            switch (dialogueLines.length) {
                case 1:
                    renderer.renderImage(background, position.getX(), position.getY(), size.getWidth(), size.getHeight() - 25);
                    renderer.setFont(font12B);
                    renderer.renderText(dialogueTitle, titleX, winY - 18);

                    renderer.setFont(font14B);
                    renderer.renderText(dialogueLines[0], msgX, msgY - 56);
                    break;
                case 2:
                    renderer.renderImage(background, winX, winY, size.getWidth(), size.getHeight());
                    renderer.setFont(font12B);
                    renderer.renderText(dialogueTitle, titleX, winY - 18);

                    renderer.setFont(font14B);
                    renderer.renderText(dialogueLines[0], msgX, msgY - 56);
                    renderer.renderText(dialogueLines[1], msgX, msgY - 48);
                    break;
                default:
                    renderer.renderImage(background, winX, winY, size.getWidth(), size.getHeight());
                    renderer.setFont(font12B);
                    renderer.renderText(dialogueTitle, titleX, winY - 18);

                    renderer.setFont(font14B);
                    renderer.renderText(dialogueLines[0], msgX, msgY - 56);
                    renderer.renderText(dialogueLines[1], msgX, msgY - 48);
                    renderer.renderText(dialogueLines[2], msgX - 32, msgY - 24);
                    break;
            }
            renderer.setFont(font14B);
            var ctc = "Click to close.";
            var ctcY = winY + (size.getHeight() / 2);

            c2c = new Rectangle(titleX, ctcY, getTextWidth(renderer, ctc), 16);
            renderer.renderText(ctc, titleX, ctcY);
        }
    }

    public void sendInfoInter(String title, String info) {
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

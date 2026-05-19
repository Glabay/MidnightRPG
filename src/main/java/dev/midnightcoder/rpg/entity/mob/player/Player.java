package dev.midnightcoder.rpg.entity.mob.player;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.dialogue.DialogueSession;
import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.combat.CombatStats;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.PlayerAvatar;
import dev.midnightcoder.rpg.entity.skill.SkillSet;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.inventory.container.Backpack;
import dev.midnightcoder.rpg.inventory.container.Equipment;
import dev.midnightcoder.rpg.item.Item;
import dev.midnightcoder.rpg.ui.interfaces.Inventory;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Mob {
    private final int SPRITESHEET_ID = 5;

    private final PlayerAvatar playerAvatar;

    private final PlayerMovement playerMovement;
    private final KeyboardInputManager input;
    private final PlayerProfile profile;
    private final SkillSet skillSet;
    private final Backpack backpack;
    private final Equipment equipment;
    private final CombatStats combatStats;

    private Entity selectedEntity;
    private Inventory currentInventoryView;
    private DialogueSession dialogueSession;

    public Player(String username, GameMap currentMap, KeyboardInputManager input) {
        this.input = input;

        playerMovement = new PlayerMovement(currentMap.getTileMap());
        playerAvatar = new PlayerAvatar(new Vec2i(37 << 5, 50 << 5), currentMap, this.input, playerMovement);
        profile = new PlayerProfile(username);
        skillSet = new SkillSet(this);
        backpack = new Backpack();
        equipment = new Equipment();
        combatStats = new CombatStats(getMaxHealth());

        // load up default details
        loadDefaults();

        // load Saved details
        loadPlayerSettings();

        loadAnimatedFrames();
        var frames = getAvatar().getAnimatedFrames(Direction.SOUTH);
        if (frames != null && frames.length > 0) {
            getAvatar().animatedSprite.setFrames(frames);
            getAvatar().setAvatarTexture(getAvatar().animatedSprite.getCurrentFrame());
        }
    }

    @Override
    public void update(double delta) {
        playerAvatar.updateHitbox();
        // Movement logic
        if (input.isKeyHeld(KeyEvent.VK_SHIFT))
            speed = 4; // running
        else
            speed = 3;

        var dx = (int) (getAvatar().getMoveX() * speed * delta);
        var dy = (int) (getAvatar().getMoveY() * speed * delta);

        var wasMoving = moving;
        moving = lastX != getAvatar().getX() || lastY != getAvatar().getY();

        if (moving)
            getAvatar().animatedSprite.update(delta);
        else if (wasMoving)
            getAvatar().animatedSprite.reset();

        getAvatar().setAvatarTexture(getAvatar().animatedSprite.getCurrentFrame());

        lastX = getAvatar().getX();
        lastY = getAvatar().getY();

        playerMovement.move(getAvatar(), dx, dy);

        // Camera follow
        var targetX = getX() + (getAvatar().getWidth() / 2f);
        var targetY = getY() + (getAvatar().getHeight() / 2f);

        getAvatar().getCurrentMap()
            .getCamera()
            .follow(targetX, targetY);
    }

    @Override
    public void render(Renderer renderer) {
        playerAvatar.render(renderer);
    }

    private void loadDefaults() {
        playerAvatar.setTexture(TextureFactory.createFromImageFile("/texture/entity/player/wizard/wizard.png"));
        playerAvatar.getHitbox().setOffset(3, 5);
        playerAvatar.setHitboxDimension(26, 27);
        // Assign some HP to the player
        skillSet.getSkill(SkillType.HITPOINTS).addExp(900);
    }

    private void loadAnimatedFrames() {
        // set the NPC AnimatedSprites
        var cacheMan = CacheReader.getInstance().getCacheManager();
        var spriteSheets = cacheMan.getSpriteSheets();
        var spriteSheet = spriteSheets.get(SPRITESHEET_ID);
        var cachedSpriteIndex = spriteSheet.getSpriteId();
        var cachedSprite = CacheReader.getInstance().getTexture(cachedSpriteIndex);

        for (int row = 0; row < spriteSheet.getRows(); row++) {
            var animatedFrames = new BufferedImage[spriteSheet.getCols()];
            for (int col = 0; col < spriteSheet.getCols(); col++) {
                var frame = cachedSprite.image().getSubimage(
                    col * spriteSheet.getFrameWidth(),
                    row * spriteSheet.getFrameHeight(),
                    spriteSheet.getFrameWidth(),
                    spriteSheet.getFrameHeight()
                );
                animatedFrames[col] = frame;
            }
            switch (row) {
                case 0 -> getAvatar().setAnimatedFrames(Direction.SOUTH, animatedFrames);
                case 1 -> getAvatar().setAnimatedFrames(Direction.NORTH, animatedFrames);
                case 2 -> getAvatar().setAnimatedFrames(Direction.WEST, animatedFrames);
                default -> getAvatar().setAnimatedFrames(Direction.EAST, animatedFrames);
            }
        }
    }

    private void loadPlayerSettings() {
        // update their position on this map
        // set the volume to the last value the player set it to
        MidnightRPG.getInstance().getGameScreen().adjustVolume(profile.getMasterVolume());
        // load SkillSet
        // load Backpack
        // load Equipment
        // load CombatStats
    }

    public int getMaxHealth() {
        return skillSet.getSkill(SkillType.HITPOINTS).getLevel();
    }

    public int getCurrentHealth() {
        return combatStats.getCurrentHealth();
    }

    public boolean isDead() {
        return combatStats.isDead();
    }

    /// Simple way to add an item to the player for now, to be removed and improved later
    public void addItem(Item item) {
        MidnightRPG.getInstance()
            .getGameScreen()
            .getInventoryHUD()
            .addItem(item);
    }

    /// Setters here

    public void setSelectedEntity(Entity entity) {
        this.selectedEntity = entity;
    }

    public void setCurrentInventoryView(Inventory inventory) {
        this.currentInventoryView = inventory;
    }

    /// Getters Below here

    public Inventory getCurrentInventoryView() {
        return currentInventoryView;
    }

    public PlayerAvatar getAvatar() {
        return playerAvatar;
    }

    public CombatStats getCombatStats() {
        return combatStats;
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getX() {
        return playerAvatar.getX();
    }

    public int getY() {
        return playerAvatar.getY();
    }

    public Vec2i getPosition() {
        return new Vec2i(playerAvatar.getX(), playerAvatar.getY());
    }

    public DialogueSession getDialogueSession() {
        return dialogueSession;
    }

    public void setDialogueSession(DialogueSession session) {
        this.dialogueSession = session;
    }

    @Override
    public int getWidth() {
        return playerAvatar.getWidth();
    }

    @Override
    public int getHeight() {
        return playerAvatar.getHeight();
    }

}

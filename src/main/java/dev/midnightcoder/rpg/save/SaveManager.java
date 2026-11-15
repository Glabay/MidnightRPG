package dev.midnightcoder.rpg.save;

import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.skill.Skill;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.item.EquipmentSlot;
import dev.midnightcoder.rpg.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton managing the SQLite connection to ./saves/player.db for player save/load operations.
 */
public class SaveManager {
    private static final Logger log = LoggerFactory.getLogger(SaveManager.class);
    private static final String DB_DIR = "./saves";
    private static final String DB_PATH = DB_DIR + "/player.db";
    private static final String JDBC_URL = "jdbc:sqlite:" + DB_PATH;

    private Connection connection;

    public SaveManager() {
    }

    public void init() {
        try {
            Files.createDirectories(Path.of(DB_DIR));

            connection = DriverManager.getConnection(JDBC_URL);
            connection.setAutoCommit(true);

            createTables();
            log.info("SaveManager initialized: database at {}", DB_PATH);
        }
        catch (SQLException e) {
            log.error("Failed to initialize SaveManager database", e);
        }
        catch (Exception e) {
            log.error("Failed to create saves directory", e);
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS profiles (
                        username TEXT NOT NULL,
                        master_volume REAL NOT NULL DEFAULT 0.5
                    )
                """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS skills (
                        skill_type TEXT NOT NULL PRIMARY KEY,
                        experience INT NOT NULL DEFAULT 0
                    )
                """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS backpack (
                        slot_index INT NOT NULL PRIMARY KEY,
                        item_id INT NOT NULL,
                        quantity INT NOT NULL
                    )
                """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS equipment (
                        slot_name TEXT NOT NULL PRIMARY KEY,
                        item_id INT NOT NULL,
                        quantity INT NOT NULL
                    )
                """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS position (
                        x INT NOT NULL DEFAULT 0,
                        y INT NOT NULL DEFAULT 0
                    )
                """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS combat (
                        current_health INT NOT NULL DEFAULT 10
                    )
                """);

            log.debug("Database tables created or verified");
        }
    }

    /**
     * Returns true if a save exists (profile table has a row).
     */
    public boolean hasSave() {
        if (connection == null) {
            return false;
        }
        try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM profiles")) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            log.error("Failed to check if save exists", e);
        }
        return false;
    }

    /**
     * Saves all player data to the database in a single transaction.
     * Replaces any existing save.
     *
     * @param player the player to save
     */
    public void savePlayer(Player player) {
        if (connection == null) {
            log.warn("SaveManager not initialized; call init() first");
            return;
        }
        try {
            connection.setAutoCommit(false);

            // Clear existing data
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM combat");
                stmt.execute("DELETE FROM position");
                stmt.execute("DELETE FROM equipment");
                stmt.execute("DELETE FROM backpack");
                stmt.execute("DELETE FROM skills");
                stmt.execute("DELETE FROM profiles");
            }

            // Save profile
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO profiles (username, master_volume) VALUES (?, ?)")) {
                ps.setString(1, player.getProfile().getUsername());
                ps.setFloat(2, player.getProfile().getMasterVolume());
                ps.executeUpdate();
            }

            // Save skills
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO skills (skill_type, experience) VALUES (?, ?)")) {
                for (SkillType skillType : SkillType.values()) {
                    Skill skill = player.getSkillSet().getSkill(skillType);
                    ps.setString(1, skillType.name());
                    ps.setInt(2, skill.getExperienceInt());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Save backpack
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO backpack (slot_index, item_id, quantity) VALUES (?, ?, ?)")) {
                Item[] backpackItems = player.getBackpack().getItems();
                for (int i = 0; i < backpackItems.length; i++) {
                    Item item = backpackItems[i];
                    if (item != null) {
                        ps.setInt(1, i);
                        ps.setInt(2, item.getDefinition().getId());
                        ps.setInt(3, item.getQuantity());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            // Save equipment
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO equipment (slot_name, item_id, quantity) VALUES (?, ?, ?)")) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    Item item = player.getEquipment().getEquippedItem(slot);
                    if (item != null) {
                        ps.setString(1, slot.name());
                        ps.setInt(2, item.getDefinition().getId());
                        ps.setInt(3, item.getQuantity());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }

            // Save position
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO position (x, y) VALUES (?, ?)")) {
                ps.setInt(1, player.getX());
                ps.setInt(2, player.getY());
                ps.executeUpdate();
            }

            // Save combat stats
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO combat (current_health) VALUES (?)")) {
                ps.setInt(1, player.getCurrentHealth());
                ps.executeUpdate();
            }

            connection.commit();
            log.info("Player {} saved successfully", player.getProfile().getUsername());
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Failed to rollback transaction", ex);
            }
            log.error("Failed to save player", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.error("Failed to reset auto-commit", e);
            }
        }
    }

    /**
     * Loads all player data from the database.
     *
     * @return a populated SavedProfile, or null if no save exists
     */
    public SavedProfile loadPlayer() {
        if (connection == null) {
            log.warn("SaveManager not initialized; call init() first");
            return null;
        }
        try {
            // Load profile
            String username = null;
            float masterVolume = 0.5f;
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT username, master_volume FROM profiles LIMIT 1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString("username");
                        masterVolume = rs.getFloat("master_volume");
                    }
                }
            }
            if (username == null) {
                return null;
            }

            // Load skills
            Map<SkillType, Integer> skillXp = new EnumMap<>(SkillType.class);
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT skill_type, experience FROM skills")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        SkillType skillType = SkillType.valueOf(rs.getString("skill_type"));
                        int experience = rs.getInt("experience");
                        skillXp.put(skillType, experience);
                    }
                }
            }

            // Load backpack
            Map<Integer, SavedProfile.SavedItem> backpackItems = new HashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT slot_index, item_id, quantity FROM backpack")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int slotIndex = rs.getInt("slot_index");
                        int itemId = rs.getInt("item_id");
                        int quantity = rs.getInt("quantity");
                        backpackItems.put(slotIndex, new SavedProfile.SavedItem(itemId, quantity));
                    }
                }
            }

            // Load equipment
            Map<EquipmentSlot, SavedProfile.SavedItem> equipmentItems = new HashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT slot_name, item_id, quantity FROM equipment")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        EquipmentSlot slot = EquipmentSlot.valueOf(rs.getString("slot_name"));
                        int itemId = rs.getInt("item_id");
                        int quantity = rs.getInt("quantity");
                        equipmentItems.put(slot, new SavedProfile.SavedItem(itemId, quantity));
                    }
                }
            }

            // Load position
            int positionX = 0;
            int positionY = 0;
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT x, y FROM position LIMIT 1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        positionX = rs.getInt("x");
                        positionY = rs.getInt("y");
                    }
                }
            }

            // Load combat stats
            int currentHealth = 10;
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT current_health FROM combat LIMIT 1")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        currentHealth = rs.getInt("current_health");
                    }
                }
            }

            SavedProfile profile = new SavedProfile(
                    username, masterVolume, skillXp, backpackItems, equipmentItems,
                    positionX, positionY, currentHealth
            );
            log.info("Loaded save for player: {}", username);
            return profile;
        } catch (SQLException e) {
            log.error("Failed to load player", e);
            return null;
        }
    }

    /**
     * Clears all saved data. Useful for testing or resetting progress.
     */
    public void clearSave() {
        if (connection == null) {
            log.warn("SaveManager not initialized; call init() first");
            return;
        }
        try {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM combat");
                stmt.execute("DELETE FROM position");
                stmt.execute("DELETE FROM equipment");
                stmt.execute("DELETE FROM backpack");
                stmt.execute("DELETE FROM skills");
                stmt.execute("DELETE FROM profiles");
            }
            log.info("Save cleared successfully");
        } catch (SQLException e) {
            log.error("Failed to clear save", e);
        }
    }

    /**
     * Closes the database connection. Call this when the game shuts down.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Failed to close database connection", e);
            }
            connection = null;
        }
    }
}

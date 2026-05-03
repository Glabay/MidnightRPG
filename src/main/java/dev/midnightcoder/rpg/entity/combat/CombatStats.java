package dev.midnightcoder.rpg.entity.combat;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class CombatStats {
    private int currentHealth;

    public CombatStats(int initialHealth) {
        this.currentHealth = initialHealth;
    }

    public void damage(int damage) {
        this.currentHealth = Math.max(0, this.currentHealth - damage);
    }

    public void heal(int healing, int maxHealth) {
        this.currentHealth = Math.min(maxHealth, this.currentHealth + healing);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }
}

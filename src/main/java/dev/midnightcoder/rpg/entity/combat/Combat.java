package dev.midnightcoder.rpg.entity.combat;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.Mob;

import java.util.Random;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public abstract class Combat {
    public static final int DEFAULT_DEATH_DELAY = 4;

    protected static final int DEFAULT_ATTACK_SPEED = 90;
    protected static final int DEFAULT_ATTACK_RANGE = 1;
    protected static final Random RANDOM = new Random();

    protected final Mob owner;

    protected Mob target;
    protected int attackSpeed;
    protected int attackDelay;
    protected int attackRange;

    public Combat(Mob owner, int attackSpeed, int attackRange) {
        this.owner = owner;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.attackDelay = 0;
    }

    public Combat(Mob owner, int attackSpeed) {
        this(owner, attackSpeed, DEFAULT_ATTACK_RANGE);
    }

    public Combat(Mob owner) {
        this(owner, DEFAULT_ATTACK_SPEED, DEFAULT_ATTACK_RANGE);
    }

    /**
     * Handles the method call from every tick.
     */
    public void tick() {
        if (attackDelay > 0)
            attackDelay--;

        if (target != null) {
            if (target.isDead() ||
                (getOwner() != null && getOwner().isDead())
            ) {
                reset();
                return;
            }
            if (canAttack(target)) {
                if (attackDelay <= 0) {
                    attack(target);
                    attackDelay = attackSpeed;
                }
            }
        }
    }

    public abstract void attack(Mob target);
    public abstract int calculateDamage();

    public boolean canAttack(Mob target) {
        if (target == null || target.isDead() || (getOwner() != null && getOwner().isDead())) {
            return false;
        }
        return isWithinAttackRange(target);
    }

    public boolean isWithinAttackRange(Mob target) {
        if (target == null || getOwner() == null) {
            return false;
        }
        double distance = Vec2i.getDistance(getOwner().getPosition(), target.getPosition());
        return distance <= (attackRange << 5);
    }

    public void retaliate(Mob attacker) {
        if (attacker == null || attacker.isDead() || (getOwner() != null && getOwner().isDead())) {
            return;
        }
        if (this.target == null || this.target.isDead()) {
            this.target = attacker;
            if (this.attackDelay <= 0) {
                this.attackDelay = this.attackSpeed;
            }
        }
    }

    public void reset() {
        this.target = null;
    }

    public Mob getOwner() {
        return owner;
    }

    public Mob getTarget() {
        return target;
    }

    public void setTarget(Mob target) {
        this.target = target;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public void setAttackDelay(int attackDelay) {
        this.attackDelay = attackDelay;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }
}

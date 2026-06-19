package dev.midnightcoder.rpg.entity.skill;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public enum SkillType {
   ATTACK("⚔"),
   STRENGTH("\uD83D\uDCAA"),
   DEFENCE("\uD83D\uDEE1\uFE0F"),
   RANGED("\uD83C\uDFF9"),
   MAGIC("\uD83D\uDD2E"),
   HITPOINTS("♥"),
   MINING("⛏"),
   BLACKSMITH("\uD83D\uDD28"),
   WOODCUTTING("\uD83C\uDF34"),
   FISHING("\uD83C\uDFA3"),
   COOKING("♨\uFE0F")
    ;

    private final String SkillIcon;

    SkillType(String skillIcon) {
        SkillIcon = skillIcon;
    }

    public String getSkillIcon() {
        return SkillIcon;
    }

    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}

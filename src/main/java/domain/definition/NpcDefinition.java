package domain.definition;

/**
 * Created by Gilles on 13/05/2016.
 */
public final class NpcDefinition {
    private final int id;
    private String name;
    private String examine;
    private int combat;
    private int size;
    private boolean attackable;
    private boolean aggressive;
    private boolean retreats;
    private boolean poisonous;
    private int respawn;
    private int maxHit;
    private int hitpoints;
    private int attackSpeed;
    private int attackAnim;
    private int defenceAnim;
    private int deathAnim;
    private int attackBonus;
    private int defenceMelee;
    private int defenceRange;
    private int defenceMage;
    private int slayerLevel;

    public NpcDefinition(int id) {
        this.id = id;
    }

    public NpcDefinition(int id, String name, String examine, int combat, int size, boolean attackable, boolean aggressive, boolean retreats, boolean poisonous, int respawn, int maxHit, int hitpoints, int attackSpeed, int attackAnim, int defenceAnim, int deathAnim, int attackBonus, int defenceMelee, int defenceRange, int defenceMage, int slayerLevel) {
        this.id = id;
        this.name = name;
        this.examine = examine;
        this.combat = combat;
        this.size = size;
        this.attackable = attackable;
        this.aggressive = aggressive;
        this.retreats = retreats;
        this.poisonous = poisonous;
        this.respawn = respawn;
        this.maxHit = maxHit;
        this.hitpoints = hitpoints;
        this.attackSpeed = attackSpeed;
        this.attackAnim = attackAnim;
        this.defenceAnim = defenceAnim;
        this.deathAnim = deathAnim;
        this.attackBonus = attackBonus;
        this.defenceMelee = defenceMelee;
        this.defenceRange = defenceRange;
        this.defenceMage = defenceMage;
        this.slayerLevel = slayerLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
    }

    public int getCombat() {
        return combat;
    }

    public void setCombat(int combat) {
        this.combat = combat;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAttackable() {
        return attackable;
    }

    public void setAttackable(boolean attackable) {
        this.attackable = attackable;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    public boolean isRetreats() {
        return retreats;
    }

    public void setRetreats(boolean retreats) {
        this.retreats = retreats;
    }

    public boolean isPoisonous() {
        return poisonous;
    }

    public void setPoisonous(boolean poisonous) {
        this.poisonous = poisonous;
    }

    public int getRespawn() {
        return respawn;
    }

    public void setRespawn(int respawn) {
        this.respawn = respawn;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public void setMaxHit(int maxHit) {
        this.maxHit = maxHit;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getAttackAnim() {
        return attackAnim;
    }

    public void setAttackAnim(int attackAnim) {
        this.attackAnim = attackAnim;
    }

    public int getDefenceAnim() {
        return defenceAnim;
    }

    public void setDefenceAnim(int defenceAnim) {
        this.defenceAnim = defenceAnim;
    }

    public int getDeathAnim() {
        return deathAnim;
    }

    public void setDeathAnim(int deathAnim) {
        this.deathAnim = deathAnim;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void setAttackBonus(int attackBonus) {
        this.attackBonus = attackBonus;
    }

    public int getDefenceMelee() {
        return defenceMelee;
    }

    public void setDefenceMelee(int defenceMelee) {
        this.defenceMelee = defenceMelee;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public void setDefenceRange(int defenceRange) {
        this.defenceRange = defenceRange;
    }

    public int getDefenceMage() {
        return defenceMage;
    }

    public void setDefenceMage(int defenceMage) {
        this.defenceMage = defenceMage;
    }

    public int getSlayerLevel() {
        return slayerLevel;
    }

    public void setSlayerLevel(int slayerLevel) {
        this.slayerLevel = slayerLevel;
    }
}

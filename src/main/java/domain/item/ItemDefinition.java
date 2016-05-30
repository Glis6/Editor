package domain.item;

/**
 * Created by Gilles on 13/05/2016.
 */
public class ItemDefinition {

    private final int id;
    private String name = "None";
    private String description = "Null";
    private boolean stackable;
    private int value;
    private boolean noted;
    private boolean isTwoHanded;
    private boolean weapon;
    private EquipmentType equipmentType = EquipmentType.WEAPON;
    private double[] bonus = new double[18];
    private int[] requirement = new int[25];

    public ItemDefinition(int id) {
        this.id = id;
    }

    public ItemDefinition(int id, String name, String description, boolean stackable, int value, boolean noted, boolean isTwoHanded, boolean weapon, EquipmentType equipmentType, double[] bonus, int[] requirement) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stackable = stackable;
        this.value = value;
        this.noted = noted;
        this.isTwoHanded = isTwoHanded;
        this.weapon = weapon;
        this.equipmentType = equipmentType;
        this.bonus = bonus;
        this.requirement = requirement;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isNoted() {
        return noted;
    }

    public void setNoted(boolean noted) {
        this.noted = noted;
    }

    public boolean isTwoHanded() {
        return isTwoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        isTwoHanded = twoHanded;
    }

    public boolean isWeapon() {
        return weapon;
    }

    public void setWeapon(boolean weapon) {
        this.weapon = weapon;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public double[] getBonus() {
        return bonus;
    }

    public void setBonus(double[] bonus) {
        this.bonus = bonus;
    }

    public int[] getRequirement() {
        return requirement;
    }

    public void setRequirement(int[] requirement) {
        this.requirement = requirement;
    }

    @Override
    public String toString() {
        return getName();
    }
}

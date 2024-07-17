package idiotamspielen.vttproject.classifications;

import java.util.HashMap;
import java.util.Map;

public class Weapon extends Item{
    private String weaponType; //i.e. "Melee", "Ranged"
    private int range;
    private Map<String, Integer> damageTypes = new HashMap<>();

    public Weapon(String name, String description, double weight, int value, String weaponType, int range){
        super(name, description, weight, value);
        this.weaponType = weaponType;
        this.range = range;
    }

    public Map<String, Integer> getDamageTypes() {
        return damageTypes;
    }
    public void addDamageType(String type, int value) {
        this.damageTypes.put(type, value);
    }

    public int getDamageValue(String type) {
        return this.damageTypes.getOrDefault(type, 0);
    }

    public String getWeaponType() { return weaponType; }
    public void setWeaponType(String weaponType) { this.weaponType = weaponType; }

    public int getRange() { return range; }
    public void setRange(int range) { this.range = range; }

    @Override
    public String getItemType() {
        return "Weapon";
    }
}

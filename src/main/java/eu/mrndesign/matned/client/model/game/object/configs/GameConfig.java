package eu.mrndesign.matned.client.model.game.object.configs;

import eu.mrndesign.matned.client.model.game.object.element.Hero;
import eu.mrndesign.matned.client.model.game.object.element.Weapon;

import java.util.HashMap;
import java.util.Map;

public class GameConfig {

    private int points;
    private final Hero hero;
    private final Map<String, Weapon> mapNameToWeapon = new HashMap<>();

    public GameConfig(Hero hero, Weapon initWeapon) {
        this.hero = hero;
        mapNameToWeapon.put(initWeapon.getName(), initWeapon);
    }

    public Hero getHero() {
        return hero;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public Map<String, Weapon> getMapNameToWeapon() {
        return mapNameToWeapon;
    }

    public Weapon getWeapon(String name) {
        return mapNameToWeapon.get(name);
    }

    public void putWeapon(Weapon weapon) {
        mapNameToWeapon.put(weapon.getName(), weapon);
    }

}

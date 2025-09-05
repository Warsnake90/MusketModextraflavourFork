package ewewukek.musketmod.recruitscompat;

import com.talhanation.recruits.compat.IWeapon;
import ewewukek.musketmod.recruitscompat.CarbineWeapon;

import java.lang.reflect.Field;
import java.util.List;

public class WeaponCompatInjector {

    public static void injectCustomWeapons() {
        try {
            // Locate the class and field
            Class<?> compatClass = Class.forName("com.talhanation.recruits.compat.WeaponCompat");
            Field weaponsField = compatClass.getDeclaredField("WEAPONS");
            weaponsField.setAccessible(true);

            // Get the current weapon list
            List<IWeapon> weapons = (List<IWeapon>) weaponsField.get(null); // static field

            // Add your custom weapons
            weapons.add(new CarbineWeapon());

            System.out.println("[MusketMod] Injected custom weapons into Recruits compatibility.");
        } catch (Exception e) {
            System.err.println("[MusketMod] Failed to inject weapons into Recruits:");
            e.printStackTrace();
        }
    }
}
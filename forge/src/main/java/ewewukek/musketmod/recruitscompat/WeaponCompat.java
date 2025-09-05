package ewewukek.musketmod.recruitscompat;

import com.talhanation.recruits.compat.IWeapon;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeaponCompat {
    private static final List<IWeapon> WEAPONS = new ArrayList<>();

    public static void registerWeapons() {
        WEAPONS.add(new CarbineWeapon());

    }

    public static Optional<IWeapon> getWeaponForItem(ItemStack stack) {
        return WEAPONS.stream()
                .filter(w -> w.getWeapon() != null && w.getWeapon().equals(stack.getItem()))
                .findFirst();
    }
}
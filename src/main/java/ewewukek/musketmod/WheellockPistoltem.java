package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WheellockPistoltem extends GunItem {
    public WheellockPistoltem(Properties properties) {
        super(properties);
    }

    @Override
    public float bulletStdDev() {
        return 4f;
    }

    @Override
    public float bulletSpeed() {
        return 180;
    }

    @Override
    public int pelletCount() {
        return 1;
    }

    @Override
    public float damage() {
        return 17;
    }

    @Override
    public boolean twoHanded() {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.literal("Don't get your hand caught. ").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public SoundEvent fireSound(ItemStack stack) {
        if (hasFlame(stack)) {
            return Sounds.WHEELLOCK_PISTOL_FIRE;
        } else {
            return Sounds.WHEELLOCK_PISTOL_FIRE;
        }
    }
}


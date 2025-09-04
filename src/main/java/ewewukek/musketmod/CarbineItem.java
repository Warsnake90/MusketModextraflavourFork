package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CarbineItem extends GunItem {
    public CarbineItem(Properties properties) {
        super(properties);
    }

    @Override
    public float bulletStdDev() {
        return 3.5f;
    }

    @Override
    public float bulletSpeed() {
        return 250;
    }

    @Override
    public int pelletCount() {
        return 1;
    }

    @Override
    public float damage() {
        return 11;
    }

    @Override
    public boolean twoHanded() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.literal("being Lighter than a musket, ").withStyle(ChatFormatting.BLUE));
        list.add(Component.literal("its ideal for mounted shooters.").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public SoundEvent fireSound(ItemStack stack) {
        if (hasFlame(stack)) {
            return Sounds.CARBINE_FIRE;
        } else {
            return Sounds.CARBINE_FIRE;
        }
    }
}


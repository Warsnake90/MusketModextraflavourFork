package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WheellockItem extends GunItem {
    public WheellockItem(Properties properties) {
        super(properties);
    }

    @Override
    public float bulletStdDev() {
        return 2f;
    }

    @Override
    public float bulletSpeed() {
        return 230;
    }

    @Override
    public int pelletCount() {
        return 1;
    }

    @Override
    public float damage() {
        return 14;
    }

    @Override
    public boolean twoHanded() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.literal("Why does it have a wheel?!").withStyle(ChatFormatting.BLUE));
        list.add(Component.literal("No like, what the fuck.").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public SoundEvent fireSound(ItemStack stack) {
        if (hasFlame(stack)) {
            return Sounds.WHEELLOCK_FIRE;
        } else {
            return Sounds.WHEELLOCK_FIRE;
        }
    }
}


package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MatchlockItem extends GunItem {
    public MatchlockItem(Properties properties) {
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

        list.add(Component.literal("It's really old... And unwieldy. ").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public SoundEvent fireSound(ItemStack stack) {
        if (hasFlame(stack)) {
            return Sounds.MATCHLOCK_FIRE;
        } else {
            return Sounds.MATCHLOCK_FIRE;
        }
    }
}


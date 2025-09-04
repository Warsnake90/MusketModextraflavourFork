package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlunderbussItem extends GunItem {
    public BlunderbussItem(Properties properties) {
        super(properties);
    }

    @Override
    public float bulletStdDev() {
        return Config.blunderbussBulletStdDev;
    }

    @Override
    public float bulletSpeed() {
        return Config.blunderbussBulletSpeed;
    }

    @Override
    public int pelletCount() {
        return Config.blunderbussPelletCount;
    }

    @Override
    public float damage() {
        return Config.blunderbussDamage;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.literal("Effective only at short range, ").withStyle(ChatFormatting.BLUE));
        list.add(Component.literal("Lacking accuracy at long range.").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public SoundEvent fireSound(ItemStack stack) {
        if (hasFlame(stack)) {
            return Sounds.BLUNDERBUSS_FIRE_FLAME;
        } else {
            return Sounds.BLUNDERBUSS_FIRE;
        }
    }
}

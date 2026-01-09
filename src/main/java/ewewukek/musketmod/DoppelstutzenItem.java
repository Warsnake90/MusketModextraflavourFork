package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoppelstutzenItem extends GunItem {
    public DoppelstutzenItem(Properties properties) {
        super(properties);
    }

    @Override
    public float bulletStdDev() {
        return 5;
    }

    @Override
    public float bulletSpeed() {
        return 170;
    }

    @Override
    public int pelletCount() {
        return 2;
    }

    @Override
    public float damage() {
        return 21;
    }

    @Override
    public boolean twoHanded() {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        list.add(Component.literal("Double the barrels, Double the fun.").withStyle(ChatFormatting.BLUE));
    }


    @Override
    public SoundEvent fireSound(ItemStack stack) {
        if (hasFlame(stack)) {
            return Sounds.DOPPELSTUZEN_FIRE;
        } else {
            return Sounds.DOPPELSTUZEN_FIRE;
        }
    }
}


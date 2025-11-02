package ewewukek.musketmod;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MusketItem extends GunItem {
    public final Multimap<Attribute, AttributeModifier> bayonetAttributeModifiers;

    public MusketItem(Item.Properties properties, boolean withBayonet) {
        super(properties);
        if (withBayonet) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", Config.bayonetDamage - 1.5f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                BASE_ATTACK_SPEED_UUID, "Weapon modifier", Config.bayonetSpeed - 2.5f, AttributeModifier.Operation.ADDITION));
            bayonetAttributeModifiers = builder.build();
        } else {
            bayonetAttributeModifiers = null;
        }
    }

    @Override
    public float bulletStdDev() {
        return Config.musketBulletStdDev;
    }

    @Override
    public float bulletSpeed() {
        return Config.musketBulletSpeed;
    }

    @Override
    public float damage() {
        return 14;
    }

    @Override
    public SoundEvent fireSound(ItemStack stack) {
        return Sounds.MUSKET_FIRE;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND && bayonetAttributeModifiers != null
                ? bayonetAttributeModifiers : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);

        boolean bayoneted;

        if (this.bayonetAttributeModifiers != null) {
           bayoneted = true;
        } else {
            bayoneted = false;
        }

            if (bayoneted) {
                list.add(Component.literal("This musket was fitted for cqc.").withStyle(ChatFormatting.BLUE));
            }
            if (!bayoneted) {
            list.add(Component.literal("Standard Firearm of armies around the world.").withStyle(ChatFormatting.BLUE));
            }
        }

    }


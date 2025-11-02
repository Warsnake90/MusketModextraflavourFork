package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BayonetItem extends SwordItem {

    public BayonetItem(Properties properties) {
        super(Tiers.IRON, 1, -2.4F, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal("Can be mounted to the front of muskets").withStyle(ChatFormatting.BLUE));
    }
}
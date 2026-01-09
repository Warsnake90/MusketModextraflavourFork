package ewewukek.musketmod;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScopedMusketItem extends MusketItem {
    public static final int RECOIL_TICKS = 12;
    public static final float RECOIL_AMOUNT = 0.25f;

    // for client-side logic
    public static boolean isScoping;
    public static int recoilTicks;

    public ScopedMusketItem(Properties properties) {
        super(properties, false);
    }

    @Override
    public float bulletStdDev() {
        return 1f;
    }

    @Override
    public float damage() {
        return 25;
    }

    @Override
    public float bulletSpeed() {
        return 350f;
    }

    @Override
    public float bulletDropReduction() {
        return 1.0f - 0.5f;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.literal("Rifling allows this one to shoot far and fast.").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public int hitDurabilityDamage() {
        return 2;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean wasLoaded = GunItem.isLoaded(player.getItemInHand(hand));
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);

        if (level.isClientSide && wasLoaded && result.getResult().consumesAction()) {
            ScopedMusketItem.recoilTicks = ScopedMusketItem.RECOIL_TICKS;
        }
        return result;
    }
}

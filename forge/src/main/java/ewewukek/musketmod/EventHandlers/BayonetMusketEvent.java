package ewewukek.musketmod.EventHandlers;

import ewewukek.musketmod.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "musketmod", bus = EventBusSubscriber.Bus.FORGE)
public class BayonetMusketEvent {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = player.level();


        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (level.isClientSide()) return;
        if (!player.isShiftKeyDown()) return;
        if (player.getInventory().getFreeSlot() == -1) {return;}
        if (mainHand.isEnchanted()) {return;}
        if (mainHand.hasCustomHoverName()) {return;}
        if (offHand.isEnchanted()) {return;}
        if (offHand.hasCustomHoverName()) {return;}

        Item ITEM_A = Items.MUSKET;
        Item ITEM_B = Items.BAYONET;
        Item ITEM_C = Items.MUSKET_WITH_BAYONET;

        if (mainHand.getItem() == ITEM_A && offHand.getItem() == ITEM_B) {
            event.setCanceled(true);
            mainHand.shrink(1);
            offHand.shrink(1);

            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ITEM_C));
        }
        if (mainHand.getItem() == ITEM_C){
            event.setCanceled(true);
            mainHand.shrink(1);
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ITEM_A));
            player.getInventory().add(new ItemStack(ITEM_B, 1));
        }
    }
}
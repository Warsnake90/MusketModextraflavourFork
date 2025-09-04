package ewewukek.musketmod;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class VanillaHelper {
    public static void modifyLootTableItems(ResourceLocation location, LootContext context, Consumer<ItemStack> adder) {
        if (location.getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE)) {
            LootTable modTable = context.getLevel().getServer().getLootData()
                .getLootTable(MusketMod.resource(location.getPath()));
            if (modTable != null) {
                modTable.getRandomItemsRaw(context,
                    LootTable.createStackSplitter(context.getLevel(), adder));
            }
        }
    }

    public static boolean canEnchant(Enchantment enchantment, ItemStack stack) {
        if (!(stack.getItem() instanceof GunItem)) {
            return false;
        }

        String name = "";
        if (enchantment == Enchantments.BANE_OF_ARTHROPODS) {
            name = "bane_of_arthropods";

        } else if (enchantment == Enchantments.FIRE_ASPECT) {
            name = "fire_aspect";

        } else if (enchantment == Enchantments.FLAMING_ARROWS) {
            name = "flame";

        } else if (enchantment == Enchantments.INFINITY_ARROWS) {
            name = "infinity";

        } else if (enchantment == Enchantments.KNOCKBACK) {
            name = "knockback";

        } else if (enchantment == Enchantments.MOB_LOOTING) {
            name = "looting";

        } else if (enchantment == Enchantments.POWER_ARROWS) {
            name = "power";

        } else if (enchantment == Enchantments.QUICK_CHARGE) {
            name = "quick_charge";

        } else if (enchantment == Enchantments.SHARPNESS) {
            name = "sharpness";

        } else if (enchantment == Enchantments.SMITE) {
            name = "smite";

        } else {
            return enchantment == Enchantments.UNBREAKING
                || enchantment == Enchantments.MENDING;
        }

        TagKey<Item> tag = TagKey.create(Registries.ITEM, MusketMod.resource("enchantable/" + name));
        return stack.is(tag);
    }

    public static ItemStack getRandomWeapon(Entity entity, ResourceLocation location) {
        ServerLevel serverLevel = (ServerLevel)entity.level();
        LootTable table = serverLevel.getServer().getLootData().getLootTable(location);
        LootParams params = new LootParams.Builder((ServerLevel)entity.level())
            .withParameter(LootContextParams.THIS_ENTITY, entity)
            .withParameter(LootContextParams.ORIGIN, entity.position())
            .create(LootContextParamSets.SELECTOR);
        List<ItemStack> items = table.getRandomItems(params);
        if (items.size() > 0) {
            return items.get(0);
        } else {
            return ItemStack.EMPTY;
        }
    }
}

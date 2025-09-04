package ewewukek.musketmod;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class ModLootModifier extends LootModifier {
    public static final Codec<ModLootModifier> CODEC = RecordCodecBuilder.create(instance ->
        codecStart(instance)
        .apply(instance, ModLootModifier::new)
    );

    public ModLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    @Override
    @NotNull
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> items, LootContext context) {
        VanillaHelper.modifyLootTableItems(context.getQueriedLootTableId(), context, items::add);
        return items;
    }
}

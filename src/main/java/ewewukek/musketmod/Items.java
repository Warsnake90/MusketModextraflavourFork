package ewewukek.musketmod;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Items {
    public static final Item MUSKET = new MusketItem(new Item.Properties()
        .durability(Config.musketDurability), false);
    public static final Item MUSKET_WITH_BAYONET = new MusketItem(new Item.Properties()
        .durability(Config.musketDurability), true);
    public static final Item MUSKET_WITH_SCOPE = new ScopedMusketItem(new Item.Properties()
        .durability(Config.scopedMusketDurability));
    public static final Item BLUNDERBUSS = new BlunderbussItem(new Item.Properties()
        .durability(Config.blunderbussDurability));
    public static final Item PISTOL = new PistolItem(new Item.Properties()
        .durability(Config.pistolDurability));
    public static final Item CARTRIDGE = new CartridgeItem(new Item.Properties());

    //

    public static final Item DOPPELSTUTZEN = new DoppelstutzenItem(new Item.Properties().stacksTo(1));
    public static final Item CARBINE = new CarbineItem(new Item.Properties().stacksTo(1));
    public static final Item WHEELLOCK = new WheellockItem(new Item.Properties().stacksTo(1));
    public static final Item WHEELLOCKPISTOL = new WheellockPistoltem(new Item.Properties().stacksTo(1));
    public static final Item BAYONET = new SwordItem(
            Tiers.IRON, 2, -2, new Item.Properties().stacksTo(1).durability(150));

    //

    public static final Item PISTOL_BODY = new Item(new Item.Properties().stacksTo(16));
    public static final Item CARBINE_BODY = new Item(new Item.Properties().stacksTo(16));
    public static final Item MUSKET_BODY = new Item(new Item.Properties().stacksTo(16));
    public static final Item FLINT_LOCK = new Item(new Item.Properties().stacksTo(16));
    public static final Item MATCH_LOCK = new Item(new Item.Properties().stacksTo(16));
    public static final Item WHEEL_LOCK = new Item(new Item.Properties().stacksTo(16));
    public static final Item SHORT_BARREL = new Item(new Item.Properties().stacksTo(16));
    public static final Item MEDIUM_BARREL = new Item(new Item.Properties().stacksTo(16));
    public static final Item LONG_BARREL = new Item(new Item.Properties().stacksTo(16));
    public static final Item BLUNDERBUSS_BARREL = new Item(new Item.Properties().stacksTo(16));
    public static final Item RIFLED_BARREL = new Item(new Item.Properties().stacksTo(16));
    public static final Item UNFINISHED_RIFLE = new Item(new Item.Properties().stacksTo(1));


    //

    public static final ResourceLocation EMPTY_SLOT_MUSKET = MusketMod.resource("item/empty_slot_musket");
    public static final ResourceLocation EMPTY_SLOT_SPYGLASS = new ResourceLocation("item/empty_slot_spyglass");

    public static final Item MUSKET_UPGRADE = new SmithingTemplateItem(
        Component.translatable(Util.makeDescriptionId("item",
            MusketMod.resource("musket")))
            .withStyle(SmithingTemplateItem.DESCRIPTION_FORMAT),
        Component.translatable(Util.makeDescriptionId("item",
            MusketMod.resource("musket_upgrade.ingredients")))
            .withStyle(SmithingTemplateItem.DESCRIPTION_FORMAT),
        Component.translatable(Util.makeDescriptionId("item",
            MusketMod.resource("musket_upgrade")))
            .withStyle(SmithingTemplateItem.TITLE_FORMAT),
        Component.translatable(Util.makeDescriptionId("item",
            MusketMod.resource("musket_upgrade.base_slot_description"))),
        Component.translatable(Util.makeDescriptionId("item",
            MusketMod.resource("musket_upgrade.additions_slot_description"))),
        List.of(EMPTY_SLOT_MUSKET),
        List.of(SmithingTemplateItem.EMPTY_SLOT_SWORD, EMPTY_SLOT_SPYGLASS)
    );

    public static void register(BiConsumer<String, Item> helper) {
        helper.accept("musket", MUSKET);
        helper.accept("musket_with_bayonet", MUSKET_WITH_BAYONET);
        helper.accept("musket_with_scope", MUSKET_WITH_SCOPE);
        helper.accept("blunderbuss", BLUNDERBUSS);
        helper.accept("pistol", PISTOL);
        helper.accept("cartridge", CARTRIDGE);
        helper.accept("musket_upgrade_smithing_template", MUSKET_UPGRADE);

        //

        helper.accept("doppelstutzen", DOPPELSTUTZEN);
        helper.accept("carbine", CARBINE);
        helper.accept("wheellockpistol", WHEELLOCKPISTOL);
        helper.accept("wheellock", WHEELLOCK);
        helper.accept("bayonet", BAYONET);

        //

        helper.accept("pistol_body", PISTOL_BODY);
        helper.accept("carbine_body", CARBINE_BODY);
        helper.accept("musket_body", MUSKET_BODY);
        helper.accept("flint_lock", FLINT_LOCK);
        helper.accept("match_lock", MATCH_LOCK);
        helper.accept("wheel_lock", WHEEL_LOCK);
        helper.accept("short_barrel", SHORT_BARREL);
        helper.accept("medium_barrel", MEDIUM_BARREL);
        helper.accept("long_barrel", LONG_BARREL);
        helper.accept("blunderbuss_barrel", BLUNDERBUSS_BARREL);
        helper.accept("rifled_barrel", RIFLED_BARREL);
        helper.accept("unfinished_rifle", UNFINISHED_RIFLE);
    }

    public static void addToCreativeTab(ResourceKey<CreativeModeTab> tab, Consumer<Item> helper) {
        if (tab == CreativeModeTabs.COMBAT) {
            helper.accept(MUSKET);
            helper.accept(MUSKET_WITH_BAYONET);
            helper.accept(MUSKET_WITH_SCOPE);
            helper.accept(BLUNDERBUSS);
            helper.accept(PISTOL);
            helper.accept(CARTRIDGE);

            //

            helper.accept(DOPPELSTUTZEN);
            helper.accept(CARBINE);
            helper.accept(WHEELLOCK);
            helper.accept(WHEELLOCKPISTOL);
            helper.accept(BAYONET);

            //

            helper.accept(PISTOL_BODY);
            helper.accept(CARBINE_BODY);
            helper.accept(MUSKET_BODY);
            helper.accept(FLINT_LOCK);
            helper.accept(MATCH_LOCK);
            helper.accept(WHEEL_LOCK);
            helper.accept(SHORT_BARREL);
            helper.accept(MEDIUM_BARREL);
            helper.accept(LONG_BARREL);
            helper.accept(BLUNDERBUSS_BARREL);
            helper.accept(RIFLED_BARREL);
            helper.accept(UNFINISHED_RIFLE);
        }
        if (tab == CreativeModeTabs.INGREDIENTS) {
            helper.accept(MUSKET_UPGRADE);
        }
    }
}

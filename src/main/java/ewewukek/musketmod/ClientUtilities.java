package ewewukek.musketmod;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ClientUtilities {
    public static void registerItemProperties() {
        registerItemPredicate(MusketMod.resource("loaded"), (stack, level, entity, seed) -> {
            return GunItem.isLoaded(stack) ? 1 : 0;
        });
        registerItemPredicate(MusketMod.resource("loading"), (stack, level, entity, seed) -> {
            return (entity != null && entity.isUsingItem()
            && entity.getItemInHand(entity.getUsedItemHand()) == stack)
                ? 1 : 0;
        });
        registerItemPredicate(MusketMod.resource("aiming"), (stack, level, entity, seed) -> {
            return (entity != null && shouldAim(entity, stack)) ? 1 : 0;
        });
    }

    public static void registerItemPredicate(ResourceLocation location, ClampedItemPropertyFunction predicate) {
        ItemProperties.register(Items.MUSKET, location, predicate);
        ItemProperties.register(Items.MUSKET_WITH_BAYONET, location, predicate);
        ItemProperties.register(Items.MUSKET_WITH_SCOPE, location, predicate);
        ItemProperties.register(Items.BLUNDERBUSS, location, predicate);
        ItemProperties.register(Items.PISTOL, location, predicate);
        ItemProperties.register(Items.DOPPELSTUTZEN, location, predicate);
        ItemProperties.register(Items.CARBINE, location, predicate);
        ItemProperties.register(Items.WHEELLOCK, location, predicate);
        ItemProperties.register(Items.WHEELLOCKPISTOL, location, predicate);

    }

    // for mixins
    public static boolean canUseScope;
    public static boolean attackKeyDown;
    public static boolean preventFiring;

    public static void setScoping(Player player, boolean scoping) {
        if (scoping != ScopedMusketItem.isScoping) {
            player.playSound(
                scoping ? SoundEvents.SPYGLASS_USE : SoundEvents.SPYGLASS_STOP_USING,
                1.0f, 1.0f);
            ScopedMusketItem.isScoping = scoping;
        }
        if (!scoping) ScopedMusketItem.recoilTicks = 0;
    }

    public static boolean poseArm(LivingEntity entity, HumanoidModel<? extends LivingEntity> model, ModelPart arm) {
        if (entity.isUsingItem() || (entity instanceof Mob mob && !mob.isAggressive())) {
            return false;
        }

        boolean isRight = arm == model.rightArm;
        InteractionHand hand = entity.getMainArm() == (isRight ? HumanoidArm.RIGHT : HumanoidArm.LEFT)
            ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.getItem() instanceof GunItem && shouldAim(entity, stack, hand)) {
            arm.xRot = model.head.xRot + 0.1f - Mth.HALF_PI;
            if (model.crouching) arm.xRot -= 0.4f;
            arm.yRot = model.head.yRot + (isRight ? -0.3f : 0.3f);
            return true;
        }

        InteractionHand hand2 = hand == InteractionHand.MAIN_HAND
            ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack stack2 = entity.getItemInHand(hand2);
        if (stack2.getItem() instanceof GunItem gun2 && shouldAim(entity, stack2, hand2)
        && (gun2.twoHanded() || stack == ItemStack.EMPTY)) {
            arm.xRot = model.head.xRot - 1.5f;
            if (model.crouching) arm.xRot -= 0.4f;
            arm.yRot = model.head.yRot + (isRight ? -0.6f : 0.6f);
            return true;
        }

        return false;
    }

    public static boolean shouldAim(LivingEntity entity, ItemStack stack, InteractionHand hand) {
        if (entity.isUsingItem()) return false;
        if (entity instanceof Mob mob) return mob.isAggressive() || (mob instanceof Pillager);

        return ((GunItem)stack.getItem()).canUseFrom(entity, hand)
            && (GunItem.isLoaded(stack) || Config.alwaysAim);
    }

    public static boolean shouldAim(LivingEntity entity, ItemStack stack) {
        InteractionHand hand = stack == entity.getMainHandItem()
            ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        return shouldAim(entity, stack, hand);
    }

    public static void renderGunInHand(ItemInHandRenderer renderer, AbstractClientPlayer player, InteractionHand hand, float dt, float pitch, float swingProgress, float equipProgress, ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        if (player.isScoping()) {
            return;
        }

        HumanoidArm arm = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
        boolean isRightHand = arm == HumanoidArm.RIGHT;
        float sign = isRightHand ? 1 : -1;

        GunItem gun = (GunItem)stack.getItem();
        if (!gun.canUseFrom(player, hand)) {
            poseStack.pushPose();
            poseStack.translate(sign * 0.5, -0.5 - 0.6 * equipProgress, -0.7);
            poseStack.mulPose(Axis.XP.rotationDegrees(70));
            renderer.renderItem(player, stack, isRightHand ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRightHand, poseStack, bufferSource, light);
            poseStack.popPose();
            return;
        }

        ItemStack activeStack = GunItem.getActiveStack(hand);
        if (stack == activeStack) {
            setEquipAnimationDisabled(hand, true);

        } else if (activeStack != null && activeStack.getItem() != gun) {
            setEquipAnimationDisabled(hand, false);
        }

        poseStack.pushPose();
        poseStack.translate(sign * 0.15, -0.25, -0.35);

        if (swingProgress > 0) {
            float swingSharp = Mth.sin(Mth.sqrt(swingProgress) * Mth.PI);
            float swingNormal = Mth.sin(swingProgress * Mth.PI);

            if (gun == Items.MUSKET_WITH_BAYONET) {
                poseStack.translate(sign * -0.05 * swingNormal, 0, 0.05 - 0.3 * swingSharp);
                poseStack.mulPose(Axis.YP.rotationDegrees(5 * swingSharp));
            } else {
                poseStack.translate(sign * 0.05 * (1 - swingNormal), 0.05 * (1 - swingNormal), 0.05 - 0.4 * swingSharp);
                poseStack.mulPose(Axis.XP.rotationDegrees(180 + sign * 20 * (1 - swingSharp)));
            }

        } else if (player.isUsingItem() && player.getUsedItemHand() == hand) {
            Pair<Integer, Integer> loadingDuration = GunItem.getLoadingDuration(stack);
            int loadingStages = loadingDuration.getLeft();
            int ticksPerLoadingStage = loadingDuration.getRight();

            float usingTicks = player.getTicksUsingItem() + dt - 1;
            int loadingStage = GunItem.getLoadingStage(stack) + (int)(usingTicks / ticksPerLoadingStage);
            int reloadDuration = GunItem.reloadDuration(stack);

            if (reloadDuration > 0 && usingTicks < reloadDuration + 5) {
                poseStack.translate(0, -0.3, 0.05);
                poseStack.mulPose(Axis.XP.rotationDegrees(60));
                poseStack.mulPose(Axis.ZP.rotationDegrees(sign * 10));

                float t = 0;
                // return
                if (usingTicks >= ticksPerLoadingStage && loadingStage <= loadingStages) {
                    usingTicks = usingTicks % ticksPerLoadingStage;
                    if (usingTicks < 4) {
                        t = (4 - usingTicks) / 4;
                    }
                }
                // hit down by ramrod
                if (usingTicks >= ticksPerLoadingStage - 2 && loadingStage < loadingStages) {
                    t = (usingTicks - ticksPerLoadingStage + 2) / 2;
                    t = Mth.sin(Mth.HALF_PI * Mth.sqrt(t));
                }
                poseStack.translate(0, 0, 0.025 * t);

                if (gun == Items.BLUNDERBUSS) {
                    poseStack.translate(0, 0, -0.06);
                } else if (gun == Items.PISTOL) {
                    poseStack.translate(0, 0, -0.12);
                }
            }
        } else {
            if (isEquipAnimationDisabled(hand)) {
                if (equipProgress == 0) {
                    setEquipAnimationDisabled(hand, false);
                    GunItem.setActiveStack(hand, null);
                }
            } else {
                poseStack.translate(0, -0.6 * equipProgress, 0);
            }
        }

        renderer.renderItem(player, stack, isRightHand ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !isRightHand, poseStack, bufferSource, light);
        poseStack.popPose();
    }

    public static boolean disableMainHandEquipAnimation;
    public static boolean disableOffhandEquipAnimation;

    public static boolean isEquipAnimationDisabled(InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            return disableMainHandEquipAnimation;
        } else {
            return disableOffhandEquipAnimation;
        }
    }

    public static void setEquipAnimationDisabled(InteractionHand hand, boolean disabled) {
        if (hand == InteractionHand.MAIN_HAND) {
            disableMainHandEquipAnimation = disabled;
        } else {
            disableOffhandEquipAnimation = disabled;
        }
    }
}

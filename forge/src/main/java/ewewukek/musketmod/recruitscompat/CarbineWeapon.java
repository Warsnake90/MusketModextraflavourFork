package ewewukek.musketmod.recruitscompat;

import com.talhanation.recruits.Main;
import com.talhanation.recruits.compat.IWeapon;
import com.talhanation.recruits.entities.AbstractRecruitEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CarbineWeapon implements IWeapon {

    @Override
    @Nullable
    public Item getWeapon() {
        try {
            Class<?> itemsClass = Class.forName("ewewukek.musketmod.Items");
            Field carbineField = itemsClass.getField("CARBINE");
            return (Item) carbineField.get(null);  // static field, so pass null
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            Main.LOGGER.error("MusketMod Items class or CARBINE field not found", e);
            return null;
        }
    }

    @Override
    public double getMoveSpeedAmp() {
        return 0.45D; // example different speed
    }

    @Override
    public int getAttackCooldown() {
        return 35; // example cooldown for carbine
    }

    @Override
    public int getWeaponLoadTime() {
        return 50; // example load time
    }

    @Override
    public float getProjectileSpeed() {
        return 2.5F; // example projectile speed
    }

    @Override
    public boolean isLoaded(ItemStack stack) {
        try {
            Class<?> carbineItemClass = Class.forName("ewewukek.musketmod.CarbineItem");
            Method isLoadedMethod = carbineItemClass.getMethod("isLoaded", ItemStack.class);
            return (boolean) isLoadedMethod.invoke(null, stack);
        } catch (Exception e) {
            Main.LOGGER.info("CarbineItem class or isLoaded method not found", e);
            return false;
        }
    }

    @Override
    public void setLoaded(ItemStack stack, boolean loaded) {
        try {
            Class<?> carbineItemClass = Class.forName("ewewukek.musketmod.CarbineItem");
            Method setLoadedMethod = carbineItemClass.getMethod("setLoaded", ItemStack.class, boolean.class);
            setLoadedMethod.invoke(null, stack, loaded);
        } catch (Exception e) {
            Main.LOGGER.info("CarbineItem class or setLoaded method not found", e);
        }
    }

    @Override
    public AbstractHurtingProjectile getProjectile(LivingEntity shooter) {
        try {
            Class<?> bulletClass = Class.forName("ewewukek.musketmod.BulletEntity");
            Constructor<?> constructor = bulletClass.getConstructor(Level.class);
            AbstractHurtingProjectile bullet = (AbstractHurtingProjectile) constructor.newInstance(shooter.getCommandSenderWorld());
            bullet.setOwner(shooter);
            bullet.setPos(shooter.getX(), shooter.getEyeHeight() - 0.1D, shooter.getZ());
            return bullet;
        } catch (Exception e) {
            Main.LOGGER.info("BulletEntity class or constructor not found", e);
            return null;
        }
    }

    @Override
    public AbstractArrow getProjectileArrow(LivingEntity shooter) {
        return null; // Not applicable for Carbine
    }

    @Override
    @Nullable
    public AbstractHurtingProjectile shoot(LivingEntity shooter, AbstractHurtingProjectile projectile, double x, double y, double z) {
        if (!shooter.getCommandSenderWorld().isClientSide()) {
            double horizontalDistance = Mth.sqrt((float) (x * x + z * z));
            Vec3 velocityVec = (new Vec3(x, y + horizontalDistance * 0.065, z)).normalize().scale(10F);

            try {
                Class<?> bulletClass = Class.forName("ewewukek.musketmod.BulletEntity");
                if (bulletClass.isInstance(projectile)) {
                    Object bullet = bulletClass.cast(projectile);

                    Field damageField = bullet.getClass().getField("damage");
                    damageField.setAccessible(true);

                    Method setInitialSpeedMethod = bullet.getClass().getMethod("setInitialSpeed", float.class);

                    setInitialSpeedMethod.invoke(bullet, 5F);
                    damageField.setFloat(bullet, 50F); // example damage

                    projectile.setDeltaMovement(velocityVec);
                    projectile.shoot(x, y + horizontalDistance * 0.065, z, 4.5F, 2F);
                }
            } catch (Exception e) {
                Main.LOGGER.error("Error during shooting bullet", e);
            }

            Vec3 forward = new Vec3(x, y, z).normalize();
            Vec3 origin = new Vec3(shooter.getX(), shooter.getEyeY(), shooter.getZ());

            try {
                Class<?> musketModClass = Class.forName("ewewukek.musketmod.MusketMod");
                Method sendSmokeEffectMethod = musketModClass.getMethod("sendSmokeEffect", ServerLevel.class, Vec3.class, Vec3.class);
                sendSmokeEffectMethod.invoke(null, (ServerLevel) shooter.getCommandSenderWorld(), origin, forward);
            } catch (Exception e) {
                Main.LOGGER.error("Failed to invoke sendSmokeEffect", e);
            }

            return projectile;
        }
        return null;
    }

    @Override
    public AbstractArrow shootArrow(LivingEntity shooter, AbstractArrow projectile, double x, double y, double z) {
        return null; // Not applicable
    }

    @Override
    public SoundEvent getShootSound() {
        try {
            Class<?> soundsClass = Class.forName("ewewukek.musketmod.Sounds");
            Field shootSoundField = soundsClass.getField("CARBINE_FIRE");
            return (SoundEvent) shootSoundField.get(null);
        } catch (Exception e) {
            Main.LOGGER.error("CARBINE_FIRE sound not found", e);
            return null;
        }
    }

    @Override
    public SoundEvent getLoadSound() {
        try {
            Class<?> soundsClass = Class.forName("ewewukek.musketmod.Sounds");
            Field loadSoundField = soundsClass.getField("CARBINE_READY");
            return (SoundEvent) loadSoundField.get(null);
        } catch (Exception e) {
            Main.LOGGER.error("CARBINE_READY sound not found", e);
            return null;
        }
    }

    @Override
    public boolean isGun() {
        return true;
    }

    @Override
    public boolean canMelee() {
        return false;
    }

    @Override
    public boolean isBow() {
        return false;
    }

    @Override
    public boolean isCrossBow() {
        return false;
    }

    @Override
    public void performRangedAttackIWeapon(AbstractRecruitEntity shooter, double x, double y, double z, float projectileSpeed) {
        AbstractHurtingProjectile projectileEntity = this.getProjectile(shooter);
        if (projectileEntity == null) return;

        double dx = x - shooter.getX();
        double dy = y + 0.5D - projectileEntity.getY();
        double dz = z - shooter.getZ();

        this.shoot(shooter, projectileEntity, dx, dy, dz);

        shooter.playSound(this.getShootSound(), 1.0F, 1.0F / (shooter.getRandom().nextFloat() * 0.4F + 0.8F));
        shooter.getCommandSenderWorld().addFreshEntity(projectileEntity);

        shooter.damageMainHandItem();
    }
}
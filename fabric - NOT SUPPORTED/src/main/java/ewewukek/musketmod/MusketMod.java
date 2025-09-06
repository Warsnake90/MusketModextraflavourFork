package ewewukek.musketmod;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.phys.Vec3;

public class MusketMod implements ModInitializer {
    public static final String MODID = "musketmod";
    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("musketmod.txt");

    public static ResourceLocation resource(String path) {
        return new ResourceLocation(MODID, path);
    }

    @Override
    public void onInitialize() {
        Config.load();
        System.out.println("----!!!----");
        System.out.println("----!!!----");
        System.out.println("FABRIC IS NOT SUPPORTED ON THE FORK!");
        System.out.println("----!!!----");
        System.out.println("----!!!----");
        System.exit(0);

    }

    public static final ResourceLocation SMOKE_EFFECT_PACKET_ID = new ResourceLocation(MODID, "smoke_effect");

    public static void sendSmokeEffect(ServerLevel level, Vec3 origin, Vec3 direction) {

    }
}

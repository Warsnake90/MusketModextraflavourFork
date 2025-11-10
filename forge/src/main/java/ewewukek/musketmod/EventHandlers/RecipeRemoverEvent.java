package ewewukek.musketmod.EventHandlers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "musketmod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RecipeRemoverEvent {

    @SubscribeEvent
    public static void onRecipesUpdated(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        var recipeManager = server.getRecipeManager();

       // ResourceLocation recipeId = new ResourceLocation("create", "crafting/kinetics/fluid_pipe");

        try {

            Field byNameField = recipeManager.getClass().getDeclaredField("byName");
            byNameField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<ResourceLocation, ?> recipes = (Map<ResourceLocation, ?>) byNameField.get(recipeManager);

           // if (recipes.containsKey(recipeId)) {
             //   recipes.remove(recipeId);
               // System.out.println("Musketmod Removed: " + recipeId);
          //  } else {
             //   System.out.println("Musketmod could not remove: " + recipeId + " - Not found");
           // }

        } catch (Exception e) {
            System.out.println("Musketmod - Don't worry, this shit works fine");
        }
    }
}
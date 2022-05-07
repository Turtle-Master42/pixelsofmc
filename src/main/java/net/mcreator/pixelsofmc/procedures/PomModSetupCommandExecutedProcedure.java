package net.mcreator.pixelsofmc.procedures;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraft.world.level.LevelAccessor;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class PomModSetupCommandExecutedProcedure {
	public static void execute(LevelAccessor world) {
		File recipe = new File("");
		File recipe2 = new File("");
		File mekanism = new File("");
		File mekanism2 = new File("");
		com.google.gson.JsonObject mainJsonDeformer = new com.google.gson.JsonObject();
		com.google.gson.JsonObject MekanismJsonDeformer = new com.google.gson.JsonObject();
		com.google.gson.JsonObject MekanismJsonSplitter = new com.google.gson.JsonObject();
		com.google.gson.JsonObject mainJsonSplitter = new com.google.gson.JsonObject();
		recipe = new File((FMLPaths.GAMEDIR.get().toString() + "/config/pixelsofminecraft/recipes/splitter"), File.separator + "vanilla.json");
		recipe2 = new File((FMLPaths.GAMEDIR.get().toString() + "/config/pixelsofminecraft/recipes/deformer"), File.separator + "vanilla.json");
		if (!recipe.exists()) {
			try {
				recipe.getParentFile().mkdirs();
				recipe.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			mainJsonSplitter.addProperty("recipe_amount", 1);
			mainJsonSplitter.addProperty("input_1", "diamond_block");
			mainJsonSplitter.addProperty("output_1", "minecraft:diamond");
			mainJsonSplitter.addProperty("amount_1", 5);
			{
				Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
				try {
					FileWriter fileWriter = new FileWriter(recipe);
					fileWriter.write(mainGSONBuilderVariable.toJson(mainJsonSplitter));
					fileWriter.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
		if (!recipe2.exists()) {
			try {
				recipe2.getParentFile().mkdirs();
				recipe2.createNewFile();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			mainJsonDeformer.addProperty("recipe_amount", 1);
			mainJsonDeformer.addProperty("input_tag_1", "minecraft:saplings");
			mainJsonDeformer.addProperty("output_1", "minecraft:pixelsofmc:jar_of_pixels_plants");
			mainJsonDeformer.addProperty("amount_1", 2);
			{
				Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
				try {
					FileWriter fileWriter = new FileWriter(recipe2);
					fileWriter.write(mainGSONBuilderVariable.toJson(mainJsonDeformer));
					fileWriter.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
		if (net.minecraftforge.fml.ModList.get().isLoaded("mekanism")) {
			if (!recipe2.exists()) {
				try {
					mekanism2.getParentFile().mkdirs();
					mekanism2.createNewFile();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
				MekanismJsonDeformer.addProperty("recipe_amount", 1);
				MekanismJsonDeformer.addProperty("input_1", "iron_ingot");
				MekanismJsonDeformer.addProperty("output_1", "minecraft:diamond_block");
				MekanismJsonDeformer.addProperty("amount_1", 2);
				{
					Gson mainGSONBuilderVariable = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileWriter fileWriter = new FileWriter(recipe2);
						fileWriter.write(mainGSONBuilderVariable.toJson(mainJsonDeformer));
						fileWriter.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}
}

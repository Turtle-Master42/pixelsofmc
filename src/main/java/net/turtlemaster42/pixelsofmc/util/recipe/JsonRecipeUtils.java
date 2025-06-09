package net.turtlemaster42.pixelsofmc.util.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class JsonRecipeUtils {

    public static ChanceIngredient CHIFromJson(JsonObject json, String name) {
        return ChanceIngredient.fromJson(json.getAsJsonObject(name));
    }

    public static CountedIngredient CIFromJson(JsonObject json, String name) {
        return CountedIngredient.fromJson(json.getAsJsonObject(name));
    }

    public static Ingredient IFromJson(JsonObject json, String name) {
        return Ingredient.fromJson(json.get(name));
    }

    public static List<ChanceIngredient> CHIListFromJson(JsonObject json, String name) {
        JsonArray jsonInputs = json.getAsJsonArray(name);
        List<ChanceIngredient> list = new ArrayList<>(jsonInputs.size());
        for (int i = 0; i < jsonInputs.size(); i++) {
            list.add(i, ChanceIngredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
        }
        return list;
    }

    public static List<CountedIngredient> CIListFromJson(JsonObject json, String name) {
        JsonArray jsonInputs = json.getAsJsonArray(name);
        List<CountedIngredient> list = new ArrayList<>(jsonInputs.size());
        for (int i = 0; i < jsonInputs.size(); i++) {
            list.add(i, CountedIngredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
        }
        return list;
    }

    public static List<Ingredient> IListFromJson(JsonObject json, String name) {
        JsonArray jsonInputs = json.getAsJsonArray(name);
        List<Ingredient> list = new ArrayList<>(jsonInputs.size());
        for (int i = 0; i < jsonInputs.size(); i++) {
            list.add(i, Ingredient.fromJson(jsonInputs.get(i).getAsJsonObject()));
        }
        return list;
    }

    public static FluidStack FFromJson(JsonObject json, String name) {
        return FluidJSONUtil.readFluid(json.get(name).getAsJsonObject());
    }

    public static List<FluidStack> FListFromJson(JsonObject json, String name) {
        JsonArray jsonOutputs = json.getAsJsonArray(name);
        List<FluidStack> fluidList = new ArrayList<>(jsonOutputs.size());
        for (int i = 0; i < jsonOutputs.size(); i++) {
            fluidList.add(i, FluidJSONUtil.readFluid(jsonOutputs.get(i).getAsJsonObject()));
        }
        return fluidList;
    }

    public static boolean booleanFromJson(JsonObject json, String name) {
        return json.get(name).getAsBoolean();
    }

    public static long longFromJson(JsonObject json, String name) {
        return json.get(name).getAsLong();
    }

    public static int intFromJson(JsonObject json, String name) {return json.get(name).getAsInt();}

    public static double doubleFromJson(JsonObject json, String name) {
        return json.get(name).getAsDouble();
    }

    public static float floatFromJson(JsonObject json, String name) {
        return json.get(name).getAsFloat();
    }

    public static String stringFromJson(JsonObject json, String name) {
        return GsonHelper.getAsString(json, name);
    }

}

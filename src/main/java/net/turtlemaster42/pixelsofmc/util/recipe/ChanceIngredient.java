package net.turtlemaster42.pixelsofmc.util.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public record ChanceIngredient(Ingredient ingredient, int count, float chance) implements Predicate<ItemStack> {
    /**
     * An empty ingredient.
     */
    public static ChanceIngredient EMPTY = new ChanceIngredient(Ingredient.EMPTY, 0, 0);

    public static ChanceIngredient of() {
        return EMPTY;
    }

    public static ChanceIngredient of(ItemLike... items) {
        return of(Arrays.stream(items).map(ItemStack::new));
    }

    public static ChanceIngredient of(int count, ItemLike... items) {
        return of(count, Arrays.stream(items).map(ItemStack::new));
    }

    public static ChanceIngredient of(ItemStack... stacks) {
        return of(Arrays.stream(stacks));
    }

    public static ChanceIngredient of(int count, ItemStack... stacks) {
        return of(count, Arrays.stream(stacks));
    }

    public static ChanceIngredient of(Stream<ItemStack> stacks) {
        return of(1, stacks);
    }

    public static ChanceIngredient of(int count, Stream<ItemStack> stacks) {
        return new ChanceIngredient(Ingredient.of(stacks), count, 0);
    }

    public static ChanceIngredient of(TagKey<Item> tag) {
        return of(1, tag);
    }

    public static ChanceIngredient of(int count, TagKey<Item> tag) {
        return new ChanceIngredient(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(tag))), count, 0);
    }

    public static ChanceIngredient of(Ingredient ingredient) {
        return new ChanceIngredient(ingredient, 1, 0);
    }

    public static ChanceIngredient of(int count, Ingredient ingredient) {
        return new ChanceIngredient(ingredient, count, 0);
    }



    public static ChanceIngredient of(int count, float chance, ItemLike... items) {
        return of(count, chance, Arrays.stream(items).map(ItemStack::new));
    }

    public static ChanceIngredient of(int count, float chance, ItemStack... stacks) {
        return of(count, chance, Arrays.stream(stacks));
    }

    public static ChanceIngredient of(int count, float chance, Stream<ItemStack> stacks) {
        return new ChanceIngredient(Ingredient.of(stacks), count, chance);
    }

    public static ChanceIngredient of(int count, float chance, TagKey<Item> tag) {
        return new ChanceIngredient(Ingredient.fromValues(Stream.of(new Ingredient.TagValue(tag))), count, chance);
    }

    public static ChanceIngredient of(int count, float chance, Ingredient ingredient) {
        return new ChanceIngredient(ingredient, count, chance);
    }





    public ItemStack[] getItems() {
        ItemStack[] matchingStacks = ingredient.getItems();
        for (ItemStack matchingStack : matchingStacks) {
            matchingStack.setCount(count);
        }
        return matchingStacks;
    }

    public Item asItem() {
        return ingredient.getItems()[0].getItem();
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {
        return ingredient.test(itemStack) && itemStack.getCount() >= count;
    }

    public static ChanceIngredient fromJson(JsonObject json) {
        return new ChanceIngredient(Ingredient.fromJson(json.get("ingredient")), json.get("count").getAsInt(), json.get("chance").getAsFloat());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add("ingredient", ingredient.toJson());
        json.addProperty("count", count);
        json.addProperty("chance", chance);
        return json;
    }

    public static ChanceIngredient fromNetwork(FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        return new ChanceIngredient(ingredient, buffer.readShort(), buffer.readFloat());
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        ingredient.toNetwork(buffer);
        buffer.writeShort(count());
        buffer.writeFloat(chance());
    }
}

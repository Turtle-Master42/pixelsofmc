package net.turtlemaster42.pixelsofmc.datagen;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jline.reader.EndOfFileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookBuilder<B extends BookBuilder<B>> {
    protected ResourceLocation location;
    protected final ExistingFileHelper existingFileHelper;

    protected String parent;
    protected String text;
    protected String title;
    protected String title_locale;
    protected List<PageButtonBuilder> pageButtons = new ArrayList<>();
    protected List<ImageBuilder> images = new ArrayList<>();
    protected List<ItemRenderBuilder> items = new ArrayList<>();
    protected List<RecipeRenderBuilder> recipes = new ArrayList<>();
    protected List<EntityRenderBuilder> entities = new ArrayList<>();
    protected List<TabulaRenderBuilder> tabulas = new ArrayList<>();
    protected List<EntityButtonBuilder> entityButtons = new ArrayList<>();



    protected BookBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper)
    {
        this.location = outputLocation;
        this.existingFileHelper = existingFileHelper;
    }

    @SuppressWarnings("unchecked")
    private B self() { return (B) this; }


    public B pageParent(String parent) {
        if (!parent.endsWith(".json")) {
            throw new EndOfFileException("Page parent needs to end with .json");
        }
        this.parent = parent;
        return self();
    }

    public B text(String text) {
        if (!text.endsWith(".txt")) {
            throw new EndOfFileException("Text needs to end with .txt");
        }
        this.text = text;
        return self();
    }

    public B title(String title) {
        this.title = title;
        return self();
    }

    public B translatableTitle(String translatable) {
        this.title_locale = translatable;
        return self();
    }

    public PageButtonBuilder pageButton(String linked_page, String text) {
        BookBuilder<B>.PageButtonBuilder builder = new PageButtonBuilder(linked_page, text);
        pageButtons.add(builder);
        return builder;
    }

    public PageButtonBuilder pageButton(String linked_page) {
        return pageButton(linked_page, "");
    }

    public ImageBuilder image(String texture) {
        BookBuilder<B>.ImageBuilder builder = new ImageBuilder(texture);
        images.add(builder);
        return builder;
    }

    public ImageBuilder image(ResourceLocation texture) {
        return image(texture.toString());
    }

    public ItemRenderBuilder item(String item) {
        BookBuilder<B>.ItemRenderBuilder builder = new ItemRenderBuilder(item);
        items.add(builder);
        return builder;
    }

    public ItemRenderBuilder item(Item item) {
        return item(item.getCreatorModId(item.getDefaultInstance()) + ":" + item);
    }

    public RecipeRenderBuilder recipe(String recipe) {
        BookBuilder<B>.RecipeRenderBuilder builder = new RecipeRenderBuilder(recipe);
        recipes.add(builder);
        return builder;
    }

    public RecipeRenderBuilder recipe(ResourceLocation recipe) {
        return recipe(recipe.toString());
    }

    public EntityRenderBuilder entity(String entity) {
        BookBuilder<B>.EntityRenderBuilder builder = new EntityRenderBuilder(entity);
        entities.add(builder);
        return builder;
    }

    public EntityRenderBuilder entity(EntityType<?> entity) {
        return entity(BuiltInRegistries.ENTITY_TYPE.getKey(entity).toString());
    }

    public EntityButtonBuilder entityButton(String entity, String linked_page) {
        BookBuilder<B>.EntityButtonBuilder builder = new EntityButtonBuilder(entity, linked_page);
        entityButtons.add(builder);
        return builder;
    }


    public JsonElement toJson() {
        JsonObject root = new JsonObject();

        if (this.parent != null) {
            root.addProperty("parent", this.parent);
        }

        if (this.text != null) {
            root.addProperty("text", this.text);
        }

        if (this.title != null) {
            root.addProperty("title", this.title);
        }

        if (this.title_locale != null) {
            root.addProperty("title_locale", this.title_locale);
        }

        if (!this.pageButtons.isEmpty()) {
            JsonArray jsonButtons = new JsonArray();
            for (PageButtonBuilder button : pageButtons) {
                jsonButtons.add(button.toJson());
            }
            root.add("linked_page_buttons", jsonButtons);
        }

        if (!this.images.isEmpty()) {
            JsonArray jsonImages = new JsonArray();
            for (ImageBuilder button : images) {
                jsonImages.add(button.toJson());
            }
            root.add("images", jsonImages);
        }

        if (!this.items.isEmpty()) {
            JsonArray jsonImages = new JsonArray();
            for (ItemRenderBuilder button : items) {
                jsonImages.add(button.toJson());
            }
            root.add("item_renders", jsonImages);
        }

        if (!this.recipes.isEmpty()) {
            JsonArray jsonIRecipes = new JsonArray();
            for (RecipeRenderBuilder button : recipes) {
                jsonIRecipes.add(button.toJson());
            }
            root.add("recipes", jsonIRecipes);
        }

        if (!this.entities.isEmpty()) {
            JsonArray jsonIRecipes = new JsonArray();
            for (EntityRenderBuilder button : entities) {
                jsonIRecipes.add(button.toJson());
            }
            root.add("entity_renders", jsonIRecipes);
        }

        if (!this.tabulas.isEmpty()) {
            JsonArray jsonIRecipes = new JsonArray();
            for (TabulaRenderBuilder button : tabulas) {
                jsonIRecipes.add(button.toJson());
            }
            root.add("tabula_renders", jsonIRecipes);
        }

        if (!this.entityButtons.isEmpty()) {
            JsonArray jsonIRecipes = new JsonArray();
            for (EntityButtonBuilder button : entityButtons) {
                jsonIRecipes.add(button.toJson());
            }
            root.add("tabula_renders", jsonIRecipes);
        }

        return root;
    }

    // LOCATION

    public ResourceLocation getLocation() {
        assertExistence();
        return location;
    }

    /**
     * Assert that this model exists.
     * @throws IllegalStateException if this book does not exist
     */
    public void assertExistence() {
        Preconditions.checkState(exists(), "Book at %s does not exist", location);
    }

    public ResourceLocation getUncheckedLocation() {
        return location;
    }

    protected boolean exists() {
        return existingFileHelper.exists(getUncheckedLocation(), BookProvider.PAGE);
    }


    public class PageButtonBuilder {
        private final String linked_page;
        private final String text;
        private int x;
        private int y;
        private int page;
        private String item;
        private String item_tag;

        PageButtonBuilder(String linked_page, String text) {
            if (!linked_page.endsWith(".json")) {
                throw new EndOfFileException("Linked page needs to end with .json");
            }
            this.linked_page = linked_page;
            this.text = text;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public PageButtonBuilder x(int x) {
            this.x = x;
            return this;
        }

        public PageButtonBuilder y(int y) {
            this.y = y;
            return this;
        }

        public PageButtonBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public PageButtonBuilder page(int page) {
            this.page = page;
            return this;
        }

        public PageButtonBuilder item(String item) {
            this.item = item;
            return this;
        }

        public PageButtonBuilder item(Item item) {
            this.item = item.getCreatorModId(item.getDefaultInstance()) + ":" + item;
            return this;
        }

        public PageButtonBuilder item(String item, String nbt) {
            this.item = item;
            this.item_tag = nbt;
            return this;
        }

        public PageButtonBuilder item(Item item, String nbt) {
            this.item = item.getCreatorModId(item.getDefaultInstance()) + ":" + item;
            this.item_tag = nbt;
            return this;
        }


        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            json.addProperty("text", Objects.requireNonNullElse(text, ""));
            if (linked_page != null) {
                json.addProperty("linked_page", linked_page);
            }
            if (x != 0) {
                json.addProperty("x", x);
            }
            if (y != 0) {
                json.addProperty("y", y);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (item != null) {
                json.addProperty("item", item);
            }
            if (item_tag != null) {
                json.addProperty("item_tag", item_tag);
            }

            return json;
        }
    }

    public class ImageBuilder {
        private final String texture;
        private int x;
        private int y;
        private int page;
        private float scale = 1;
        private int width = 256;
        private int height = 256;
        private int u;
        private int v;

        ImageBuilder(String texture) {
            this.texture = texture;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public ImageBuilder x(int x) {
            this.x = x;
            return this;
        }

        public ImageBuilder y(int y) {
            this.y = y;
            return this;
        }

        public ImageBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public ImageBuilder page(int page) {
            this.page = page;
            return this;
        }

        public ImageBuilder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public ImageBuilder size(int size) {
            this.width = size;
            this.height = size;
            return this;
        }

        public ImageBuilder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public ImageBuilder u(int u) {
            this.u = u;
            return this;
        }

        public ImageBuilder v(int v) {
            this.v = v;
            return this;
        }

        public ImageBuilder uv(int u, int v) {
            this.u = u;
            this.v = v;
            return this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            if (texture != null) {
                json.addProperty("texture", texture);
            }
            if (x != 0) {
                json.addProperty("x", x);
            }
            if (y != 0) {
                json.addProperty("y", y);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (u != 0) {
                json.addProperty("u", u);
            }
            if (v != 0) {
                json.addProperty("v", v);
            }
            if (width > 0) {
                json.addProperty("width", width);
            }
            if (height > 0) {
                json.addProperty("height", height);
            }
            if (scale >= 0) {
                json.addProperty("scale", scale);
            }

            return json;
        }
    }

    public class ItemRenderBuilder {
        private final String item;
        private String item_tag;
        private int x;
        private int y;
        private double scale = 1;
        private int page;

        ItemRenderBuilder(String item) {
            this.item = item;
        }

        public ItemRenderBuilder nbt(String item_tag) {
            this.item_tag = item_tag;
            return this;
        }

        public ItemRenderBuilder x(int x) {
            this.x = x;
            return this;
        }

        public ItemRenderBuilder y(int y) {
            this.y = y;
            return this;
        }

        public ItemRenderBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public ItemRenderBuilder page(int page) {
            this.page = page;
            return this;
        }

        public ItemRenderBuilder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            if (x != 0) {
                json.addProperty("x", x / scale);
            }
            if (y != 0) {
                json.addProperty("y", y / scale);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (scale > 0) {
                json.addProperty("scale", scale);
            }
            if (item != null) {
                json.addProperty("item", item);
            }
            if (item_tag != null) {
                json.addProperty("item_tag", item_tag);
            }

            return json;
        }

    }

    public class RecipeRenderBuilder {
        private final String recipe;
        private boolean shapeless = false;
        private int x;
        private int y;
        private double scale = 1;
        private int page;

        RecipeRenderBuilder(String recipe) {
            this.recipe = recipe;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public RecipeRenderBuilder x(int x) {
            this.x = x;
            return this;
        }

        public RecipeRenderBuilder isShapeless() {
            this.shapeless = true;
            return this;
        }

        public RecipeRenderBuilder y(int y) {
            this.y = y;
            return this;
        }

        public RecipeRenderBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public RecipeRenderBuilder page(int page) {
            this.page = page;
            return this;
        }

        public RecipeRenderBuilder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            if (recipe != null) {
                json.addProperty("recipe", recipe);
            }
            if (x != 0) {
                json.addProperty("x", x);
            }
            if (y != 0) {
                json.addProperty("y", y);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (scale >= 0) {
                json.addProperty("scale", scale);
            }
            if (shapeless) {
                json.addProperty("shapeless", true);
            }

            return json;
        }

    }

    public class TabulaRenderBuilder {
        private final String model;
        private final String texture;
        private int x;
        private int y;
        private double scale = 1;
        private int page;
        private double rot_x;
        private double rot_y;
        private double rot_z;
        private boolean follow_cursor = false;

        TabulaRenderBuilder(String model, String texture) {
            this.model = model;
            this.texture = texture;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public TabulaRenderBuilder followsCursor() {
            this.follow_cursor = true;
            return this;
        }

        public TabulaRenderBuilder x(int x) {
            this.x = x;
            return this;
        }

        public TabulaRenderBuilder y(int y) {
            this.y = y;
            return this;
        }

        public TabulaRenderBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public TabulaRenderBuilder rotationX(int rot_x) {
            this.rot_x = rot_x;
            return this;
        }

        public TabulaRenderBuilder rotationY(int rot_y) {
            this.rot_y = rot_y;
            return this;
        }

        public TabulaRenderBuilder rotationZ(int rot_z) {
            this.rot_z = rot_z;
            return this;
        }

        public TabulaRenderBuilder rotation(int rot_x, int rot_y, int rot_z) {
            this.rot_x = rot_x;
            this.rot_y = rot_y;
            this.rot_z = rot_z;
            return this;
        }


        public TabulaRenderBuilder page(int page) {
            this.page = page;
            return this;
        }

        public TabulaRenderBuilder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            if (model != null) {
                json.addProperty("model", model);
            }
            if (texture != null) {
                json.addProperty("texture", texture);
            }
            if (x != 0) {
                json.addProperty("x", x);
            }
            if (y != 0) {
                json.addProperty("y", y);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (scale >= 0) {
                json.addProperty("scale", scale);
            }
            if (follow_cursor) {
                json.addProperty("follow_cursor", true);
            }
            if (rot_x != 0) {
                json.addProperty("rot_x", rot_x);
            }
            if (rot_y != 0) {
                json.addProperty("rot_y", rot_y);
            }
            if (rot_z != 0) {
                json.addProperty("rot_z", rot_z);
            }

            return json;
        }
    }

    public class EntityRenderBuilder {
        private final String entity;
        private int x;
        private int y;
        private double scale = 1;
        private int page;
        private double rot_x;
        private double rot_y;
        private double rot_z;
        private boolean follow_cursor = false;
        private String entity_data;

        EntityRenderBuilder(String entity) {
            this.entity = entity;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public EntityRenderBuilder followsCursor() {
            this.follow_cursor = true;
            return this;
        }

        public EntityRenderBuilder nbt(String entity_data) {
            this.entity_data = entity_data;
            return this;
        }

        public EntityRenderBuilder x(int x) {
            this.x = x;
            return this;
        }

        public EntityRenderBuilder y(int y) {
            this.y = y;
            return this;
        }

        public EntityRenderBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public EntityRenderBuilder rotationX(int rot_x) {
            this.rot_x = rot_x;
            return this;
        }

        public EntityRenderBuilder rotationY(int rot_y) {
            this.rot_y = rot_y;
            return this;
        }

        public EntityRenderBuilder rotationZ(int rot_z) {
            this.rot_z = rot_z;
            return this;
        }

        public EntityRenderBuilder rotation(int rot_x, int rot_y, int rot_z) {
            this.rot_x = rot_x;
            this.rot_y = rot_y;
            this.rot_z = rot_z;
            return this;
        }


        public EntityRenderBuilder page(int page) {
            this.page = page;
            return this;
        }

        public EntityRenderBuilder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            if (entity != null) {
                json.addProperty("entity", entity);
            }
            if (entity_data != null) {
                json.addProperty("entity_data", entity_data);
            }
            if (x != 0) {
                json.addProperty("x", x);
            }
            if (y != 0) {
                json.addProperty("y", y);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (scale >= 0) {
                json.addProperty("scale", scale);
            }
            if (follow_cursor) {
                json.addProperty("follow_cursor", true);
            }
            if (rot_x != 0) {
                json.addProperty("rot_x", rot_x);
            }
            if (rot_y != 0) {
                json.addProperty("rot_y", rot_y);
            }
            if (rot_z != 0) {
                json.addProperty("rot_z", rot_z);
            }

            return json;
        }
    }

    public class EntityButtonBuilder {
        private final String entity;
        private int x;
        private int y;
        private float offset_x;
        private float offset_y;
        private double entity_scale = 1;
        private double scale = 1;
        private int page;
        private final String linked_page;
        private String hover_text;

        EntityButtonBuilder(String entity, String linked_page) {
            this.entity = entity;
            if (!linked_page.endsWith(".json")) {
                throw new EndOfFileException("Linked page needs to end with .json");
            }
            this.linked_page = linked_page;
        }

        public BookBuilder<B> end()
        {
            return BookBuilder.this;
        }

        public EntityButtonBuilder hoverText(String hover_text) {
            this.hover_text = hover_text;
            return this;
        }

        public EntityButtonBuilder x(int x) {
            this.x = x;
            return this;
        }

        public EntityButtonBuilder y(int y) {
            this.y = y;
            return this;
        }

        public EntityButtonBuilder pos(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public EntityButtonBuilder offsetX(int offset_x) {
            this.offset_x = offset_x;
            return this;
        }

        public EntityButtonBuilder offsetY(int offset_y) {
            this.offset_y = offset_y;
            return this;
        }

        public EntityButtonBuilder offset(int offset_x, int offset_y) {
            this.offset_x = offset_x;
            this.offset_y = offset_y;
            return this;
        }

        public EntityButtonBuilder page(int page) {
            this.page = page;
            return this;
        }

        public EntityButtonBuilder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public EntityButtonBuilder entityScale(float entity_scale) {
            this.entity_scale = entity_scale;
            return this;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            if (entity != null) {
                json.addProperty("entity", entity);
            }
            if (linked_page != null) {
                json.addProperty("linked_page", linked_page);
            }
            if (hover_text != null) {
                json.addProperty("hover_text", hover_text);
            }
            if (x != 0) {
                json.addProperty("x", x);
            }
            if (y != 0) {
                json.addProperty("y", y);
            }
            if (offset_x != 0) {
                json.addProperty("offset_x", offset_x);
            }
            if (offset_y != 0) {
                json.addProperty("offset_y", offset_y);
            }
            if (page != 0) {
                json.addProperty("page", page);
            }
            if (scale >= 0) {
                json.addProperty("scale", scale);
            }
            if (entity_scale >= 0) {
                json.addProperty("entity_scale", entity_scale);
            }

            return json;
        }
    }
}

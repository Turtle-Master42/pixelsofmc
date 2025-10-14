package net.turtlemaster42.pixelsofmc.datagen;

import com.google.common.base.Preconditions;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.turtlemaster42.pixelsofmc.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class BookProvider<B extends BookBuilder<B>> implements DataProvider {

    protected static final ExistingFileHelper.ResourceType PAGE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".json", "book");

    protected final PackOutput output;
    protected final String modId;
    protected final Function<ResourceLocation, B> factory;
    @VisibleForTesting
    public final Map<ResourceLocation, B> generatedPages = new HashMap<>();
    @VisibleForTesting
    public final ExistingFileHelper existingFileHelper;

    public BookProvider(PackOutput output, String modId, Function<ResourceLocation, B> factory, ExistingFileHelper existingFileHelper) {
        Preconditions.checkNotNull(output);
        this.output = output;
        Preconditions.checkNotNull(modId);
        this.modId = modId;
        Preconditions.checkNotNull(factory);
        this.factory = factory;
        Preconditions.checkNotNull(existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    public BookProvider(PackOutput output, String modId, BiFunction<ResourceLocation, ExistingFileHelper, B> builderFromModId, ExistingFileHelper existingFileHelper) {
        this(output, modId, loc -> builderFromModId.apply(loc, existingFileHelper), existingFileHelper);
    }


    public B getBuilder(String book, String path) {
        Preconditions.checkNotNull(path, "Path must not be null");
        ResourceLocation outputLoc = Util.resourceLocation(modId, book +"/" + path);
        this.existingFileHelper.trackGenerated(outputLoc, PAGE);
        return generatedPages.computeIfAbsent(outputLoc, factory);
    }

    public B page(String book, String name, String title) {
        return getBuilder(book, name).title(title);
    }

    protected void clear() {
        generatedPages.clear();
    }

    abstract void generatePages();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        clear();
        generatePages();
        return generateAll(pOutput);
    }

    protected CompletableFuture<?> generateAll(CachedOutput cache) {
        CompletableFuture<?>[] futures = new CompletableFuture<?>[this.generatedPages.size()];
        int i = 0;

        for (B page : this.generatedPages.values()) {
            Path target = getPath(page);
            futures[i++] = DataProvider.saveStable(cache, page.toJson(), target);
        }

        return CompletableFuture.allOf(futures);
    }

    protected Path getPath(B model) {
        ResourceLocation loc = model.getLocation();
        return this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(loc.getNamespace()).resolve("book").resolve(loc.getPath() + ".json");
    }

    @Override
    public @NotNull String getName() {
        return "Books: " + modId;
    }
}

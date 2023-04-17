package net.turtlemaster42.pixelsofmc.gui.book;

import com.github.alexthe666.citadel.client.gui.GuiBasicBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiBook1 extends GuiBasicBook {

    private static final ResourceLocation BOOK_PAGE_TEXTURE = new ResourceLocation("citadel:textures/gui/book/book_pages.png");
    private static final ResourceLocation BOOK_BINDING_TEXTURE = new ResourceLocation("citadel:textures/gui/book/book_binding.png");
    private static final ResourceLocation BOOK_WIDGET_TEXTURE = new ResourceLocation("citadel:textures/gui/book/widgets.png");
    private static final ResourceLocation BOOK_BUTTONS_TEXTURE = new ResourceLocation("citadel:textures/gui/book/link_buttons.png");

    public GuiBook1(ItemStack bookStack) {
        super(bookStack, Component.translatable("book.pixelsofmc_guide_book_1.title"));
    }

    public GuiBook1(ItemStack bookStack, String page) {
        super(bookStack, Component.translatable("book.pixelsofmc_guide_book_1.title"));
    }

    @Override
    protected int getBindingColor() {
        return 0x624839;
    }

    @Override
    public ResourceLocation getRootPage() {
        return new ResourceLocation("pixelsofmc:book/book_1/root.json");
    }

    @Override
    public String getTextFileDirectory() {
        return "pixelsofmc:book/book_1/";
    }


    @Override
    protected void playBookOpeningSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
    }

    @Override
    protected void playBookClosingSound() {
    }

    @Override
    protected ResourceLocation getBookPageTexture() {
        return BOOK_PAGE_TEXTURE;
    }

    @Override
    protected ResourceLocation getBookBindingTexture() {
        return BOOK_BINDING_TEXTURE;
    }

    @Override
    protected ResourceLocation getBookWidgetTexture() {
        return BOOK_WIDGET_TEXTURE;
    }

}

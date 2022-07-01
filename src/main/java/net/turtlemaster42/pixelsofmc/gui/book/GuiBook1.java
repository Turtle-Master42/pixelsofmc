package net.turtlemaster42.pixelsofmc.gui.book;

import com.github.alexthe666.citadel.client.gui.GuiBasicBook;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiBook1 extends GuiBasicBook {

    public GuiBook1(ItemStack bookStack) {
        super(bookStack,  new TranslatableComponent("book.pixelsofmc_guide_book_1.title"));
    }

    public GuiBook1(ItemStack bookStack, String page) {
        super(bookStack,  new TranslatableComponent("book.pixelsofmc_guide_book_1.title"));
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
}

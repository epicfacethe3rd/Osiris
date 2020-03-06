package me.finz0.osiris.gui.csgui;

import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

public class CategoryButton {
    int x;
    int y;
    int width;
    int height;
    CsClickGUI gui;
    Module.Category category;

    public CategoryButton(Module.Category c, int iX, int iY, CsClickGUI csClickGUI){
        x= iX;
        y = iY;
        gui = csClickGUI;
        category = c;
        width = FontUtils.getStringWidth(gui.customFont, category.name()) + 20;
        height = FontUtils.getFontHeight(gui.customFont) + 6;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        width = FontUtils.getStringWidth(gui.customFont, category.name()) + 2;
        height = FontUtils.getFontHeight(gui.customFont) + 2;
        Gui.drawRect(x, y, x + width, y + height, 0xff444444);
        Gui.drawRect(x + 2, y + 2, (x + 2) + (width - 2), (y + 2) + (height - 2), 0xff222222);
        FontUtils.drawStringWithShadow(gui.customFont, category.name(), x + 4, y + 4, Rainbow.getInt());
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(mouseX > x && mouseX < width && mouseY > y && mouseY < height){
            gui.currentCategory = category;
            return true;
        }
        return false;
    }
}

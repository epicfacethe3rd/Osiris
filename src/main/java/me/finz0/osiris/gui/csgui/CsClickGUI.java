package me.finz0.osiris.gui.csgui;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.CsClickGuiModule;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsClickGUI extends GuiScreen {
    int x;
    int y;
    int width;
    int height;

    boolean dragging = false;
    int newX;
    int newY;
    int cbuttonX;
    int cbuttonY;

    List<CategoryButton> categoryButtons;

    public boolean customFont;
    public Module.Category currentCategory;

    public CsClickGUI(){
        categoryButtons = new ArrayList<>();
        x = 100;
        y = 25;
        width = 100;
        height = 100;
        customFont = false;
        int buttonX = 0;
        for(Module.Category c : Module.Category.values()){
            CategoryButton button = new CategoryButton(c, cbuttonX + buttonX, cbuttonY, this);
            categoryButtons.add(button);
            buttonX += button.width;
            width = buttonX + button.width;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        customFont = ((CsClickGuiModule) ModuleManager.getModuleByName("CsClickGUI")).cfont.getValBoolean();
        if(dragging){
            x = newX + mouseX;
            y = newY + mouseY;
        }
        //title
        drawRect(x - 2, y - 4 - FontUtils.getFontHeight(customFont), x + width + 2, y - 2, Rainbow.getInt());
        FontUtils.drawStringWithShadow(customFont, OsirisMod.MODNAME, x - 2, y - 4 - FontUtils.getFontHeight(customFont), 0xffaaaaaa);

        drawRect(x - 2, y - 2, x + width + 2, y + height + 2, Rainbow.getInt());
        drawRect(x, y, x + width, y + height, 0xff222222);
        for(CategoryButton button : categoryButtons){
            if(dragging){
                cbuttonX = newX + mouseX;
                cbuttonY = newY + mouseY;
            }
            button.drawScreen(mouseX, mouseY, partialTicks);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(CategoryButton button : categoryButtons){
            if(button.mouseClicked(mouseX, mouseY, mouseButton)) return;
        }

        if(mouseButton == 0 && mouseX >= (x - 2) && mouseX <= (x + width + 2) && mouseY >= (y - 4 - FontUtils.getFontHeight(customFont)) && mouseY <= (y - 2)){
            newX = x - mouseX;
            newY = y - mouseY;
            dragging = true;
            return;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state){
        if (state == 0) dragging = false;
    }

    public boolean doesGuiPauseGame(){
        return false;
    }
}

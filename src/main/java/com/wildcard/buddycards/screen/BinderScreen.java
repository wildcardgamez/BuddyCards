package com.wildcard.buddycards.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BinderScreen extends ContainerScreen<BinderContainer> {

    private static final ResourceLocation TEXTURE1 = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/binder.png");
    private static final ResourceLocation TEXTURE2 = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/binder2.png");
    private static final ResourceLocation TEXTURE3 = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/binder3.png");
    private static final ResourceLocation TEXTURE4 = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/binder4.png");

    public BinderScreen(BinderContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        //set up sizes for the gui
        int size = container.getItems().size();
        this.leftPos = 0;
        this.topPos = 0;
        if (size == 90) {
            this.width = 176;
            this.height = 222;
            return;
        }
        else if (size == 108) {
            this.width = 230;
            this.height = 222;
            return;
        }
        else if (size == 132) {
            this.width = 230;
            this.height = 258;
            return;
        }
        else if (size == 156) {
            this.width = 230;
            this.height = 294;
            return;
        }
        this.width = 176;
        this.height = 222;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        //Draw the name of the binder and the inventory titles
        this.font.draw(matrixStack, title, 8.0f, 6.0f, 4210752);
        int size = menu.getItems().size();
        if (size == 90)
            this.font.draw(matrixStack, inventory.getDisplayName(),8.0f, 128.0f, 4210752);
        else if (size == 108)
            this.font.draw(matrixStack, inventory.getDisplayName(),35.0f, 128.0f, 4210752);
        else if (size == 132)
            this.font.draw(matrixStack, inventory.getDisplayName(),35.0f, 164.0f, 4210752);
        else if (size == 156)
            this.font.draw(matrixStack, inventory.getDisplayName(),35.0f, 200.0f, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //Place the texture for the binder gui
        int size = menu.getItems().size();
        assert this.minecraft != null;
        if (size == 90) {
            this.minecraft.getTextureManager().bind(TEXTURE1);
            blit(matrixStack, leftPos, topPos, 0, 0, width, height, 256, 256);
        }
        else if (size == 108) {
            this.minecraft.getTextureManager().bind(TEXTURE2);
            blit(matrixStack, leftPos, topPos, 0, 0, width, height, 256, 256);
        }
        else if (size == 132) {
            this.minecraft.getTextureManager().bind(TEXTURE3);
            blit(matrixStack, leftPos, topPos, 0, 0, width, height, 512, 512);
        }
        else if (size == 156) {
            this.minecraft.getTextureManager().bind(TEXTURE4);
            blit(matrixStack, leftPos, topPos, 0, 0, width, height, 512, 512);
        }
    }
}

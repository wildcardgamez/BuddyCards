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
        int size = container.getInventory().size();
        if (size == 90) {
            this.guiLeft = 0;
            this.guiTop = 0;
            this.xSize = 176;
            this.ySize = 222;
            return;
        }
        else if (size == 108) {
            this.guiLeft = 0;
            this.guiTop = 0;
            this.xSize = 230;
            this.ySize = 222;
            return;
        }
        else if (size == 132) {
            this.guiLeft = 0;
            this.guiTop = 0;
            this.xSize = 230;
            this.ySize = 258;
            return;
        }
        else if (size == 156) {
            this.guiLeft = 0;
            this.guiTop = 0;
            this.xSize = 230;
            this.ySize = 294;
            return;
        }
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 222;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        //Draw the name of the binder and the inventory titles
        this.font.func_243248_b(matrixStack, title, 8.0f, 6.0f, 4210752);
        int size = container.getInventory().size();
        if (size == 90)
            this.font.func_243248_b(matrixStack, playerInventory.getDisplayName(),8.0f, 128.0f, 4210752);
        else if (size == 108)
            this.font.func_243248_b(matrixStack, playerInventory.getDisplayName(),35.0f, 128.0f, 4210752);
        else if (size == 132)
            this.font.func_243248_b(matrixStack, playerInventory.getDisplayName(),35.0f, 164.0f, 4210752);
        else if (size == 156)
            this.font.func_243248_b(matrixStack, playerInventory.getDisplayName(),35.0f, 200.0f, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //Place the texture for the binder gui
        int size = container.getInventory().size();
        assert this.minecraft != null;
        if (size == 90) {
            this.minecraft.getTextureManager().bindTexture(TEXTURE1);
            blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize, 256, 256);
        }
        else if (size == 108) {
            this.minecraft.getTextureManager().bindTexture(TEXTURE2);
            blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize, 256, 256);
        }
        else if (size == 132) {
            this.minecraft.getTextureManager().bindTexture(TEXTURE3);
            blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
        }
        else if (size == 156) {
            this.minecraft.getTextureManager().bindTexture(TEXTURE4);
            blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
        }
    }
}

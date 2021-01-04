package com.wildcard.buddycards.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BinderScreen extends ContainerScreen<BinderContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/binder.png");

    public BinderScreen(BinderContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        //set up sizes for the gui
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
        this.font.func_243248_b(matrixStack, playerInventory.getDisplayName(), 8.0f, 128.0f, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //Place the texture for the binder gui
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize, 256, 256);
    }
}

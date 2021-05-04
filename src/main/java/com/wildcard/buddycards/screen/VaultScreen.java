package com.wildcard.buddycards.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.VaultContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class VaultScreen extends ContainerScreen<VaultContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/buddysteel_vault.png");
    public VaultScreen(VaultContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 230;
        this.imageHeight = 294;
        return;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        //Draw the name of the vault and the inventory titles
        this.font.draw(matrixStack, title, 8.0f, 6.0f, 4210752);
        int size = menu.getItems().size();
        this.font.draw(matrixStack, inventory.getDisplayName(),35.0f, 200.0f, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //Place the texture for the vault gui
        int size = menu.getItems().size();
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(TEXTURE);
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight, 512, 512);
    }
}

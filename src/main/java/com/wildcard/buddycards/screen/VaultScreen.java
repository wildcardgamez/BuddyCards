package com.wildcard.buddycards.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.VaultContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class VaultScreen extends AbstractContainerScreen<VaultContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(BuddyCards.MOD_ID, "textures/gui/buddysteel_vault.png");
    public VaultScreen(VaultContainer container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 230;
        this.imageHeight = 294;
        return;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        //Draw the name of the vault and the inventory titles
        this.font.draw(matrixStack, title, 8.0f, 6.0f, 4210752);
        int size = menu.getItems().size();
        this.font.draw(matrixStack, playerInventoryTitle,35.0f, 200.0f, 4210752);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //Place the texture for the vault gui
        int size = menu.getItems().size();
        assert this.minecraft != null;
        RenderSystem._setShaderTexture(0, TEXTURE);
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight, 512, 512);
    }
}

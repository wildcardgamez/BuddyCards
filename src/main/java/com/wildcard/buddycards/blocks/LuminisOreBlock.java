package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.CardRegistry;
import com.wildcard.buddycards.util.ConfigManager;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Random;

public class LuminisOreBlock extends OreBlock {
    public LuminisOreBlock() {
        super(AbstractBlock.Properties.copy(Blocks.GOLD_ORE).lightLevel((state) -> 1));
    }

    @Override
    protected int xpOnDrop(Random random) {
        return MathHelper.nextInt(random, 1, 5);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        double i = player.getMainHandItem().equals(RegistryHandler.LUMINIS_PICKAXE.get()) ? 1 : 1.4;
        if(ModList.get().isLoaded("curios") &&
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.LUMINIS_RING.get(), player).isPresent() &&
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.LUMINIS_RING.get(), player).get().right.getItem().equals(RegistryHandler.LUMINIS_RING.get()))
            i += .3;
        if(ModList.get().isLoaded("curios") && ((
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.LUMINIS_MEDAL.get(), player).isPresent() &&
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.LUMINIS_MEDAL.get(), player).get().right.getItem().equals(RegistryHandler.LUMINIS_MEDAL.get())) || (
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.PERFECT_BUDDYSTEEL_MEDAL.get(), player).isPresent() &&
                        CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.PERFECT_BUDDYSTEEL_MEDAL.get(), player).get().right.getItem().equals(RegistryHandler.PERFECT_BUDDYSTEEL_MEDAL.get())))) {
            i += .3;
            if(Math.random() < ConfigManager.cardLuminisOdds.get())
            {
                ItemStack card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
                while (!card.getItem().isFoil(card) || card.getRarity() == Rarity.EPIC) {
                    card = new ItemStack(CardRegistry.LOADED_CARDS.get((int)(Math.random() * (CardRegistry.LOADED_CARDS.size()))).get());
                }
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), card);
            }
        }
        if(Math.random() < ConfigManager.deepLuminisOdds.get() * i)
            InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.DEEP_LUMINIS_CRYSTAL.get()));
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }
}

package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.items.GummyCardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
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
        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) == 0) {
            double i = player.getMainHandItem().equals(BuddycardsItems.LUMINIS_PICKAXE.get()) ? 1.4 : 1;
            if (ModList.get().isLoaded("curios") &&
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_RING.get(), player).isPresent() &&
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_RING.get(), player).get().right.getItem().equals(BuddycardsItems.LUMINIS_RING.get()))
                i += .3;
            if (ModList.get().isLoaded("curios") && ((
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_MEDAL.get(), player).isPresent() &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_MEDAL.get(), player).get().right.getItem().equals(BuddycardsItems.LUMINIS_MEDAL.get())) || (
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), player).isPresent() &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), player).get().right.getItem().equals(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get())))) {
                i += .3;
                if (Math.random() < ConfigManager.cardLuminisOdds.get()) {
                    ItemStack card = new ItemStack(getRandomLoadedCard());
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), card);
                }
            }
            if (Math.random() < ConfigManager.deepLuminisOdds.get() * i)
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BuddycardsItems.DEEP_LUMINIS_CRYSTAL.get()));
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    public static CardItem getRandomLoadedCard() {
        CardItem card = BuddycardsItems.LOADED_CARDS.get((int)(Math.random() * BuddycardsItems.LOADED_CARDS.size())).get();
        while (card instanceof GummyCardItem || card.getRegistryName().toString().endsWith("s") || card.getRarity() == Rarity.EPIC) {
            card = BuddycardsItems.LOADED_CARDS.get((int)(Math.random() * BuddycardsItems.LOADED_CARDS.size())).get();
        }
        return card;
    }
}

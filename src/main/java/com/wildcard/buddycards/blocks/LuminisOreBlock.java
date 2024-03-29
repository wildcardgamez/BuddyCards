package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.items.GummyCardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

public class LuminisOreBlock extends OreBlock {
    public LuminisOreBlock() {
        super(Properties.copy(Blocks.GOLD_ORE).lightLevel((state) -> 1));
    }

    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) == 0) {
            double deepOdds = player.getMainHandItem().getItem().equals(BuddycardsItems.LUMINIS_PICKAXE.get()) ? ConfigManager.deepLuminisOdds.get() : ConfigManager.deepLuminisOdds.get() + .05;
            if (ModList.get().isLoaded("curios") &&
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_RING.get(), player).isPresent() &&
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_RING.get(), player).get().right.getItem().equals(BuddycardsItems.LUMINIS_RING.get()))
                deepOdds += .03;
            if (ModList.get().isLoaded("curios") && ((
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_MEDAL.get(), player).isPresent() &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.LUMINIS_MEDAL.get(), player).get().right.getItem().equals(BuddycardsItems.LUMINIS_MEDAL.get())) || (
                    CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), player).isPresent() &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), player).get().right.getItem().equals(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get())))) {
                deepOdds += .03;
                if (Math.random() < ConfigManager.cardLuminisOdds.get()) {
                    ItemStack card = new ItemStack(getRandomLoadedCard());
                    CompoundTag nbt = new CompoundTag();
                    nbt.putBoolean("foil", true);
                    card.setTag(nbt);
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), card);
                }
            }
            if (Math.random() < deepOdds)
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(BuddycardsItems.DEEP_LUMINIS_CRYSTAL.get()));
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    public static CardItem getRandomLoadedCard() {
        CardItem card = BuddycardsItems.LOADED_CARDS.get((int)(Math.random() * BuddycardsItems.LOADED_CARDS.size())).get();
        while (card instanceof GummyCardItem || card.getRarity() == Rarity.EPIC) {
            card = BuddycardsItems.LOADED_CARDS.get((int)(Math.random() * BuddycardsItems.LOADED_CARDS.size())).get();
        }
        return card;
    }
}

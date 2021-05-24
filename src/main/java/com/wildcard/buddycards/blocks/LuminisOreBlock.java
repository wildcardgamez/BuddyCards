package com.wildcard.buddycards.blocks;

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
        double i = player.getMainHandItem().equals(RegistryHandler.LUMINIS_PICKAXE.get()) ? 1 : 1.5;
        if(ModList.get().isLoaded("curios") &&
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.LUMINIS_RING.get(), player).isPresent() &&
                CuriosApi.getCuriosHelper().findEquippedCurio(RegistryHandler.LUMINIS_RING.get(), player).get().right.getItem().equals(RegistryHandler.ZYLEX_RING.get()))
            i+= .2;
        if(Math.random() < ConfigManager.deepLuminisOdds.get() * i)
            InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.DEEP_LUMINIS_CRYSTAL.get()));
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }
}

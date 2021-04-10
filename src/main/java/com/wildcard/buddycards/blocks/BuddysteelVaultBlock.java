package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultTile;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class BuddysteelVaultBlock extends ContainerBlock {
    public static final DirectionProperty DIR = HorizontalBlock.HORIZONTAL_FACING;
    protected static final VoxelShape VAULT_SHAPE = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public BuddysteelVaultBlock(int setNumber) {
        super(Properties.from(Blocks.IRON_BLOCK).hardnessAndResistance(5, 1200));
        this.setDefaultState(this.stateContainer.getBaseState().with(DIR, Direction.NORTH));
        SET_NUMBER = setNumber;
    }

    final int SET_NUMBER;

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VAULT_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        return this.getDefaultState().with(DIR, context.getPlacementHorizontalFacing());
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BuddysteelVaultTile();
    }

    @Override
    public boolean  hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIR);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof BuddysteelVaultTile) {
            tileentity.validate();
            if(stack.hasDisplayName())
                ((BuddysteelVaultTile) tileentity).setDisplayName(stack.getDisplayName());
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if(playerIn instanceof ServerPlayerEntity && tileentity instanceof BuddysteelVaultTile) {
            if(playerIn.getHeldItem(handIn).getItem() == RegistryHandler.BUDDYSTEEL_KEY.get()) {
                if (((BuddysteelVaultTile)tileentity).isLocked()) {
                    if (((BuddysteelVaultTile)tileentity).toggleLock(playerIn.getUniqueID()))
                        playerIn.sendStatusMessage(new TranslationTextComponent("block.buddycards.vault.unlock"), true);
                    else
                        playerIn.sendStatusMessage(new TranslationTextComponent("block.buddycards.vault.fail_unlock"), true);
                }
                else {
                    ((BuddysteelVaultTile)tileentity).toggleLock(playerIn.getUniqueID());
                    playerIn.sendStatusMessage(new TranslationTextComponent("block.buddycards.vault.lock"), true);
                }
            }
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, (BuddysteelVaultTile)tileentity, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new BuddysteelVaultTile();
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (world.getTileEntity(pos) instanceof BuddysteelVaultTile) {
            IItemHandler handler = ((BuddysteelVaultTile)world.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(new ItemStackHandler());
            for (int i = 0; i < handler.getSlots(); i++) {
                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(i));
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(SET_NUMBER == 4 && !ModList.get().isLoaded("byg"))
            return;
        else if(SET_NUMBER == 5 && !ModList.get().isLoaded("create"))
            return;
        else if(SET_NUMBER == 6 && !ModList.get().isLoaded("aquaculture"))
            return;
        else if(SET_NUMBER == 7 && !ModList.get().isLoaded("farmersdelight"))
            return;
        super.fillItemGroup(group, items);
    }
}

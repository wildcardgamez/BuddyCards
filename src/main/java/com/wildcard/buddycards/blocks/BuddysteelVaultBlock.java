package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultTile;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;

public class BuddysteelVaultBlock extends ContainerBlock {
    public static final DirectionProperty DIR = HorizontalBlock.FACING;
    protected static final VoxelShape VAULT_SHAPE = Block.box(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    public BuddysteelVaultBlock(int setNumber, String modId) {
        super(Properties.copy(Blocks.IRON_BLOCK).strength(5, 1200));
        this.registerDefaultState(this.stateDefinition.any().setValue(DIR, Direction.NORTH));
        SET_NUMBER = setNumber;
        SPECIFIC_MOD = modId;
    }

    final int SET_NUMBER;
    final String SPECIFIC_MOD;

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VAULT_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        return this.defaultBlockState().setValue(DIR, context.getHorizontalDirection());
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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIR);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof BuddysteelVaultTile) {
            tileentity.clearRemoved();
            if(stack.hasCustomHoverName())
                ((BuddysteelVaultTile) tileentity).setDisplayName(stack.getHoverName());
            worldIn.setBlockEntity(pos, tileentity);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if(playerIn instanceof ServerPlayerEntity && tileentity instanceof BuddysteelVaultTile) {
            if(playerIn.getItemInHand(handIn).getItem() == BuddycardsItems.BUDDYSTEEL_KEY.get()) {
                if (((BuddysteelVaultTile)tileentity).isLocked()) {
                    if (((BuddysteelVaultTile)tileentity).toggleLock(playerIn.getUUID()))
                        playerIn.displayClientMessage(new TranslationTextComponent("block.buddycards.vault.unlock"), true);
                    else
                        playerIn.displayClientMessage(new TranslationTextComponent("block.buddycards.vault.fail_unlock"), true);
                }
                else {
                    ((BuddysteelVaultTile)tileentity).toggleLock(playerIn.getUUID());
                    playerIn.displayClientMessage(new TranslationTextComponent("block.buddycards.vault.lock"), true);
                }
                return ActionResultType.SUCCESS;
            }
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, (BuddysteelVaultTile)tileentity, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new BuddysteelVaultTile();
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (world.getBlockEntity(pos) instanceof BuddysteelVaultTile) {
            if (player.getItemInHand(Hand.MAIN_HAND).getItem() == BuddycardsItems.ZYLEX_RING.get() ||
                    (ModList.get().isLoaded("curios") &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), player).isPresent() &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), player).get().right.getItem().equals(BuddycardsItems.ZYLEX_RING.get()))) {
                ItemStack i = new ItemStack(state.getBlock().asItem());
                CompoundNBT nbt = new CompoundNBT();
                nbt.put("BlockEntityTag", world.getBlockEntity(pos).save(new CompoundNBT()));
                i.setTag(nbt);
                InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), i);
            }
            else {
                IItemHandler handler = world.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(new ItemStackHandler());
                for (int i = 0; i < handler.getSlots(); i++) {
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(i));
                }
                if(!player.isCreative())
                    InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(state.getBlock().asItem()));
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded(SPECIFIC_MOD))
            return;
        super.fillItemCategory(group, items);
    }
}

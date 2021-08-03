package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.blocks.tiles.BuddysteelVaultBlockEntity;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;

public class BuddysteelVaultBlock extends BaseEntityBlock {
    public static final DirectionProperty DIR = HorizontalDirectionalBlock.FACING;
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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return VAULT_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement (BlockPlaceContext context) {
        return this.defaultBlockState().setValue(DIR, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIR);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof BuddysteelVaultBlockEntity) {
            tileentity.clearRemoved();
            if(stack.hasCustomHoverName())
                ((BuddysteelVaultBlockEntity) tileentity).setDisplayName(stack.getHoverName());
            worldIn.setBlockEntity(tileentity);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player playerIn, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if(playerIn instanceof ServerPlayer && tileentity instanceof BuddysteelVaultBlockEntity) {
            if(playerIn.getItemInHand(handIn).getItem() == BuddycardsItems.BUDDYSTEEL_KEY.get()) {
                if (((BuddysteelVaultBlockEntity)tileentity).isLocked()) {
                    if (((BuddysteelVaultBlockEntity)tileentity).toggleLock(playerIn.getUUID()))
                        playerIn.displayClientMessage(new TranslatableComponent("block.buddycards.vault.unlock"), true);
                    else
                        playerIn.displayClientMessage(new TranslatableComponent("block.buddycards.vault.fail_unlock"), true);
                }
                else {
                    ((BuddysteelVaultBlockEntity)tileentity).toggleLock(playerIn.getUUID());
                    playerIn.displayClientMessage(new TranslatableComponent("block.buddycards.vault.lock"), true);
                }
                return InteractionResult.SUCCESS;
            }
            NetworkHooks.openGui((ServerPlayer) playerIn, (BuddysteelVaultBlockEntity)tileentity, pos);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BuddysteelVaultBlockEntity(pos, state);
    }

    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (world.getBlockEntity(pos) instanceof BuddysteelVaultBlockEntity && !player.isCreative()) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == BuddycardsItems.ZYLEX_RING.get() ||
                    (ModList.get().isLoaded("curios") &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), player).isPresent() &&
                            CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_RING.get(), player).get().right.getItem().equals(BuddycardsItems.ZYLEX_RING.get()))) {
                ItemStack i = new ItemStack(state.getBlock().asItem());
                CompoundTag nbt = new CompoundTag();
                nbt.put("BlockEntityTag", world.getBlockEntity(pos).save(new CompoundTag()));
                i.setTag(nbt);
                Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), i);
            }
            else {
                IItemHandler handler = world.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(new ItemStackHandler());
                for (int i = 0; i < handler.getSlots(); i++) {
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(i));
                }
                if(!player.isCreative())
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(state.getBlock().asItem()));
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded(SPECIFIC_MOD))
            return;
        super.fillItemCategory(group, items);
    }
}

package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.blocks.tiles.CardStandTile;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class CardStandBlock extends Block{
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 1.0D, 11.0D);

    public CardStandBlock() {
        super(Properties.copy(Blocks.STONE_BUTTON));
        NEEDED_MOD = "";
    }

    public CardStandBlock(String neededMod) {
        super(Properties.copy(Blocks.STONE_BUTTON));
        NEEDED_MOD = neededMod;
    }

    final String NEEDED_MOD;

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public VoxelShape getOcclusionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof CardStandTile) {
            tileentity.clearRemoved();
            worldIn.setBlockEntity(pos, tileentity);
        }
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CardStandTile();
    }

    @Override
    public boolean  hasTileEntity(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.getBlockEntity(pos) instanceof CardStandTile) {
            CardStandTile standTile = (CardStandTile) world.getBlockEntity(pos);
            ItemStack stack = player.getItemInHand(hand);
            if(stack.getItem() == BuddycardsItems.BUDDYSTEEL_KEY.get()) {
                if (standTile.isLocked()) {
                    if (standTile.toggleLock(player.getUUID()))
                        player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_stand.unlock"), true);
                    else
                        player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_stand.fail_unlock"), true);
                }
                else {
                    standTile.toggleLock(player.getUUID());
                    player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_stand.lock"), true);
                }
            }
            else if (standTile.isLocked())
                player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_stand.lock"), true);
            else if(standTile.getCard().getItem() instanceof CardItem) {
                ItemStack oldCard = standTile.getCard();
                if (stack.getItem() instanceof CardItem) {
                    ItemStack card = new ItemStack(stack.getItem(), 1);
                    card.setTag(stack.getTag());
                    standTile.setCard(card);
                    stack.shrink(1);
                }
                else {
                    standTile.setCard(ItemStack.EMPTY);
                }
                player.addItem(oldCard);
                standTile.setDir((int)((player.getYHeadRot() * 16.0F / 360.0F) + 0.5D) & 15);
            }
            else if(stack.getItem() instanceof CardItem) {
                ItemStack card = new ItemStack(stack.getItem(), 1);
                card.setTag(stack.getTag());
                standTile.setCard(card);
                stack.shrink(1);
                standTile.setDir((int)((player.getYHeadRot() * 16.0F / 360.0F) + 0.5D) & 15);
            }
        }
        world.updateNeighbourForOutputSignal(pos, this);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (world.getBlockEntity(pos) instanceof CardStandTile)
            InventoryHelper.dropContents(world, pos, NonNullList.withSize(1, ((CardStandTile) (world.getBlockEntity(pos))).getCard()));
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(NEEDED_MOD != "" && !ModList.get().isLoaded(NEEDED_MOD))
            return;
        super.fillItemCategory(group, items);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, IBlockReader world, BlockPos pos, Entity entity)
    {
    	CardStandTile displayTile = (CardStandTile) world.getBlockEntity(pos);
    	if ( displayTile.isLocked() )
    	{
    		return false;
    	}
    	
    	return super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
    	CardStandTile displayTile = (CardStandTile) world.getBlockEntity(pos);
    	if ( displayTile.isLocked() )
    	{
    		return false;
    	}
    	
    	return super.canHarvestBlock(state, world, pos, player);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getAnalogOutputSignal(BlockState blockState, World world, BlockPos pos) {
        TileEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof CardStandTile && ((CardStandTile) tileentity).getCard().isEmpty()) {
            return 10;
        }
        else
            return 0;
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return canSupportRigidBlock(worldIn, blockpos) || canSupportCenter(worldIn, blockpos, Direction.UP);
    }

    @Override
    public boolean isPossibleToRespawnInThis() {
        return true;
    }
}
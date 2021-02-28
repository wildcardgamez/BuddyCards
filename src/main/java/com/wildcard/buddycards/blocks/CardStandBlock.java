package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class CardStandBlock extends Block{
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 1.0D, 11.0D);

    public CardStandBlock() {
        super(Block.Properties.from(Blocks.STONE_BUTTON));
        NEEDED_MOD = "";
    }

    public CardStandBlock(String neededMod) {
        super(Block.Properties.from(Blocks.STONE_BUTTON));
        NEEDED_MOD = neededMod;
    }

    final String NEEDED_MOD;

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof CardStandTile) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
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
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.getTileEntity(pos) instanceof CardStandTile) {
            CardStandTile standTile = (CardStandTile) world.getTileEntity(pos);
            ItemStack stack = player.getHeldItem(hand);
            if(stack.getItem() == RegistryHandler.BUDDYSTEEL_KEY.get()) {
                if (standTile.isLocked()) {
                    if (standTile.toggleLock(player.getUniqueID()))
                        player.sendStatusMessage(new TranslationTextComponent("block.buddycards.card_stand.unlock"), true);
                    else
                        player.sendStatusMessage(new TranslationTextComponent("block.buddycards.card_stand.fail_unlock"), true);
                }
                else {
                    standTile.toggleLock(player.getUniqueID());
                    player.sendStatusMessage(new TranslationTextComponent("block.buddycards.card_stand.lock"), true);
                }
            }
            else if (standTile.isLocked())
                player.sendStatusMessage(new TranslationTextComponent("block.buddycards.card_stand.lock"), true);
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
                player.addItemStackToInventory(oldCard);
                standTile.setDir((int)((player.getRotationYawHead() * 16.0F / 360.0F) + 0.5D) & 15);
            }
            else if(stack.getItem() instanceof CardItem) {
                ItemStack card = new ItemStack(stack.getItem(), 1);
                card.setTag(stack.getTag());
                standTile.setCard(card);
                stack.shrink(1);
                standTile.setDir((int)((player.getRotationYawHead() * 16.0F / 360.0F) + 0.5D) & 15);
            }
        }
        world.updateComparatorOutputLevel(pos, this);
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (world.getTileEntity(pos) instanceof CardStandTile)
            InventoryHelper.dropItems(world, pos, NonNullList.withSize(1, ((CardStandTile) (world.getTileEntity(pos))).getCard()));
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if(NEEDED_MOD != "" && !ModList.get().isLoaded(NEEDED_MOD))
            return;
        super.fillItemGroup(group, items);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity instanceof CardStandTile && ((CardStandTile) tileentity).getCard().isEmpty()) {
            return 10;
        }
        else
            return 0;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        return hasSolidSideOnTop(worldIn, blockpos) || hasEnoughSolidSide(worldIn, blockpos, Direction.UP);
    }

    @Override
    public boolean canSpawnInBlock() {
        return true;
    }
}
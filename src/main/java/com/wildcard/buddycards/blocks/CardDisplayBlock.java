package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.blocks.tiles.CardDisplayTile;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class CardDisplayBlock extends Block {
    public static final DirectionProperty DIR = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape NSHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape ESHAPE = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SSHAPE = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WSHAPE = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    public CardDisplayBlock() {
        super(Properties.copy(Blocks.OAK_PLANKS));
        this.registerDefaultState(this.defaultBlockState().setValue(DIR, Direction.NORTH));
        NEEDED_MOD = "";
    }

    public CardDisplayBlock(String neededMod) {
        super(Properties.copy(Blocks.OAK_PLANKS));
        this.registerDefaultState(this.defaultBlockState().setValue(DIR, Direction.NORTH));
        NEEDED_MOD = neededMod;
    }

    final String NEEDED_MOD;

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.getValue(DIR);
        switch(direction) {
            case NORTH:
            default:
                return NSHAPE;
            case EAST:
                return ESHAPE;
            case SOUTH:
                return SSHAPE;
            case WEST:
                return WSHAPE;
        }
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        switch(direction) {
            case CLOCKWISE_90:
                switch(state.getValue(DIR)) {
                    case NORTH:
                        return state.setValue(DIR, Direction.EAST);
                    case EAST:
                        return state.setValue(DIR, Direction.SOUTH);
                    case SOUTH:
                        return state.setValue(DIR, Direction.WEST);
                    case WEST:
                        return state.setValue(DIR, Direction.NORTH);
                }
            case CLOCKWISE_180:
                switch(state.getValue(DIR)) {
                    case NORTH:
                        return state.setValue(DIR, Direction.SOUTH);
                    case EAST:
                        return state.setValue(DIR, Direction.WEST);
                    case SOUTH:
                        return state.setValue(DIR, Direction.NORTH);
                    case WEST:
                        return state.setValue(DIR, Direction.EAST);
                }
            case COUNTERCLOCKWISE_90:
                switch(state.getValue(DIR)) {
                    case NORTH:
                        return state.setValue(DIR, Direction.WEST);
                    case EAST:
                        return state.setValue(DIR, Direction.NORTH);
                    case SOUTH:
                        return state.setValue(DIR, Direction.EAST);
                    case WEST:
                        return state.setValue(DIR, Direction.SOUTH);
                }
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        return this.defaultBlockState().setValue(DIR, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIR);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof CardDisplayTile) {
            worldIn.setBlockEntity(pos, tileentity);
        }
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CardDisplayTile();
    }

    @Override
    public boolean  hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        int slot = getSlot(state.getValue(DIR), hit.getLocation());
        if (world.getBlockEntity(pos) instanceof CardDisplayTile) {
            CardDisplayTile displayTile = (CardDisplayTile) world.getBlockEntity(pos);
            ItemStack stack = player.getItemInHand(hand);
            if(stack.getItem() == RegistryHandler.BUDDYSTEEL_KEY.get()) {
                if (displayTile.isLocked()) {
                    if (displayTile.toggleLock(player.getUUID()))
                        player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_display.unlock"), true);
                    else
                        player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_display.fail_unlock"), true);
                }
                else {
                    displayTile.toggleLock(player.getUUID());
                    player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_display.lock"), true);
                }
            }
            else if (displayTile.isLocked())
                player.displayClientMessage(new TranslationTextComponent("block.buddycards.card_display.lock"), true);
            else if(displayTile.getCardInSlot(slot).getItem() instanceof CardItem) {
                ItemStack oldCard = displayTile.getCardInSlot(slot);
                if (stack.getItem() instanceof CardItem) {
                    ItemStack card = new ItemStack(stack.getItem(), 1);
                    card.setTag(stack.getTag());
                    displayTile.putCardInSlot(card, slot);
                    stack.shrink(1);
                }
                else {
                    displayTile.putCardInSlot(ItemStack.EMPTY, slot);
                }
                player.addItem(oldCard);
            }
            else if(stack.getItem() instanceof CardItem) {
                ItemStack card = new ItemStack(stack.getItem(), 1);
                card.setTag(stack.getTag());
                displayTile.putCardInSlot(card, slot);
                stack.shrink(1);
            }
        }
        return ActionResultType.SUCCESS;
    }

    private int getSlot(Direction dir, Vector3d hit) {
        hit = new Vector3d(
                ((hit.x < 0) ? hit.x - Math.floor(hit.x) : hit.x) % 1,
                ((hit.y < 0) ? hit.y - Math.floor(hit.y) : hit.y) % 1,
                ((hit.z < 0) ? hit.z - Math.floor(hit.z) : hit.z) % 1
        );
        if(hit.y > .5) {
            if(dir == Direction.NORTH) {
                if (hit.x < 1/3f)
                    return 1;
                else if (hit.x < 2/3f)
                    return 2;
                else
                    return 3;
            }
            if(dir == Direction.EAST) {
                if (hit.z < 1/3f)
                    return 1;
                else if (hit.z < 2/3f)
                    return 2;
                else
                    return 3;
            }
            if(dir == Direction.SOUTH) {
                if (hit.x > 2/3f)
                    return 1;
                else if (hit.x > 1/3f)
                    return 2;
                else
                    return 3;
            }
            if(dir == Direction.WEST) {
                if (hit.z > 2/3f)
                    return 1;
                else if (hit.z > 1/3f)
                    return 2;
                else
                    return 3;
            }
        }
        else {
            if(dir == Direction.NORTH) {
                if (hit.x < 1/3f)
                    return 4;
                else if (hit.x < 2/3f)
                    return 5;
                else
                    return 6;
            }
            if(dir == Direction.EAST) {
                if (hit.z < 1/3f)
                    return 4;
                else if (hit.z < 2/3f)
                    return 5;
                else
                    return 6;
            }
            if(dir == Direction.SOUTH) {
                if (hit.x > 2/3f)
                    return 4;
                else if (hit.x > 1/3f)
                    return 5;
                else
                    return 6;
            }
            if(dir == Direction.WEST) {
                if (hit.z > 2/3f)
                    return 4;
                else if (hit.z > 1/3f)
                    return 5;
                else
                    return 6;
            }
        }
        return 1;
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
        if (world.getBlockEntity(pos) instanceof CardDisplayTile)
            InventoryHelper.dropContents(world, pos, ((CardDisplayTile) (world.getBlockEntity(pos))).getInventory());
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
    	CardDisplayTile displayTile = (CardDisplayTile) world.getBlockEntity(pos);
    	if ( displayTile.isLocked() )
    	{
    		return false;
    	}
    	
    	return super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
    	CardDisplayTile displayTile = (CardDisplayTile) world.getBlockEntity(pos);
    	if ( displayTile.isLocked() )
    	{
    		return false;
    	}
    	
    	return super.canHarvestBlock(state, world, pos, player);
    }
}

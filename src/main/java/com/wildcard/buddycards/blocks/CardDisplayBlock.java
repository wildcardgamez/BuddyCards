package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.items.CardItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class CardDisplayBlock extends Block {
    public static final DirectionProperty DIR = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape NSHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape ESHAPE = Block.makeCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SSHAPE = Block.makeCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WSHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);

    public CardDisplayBlock() {
        super(Block.Properties.from(Blocks.OAK_PLANKS));
        this.setDefaultState(this.stateContainer.getBaseState().with(DIR, Direction.NORTH));
        NEEDED_MOD = "";
    }

    public CardDisplayBlock(String neededMod) {
        super(Block.Properties.from(Blocks.OAK_PLANKS));
        this.setDefaultState(this.stateContainer.getBaseState().with(DIR, Direction.NORTH));
        NEEDED_MOD = neededMod;
    }

    final String NEEDED_MOD;

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(DIR);
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
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        return this.getDefaultState().with(DIR, context.getPlacementHorizontalFacing());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DIR);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof CardDisplayTile) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
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

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        int slot = getSlot(state.get(DIR), hit.getHitVec());
        if (world.getTileEntity(pos) instanceof CardDisplayTile) {
            CardDisplayTile displayTile = (CardDisplayTile) world.getTileEntity(pos);
            ItemStack stack = player.getHeldItem(hand);
            if(displayTile.getCardInSlot(slot).getItem() instanceof CardItem) {
                ItemStack oldCard = displayTile.getCardInSlot(slot);
                if (stack.getItem() instanceof CardItem) {
                    displayTile.putCardInSlot(new ItemStack(stack.getItem(), 1), slot);
                    stack.shrink(1);
                }
                else {
                    displayTile.putCardInSlot(ItemStack.EMPTY, slot);
                }
                player.addItemStackToInventory(oldCard);
            }
            else if(stack.getItem() instanceof CardItem) {
                displayTile.putCardInSlot(new ItemStack(stack.getItem(), 1), slot);
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
        if (world.getTileEntity(pos) instanceof CardDisplayTile)
            InventoryHelper.dropItems(world, pos, ((CardDisplayTile) (world.getTileEntity(pos))).getInventory());
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        //if(NEEDED_MOD != "" && !ModList.get().isLoaded(NEEDED_MOD))
            //return;
        super.fillItemGroup(group, items);
    }
}

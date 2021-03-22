package com.wildcard.buddycards.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BuddysteelVaultBlock extends ContainerBlock {
    public static final DirectionProperty DIR = HorizontalBlock.HORIZONTAL_FACING;

    protected static final VoxelShape VAULT_SHAPE = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);

    private final int SET_NUMBER;

    public BuddysteelVaultBlock(int setNum) {
        super(Properties.from(Blocks.IRON_BLOCK));
        this.setDefaultState(this.stateContainer.getBaseState().with(DIR, Direction.NORTH));
        SET_NUMBER = setNum;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VAULT_SHAPE;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
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
            playerIn.openContainer((INamedContainerProvider) tileentity);
        }
        return super.onBlockActivated(state, worldIn, pos, playerIn, handIn, hit);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new BuddysteelVaultTile();
    }

    public int getSetNumber() {
        return SET_NUMBER;
    }
}

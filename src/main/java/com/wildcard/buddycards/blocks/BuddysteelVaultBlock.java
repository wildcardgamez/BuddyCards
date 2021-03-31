package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BuddysteelVaultBlock extends ContainerBlock {
    public static final DirectionProperty DIR = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    protected static final VoxelShape VAULT_SHAPE = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape OPEN_SHAPE = Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 9.0D, 15.0D);

    public BuddysteelVaultBlock() {
        super(Properties.from(Blocks.IRON_BLOCK).hardnessAndResistance(5, 1200));
        this.setDefaultState(this.stateContainer.getBaseState().with(DIR, Direction.NORTH).with(OPEN, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(OPEN))
            return OPEN_SHAPE;
        else
            return VAULT_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement (BlockItemUseContext context) {
        return this.getDefaultState().with(DIR, context.getPlacementHorizontalFacing()).with(OPEN, false);
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
        builder.add(DIR).add(OPEN);
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
        return ActionResultType.PASS;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new BuddysteelVaultTile();
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            if (state.get(OPEN) != worldIn.isBlockPowered(pos)) {
                if (state.get(OPEN)) {
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlockState(pos, state.func_235896_a_(OPEN), 2);
                }
            }
        }
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(OPEN) && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.func_235896_a_(OPEN), 2);
        }
    }
}

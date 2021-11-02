package com.wildcard.buddycards.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class BoosterBoxBlock extends Block {
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 4);
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{VoxelShapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public BoosterBoxBlock(int setNumber, String modId) {
        super(Properties.copy(Blocks.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)));
        SET_NUMBER = setNumber;
        SPECIFIC_MOD = modId;
    }

    final int SET_NUMBER;
    final String SPECIFIC_MOD;

    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS) - 1];
    }

    public VoxelShape getBlockSupportShape(BlockState state, IBlockReader blockReader, BlockPos pos) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    public VoxelShape getVisualShape(BlockState blockState, IBlockReader blockReader, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, IWorld world, BlockPos pos, BlockPos pos1) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, world, pos, pos1);
    }

    public boolean canBeReplaced(BlockState state, BlockItemUseContext context) {
        int i = state.getValue(LAYERS);
        if (context.getItemInHand().getItem() == this.asItem() && i < 4) {
            if (context.replacingClickedOnBlock()) {
                return context.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Math.min(4, i + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded(SPECIFIC_MOD))
            return;
        super.fillItemCategory(group, items);
    }
}

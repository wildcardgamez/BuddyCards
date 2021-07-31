package com.wildcard.buddycards.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

public class BoosterBoxBlock extends Block {
    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 4);
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public BoosterBoxBlock(int setNumber, String modId) {
        super(Properties.copy(Blocks.STONE));
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)));
        SET_NUMBER = setNumber;
        SPECIFIC_MOD = modId;
    }

    final int SET_NUMBER;
    final String SPECIFIC_MOD;

    public VoxelShape getShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter blockReader, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS) - 1];
    }

    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter blockReader, BlockPos pos) {
        return SHAPE_BY_LAYER[state.getValue(LAYERS)];
    }

    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockReader, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor world, BlockPos pos, BlockPos pos1) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, world, pos, pos1);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(LAYERS);
        if (context.getItemInHand().getItem() == this.asItem() && i < 4) {
            if (context.replacingClickedOnBlock()) {
                return context.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Math.min(4, i + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(!ModList.get().isLoaded(SPECIFIC_MOD))
            return;
        super.fillItemCategory(group, items);
    }
}

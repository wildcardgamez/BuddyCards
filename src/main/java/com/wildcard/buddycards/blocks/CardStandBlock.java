package com.wildcard.buddycards.blocks;

import com.wildcard.buddycards.blocks.tiles.CardDisplayBlockEntity;
import com.wildcard.buddycards.blocks.tiles.CardStandBlockEntity;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.Containers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class CardStandBlock extends BaseEntityBlock {
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CardStandBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof CardStandBlockEntity) {
            tileentity.clearRemoved();
            worldIn.setBlockEntity(tileentity);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof CardStandBlockEntity) {
            CardStandBlockEntity standTile = (CardStandBlockEntity) world.getBlockEntity(pos);
            ItemStack stack = player.getItemInHand(hand);
            if(stack.getItem() == BuddycardsItems.BUDDYSTEEL_KEY.get()) {
                if (standTile.isLocked()) {
                    if (standTile.toggleLock(player.getUUID()))
                        player.displayClientMessage(new TranslatableComponent("block.buddycards.card_stand.unlock"), true);
                    else
                        player.displayClientMessage(new TranslatableComponent("block.buddycards.card_stand.fail_unlock"), true);
                }
                else {
                    standTile.toggleLock(player.getUUID());
                    player.displayClientMessage(new TranslatableComponent("block.buddycards.card_stand.lock"), true);
                }
            }
            else if (standTile.isLocked())
                player.displayClientMessage(new TranslatableComponent("block.buddycards.card_stand.lock"), true);
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
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean removedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (world.getBlockEntity(pos) instanceof CardStandBlockEntity)
            Containers.dropContents(world, pos, NonNullList.withSize(1, ((CardStandBlockEntity) (world.getBlockEntity(pos))).getCard()));
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if(NEEDED_MOD != "" && !ModList.get().isLoaded(NEEDED_MOD))
            return;
        super.fillItemCategory(group, items);
    }

    @Override
    public boolean canEntityDestroy(BlockState state, BlockGetter world, BlockPos pos, Entity entity)
    {
    	CardStandBlockEntity displayTile = (CardStandBlockEntity) world.getBlockEntity(pos);
    	if ( displayTile.isLocked() )
    	{
    		return false;
    	}
    	
    	return super.canEntityDestroy(state, world, pos, entity);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player)
    {
    	CardStandBlockEntity displayTile = (CardStandBlockEntity) world.getBlockEntity(pos);
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
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof CardStandBlockEntity && ((CardStandBlockEntity) tileentity).getCard().isEmpty()) {
            return 10;
        }
        else
            return 0;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return canSupportRigidBlock(worldIn, blockpos) || canSupportCenter(worldIn, blockpos, Direction.UP);
    }

    @Override
    public boolean isPossibleToRespawnInThis() {
        return true;
    }
}
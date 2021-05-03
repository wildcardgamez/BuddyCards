package com.wildcard.buddycards.entities;

import com.wildcard.buddycards.util.EnderlingOfferMaker;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class EnderlingEntity extends CreatureEntity implements INPC, IMerchant, INameable {
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(RegistryHandler.PACK_MYSTERY.get(), RegistryHandler.PACK_END.get(), RegistryHandler.ZYLEX.get());
    private PlayerEntity customer;
    private MerchantOffers offers;

    public EnderlingEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.stepHeight = 1.0F;
        this.setPathPriority(PathNodeType.WATER, -1.0F);
    }

    public static AttributeModifierMap.MutableAttribute setupAttributes() {
        return MobEntity.registerAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, .5D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 6.0f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1f));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, .75f, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, .5f, 0.0f));
        this.goalSelector.addGoal(4, new LookAtGoal(this, LivingEntity.class, 8.0f));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 0.6f, 1.5f, 6.0f));
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    protected void updateAITasks() {
        if (this.world.isDaytime() && this.ticksExisted >= 600) {
            float f = this.getBrightness();
            if (f > 0.5F && this.world.canSeeSky(this.getPosition()) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.teleportRandomly();
            }
        }

        super.updateAITasks();
    }

    protected void teleportRandomly() {
        if (!this.world.isRemote() && this.isAlive()) {
            double d0 = this.getPosX() + (this.rand.nextDouble() - 0.5D) * 64.0D;
            double d1 = this.getPosY() + (double)(this.rand.nextInt(64) - 32);
            double d2 = this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 64.0D;
            this.teleportTo(d0, d1, d2);
        }
    }

    private void teleportTo(double x, double y, double z) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(x, y, z);

        while(blockpos$mutable.getY() > 0 && !this.world.getBlockState(blockpos$mutable).getMaterial().blocksMovement()) {
            blockpos$mutable.move(Direction.DOWN);
        }

        BlockState blockstate = this.world.getBlockState(blockpos$mutable);
        boolean flag = blockstate.getMaterial().blocksMovement();
        boolean flag1 = blockstate.getFluidState().isTagged(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
            boolean flag2 = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !this.isSilent()) {
                this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

        }
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            for(int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, this.getPosXRandom(0.5D), this.getPosYRandom() - 0.25D, this.getPosZRandom(0.5D), (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        super.livingTick();
    }

    public boolean isWaterSensitive() {
        return true;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {
        customer = player;
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return customer;
    }

    @Override
    public MerchantOffers getOffers() {
        if(offers == null) {
            offers = new MerchantOffers();
            populateTradeDate();
        }
        return offers;
    }

    private void populateTradeDate() {
        offers.add(EnderlingOfferMaker.createCardBuyOffer());
        offers.add(EnderlingOfferMaker.createGradedCardBuyOffer());
        offers.add(EnderlingOfferMaker.createGenericOffer());
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void onTrade(MerchantOffer offer) {
        offer.increaseUses();
        if(offers.size() < 7) {
            if (offers.size() == 3)
                offers.add(EnderlingOfferMaker.createGradedCardSellOffer());
            else if (offers.size() == 4)
                offers.add(EnderlingOfferMaker.createPackOffer());
            else {
                offers.add(EnderlingOfferMaker.createGenericOffer());
            }
        }
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Offers", 10)) {
            offers = new MerchantOffers(compound.getCompound("Offers"));
        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (offers != null) {
            compound.put("Offers", offers.write());
        }
    }

    @Override
    public void verifySellingItem(ItemStack stack) {

    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public void setXP(int xpIn) {

    }

    @Override
    public boolean hasXPBar() {
        return false;
    }

    @Override
    public SoundEvent getYesSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if(heldItem.getItem() == Items.NAME_TAG) {
            heldItem.interactWithEntity(player, this, hand);
            return ActionResultType.SUCCESS;
        }
        else if(this.isAlive() && customer == null && !this.world.isRemote) {
            getOffers();
            this.setCustomer(player);
            this.openMerchantContainer(player, this.getDisplayName(), 1);
            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(player, hand);
    }

    public class TradeWithPlayerGoal extends Goal {
        private final EnderlingEntity enderling;

        public TradeWithPlayerGoal(EnderlingEntity enderling) {
            this.enderling = enderling;
            this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }
        
        public boolean shouldExecute() {
            if (!this.enderling.isAlive()) {
                return false;
            } else if (this.enderling.isInWater()) {
                return false;
            } else if (!this.enderling.isOnGround()) {
                return false;
            } else if (this.enderling.velocityChanged) {
                return false;
            } else {
                PlayerEntity playerentity = this.enderling.getCustomer();
                if (playerentity == null) {
                    return false;
                } else if (this.enderling.getDistanceSq(playerentity) > 16.0D) {
                    return false;
                } else {
                    return playerentity.openContainer != null;
                }
            }
        }
        
        public void startExecuting() {
            this.enderling.getNavigator().clearPath();
        }
        
        public void resetTask() {
            this.enderling.setCustomer((PlayerEntity)null);
        }
    }
}
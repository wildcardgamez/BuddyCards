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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class EnderlingEntity extends CreatureEntity implements INPC, IMerchant, INameable {
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(RegistryHandler.PACK_MYSTERY.get(), RegistryHandler.PACK_END.get(), RegistryHandler.ZYLEX.get());
    private PlayerEntity customer;
    private MerchantOffers offers;

    public EnderlingEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.0F;
        this.setPathfindingMalus(PathNodeType.WATER, -1.0F);
    }

    public static AttributeModifierMap.MutableAttribute setupAttributes() {
        return MobEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, .5D)
                .add(Attributes.FOLLOW_RANGE, 6.0f);
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
    protected int getExperienceReward(PlayerEntity player) {
        return 1 + this.level.random.nextInt(3);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDERMAN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENDERMAN_HURT;
    }

    protected void customServerAiStep() {
        if (this.level.isDay() && this.tickCount >= 600) {
            float f = this.getBrightness();
            if (f > 0.5F && this.level.canSeeSky(this.blockPosition()) && this.random.nextFloat() * 60.0F < (f - 0.4F) * 2.0F) {
                this.teleport();
            }
        }
        super.customServerAiStep();
    }

    protected boolean teleport() {
        if (!this.level.isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 32.0D;
            double d1 = this.getY() + (double)(this.random.nextInt(32) - 32);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 32.0D;
            return this.teleport(d0, d1, d2);
        } else {
            return false;
        }
    }

    private boolean teleport(double p_70825_1_, double p_70825_3_, double p_70825_5_) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(p_70825_1_, p_70825_3_, p_70825_5_);
        while(blockpos$mutable.getY() > 0 && !this.level.getBlockState(blockpos$mutable).getMaterial().blocksMotion()) {
            blockpos$mutable.move(Direction.DOWN);
        }
        BlockState blockstate = this.level.getBlockState(blockpos$mutable);
        if (blockstate.getMaterial().blocksMotion() && !blockstate.getFluidState().is(FluidTags.WATER)) {
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
            boolean success = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (success && !this.isSilent()) {
                this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
            return success;
        } else {
            return false;
        }
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide) {
            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
        }
        super.aiStep();
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public void setTradingPlayer(@Nullable PlayerEntity player) {
        customer = player;
    }

    @Nullable
    @Override
    public PlayerEntity getTradingPlayer() {
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
    public void overrideOffers(@Nullable MerchantOffers offers) {
        this.offers = offers;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
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

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Offers", 10)) {
            offers = new MerchantOffers(compound.getCompound("Offers"));
        }
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (offers != null) {
            compound.put("Offers", offers.createTag());
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {

    }

    @Override
    public World getLevel() {
        return level;
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int xpIn) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.ENDERMAN_AMBIENT;
    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if(heldItem.getItem() == Items.NAME_TAG) {
            heldItem.interactLivingEntity(player, this, hand);
            return ActionResultType.SUCCESS;
        }
        else if(this.isAlive() && customer == null && !this.level.isClientSide) {
            getOffers();
            this.setTradingPlayer(player);
            this.openTradingScreen(player, this.getDisplayName(), 1);
            return ActionResultType.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    public static class TradeWithPlayerGoal extends Goal {
        private final EnderlingEntity enderling;

        public TradeWithPlayerGoal(EnderlingEntity enderling) {
            this.enderling = enderling;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }
        
        public boolean canUse() {
            if (!this.enderling.isAlive()) {
                return false;
            } else if (this.enderling.isInWater()) {
                return false;
            } else if (!this.enderling.isOnGround()) {
                return false;
            } else if (this.enderling.hurtMarked) {
                return false;
            } else {
                PlayerEntity playerentity = this.enderling.getTradingPlayer();
                if (playerentity == null) {
                    return false;
                } else if (this.enderling.distanceToSqr(playerentity) > 16.0D) {
                    return false;
                } else {
                    return playerentity.containerMenu != null;
                }
            }
        }
        
        public void start() {
            this.enderling.getNavigation().stop();
        }
        
        public void stop() {
            this.enderling.setTradingPlayer(null);
        }
    }
}
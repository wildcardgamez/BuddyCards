package com.wildcard.buddycards.entities;

import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.util.EnderlingOfferMaker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.Nameable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class EnderlingEntity extends PathfinderMob implements Npc, Merchant, Nameable {
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(BuddycardsItems.MYSTERY_PACK.get(), BuddycardsItems.END_SET.PACK.get(), BuddycardsItems.ZYLEX.get());
    private Player customer;
    private MerchantOffers offers;
    private int resets;

    public EnderlingEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.maxUpStep = 1.0F;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    public static AttributeSupplier.Builder setupAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, .5D)
                .add(Attributes.FOLLOW_RANGE, 6.0f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1f));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, .75f, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, .5f, 0.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, LivingEntity.class, 8.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 0.6f, 1.5f, 6.0f));
    }

    @Override
    protected int getExperienceReward(Player player) {
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
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos(p_70825_1_, p_70825_3_, p_70825_5_);
        while(blockpos$mutable.getY() > 0 && !this.level.getBlockState(blockpos$mutable).getMaterial().blocksMotion()) {
            blockpos$mutable.move(Direction.DOWN);
        }
        BlockState blockstate = this.level.getBlockState(blockpos$mutable);
        if (blockstate.getMaterial().blocksMotion() && !blockstate.getFluidState().is(FluidTags.WATER)) {
            EntityTeleportEvent event = new EntityTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_);
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
    public void setTradingPlayer(@Nullable Player player) {
        customer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return customer;
    }

    @Override
    public MerchantOffers getOffers() {
        if(offers == null) {
            offers = new MerchantOffers();
            populateTradeDate();
        }
        if(offers.size() == 7 && resets >= 2 && resets % 2 == 0){
            boolean flag = true;
            for (int i = 0; i < 7 && flag; i++) {
                if(!offers.get(i).isOutOfStock())
                    flag = false;
            }
            if(flag)
                offers.add(EnderlingOfferMaker.createSpecialtyOffer(getRandom()));
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

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Offers", 10)) {
            offers = new MerchantOffers(compound.getCompound("Offers"));
        }
        resets = compound.getInt("Resets");
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (offers != null) {
            compound.put("Offers", offers.createTag());
        }
        compound.putInt("Resets", resets);
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {

    }

    @Override
    public Level getLevel() {
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
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if(offers != null && !this.level.isClientSide && (heldItem.getItem() == BuddycardsItems.ZYLEX_TOKEN.get() || (ModList.get().isLoaded("curios") && ((
                CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_MEDAL.get(), player).isPresent() &&
                CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.ZYLEX_MEDAL.get(), player).get().right.getItem().equals(BuddycardsItems.ZYLEX_MEDAL.get())) ||
                (CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), player).isPresent() &&
                CuriosApi.getCuriosHelper().findEquippedCurio(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), player).get().right.getItem().equals(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get()))
                        )))) {
            boolean flag = true;
            for (int i = 0; i < offers.size() && flag; i++) {
                if(!offers.get(i).isOutOfStock())
                    flag = false;
            }
            if(flag) {
                offers.clear();
                populateTradeDate();
                resets++;
                if(heldItem.getItem() == BuddycardsItems.ZYLEX_TOKEN.get())
                    heldItem.shrink(1);
            }
        }
        if(heldItem.getItem() == Items.NAME_TAG) {
            heldItem.interactLivingEntity(player, this, hand);
            return InteractionResult.SUCCESS;
        }
        else if(this.isAlive() && customer == null && !this.level.isClientSide) {
            getOffers();
            this.setTradingPlayer(player);
            this.openTradingScreen(player, this.getDisplayName(), 1);
            return InteractionResult.SUCCESS;
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
                Player playerentity = this.enderling.getTradingPlayer();
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
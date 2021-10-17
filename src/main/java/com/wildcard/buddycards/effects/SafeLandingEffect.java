package com.wildcard.buddycards.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class SafeLandingEffect extends MobEffect {
    public SafeLandingEffect() {
        super(MobEffectCategory.BENEFICIAL, 10276599);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int i) {
        System.out.println(livingEntity.isFallFlying() + "::" + livingEntity.fallDistance);
        if(livingEntity.fallDistance > livingEntity.getMaxFallDistance() * 2 && (!livingEntity.hasEffect(MobEffects.SLOW_FALLING) || livingEntity.getEffect(MobEffects.SLOW_FALLING).getDuration() < 10))
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 100));
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}

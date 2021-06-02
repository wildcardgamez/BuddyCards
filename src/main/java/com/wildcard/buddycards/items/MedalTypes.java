package com.wildcard.buddycards.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import vectorwing.farmersdelight.registry.ModEffects;

public enum MedalTypes {
    BASE_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 300, mod, true, false));
    }, Rarity.UNCOMMON),
    NETHER_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0, true, false));
        if(mod > 0 && player.isOnFire()) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1800, 0, true, false));
            if (mod > 1)
                player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 300, 0, true, false));
        }
    }, Rarity.UNCOMMON),
    END_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 300, mod / 2, true, false));
        if (player.hasEffect(Effects.LEVITATION) && mod > 0)
            player.removeEffect(Effects.LEVITATION);
    }, Rarity.UNCOMMON),
    BYG_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.JUMP, 300, mod / 2, true, false));
        if (mod > 0)
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 300, 0, true, false));
    }, Rarity.UNCOMMON),
    CREATE_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.DIG_SPEED, 300, mod, true, false));
    }, Rarity.UNCOMMON),
    AQUACULTURE_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.LUCK, 300, mod / 2, true, false));
        if (mod > 0)
            player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 300, 0, true, false));
    }, Rarity.UNCOMMON),
    FD_SET((player, mod) -> {
        player.addEffect(new EffectInstance(ModEffects.NOURISHED.get(), 300, 0, true, false));
        if (mod > 0 && player.getFoodData().getFoodLevel() > 19) {
            player.addEffect(new EffectInstance(ModEffects.COMFORT.get(), 300, 0, true, false));
            if (mod > 1)
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 300, 0, true, false));
        }
    }, Rarity.UNCOMMON),
    ZYLEX((player, mod) -> {
        if (mod > 0)
            player.addEffect(new EffectInstance(RegistryHandler.GRADING_LUCK.get(), 300, mod - 1, true, false));
    }, Rarity.RARE),
    LUMNIS((player, mod) -> {
        if (mod > 0)
            player.addEffect(new EffectInstance(RegistryHandler.GRADING_LUCK.get(), 300, mod - 1, true, false));
    }, Rarity.RARE),
    PERFECT((player, mod) -> {
        player.addEffect(new EffectInstance(RegistryHandler.GRADING_LUCK.get(), 300, mod, true, false));
    }, Rarity.EPIC);

    MedalTypes(MedalEffect effect, Rarity rarity) {
        this.effect = effect;
        this.rarity = rarity;
    }
    private final MedalEffect effect;
    private final Rarity rarity;

    public void applyEffect(PlayerEntity player, int mod) {
        effect.applyEffect(player, mod);
    }

    public Rarity getRarity() {
        return rarity;
    }
}

interface MedalEffect {
    void applyEffect(PlayerEntity player, int mod);
}

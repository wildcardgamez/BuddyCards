package com.wildcard.buddycards.items;

import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import vectorwing.farmersdelight.registry.ModEffects;

public enum MedalTypes {
    BASE_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 300, mod, true, false));
    }),
    NETHER_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0, true, false));
        if(mod > 0 && player.isOnFire()) {
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1800, 0, true, false));
            if (mod > 1)
                player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 300, 0, true, false));
        }
    }),
    END_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 300, mod / 2, true, false));
        if (player.hasEffect(Effects.LEVITATION) && mod > 0)
            player.removeEffect(Effects.LEVITATION);
    }),
    BYG_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.JUMP, 300, mod / 2, true, false));
        if (mod > 0)
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 300, 0, true, false));
    }),
    CREATE_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.DIG_SPEED, 300, mod, true, false));
    }),
    AQUACULTURE_SET((player, mod) -> {
        player.addEffect(new EffectInstance(Effects.LUCK, 300, mod / 2, true, false));
        if (mod > 0)
            player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 300, 0, true, false));
    }),
    FD_SET((player, mod) -> {
        player.addEffect(new EffectInstance(ModEffects.NOURISHED.get(), 300, 0, true, false));
        if (mod > 0 && player.getFoodData().getFoodLevel() > 19) {
            player.addEffect(new EffectInstance(ModEffects.COMFORT.get(), 300, 0, true, false));
            if (mod > 1)
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 300, 0, true, false));
        }
    }),
    ZYLEX((player, mod) -> {
        if (mod > 0)
            player.addEffect(new EffectInstance(RegistryHandler.GRADING_LUCK.get(), 300, mod - 1, true, false));
    });

    MedalTypes(MedalEffect effect) {
        this.effect = effect;
    }
    private final MedalEffect effect;

    public void applyEffect(PlayerEntity player, int mod) {
        effect.applyEffect(player, mod);
    }
}

interface MedalEffect {
    void applyEffect(PlayerEntity player, int mod);
}

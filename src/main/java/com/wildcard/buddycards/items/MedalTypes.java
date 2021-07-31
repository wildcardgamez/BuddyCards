package com.wildcard.buddycards.items;

import com.wildcard.buddycards.registries.BuddycardsMisc;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;

public enum MedalTypes {
    BASE_SET((player, mod) -> {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, mod, true, false));
    }, Rarity.UNCOMMON),
    NETHER_SET((player, mod) -> {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0, true, false));
        if(mod > 0 && player.isOnFire()) {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 0, true, false));
            if (mod > 1)
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 0, true, false));
        }
    }, Rarity.UNCOMMON),
    END_SET((player, mod) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, mod / 2, true, false));
        if (player.hasEffect(MobEffects.LEVITATION) && mod > 0)
            player.removeEffect(MobEffects.LEVITATION);
    }, Rarity.UNCOMMON),
    BYG_SET((player, mod) -> {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 300, mod / 2, true, false));
        if (mod > 0)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0, true, false));
    }, Rarity.UNCOMMON),
    CREATE_SET((player, mod) -> {
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 300, mod, true, false));
    }, Rarity.UNCOMMON),
    AQUACULTURE_SET((player, mod) -> {
        player.addEffect(new MobEffectInstance(MobEffects.LUCK, 300, mod / 2, true, false));
        if (mod > 0)
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 300, 0, true, false));
    }, Rarity.UNCOMMON),
    FD_SET((player, mod) -> {
        //player.addEffect(new MobEffectInstance(ModEffects.NOURISHED.get(), 300, 0, true, false));
        //if (mod > 0 && player.getFoodData().getFoodLevel() > 19) {
        //    player.addEffect(new MobEffectInstance(ModEffects.COMFORT.get(), 300, 0, true, false));
        //    if (mod > 1)
        //        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0, true, false));
        //}
    }, Rarity.UNCOMMON),
    ZYLEX((player, mod) -> {
        if (mod > 0)
            player.addEffect(new MobEffectInstance(BuddycardsMisc.GRADING_LUCK.get(), 300, mod - 1, true, false));
    }, Rarity.RARE),
    LUMNIS((player, mod) -> {
        if (mod > 0)
            player.addEffect(new MobEffectInstance(BuddycardsMisc.GRADING_LUCK.get(), 300, mod - 1, true, false));
    }, Rarity.RARE),
    PERFECT((player, mod) -> {
        player.addEffect(new MobEffectInstance(BuddycardsMisc.GRADING_LUCK.get(), 300, mod, true, false));
    }, Rarity.EPIC);

    MedalTypes(MedalEffect effect, Rarity rarity) {
        this.effect = effect;
        this.rarity = rarity;
    }
    private final MedalEffect effect;
    private final Rarity rarity;

    public void applyEffect(Player player, int mod) {
        effect.applyEffect(player, mod);
    }

    public Rarity getRarity() {
        return rarity;
    }
}

interface MedalEffect {
    void applyEffect(Player player, int mod);
}

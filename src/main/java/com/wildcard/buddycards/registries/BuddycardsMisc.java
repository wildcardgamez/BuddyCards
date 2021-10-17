package com.wildcard.buddycards.registries;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import com.wildcard.buddycards.container.VaultContainer;
import com.wildcard.buddycards.effects.GradingLuckEffect;
import com.wildcard.buddycards.effects.SafeLandingEffect;
import com.wildcard.buddycards.enchantment.*;
import com.wildcard.buddycards.loot.LootInjection;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BuddycardsMisc {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BuddyCards.MOD_ID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BuddyCards.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, BuddyCards.MOD_ID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLMS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, BuddyCards.MOD_ID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BuddyCards.MOD_ID);

    public static void init() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        GLMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Containers
    public static final RegistryObject<MenuType<BinderContainer>> BINDER_CONTAINER = CONTAINERS.register("binder",
            () -> new MenuType<>((BinderContainer::new)));
    public static final RegistryObject<MenuType<VaultContainer>> VAULT_CONTAINER = CONTAINERS.register("vault",
            () -> IForgeContainerType.create((id, inv, data) -> new VaultContainer(id, inv, data.readBlockPos())));

    //Enchants
    public static final RegistryObject<Enchantment> BUDDY_BINDING = ENCHANTMENTS.register("buddy_binding", EnchantmentBuddyBinding::new);
    public static final RegistryObject<Enchantment> BUDDY_BOOST = ENCHANTMENTS.register("buddy_boost", EnchantmentBuddyBoost::new);
    public static final RegistryObject<Enchantment> EXTRA_PAGE = ENCHANTMENTS.register("extra_page", EnchantmentExtraPage::new);
    public static final RegistryObject<Enchantment> KINETIC_PROT = ENCHANTMENTS.register("kinetic_protection", EnchantmentKineticProtection::new);

    public static final RegistryObject<Enchantment> BUDDY_DMG = ENCHANTMENTS.register("buddycard_blade", EnchantmentBuddycardBlade::new);
    public static final RegistryObject<Enchantment> BUDDY_PROT = ENCHANTMENTS.register("buddycard_barrier", EnchantmentBuddycardBarrier::new);
    public static final RegistryObject<Enchantment> BUDDY_EFF = ENCHANTMENTS.register("buddycard_break", EnchantmentBuddycardBreak::new);

    //Effects
    public static final RegistryObject<MobEffect> GRADING_LUCK = EFFECTS.register("grading_luck", GradingLuckEffect::new);
    public static final RegistryObject<MobEffect> SAFE_LANDING = EFFECTS.register("safe_landing", SafeLandingEffect::new);

    //Potions
    public static final RegistryObject<Potion> GRADING_LUCK_NORMAL = POTIONS.register("grading_luck", () -> new Potion(new MobEffectInstance(GRADING_LUCK.get(), 3600)));
    public static final RegistryObject<Potion> GRADING_LUCK_STRONG = POTIONS.register("grading_luck_strong", () -> new Potion(new MobEffectInstance(GRADING_LUCK.get(), 1800, 1)));
    public static final RegistryObject<Potion> GRADING_LUCK_LONG = POTIONS.register("grading_luck_long", () -> new Potion(new MobEffectInstance(GRADING_LUCK.get(), 9600)));
    public static final RegistryObject<Potion> SAFE_LANDING_NORMAL = POTIONS.register("safe_landing_normal", () -> new Potion(new MobEffectInstance(SAFE_LANDING.get(), 3600)));
    public static final RegistryObject<Potion> SAFE_LANDING_LONG = POTIONS.register("safe_landing_long", () -> new Potion(new MobEffectInstance(SAFE_LANDING.get(), 9600)));
    public static final RegistryObject<Potion> ROCKETEER_NORMAL = POTIONS.register("rocketeer", () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 20, 100), new MobEffectInstance(SAFE_LANDING.get(), 3600)));
    public static final RegistryObject<Potion> ROCKETEER_LONG = POTIONS.register("rocketeer_long", () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 20, 100), new MobEffectInstance(SAFE_LANDING.get(), 9600)));

    public static void brewingSetup() {
        PotionBrewing.addMix(Potions.AWKWARD, BuddycardsItems.ZYLEX.get(), GRADING_LUCK_NORMAL.get());
        PotionBrewing.addMix(GRADING_LUCK_NORMAL.get(), Items.GLOWSTONE_DUST, GRADING_LUCK_STRONG.get());
        PotionBrewing.addMix(GRADING_LUCK_NORMAL.get(), Items.REDSTONE, GRADING_LUCK_LONG.get());
        PotionBrewing.addMix(Potions.SLOW_FALLING, BuddycardsItems.YANEL.get(), SAFE_LANDING_NORMAL.get());
        PotionBrewing.addMix(SAFE_LANDING_NORMAL.get(), Items.REDSTONE, SAFE_LANDING_LONG.get());
        PotionBrewing.addMix(SAFE_LANDING_NORMAL.get(), Items.FIREWORK_ROCKET, ROCKETEER_NORMAL.get());
        PotionBrewing.addMix(ROCKETEER_LONG.get(), Items.REDSTONE, ROCKETEER_LONG.get());
    }

    //GLMs
    public static RegistryObject<GlobalLootModifierSerializer<LootInjection.LootInjectionModifier>> LOOT_INJECTION = GLMS.register("loot_injection", LootInjection.LootInjectionSerializer::new);

}

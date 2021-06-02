package com.wildcard.buddycards.registries;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.container.BinderContainer;
import com.wildcard.buddycards.container.VaultContainer;
import com.wildcard.buddycards.effects.GradingLuckEffect;
import com.wildcard.buddycards.enchantment.EnchantmentBuddyBinding;
import com.wildcard.buddycards.enchantment.EnchantmentBuddyBoost;
import com.wildcard.buddycards.enchantment.EnchantmentExtraPage;
import com.wildcard.buddycards.loot.LootInjection;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BuddycardsMisc {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BuddyCards.MOD_ID);
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, BuddyCards.MOD_ID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, BuddyCards.MOD_ID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLMS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, BuddyCards.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BuddyCards.MOD_ID);

    public static void init() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        GLMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Containers
    public static final RegistryObject<ContainerType<BinderContainer>> BINDER_CONTAINER = CONTAINERS.register("binder",
            () -> new ContainerType<>((BinderContainer::new)));
    public static final RegistryObject<ContainerType<VaultContainer>> VAULT_CONTAINER = CONTAINERS.register("vault",
            () -> new ContainerType<>((VaultContainer::new)));

    //Enchants
    public static final RegistryObject<Enchantment> BUDDY_BINDING = ENCHANTMENTS.register("buddy_binding", EnchantmentBuddyBinding::new);
    public static final RegistryObject<Enchantment> BUDDY_BOOST = ENCHANTMENTS.register("buddy_boost", EnchantmentBuddyBoost::new);
    public static final RegistryObject<Enchantment> EXTRA_PAGE = ENCHANTMENTS.register("extra_page", EnchantmentExtraPage::new);

    //Effects
    public static final RegistryObject<Effect> GRADING_LUCK = EFFECTS.register("grading_luck", GradingLuckEffect::new);

    //Potions
    public static final RegistryObject<Potion> GRADING_LUCK_NORMAL = POTIONS.register("grading_luck", () -> new Potion(new EffectInstance(GRADING_LUCK.get(), 3600)));
    public static final RegistryObject<Potion> GRADING_LUCK_STRONG = POTIONS.register("grading_luck_strong", () -> new Potion(new EffectInstance(GRADING_LUCK.get(), 1800, 1)));
    public static final RegistryObject<Potion> GRADING_LUCK_LONG = POTIONS.register("grading_luck_long", () -> new Potion(new EffectInstance(GRADING_LUCK.get(), 9600)));

    public static void brewingSetup() {
        PotionBrewing.addMix(Potions.AWKWARD, BuddycardsItems.ZYLEX.get(), GRADING_LUCK_NORMAL.get());
        PotionBrewing.addMix(GRADING_LUCK_NORMAL.get(), Items.GLOWSTONE_DUST, GRADING_LUCK_STRONG.get());
        PotionBrewing.addMix(GRADING_LUCK_NORMAL.get(), Items.REDSTONE, GRADING_LUCK_LONG.get());
    }

    //GLMs
    public static RegistryObject<GlobalLootModifierSerializer<LootInjection.LootInjectionModifier>> LOOT_INJECTION = GLMS.register("loot_injection", LootInjection.LootInjectionSerializer::new);

}

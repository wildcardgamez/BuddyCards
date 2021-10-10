package com.wildcard.buddycards.integration;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.renderer.MedalRenderer;
import com.wildcard.buddycards.items.MedalTypes;
import com.wildcard.buddycards.registries.BuddycardsItems;
import com.wildcard.buddycards.registries.BuddycardsMisc;
import com.wildcard.buddycards.util.ConfigManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CuriosIntegration {
    public static void Imc() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("medal")
                .icon(new ResourceLocation(BuddyCards.MOD_ID, "misc/medal")).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring")
                .build());
    }

    public static ICapabilityProvider initCapabilities(MedalTypes type, ItemStack itemStack) {
        ICurio curio = new ICurio() {
            @Override
            public boolean canRightClickEquip() {
                return true;
            }

            @Override
            public ItemStack getStack() {
                return itemStack;
            }

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof Player && ConfigManager.doMedalEffects.get()) {
                    int mod = EnchantmentHelper.getItemEnchantmentLevel(BuddycardsMisc.BUDDY_BOOST.get(), itemStack);
                    type.applyEffect((Player) livingEntity, mod);
                }
            }
        };

        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void LoadTextures(TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation(BuddyCards.MOD_ID, "misc/medal"));
    }

    public static void setupRenderers() {
        CuriosRendererRegistry.register(BuddycardsItems.BASE_SET.MEDAL.get(), () -> new MedalRenderer("medal.1"));
        CuriosRendererRegistry.register(BuddycardsItems.NETHER_SET.MEDAL.get(), () -> new MedalRenderer("medal.2"));
        CuriosRendererRegistry.register(BuddycardsItems.END_SET.MEDAL.get(), () -> new MedalRenderer("medal.3"));
        CuriosRendererRegistry.register(BuddycardsItems.BYG_SET.MEDAL.get(), () -> new MedalRenderer("medal.4"));
        CuriosRendererRegistry.register(BuddycardsItems.CREATE_SET.MEDAL.get(), () -> new MedalRenderer("medal.5"));
        CuriosRendererRegistry.register(BuddycardsItems.AQUACULTURE_SET.MEDAL.get(), () -> new MedalRenderer("medal.6"));
        CuriosRendererRegistry.register(BuddycardsItems.FD_SET.MEDAL.get(), () -> new MedalRenderer("medal.7"));
        CuriosRendererRegistry.register(BuddycardsItems.ZYLEX_MEDAL.get(), () -> new MedalRenderer("zylex_medal"));
        CuriosRendererRegistry.register(BuddycardsItems.LUMINIS_MEDAL.get(), () -> new MedalRenderer("luminis_medal"));
        CuriosRendererRegistry.register(BuddycardsItems.PERFECT_BUDDYSTEEL_MEDAL.get(), () -> new MedalRenderer("perfect_buddysteel_medal"));
    }
}

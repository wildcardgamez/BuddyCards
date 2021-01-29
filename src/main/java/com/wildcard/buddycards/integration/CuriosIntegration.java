package com.wildcard.buddycards.integration;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.MedalItem;
import com.wildcard.buddycards.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CuriosIntegration {
    public static void Imc() {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("medal")
                .icon(new ResourceLocation(BuddyCards.MOD_ID, "misc/medal")).build());
    }

    public static boolean isStackInCuriosSlot(LivingEntity entity, ItemStack stack) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(stack.getItem(), entity).isPresent();
    }

    public static ICapabilityProvider initCapabilities(int setNumber, ItemStack itemStack) {
        ICurio curio = new ICurio() {
            @Override
            public boolean canRightClickEquip() {
                return true;
            }

            @Override
            public void curioTick(String identifier, int index, LivingEntity livingEntity) {
                if (livingEntity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity)livingEntity;
                    //At certain times, refresh the enchant based on the medals set number
                    if(player.world.getGameTime() % 80L != 0L)
                        return;
                    //Get the level of Buddy Boost to use when calculating what effects to give
                    int boostVal = EnchantmentHelper.getEnchantmentLevel(RegistryHandler.BUDDY_BOOST.get(), itemStack);
                    if(setNumber == 1) {
                        player.addPotionEffect(new EffectInstance(Effects.SPEED, 300, boostVal, true, false));
                    }
                    else if(setNumber == 2) {
                        player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0, true, false));
                        if(boostVal > 0)
                            player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 300, boostVal - 1, true, false));
                    }
                    else if(setNumber == 3) {
                        player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, boostVal / 2, true, false));
                        //Cancel out any levitation
                        if (player.isPotionActive(Effects.LEVITATION) && boostVal > 0)
                            player.removePotionEffect(Effects.LEVITATION);
                    }
                    else if(setNumber == 4) {
                        player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 300, boostVal / 2, true, false));
                        if (boostVal > 0)
                            player.addPotionEffect(new EffectInstance(Effects.SPEED, 300, 0, true, false));
                    }
                    else if(setNumber == 5)
                        player.addPotionEffect(new EffectInstance(Effects.HASTE, 300, boostVal, true, false));
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
}

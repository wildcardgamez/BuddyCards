package com.wildcard.buddycards.client;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.client.models.MedalModel;
import com.wildcard.buddycards.client.models.PerfectBuddysteelArmorModel;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = BuddyCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BuddycardsLayers {

    public static final ModelLayerLocation HEAD_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "perfect_buddysteel_armor_head"), "main");
    public static final ModelLayerLocation CHEST_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "perfect_buddysteel_armor_chest"), "main");
    public static final ModelLayerLocation LEGS_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "perfect_buddysteel_armor_legs"), "main");
    public static final ModelLayerLocation FEET_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "perfect_buddysteel_armor_feet"), "main");
    public static final ModelLayerLocation MEDAL_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "medal"), "main");
    public static final ModelLayerLocation YANNEL_ELYTRA_LAYER = new ModelLayerLocation(new ResourceLocation(BuddyCards.MOD_ID, "yannel_elyta"), "main");

    public static PerfectBuddysteelArmorModel<LivingEntity> helmet;
    public static PerfectBuddysteelArmorModel<LivingEntity> chestplate;
    public static PerfectBuddysteelArmorModel<LivingEntity> leggings;
    public static PerfectBuddysteelArmorModel<LivingEntity> boots;
    public static MedalModel<LivingEntity> medal;
    public static ElytraModel<LivingEntity> yannel_elytra;

    @SubscribeEvent
    public static void initLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(HEAD_LAYER, () -> PerfectBuddysteelArmorModel.createBodyLayer(EquipmentSlot.HEAD));
        event.registerLayerDefinition(CHEST_LAYER, () -> PerfectBuddysteelArmorModel.createBodyLayer(EquipmentSlot.CHEST));
        event.registerLayerDefinition(LEGS_LAYER, () -> PerfectBuddysteelArmorModel.createBodyLayer(EquipmentSlot.LEGS));
        event.registerLayerDefinition(FEET_LAYER, () -> PerfectBuddysteelArmorModel.createBodyLayer(EquipmentSlot.FEET));
        if(ModList.get().isLoaded("curios"))
            event.registerLayerDefinition(MEDAL_LAYER, () -> MedalModel.createBodyLayer());
        event.registerLayerDefinition(YANNEL_ELYTRA_LAYER, () -> ElytraModel.createLayer());
    }

    @SubscribeEvent
    public static void initModels(EntityRenderersEvent.AddLayers event)
    {
        helmet = new PerfectBuddysteelArmorModel<>(event.getEntityModels().bakeLayer(HEAD_LAYER));
        chestplate = new PerfectBuddysteelArmorModel<>(event.getEntityModels().bakeLayer(CHEST_LAYER));
        leggings = new PerfectBuddysteelArmorModel<>(event.getEntityModels().bakeLayer(LEGS_LAYER));
        boots = new PerfectBuddysteelArmorModel<>(event.getEntityModels().bakeLayer(FEET_LAYER));
        if(ModList.get().isLoaded("curios"))
            medal = new MedalModel<>(event.getEntityModels().bakeLayer(MEDAL_LAYER));
    }

    public static PerfectBuddysteelArmorModel<LivingEntity> getArmor(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> helmet;
            case CHEST -> chestplate;
            case LEGS -> leggings;
            case FEET -> boots;
            default -> null;
        };
    }
}

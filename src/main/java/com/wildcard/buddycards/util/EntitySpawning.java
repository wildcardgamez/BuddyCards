package com.wildcard.buddycards.util;

import com.wildcard.buddycards.entities.EnderlingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntitySpawning {

    @SubscribeEvent
    public static void spawnIn(LivingSpawnEvent event) {
        if (event.getEntityLiving() instanceof EndermanEntity) {
            EnderlingEntity entity = RegistryHandler.ENDERLING.get().create(event.getEntityLiving().world);
            event.getEntityLiving().world.addEntity(entity);
        }
    }
}

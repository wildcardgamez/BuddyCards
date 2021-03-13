package com.wildcard.buddycards.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collection;

public class MobDropHandler {
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            Collection<ItemEntity> drops = event.getDrops();
            //If killed by player, certain mobs have a chance to drop certain packs, defined in the configs
            if (entity instanceof ZombifiedPiglinEntity) {
                if (entity.isChild() && Math.random() < ConfigManager.zombiePiglinChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_NETHER.get(), 1)));
                }
            }
            else if (entity instanceof ZombieVillagerEntity && entity.isChild()) {
                if (Math.random() < ConfigManager.zombieVillagerChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_BASE.get(), 1)));
                }
            }
            else if (entity instanceof ZombieEntity && entity.isChild()) {
                if (Math.random() < ConfigManager.zombieChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_BASE.get(), 1)));
                }
            }
            else if (entity instanceof VillagerEntity && entity.isChild()) {
                if (Math.random() < ConfigManager.villagerChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_BASE.get(), 1)));
                }
            }
            else if (entity instanceof PiglinEntity && entity.isChild()) {
                if (Math.random() < ConfigManager.piglinChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_NETHER.get(), 1)));
                }
            }
            else if (entity instanceof ShulkerEntity) {
                if (Math.random() < ConfigManager.shulkerChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_END.get(), 1)));
                }
            }
            else if (entity instanceof EnderDragonEntity) {
                if (Math.random() < ConfigManager.dragonChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_END.get(),
                            (int) (Math.random() * ConfigManager.dragonMaxPacks.get()) + 1)));
                }
            }
            else if (entity instanceof WitherEntity) {
                if (Math.random() < ConfigManager.witherChance.get()) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(RegistryHandler.PACK_NETHER.get(),
                            (int) (Math.random() * ConfigManager.witherMaxPacks.get()) + 1)));
                }
            }
        }
    }
}

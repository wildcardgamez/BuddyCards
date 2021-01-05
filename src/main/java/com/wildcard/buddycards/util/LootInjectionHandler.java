package com.wildcard.buddycards.util;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LootInjectionHandler {

    @SubscribeEvent
    public static void lootLoad(LootTableLoadEvent event) {
        //Inject respective loottables into existing ones to add a chance to get packs
        if (ConfigManager.lootChestPacks.get()) {
            if (event.getName().toString().startsWith("minecraft:chests/village/village_"))
                event.getTable().addPool(makePool("buddycards:inject/village_house"));
            else if (event.getName().toString().equals("minecraft:chests/simple_dungeon"))
                event.getTable().addPool(makePool("buddycards:inject/simple_dungeon"));
            else if (event.getName().toString().equals("minecraft:chests/buried_treasure"))
                event.getTable().addPool(makePool("buddycards:inject/buried_treasure"));
            else if (event.getName().toString().equals("minecraft:chests/abandoned_mineshaft"))
                event.getTable().addPool(makePool("buddycards:inject/abandoned_mineshaft"));
            else if (event.getName().toString().equals("minecraft:chests/desert_pyramid"))
                event.getTable().addPool(makePool("buddycards:inject/desert_pyramid"));
            else if (event.getName().toString().equals("minecraft:chests/shipwreck_treasure"))
                event.getTable().addPool(makePool("buddycards:inject/shipwreck_treasure"));
            else if (event.getName().toString().equals("minecraft:chests/spawn_bonus_chest"))
                event.getTable().addPool(makePool("buddycards:inject/spawn_bonus_chest"));
            else if (event.getName().toString().equals("minecraft:chests/nether_bridge"))
                event.getTable().addPool(makePool("buddycards:inject/nether_bridge"));
            else if (event.getName().toString().equals("minecraft:chests/ruined_portal"))
                event.getTable().addPool(makePool("buddycards:inject/ruined_portal"));
            else if (event.getName().toString().equals("minecraft:chests/bastion_bridge"))
                event.getTable().addPool(makePool("buddycards:inject/bastion_bridge"));
            else if (event.getName().toString().equals("minecraft:chests/bastion_other"))
                event.getTable().addPool(makePool("buddycards:inject/bastion_other"));
            else if (event.getName().toString().equals("minecraft:chests/bastion_treasure"))
                event.getTable().addPool(makePool("buddycards:inject/bastion_treasure"));
            else if (event.getName().toString().equals("minecraft:chests/end_city_treasure"))
                event.getTable().addPool(makePool("buddycards:inject/end_city_treasure"));
        }
        else if (event.getName().toString().equals("minecraft:gameplay/piglin_bartering") && ConfigManager.piglinBartering.get())
            event.getTable().addPool(makePool("buddycards:inject/piglin_bartering"));
    }

    private static LootPool makePool(String location){
        return LootPool.builder()
                .addEntry(TableLootEntry.builder(new ResourceLocation(location)).weight(1))
                .bonusRolls(0, 1)
                .name("buddycards_inject")
                .build();
    }
}

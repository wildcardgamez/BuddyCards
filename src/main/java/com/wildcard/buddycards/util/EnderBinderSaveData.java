package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.inventory.BinderInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/*
public class EnderBinderSaveData extends SavedData {
    private final static HashMap<UUID, BinderInventory> INVENTORIES = new HashMap<UUID,BinderInventory>();

    public EnderBinderSaveData() {
        super(BuddyCards.MOD_ID + "_ebdata");
    }

    public static EnderBinderSaveData get(ServerLevel world) {
        return world.getDataStorage().computeIfAbsent(EnderBinderSaveData::new, BuddyCards.MOD_ID + "_ebdata");
    }

    @Override
    public void data(CompoundTag nbt) {
        ListTag list = nbt.getList("ebdata", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag compound = list.getCompound(i);
            BinderInventory inv = new BinderInventory(54, true);
            inv.fromTag(compound.getList("inv", Constants.NBT.TAG_COMPOUND));
            INVENTORIES.put(UUID.fromString(compound.getString("uuid")), inv);
        }
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, BinderInventory> i: INVENTORIES.entrySet()) {
            CompoundTag compound = new CompoundTag();
            compound.putString("uuid", i.getKey().toString());
            compound.put("inv", i.getValue().createTag());
            list.add(compound);
        }
        nbt.put("ebdata", list);
        return nbt;
    }

    public BinderInventory getOrMakeEnderBinder(UUID uuid) {
        if(!INVENTORIES.containsKey(uuid))
            INVENTORIES.put(uuid, new BinderInventory(54, true));
        setDirty();
        return(INVENTORIES.get(uuid));
    }
}
*/
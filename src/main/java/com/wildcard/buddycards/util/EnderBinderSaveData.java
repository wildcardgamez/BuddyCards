package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.inventory.BinderInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderBinderSaveData extends WorldSavedData {
    private final static HashMap<UUID, BinderInventory> INVENTORIES = new HashMap<UUID,BinderInventory>();

    public EnderBinderSaveData() {
        super(BuddyCards.MOD_ID + "_ebdata");
    }

    public static EnderBinderSaveData get(ServerWorld world) {
        return world.getDataStorage().computeIfAbsent(EnderBinderSaveData::new, BuddyCards.MOD_ID + "_ebdata");
    }

    @Override
    public void load(CompoundNBT nbt) {
        ListNBT list = nbt.getList("ebdata", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT compound = list.getCompound(i);
            BinderInventory inv = new BinderInventory(54, true);
            inv.fromTag(compound.getList("inv", Constants.NBT.TAG_COMPOUND));
            INVENTORIES.put(UUID.fromString(compound.getString("uuid")), inv);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT list = new ListNBT();
        for (Map.Entry<UUID, BinderInventory> i: INVENTORIES.entrySet()) {
            CompoundNBT compound = new CompoundNBT();
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

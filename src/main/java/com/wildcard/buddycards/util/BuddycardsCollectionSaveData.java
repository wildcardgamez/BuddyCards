package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.inventory.BinderInventory;
import com.wildcard.buddycards.registries.BuddycardsItems;
import javafx.util.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.*;

public class BuddycardsCollectionSaveData extends WorldSavedData {
    private final static HashMap<UUID, BuddycardsCollection> INVENTORIES = new HashMap<>();

    public BuddycardsCollectionSaveData() {
        super(BuddyCards.MOD_ID + "_collectiondata");
    }

    public static BuddycardsCollectionSaveData get(ServerWorld world) {
        return world.getDataStorage().computeIfAbsent(BuddycardsCollectionSaveData::new, BuddyCards.MOD_ID + "_collectiondata");
    }

    @Override
    public void load(CompoundNBT nbt) {
        ListNBT list = nbt.getList("collectiondata", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT compound = list.getCompound(i);

        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT list = new ListNBT();
        for (Map.Entry<UUID, BuddycardsCollection> i: INVENTORIES.entrySet()) {
            CompoundNBT compound = new CompoundNBT();
            compound.putString("uuid", i.getKey().toString());
            compound.put("collection", i.getValue().collectionTag());
            list.add(compound);
        }
        nbt.put("collectiondata", list);
        return nbt;
    }

    public class BuddycardsCollection {
        ArrayList<Pair<Integer, Integer>> COLLECTION;

        public void addCard(int set, int card) {
            COLLECTION.add(new Pair<>(set, card));
            setDirty();
        }

        public float getPower() {
            int loadedCollectedCards = 0;
            for (Pair<Integer, Integer> card : COLLECTION) {
                if(BuddycardsItems.SETS.containsKey(card.getKey()) && BuddycardsItems.SETS.get(card.getKey()).CARDS.size() >= card.getValue())
                    loadedCollectedCards++;
            }
            return (float)loadedCollectedCards / BuddycardsItems.LOADED_CARDS.size();
        }

        public ListNBT collectionTag() {
            HashMap<Integer, ArrayList<Integer>> temp = new HashMap<>();
            for (Pair<Integer, Integer> i: COLLECTION) {
                if (!temp.containsKey(i.getKey()))
                    temp.put(i.getKey(), new ArrayList<>());
                temp.get(i.getKey()).add(i.getValue());
            }
            ListNBT list = new ListNBT();
            for (Map.Entry<Integer, ArrayList<Integer>> i : temp.entrySet()) {
                CompoundNBT setNbt = new CompoundNBT();
                setNbt.putInt("set", i.getKey());
                for(int j : i.getValue()) {
                    setNbt.putInt("card" + j, j);
                }
                list.add(setNbt);
            }
           return list;
        }

        public void addCardsFromTag(ListNBT nbt) {

        }
    }
}

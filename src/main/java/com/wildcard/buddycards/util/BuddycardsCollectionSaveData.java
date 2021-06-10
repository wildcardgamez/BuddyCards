package com.wildcard.buddycards.util;

import com.wildcard.buddycards.BuddyCards;
import com.wildcard.buddycards.items.CardItem;
import com.wildcard.buddycards.registries.BuddycardsItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BuddycardsCollectionSaveData extends WorldSavedData {
    private final static HashMap<UUID, BuddycardsCollection> COLLECTIONS = new HashMap<>();

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
            BuddycardsCollection collection = new BuddycardsCollection();
            collection.load(compound.getList("collection", Constants.NBT.TAG_COMPOUND));
            COLLECTIONS.put(UUID.fromString(compound.getString("uuid")), collection);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        ListNBT list = new ListNBT();
        for (Map.Entry<UUID, BuddycardsCollection> i: COLLECTIONS.entrySet()) {
            CompoundNBT compound = new CompoundNBT();
            compound.putString("uuid", i.getKey().toString());
            compound.put("collection", i.getValue().save());
            list.add(compound);
        }
        nbt.put("collectiondata", list);
        return nbt;
    }

    public void addCard(PlayerEntity player, ItemStack stack) {
        if (stack.getItem() instanceof CardItem) {
            if (!COLLECTIONS.containsKey(player.getUUID()))
                COLLECTIONS.put(player.getUUID(), new BuddycardsCollection());
            COLLECTIONS.get(player.getUUID()).addCard(((CardItem) stack.getItem()).SET_NUMBER, ((CardItem) stack.getItem()).CARD_NUMBER);
        }
    }

    public float getPower(PlayerEntity player) {
        if (!COLLECTIONS.containsKey(player.getUUID()))
            return 0;
        return COLLECTIONS.get(player.getUUID()).getPower();
    }

    public class BuddycardsCollection {
        HashMap<Integer, ArrayList<Integer>> COLLECTION = new HashMap<>();

        public boolean addCard(int set, int card) {
            if (!COLLECTION.containsKey(set))
                COLLECTION.put(set, new ArrayList<>());
            boolean hasCard = COLLECTION.get(set).contains(card);
            COLLECTION.get(set).add(card);
            return hasCard;
        }

        public float getPower() {
            int loadedCollectedCards = 0;
            for (int set : COLLECTION.keySet()) {
                for (int card : COLLECTION.get(set)) {
                    if(BuddycardsItems.SETS.get(set).isLoaded())
                        loadedCollectedCards++;
                }
            }
            return (float)loadedCollectedCards / BuddycardsItems.LOADED_CARDS.size();
        }

        public ListNBT save() {
            ListNBT list = new ListNBT();
            for (Map.Entry<Integer, ArrayList<Integer>> i : COLLECTION.entrySet()) {
                CompoundNBT setNbt = new CompoundNBT();
                setNbt.putInt("set", i.getKey());
                ListNBT subList = new ListNBT();
                for(int j : i.getValue()) {
                    subList.add(StringNBT.valueOf("" + j));
                    System.out.println("SAVING NBT COLLECTION: SET" + i + " CARD" + j);
                }
                setNbt.put("cards", subList);
                list.add(setNbt);
            }
           return list;
        }

        public void load(ListNBT list) {
            for (int i = 0; i < list.size(); i++) {
                CompoundNBT setNbt = list.getCompound(i);
                ArrayList<Integer> array = new ArrayList<>();
                ListNBT subList = setNbt.getList("cards", Constants.NBT.TAG_COMPOUND);
                for (int j = 0; j < subList.size(); j++) {
                    array.add(Integer.parseInt(subList.getString(j)));
                    System.out.println("LOADING NBT COLLECTION: SET" + i + " CARD" + j);
                }
                COLLECTION.put(setNbt.getInt("set"), array);
            }
        }
    }
}

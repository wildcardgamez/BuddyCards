package com.wildcard.buddycards.items;

import com.wildcard.buddycards.BuddyCards;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

import net.minecraft.world.item.Item.Properties;

@Mod.EventBusSubscriber(modid = BuddyCards.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModdedSpawnEggItem extends SpawnEggItem {
    protected static final ArrayList<ModdedSpawnEggItem> ITEMS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entitySupplier;

    public ModdedSpawnEggItem(final RegistryObject<? extends EntityType<?>> entity, int color1, int color2, Properties itemProperties) {
        super(null, color1, color2, itemProperties);
        this.entitySupplier = Lazy.of(entity::get);
        ITEMS.add(this);
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag nbt) {
        return this.entitySupplier.get();
    }

    public static void initSpawnEggItems() {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
        DefaultDispenseItemBehavior dispenserBehavior = new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                type.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };

        for(final SpawnEggItem spawnEggItem : ITEMS) {
            EGGS.put(spawnEggItem.getType(null), spawnEggItem);
            DispenserBlock.registerBehavior(spawnEggItem, dispenserBehavior);
        }
        ITEMS.clear();
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        initSpawnEggItems();
    }
}

package com.wildcard.buddycards.loot;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;

public class LootInjection {
    public static class LootInjectionModifier extends LootModifier {
        private ResourceLocation table;

        protected LootInjectionModifier(ILootCondition[] conditionsIn, ResourceLocation tableIn) {
            super(conditionsIn);
            System.out.println("blblbl="+table);
            table = tableIn;
        }

        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            System.out.println(table);
            List<ItemStack> loot = context.getWorld().getServer().getLootTableManager().getLootTableFromLocation(table).generate(context);
            generatedLoot.addAll(loot);
            return generatedLoot;
        }
    }

    public static class LootInjectionSerializer extends GlobalLootModifierSerializer<LootInjectionModifier> {
        public LootInjectionSerializer() {
            System.out.println("blblbl");
        }

        @Override
        public LootInjectionModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            return new LootInjectionModifier(ailootcondition, new ResourceLocation(JSONUtils.getString(object, "injection")));
        }

        @Override
        public JsonObject write(LootInjectionModifier instance) {
            return null;
        }
    }
}

package com.wildcard.buddycards.loot;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
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
            table = tableIn;
        }

        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            LootContext.Builder builder = (new LootContext.Builder(context.getWorld()).withRandom(context.getRandom()));
            LootTable loottable = context.getWorld().getServer().getLootTableManager().getLootTableFromLocation(table);
            generatedLoot.addAll(loottable.generate(builder.build(LootParameterSets.EMPTY)));
            return generatedLoot;
        }
    }

    public static class LootInjectionSerializer extends GlobalLootModifierSerializer<LootInjectionModifier> {
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

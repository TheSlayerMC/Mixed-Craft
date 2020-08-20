package net.mixed.container.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mixed.ItemHandler;

public class MixerRecipes {
	
    private static final MixerRecipes SMELTING_BASE = new MixerRecipes();
    private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static MixerRecipes instance() {
        return SMELTING_BASE;
    }

    private MixerRecipes() {
        this.addSmelting(Items.PORKCHOP, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.BEEF, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Blocks.WOOL, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.GUNPOWDER, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.CHICKEN, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.ENDER_PEARL, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.ROTTEN_FLESH, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.FEATHER, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.BONE, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.GHAST_TEAR, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.ARROW, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.STRING, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.SLIME_BALL, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.BLAZE_ROD, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.SPIDER_EYE, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.DYE, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.SKULL, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
        this.addSmelting(Items.NETHER_STAR, new ItemStack(ItemHandler.MIXED_DNA_DRIVE), 0.0F);
    }

    public void addSmelting(Block input, ItemStack stack, float experience) {
        this.addSmelting(Item.getItemFromBlock(input), stack, experience);
    }

    public void addSmelting(Item input, ItemStack stack, float experience) {
        this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
    }

    public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience) {
        if (getSmeltingResult(input) != ItemStack.EMPTY) { net.minecraftforge.fml.common.FMLLog.log.info("Ignored incubator recipe with conflicting input: {} = {}", input, stack); return; }
        this.smeltingList.put(input, stack);
        this.experienceList.put(stack, Float.valueOf(experience));
    }

    public ItemStack getSmeltingResult(ItemStack stack) {
        for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getSmeltingList() {
        return this.smeltingList;
    }

    public float getSmeltingExperience(ItemStack stack) {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;
        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return ((Float)entry.getValue()).floatValue();
            }
        }
        return 0.0F;
    }
}
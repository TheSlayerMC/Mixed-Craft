package net.slayer.api;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.mixed.BlockHandler;
import net.mixed.ItemHandler;
import net.mixed.MixedCraft;

public class StuffConstructor {
	/**
	 * Registers block.
	 * Also sets registry name, translation key, creative tab and adds it to lang generator.
	 * Registers default itemblock for it.
	 */
	public static void regAndSetupBlock(Block block, String name, String enName, @Nullable CreativeTabs tab) {
		regAndSetupBlock(block, name, enName, tab, new ItemBlock(block));
	}

	/**
	 * Registers block.
	 * Also sets registry name, translation key, creative tab and adds it to lang generator.
	 * Registers its itemblock, if provided.
	 */
	public static void regAndSetupBlock(Block block, String name, String enName, @Nullable CreativeTabs tab, @Nullable Item itemBlock) {
		name = name.toLowerCase();

		block.setRegistryName(MixedCraft.MOD_ID, name)
				.setTranslationKey(name)
				.setCreativeTab(tab);

		BlockHandler.blocks.add(block);
		BlockHandler.blockName.add(name);

		if (itemBlock != null) {
			BlockHandler.itemBlocks.add(itemBlock
					.setRegistryName(block.getRegistryName()));
		}

		//LangGeneratorFacade.addBlockEntry(block, enName);
	}

	public static void regAndSetupItem(Item item, String name, String enName, @Nullable CreativeTabs tab) {
		name = name.toLowerCase();

		item.setRegistryName(MixedCraft.MOD_ID, name)
				.setTranslationKey(name)
				.setCreativeTab(tab);

		ItemHandler.items.add(item);
		ItemHandler.itemName.add(name);

		//LangGeneratorFacade.addItemEntry(item, enName);
	}

}

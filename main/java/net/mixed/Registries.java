package net.mixed;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.mixed.block.tileentity.*;

@EventBusSubscriber(modid = MixedCraft.MOD_ID)
public class Registries {


	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : ItemHandler.items) {
			event.getRegistry().register(item);
		}
		for (Item item : BlockHandler.itemBlocks) {
			event.getRegistry().register(item);
		}
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block block : BlockHandler.blocks) {
			event.getRegistry().register(block);
		}

		registerTileEntitys();
	}

	@SubscribeEvent
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> e) {
		IForgeRegistry<Enchantment> enchant = e.getRegistry();

	}
	
	public static void registerTileEntitys() {
        GameRegistry.registerTileEntity(TileEntityExtractor.class, new ResourceLocation(MixedCraft.PREFIX + "extractor"));
        GameRegistry.registerTileEntity(TileEntityMixer.class, new ResourceLocation(MixedCraft.PREFIX + "mixer"));
        GameRegistry.registerTileEntity(TileEntityAssembler.class, new ResourceLocation(MixedCraft.PREFIX + "assembler"));
    }
}
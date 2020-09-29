package net.slayer.api;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mixed.MixedCraft;

public class ItemMod extends Item {
	public ItemMod(String name, String enName) {
		this(name, enName, MixedCraft.MTab);
	}

	public ItemMod(String name, String enName, CreativeTabs tab) {
		StuffConstructor.regAndSetupItem(this, name, enName, tab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flagIn) {
		//ItemDescription.addInformation(item, list);
	}
}
package net.mixed.item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.mixed.ItemHandler;
import net.mixed.client.gui.GUIDNAKey;
import net.mixed.misc.EnumDNAKey;
import net.slayer.api.ItemMod;

public class ItemDNAKey extends ItemMod{

	public EnumDNAKey dna;
	
	public ItemDNAKey(String name, String name1, EnumDNAKey dna) {
		super(name, name1);
		this.dna = dna;
		setMaxStackSize(8);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack heldItem = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote) 
			Minecraft.getMinecraft().displayGuiScreen(new GUIDNAKey(dna));
		return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
	}
	
	@Override
	public void addInformation(ItemStack item, World world, List<String> list, ITooltipFlag flagIn) {
		list.add("DNA of: " + dna.getDesc()[3]);
	}
}
package net.mixed.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDNAKey extends Container {

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}
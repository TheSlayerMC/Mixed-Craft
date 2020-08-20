package net.mixed.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mixed.block.tileentity.TileEntityMixer;
import net.mixed.container.recipes.MixerRecipes;
import net.mixed.container.slot.SlotMixerOutput;

public class ContainerMixer extends Container {
	private final IInventory tileMixer;
	private int cookTime;
	private int totalCookTime;
	private int MixerBurnTime;
	private int currentItemBurnTime;

	public ContainerMixer(InventoryPlayer playerInventory, IInventory MixerInventory) {
		this.tileMixer = MixerInventory;
		this.addSlotToContainer(new Slot(MixerInventory, 0, 70, 24));
		this.addSlotToContainer(new Slot(MixerInventory, 1, 70, 60));
		this.addSlotToContainer(new SlotMixerOutput(playerInventory.player, MixerInventory, 2, 120, 42));

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 31));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 173));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileMixer);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.cookTime != this.tileMixer.getField(2)) {
				icontainerlistener.sendWindowProperty(this, 2, this.tileMixer.getField(2));
			}

			if (this.MixerBurnTime != this.tileMixer.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, this.tileMixer.getField(0));
			}

			if (this.currentItemBurnTime != this.tileMixer.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, this.tileMixer.getField(1));
			}

			if (this.totalCookTime != this.tileMixer.getField(3)) {
				icontainerlistener.sendWindowProperty(this, 3, this.tileMixer.getField(3));
			}
		}
		this.cookTime = this.tileMixer.getField(2);
		this.MixerBurnTime = this.tileMixer.getField(0);
		this.currentItemBurnTime = this.tileMixer.getField(1);
		this.totalCookTime = this.tileMixer.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileMixer.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileMixer.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 1 && index != 0) {
				if (!MixerRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityMixer.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}
}
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
import net.mixed.block.tileentity.TileEntityAssembler;
import net.mixed.container.recipes.AssemblerRecipes;
import net.mixed.container.slot.SlotAssemblerOutput;

public class ContainerDNAAssembler extends Container {
	private final IInventory tileAssembler;
	private int cookTime;
	private int totalCookTime;
	private int AssemblerBurnTime;
	private int currentItemBurnTime;

	public ContainerDNAAssembler(InventoryPlayer playerInventory, IInventory AssemblerInventory) {
		this.tileAssembler = AssemblerInventory;
		this.addSlotToContainer(new Slot(AssemblerInventory, 0, 28, 42));
		//this.addSlotToContainer(new Slot(AssemblerInventory, 1, 70, 60));
		this.addSlotToContainer(new SlotAssemblerOutput(playerInventory.player, AssemblerInventory, 2, 128, 42));

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
		listener.sendAllWindowProperties(this, this.tileAssembler);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.cookTime != this.tileAssembler.getField(2)) {
				icontainerlistener.sendWindowProperty(this, 2, this.tileAssembler.getField(2));
			}

			if (this.AssemblerBurnTime != this.tileAssembler.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, this.tileAssembler.getField(0));
			}

			if (this.currentItemBurnTime != this.tileAssembler.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, this.tileAssembler.getField(1));
			}

			if (this.totalCookTime != this.tileAssembler.getField(3)) {
				icontainerlistener.sendWindowProperty(this, 3, this.tileAssembler.getField(3));
			}
		}
		this.cookTime = this.tileAssembler.getField(2);
		this.AssemblerBurnTime = this.tileAssembler.getField(0);
		this.currentItemBurnTime = this.tileAssembler.getField(1);
		this.totalCookTime = this.tileAssembler.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileAssembler.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileAssembler.isUsableByPlayer(playerIn);
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
				if (!AssemblerRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityAssembler.isItemFuel(itemstack1)) {
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
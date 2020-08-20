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
import net.mixed.block.tileentity.TileEntityExtractor;
import net.mixed.container.recipes.ExtractorRecipes;
import net.mixed.container.slot.SlotExtractorOutput;

public class ContainerExtractor extends Container {
	private final IInventory tileExtractor;
	private int cookTime;
	private int totalCookTime;
	private int ExtractorBurnTime;
	private int currentItemBurnTime;

	public ContainerExtractor(InventoryPlayer playerInventory, IInventory ExtractorInventory) {
		this.tileExtractor = ExtractorInventory;
		this.addSlotToContainer(new Slot(ExtractorInventory, 0, 70, 24));
		this.addSlotToContainer(new Slot(ExtractorInventory, 1, 70, 60));
		this.addSlotToContainer(new SlotExtractorOutput(playerInventory.player, ExtractorInventory, 2, 120, 42));

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
		listener.sendAllWindowProperties(this, this.tileExtractor);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i) {
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.cookTime != this.tileExtractor.getField(2)) {
				icontainerlistener.sendWindowProperty(this, 2, this.tileExtractor.getField(2));
			}

			if (this.ExtractorBurnTime != this.tileExtractor.getField(0)) {
				icontainerlistener.sendWindowProperty(this, 0, this.tileExtractor.getField(0));
			}

			if (this.currentItemBurnTime != this.tileExtractor.getField(1)) {
				icontainerlistener.sendWindowProperty(this, 1, this.tileExtractor.getField(1));
			}

			if (this.totalCookTime != this.tileExtractor.getField(3)) {
				icontainerlistener.sendWindowProperty(this, 3, this.tileExtractor.getField(3));
			}
		}
		this.cookTime = this.tileExtractor.getField(2);
		this.ExtractorBurnTime = this.tileExtractor.getField(0);
		this.currentItemBurnTime = this.tileExtractor.getField(1);
		this.totalCookTime = this.tileExtractor.getField(3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileExtractor.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileExtractor.isUsableByPlayer(playerIn);
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
				if (!ExtractorRecipes.instance().getSmeltingResult(itemstack1).isEmpty()) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityExtractor.isItemFuel(itemstack1)) {
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
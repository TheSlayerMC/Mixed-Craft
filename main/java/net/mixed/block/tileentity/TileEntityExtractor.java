package net.mixed.block.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.mixed.ItemHandler;
import net.mixed.MixedCraft;
import net.mixed.container.ContainerExtractor;
import net.mixed.container.recipes.ExtractorRecipes;

public class TileEntityExtractor extends TileEntityLockable implements ITickable, ISidedInventory {

	private static final int[] SLOTS_TOP = new int[]{0};
	private static final int[] SLOTS_BOTTOM = new int[]{2, 1};
	private static final int[] SLOTS_SIDES = new int[]{1};
	private final IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	private final IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	private final IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

	private NonNullList<ItemStack> extractorItemStacks = NonNullList.withSize(3, ItemStack.EMPTY);

	private int extractorBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String extractorCustomName;

	@Override
	public int getSizeInventory() {
		return this.extractorItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.extractorItemStacks) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.extractorItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.extractorItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.extractorItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.extractorItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.extractorItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.extractorCustomName : "container.extractor";
	}

	@Override
	public boolean hasCustomName() {
		return this.extractorCustomName != null && !this.extractorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		this.extractorCustomName = p_145951_1_;
	}

	public static void registerFixesextractor(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityExtractor.class, "Items"));
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.extractorItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.extractorItemStacks);
		this.extractorBurnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
		this.currentItemBurnTime = getItemBurnTime(this.extractorItemStacks.get(1));

		if (compound.hasKey("CustomName", 8)) {
			this.extractorCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short)this.extractorBurnTime);
		compound.setInteger("CookTime", (short)this.cookTime);
		compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
		ItemStackHelper.saveAllItems(compound, this.extractorItemStacks);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.extractorCustomName);
		}

		return compound;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isBurning() {
		return this.extractorBurnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	@Override
	public void update() {
		boolean flag = this.isBurning();
		boolean flag1 = false;

		if (this.isBurning()) {
			--this.extractorBurnTime;
		}

		if (!this.world.isRemote) {
			ItemStack itemstack = this.extractorItemStacks.get(1);

			if (this.isBurning() || !itemstack.isEmpty() && !this.extractorItemStacks.get(0).isEmpty()) {
				if (!this.isBurning() && this.canSmelt()) {
					this.extractorBurnTime = getItemBurnTime(itemstack);
					this.currentItemBurnTime = this.extractorBurnTime;

					if (this.isBurning()) {
						flag1 = true;

						if (!itemstack.isEmpty()) {
							Item item = itemstack.getItem();
							itemstack.shrink(1);

							if (itemstack.isEmpty()) {
								ItemStack item1 = item.getContainerItem(itemstack);
								this.extractorItemStacks.set(1, item1);
								
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt()) {
					++this.cookTime;

					if (this.cookTime == this.totalCookTime) {
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime(this.extractorItemStacks.get(0));
						this.smeltItem();
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			}
			else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}

			if (flag != this.isBurning()) {
				flag1 = true;
			}
		}
		if (flag1) {
			this.markDirty();
		}
	}

	public int getCookTime(ItemStack stack) {
		return 500;
	}

	private boolean canSmelt() {
		if (this.extractorItemStacks.get(0).isEmpty()) {
			return false;
		} else {
			ItemStack itemstack = ExtractorRecipes.instance().getSmeltingResult(this.extractorItemStacks.get(0));

			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.extractorItemStacks.get(2);

				if (itemstack1.isEmpty()) {
					return true;
				}
				else if (!itemstack1.isItemEqual(itemstack)) {
					return false;
				}
				else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
					return true;
				} else {
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); 
				}
			}
		}
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack itemstack = this.extractorItemStacks.get(0);
			ItemStack itemstack1 = ExtractorRecipes.instance().getSmeltingResult(itemstack);
			ItemStack itemstack2 = this.extractorItemStacks.get(2);

			if (itemstack2.isEmpty()) {
				this.extractorItemStacks.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}
			itemstack.shrink(1);
		}
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			int burnTime = ForgeEventFactory.getItemBurnTime(stack);
			if (burnTime >= 0) return burnTime;
			Item item = stack.getItem();
			if (item == ItemHandler.FLASH_DRIVE) {
				return 500;
			}
			return 0;
		}
	}

	public static boolean isItemFuel(ItemStack stack) {
		return stack.getItem() == ItemHandler.FLASH_DRIVE;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) { }

	@Override
	public void closeInventory(EntityPlayer player) { }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 2) {
			return false;
		}
		else if (index != 1) {
			return true;
		} else {
			ItemStack itemstack = this.extractorItemStacks.get(1);
			return isItemFuel(stack);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return SLOTS_BOTTOM;
		} else {
			return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}

	@Override
	public String getGuiID() {
		return MixedCraft.PREFIX + "extractor";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerExtractor(playerInventory, this);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.extractorBurnTime;
		case 1:
			return this.currentItemBurnTime;
		case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.extractorBurnTime = value;
			break;
		case 1:
			this.currentItemBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		this.extractorItemStacks.clear();
	}

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else if (facing == EnumFacing.UP)
				return (T) handlerTop;
			else
				return (T) handlerSide;
		return super.getCapability(capability, facing);
	}
}
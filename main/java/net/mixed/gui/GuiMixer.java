package net.mixed.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mixed.MixedCraft;
import net.mixed.block.tileentity.TileEntityMixer;
import net.mixed.container.ContainerMixer;

@SideOnly(Side.CLIENT)
public class GuiMixer extends GuiContainer {

	private static final ResourceLocation Mixer_GUI_TEXTURES = new ResourceLocation(MixedCraft.PREFIX + "textures/gui/mixer.png");
	private final InventoryPlayer playerInventory;
	private final IInventory tileMixer;

	public GuiMixer(InventoryPlayer playerInv, IInventory incubatorInv) {
		super(new ContainerMixer(playerInv, incubatorInv));
		this.playerInventory = playerInv;
		this.tileMixer = incubatorInv;
		this.xSize = 176;
		this.ySize = 197;
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.tileMixer.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 12, 0xFFFFFF);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(Mixer_GUI_TEXTURES);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		if (TileEntityMixer.isBurning(this.tileMixer)) {
			int k = this.getBurnLeftScaled(12);
			this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 2);
		}

		int l = this.getCookProgressScaled(80);
		this.drawTexturedModalRect(i + 38, j + 42, 176, 14, l + 0, 80);
	}

	private int getCookProgressScaled(int pixels) {
		int i = this.tileMixer.getField(2);
		int j = this.tileMixer.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getBurnLeftScaled(int pixels) {
		int i = this.tileMixer.getField(1);

		if (i == 0) {
			i = 200;
		}

		return this.tileMixer.getField(0) * pixels / i;
	}
}
package net.mixed.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mixed.EnumDNAKey;
import net.mixed.MixedCraft;
import net.mixed.container.ContainerDNAKey;

public class GUIDNAKey extends GuiContainer {

	private EnumDNAKey dna;
	
	public GUIDNAKey(EnumDNAKey key) {
		super(new ContainerDNAKey());
		dna = key;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(MixedCraft.MOD_ID, "textures/gui/dna_key.png"));
		this.xSize = 238;
		this.ySize = 247;
		int x1 = (this.width - this.xSize) / 2;
		int y1 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x1, y1, 0, 0, this.xSize, this.ySize);
		ScaledResolution scaledresolution = new ScaledResolution(mc);
		
		scaleFont(dna.getDesc()[0], x1 + 85, y1 + 27, 0xFFFFFF, 1.0F);
				
		scaleFont(dna.getDesc()[1], x1 + 87, y1 + 99, 0xFF0000, 1.0F);
		
		scaleFont(dna.getDesc()[2], x1 + 87, y1 + 116, 0xD6C900, 1.0F);
		
		scaleFont(dna.getDesc()[3], x1 + 87, y1 + 131, 0x00D61A, 1.0F);
		
		scaleFont(dna.getDesc()[4], x1 + 22, y1 + 190, 0xFFFFFF, 1.0F);
		scaleFont(dna.getDesc()[5], x1 + 22, y1 + 200, 0xFFFFFF, 1.0F);
		scaleFont(dna.getDesc()[6], x1 + 22, y1 + 210, 0xFFFFFF, 1.0F);
		scaleFont(dna.getDesc()[7], x1 + 22, y1 + 220, 0xFFFFFF, 1.0F);
	}
	
	@SideOnly(Side.CLIENT)
    public void scaleFont(String s, int x, int y, int color, double scale) {
        GL11.glPushMatrix();
        GL11.glScaled(scale, scale, scale);
        this.fontRenderer.drawString(s, x, y, color);
        GL11.glTranslatef(x, y, 0);
        double fixScale = 1 / scale;
        GL11.glScaled(fixScale, fixScale, fixScale);
        GL11.glPopMatrix();
    }
}
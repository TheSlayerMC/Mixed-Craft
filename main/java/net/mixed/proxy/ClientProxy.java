package net.mixed.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.mixed.MixedCraft;
import net.mixed.client.GuiHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public EntityPlayer getPlayer() {
		return FMLClientHandler.instance().getClientPlayerEntity();
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MixedCraft.instance, new GuiHandler());

		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

	}
}
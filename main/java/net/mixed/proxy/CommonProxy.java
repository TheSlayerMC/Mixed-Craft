package net.mixed.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.mixed.BlockHandler;
import net.mixed.ItemHandler;

public class CommonProxy {

    public EntityPlayer getPlayer() {
        return null;
    }

    public void preInit(FMLPreInitializationEvent event) {
        ItemHandler.init();
        BlockHandler.init();
    }

    public void init(FMLInitializationEvent event) {
       
    }

    public void postInit(FMLPostInitializationEvent event) { }
    

    public void onLoadComplete(FMLLoadCompleteEvent event) { 
    	ItemHandler.items.clear();
    	ItemHandler.itemName.clear();
    	
    	BlockHandler.blocks.clear();
    	BlockHandler.blockName.clear();
    }

    public void serverStarting(FMLServerStartingEvent event) { }
}
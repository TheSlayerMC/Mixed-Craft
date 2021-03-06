package net.mixed.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.mixed.block.tileentity.*;
import net.mixed.client.gui.*;
import net.mixed.container.*;
import net.mixed.misc.DNAKeyDesc;
import net.mixed.misc.EnumDNAKey;

public class GuiHandler implements IGuiHandler {

    public static int id = 0;
    public static int 
    
    FAILED = id++,
    CREEPER = id++,
	PIG = id++,
	SHEEP = id++,
	COW = id++,
	CHICKEN = id++,
	ENDERMAN = id++,
	ZOMBIE = id++,
	SKELETON = id++,
	GHAST = id++,
	SPIDER = id++,
	SLIME = id++,
	SQUID = id++,
	BLAZE = id++,
	WITHER_SKELETON = id++,
	WITHER = id++,
	
	
	EXTRACTOR = id++,
	MIXER = id++,
	ASSEMBLER = id++
	;
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
        
        if (ID == EXTRACTOR)
            return new ContainerExtractor(player.inventory, (TileEntityExtractor)entity);
        if (ID == MIXER)
            return new ContainerMixer(player.inventory, (TileEntityMixer)entity);
        if (ID == ASSEMBLER)
            return new ContainerDNAAssembler(player.inventory, (TileEntityAssembler)entity);
        
        if (ID == FAILED || ID == CREEPER || ID == PIG || ID == SHEEP || ID == COW || ID == CHICKEN || ID == ENDERMAN || ID == ZOMBIE || ID == SKELETON || ID == GHAST || ID == SPIDER 
        		|| ID == SLIME || ID == SQUID || ID == BLAZE || ID == WITHER_SKELETON || ID == WITHER)
            return new ContainerDNAKey();
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == EXTRACTOR) 
        	return new GuiExtractor(player.inventory, (TileEntityExtractor)entity);
        if (ID == MIXER) 
        	return new GuiMixer(player.inventory, (TileEntityMixer)entity);
        if (ID == ASSEMBLER) 
        	return new GuiAssembler(player.inventory, (TileEntityAssembler)entity);
        
        if (ID == FAILED)
            return new GUIDNAKey(EnumDNAKey.FAILED);
        if (ID == CREEPER)
            return new GUIDNAKey(EnumDNAKey.CREEPER);
        if (ID == PIG)
            return new GUIDNAKey(EnumDNAKey.PIG);
        if (ID == SHEEP)
            return new GUIDNAKey(EnumDNAKey.SHEEP);
        if (ID == COW)
            return new GUIDNAKey(EnumDNAKey.COW);
        if (ID == CHICKEN)
            return new GUIDNAKey(EnumDNAKey.CHICKEN);
        if (ID == ENDERMAN)
            return new GUIDNAKey(EnumDNAKey.ENDERMAN);
        if (ID == ZOMBIE)
            return new GUIDNAKey(EnumDNAKey.ZOMBIE);
        if (ID == SKELETON)
            return new GUIDNAKey(EnumDNAKey.SKELETON);
        if (ID == GHAST)
            return new GUIDNAKey(EnumDNAKey.GHAST);
        if (ID == SPIDER)
            return new GUIDNAKey(EnumDNAKey.SPIDER);
        if (ID == SLIME)
            return new GUIDNAKey(EnumDNAKey.SLIME);
        if (ID == SQUID)
            return new GUIDNAKey(EnumDNAKey.SQUID);
        if (ID == BLAZE)
            return new GUIDNAKey(EnumDNAKey.BLAZE);
        if (ID == WITHER_SKELETON)
            return new GUIDNAKey(EnumDNAKey.WITHER_SKELETON);
        if (ID == WITHER)
            return new GUIDNAKey(EnumDNAKey.WITHER);
        return null;
    }
}
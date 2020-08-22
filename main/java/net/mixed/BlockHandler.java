package net.mixed;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.mixed.block.*;
import net.slayer.api.BlockMod;

public class BlockHandler {

	public static ArrayList<Block> blocks = new ArrayList<>();
	public static ArrayList<String> blockName = new ArrayList<>();
	public static ArrayList<Item> itemBlocks = new ArrayList<>();

	public static BlockDNAExtractor DNA_EXTRACTOR;
	public static BlockDNAMixer DNA_MIXER;
	public static BlockDNAAssembler ASSEMBLER;

	public static BlockMod COPPER_ORE;
	public static BlockMod COPPER_BLOCK;

	public static BlockMod TIN_ORE;
	public static BlockMod TIN_BLOCK;
	
	public static BlockMod SILVER_ORE;
	public static BlockMod SILVER_BLOCK;
	
	public static void init() {
		DNA_EXTRACTOR = new BlockDNAExtractor("dna_extractor", "DNA Extractor");
		DNA_MIXER = new BlockDNAMixer("dna_mixer", "DNA Mixer");
		ASSEMBLER = new BlockDNAAssembler("assembler", "Mob Assembler");

		COPPER_ORE = new BlockMod("copper_ore", "Copper Ore");
		COPPER_BLOCK = new BlockMod("copper_block", "Copper Block");
		
		TIN_ORE = new BlockMod("tin_ore", "Tin Ore");
		TIN_BLOCK = new BlockMod("tin_block", "Tin Block");
		
		SILVER_ORE = new BlockMod("silver_ore", "Silver Ore");
		SILVER_BLOCK = new BlockMod("silver_block", "Silver Block");
	}
}

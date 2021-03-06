package net.mixed;

import java.util.ArrayList;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.mixed.item.ItemDNAKey;
import net.mixed.misc.EnumDNAKey;
import net.slayer.api.ItemMod;

public class ItemHandler {
    public static ArrayList<Item> items = new ArrayList<>();
    public static ArrayList<String> itemName = new ArrayList<>();

    public static final EntityEquipmentSlot HEAD = EntityEquipmentSlot.HEAD, BODY = EntityEquipmentSlot.CHEST, LEGS = EntityEquipmentSlot.LEGS, BOOTS = EntityEquipmentSlot.FEET;
	
    public static Item FLASH_DRIVE;
    public static Item FAILED_FLASH_DRIVE;
    public static Item MIXED_DNA_DRIVE;

	public static Item COW_DNA_DRIVE;
	public static Item PIG_DNA_DRIVE;
	public static Item SHEEP_DNA_DRIVE;
	public static Item CHICKEN_DNA_DRIVE;
	public static Item CREEPER_DNA_DRIVE;
	public static Item ENDERMAN_DNA_DRIVE;
	public static Item ZOMBIE_DNA_DRIVE;
	public static Item SKELETON_DNA_DRIVE;
	public static Item GHAST_DNA_DRIVE;
	public static Item SPIDER_DNA_DRIVE;
	public static Item SLIME_DNA_DRIVE;
	public static Item SQUID_DNA_DRIVE;
	public static Item BLAZE_DNA_DRIVE;
	public static Item WITHER_SKELETON_DNA_DRIVE;
	public static Item WITHER_DNA_DRIVE;
    
    public static void init() {
    	FLASH_DRIVE = new ItemMod("flash_drive", "Flash Drive");
    	FAILED_FLASH_DRIVE = new ItemDNAKey("failed_flash_drive", "Corrupt Drive", EnumDNAKey.FAILED);
    	MIXED_DNA_DRIVE = new ItemMod("mixed_dna_drive", "Mixed DNA Drive");

    	COW_DNA_DRIVE = new ItemDNAKey("cow_dna_drive", "DNA Drive", EnumDNAKey.COW);
    	PIG_DNA_DRIVE = new ItemDNAKey("pig_dna_drive", "DNA Drive", EnumDNAKey.PIG);
    	SHEEP_DNA_DRIVE = new ItemDNAKey("sheep_dna_drive", "DNA Drive", EnumDNAKey.SHEEP);
    	CHICKEN_DNA_DRIVE = new ItemDNAKey("chicken_dna_drive", "DNA Drive", EnumDNAKey.CHICKEN); 
    	CREEPER_DNA_DRIVE = new ItemDNAKey("creeper_dna_drive", "DNA Drive", EnumDNAKey.CREEPER);
    	ENDERMAN_DNA_DRIVE = new ItemDNAKey("enderman_dna_drive", "DNA Drive", EnumDNAKey.ENDERMAN);
    	ZOMBIE_DNA_DRIVE = new ItemDNAKey("zombie_dna_drive", "DNA Drive", EnumDNAKey.ZOMBIE);
    	SKELETON_DNA_DRIVE = new ItemDNAKey("skeleton_dna_drive", "DNA Drive", EnumDNAKey.SKELETON);
    	GHAST_DNA_DRIVE = new ItemDNAKey("ghast_dna_drive", "DNA Drive", EnumDNAKey.GHAST);
    	SPIDER_DNA_DRIVE = new ItemDNAKey("spider_dna_drive", "DNA Drive", EnumDNAKey.SPIDER);
    	SLIME_DNA_DRIVE = new ItemDNAKey("slime_dna_drive", "DNA Drive", EnumDNAKey.SLIME);
    	SQUID_DNA_DRIVE = new ItemDNAKey("squid_dna_drive", "DNA Drive", EnumDNAKey.SQUID);
    	BLAZE_DNA_DRIVE = new ItemDNAKey("blaze_dna_drive", "DNA Drive", EnumDNAKey.BLAZE);
    	WITHER_SKELETON_DNA_DRIVE = new ItemDNAKey("wither_skeleton_dna_drive", "DNA Drive", EnumDNAKey.WITHER_SKELETON);
    	WITHER_DNA_DRIVE = new ItemDNAKey("wither_dna_drive", "DNA Drive", EnumDNAKey.WITHER);
    }
}

package net.mixed;

public enum EnumDNAKey {

	FAILED(DNAKeyDesc.FAILED),
	CREEPER(DNAKeyDesc.CREEPER),
	PIG(DNAKeyDesc.PIG),
	COW(DNAKeyDesc.COW),
	SHEEP(DNAKeyDesc.SHEEP),
	CHICKEN(DNAKeyDesc.CHICKEN),
	ENDERMAN(DNAKeyDesc.ENDERMAN),
	ZOMBIE(DNAKeyDesc.ZOMBIE),
	SKELETON(DNAKeyDesc.SKELETON),
	GHAST(DNAKeyDesc.GHAST),
	SPIDER(DNAKeyDesc.SPIDER),
	SLIME(DNAKeyDesc.SLIME),
	SQUID(DNAKeyDesc.SQUID),
	BLAZE(DNAKeyDesc.BLAZE),
	WITHER_SKELETON(DNAKeyDesc.WITHER_SKELETON),
	WITHER(DNAKeyDesc.WITHER);
	
	
	private String[] desc;
	
	private EnumDNAKey(String[] desc) {
		this.desc = desc;
	}
	
	public String[] getDesc() {
		return desc;
	}
}
package net.slayer.api;

import net.minecraft.item.Item;

public enum EnumToolType {

    WOOD_SHOVEL("shovel", Item.ToolMaterial.WOOD.getHarvestLevel()),
    GOLD_SHOVEL("shovel", Item.ToolMaterial.GOLD.getHarvestLevel()),
    STONE_SHOVEL("shovel", Item.ToolMaterial.STONE.getHarvestLevel()),
    IRON_SHOVEL("shovel", Item.ToolMaterial.IRON.getHarvestLevel()),
    DIAMOND_SHOVEL("shovel", Item.ToolMaterial.DIAMOND.getHarvestLevel());

    private String toolType;
    private int harvestLevel;

    EnumToolType(String toolType, int harvestLevel) {
        this.toolType = toolType;
        this.harvestLevel = harvestLevel;
    }

    public String getType() {
        return toolType;
    }

    public int getLevel() {
        return harvestLevel;
    }
}

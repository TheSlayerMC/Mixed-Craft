package net.slayer.api;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mixed.MixedCraft;

public class BlockMod extends Block {
    protected Boolean isFireSource;

    public BlockMod(String name, String enName, float hardness) {
        this(EnumMaterialTypes.STONE, name, enName, hardness, MixedCraft.MTab);
    }

    public BlockMod(String name, String enName) {
        this(EnumMaterialTypes.STONE, name, enName, MixedCraft.MTab);
    }

    public BlockMod(EnumMaterialTypes type, String name, String enName, float hardness) {
        this(type, name, enName, hardness, MixedCraft.MTab);
    }

    public BlockMod(String name, String enName, boolean breakable, CreativeTabs tab) {
        this(EnumMaterialTypes.STONE, name, enName, tab);
    }

    public BlockMod(String name, String enName, boolean breakable) {
        this(name, enName, breakable, MixedCraft.MTab);
    }

    public BlockMod(EnumMaterialTypes blockType, String name, String enName, CreativeTabs tab) {
        this(blockType, name, enName, 2.0F, tab);
    }

    public BlockMod(EnumMaterialTypes blockType, String name, String enName, float hardness, CreativeTabs tab) {
        super(blockType.getMaterial());
        setSoundType(blockType.getSound());

        setHardness(hardness);
        StuffConstructor.regAndSetupBlock(this, name, enName, tab);
    }

    public BlockMod setFireSource(boolean isFireSource) {
        this.isFireSource = isFireSource;
        return this;
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return isFireSource != null ? isFireSource : super.isFireSource(world, pos, side);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public BlockMod setHarvestLevel(EnumToolType type) {
        setHarvestLevel(type.getType(), type.getLevel());
        return this;
    }
}
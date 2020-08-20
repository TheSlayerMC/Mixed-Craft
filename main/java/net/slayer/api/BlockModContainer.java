package net.slayer.api;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.mixed.BlockHandler;
import net.mixed.MixedCraft;

public abstract class BlockModContainer extends BlockContainer {

    public int boostBrightnessLow;
    public int boostBrightnessHigh;
    public boolean enhanceBrightness;
    public String name;
    protected EnumMaterialTypes blockType;
    protected Item drop = null;
    protected Random rand;

    public BlockModContainer(String name, String finalName, float hardness) {
        this(EnumMaterialTypes.STONE, name, finalName, hardness, MixedCraft.MTab);
    }

    public BlockModContainer(String name, String finalName) {
        this(EnumMaterialTypes.STONE, name, finalName, 2.0F, MixedCraft.MTab);
    }

    public BlockModContainer(EnumMaterialTypes type, String name, String finalName, float hardness) {
        this(type, name, finalName, hardness, MixedCraft.MTab);
    }

    public BlockModContainer(String name, String finalName, boolean breakable, CreativeTabs tab) {
        this(EnumMaterialTypes.STONE, name, finalName, tab);
    }

    public BlockModContainer(String name, String finalName, boolean breakable) {
        this(name, finalName, breakable, MixedCraft.MTab);
    }

    public BlockModContainer(EnumMaterialTypes blockType, String name, String finalName, CreativeTabs tab) {
        super(blockType.getMaterial());
        this.blockType = blockType;
        setHardness(2.0F);
        rand = new Random();
        setSoundType(blockType.getSound());
        setCreativeTab(tab);
        setTranslationKey(name);
        this.name = name;
        BlockHandler.blocks.add(this);
	    setRegistryName(MixedCraft.MOD_ID, name);
	    //LangGeneratorFacade.addBlockEntry(this, finalName);
	    BlockHandler.itemBlocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public BlockModContainer(EnumMaterialTypes blockType, String name, String finalName, float hardness, CreativeTabs tab) {
        super(blockType.getMaterial());
        this.blockType = blockType;
        rand = new Random();
        setSoundType(blockType.getSound());
        setCreativeTab(tab);
        setTranslationKey(name);
        setHardness(hardness);
        this.name = name;
        BlockHandler.blocks.add(this);
	    setRegistryName(MixedCraft.MOD_ID, name);
	    //LangGeneratorFacade.addBlockEntry(this, finalName);
	    BlockHandler.itemBlocks.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }

    public String getName() {
        return name;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (drop == null) return Item.getItemFromBlock(this);
        return drop;
    }

    public BlockModContainer setHarvestLevel(EnumToolType type) {
        setHarvestLevel(type.getType(), type.getLevel());
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    }
}
package com.example.examplemod;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ExampleBlock extends Block implements ITileEntityProvider {
	public ExampleBlock() {
		super(Material.ROCK);
		setUnlocalizedName("exampleblock");
		setRegistryName("exampleblock");
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		return new ExampleTileEntity();
	}
}

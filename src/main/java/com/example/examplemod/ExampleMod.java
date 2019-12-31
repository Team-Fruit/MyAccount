package com.example.examplemod;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod {
	public static final String MODID = "examplemod";
	public static final String NAME = "Example Mod";
	public static final String VERSION = "1.0";

	public static Logger logger;

	public static final Block EXAMPLE_BLOCK = new ExampleBlock();

	@Mod.EventHandler
	public void construct(final FMLConstructionEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(EXAMPLE_BLOCK).setRegistryName("exampleblock"));
	}

	@SubscribeEvent
	public void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().register(EXAMPLE_BLOCK);
	}

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		logger = event.getModLog();

		GameRegistry.registerTileEntity(ExampleTileEntity.class, new ResourceLocation(ExampleMod.MODID, "exampletileentity"));
		ClientRegistry.bindTileEntitySpecialRenderer(ExampleTileEntity.class, new ExampleTESR());// TSUR
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
	}
}

package net.slayer.api.render;

import java.util.function.Supplier;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;

/**
 * If block that implements this interface has a TileEntityItemStackRenderer, TileEntityItemStackRenderer will be automatically registered.
 */
public interface IHasTeisr {

	Supplier<TileEntityItemStackRenderer> createTeisr();
}

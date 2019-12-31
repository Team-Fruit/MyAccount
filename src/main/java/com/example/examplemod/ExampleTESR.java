package com.example.examplemod;

import java.awt.Color;
import java.util.Random;

import javax.vecmath.AxisAngle4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import com.rwtema.extrautils2.utils.MCTimer;
import com.rwtema.extrautils2.utils.helpers.ColorHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExampleTESR extends TileEntitySpecialRenderer<ExampleTileEntity> {
	@Override
	public void render(final ExampleTileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder vertexBuffer = tessellator.getBuffer();
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();

		if (Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(7425);
		else
			GlStateManager.shadeModel(7424);

		preRender(destroyStage);
		vertexBuffer.begin(getDrawMode(te), getVertexFormat(te));
		renderTileEntityFast(te, x, y, z, partialTicks, destroyStage, alpha, vertexBuffer);
		vertexBuffer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		postRender(destroyStage);
		RenderHelper.enableStandardItemLighting();
	}

	protected VertexFormat getVertexFormat(final ExampleTileEntity te) {
		return DefaultVertexFormats.POSITION_COLOR;
	}

	protected int getDrawMode(final ExampleTileEntity te) {
		return 7;
	}

	@Override
	public void renderTileEntityFast(final ExampleTileEntity te, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float partial, final BufferBuilder renderer) {
		final BlockPos pos = te.getPos();
		renderer.setTranslation(x-pos.getX(), y-pos.getY(), z-pos.getZ());
		render(te.getWorld(), pos, x, y, z, partialTicks, destroyStage, renderer);
	}

	@SideOnly(Side.CLIENT)
	public void render(final IBlockAccess world, final BlockPos pos, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final BufferBuilder renderer) {
		final Vector3f[] vecs = new Vector3f[] { new Vector3f(1.0F, 0.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 0.0F, 1.0F) };

		final Random rand = new Random(425L+pos.hashCode());

		final Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		final Vector4f b = new Vector4f();
		final Vector4f c = new Vector4f();
		final Vector4f d = new Vector4f();

		for (int i = 0; i<32; ++i) {
			final Vector3f[] var20 = vecs;
			int rgb = vecs.length;
			for (int var22 = 0; var22<rgb; ++var22) {
				final Vector3f vec = var20[var22];
				matrix.mul(CreateFromAxisAngle(vec, 6.2831855F*rand.nextFloat()+MCTimer.renderTimer/360.0F));
			}

			final float r = (1.0F+rand.nextFloat()*2.5F)*5.0F;

			matrix.mul(CreateFromAxisAngle(new Vector3f(0.0F, 1.0F, 0.0F), MCTimer.renderTimer/180.0F));

			b.set(0.0F, 0.126F*r, 0.5F*r, 1.0F);
			c.set(0.0F, -0.126F*r, 0.5F*r, 1.0F);
			d.set(0.0F, 0.0F, 0.6F*r, 1.0F);

			matrix.transform(b);
			matrix.transform(c);
			matrix.transform(d);

			rgb = Color.HSBtoRGB(i/16.0F, 1.0F, 1.0F);
			final float col_r = ColorHelper.getRF(rgb);
			final float col_g = ColorHelper.getGF(rgb);
			final float col_b = ColorHelper.getBF(rgb);

			renderer.pos(pos.getX()+0.5F, pos.getY()+0.5F, pos.getZ()+0.5F).color(col_r, col_g, col_b, 0.9F).endVertex();
			renderer.pos(pos.getX()+0.5F+b.x, pos.getY()+0.5F+b.y, pos.getZ()+0.5F+b.z).color(col_r, col_g, col_b, 0.01F).endVertex();
			renderer.pos(pos.getX()+0.5F+d.x, pos.getY()+0.5F+d.y, pos.getZ()+0.5F+d.z).color(col_r, col_g, col_b, 0.01F).endVertex();
			renderer.pos(pos.getX()+0.5F+c.x, pos.getY()+0.5F+c.y, pos.getZ()+0.5F+c.z).color(col_r, col_g, col_b, 0.01F).endVertex();

		}
	}

	public void preRender(final int destroyStage) {
		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.disableTexture2D();
		GlStateManager.shadeModel(7425);
		GlStateManager.depthMask(false);
	}

	public void postRender(final int destroyStage) {
		GlStateManager.enableTexture2D();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableAlpha();
		RenderHelper.enableStandardItemLighting();
	}

	public static Matrix4f CreateFromAxisAngle(final Vector3f axis, final float angle) {
		final Matrix4f mat = new Matrix4f();
		mat.set(new AxisAngle4f(axis, angle));
		return mat;
	}
}

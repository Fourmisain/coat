package de.siphalor.coat.list.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import de.siphalor.coat.Coat;
import de.siphalor.coat.ConfigScreen;
import de.siphalor.coat.handler.Message;
import de.siphalor.coat.list.ConfigListCompoundEntry;
import de.siphalor.coat.list.ConfigListWidget;
import de.siphalor.coat.util.CoatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConfigListSubTreeEntry extends ConfigListCompoundEntry {
	private static final TranslatableText OPEN_TEXT = new TranslatableText(Coat.MOD_ID + ".tree.open");

	private final ConfigListWidget configWidget;
	private final ButtonWidget button;
	private Text nameText;

	public ConfigListSubTreeEntry(ConfigListWidget configWidget) {
		this.configWidget = configWidget;
		button = new ButtonWidget(0, 0, 50, 20, OPEN_TEXT,
				button -> ((ConfigScreen) MinecraftClient.getInstance().currentScreen).openCategory(configWidget.getTreeEntry())
		);
	}

	@Override
	public void widthChanged(int newWidth) {
		super.widthChanged(newWidth);
		button.x = newWidth - button.getWidth();
		nameText = CoatUtil.intelliTrim(MinecraftClient.getInstance().textRenderer, configWidget.getName(), newWidth);
	}

	@Override
	public void render(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		int r = entryHeight / 2;

		MinecraftClient.getInstance().getTextureManager().bindTexture(configWidget.getBackground());
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();
		buffer.begin(GL11.GL_TRIANGLE_STRIP, VertexFormats.POSITION_COLOR_TEXTURE);
		buffer.vertex(x,                  y,               0).color(0x33, 0x33, 0x33, 0xff).texture(0F,                      0F               ).next();
		buffer.vertex(x + r,              y + r,           0).color(0x77, 0x77, 0x77, 0xff).texture(r / 32F,                 r / 32F          ).next();
		buffer.vertex(x + entryWidth,     y,               0).color(0x33, 0x33, 0x33, 0xff).texture(entryWidth / 32F,        0F               ).next();
		buffer.vertex(x + entryWidth - r, y + r,           0).color(0x77, 0x77, 0x77, 0xff).texture((entryWidth - r) / 32F,  r / 32F          ).next();
		buffer.vertex(x + entryWidth,     y + entryHeight, 0).color(0x33, 0x33, 0x33, 0xff).texture(entryWidth / 32F,        entryHeight / 32F).next();
		buffer.vertex(x + r,              y + r,           0).color(0x77, 0x77, 0x77, 0xff).texture(r / 32F,                 r / 32F          ).next();
		buffer.vertex(x,                  y + entryHeight, 0).color(0x33, 0x33, 0x33, 0xff).texture(0F,                      entryHeight / 32F).next();
		buffer.vertex(x,                  y,               0).color(0x33, 0x33, 0x33, 0xff).texture(0F,                      0F               ).next();
		tessellator.draw();

		button.x = x + getEntryWidth() - button.getWidth();
		button.y = y + 2;
		MinecraftClient.getInstance().textRenderer.draw(matrices, nameText, x + r, y + (entryHeight - 7) / 2F, CoatUtil.TEXT_COLOR);
		button.render(matrices, mouseX, mouseY, tickDelta);
	}

	@Override
	public int getHeight() {
		return 24;
	}

	@Override
	public Collection<Message> getMessages() {
		return Collections.emptyList();
	}

	@Override
	public void tick() {

	}

	@Override
	public int getEntryWidth() {
		return parent.getEntryWidth();
	}

	@Override
	public List<? extends Element> children() {
		return Collections.singletonList(button);
	}
}

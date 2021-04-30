package de.siphalor.coat.testmod;

import com.google.common.collect.ImmutableList;
import de.siphalor.amecs.api.PriorityKeyBinding;
import de.siphalor.coat.ConfigScreen;
import de.siphalor.coat.handler.Message;
import de.siphalor.coat.input.CheckBoxConfigInput;
import de.siphalor.coat.input.TextConfigInput;
import de.siphalor.coat.list.ConfgListEntry;
import de.siphalor.coat.list.ConfigListConfigEntry;
import de.siphalor.coat.list.ConfigListTextEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.text.LiteralText;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedList;

public class CoatTestmod implements ClientModInitializer {
	private static final String MOD_ID = "coat_testmod";

	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(new ScreenBinding(MOD_ID, 84, MOD_ID));
	}

	public static ConfigScreen createScreen() {
		LinkedList<ConfgListEntry> list = new LinkedList<>();
		list.add(new ConfigListTextEntry(new LiteralText("This is some stupidly long text!")));
		list.add(new ConfigListTextEntry(new LiteralText("This is some stupidly long text! this is even fucking hell longer.")));
		list.add(new ConfigListTextEntry(new LiteralText("This is some stupidly long text!")));
		list.add(new ConfigListTextEntry(new LiteralText("I'd probably just kill all humans if it came to it - profjb")));
		list.add(new ConfigListConfigEntry<>(
				new LiteralText("a boolean"),
				new LiteralText("noop"),
				new GenericEntryHandler<>(true, s -> Collections.emptyList()),
				new CheckBoxConfigInput(null, true, false)
		));
		list.add(new ConfigListConfigEntry<>(
				new LiteralText("Blub"),
				new LiteralText("This is a fine description"),
				new GenericEntryHandler<>("default", s -> Collections.emptyList()),
				new TextConfigInput(new LiteralText("Some placeholder"))
		));
		list.add(new ConfigListConfigEntry<>(
				new LiteralText("This is a really long title for a config entry"),
				new LiteralText("This is a fine description"),
				new GenericEntryHandler<>("test", s ->
						StringUtils.isAllLowerCase(s)
								? Collections.emptyList()
								: ImmutableList.of(
										new Message(Message.Level.INFO, new LiteralText("Some information about why this config aught to be lowercase letters only")),
										new Message(Message.Level.ERROR, new LiteralText("Must be all lowercase!"))
								)
				),
				new TextConfigInput(new LiteralText("Some placeholder"))
		));
		list.add(new ConfigListConfigEntry<>(
				new LiteralText("Blub"),
				new LiteralText("This is a fine description"),
				new GenericEntryHandler<>("default", s -> Collections.emptyList()),
				new TextConfigInput(new LiteralText("Some placeholder"))
		));

		ConfigScreen screen = new ConfigScreen(MinecraftClient.getInstance().currentScreen, MOD_ID, list);

		return screen;
	}

	public static class ScreenBinding extends KeyBinding implements PriorityKeyBinding {
		public ScreenBinding(String translationKey, int code, String category) {
			super(translationKey, code, category);
		}

		@Override
		public boolean onPressedPriority() {
			if (MinecraftClient.getInstance().currentScreen instanceof TitleScreen) {
				MinecraftClient.getInstance().openScreen(createScreen());
			}
			return false;
		}
	}
}

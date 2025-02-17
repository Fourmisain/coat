package de.siphalor.coat.list;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract entry that consists of several sub elements.
 */
public abstract class ConfigListCompoundEntry extends ConfigListEntry implements ParentElement, EntryContainer {
	private Element focused;
	private boolean dragging;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDragging() {
		return dragging;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	/**
	 * {@inheritDoc}
	 */
	@Nullable
	@Override
	public Element getFocused() {
		return focused;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocused(Element focused) {
		this.focused = focused;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void focusLost() {
		setFocused(null);
		super.focusLost();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void entryHeightChanged(Element element) {
		parent.entryHeightChanged(this);
	}
}

package me.saiintbrisson.minecraft;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@ToString
@Setter(AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
public final class ViewItem {

	enum State {UNDEFINED, HOLDING}

	private int slot;
	private State state = State.UNDEFINED;
	private boolean paginationItem;
	private String referenceKey;
	private ViewItemHandler renderHandler, updateHandler, clickHandler;

	@Setter(AccessLevel.PUBLIC)
	private Object item;

	@Getter(AccessLevel.PUBLIC)
	private boolean closeOnClick, cancelOnClick, cancelOnShiftClick;

	@Getter(AccessLevel.NONE)
	private Map<String, Object> data;

	/**
	 * @deprecated Use {@link VirtualView#slot(int)} instead.
	 */
	@Deprecated
	public ViewItem() {
		this(-1);
	}

	ViewItem(final int slot) {
		this.slot = slot;
	}

	/**
	 * The fallback item stack that will be rendered if a function that can render is not defined or if
	 * a function that can render does not render an item.
	 *
	 * @return The fallback item stack.
	 */
	Object getItem() {
		return item;
	}

	/**
	 * Sets the handler that'll be called when the item is rendered.
	 *
	 * @param renderHandler The render handler.
	 */
	public ViewItem onRender(@Nullable ViewItemHandler renderHandler) {
		this.renderHandler = renderHandler;
		return this;
	}

	/**
	 * Sets the handler that'll be called when the item is updated.
	 *
	 * @param updateHandler The update handler.
	 */
	public ViewItem onUpdate(@Nullable ViewItemHandler updateHandler) {
		this.updateHandler = updateHandler;
		return this;
	}

	/**
	 * Sets the handler that'll be called when the item is clicked by a player.
	 *
	 * @param clickHandler The click handler.
	 */
	public ViewItem onClick(@Nullable ViewItemHandler clickHandler) {
		this.clickHandler = clickHandler;
		return this;
	}

	@NotNull
	public ViewItem cancelOnClick() {
		return withCancelOnClick(!cancelOnClick);
	}

	@NotNull
	public ViewItem withCancelOnClick(boolean cancelOnClick) {
		this.cancelOnClick = cancelOnClick;
		return this;
	}

	@NotNull
	public ViewItem closeOnClick() {
		return withCloseOnClick(!closeOnClick);
	}

	@NotNull
	public ViewItem withCloseOnClick(boolean closeOnClick) {
		this.closeOnClick = !closeOnClick;
		return this;
	}

	@SuppressWarnings("unchecked")
	<T> T getData(@NotNull String key) {
		return data == null ? null : (T) data.get(key);
	}

	void setData(@NotNull String key, @NotNull Object value) {
		withData(key, value);
	}

	@NotNull
	public ViewItem withData(@NotNull String key, @NotNull Object value) {
		if (data == null)
			data = new HashMap<>();

		data.put(key, value);
		return this;
	}

	public void referencedBy(String key) {
		this.referenceKey = key;
	}

}
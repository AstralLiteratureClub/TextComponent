package bet.astral.text.component.impl;

import bet.astral.text.color.TextColor;
import bet.astral.text.component.Component;
import bet.astral.text.component.translation.TranslatableComponent;
import bet.astral.text.events.click.ClickEvent;
import bet.astral.text.events.click.ClickType;
import bet.astral.text.events.hover.HoverEvent;
import bet.astral.text.placeholder.Placeholder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.*;

public abstract class ComponentImpl implements Component {
	private static final Map<String, DecimalFormat> decimalFormats = new HashMap<>();
	private String value;
	private Map<ClickType, ClickEvent> clickEvents = null;
	private Map<Integer, Placeholder<?>> placeholders = null;
	private TextColor color = null;
	private Component before = null;
	private Component next = null;
	private HoverEvent hoverEvent = null;

	protected ComponentImpl(String value) {
		this.value = value;
	}
	protected ComponentImpl(String value, TextColor color) {
		this.value = value;
		this.color = color;
	}

	@Override
	public @NotNull String getValue() {
		return value;
	}

	@Override
	public @NotNull String getValueParsed() {
		if (getPlaceholders()==null){
			return value;
		}
		String returnValue = this.value;
		for (int i = 0; i < 10; i++){
			Placeholder<?> placeholder = getPlaceholders().get(i);
			if (placeholder==null){
				continue;
			}
			String value = placeholder.getValue().toString();
			if (placeholder.getExtra() != null) {
				switch (placeholder.getType()) {
					case FLOAT, DOUBLE -> {
						DecimalFormat format = decimalFormats.get(placeholder.getExtra());
						String formatted = format.format(placeholder.getValue());
						returnValue = returnValue.replace("{"+i+"}", formatted);
						continue;
					}
				}
			}
			returnValue = returnValue.replace("{"+i+"}", value);
		}
		return returnValue;
	}

	@Override
	public TextColor getColor() {
		return color;
	}

	@Override
	public @NotNull Component setColor(TextColor color) {
		this.color = color;
		return this;
	}

	@Override
	public boolean hasColor() {
		return color != null;
	}


	@Override
	public @NotNull String getPlain() {
		Component component = getPrevious();
		if (component == null){
			component = this;
		}
		while (component.hasPrevious()) {
			component = component.getPrevious();
		}
		StringBuilder builder = new StringBuilder();
		builder.append(component.getValue());
		while (component.hasNext()){
			component = component.getNext();
			assert component != null;
			builder.append(component.getValue());
		}
		return builder.toString();
	}

	@Override
	public @NotNull String getParsed() {
		Component component = getPrevious();
		if (component == null){
			component = this;
		}
		while (component.hasPrevious()) {
			component = component.getPrevious();
		}
		StringBuilder builder = new StringBuilder();
		builder.append(component.getValueParsed());
		while (component.hasNext()){
			component = component.getNext();
			assert component != null;
			builder.append(component.getValueParsed());
		}
		return builder.toString();
	}


	@Override
	public Component getPrevious() {
		return before;
	}

	@Override
	public Component getNext() {
		return next;
	}

	@Override
	public @NotNull Component append(@NotNull Component component) {
		if (component instanceof ComponentImpl impl){
			impl.before = this;
		}
		this.next = component;
		return next;
	}

	@Override
	public HoverEvent getHoverEvent() {
		return hoverEvent;
	}

	@Override
	public @NotNull Component hoverEvent(HoverEvent hoverEvent) {
		this.hoverEvent = hoverEvent;
		return this;
	}

	@Override
	public @NotNull Component clearHoverEvent() {
		this.hoverEvent = null;
		return this;
	}

	@Override
	public @Nullable Map<ClickType, ClickEvent> getClickEvents() {
		return clickEvents;
	}

	@Override
	public @NotNull Component clearClickEvents() {
		clickEvents = null;
		return this;
	}

	@Override
	public @Nullable ClickEvent getClickEvent(@NotNull ClickType clickType) {
		if (clickEvents == null){
			return null;
		}
		return clickEvents.get(clickType);
	}

	@Override
	public @NotNull Component clickEvent(@NotNull ClickType clickType, @NotNull ClickEvent clickEvent) {
		if (clickEvents == null){
			clickEvents = new HashMap<>();
		}
		clickEvents.put(clickType, clickEvent);
		return this;
	}

	@Override
	public Component asComponent() {
		return this;
	}

	@Override
	public Map<Integer, Placeholder<?>> getPlaceholders() {
		return placeholders;
	}

	@Override
	public Placeholder<?> getPlaceholder(int value) {
		return placeholders != null ? placeholders.get(value) : null;
	}

	protected void addPlaceholder(Object object, String extra, Placeholder.Type type){
		int id = 0;
		if (placeholders!=null) {
			for (id = 0; id < 11; id++) {
				if (placeholders.get(id)==null){
					break;
				}
			}
		}
		if (id>10){
			return;
		}
		if (placeholders == null){
			placeholders = new HashMap<>();
		}
		placeholders.put(id, new Placeholder<>(id, object, extra, type));
	}

	@Override
	public @Nullable Component createPlaceholders() {
		if (placeholders == null){
			placeholders = new HashMap<>();
		}
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(Component value) {
		addPlaceholder(value, null, Placeholder.Type.COMPONENT);
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(String value) {
		addPlaceholder(value, null, Placeholder.Type.STRING);
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(Character value) {
		addPlaceholder(value, null, Placeholder.Type.CHARACTER);
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(Long value) {
		addPlaceholder(value, null, Placeholder.Type.LONG);
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(Integer value) {
		addPlaceholder(value, null, Placeholder.Type.INTEGER);
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(Float value, String format) {
		addPlaceholder(value, format, Placeholder.Type.FLOAT);
		return this;
	}

	@Override
	public @NotNull Component addPlaceholder(Double value, String format) {
		addPlaceholder(value, format, Placeholder.Type.DOUBLE);
		return this;
	}
}

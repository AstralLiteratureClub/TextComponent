package bet.astral.text.events.hover;

import bet.astral.text.component.ComponentLike;
import bet.astral.text.events.EventType;
import bet.astral.text.component.Component;

public class TextHoverEvent implements HoverEvent, ComponentLike, HoverComponentEvent {
	private final Component component;
	public TextHoverEvent(Component component) {
		this.component = component;
	}

	public Component geComponentValue(){
		return component;
	}
	@Override
	public Component asComponent() {
		return component;
	}

	@Override
	public EventType getEventType() {
		return EventType.HOVER;
	}

	@Override
	public String getValue() {
		return component.getPlain();
	}

	@Override
	public HoverType getType() {
		return HoverType.SHOW_TEXT;
	}

	@Override
	public Component getComponentValue() {
		return component;
	}
}

package bet.astral.text.events.click;

import bet.astral.text.events.Event;
import bet.astral.text.events.EventType;
import bet.astral.text.events.hover.HoverType;

public interface ClickEvent extends Event {
	static ClickEvent copyToClipboard(String value){
		return new CopyToClipboardClickEvent(value);
	}

	static ClickEvent of(HoverType type, String value){
		if (ClickEventType.map.get(type.getName()) == null){
			return null;
		}
		return ClickEventType.map.get(type.getName()).create(value);
	}

	@Override
	default EventType getEventType() {
		return EventType.CLICK;
	}

	String getValue();
	ClickEventType getClickEventType();
}
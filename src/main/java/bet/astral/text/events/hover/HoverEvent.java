package bet.astral.text.events.hover;

import bet.astral.text.events.Event;
import bet.astral.text.component.Component;

public interface HoverEvent extends Event {
	static HoverEvent showText(Component component){
		return new TextHoverEvent(component);
	}
	static HoverEvent showText(String text){
		return new TextHoverEvent(Component.of(text));
	}
	static HoverEvent of(HoverType type, String value){
		if (HoverType.map.get(type.getName()) == null){
			return null;
		}
		return HoverType.map.get(type.getName()).create(value);
	}
	static HoverEvent of(HoverType type, Component value){
		if (HoverType.map.get(type.getName()) == null){
			return null;
		}
		return HoverType.map.get(type.getName()).create(value);
	}
	String getValue();
	HoverType getType();
}

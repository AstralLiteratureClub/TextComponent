package bet.astral.text.events.hover;

import bet.astral.text.component.Component;

public interface HoverComponentEvent extends HoverEvent{
	Component getComponentValue();
}

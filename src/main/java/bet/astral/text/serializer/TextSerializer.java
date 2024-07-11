package bet.astral.text.serializer;

import bet.astral.text.component.Component;

public interface TextSerializer<S> {
	S serialize(Component component);
}

package bet.astral.text.serializer;

import bet.astral.text.component.Component;

public interface TextDeserializer<S> {
	Component deserialize(S serialized);
}

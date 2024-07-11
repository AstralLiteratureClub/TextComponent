package bet.astral.text.component.text.impl;

import bet.astral.text.color.TextColor;
import bet.astral.text.component.impl.ComponentImpl;
import bet.astral.text.component.text.TextComponent;

public class TextComponentImpl extends ComponentImpl implements TextComponent {
	public TextComponentImpl(String value) {
		super(value);
	}

	public TextComponentImpl(String value, TextColor color) {
		super(value, color);
	}
}

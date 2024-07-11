package bet.astral.text.component.translation.impl;

import bet.astral.text.color.TextColor;
import bet.astral.text.component.Component;
import bet.astral.text.component.impl.ComponentImpl;
import bet.astral.text.component.translation.TranslatableComponent;
import bet.astral.text.translations.Translatable;
import bet.astral.text.translations.TranslationKey;
import org.jetbrains.annotations.NotNull;

public class TranslationComponentImpl extends ComponentImpl implements TranslatableComponent {
	private Component fallback;
	private final TranslationKey key;
	public TranslationComponentImpl(@NotNull String value) {
		super(value);
		key = new TranslationKey(value);
	}
	public TranslationComponentImpl(@NotNull String value, TextColor color) {
		super(value, color);
		key = new TranslationKey(value);
	}
	public TranslationComponentImpl(@NotNull Translatable value) {
		super(value.getTranslationKeyAsString());
		key = value.getTranslationKey();
	}

	public TranslationComponentImpl(@NotNull Translatable value, TextColor color) {
		super(value.getTranslationKeyAsString(), color);
		key = value.getTranslationKey();
	}

	@Override
	public TranslationKey getTranslationKey() {
		return key;
	}

	@Override
	public Component getFallback() {
		return fallback;
	}

	@Override
	public TranslatableComponent setFallback(Component textComponent) {
		this.fallback = textComponent;
		return this;
	}

	@Override
	public TranslatableComponent setFallback(String text) {
		this.fallback = Component.of(text);
		return this;
	}

	@Override
	public TranslatableComponent clearFallback() {
		this.fallback = null;
		return this;
	}
}

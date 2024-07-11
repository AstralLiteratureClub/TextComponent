package bet.astral.text.component.translation;

import bet.astral.text.component.Component;
import bet.astral.text.translations.Translatable;
import bet.astral.text.translations.TranslationKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TranslatableComponent extends Component, Translatable {
	TranslationKey getTranslationKey();

	@Contract("-> this")
	Component getFallback();
	@Contract("_ -> this")
	TranslatableComponent setFallback(Component textComponent);
	@Contract("_ -> this")
	TranslatableComponent setFallback(String text);
	@Contract("-> this")
	TranslatableComponent clearFallback();

	@Override
	default @NotNull String getType() {
		return "translation";
	}
}

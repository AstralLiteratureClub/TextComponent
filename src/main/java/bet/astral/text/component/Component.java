package bet.astral.text.component;

import bet.astral.text.color.TextColor;
import bet.astral.text.component.text.TextComponent;
import bet.astral.text.component.text.impl.TextComponentImpl;
import bet.astral.text.component.translation.TranslatableComponent;
import bet.astral.text.component.translation.impl.TranslationComponentImpl;
import bet.astral.text.events.click.ClickEvent;
import bet.astral.text.events.click.ClickType;
import bet.astral.text.events.hover.HoverEvent;
import bet.astral.text.placeholder.Placeholder;
import bet.astral.text.translations.Translatable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Map;

public interface Component extends ComponentLike {
	@Contract("_ -> new")
	static @NotNull TextComponent of(@NotNull String value) {
		return new TextComponentImpl(value);
	}
	@Contract("_, _ -> new")
	static @NotNull TextComponent of(@NotNull String value, @Nullable TextColor color) {
		return new TextComponentImpl(value, color);
	}
	@Contract("_ -> new")
	static @NotNull TranslatableComponent translatable(@NotNull String value){
		return new TranslationComponentImpl(value);
	}
	@Contract("_, _ -> new")
	static @NotNull TranslatableComponent translatable(@NotNull String value, @Nullable TextColor color){
		return new TranslationComponentImpl(value, color);
	}
	@Contract("_ -> new")
	static @NotNull TranslatableComponent translatable(@NotNull Translatable value){
		return new TranslationComponentImpl(value);
	}
	@Contract("_, _ -> new")
	static @NotNull TranslatableComponent translatable(@NotNull Translatable value, TextColor color){
		return new TranslationComponentImpl(value, color);
	}
	@NotNull
	String getType();

	/**
	 * Returns the complete component stream as a plain string
	 * @return complete component stream as plain string
	 */
	@NotNull
	String getPlain();

	@NotNull
	String getParsed();

	/**
	 * Returns this component instances text or translation key value
	 * @return value
	 */
	@NotNull
	String getValue();

	/**
	 * Returns this component instance text or translation with placeholders parsed.
	 * @return value
	 */
	@NotNull
	String getValueParsed();

	/**
	 * Returns the component before this component
	 * @return previous component, else null
	 */
	@Nullable
	Component getPrevious();
	default boolean hasPrevious() {
		return getPrevious() != null;
	}

	@Nullable
	Component getNext();
	default boolean hasNext() {
		return getNext() != null;
	}

	@NotNull
	@Contract("_ -> param1")
	Component append(@NotNull Component component);
	@NotNull
	@Contract("_ -> new")
	default TextComponent append(String text) {
		return (TextComponent) append(of(text));
	}
	@NotNull
	@Contract("_ -> new")
	default TextComponent append(char character){
		return append(String.valueOf(character));
	}

	@NotNull
	@Contract("_, _ -> new")
	default TextComponent append(@NotNull String text, @Nullable TextColor color) {
		return (TextComponent) append(of(text, color));
	}
	@NotNull
	@Contract("_ -> new")
	default TranslatableComponent appendTranslatable(@NotNull String translation) {
		return (TranslatableComponent) append(translatable(translation));
	}
	@NotNull
	@Contract("_, _ -> new")
	default TranslatableComponent appendTranslatable(@NotNull String translation, @NotNull TextColor color) {
		return (TranslatableComponent) append(translatable(translation, color));
	}
	@NotNull
	@Contract("_, _, _ -> new")
	default TranslatableComponent appendTranslatable(@NotNull String translation, @NotNull TextColor color, @NotNull Component fallback) {
		return ((TranslatableComponent) append(translatable(translation, color))).setFallback(fallback);
	}
	@NotNull
	@Contract("_ -> new")
	default TranslatableComponent appendTranslatable(@NotNull Translatable translatable) {
		return (TranslatableComponent) append(translatable(translatable));
	}
	@NotNull
	@Contract("_, _ -> new")
	default TranslatableComponent appendTranslatable(@NotNull Translatable translatable, @Nullable TextColor color) {
		return (TranslatableComponent) append(translatable(translatable, color));
	}
	@NotNull
	@Contract("_, _, _ -> new")
	default TranslatableComponent appendTranslatable(@NotNull Translatable translatable, @Nullable TextColor color, @NotNull Component fallback) {
		return ((TranslatableComponent) append(translatable(translatable, color))).setFallback(fallback);
	}	@NotNull
	@Contract("-> new")
	default Component appendSpace(){
		return append(" ");
	}
	@NotNull
	@Contract("-> new")
	default Component appendNewline(){
		return append("\n");
	}
	@Nullable
	HoverEvent getHoverEvent();
	@NotNull
	@Contract("_ -> this")
	Component hoverEvent(@Nullable HoverEvent hoverEvent);
	@NotNull
	@Contract("-> this")
	Component clearHoverEvent();

	@Nullable
	Map<ClickType, ClickEvent> getClickEvents();
	@NotNull
	@Contract("-> this")
	Component clearClickEvents();
	@Nullable
	ClickEvent getClickEvent(@NotNull ClickType clickType);
	@NotNull
	@Contract("_, _ -> this")
	Component clickEvent(@NotNull ClickType clickType, @NotNull ClickEvent clickEvent);

	@Nullable
	TextColor getColor();

	@NotNull
	@Contract("_ -> this")
	Component setColor(@Nullable TextColor color);
	boolean hasColor();

	@NotNull
	default Component getFirstComponent(){
		Component current = this;
		while (current.getPrevious() != null){
			current = current.getPrevious();
		}
		return current;
	}

	@Nullable
	Map<Integer, Placeholder<?>> getPlaceholders();
	@Contract("-> this")
	@Nullable
	Component createPlaceholders();
	@Nullable
	Placeholder<?> getPlaceholder(@Range(from = 0, to = 10) int value);
	@NotNull
	@Contract("_ -> this")
	Component addPlaceholder(Component value);
	@NotNull
	@Contract("_ -> this")
	Component addPlaceholder(String value);
	@NotNull
	@Contract("_ -> this")
	Component addPlaceholder(Character value);
	@NotNull
	@Contract("_ -> this")
	Component addPlaceholder(Long value);
	@NotNull
	@Contract("_ -> this")
	Component addPlaceholder(Integer value);
	@NotNull
	@Contract("_ -> this")
	default Component addPlaceholder(Float value) {
		return addPlaceholder(value, ".xx");
	}
	@NotNull
	@Contract("_ -> this")
	default Component addPlaceholder(Double value) {
		return addPlaceholder(value, ".xx");
	}
	@NotNull
	@Contract("_, _ -> this")
	Component addPlaceholder(Float value, String format);
	@NotNull
	@Contract("_, _ -> this")
	Component addPlaceholder(Double value, String format);
}

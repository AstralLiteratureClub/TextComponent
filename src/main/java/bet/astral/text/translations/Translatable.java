package bet.astral.text.translations;

public interface Translatable {
	default String getTranslationKeyAsString() {
		return getTranslationKey().getTranslationKeyAsString();
	}
	TranslationKey getTranslationKey();
}

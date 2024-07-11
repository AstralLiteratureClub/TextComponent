package bet.astral.text.translations;

public class TranslationKey implements Translatable {
	private final String key;

	public TranslationKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return key;
	}

	@Override
	public TranslationKey getTranslationKey() {
		return this;
	}

	@Override
	public String getTranslationKeyAsString() {
		return key;
	}
}

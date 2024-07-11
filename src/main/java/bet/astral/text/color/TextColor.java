package bet.astral.text.color;

public interface TextColor {
	TextColor WHITE = of(255, 255, 255);

	static StaticTextColor of(float red, float green, float blue){
		return new StaticTextColor(red, green, blue);
	}
	static StaticTextColor of(float red, float green, float blue, float alpha){
		return new StaticTextColor(red, green, blue, alpha);
	}
	static StaticTextColor of(float[] colors) throws IllegalArgumentException{
		if (colors.length < 2 || colors.length>3){
			throw new IllegalArgumentException("Given colors array is not array of 3 colors or 3 colors and alpha.");
		}
		if (colors.length==3){
			return of(colors[0], colors[1], colors[2]);
		}
		return of(colors[0], colors[1], colors[2], colors[3]);
	}
	static StaticTextColor of(String hex) {
		if (hex==null){
			return null;
		}
		return of(
				Integer.valueOf(hex.substring(1, 3), 16),
				Integer.valueOf(hex.substring(3, 5), 16),
				Integer.valueOf(hex.substring(5, 7), 16));
	}

	float getRed();
	float getGreen();
	float getBlue();
	float getAlpha();
	float[] toArray();
	String toHex();
	String toHexAlpha();

	boolean isAlphaPresent();
}

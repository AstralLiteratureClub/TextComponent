package bet.astral.text.color;

public class StaticTextColor implements TextColor{
	private final float red;
	private final float green;
	private final float blue;
	private final float alpha;

	public StaticTextColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public StaticTextColor(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = -1;
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public float getAlpha() {
		return alpha;
	}

	@Override
	public float[] toArray() {
		if (alpha<0){
			return new float[]{red, green, blue};
		}
		return new float[]{red, green, blue, alpha};
	}

	@Override
	public String toHex() {
		return String.format("#%02X%02X%02X", (int) red, (int) green, (int) blue);
	}

	@Override
	public String toHexAlpha() {
		return String.format("#%02X%02X%02X%02X", (int) red, (int) green, (int) blue, (int) alpha);
	}

	@Override
	public boolean isAlphaPresent() {
		return alpha>-1;
	}
}

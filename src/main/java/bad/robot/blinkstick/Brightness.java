package bad.robot.blinkstick;

public class Brightness {

	public static Brightness Max = new Brightness(255);

	private int brightness;

	public Brightness(int brightness) {
		if (brightness < 0)
			brightness = 0;
		else if (brightness > 255)
			brightness = 255;

		this.brightness = brightness;
	}

	public boolean max() {
		return brightness == 255;
	}

	public Color applyTo(Color color) {
		byte r = applyBrightnessTo((color.getRed()), brightness);
		byte g = applyBrightnessTo((color.getGreen()), brightness);
		byte b = applyBrightnessTo((color.getBlue()), brightness);
		return new Color(r, g, b);
	}

	public static Color applyBrightnessAsPercentage(Color color, int percent) {
		if (color.maximumBrightness())
			return color;
		long amount = Math.round(2.55 * percent);
		long r = color.getRed() + amount;
		long g = color.getGreen() + amount;
		long b = color.getBlue() + amount;
		return new Color((byte) r, (byte) g, (byte) b);

	}

	private byte applyBrightnessTo(int color, float brightness) {
//		if (brightness == 255)
//			return (byte) color;

		int colorAsUnsigned = unsigned((byte) color);
		float colorAsScaledValue = (colorAsUnsigned) / (float) 255;
		int remappedColor = (int) (colorAsScaledValue * brightness);
		return (byte) signed(remappedColor);
	}

	private static int unsigned(int signed) {
		if (signed < 0)
			return signed + 0xFF;
		return signed;
	}

	private static int signed(int unsigned) {
		if (unsigned > 127)
			return unsigned - 0xFF;
		return unsigned;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Brightness that = (Brightness) o;
		return brightness == that.brightness;
	}

	@Override
	public int hashCode() {
		return brightness;
	}

	public String toString() {
		return String.format("brightness: %d", brightness);
	}
}

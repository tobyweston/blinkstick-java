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
//		if (color.maximumBrightness())
//			return color;

		byte r = remap((color.getRed()), brightness);
		byte g = remap((color.getGreen()), brightness);
		byte b = remap((color.getBlue()), brightness);
		return new Color(r, g, b);
	}

	private byte remap(int color, float brightness) {
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

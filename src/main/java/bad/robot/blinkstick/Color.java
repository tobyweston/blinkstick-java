package bad.robot.blinkstick;

import static java.lang.Math.*;
import static java.lang.String.format;

public class Color {

	private final int red;
	private final int green;
	private final int blue;

	Color(String hex) {
		if (hex.length() != 7) throw new IllegalArgumentException("Hex values should be '#rrggbb'");
		this.red = Integer.valueOf(hex.substring(1, 3), 16);
		this.green = Integer.valueOf(hex.substring(3, 5), 16);
		this.blue = Integer.valueOf(hex.substring(5, 7), 16);
	}

	Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int rgb() {
		return 255 << 24 | red << 16 | green << 8 | blue;
	}

	public int getRed() {
		return toUnsignedInt(red);
	}

	public int getGreen() {
		return toUnsignedInt(green);
	}

	public int getBlue() {
		return toUnsignedInt(blue);
	}

	public boolean maximumBrightness() {
		return red == 255 || green == 255 || blue == 255;
	}

	@Deprecated
	public Color apply(Brightness brightness) {
		return brightness.applyTo(this);
	}

	public Color lighten(double factor) {
		int r = getRed();
		int g = getGreen();
		int b = getBlue();

		int i = (int) (1.0 / (1.0 - factor));
		if (r == 0 && g == 0 && b == 0) {
			return new Color(i, i, i);
		}
		if (r > 0 && r < i) r = i;
		if (g > 0 && g < i) g = i;
		if (b > 0 && b < i) b = i;

		return new Color(min((int) (r / factor), 255), min((int) (g / factor), 255), min((int) (b / factor), 255));
	}

	public Color darken(double factor) {
		return new Color(max((int) (getRed() * factor), 0), max((int) (getGreen() * factor), 0), max((int) (getBlue() * factor), 0));
	}


	private static int toUnsignedInt(int signed) {
		return signed & 0xFF;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Color color = (Color) o;
		return rgb() == color.rgb();

	}

	@Override
	public int hashCode() {
		return 31 * rgb();
	}

	@Override
	public String toString() {
		int red = toUnsignedInt(this.red);
		int green = toUnsignedInt(this.green);
		int blue = toUnsignedInt(this.blue);

		return format("#%02X%02X%02X", red, green, blue);
//		return format(String.format("(%d,%d,%d)", red, green, blue));
	}
}

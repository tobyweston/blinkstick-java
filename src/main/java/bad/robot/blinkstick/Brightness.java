package bad.robot.blinkstick;

public class Brightness {

	private int value;

	public Brightness(int value) {
		if (value < 0)
			value = 0;
		else if (value > 255)
			value = 255;

		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Brightness that = (Brightness) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return value;
	}

	public String toString() {
		return String.format("brightness: %d", value);
	}
}

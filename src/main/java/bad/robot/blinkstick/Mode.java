package bad.robot.blinkstick;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Mode {

	Normal(0),
	Inverse(1),
	Ws2812(2),
	Ws2812Mirror(2),
	Unknown(-1);

	private final int mode;

	Mode(int mode) {
		this.mode = mode;
	}

	public byte asByte() {
		return (byte) mode;
	}

	public static Mode fromByte(byte b) {
		return Stream.of(Mode.values())
			.filter(mode -> mode.asByte() == b)
			.findFirst()
			.orElse(Unknown);
	}
}

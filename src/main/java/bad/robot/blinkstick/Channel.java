package bad.robot.blinkstick;

enum Channel {
	R(0),
	G(1),
	B(2);

	private final int channel;

	Channel(int channel) {
		this.channel = channel;
	}

	public byte asByte() {
		return (byte) channel;
	}
}

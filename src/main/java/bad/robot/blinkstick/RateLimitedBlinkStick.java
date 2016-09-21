package bad.robot.blinkstick;

import java.util.function.Supplier;

class RateLimitedBlinkStick implements BlinkStick {

	private static final long MaximumFrequency = 20;

	private final BlinkStick delegate;

	private long timeOfLastCall = System.currentTimeMillis();

	public RateLimitedBlinkStick(BlinkStick delegate) {
		this.delegate = delegate;
	}

	@Override
	public void setMode(Mode mode) {
		rateLimit(() -> {
			delegate.setMode(mode);
			return null;
		});
	}

	@Override
	public Mode getMode() {
		return rateLimit(delegate::getMode);
	}

	@Override
	@Deprecated
	public void setColor(int r, int g, int b) {
		rateLimit(() -> {
			delegate.setColor(r, g, b);
			return null;
		});
	}

	@Override
	@Deprecated
	public void setColor(int value) {
		rateLimit(() -> {
			delegate.setColor(value);
			return null;
		});
	}

	@Override
	public void setColor(Color color) {
		rateLimit(() -> {
			delegate.setColor(color);
			return null;
		});
	}

	@Override
	@Deprecated
	public void setColors(byte[] colorData) {
		rateLimit(() -> {
			delegate.setColors(colorData);
			return null;
		});
	}

	@Override
	@Deprecated
	public void setColors(Channel channel, byte[] colorData) {
		rateLimit(() -> {
			delegate.setColors(channel, colorData);
			return null;
		});
	}

	@Override
	@Deprecated
	public void setIndexedColor(int channel, int index, int r, int g, int b) {
		rateLimit(() -> {
			delegate.setIndexedColor(channel, index, r, g, b);
			return null;
		});
	}

	@Override
	@Deprecated
	public void setIndexedColor(int channel, int index, int value) {
		rateLimit(() -> {
			delegate.setIndexedColor(channel, index, value);
			return null;
		});
	}

	@Override
	public void setIndexedColor(int index, Color color) {
		rateLimit(() -> {
			delegate.setIndexedColor(index, color);
			return null;
		});
	}

	@Override
	@Deprecated
	public void setRandomColor() {
		rateLimit(() -> {
			delegate.setRandomColor();
			return null;
		});
	}

	@Override
	public void turnOff() {
		rateLimit(() -> {
			delegate.turnOff();
			return null;
		});
	}

	@Override
	public int getColor() {
		return rateLimit(delegate::getColor);
	}

	@Override
	public void setInfoBlock1(String value) {
		rateLimit(() -> {
			delegate.setInfoBlock1(value);
			return null;
		});
	}

	@Override
	public void setInfoBlock2(String value) {
		rateLimit(() -> {
			delegate.setInfoBlock2(value);
			return null;
		});
	}

	@Override
	public String getInfoBlock1() {
		return rateLimit(delegate::getInfoBlock1);
	}

	@Override
	public String getInfoBlock2() {
		return rateLimit(delegate::getInfoBlock2);
	}

	@Override
	public String getManufacturer() {
		return rateLimit(delegate::getManufacturer);
	}

	@Override
	public String getProductDescription() {
		return rateLimit(delegate::getProductDescription);
	}

	@Override
	public String getSerial() {
		return rateLimit(delegate::getSerial);
	}

	private <T> T rateLimit(Supplier<T> callable) {
		long timeSinceLastCall = System.currentTimeMillis() - timeOfLastCall;
		if (timeSinceLastCall < MaximumFrequency) {
			System.out.println("last call " + timeSinceLastCall + "ms waiting " + (MaximumFrequency - timeSinceLastCall));
			try {
				Thread.sleep(MaximumFrequency - timeSinceLastCall);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		try {
			return callable.get();
		} finally {
			timeOfLastCall = System.currentTimeMillis();
		}
	}
}

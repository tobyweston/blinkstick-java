package bad.robot.blinkstick;

public interface BlinkStick {
	void setMode(Mode mode);
	Mode getMode();

	void setColor(int r, int g, int b);
	void setColor(int value);
	void setColor(Color color);

	void setColors(byte[] colorData);
	void setColors(Channel channel, byte[] colorData);

	void setIndexedColor(int channel, int index, int r, int g, int b);
	void setIndexedColor(int channel, int index, int value);
	void setIndexedColor(int index, Color color);

	void setRandomColor();
	void turnOff();

	int getColor();

	void setInfoBlock1(String value);
	void setInfoBlock2(String value);
	String getInfoBlock1();
	String getInfoBlock2();

	String getManufacturer();
	String getProductDescription();
	String getSerial();

}

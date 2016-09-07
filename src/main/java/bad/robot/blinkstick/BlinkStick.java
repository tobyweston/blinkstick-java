package bad.robot.blinkstick;

import com.codeminders.hidapi.HIDDevice;

import java.util.Random;

import static bad.robot.blinkstick.Mode.Unknown;
import static java.lang.String.*;

public class BlinkStick {

	private HIDDevice device;

	BlinkStick(HIDDevice device) {
		this.device = device;
	}

	/**
	 * Set the color of the device with separate r, g and b int values.
	 * The values are automatically converted to byte values
	 *
	 * @param r red int color value 0..255
	 * @param g gree int color value 0..255
	 * @param b blue int color value 0..255
	 */
	public void setColor(int r, int g, int b) {
		setColor((byte) r, (byte) g, (byte) b);
	}

	/**
	 * Set the color of the device with separate r, g and b byte values
	 *
	 * @param r red byte color value 0..255
	 * @param g gree byte color value 0..255
	 * @param b blue byte color value 0..255
	 */
	public void setColor(byte r, byte g, byte b) {
		try {
			device.sendFeatureReport(new byte[]{1, r, g, b});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Set indexed color of the device with separate r, g and b byte values for channel and LED index
	 *
	 * @param channel Channel (0 - R, 1 - G, 2 - B)
	 * @param index   Index of the LED
	 * @param r       red int color value 0..255
	 * @param g       gree int color value 0..255
	 * @param b       blue int color value 0..255
	 */
	public void setIndexedColor(int channel, int index, int r, int g, int b) {
		setIndexedColor((byte) channel, (byte) index, (byte) r, (byte) g, (byte) b);
	}

	/**
	 * Set indexed color of the device with separate r, g and b byte values for channel and LED index
	 *
	 * @param channel Channel (0 - R, 1 - G, 2 - B)
	 * @param index   Index of the LED
	 * @param r       red byte color value 0..255
	 * @param g       gree byte color value 0..255
	 * @param b       blue byte color value 0..255
	 */
	public void setIndexedColor(byte channel, byte index, byte r, byte g, byte b) {
		try {
			device.sendFeatureReport(new byte[]{5, channel, index, r, g, b});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Set the indexed color of BlinkStick Pro with Processing color value
	 *
	 * @param channel Channel (0 - R, 1 - G, 2 - B)
	 * @param index   Index of the LED
	 * @param value   color as int
	 */
	public void setIndexedColor(int channel, int index, int value) {
		int r = (value >> 16) & 0xFF;
		int g = (value >> 8) & 0xFF;
		int b = value & 0xFF;

		setIndexedColor(channel, index, r, g, b);
	}

	/**
	 * Set the indexed color of BlinkStick Pro with Processing color value for channel 0
	 *
	 * @param index Index of the LED
	 * @param color color as int
	 */
	public void setIndexedColor(int index, Color color) {
		setIndexedColor(0, index, color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Set the color of the device with Processing color value
	 *
	 * @param value color as int
	 */
	public void setColor(int value) {
		int r = (value >> 16) & 0xFF;
		int g = (value >> 8) & 0xFF;
		int b = value & 0xFF;

		setColor(r, g, b);
	}

	public void setColor(Color color) {
		setColor(color.rgb());
	}

	public void setRandomColor() {
		Random random = new Random();
		setColor(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}

	public void turnOff() {
		setColor(0, 0, 0);
	}


	/**
	 * Get the current color of the device as int
	 *
	 * @return The current color of the device as int
	 */
	public int getColor() {
		byte[] data = new byte[33];
		data[0] = 1;// First byte is ReportID

		try {
			int read = device.getFeatureReport(data);
			if (read > 0) {
				return (255 << 24) | (data[1] << 16) | (data[2] << 8) | data[3];
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return 0;
	}

	/**
	 * Get the current color of the device in #rrggbb format
	 *
	 * @return Returns the current color of the device as #rrggbb formated string
	 */
	public String getColorString() {
		int c = getColor();

		int red = (c >> 16) & 0xFF;
		int green = (c >> 8) & 0xFF;
		int blue = c & 0xFF;

		return format("#%02X%02X%02X", red, green, blue);
	}

	/**
	 * Get value of InfoBlocks
	 *
	 * @param id InfoBlock id, should be 1 or 2 as only supported info blocks
	 */
	private String getInfoBlock(int id) {
		byte[] data = new byte[33];
		data[0] = (byte) (id + 1);

		String result = "";
		try {
			int read = device.getFeatureReport(data);
			if (read > 0) {
				for (int i = 1; i < data.length; i++) {
					if (i == 0) {
						break;
					}

					result += (char) data[i];
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	/**
	 * Get value of InfoBlock1
	 *
	 * @return The value of info block 1
	 */
	public String getInfoBlock1() {
		return getInfoBlock(1);
	}

	/**
	 * Get value of InfoBlock2
	 *
	 * @return The value of info block 2
	 */
	public String getInfoBlock2() {
		return getInfoBlock(2);
	}


	/**
	 * Set value for InfoBlocks
	 *
	 * @param id    InfoBlock id, should be 1 or 2 as only supported info blocks
	 * @param value The value to be written to the info block
	 */
	private void setInfoBlock(int id, String value) {
		char[] charArray = value.toCharArray();
		byte[] data = new byte[33];
		data[0] = (byte) (id + 1);

		for (int i = 0; i < charArray.length; i++) {
			if (i > 32) {
				break;
			}

			data[i + 1] = (byte) charArray[i];
		}

		try {
			device.sendFeatureReport(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Set value for InfoBlock1
	 *
	 * @param value The value to be written to the info block 1
	 */
	public void setInfoBlock1(String value) {
		setInfoBlock(1, value);
	}

	/**
	 * Set value for InfoBlock2
	 *
	 * @param value The value to be written to the info block 2
	 */
	public void setInfoBlock2(String value) {
		setInfoBlock(2, value);
	}

	/**
	 * Get the manufacturer of the device
	 *
	 * @return Returns the manufacturer name of the device
	 */
	public String getManufacturer() {
		try {
			return device.getManufacturerString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Get the product description of the device
	 *
	 * @return Returns the product name of the device.
	 */
	public String getProduct() {
		try {
			return device.getProductString();
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * Get the serial number of the device
	 *
	 * @return Returns the serial number of device.
	 */
	public String getSerial() {
		try {
			return device.getSerialNumberString();
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * Determine report id for the amount of data to be sent
	 *
	 * @return Returns the report id
	 */
	private byte determineReportId(int length) {
		byte reportId = 9;
		//Automatically determine the correct report id to send the data to
		if (length <= 8 * 3) {
			reportId = 6;
		} else if (length <= 16 * 3) {
			reportId = 7;
		} else if (length <= 32 * 3) {
			reportId = 8;
		} else if (length <= 64 * 3) {
			reportId = 9;
		} else if (length <= 128 * 3) {
			reportId = 10;
		}

		return reportId;
	}

	/**
	 * Determine the adjusted maximum amount of LED for the report
	 *
	 * @return Returns the adjusted amount of LED data
	 */
	private byte determineMaxLeds(int length) {
		byte maxLeds = 64;
		//Automatically determine the correct report id to send the data to
		if (length <= 8 * 3) {
			maxLeds = 8;
		} else if (length <= 16 * 3) {
			maxLeds = 16;
		} else if (length <= 32 * 3) {
			maxLeds = 32;
		} else if (length <= 64 * 3) {
			maxLeds = 64;
		} else if (length <= 128 * 3) {
			maxLeds = 64;
		}

		return maxLeds;
	}

	/**
	 * Send a packet of data to LEDs on channel 0 (R)
	 *
	 * @param colorData Report data must be a byte array in the following format: [g0, r0, b0, g1, r1, b1, g2, r2, b2 ...]
	 */
	public void setColors(byte[] colorData) {
		setColors((byte) 0, colorData);
	}

	/**
	 * Send a packet of data to LEDs
	 *
	 * @param channel   Channel (0 - R, 1 - G, 2 - B)
	 * @param colorData Report data must be a byte array in the following format: [g0, r0, b0, g1, r1, b1, g2, r2, b2 ...]
	 */
	public void setColors(int channel, byte[] colorData) {
		setColors((byte) channel, colorData);
	}

	/**
	 * Send a packet of data to LEDs
	 *
	 * @param channel   Channel (0 - R, 1 - G, 2 - B)
	 * @param colorData Report data must be a byte array in the following format: [g0, r0, b0, g1, r1, b1, g2, r2, b2 ...]
	 */
	public void setColors(byte channel, byte[] colorData) {
		byte leds = determineMaxLeds(colorData.length);
		byte[] data = new byte[leds * 3 + 2];

		data[0] = determineReportId(colorData.length);
		data[1] = channel;


		for (int i = 0; i < Math.min(colorData.length, data.length - 2); i++) {
			data[i + 2] = colorData[i];
		}

		for (int i = colorData.length + 2; i < data.length; i++) {
			data[i] = 0;
		}

		try {
			device.sendFeatureReport(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setMode(Mode mode) {
		if (getMode() != mode)
			setMode(mode.asByte());
	}

	/**
	 * Set the mode of BlinkStick Pro as byte
	 *
	 * @param mode 0 - Normal, 1 - Inverse, 2 - WS2812, 3 - WS2812 mirror
	 */
	private void setMode(byte mode) {
		try {
			device.sendFeatureReport(new byte[]{4, mode});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Mode getMode() {
		byte[] data = new byte[2];
		data[0] = 4;// First byte is ReportID

		try {
			int read = device.getFeatureReport(data);
			if (read > 0) {
				return Mode.fromByte(data[1]);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return Unknown;
	}


}

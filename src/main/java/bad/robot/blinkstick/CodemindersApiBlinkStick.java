package bad.robot.blinkstick;

import com.codeminders.hidapi.HIDDevice;

import java.util.Random;

import static bad.robot.blinkstick.Mode.Unknown;
import static bad.robot.blinkstick.ReportId.ModeReportId;

public class CodemindersApiBlinkStick implements BlinkStick {

	private HIDDevice device;

	CodemindersApiBlinkStick(HIDDevice device) {
		this.device = device;
	}


	/**
	 * Set the color of the device with separate r, g and b byte values
	 *
	 * @param r red byte color value 0..255
	 * @param g gree byte color value 0..255
	 * @param b blue byte color value 0..255
	 */
	@Override
	public void setColor(int r, int g, int b) {
		try {
			device.sendFeatureReport(new byte[]{1, (byte) r, (byte) g, (byte) b});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
	@Override
	public void setIndexedColor(int channel, int index, int r, int g, int b) {
		try {
			device.sendFeatureReport(new byte[]{5, (byte) channel, (byte) index, (byte) r, (byte) g, (byte) b});
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
	@Override
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
	@Override
	public void setIndexedColor(int index, Color color) {
		setIndexedColor(0, index, color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Set the color of the device with Processing(?) color value
	 *
	 * @param value color as int
	 */
	// TODO check against Android API
	@Override
	public void setColor(int value) {
		int r = (value >> 16) & 0xFF;
		int g = (value >> 8)  & 0xFF;
		int b =  value        & 0xFF;

		setColor(r, g, b);
	}

	@Override
	public void setColor(Color color) {
		setColor(color.rgb());
	}

	@Override
	public void setRandomColor() {
		Random random = new Random();
		setColor(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}

	@Override
	public void turnOff() {
		setColor(0, 0, 0);
	}


	@Override
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
	@Override
	public String getInfoBlock1() {
		return getInfoBlock(1);
	}

	/**
	 * Get value of InfoBlock2
	 *
	 * @return The value of info block 2
	 */
	@Override
	public String getInfoBlock2() {
		return getInfoBlock(2);
	}

	private void setInfoBlock(int id, String value) {
		if (id < 1 || id > 2) throw new IllegalArgumentException("BlinkStick devices supports setting user strings for InfoBlock 1 or 2 as only");

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

	@Override
	public void setInfoBlock1(String value) {
		setInfoBlock(1, value);
	}

	@Override
	public void setInfoBlock2(String value) {
		setInfoBlock(2, value);
	}

	@Override
	public String getManufacturer() {
		try {
			return device.getManufacturerString();
		} catch (Exception e) {
			return "<unknown manufacturer>";
		}
	}

	@Override
	public String getProductDescription() {
		try {
			return device.getProductString();
		} catch (Exception e) {
			return "<unknown product>";
		}
	}

	@Override
	public String getSerial() {
		try {
			return device.getSerialNumberString();
		} catch (Exception e) {
			return "<unknown serial#>";
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
	@Override
	public void setColors(byte[] colorData) {
		setColors(Channel.R, colorData);
	}

	/**
	 * Send a packet of data to LEDs
	 *
	 * @param channel   Channel (0 - R, 1 - G, 2 - B)
	 * @param colorData Report data must be a byte array in the following format: [g0, r0, b0, g1, r1, b1, g2, r2, b2 ...]
	 */
	@Override
	public void setColors(Channel channel, byte[] colorData) {
		byte leds = determineMaxLeds(colorData.length);
		byte[] data = new byte[leds * 3 + 2];

		data[0] = determineReportId(colorData.length);
		data[1] = channel.asByte();

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

	@Override
	public void setMode(Mode mode) {
		if (getMode() != mode)
			try {
				device.sendFeatureReport(new byte[]{ModeReportId.value(), mode.asByte()});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public Mode getMode() {
		byte[] data = new byte[2];
		data[0] = ModeReportId.value();

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

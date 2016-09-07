package bad.robot.blinkstick;

public class Device {

	final static int BlinkStickVendorId = 0x20a0;
	final static int BlinkStickProductName = 0x41e5;

	private final String manufacturer;
	private final String productName;
	private final String serialNumber;
	private final int vendorId;
	private final int productId;

	public Device(String manufacturer, String productName, String serialNumber, int vendorId, int productId) {
		this.manufacturer = manufacturer;
		this.productName = productName;
		this.serialNumber = serialNumber;
		this.vendorId = vendorId;
		this.productId = productId;
	}

	public boolean isBlinkStick() {
		return vendorId == BlinkStickVendorId && productId == BlinkStickProductName;
	}

	@Override
	public String toString() {
		return String.format("%s %s (#%s)", manufacturer, productName, serialNumber);
	}
}

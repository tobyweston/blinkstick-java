package bad.robot.blinkstick.example;

import static bad.robot.blinkstick.Usb.devices;
import static bad.robot.blinkstick.Usb.findAllBlinkSticks;

public class Usb {
	public static void main(String... args) {
		devices().forEach(device -> System.out.println(device.toString()));
		findAllBlinkSticks().forEach(blinkStick -> System.out.printf("%n%s in mode %s, colour %s%n", blinkStick.getProduct(), blinkStick.getMode(), blinkStick.getColorString()));
	}

}

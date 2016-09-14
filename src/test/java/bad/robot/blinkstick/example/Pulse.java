package bad.robot.blinkstick.example;

import bad.robot.blinkstick.Usb;

import static bad.robot.blinkstick.Colors.Green;
import static bad.robot.blinkstick.Mode.Normal;

public class Pulse {
	public static void main(String... args) {
		Usb.findFirstBlinkStick().map(stick -> {
			stick.setMode(Normal);
			stick.setColor(Green);
			Sleep.sleep(250);
			stick.turnOff();
			return null;
		});
		System.exit(0);
	}
}

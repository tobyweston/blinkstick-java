package bad.robot.blinkstick.example;

import bad.robot.blinkstick.Brightness;
import bad.robot.blinkstick.Usb;

import static bad.robot.blinkstick.Colors.Green;
import static bad.robot.blinkstick.Mode.Normal;
import static bad.robot.blinkstick.Mode.Ws2812Mirror;
import static bad.robot.blinkstick.example.Sleep.sleep;

public class Pulse {
	public static void main(String... args) {
		Usb.findFirstBlinkStick().map(stick -> {
			stick.setMode(Ws2812Mirror);
			stick.setIndexedColor(0, Green);
			for (int percent = 0; percent < 100; percent++) {
//				stick.setColor(new Brightness(-percent).applyTo(Green));
			}
			sleep(500);
			stick.turnOff();
			return null;
		});
		System.exit(0);
	}
}

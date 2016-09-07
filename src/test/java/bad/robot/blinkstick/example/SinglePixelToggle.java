package bad.robot.blinkstick.example;

import bad.robot.blinkstick.Color;
import bad.robot.blinkstick.Usb;

import static bad.robot.blinkstick.Color.Black;
import static bad.robot.blinkstick.Color.Green;
import static bad.robot.blinkstick.Mode.Ws2812;

public class SinglePixelToggle {

	public static void main(String... args) {
		Usb.findFirstBlinkStick().map(stick -> {
			stick.setMode(Ws2812);
			stick.setIndexedColor(0, Color.random());
			Sleep.sleep(500);
			stick.setIndexedColor(1, Color.random());
			Sleep.sleep(500);
			stick.setIndexedColor(0, Black);
			stick.setIndexedColor(1, Black);
			return null;
		});
		System.exit(0);

	}
}

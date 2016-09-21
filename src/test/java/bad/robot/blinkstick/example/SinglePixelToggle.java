package bad.robot.blinkstick.example;

import bad.robot.blinkstick.Colors;
import bad.robot.blinkstick.Usb;

import static bad.robot.blinkstick.Colors.Black;
import static bad.robot.blinkstick.Mode.Ws2812;

public class SinglePixelToggle {

	public static void main(String... args) {
		Usb.findFirstBlinkStick().map(stick -> {
			stick.setMode(Ws2812);
			stick.setIndexedColor(0, Colors.Aqua);
			stick.setIndexedColor(1, Colors.Green);
			stick.setIndexedColor(0, Black);
			stick.setIndexedColor(1, Black);
			return null;
		});
		System.exit(0);

	}
}

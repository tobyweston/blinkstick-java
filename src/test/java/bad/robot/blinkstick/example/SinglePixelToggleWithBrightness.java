package bad.robot.blinkstick.example;

import bad.robot.blinkstick.Brightness;
import bad.robot.blinkstick.Colors;
import bad.robot.blinkstick.Usb;

import static bad.robot.blinkstick.Colors.*;
import static bad.robot.blinkstick.Colors.Black;
import static bad.robot.blinkstick.Mode.Ws2812;

public class SinglePixelToggleWithBrightness {

	public static void main(String... args) {
		Usb.findFirstBlinkStick().map(stick -> {
			stick.setMode(Ws2812);
			for (int i = 0; i < 8; i++) {
				stick.setIndexedColor(i, Green);
				stick.setIndexedColor(i, new Brightness(-15).applyTo(Green));
				stick.setIndexedColor(i, new Brightness(-25).applyTo(Green));
				stick.setIndexedColor(i, new Brightness(-50).applyTo(Green));
				stick.setIndexedColor(i, new Brightness(-100).applyTo(Green));
			}
			return null;
		});
		System.exit(0);
	}
}

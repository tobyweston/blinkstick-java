package bad.robot.blinkstick.example;

import bad.robot.blinkstick.Usb;

import static bad.robot.blinkstick.Colors.Black;
import static bad.robot.blinkstick.Colors.Green;
import static bad.robot.blinkstick.Colors.Red;
import static bad.robot.blinkstick.Mode.*;

public class AllPixelsToggle {

	public static void main(String... args) {
		Usb.findFirstBlinkStick().map(stick -> {
			stick.setMode(Normal);
			stick.setColor(Red);
			Sleep.sleep(200);
			stick.setColor(Black);
			Sleep.sleep(200);
			return null;
		});
		System.exit(0);
	}
}

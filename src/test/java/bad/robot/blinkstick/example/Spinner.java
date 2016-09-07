package bad.robot.blinkstick.example;

import bad.robot.blinkstick.BlinkStick;
import bad.robot.blinkstick.Color;

import java.util.stream.IntStream;

import static bad.robot.blinkstick.Color.Black;
import static bad.robot.blinkstick.Color.Red;
import static bad.robot.blinkstick.Usb.findFirstBlinkStick;

public class Spinner {

	public static void main(String... args) {
		findFirstBlinkStick().map(blinkStick -> {
//			blinkStick.setMode(2);
//			blinkStick.setColor("red");
			IntStream.range(1, 5).forEach(x -> {
				IntStream.range(0, 8).forEach(index -> {
					onAndOffAgain(blinkStick, Red, index);
				});
			});
			return blinkStick;
		});
		Sleep.sleep(1500);
		System.exit(0);
	}

	private static void onAndOffAgain(BlinkStick blinkStick, Color color, int index) {
		blinkStick.setIndexedColor(index, color.rgb());
		Sleep.sleep(100);
		blinkStick.setIndexedColor(index, Black.rgb());
	}

}

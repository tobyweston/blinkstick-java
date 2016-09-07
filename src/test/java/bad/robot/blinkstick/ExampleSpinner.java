package bad.robot.blinkstick;

import java.util.stream.IntStream;

import static bad.robot.blinkstick.BlinkStick.*;
import static bad.robot.blinkstick.BlinkStick.COLORS;

public class ExampleSpinner {

	public static void main(String... args) {
		System.out.printf("loaded %s%n", loadLibrary());
		findFirst().map(blinkStick -> {
//			blinkStick.setMode(2);
//			blinkStick.setColor("red");
			int red = hex2Rgb(COLORS.get("red"));

			IntStream.range(1, 5).forEach(x -> {
				IntStream.range(0, 8).forEach(index -> {
					onAndOffAgain(blinkStick, red, index);
				});
			});
			return blinkStick;
		});
		sleep(1500);
		System.exit(0);
	}

	private static void onAndOffAgain(BlinkStick blinkStick, int colour, int index) {
		blinkStick.setIndexedColor(index, colour);
		sleep(100);
		blinkStick.setIndexedColor(index, hex2Rgb(COLORS.get("black")));
	}

	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

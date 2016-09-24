package bad.robot.blinkstick.example;

import java.util.List;
import java.util.stream.Collectors;

import static bad.robot.blinkstick.Usb.findAllBlinkSticks;
import static java.lang.String.*;

public class BlinkStickInfo {
	public static void main(String... args) {
		List<String> infos = findAllBlinkSticks().stream().map(stick -> {
			StringBuilder info = new StringBuilder();
			info.append(format("Product      : %s%n", stick.getProductDescription()));
			info.append(format("Serial #     : %s%n", stick.getSerial()));
			info.append(format("Manufacturer : %s%n", stick.getManufacturer()));
			info.append(format("Mode         : %s%n", stick.getMode()));
			info.append(format("Info Block 1 : %s%n", stick.getInfoBlock1()));
			info.append(format("Info Block 2 : %s%n", stick.getInfoBlock2()));
			return info.toString();
		}).collect(Collectors.toList());

		infos.forEach(System.out::println);

		if (infos.isEmpty())
			System.out.println("No BlinkStick(s) found");

		System.exit(0);
	}
}

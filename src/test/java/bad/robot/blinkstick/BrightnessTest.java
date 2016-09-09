package bad.robot.blinkstick;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class BrightnessTest {

	@Test
	public void brightnessRange() {
		assertThat(new Brightness(-1), is(equalTo(new Brightness(0))));
		assertThat(new Brightness(256), is(equalTo(new Brightness(255))));
		assertThat(new Brightness(16), is(equalTo(new Brightness(16))));
	}

}
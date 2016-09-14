package bad.robot.blinkstick;

import org.junit.Test;

import static bad.robot.blinkstick.Brightness.Max;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class BrightnessTest {

	@Test
	public void brightnessRange() {
		assertThat(new Brightness(-1), is(equalTo(new Brightness(0))));
		assertThat(new Brightness(256), is(equalTo(new Brightness(255))));
		assertThat(new Brightness(16), is(equalTo(new Brightness(16))));
	}

	@Test
	public void maxBrightness() {
		assertThat(new Brightness(0).max(), is(false));
		assertThat(new Brightness(16).max(), is(false));
		assertThat(new Brightness(254).max(), is(false));
		assertThat(new Brightness(255).max(), is(true));
	}

	@Test
	public void applyTheBrightnessValueToAColor() {
		Color darkerRed = new Color("#C20000");
		assertThat(new Brightness(125).applyTo(darkerRed).toString(), is("#5E0000"));
		assertThat(new Brightness(0).applyTo(darkerRed).toString(), is("#000000"));
		assertThat(new Brightness(254).applyTo(darkerRed).toString(), is("#C10000"));

		Color brightRed = new Color("#FF0000");
		assertThat(new Brightness(0).applyTo(brightRed).toString(), is("#000000"));
		assertThat(new Brightness(50).applyTo(brightRed).toString(), is("#310000"));
		assertThat(new Brightness(254).applyTo(brightRed).toString(), is("#FE0000"));
		assertThat(new Brightness(255).applyTo(brightRed).toString(), is("#FF0000"));

		Color midBlue = new Color("#356C91");
		assertThat(new Brightness(0).applyTo(midBlue).toString(), is("#000000"));
		assertThat(new Brightness(1).applyTo(midBlue).toString(), is("#000000"));
		assertThat(new Brightness(50).applyTo(midBlue).toString(), is("#0A151C"));
		assertThat(new Brightness(125).applyTo(midBlue).toString(), is("#193446"));
		assertThat("wtf?", new Brightness(254).applyTo(midBlue).toString(), is("#346B90"));
		assertThat("wtf?", new Brightness(255).applyTo(midBlue).toString(), is("#356C91"));
	}

	@Test
	public void applyTheMaxBrightness() {
		Color red   = new Color("#FF0000");
		Color green = new Color("#00FF00");
		Color blue  = new Color("#0000FF");

		verify(red);
		verify(green);
		verify(blue);
	}

	private static void verify(Color color) {
		assertThat("verify method requires a maximally bright color", color.maximumBrightness(), is(true));
		Color darkened = darkenUsingJavaColor(color);
		assertThat("maximising brightness on an already maximal color failed", Max.applyTo(color), is(color));
		assertThat(String.format("did not brighten %s to %s", darkened, color), Max.applyTo(darkened), is(color));
	}

	private static Color darkenUsingJavaColor(Color color) {
		java.awt.Color darker = new java.awt.Color(color.rgb()).darker();
		return new Color(darker.getRed(), darker.getGreen(), darker.getBlue());
	}
}
package bad.robot.blinkstick;

import org.junit.Ignore;
import org.junit.Test;


import static bad.robot.blinkstick.Colors.Blue;
import static bad.robot.blinkstick.Colors.Green;
import static bad.robot.blinkstick.Colors.Red;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ColorTest {

	@Test
	public void getRgb() {
		assertThat(Red.rgb(), is(java.awt.Color.RED.getRGB()));
		assertThat(Red.rgb(), is(-65536));
	}

	@Test
	public void getRedGreenBlue() {
		assertThat(Red.getRed(), is(255));
		assertThat(Red.getGreen(), is(0));
		assertThat(Red.getBlue(), is(0));

		Color orange = new Color("#FFC800");
		assertThat(orange.getRed(), is(255));
		assertThat(orange.getGreen(), is(200));
		assertThat(orange.getBlue(), is(0));
	}

	@Test
	public void hexValueWhenToString() {
		assertThat(Red.toString(), is("#FF0000"));
	}

	@Test
	public void quickAndDirtyEqualityTest() {
		assertThat(Red, is(new Color("#FF0000")));
	}

	@Test
	public void brighten() {
		Color color = new Color("#2F4F4F");
		assertThat(color.lighten(0.7).rgb(), is(new java.awt.Color(color.rgb()).brighter().getRGB()));
//		assertThat(color.lighten(0.1).toString(), is("#000000"));
//		assertThat(color.lighten(0.9).toString(), is("#437070"));
//		assertThat(color.lighten(1.0).toString(), is("#FFFFFF")); // white
//		assertThat(color.lighten(0.7).toString(), is("#437070"));

		Color darkRed = new Color("#3C0000");
		assertThat(darkRed.lighten(1.0).toString(), is("#FF0000")); // lighten (max)

		assertThat(darkRed.lighten(0.9).toString(), is("#420000")); // darker
		assertThat(darkRed.lighten(0.7).toString(), is("#550000"));
		assertThat(darkRed.lighten(0.5).toString(), is("#780000")); // ~50%
		assertThat(darkRed.lighten(0.3).toString(), is("#C80000"));
		assertThat(darkRed.lighten(0.0).toString(), is("#FF0000")); // lighter

		assertThat(new Color("#110000").lighten(0.5).toString(), is("#220000"));
		assertThat(new Color("#110000").lighten(0.7).toString(), is("#180000"));
	}

	@Test
	public void darken() {
		Color brightRed = new Color("#FF0000");
		assertThat(brightRed.darken(0.7).rgb(), is(new java.awt.Color(brightRed.rgb()).darker().getRGB()));

		assertThat(brightRed.darken(1.0).toString(), is("#FF0000")); // lighten (max) !!!

		assertThat(brightRed.darken(0.9).toString(), is("#E50000")); // lighter
		assertThat(brightRed.darken(0.7).toString(), is("#B20000"));
		assertThat(brightRed.darken(0.5).toString(), is("#7F0000"));
		assertThat(brightRed.darken(0.1).toString(), is("#190000"));
		assertThat(brightRed.darken(0.0).toString(), is("#000000")); // darker
	}

}
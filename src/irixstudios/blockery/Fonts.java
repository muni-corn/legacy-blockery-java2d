package irixstudios.blockery;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class Fonts {

	public static Font PIRULEN;
	public static Font NEWCICLESEMI;
	public static Font SFDR;

	private Runner runner;

	public Fonts(Runner runner) {
		this.runner = runner;
	}

	public void loadFonts() {
		try {
			PIRULEN = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("pirulen.ttf"));
			NEWCICLESEMI = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("New_Cicle_Semi.ttf"));
			SFDR = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("SF Digital Readout Heavy.ttf"));
		} catch (FontFormatException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Problem loading fonts, using Helvetica instead.";
		} catch (IOException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Missing fonts, Java will use Helvetica instead.";

		}
	}
}
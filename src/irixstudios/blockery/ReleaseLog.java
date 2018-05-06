package irixstudios.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class ReleaseLog {

	private Runner runner;

	int yesNoHighlight = 2;

	public boolean nothingAnimated = true;
	int wordsY;

	String line1version;
	String line2 = "";
	String line3 = "- In case there was no difference in fonts before, there should be now :)";
	String line4 = "- ''Until affordable'' timers in Endless are cleaner looking, slightly more accurate, and are now in Environment too";
	String line5 = "- Buttons will highlight when you hover over them";
	String line6 = "- Tweaked block animations";
	String line7 = "- Can now disable initializing audio and all of the other \"fancy\" stuff";
	String line8 = "- NotiBar will reset its standing time if a new notification appears while there's already one being shown";
	String line9 = "- Added some fancy CO2 clouds";
	String line10 = "- Added floating numbers to collected blocks";
	String line11 = "- Can now report bugs or visit Blockery's website (mainly for checking for updates) from links within the game";
	String line12 = "";
	String line13 = "";
	String line14 = "";
	String line15 = "";
	String line16 = "";
	String line17 = "";
	String line18 = "";

	String copyrightState = "Copyright 2013, IrixStudios";

	public ReleaseLog(Runner runner) {
		this.runner = runner;
		line1version = "Blockery " + runner.version;
	}

	public void yesNoButton(int xx, int yy) {
		if (xx > 448 && xx < 513 && yy > 390 && yy < 420) {
			runner.playingEnd = false;
			runner.playingOpt = false;
			runner.playingEnv = false;
			runner.viewingLog = false;
			runner.onMenu = true;
			nothingAnimated = true;
			playAudio();
			if (runner.soundOn) {
				Audio.CANCEL.play();
			}
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
		}
	}

	public void playAudio() {
		if (runner.playingOpt) {
			if (runner.musicOn) {
				Audio.RLOG.loop();
			}
		} else {
			Audio.RLOG.stop();
			runner.noAudioOpt = true;
		}
	}

	public void paint(Graphics2D g) {

		if (nothingAnimated) {
			wordsY = 150;
			nothingAnimated = false;
		}
		if (wordsY > 40) {
			wordsY = (wordsY + 40) / 2;
		}

		FontMetrics FM;
		int FMSW;

		g.setColor(new Color(235, 235, 239));
		g.fillRect(0, 0, runner.frame.getWidth(), 568);

		g.setColor(new Color(125, 209, 213));
		g.fillRect((runner.frame.getWidth() / 2) - 35, 390, 70, 30);
		g.setColor(Color.WHITE);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 15f));
		FM = g.getFontMetrics();
		g.drawString("OK", 472, 411);

		g.setColor(new Color(187, 187, 187));

		FMSW = FM.stringWidth(line1version);
		g.drawString(line1version, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY);

		FMSW = FM.stringWidth(line2);
		g.drawString(line2, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 20);

		FMSW = FM.stringWidth(line3);
		g.drawString(line3, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 40);

		FMSW = FM.stringWidth(line4);
		g.drawString(line4, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 60);

		FMSW = FM.stringWidth(line5);
		g.drawString(line5, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 80);

		FMSW = FM.stringWidth(line6);
		g.drawString(line6, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 100);

		FMSW = FM.stringWidth(line7);
		g.drawString(line7, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 120);

		FMSW = FM.stringWidth(line8);
		g.drawString(line8, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 140);

		FMSW = FM.stringWidth(line9);
		g.drawString(line9, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 160);

		FMSW = FM.stringWidth(line10);
		g.drawString(line10, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 180);

		FMSW = FM.stringWidth(line11);
		g.drawString(line11, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 200);

		FMSW = FM.stringWidth(line12);
		g.drawString(line12, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 220);

		FMSW = FM.stringWidth(line13);
		g.drawString(line13, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 240);

		FMSW = FM.stringWidth(line14);
		g.drawString(line14, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 260);

		FMSW = FM.stringWidth(line15);
		g.drawString(line15, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 280);

		FMSW = FM.stringWidth(line16);
		g.drawString(line16, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 300);

		FMSW = FM.stringWidth(line17);
		g.drawString(line17, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 320);

		FMSW = FM.stringWidth(line18);
		g.drawString(line18, (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY + 340);

		FMSW = FM.stringWidth(copyrightState);
		g.drawString(copyrightState, (runner.frame.getWidth() / 2) - (FMSW / 2), 470);

		g.setColor(new Color(255, 255, 255, 50));

		switch (yesNoHighlight) {
		case 0:
			g.fillRect((runner.frame.getWidth() / 2) - 35, 350, 70, 30);
			break;
		case 1:
			g.fillRect((runner.frame.getWidth() / 2) - 35, 390, 70, 30);
			break;
		}
	}

}

package irixstudios.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Options {

	private Runner runner;
	public boolean nothingAnimated = true;
	int wordsY;

	Color soundColor;
	Color musicColor;

	int highlight = -1;
	int yesNoHighlight = -1;
	int switchesHighlight = -1;

	public Options(Runner runner) {
		this.runner = runner;
	}

	public void yesNoButton(int xx, int yy) {
		if (xx >= 448 && xx <= 518 && yy >= 390 && yy <= 420) {
			runner.optfr.saveFiles();
			runner.playingEnd = false;
			runner.playingOpt = false;
			runner.playingEnv = false;
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
				Audio.OPTIONS.loop();
			}
		} else {
			Audio.OPTIONS.stop();
			runner.noAudioOpt = true;
		}
	}

	public void options(int xx, int yy) {

		if (xx >= 283 && yy >= 175 && xx <= 483 && yy <= 210 && runner.soundOn == false) {
			runner.soundOn = true;
			Audio.SOUNDON.play();
		} else
			if (xx >= 283 && yy >= 175 && xx <= 483 && yy <= 210 && runner.soundOn) {
				runner.soundOn = false;
			}

		if (xx >= 283 && yy >= 225 && xx <= 483 && yy <= 260 && runner.musicOn == false) {
			runner.musicOn = true;
			playAudio();
		} else
			if (xx >= 283 && yy >= 225 && xx <= 483 && yy <= 260 && runner.musicOn) {
				runner.musicOn = false;
				Audio.OPTIONS.stop();
			}
		///////////////////////////////////////////////////////////////////////////////////////
		if (xx >= 488 && xx <= 504 && yy >= 175 && yy <= 191 && runner.doInitialize == false) {
			runner.doInitialize = true;
			if (runner.soundOn) {
				Audio.USEOPT.play();
			}
		} else
			if (xx >= 488 && xx <= 504 && yy >= 175 && yy <= 191 && runner.doInitialize) {
				runner.doInitialize = false;
				if (runner.soundOn) {
					Audio.IGNOREOPT.play();
				}
			}
		///////////////////////////////////////////////////////////////////////////////////////
		if (xx >= 488 && xx <= 504 && yy >= 205 && yy <= 221 && runner.useTimers == false) {
			runner.useTimers = true;
			if (runner.soundOn) {
				Audio.USEOPT.play();
			}
		} else
			if (xx >= 488 && xx <= 504 && yy >= 205 && yy <= 221 && runner.useTimers) {
				runner.useTimers = false;
				if (runner.soundOn) {
					Audio.IGNOREOPT.play();
				}
			}
		///////////////////////////////////////////////////////////////////////////////////////
		if (xx >= 488 && xx <= 504 && yy >= 235 && yy <= 251 && runner.showClouds == false) {
			runner.showClouds = true;
			if (runner.soundOn) {
				Audio.USEOPT.play();
			}
		} else
			if (xx >= 488 && xx <= 504 && yy >= 235 && yy <= 251 && runner.showClouds) {
				runner.showClouds = false;
				if (runner.soundOn) {
					Audio.IGNOREOPT.play();
				}
			}
		///////////////////////////////////////////////////////////////////////////////////////
		if (xx >= 488 && xx <= 504 && yy >= 265 && yy <= 281 && runner.showNums == false) {
			runner.showNums = true;
			if (runner.soundOn) {
				Audio.USEOPT.play();
			}
		} else
			if (xx >= 488 && xx <= 504 && yy >= 265 && yy <= 281 && runner.showNums) {
				runner.showNums = false;
				if (runner.soundOn) {
					Audio.IGNOREOPT.play();
				}
			}
		///////////////////////////////////////////////////////////////////////////////////////
		if (xx >= 488 && xx <= 504 && yy >= 295 && yy <= 311 && runner.useNotiBar == false) {
			runner.useNotiBar = true;
			if (runner.soundOn) {
				Audio.USEOPT.play();
			}
		} else
			if (xx >= 488 && xx <= 504 && yy >= 295 && yy <= 311 && runner.useNotiBar) {
				runner.useNotiBar = false;
				if (runner.soundOn) {
					Audio.IGNOREOPT.play();
				}
			}

		if (xx >= 283 && xx <= 483 && yy >= 302 && yy <= 329) {
			Audio.OPTIONS.stop();
			if (runner.soundOn) {
				Audio.REALLY.play();
			}
			runner.playingOpt = false;
			runner.wipingHighs = true;
			nothingAnimated = true;
		}
	}

	public void paint(Graphics2D g) {

		FontMetrics FM;
		int FMSW;

		if (nothingAnimated) {
			wordsY = 50;
			nothingAnimated = false;
		}
		if (wordsY < 120) {
			wordsY = (wordsY + 120) / 2;
		}

		if (runner.soundOn) {
			soundColor = new Color(80, 236, 140);
		} else
			if (runner.soundOn == false) {
				soundColor = new Color(225, 220, 220);
			}
		if (runner.musicOn) {
			musicColor = new Color(80, 236, 140);
		} else
			if (runner.musicOn == false) {
				musicColor = new Color(225, 220, 220);
			}

		g.setColor(new Color(235, 235, 239));
		g.fillRect(0, 0, runner.frame.getWidth(), 568);

		g.setColor(new Color(125, 209, 213));
		g.fillRect((runner.frame.getWidth() / 2) - 35, 390, 70, 30);
		g.setColor(Color.WHITE);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 15f));
		FM = g.getFontMetrics();
		g.drawString("OK", 472, 411);

		g.setColor(soundColor);
		g.fillRect(283, 175, 200, 35);
		g.setColor(musicColor);
		g.fillRect(283, 225, 200, 35);
		g.setColor(new Color(80, 236, 140));
		g.fillRect(283, 302, 200, 27);

		g.setColor(new Color(187, 187, 187));
		FMSW = FM.stringWidth("Initialize audio on startup");
		g.drawString("Initialize audio on startup", 508, 188);
		g.fillRect(488, 175, 16, 16);
		FMSW = FM.stringWidth("''Until affordable'' timers");
		g.drawString("''Until affordable'' timers", 508, 218);
		g.fillRect(488, 205, 16, 16);
		FMSW = FM.stringWidth("CO2 clouds");
		g.drawString("CO2 clouds", 508, 248);
		g.fillRect(488, 235, 16, 16);
		FMSW = FM.stringWidth("Floating numbers");
		g.drawString("Floating numbers", 508, 278);
		g.fillRect(488, 265, 16, 16);
		FMSW = FM.stringWidth("Notification bar");
		g.drawString("Notification bar", 508, 308);
		g.fillRect(488, 295, 16, 16);

		g.setColor(new Color(80, 236, 140));
		if (runner.doInitialize) {
			g.fillRect(491, 178, 10, 10);
		}
		if (runner.useTimers) {
			g.fillRect(491, 208, 10, 10);
		}
		if (runner.showClouds) {
			g.fillRect(491, 238, 10, 10);
		}
		if (runner.showNums) {
			g.fillRect(491, 268, 10, 10);
		}
		if (runner.useNotiBar) {
			g.fillRect(491, 298, 10, 10);
		}

		g.setColor(new Color(187, 187, 187));
		FMSW = FM.stringWidth("Settings");
		g.drawString("Settings", (runner.frame.getWidth() / 2) - (FMSW / 2), wordsY);

		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 30f));
		FM = g.getFontMetrics();
		g.setColor(new Color(235, 235, 239));
		FMSW = FM.stringWidth("Sound");
		g.drawString("Sound", (383) - (FMSW / 2), 204);
		FMSW = FM.stringWidth("Music");
		g.drawString("Music", (383) - (FMSW / 2), 254);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 20f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("Reset High Scores");
		g.drawString("Reset High Scores", (383) - (FMSW / 2), 323);

		g.setColor(new Color(255, 255, 255, 50));
		switch (highlight) {
		case 0:
			g.fillRect(283, 175, 200, 35);
			break;
		case 1:
			g.fillRect(283, 225, 200, 35);
			break;
		case 2:
			g.fillRect(283, 302, 200, 27);
			break;
		}
		switch (switchesHighlight) {
		case 0:
			g.fillRect(488, 175, 16, 16);
			break;
		case 1:
			g.fillRect(488, 205, 16, 16);
			break;
		case 2:
			g.fillRect(488, 235, 16, 16);
			break;
		case 3:
			g.fillRect(488, 265, 16, 16);
			break;
		case 4:
			g.fillRect(488, 295, 16, 16);
			break;
		}
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

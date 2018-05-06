package irixstudios.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class NotiBar {

	int yPos = -25;
	int timer;

	String errorMess = "(unknown error)";

	boolean newNoti = false;
	boolean setAtStart = true;

	boolean envHasSaved = false;
	boolean envHasReset = false;
	boolean endHasSaved = false;
	boolean endHasReset = false;
	boolean highScoresReset = false;
	boolean setsSaved = false;
	boolean readFailed = false;

	private Runner runner;

	public NotiBar(Runner runner) {
		this.runner = runner;
	}

	//The notification bar will display a black bar at the top of the screen when a game or other aspect is saved or modified.

	public void paint(Graphics2D g) {
		if (runner.useNotiBar) {
			String notification = "";

			g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 18f));
			FontMetrics FM = g.getFontMetrics();
			int FMSW;

			if (readFailed) {
				notification = errorMess;
			} else
				if (envHasSaved) {
					notification = "Environment progress saved.";
				} else
					if (endHasSaved) {
						notification = "Endless progress saved.";
					} else
						if (envHasReset) {
							notification = "Environment progress reset.";
						} else
							if (endHasReset) {
								notification = "Endless progress reset.";
							} else
								if (setsSaved) {
									notification = "Preferences saved.";
								} else
									if (highScoresReset) {
										notification = "High scores reset.";
									} else {
										notification = "(unknown)";
									}

			if (newNoti) {
				if (setAtStart) {

					timer = 0;
					setAtStart = false;
				}
				if (yPos < 0) {
					yPos += 10;
					if (yPos > 0) {
						yPos = 0;
					}

				}
				timer++;
				if (timer >= 80) {
					newNoti = false;

				}
			}
			if (!newNoti && yPos > -25) {
				yPos -= 10;
				if (yPos < -25) {
					yPos = -25;
					envHasSaved = false;
					envHasReset = false;
					endHasSaved = false;
					endHasReset = false;
					highScoresReset = false;
					setsSaved = false;
					readFailed = false;
				}
				setAtStart = true;
			}

			if (readFailed) {
				g.setColor(new Color(170, 0, 0, 200));
			} else
				if (runner.playingEnv || runner.playingEnd) {
					g.setColor(new Color(0, 0, 0, 10));
				} else {
					g.setColor(new Color(0, 0, 0, 200));
				}

			g.fillRect(0, yPos, 966, 25);

			g.setColor(Color.WHITE);

			FMSW = FM.stringWidth(notification);
			g.drawString(notification, (966 / 2) - (FMSW / 2), yPos + 19);
		}
	}
}

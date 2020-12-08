package musicaflight.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Title {

	public boolean initialize = false;
	public boolean playAudio = true;
	int alpha = 255;
	int advanceIncre = 1;
	int initY = 420;

	public void paint(Graphics2D g) {

		FontMetrics FM;
		int FMSW;

		if (initialize && playAudio) {
			Audio.TUNE.play();
			playAudio = false;
		}
		g.setColor(new Color(235, 235, 239));
		g.fillRect(0, 0, 966, 568);
		g.setColor(new Color(187, 187, 187));
		g.setFont(Fonts.PIRULEN.deriveFont(Font.PLAIN, 50f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("Harrison Thorne");
		g.drawString("Harrison Thorne", (966 / 2) - (FMSW / 2), (568 / 2));
		if (initialize) {
			alpha += advanceIncre;
			if (alpha >= 255) {
				advanceIncre = -10;
			} else
				if (alpha <= 50) {
					advanceIncre = 10;
				}
			if (alpha > 255) {
				alpha = 255;
			}
			if (initY > 400) {
				initY = (initY + 400) / 2;
			}
			g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 15f));
			g.setColor(new Color(187, 187, 187, alpha));
			FM = g.getFontMetrics();
			FMSW = FM.stringWidth("Initializing audio...");
			g.drawString("Initializing audio...", (966 / 2) - (FMSW / 2), initY);

		}
	}
}

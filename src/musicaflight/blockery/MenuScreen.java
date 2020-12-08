package musicaflight.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class MenuScreen {

	boolean titleNotShown = true;

	Images imgs = new Images();
	boolean audioOffMain = true;
	public Runner runner;
	public boolean nothingAnim = true;

	int logoY;
	int envOptY;
	int endOptY;
	int setOptY;
	int quitOptY;

	int highlight = -1;

	boolean highlightEnvX;
	boolean highlightEndX;

	public MenuScreen(Runner runner) {
		this.runner = runner;
	}

	void go() {
		if (audioOffMain) {
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
			audioOffMain = false;
		}
	}

	public void paint(Graphics2D g) {

		FontMetrics FM;
		int FMSW;

		g.setColor(new Color(235, 235, 239));
		g.fillRect(0, 0, runner.frame.getWidth(), 568);
		if (nothingAnim) {
			logoY = -250;
			envOptY = 500;
			endOptY = 1000;
			setOptY = 2000;
			quitOptY = 4000;
			nothingAnim = false;
		}
		if (logoY < 204) {
			logoY = (logoY + 159) / 2;
			g.drawImage(imgs.blockeryLogo, (runner.frame.getWidth() / 2) - (390 / 2), logoY, null);
		}
		if (envOptY > 219) {
			envOptY = (envOptY + 219) / 2;
		}
		if (endOptY > 259) {
			endOptY = (endOptY + 259) / 2;
		}
		if (setOptY > 299) {
			setOptY = (setOptY + 299) / 2;
		}
		if (quitOptY > 339) {
			quitOptY = (quitOptY + 339) / 2;
		}

		g.setColor(new Color(17, 124, 255, 50));
		g.fillRect(0, envOptY, runner.frame.getWidth(), 30);
		g.setColor(new Color(80, 236, 140, 50));
		g.fillRect(0, endOptY, runner.frame.getWidth(), 30);
		g.setColor(new Color(255, 165, 48, 50));
		g.fillRect(0, setOptY, runner.frame.getWidth(), 30);
		g.setColor(new Color(255, 0, 76, 50));
		g.fillRect(0, quitOptY, runner.frame.getWidth(), 30);

		g.setColor(new Color(17, 124, 255));
		g.fillRect(388, envOptY, 190, 30);
		g.setColor(new Color(80, 236, 140));
		g.fillRect(388, endOptY, 190, 30);
		g.setColor(new Color(255, 165, 48));
		g.fillRect(388, setOptY, 190, 30);
		g.setColor(new Color(255, 0, 76));
		g.fillRect(388, quitOptY, 190, 30);

		g.setPaint(new GradientPaint(runner.frame.getWidth() / 2, 0, new Color(255, 255, 255, 125), 0, 0, new Color(235, 235, 238, 0), true));

		switch (highlight) {
		case 0:
			g.fillRect(0, envOptY, runner.frame.getWidth(), 30);
			break;
		case 1:
			g.fillRect(0, endOptY, runner.frame.getWidth(), 30);
			break;
		case 2:
			g.fillRect(0, setOptY, runner.frame.getWidth(), 30);
			break;
		case 3:
			g.fillRect(0, quitOptY, runner.frame.getWidth(), 30);
			break;
		case 4:
			g.fillRect(0, 419, runner.frame.getWidth(), 30);
			break;
		case 7:
			g.fillRect(0, 459, runner.frame.getWidth(), 30);
			break;
		case 8:
			g.fillRect(0, 499, runner.frame.getWidth(), 30);
			break;
		}

		g.setColor(new Color(255, 255, 255, 50));
		g.fillRect(548, envOptY, 30, 30);
		g.fillRect(548, endOptY, 30, 30);

		g.drawImage(imgs.x, 548, 219, null);
		g.drawImage(imgs.x, 548, 259, null);

		switch (highlight) {
		case 5:
			g.setColor(new Color(255, 255, 255, 50));
			g.fillRect(548, 219, 30, 30);
			break;
		case 6:
			g.setColor(new Color(255, 255, 255, 50));
			g.fillRect(548, 259, 30, 30);
			break;
		}

		g.setColor(Color.WHITE);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 20f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("Environment");
		g.drawString("Environment", (runner.frame.getWidth() / 2) - (FMSW / 2), 241);
		FMSW = FM.stringWidth("Endless");
		g.drawString("Endless", (runner.frame.getWidth() / 2) - (FMSW / 2), 281);
		FMSW = FM.stringWidth("Settings");
		g.drawString("Settings", (runner.frame.getWidth() / 2) - (FMSW / 2), 321);
		FMSW = FM.stringWidth("Exit");
		g.drawString("Exit", (runner.frame.getWidth() / 2) - (FMSW / 2), 361);

		g.setColor(new Color(187, 187, 187));
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 18f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("v " + runner.version);
		g.drawString("v " + runner.version, (runner.frame.getWidth() / 2) - (FMSW / 2), 441);
		FMSW = FM.stringWidth("Visit Blockery's Website");
		g.drawString("Visit Blockery's Website", (runner.frame.getWidth() / 2) - (FMSW / 2), 481);
		FMSW = FM.stringWidth("Report a Problem or Suggestion");
		g.drawString("Report a Problem or Suggestion", (runner.frame.getWidth() / 2) - (FMSW / 2), 521);

	}
}

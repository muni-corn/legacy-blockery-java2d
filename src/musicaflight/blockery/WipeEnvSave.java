package musicaflight.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class WipeEnvSave {

	private Runner runner;
	boolean wipingSaveAudio = false;
	boolean needToCheckAudio = true;
	
	int yesNoHighlight = 2;

	public WipeEnvSave(Runner runner) {
		this.runner = runner;
	}

	public void reallyWipeSave(int xx, int yy) {
		if (xx > 448 && xx < 513 && yy > 350 && yy < 380) {
			runner.playingEnd = false;
			runner.playingOpt = false;
			runner.playingEnv = false;
			runner.wipingEnvSave = false;
			runner.wipingEndSave = false;
			runner.onMenu = true;
			if (runner.soundOn) {
				Audio.CLICK.play();
			}
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
		}
		if (xx > 448 && xx < 513 && yy > 390 && yy < 420) {
			runner.envfr.resetFiles();
			runner.playingEnd = false;
			runner.playingOpt = false;
			runner.playingEnv = false;
			runner.wipingEnvSave = false;
			runner.wipingEndSave = false;
			runner.onMenu = true;
			if (runner.soundOn) {
				Audio.OHWELL.play();
			}
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
		}
	}

	public void paint(Graphics2D g) {
		g.setColor(new Color(235, 235, 239));
		g.fillRect(0, 0, 966, 568);

		g.setColor(new Color(187, 187, 187));
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 15f));
		FontMetrics FM = g.getFontMetrics();
		int FMSW = FM.stringWidth("REALLY TRASH ENVIRONMENT PROGRESS?");
		g.drawString("REALLY TRASH ENVIRONMENT PROGRESS?", (966 / 2) - (FMSW / 2), 230);
		FMSW = FM.stringWidth("This can't be undone!");
		g.drawString("This can't be undone!", (966 / 2) - (FMSW / 2), 270);

		g.setColor(new Color(125, 209, 213));
		g.fillRect((966 / 2) - 35, 350, 70, 30);
		g.setColor(new Color(232, 115, 121));
		g.fillRect((966 / 2) - 35, 390, 70, 30);
		g.setColor(Color.WHITE);
		FMSW = FM.stringWidth("No");
		g.drawString("No", (966 / 2) - (FMSW / 2), 371);
		FMSW = FM.stringWidth("Sure");
		g.drawString("Sure", (966 / 2) - (FMSW / 2), 411);

		g.setColor(new Color(255, 255, 255, 50));

		switch (yesNoHighlight) {
		case 0:
			g.fillRect((966 / 2) - 35, 350, 70, 30);
			break;
		case 1:
			g.fillRect((966 / 2) - 35, 390, 70, 30);
			break;
		}
	}

}

package irixstudios.blockery;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Number {

	String number;
	int xPos;
	int yPos;

	int row;
	int col;

	int alpha = 255;

	public Number(int colu, int row, int multi) {
		xPos = colu * 30 + 105;
		yPos = row * 30 + 80;

		number = "+" + multi;

		this.row = row;
		this.col = colu;
	}

	public void paint(Graphics2D g) {
		alpha -= 10;
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 28f));
		FontMetrics FM = g.getFontMetrics();
		int FMSW = FM.stringWidth(number);
		if (alpha < 0) {
			alpha = 0;
		}
		yPos -= yPos / 50;
		g.setColor(new Color(255, 255, 255, alpha));
		g.drawString(number, xPos - (FMSW / 2), yPos);
	}

}

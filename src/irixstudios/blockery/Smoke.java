package irixstudios.blockery;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class Smoke {

	int speed;
	int diameter;
	int xPos;
	int yPos;

	public Smoke() {
		this.xPos = (int) (Math.random() * 966);
		this.diameter = (int) (Math.random() * 255) + 40;
		this.yPos = 568 + diameter;
		this.speed = (int) (Math.random() * 4);
	}

	public void paint(Graphics2D g) {
		yPos -= speed;
		xPos++;
		g.setPaint(new GradientPaint(0, 568, new Color(0, 0, 0, 255), 0, 0, new Color(235, 235, 238, 0)));
		g.fillOval(xPos - (diameter / 2), yPos, diameter, diameter);
	}
}

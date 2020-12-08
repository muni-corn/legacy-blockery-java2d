package musicaflight.blockery;

import java.awt.Color;
import java.awt.Graphics2D;

public class Block {

	int colu;
	int row;
	Color color;
	int colorType;
	int xPos;
	int yPos;
	boolean unanimated = true;
	boolean animateThisBlock;

	public int getBlockType() {
		return this.colorType;
	}

	public void setBlockType(int type) {
		switch (type) {
		case 0:
			this.color = (new Color(255, 165, 48));
			colorType = 0;
			break;
		case 1:
			this.color = (new Color(80, 236, 140));
			colorType = 1;
			break;
		case 2:
			this.color = (new Color(17, 124, 255));
			colorType = 2;
			break;
		case 3:
			this.color = (new Color(255, 0, 76));
			colorType = 3;
			break;
		default:
			this.color = (new Color(0, 0, 0, 0));
			colorType = 4;
			break;
		}
	}

	public Block(int colu, int row, int type, boolean animateThis) {
		if (animateThis) {
			unanimated = true;
			animateThisBlock = animateThis;
		}
		this.colu = colu;
		this.row = row;
		if (unanimated) {
			xPos = colu * 30 + 94;
			yPos = (int) ((row * 30 + 64) * -(Math.pow(1.6, (14 - row))));
			unanimated = false;
		}

		this.setBlockType(type);

	}

	public void paint(Graphics2D g) {
		g.setColor(this.color);
		if (yPos < (row * 30 + 64) && animateThisBlock) {
			yPos = (yPos + (row * 30 + 64)) / 2;
			g.fillRect(xPos, yPos, 22, 22);
		} else
			if (!animateThisBlock) {
				xPos = colu * 30 + 94;
				yPos = row * 30 + 64;
				g.fillRect(xPos, yPos, 22, 22);
			}
	}
}

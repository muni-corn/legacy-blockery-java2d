package irixstudios.blockery;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Slot {

	int slotPrize;
	int slotPos;
	boolean slotSpinning;
	Color color;
	ImageIcon iiprize;
	Image prize;
	
	public int prize() {
		return this.slotPrize;
	}

	public void setSlotPrize(boolean stillSpinning) {
		if (stillSpinning) {
			slotPrize = (int) (Math.random() * 6);
			switch (slotPrize) {
			case 0:
				//multiplyBlocks
				iiprize = new ImageIcon(this.getClass().getResource("/multiplyBlocks.png"));
				prize = iiprize.getImage();
				break;
			case 1:
				//freeFact
				iiprize = new ImageIcon(this.getClass().getResource("/freeFact.png"));
				prize = iiprize.getImage();
				break;
			case 2:
				//freePow
				iiprize = new ImageIcon(this.getClass().getResource("/freePow.png"));
				prize = iiprize.getImage();
				break;
			case 3:
				//freeMegPow
				iiprize = new ImageIcon(this.getClass().getResource("/freeMegPow.png"));
				prize = iiprize.getImage();
				break;
			case 4:
				//bonusBlocks
				iiprize = new ImageIcon(this.getClass().getResource("/bonusBlocks.png"));
				prize = iiprize.getImage();
				break;
			case 5:
				//threeBlockTypes
				iiprize = new ImageIcon(this.getClass().getResource("/threeBlockTypes.png"));
				prize = iiprize.getImage();
				break;
			}
		}
	}

	public Slot(int slot, int type) {
		this.slotPos = slot;
		this.slotPrize = type;
	}

	public void paint(Graphics2D g) {
		g.setColor(color);
		g.drawImage(prize, 801 + (43 * slotPos), 167, null);
	}

}

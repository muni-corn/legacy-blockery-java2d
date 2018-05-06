package irixstudios.blockery;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class EndGame {

	int count = 0;
	int countSlot = 0;

	Images imgs = new Images();
	private Runner runner;

	public EndGame(Runner runner) {
		this.runner = runner;
	}

	private Block[][] board = new Block[10][14];
	private Number[][] nums = new Number[10][14];
	//private Slot[] slot = new Slot[3];

	int highlight = -1;
	int yesNoHighlight = -1;

	double blockTypes = 4.0;

	boolean setup = true;

	boolean blocksNotRendered = true;
	double currBlocks;
	double blocksNR;
	long blocks;
	double bps;

	int secsLeft;
	double minsLeft;
	double hrsLeft;

	boolean pauseGame = false;
	boolean pauseGameAudio = false;
	int pausedImgY;

	/*boolean spinning = false;
	boolean moveKnob = false;
	int knobY = 165;*/

	//Item Vars

	long cottCost = 200;
	long factCost = 1500;
	long powerCost = 20000;
	long megaCost = 250000;
	long doublerCost = 5000;
	//long spinCost = 1000;

	int cottOwn;
	int factOwn;
	int powerOwn;
	int megaOwn;
	int doublerOwn;
	int doublerMulti = 1;
	/*int spinsLeft;
	int totalSpins;*/

	double cottBPS = 1;
	double factBPS = 5;
	double powerBPS = 75;
	double megaBPS = 1000;

	public void autoSave() {
		count++;
		if (count > 1200) {
			runner.endfr.saveFiles();
			count = 0;
		}
	}

	public void go() {
		/* slot machine
		if (moveKnob && knobY < 199) {
			knobY = (199 + knobY) / 2;
			if (knobY >= 198) {
				knobY = 199;
				moveKnob = false;
			}
		} else
			if (!moveKnob && knobY > 165) {
				knobY = ((knobY - 165) / 2) + 165;
				if (knobY <= 165) {
					knobY = 165;

				}
			}
		
		if (spinning) {
			countSlot++;
			if (countSlot < 40) {
				slot[0].setSlotPrize(true);
				slot[1].setSlotPrize(true);
				slot[2].setSlotPrize(true);
			} else
				if (countSlot < 50) {
					slot[1].setSlotPrize(true);
					slot[2].setSlotPrize(true);
				} else
					if (countSlot < 60) {
						slot[2].setSlotPrize(true);
					} else
						if (countSlot < 70) {
							spinning = false;
							countSlot = 0;
						}
		}*/

		if (pauseGame == false) {
			if (runner.doReadFiles == false) {

				bps = ((cottOwn * cottBPS) + (factOwn * factBPS) + (powerOwn * powerBPS) + (megaOwn * megaBPS));
				blocksNR = (blocksNR + (bps / 20.0));
				if (currBlocks > 0) {
					blocksNR += (double) (currBlocks);
					currBlocks = 0;
				}
				blocks = Math.round(blocksNR);
			}
		}
		if (blocksNotRendered) {
			/*for (int slotA = 0; slotA < slot.length; slotA++) {
				slot[slotA] = new Slot(slotA, (int) (Math.random() * 6));
				slot[slotA].setSlotPrize(true);
			}*/
			for (int colu = 0; colu < board.length; colu++) {
				for (int row = 0; row < board[colu].length; row++) {
					double rand = Math.random();
					board[colu][row] = new Block(colu, row, (int) (rand * 4.0), true);
					blocksNotRendered = false;
				}
			}
		}
	}

	public void pause(int PauseRow, int PauseCol) {
		if (PauseRow > 720 && PauseCol > 30 && PauseCol < 60 && PauseRow < 930 && pauseGame == true) {
			pauseGame = false;
			pauseGameAudio = false;
			if (runner.soundOn) {
				Audio.CLICK.play();
			}
		} else
			if (PauseRow > 720 && PauseCol > 30 && PauseCol < 60 && PauseRow < 930 && pauseGame == false) {
				pauseGame = true;
				runner.endfr.saveFiles();
				pausedImgY = 150;
			}
	}

	public void items(int x, int y) {

		/*if (x >= 570 && x <= 930 && y >= 165 && y <= 210 && blocksNR >= spinCost) {
			totalSpins++;
			spinsLeft++;
			blocksNR -= spinCost;
			spinCost = (long) (1000 * Math.pow(1.35, totalSpins));
		}*/
		if (x >= 510 && x <= 930 && y >= 225 && y <= 270 && blocksNR >= doublerCost) {
			doublerOwn++;
			doublerMulti = (int) (Math.pow(2, doublerOwn));
			blocksNR -= doublerCost;
			doublerCost = (long) (5000 * Math.pow(2.2, doublerOwn));
		}
		if (x >= 510 && x <= 930 && y >= 285 && y <= 330 && blocksNR >= cottCost) {
			cottOwn++;
			blocksNR -= cottCost;
			cottCost = (long) (200 * Math.pow(1.25, cottOwn));
		}
		if (x >= 510 && x <= 930 && y >= 345 && y <= 390 && blocksNR >= factCost) {
			factOwn++;
			blocksNR -= factCost;
			factCost = (long) (1500 * Math.pow(1.25, factOwn));
		}
		if (x >= 510 && x <= 930 && y >= 405 && y <= 450 && blocksNR >= powerCost) {
			powerOwn++;
			blocksNR -= powerCost;
			powerCost = (long) (20000 * Math.pow(1.25, powerOwn));

		}
		if (x >= 510 && x <= 930 && y >= 465 && y <= 510 && blocksNR >= megaCost) {
			megaOwn++;
			blocksNR -= megaCost;
			megaCost = (long) (250000 * Math.pow(1.25, megaOwn));
		}
		/*if (x >= 475 && x <= 565 && y >= 165 && y <= 210 && spinsLeft > 0 && !spinning) {
			spinsLeft--;
			moveKnob = true;
			spinning = true;
		}*/

	}

	public void paint(Graphics2D g) {

		if (setup) {
			runner.endfr.readFiles();
			//spinCost = (long) (1000 * Math.pow(1.35, totalSpins));
			cottCost = (long) (200 * Math.pow(1.25, cottOwn));
			factCost = (long) (1500 * Math.pow(1.25, factOwn));
			powerCost = (long) (20000 * Math.pow(1.25, powerOwn));
			megaCost = (long) (250000 * Math.pow(1.25, megaOwn));
			doublerCost = (long) (5000 * Math.pow(2.2, doublerOwn));
			doublerMulti = (int) (Math.pow(2, doublerOwn));
			bps = ((cottOwn * cottBPS) + (factOwn * factBPS) + (powerOwn * powerBPS) + (megaOwn * megaBPS));
			setup = false;
		}

		g.drawImage(imgs.endbackG, 0, 0, null);

		g.setColor(new Color(235, 235, 239, 50));
		switch (highlight) {
		case 1:
			g.fillRect(510, 218, 60, 60);
			runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			break;
		case 2:
			g.fillRect(510, 278, 60, 60);
			runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			break;
		case 3:
			g.fillRect(510, 338, 60, 60);
			runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			break;
		case 4:
			g.fillRect(510, 398, 60, 60);
			runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			break;
		case 5:
			g.fillRect(510, 458, 60, 60);
			runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			break;
		}

		FontMetrics FM;
		int FMSW;

		//Menu and Block scoreboard

		g.setColor(Color.WHITE);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 28f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("Pause");
		g.drawString("Pause", 825 - (FMSW / 2), 55);

		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 23f));
		g.drawString("blocks", 865, 115);

		g.setFont(Fonts.SFDR.deriveFont(Font.BOLD, 52f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth(String.valueOf(blocks));
		g.drawString(String.valueOf(blocks), 859 - FMSW, 115);

		//Per-second-ometers

		g.setColor(new Color(187, 187, 187));
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 18f));
		FM = g.getFontMetrics();
		if (bps > 1 || bps == 0) {
			FMSW = FM.stringWidth(String.valueOf(Math.round(bps)) + " blocks per second");
			g.drawString(String.valueOf(Math.round(bps)) + " blocks per second", 930 - FMSW, 146);
		} else
			if (bps == 1) {
				FMSW = FM.stringWidth(String.valueOf(Math.round(bps)) + " block per second");
				g.drawString(String.valueOf(Math.round(bps)) + " block per second", 930 - FMSW, 146);
			}

		//Item Names, Costs and per-seconds

		g.setColor(Color.WHITE);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 21f));
		//g.drawString("Slot Machine Spin", 580, 185);
		g.drawString("Block Doubler", 580, 245);
		g.drawString("Cottage House", 580, 305);
		g.drawString("Factory", 580, 365);
		g.drawString("Powerhouse", 580, 425);
		g.drawString("Megapowerhouse", 580, 485);

		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 14f));
		//g.drawString("costs " + spinCost, 580, 205);
		g.drawString("costs " + doublerCost, 580, 265);
		g.drawString("costs " + cottCost, 580, 325);
		g.drawString("costs " + factCost, 580, 385);
		g.drawString("costs " + powerCost, 580, 445);
		g.drawString("costs " + megaCost, 580, 505);

		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("clicked blocks x2");
		g.drawString("clicked blocks x2", 923 - FMSW, 242);
		FMSW = FM.stringWidth("+" + cottBPS + " Bps");
		g.drawString("+" + cottBPS + " bps", 923 - FMSW, 302);
		FMSW = FM.stringWidth("+" + factBPS + " Bps");
		g.drawString("+" + factBPS + " bps", 923 - FMSW, 362);
		FMSW = FM.stringWidth("+" + powerBPS + " Bps");
		g.drawString("+" + powerBPS + " bps", 923 - FMSW, 422);
		FMSW = FM.stringWidth("+" + megaBPS + " Bps");
		g.drawString("+" + megaBPS + " bps", 923 - FMSW, 482);

		FM = g.getFontMetrics();
		/*if (spinsLeft != 1) {
			FMSW = FM.stringWidth("(" + spinsLeft + " spins left)");
			g.drawString("(" + spinsLeft + " spins left)", 792 - FMSW, 205);
		} else
			if (spinsLeft == 1) {
				FMSW = FM.stringWidth("(" + spinsLeft + " spin left)");
				g.drawString("(" + spinsLeft + " spin left)", 792 - FMSW, 205);
			}*/
		FMSW = FM.stringWidth("(current multiplier x" + doublerMulti + ")");
		g.drawString("(current multiplier x" + doublerMulti + ")", 923 - FMSW, 265);
		FMSW = FM.stringWidth("(" + cottOwn + " owned)");
		g.drawString("(" + cottOwn + " owned)", 923 - FMSW, 325);
		FMSW = FM.stringWidth("(" + factOwn + " owned)");
		g.drawString("(" + factOwn + " owned)", 923 - FMSW, 385);
		FMSW = FM.stringWidth("(" + powerOwn + " owned)");
		g.drawString("(" + powerOwn + " owned)", 923 - FMSW, 445);
		FMSW = FM.stringWidth("(" + megaOwn + " owned)");
		g.drawString("(" + megaOwn + " owned)", 923 - FMSW, 505);

		/*
		g.setColor(new Color(232, 115, 121));
		g.fillRect(559, knobY, 11, 11);
		*/

		//"Are we there yet?"
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 16f));
		FM = g.getFontMetrics();

		if (bps > 0) {

			/*if (blocks < spinCost) {
				secsLeft = (spinCost - blocks) / bps;
				minsLeft = secsLeft / 60;
				hrsLeft = minsLeft / 60;
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 165, 229, 45);
				g.setColor(Color.WHITE);
				if (hrsLeft > 1.00) {
					FMSW = FM.stringWidth((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable");
					g.drawString((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable", (570 + (229 / 2)) - (FMSW / 2), 194);
				} else
					if (minsLeft > 1.00) {
						FMSW = FM.stringWidth((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable");
						g.drawString((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable", (570 + (229 / 2)) - (FMSW / 2), 194);
					} else
						if (secsLeft > 0) {
							FMSW = FM.stringWidth(Math.round(secsLeft) + " seconds until affordable");
							g.drawString(Math.round(secsLeft) + " seconds until affordable", (570 + (229 / 2)) - (FMSW / 2), 194);
						}
			}*/
			if (blocks < doublerCost) {
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 225, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((doublerCost - blocks) / bps);
					minsLeft = secsLeft / 60.0;
					hrsLeft = minsLeft / 60.0;
					if (hrsLeft > 1.00) {
						FMSW = FM.stringWidth((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable");
						g.drawString((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable", (570 + 180) - (FMSW / 2), 254);
					} else
						if (minsLeft > 1.00) {
							FMSW = FM.stringWidth((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable");
							g.drawString((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable", (570 + 180) - (FMSW / 2), 254);
						} else
							if (secsLeft >= 0) {
								FMSW = FM.stringWidth(Math.round(secsLeft) + " seconds until affordable");
								g.drawString(Math.round(secsLeft) + " seconds until affordable", (570 + 180) - (FMSW / 2), 254);
							}
				}
			}
			if (blocks < cottCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 285, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {
					secsLeft = (int) ((cottCost - blocks) / bps);
					minsLeft = secsLeft / 60.0;
					hrsLeft = minsLeft / 60.0;
					if (hrsLeft > 1.00) {
						FMSW = FM.stringWidth((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable");
						g.drawString((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable", (570 + 180) - (FMSW / 2), 314);
					} else
						if (minsLeft > 1.00) {
							FMSW = FM.stringWidth((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable");
							g.drawString((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable", (570 + 180) - (FMSW / 2), 314);
						} else
							if (secsLeft >= 0) {
								FMSW = FM.stringWidth(Math.round(secsLeft) + " seconds until affordable");
								g.drawString(Math.round(secsLeft) + " seconds until affordable", (570 + 180) - (FMSW / 2), 314);
							}
				}
			}
			if (blocks < factCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 345, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((factCost - blocks) / bps);
					minsLeft = secsLeft / 60.0;
					hrsLeft = minsLeft / 60.0;
					if (hrsLeft > 1.00) {
						FMSW = FM.stringWidth((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable");
						g.drawString((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable", (570 + 180) - (FMSW / 2), 374);
					} else
						if (minsLeft > 1.00) {
							FMSW = FM.stringWidth((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable");
							g.drawString((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable", (570 + 180) - (FMSW / 2), 374);
						} else
							if (secsLeft >= 0) {
								FMSW = FM.stringWidth(Math.round(secsLeft) + " seconds until affordable");
								g.drawString(Math.round(secsLeft) + " seconds until affordable", (570 + 180) - (FMSW / 2), 374);
							}
				}
			}
			if (blocks < powerCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 405, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((powerCost - blocks) / bps);
					minsLeft = secsLeft / 60.0;
					hrsLeft = minsLeft / 60.0;
					if (hrsLeft > 1.00) {
						FMSW = FM.stringWidth((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable");
						g.drawString((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable", (570 + 180) - (FMSW / 2), 434);
					} else
						if (minsLeft > 1.00) {
							FMSW = FM.stringWidth((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable");
							g.drawString((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable", (570 + 180) - (FMSW / 2), 434);
						} else
							if (secsLeft >= 0) {
								FMSW = FM.stringWidth(Math.round(secsLeft) + " seconds until affordable");
								g.drawString(Math.round(secsLeft) + " seconds until affordable", (570 + 180) - (FMSW / 2), 434);
							}
				}
			}
			if (blocks < megaCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 465, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((megaCost - blocks) / bps);
					minsLeft = secsLeft / 60.0;
					hrsLeft = minsLeft / 60.0;
					if (hrsLeft > 1.00) {
						FMSW = FM.stringWidth((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable");
						g.drawString((Math.round(hrsLeft * 10.0) / 10.0) + " hours until affordable", (570 + 180) - (FMSW / 2), 494);
					} else
						if (minsLeft > 1.00) {
							FMSW = FM.stringWidth((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable");
							g.drawString((Math.round(minsLeft * 10.0) / 10.0) + " minutes until affordable", (570 + 180) - (FMSW / 2), 494);
						} else
							if (secsLeft >= 0) {
								FMSW = FM.stringWidth(Math.round(secsLeft) + " seconds until affordable");
								g.drawString(Math.round(secsLeft) + " seconds until affordable", (570 + 180) - (FMSW / 2), 494);
							}
				}
			}
		}

		/*Slot Machine
		for (int whichSlot = 0; whichSlot < slot.length; whichSlot++) {
			slot[whichSlot].paint(g);
		}

		g.setColor(new Color(187, 187, 187));
		g.setFont(Fonts.SFDR.deriveFont(Font.BOLD, 40f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth(String.valueOf(spinsLeft));
		g.drawString(String.valueOf(spinsLeft), 550 - FMSW, 187);
		g.setPaint(new GradientPaint(0, 208, new Color(0, 0, 0, 75), 0, 165 + (41 / 2), new Color(235, 235, 238, 0)));
		g.fillRect(801, 167, 41, 41);
		g.fillRect(844, 167, 41, 41);
		g.fillRect(887, 167, 41, 41);*/

		//Blocks
		if (g != null) {
			for (int colu = 0; colu < board.length; colu++) {
				for (int row = 0; row < board[colu].length; row++) {
					if (board[colu][row] != null) {
						board[colu][row].paint(g);
					}
				}
			}
			if (runner.showNums) {
				for (int colu = 0; colu < nums.length; colu++) {
					for (int row = 0; row < nums[colu].length; row++) {
						if (nums[colu][row] != null) {
							nums[colu][row].paint(g);
						}
					}
				}
			}
		}

		//Paused?
		if (pauseGame) {
			if (pauseGameAudio == false) {
				if (runner.soundOn) {
					Audio.PAUSED.play();
				}
				pauseGameAudio = true;
			}
			g.setColor(new Color(0, 0, 0, 250));
			g.fillRect(0, 0, runner.frame.getWidth(), 568);
			g.setColor(new Color(70, 70, 70));
			g.fillRect(720, 30, 210, 30);
			g.setColor(Color.WHITE);
			g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 28f));
			FM = g.getFontMetrics();
			FMSW = FM.stringWidth("Resume");
			g.drawString("Resume", 825 - (FMSW / 2), 55);

			g.setColor(new Color(232, 115, 121));
			g.fillRect((runner.frame.getWidth() / 2) - 35, 350, 70, 30);

			g.setColor(Color.WHITE);
			g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 15f));
			FM = g.getFontMetrics();
			FMSW = FM.stringWidth("Menu");
			g.drawString("Menu", (runner.frame.getWidth() / 2) - (FMSW / 2), 371);
			g.setColor(new Color(70, 70, 70));
			FMSW = FM.stringWidth("(Your progress will be saved.)");
			g.drawString("(Your progress will be saved.)", (runner.frame.getWidth() / 2) - (FMSW / 2), 401);

			if (pausedImgY < 200) {
				pausedImgY = (pausedImgY + 200) / 2;
				g.drawImage(imgs.paused, (runner.frame.getWidth() / 2) - (290 / 2), pausedImgY, null);
			}
		}

		//Highlights
		g.setColor(new Color(255, 255, 255, 50));
		if (highlight == 0) {
			g.fillRect(720, 30, 210, 30);
			runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		if (pauseGame == false) {
			switch (highlight) {
			case 1:
				g.fillRect(570, 225, 360, 45);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 2:
				g.fillRect(570, 285, 360, 45);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 3:
				g.fillRect(570, 345, 360, 45);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 4:
				g.fillRect(570, 405, 360, 45);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 5:
				g.fillRect(570, 465, 360, 45);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			/*case 6:
				g.fillRect(570, 165, 229, 45);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 7:
				g.fillRect(559, knobY, 11, 11);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;*/
			}

		}
		if (pauseGame) {
			switch (yesNoHighlight) {
			case 0:
				g.fillRect((runner.frame.getWidth() / 2) - 35, 350, 70, 30);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 1:
				g.fillRect((runner.frame.getWidth() / 2) - 35, 390, 70, 30);
				break;
			}
		}
	}

	public void startElimBlocks(int row, int colu) {
		if (pauseGame == false) {
			Block block = board[colu][row];
			Block blockTemp;
			boolean greaterOne = false;

			if (block.getBlockType() == 4) {
				return;
			}

			if (colu > 0) {
				blockTemp = board[colu - 1][row];
				if (block.getBlockType() == blockTemp.getBlockType()) {
					greaterOne = true;
				}
			}

			if (colu < 9) {
				blockTemp = board[colu + 1][row];
				if (block.getBlockType() == blockTemp.getBlockType()) {
					greaterOne = true;
				}
			}

			if (row > 0) {
				blockTemp = board[colu][row - 1];
				if (block.getBlockType() == blockTemp.getBlockType()) {
					greaterOne = true;
				}
			}

			if (row < 13) {
				blockTemp = board[colu][row + 1];
				if (block.getBlockType() == blockTemp.getBlockType()) {
					greaterOne = true;
				}
			}

			if (greaterOne) {
				elimBlocks(row, colu, block.getBlockType());
			}
		}
	}

	private void elimBlocks(int row, int colu, int colorType) {
		if (pauseGame == false) {
			if (colu < 0 || colu > 9 || row < 0 || row > 13) {
				return;
			}
			if (board[colu][row].getBlockType() == colorType) {
				board[colu][row].setBlockType(4);
				if (runner.showNums) {
					nums[colu][row] = new Number(colu, row, doublerMulti);
				}
				currBlocks += doublerMulti;
				elimBlocks(row - 1, colu, colorType);
				elimBlocks(row + 1, colu, colorType);
				elimBlocks(row, colu - 1, colorType);
				elimBlocks(row, colu + 1, colorType);
			}
		}
	}

	public void gravityBlocks() {
		if (pauseGame == false) {
			for (int row = 12; row >= 0; row--) {
				for (int colu = 9; colu >= 0; colu--) {
					if (board[colu][row].getBlockType() != 4) {
						if (board[colu][row + 1].getBlockType() == 4) {
							Block currBlock = board[colu][row];
							Block destBlock = board[colu][row + 1];
							board[colu][row] = new Block(colu, row, destBlock.getBlockType(), false);
							board[colu][row + 1] = new Block(colu, row + 1, currBlock.getBlockType(), false);
						}
					}
				}
			}
			int row = 0;
			for (int colu = 9; colu >= 0; colu--) {
				double rand = Math.random();
				if (board[colu][row].getBlockType() == 4) {
					board[colu][row] = new Block(colu, row, (int) (rand * blockTypes), false);
					if (runner.soundOn) {
						Audio.BCLICK.play();
					}
				}
			}
		}
	}

	public void yesNoButton(int xx, int yy) {
		if (pauseGame && xx > 448 && xx < 513 && yy > 350 && yy < 380) {
			runner.endfr.saveFiles();
			pauseGame = false;
			pauseGameAudio = false;
			if (runner.soundOn) {
				Audio.CLICK.play();
			}
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
			setup = true;
			runner.doReadFiles = true;
			runner.playingEnd = false;
			runner.playingOpt = false;
			runner.playingEnv = false;
			blocksNotRendered = true;
			runner.onMenu = true;
		}
	}
}

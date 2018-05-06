package irixstudios.blockery;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ConcurrentModificationException;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnvGame {

	Images imgs = new Images();
	private Runner runner;

	public EnvGame(Runner runner) {
		this.runner = runner;
	}

	Color randColor;

	int count = 0;
	int newBestCount;
	int smokeCount;

	boolean setup = true;

	private Block[][] board = new Block[10][14];
	private Number[][] nums = new Number[10][14];
	CopyOnWriteArrayList<Smoke> smoke = new CopyOnWriteArrayList<Smoke>();

	int highlight = 6;
	int yesNoHighlight = 2;

	double blockTypes = 4.0;

	Image gameOver;
	int GOy = 0;

	double cO2;
	int cO2mtr;
	double cPS;
	double percCO2;
	double roundOffPerc;

	boolean secsLeftSET = false;

	int prevCO2sec;
	int deltaCO2sec;
	int CO2secsLeft;
	double CO2minsLeft;
	double CO2hrsLeft;

	int secsLeft;
	double minsLeft;
	double hrsLeft;

	boolean blocksNotRendered = true;
	double currBlocks;
	double blocksNR;
	long blocks;
	double bps;

	//High Score Slots
	long highScoreFirst = 0;
	long highScoreSecond = 0;
	long highScoreThird = 0;
	long highScoreFourth = 0;
	long highScoreFifth = 0;
	boolean newHighScore = false;

	public boolean gameover = false;
	boolean setHighScore = true;

	boolean siren = false;
	boolean noAudioGO = true;

	boolean pauseGame = false;
	boolean pauseGameAudio = false;
	int pausedImgY;

	Color co2color = new Color(150, 150, 150);

	//Item Vars

	long cottCost = 200;
	long factCost = 1500;
	long powerCost = 20000;
	long megaCost = 250000;
	long treeCost;

	int cottOwn;
	int factOwn;
	int powerOwn;
	int megaOwn;
	int treeOwn;

	int cottBPS = 1;
	int factBPS = 5;
	int powerBPS = 75;
	int megaBPS = 1000;

	double cottCO2PS = 0.5;
	double factCO2PS = 2;
	double powerCO2PS = 50;
	double megaCO2PS = 750;
	double treeCO2PS = -5;

	public void autoSave() {
		count++;
		if (count > 1200) {
			runner.envfr.saveFiles();
			count = 0;
		}
	}

	public void goCounters() {
		if (pauseGame == false) {
			if (gameover == false) {
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
		}
	}

	public void goBlocks() {

		if (blocksNotRendered) {
			for (int colu = 0; colu < board.length; colu++) {
				for (int row = 0; row < board[colu].length; row++) {
					double rand = Math.random();
					board[colu][row] = new Block(colu, row, (int) (rand * 4.0), true);
				}
			}
			blocksNotRendered = false;
		}
	}

	public void goCO2() {
		if (pauseGame == false) {
			cPS = ((cottOwn * cottCO2PS) + (factOwn * factCO2PS) + (powerOwn * powerCO2PS) + (megaOwn * megaCO2PS) + (treeOwn * treeCO2PS));
			if (cO2 <= 10000) {
				cO2 = (cO2 + (cPS / 20));
				cO2mtr = (int) ((cO2 / 10000) * 480);
				percCO2 = cO2 / 100;
				roundOffPerc = Math.round(percCO2 * 10.0) / 10.0;
			}
			if (secsLeftSET) {
				if ((cPS <= 0) || (CO2secsLeft >= 30 && siren)) {
					co2color = new Color(150, 150, 150);
					if (siren) {
						siren = false;
						playAudio();
					}
				} else
					if (cPS > 0 && !siren && CO2secsLeft <= 30) {
						co2color = new Color(255, 50, 50);
						if (!siren) {
							siren = true;
							playAudio();
						}
					}
			}
			if (cO2 >= 10000) {
				percCO2 = 100;
				roundOffPerc = 100;
				cO2 = 10000;
				cO2mtr = 480;
				gameover = true;
			} else
				if (cO2 <= 0) {
					co2color = new Color(150, 150, 150);
					percCO2 = 0;
					roundOffPerc = 0;
					cO2 = 0;
				}
		}
	}

	public void pause(int PauseRow, int PauseCol) {
		if (gameover == false) {
			if (PauseRow > 720 && PauseCol > 30 && PauseCol < 60 && PauseRow < 930 && pauseGame == true) {
				pauseGame = false;
				pauseGameAudio = false;
				if (runner.soundOn) {
					Audio.CLICK.play();
				}
				playAudio();
			} else
				if (PauseRow > 720 && PauseCol > 30 && PauseCol < 60 && PauseRow < 930 && pauseGame == false) {
					pauseGame = true;
					pausedImgY = 150;
					runner.envfr.saveFiles();
					runner.notiBar.envHasSaved = true;
					runner.notiBar.newNoti = true;
					playAudio();
				}
		}
	}

	public void items(int x, int y) {
		if (gameover == false) {

			if (x >= 510 && x <= 930 && y >= 225 && y <= 270 && blocksNR >= cottCost) {
				cottOwn++;
				blocksNR -= cottCost;
			}
			if (x >= 510 && x <= 930 && y >= 285 && y <= 330 && blocksNR >= factCost) {
				factOwn++;
				blocksNR -= factCost;
			}
			if (x >= 510 && x <= 930 && y >= 345 && y <= 390 && blocksNR >= powerCost) {
				powerOwn++;
				blocksNR -= powerCost;
			}
			if (x >= 510 && x <= 930 && y >= 405 && y <= 450 && blocksNR >= megaCost) {
				megaOwn++;
				blocksNR -= megaCost;
			}
			if (x >= 510 && x <= 930 && y >= 465 && y <= 510 && blocksNR >= treeCost) {
				treeOwn++;
				blocksNR -= treeCost;
				treeCost = (long) (treeCost * 3);
			}
		}
	}

	public void paint(Graphics2D g) {

		if (setup) {
			runner.envfr.readFiles();
			bps = ((cottOwn * cottBPS) + (factOwn * factBPS) + (powerOwn * powerBPS) + (megaOwn * megaBPS));
			cPS = ((cottOwn * cottCO2PS) + (factOwn * factCO2PS) + (powerOwn * powerCO2PS) + (megaOwn * megaCO2PS) + (treeOwn * treeCO2PS));
			treeCost = (long) ((Math.pow(3, treeOwn)) * 100);
			CO2secsLeft = (int) ((10000 - cO2) / cPS);
			prevCO2sec = CO2secsLeft;
			playAudio();
			setup = false;
			secsLeftSET = true;
		}

		g.drawImage(imgs.envbackG, 0, 0, null);

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

		//TODO Smoke
		if (runner.showClouds) {
			smokeCount++;
			if ((smokeCount > CO2secsLeft && cPS > 0) || (gameover)) {
				//Create new particle
				smoke.add(new Smoke());
				smokeCount = 0;
			}

			try {
				for (Smoke value : smoke) {
					//Paint all particles
					value.paint(g);
					if (value.yPos < (0 - value.diameter) || value.xPos > (runner.frame.getWidth() + value.diameter)) {
						smoke.remove(value);
					}
				}
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
			}
		}

		//Menu and Block scoreboard

		g.setColor(new Color(255, 255, 255));
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 28f));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("Pause");
		g.drawString("Pause", 825 - (FMSW / 2), 55);

		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 23f));
		g.drawString("blocks", 865, 115);

		g.setFont(Fonts.SFDR.deriveFont(Font.PLAIN, 52f));
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
		FMSW = FM.stringWidth(String.valueOf(Math.round(cPS * 10.0) / 10.0) + " CO2 per second");
		g.drawString(String.valueOf(Math.round(cPS * 10.0) / 10.0) + " CO2 per second", 930 - FMSW, 176);

		//Item Names, Costs and per-seconds

		g.setColor(Color.WHITE);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 21f));
		g.drawString("Cottage House", 580, 245);
		g.drawString("Factory", 580, 305);
		g.drawString("Powerhouse", 580, 365);
		g.drawString("Megapowerhouse", 580, 425);
		g.drawString("Tree", 580, 485);

		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 14f));
		g.drawString("costs " + cottCost, 580, 265);
		g.drawString("costs " + factCost, 580, 325);
		g.drawString("costs " + powerCost, 580, 385);
		g.drawString("costs " + megaCost, 580, 445);
		g.drawString("costs " + treeCost, 580, 505);

		FM = g.getFontMetrics();
		FMSW = FM.stringWidth("+" + cottCO2PS + " CO2ps");
		g.drawString("+" + cottCO2PS + " CO2ps", 923 - FMSW, 265);
		FMSW = FM.stringWidth("+" + cottBPS + " Bps");
		g.drawString("+" + cottBPS + " bps", 923 - FMSW, 242);

		FMSW = FM.stringWidth("+" + factCO2PS + " CO2ps");
		g.drawString("+" + factCO2PS + " CO2ps", 923 - FMSW, 325);
		FMSW = FM.stringWidth("+" + factBPS + " Bps");
		g.drawString("+" + factBPS + " bps", 923 - FMSW, 302);

		FMSW = FM.stringWidth("+" + powerCO2PS + " CO2ps");
		g.drawString("+" + powerCO2PS + " CO2ps", 923 - FMSW, 385);
		FMSW = FM.stringWidth("+" + powerBPS + " Bps");
		g.drawString("+" + powerBPS + " bps", 923 - FMSW, 362);

		FMSW = FM.stringWidth("+" + megaCO2PS + " CO2ps");
		g.drawString("+" + megaCO2PS + " CO2ps", 923 - FMSW, 445);
		FMSW = FM.stringWidth("+" + megaBPS + " Bps");
		g.drawString("+" + megaBPS + " bps", 923 - FMSW, 422);

		FMSW = FM.stringWidth("+" + treeCO2PS + " CO2ps");
		g.drawString(treeCO2PS + " CO2ps", 923 - FMSW, 505);

		//CO2 Meter

		g.setColor(new Color(50, 50, 50));
		g.fillRect(450, 510 - cO2mtr, 15, cO2mtr);
		g.setColor(this.co2color);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 17f));
		AffineTransform orig = g.getTransform();
		g.rotate(Math.toRadians(-90));
		FM = g.getFontMetrics();
		FMSW = FM.stringWidth(String.valueOf(roundOffPerc) + "%");
		g.drawString(String.valueOf(roundOffPerc) + "%", -40 - FMSW, 463);
		g.drawString(String.valueOf(Math.round(cO2)) + "/10000 pounds", -505, 463);
		g.setTransform(orig);

		//(CO2) Are we there yet?

		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 14f));
		FM = g.getFontMetrics();
		if (cPS > 0) {
			if (cO2 < 10000) {
				CO2secsLeft = (int) ((10000 - cO2) / cPS);
				CO2minsLeft = CO2secsLeft / 60;
				CO2hrsLeft = CO2minsLeft / 60;
				if (CO2hrsLeft > 1.00) {
					FMSW = FM.stringWidth("CO2 overload in " + Math.round(CO2hrsLeft * 10.0) / 10.0 + " hours.");
					g.drawString("CO2 overload in " + Math.round(CO2hrsLeft * 10.0) / 10.0 + " hours.", 930 - FMSW, 209);
				} else
					if (CO2minsLeft > 1.00) {
						FMSW = FM.stringWidth("CO2 overload in " + Math.round(CO2minsLeft * 10.0) / 10.0 + " minutes.");
						g.drawString("CO2 overload in " + Math.round(CO2minsLeft * 10.0) / 10.0 + " minutes.", 930 - FMSW, 209);
					} else
						if (CO2secsLeft >= 0) {
							FMSW = FM.stringWidth("CO2 overload in " + Math.round(CO2secsLeft) + " seconds.");
							g.drawString("CO2 overload in " + Math.round(CO2secsLeft) + " seconds.", 930 - FMSW, 209);
						}
			}
		}
		//"Are we there yet?"
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 16f));
		FM = g.getFontMetrics();

		if (bps > 0) {

			if (blocks < treeCost) {
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 465, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((treeCost - blocks) / bps);
					minsLeft = secsLeft / 60;
					hrsLeft = minsLeft / 60;

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
			if (blocks < cottCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 225, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((cottCost - blocks) / bps);
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
			if (blocks < factCost) {
				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 285, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((factCost - blocks) / bps);
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
			if (blocks < powerCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 345, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((powerCost - blocks) / bps);
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
			if (blocks < megaCost) {

				g.setColor(new Color(0, 0, 0, 150));
				g.fillRect(570, 405, 360, 45);
				g.setColor(Color.WHITE);
				if (runner.useTimers) {

					secsLeft = (int) ((megaCost - blocks) / bps);
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
		}
		//Leaderboard
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 16f));
		g.setColor(new Color(187, 187, 187));
		g.drawString("Local Leaderboard", 510, 142);
		g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.PLAIN, 14f));
		g.drawString("1. " + highScoreFirst, 510, 161);
		g.drawString("2. " + highScoreSecond, 510, 173);
		g.drawString("3. " + highScoreThird, 510, 185);
		g.drawString("4. " + highScoreFourth, 510, 197);
		g.drawString("5. " + highScoreFifth, 510, 209);

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
			g.setColor(new Color(255, 255, 255));
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
		if (gameover == false) {
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
				}

			}
			if (pauseGame) {
				if (yesNoHighlight == 0) {
					g.fillRect((runner.frame.getWidth() / 2) - 35, 350, 70, 30);
					runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		}
		//Game over?
		if (gameover) {

			g.setColor(new Color(255, 255, 255, 240));
			g.fillRect(0, 0, runner.frame.getWidth(), 568);

			if (setHighScore) {
				if (blocks > highScoreFirst) {
					newHighScore = true;
					highScoreFifth = highScoreFourth;
					highScoreFourth = highScoreThird;
					highScoreThird = highScoreSecond;
					highScoreSecond = highScoreFirst;
					highScoreFirst = blocks;
				} else
					if (blocks > highScoreSecond) {
						highScoreFifth = highScoreFourth;
						highScoreFourth = highScoreThird;
						highScoreThird = highScoreSecond;
						highScoreSecond = blocks;
					} else
						if (blocks > highScoreThird) {
							highScoreFifth = highScoreFourth;
							highScoreFourth = highScoreThird;
							highScoreThird = blocks;
						} else
							if (blocks > highScoreFourth) {
								highScoreFifth = highScoreFourth;
								highScoreFourth = blocks;
							} else
								if (blocks > highScoreFifth) {
									highScoreFifth = blocks;
								}
				setHighScore = false;
				runner.hsfr.saveScoreFiles();
			}
			playAudio();

			if (newHighScore) {
				newBestCount++;
				g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 16f));
				FM = g.getFontMetrics();
				g.setColor(new Color(255, (int) (Math.random() * 200 + 55), 255));
				if (newBestCount > 2) {
					randColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
					newBestCount = 0;
				}
				g.setColor(randColor);
				FMSW = FM.stringWidth("NEW HIGH SCORE!");
				g.drawString("NEW HIGH SCORE!", (runner.frame.getWidth() / 2) - (FMSW / 2), 150);
			}

			cottOwn = 0;
			factOwn = 0;
			powerOwn = 0;
			megaOwn = 0;
			treeOwn = 0;

			g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 24f));
			FM = g.getFontMetrics();
			g.setColor(new Color(187, 187, 187));
			FMSW = FM.stringWidth("Local Leaderboard");
			g.drawString("Local Leaderboard", 816 - FMSW, 202);
			g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 20f));
			FM = g.getFontMetrics();
			g.drawString("1. ", 816 - FMSW, 232);
			g.drawString("2. ", 816 - FMSW, 252);
			g.drawString("3. ", 816 - FMSW, 272);
			g.drawString("4. ", 816 - FMSW, 292);
			g.drawString("5. ", 816 - FMSW, 312);

			FMSW = FM.stringWidth(String.valueOf(highScoreFirst));
			g.drawString(String.valueOf(highScoreFirst), 816 - FMSW, 232);
			FMSW = FM.stringWidth(String.valueOf(highScoreSecond));
			g.drawString(String.valueOf(highScoreSecond), 816 - FMSW, 252);
			FMSW = FM.stringWidth(String.valueOf(highScoreThird));
			g.drawString(String.valueOf(highScoreThird), 816 - FMSW, 272);
			FMSW = FM.stringWidth(String.valueOf(highScoreFourth));
			g.drawString(String.valueOf(highScoreFourth), 816 - FMSW, 292);
			FMSW = FM.stringWidth(String.valueOf(highScoreFifth));
			g.drawString(String.valueOf(highScoreFifth), 816 - FMSW, 312);

			if (GOy < 203) {

				g.setColor(new Color(187, 187, 187));
				g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 20f));
				g.drawString("Score: " + blocks + " blocks", 150, 300);
				g.drawImage(imgs.GOimg, 150, GOy, null);
				GOy = (int) (75 + GOy / 1.6);
				g.setColor(new Color(125, 209, 213));
				g.fillRect((runner.frame.getWidth() / 2) - 35, 350, 70, 30);
				g.setColor(new Color(232, 115, 121));
				g.fillRect((runner.frame.getWidth() / 2) - 35, 390, 70, 30);
				g.setColor(Color.WHITE);
				g.setFont(Fonts.NEWCICLESEMI.deriveFont(Font.BOLD, 15f));
				FM = g.getFontMetrics();
				FMSW = FM.stringWidth("Menu");
				g.drawString("Menu", (runner.frame.getWidth() / 2) - (FMSW / 2), 371);
				FMSW = FM.stringWidth("Quit");
				g.drawString("Quit", (runner.frame.getWidth() / 2) - (FMSW / 2), 411);
			}
			g.setColor(new Color(255, 255, 255, 50));

			switch (yesNoHighlight) {
			case 0:
				g.fillRect((runner.frame.getWidth() / 2) - 35, 350, 70, 30);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			case 1:
				g.fillRect((runner.frame.getWidth() / 2) - 35, 390, 70, 30);
				runner.frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				break;
			}
		}
	}

	public void startElimBlocks(int row, int colu) {
		if (pauseGame == false) {
			if (gameover == false) {
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
	}

	private void elimBlocks(int row, int colu, int colorType) {
		if (pauseGame == false) {
			if (colu < 0 || colu > 9 || row < 0 || row > 13) {
				return;
			}
			if (board[colu][row].getBlockType() == colorType) {
				board[colu][row].setBlockType(4);
				if (runner.showNums) {
					nums[colu][row] = new Number(colu, row, 1);
				}
				currBlocks += 1.0;
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
							if (runner.soundOn) {
								Audio.BCLICK.play();
							}
						}
					}
				}
			}
			int row = 0;
			for (int colu = 9; colu >= 0; colu--) {
				double rand = Math.random();
				if (board[colu][row].getBlockType() == 4) {
					board[colu][row] = new Block(colu, row, (int) (rand * blockTypes), false);
				}
			}
		}
	}

	public void playAudio() {
		if (pauseGame == false) {
			if (gameover == false) {
				if (siren) {
					Audio.PLAY.stop();
					if (runner.musicOn) {
						Audio.WARNING.loop();
					}
				} else
					if (!siren) {
						Audio.WARNING.stop();
						if (runner.musicOn) {
							Audio.PLAY.loop();
						}
					}
			}
			if (gameover && noAudioGO) {
				Audio.PLAY.stop();
				Audio.WARNING.stop();
				if (runner.musicOn && !newHighScore) {
					Audio.GAMEOVER.play();
				}
				if (runner.musicOn && newHighScore) {
					Audio.HIGHSCORE.play();
				}
				noAudioGO = false;
			}
		} else {
			Audio.PLAY.stop();
			Audio.WARNING.stop();
		}
	}

	public void yesNoButton(int xx, int yy) {
		if (gameover && xx > 448 && xx < 513 && yy > 350 && yy < 380) {
			runner.envfr.resetFiles();
			runner.playingEnd = false;
			runner.playingOpt = false;
			runner.playingEnv = false;
			runner.onMenu = true;
			if (runner.soundOn) {
				Audio.CLICK.play();
			}
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
			if (runner.showClouds) {
				smoke.remove(0);
			}
			gameover = false;
			blocksNotRendered = true;
			newHighScore = false;
			noAudioGO = true;
			runner.doReadFiles = true;
			setup = true;
			setHighScore = true;
		}
		if (gameover && xx > 448 && xx < 513 && yy > 390 && yy < 420) {
			runner.envfr.resetFiles();
			smoke.remove(smoke.size() - 1);
			runner.quitGame();
		}
		if (pauseGame && xx > 448 && xx < 513 && yy > 350 && yy < 380) {
			runner.envfr.saveFiles();
			pauseGame = false;
			pauseGameAudio = false;
			if (runner.soundOn) {
				Audio.CLICK.play();
			}
			if (runner.musicOn) {
				Audio.MENU.loop();
			}
			runner.noAudioEnv = true;
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

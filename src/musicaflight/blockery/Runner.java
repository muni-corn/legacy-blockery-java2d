/*
 * NOTES
 * Board dimensions: 14 rows, 10 columns!
 */

package musicaflight.blockery;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Runner extends JPanel {
	private static final long serialVersionUID = 1L;

	JFrame frame;
	public static String barTitle = "Blockery";

	public String version = "1.2.1";

	boolean titleNotShown = true;
	boolean doReadFiles = true;
	boolean doSaveFiles = true;

	int mouseAtX;
	int mouseAtY;

	boolean dimScreen = false;
	int dimAlpha = 0;

	public Title title = new Title();
	public EnvGame envgame = new EnvGame(this);
	public EndGame endgame = new EndGame(this);
	public Images envBG = new Images();
	public MenuScreen mainmenu = new MenuScreen(this);
	public Options options = new Options(this);
	public Filer endfr = new Filer(this);
	public OptionsFiler optfr = new OptionsFiler(this);
	public EnvFiler envfr = new EnvFiler(this);
	public HighScoreFiler hsfr = new HighScoreFiler(this);
	public WipeEndSave wipey = new WipeEndSave(this);
	public WipeEnvSave envwipey = new WipeEnvSave(this);
	public WipeHighs wipeyHighs = new WipeHighs(this);
	public ReleaseLog relLog = new ReleaseLog(this);
	public NotiBar notiBar = new NotiBar(this);
	public Fonts fonts = new Fonts(this);

	boolean printToDo = true;

	int mouseButtonNum;

	public boolean playingMenu = false;
	public boolean playingEnv = false;
	public boolean playingEnd = false;
	public boolean playingOpt = false;
	public boolean onMenu = false;
	public boolean showingTitle = true;
	public boolean wipingEnvSave = false;
	public boolean wipingEndSave = false;
	public boolean wipingHighs = false;
	public boolean viewingLog = false;

	public boolean gameover = false;
	public boolean noAudioEnv = true;
	public boolean noAudioEnd = true;
	public boolean noAudioOpt = true;

	public boolean soundOn = true;
	public boolean musicOn = true;
	public boolean doInitialize = true;
	public boolean useTimers = false;
	public boolean showClouds = true;
	public boolean showNums = true;
	public boolean useNotiBar = true;

	public Runner() {

		fonts.loadFonts();
		frame = new JFrame();

		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				dimScreen = false;
			}

			public void mouseExited(MouseEvent e) {
				dimScreen = true;

			}

			public void mousePressed(MouseEvent e) {
				mouseButtonNum = e.getButton();
				if (mouseButtonNum == 1) {
					int x = e.getX();
					int y = e.getY();
					int column = (x - 94) / 30;
					int row = (y - 64) / 30;

					if (onMenu) {
						menuButtons(x, y);
					} else
						if (playingEnv || playingEnd) {
							pauseButton(x, y);
							itemButtons(x, y);
							elimBlocks(row, column);
							yesNoButton(x, y);
							if (envgame.gameover == true) {
								yesNoButton(x, y);
							}
						} else
							if (playingOpt) {
								yesNoButton(x, y);
								options.options(x, y);
							} else
								if (wipingEnvSave) {
									yesNoButton(x, y);
								} else
									if (wipingEndSave) {
										yesNoButton(x, y);
									} else
										if (viewingLog) {
											yesNoButton(x, y);
										} else
											if (wipingHighs) {
												yesNoButton(x, y);
											}
				}
			}

			private void yesNoButton(int xx, int yy) {
				if (xx < 448 || xx > 513 || yy < 350 || yy > 420 || onMenu) {
					return;
				}
				if (playingEnv) {
					envgame.yesNoButton(xx, yy);
				} else
					if (playingEnd) {
						endgame.yesNoButton(xx, yy);
					} else
						if (playingOpt) {
							options.yesNoButton(xx, yy);
						} else
							if (wipingEnvSave) {
								envwipey.reallyWipeSave(xx, yy);
							} else
								if (wipingEndSave) {
									wipey.reallyWipeSave(xx, yy);
								} else
									if (viewingLog) {
										relLog.yesNoButton(xx, yy);
									} else
										if (wipingHighs) {
											wipeyHighs.reallyWipeSave(xx, yy);
										}
			}

			private void menuButtons(int xx, int yy) {
				/*if (xx < 388 || xx > 578 || yy < 219 || yy > 529 || onMenu == false) {
					return;
				}*/
				if (xx >= 388 && xx <= 548 && yy >= 219 && yy <= 249) {
					if (soundOn) {
						Audio.BEGINGAME.play();
					}
					playingEnd = false;
					playingOpt = false;
					playingEnv = true;
					onMenu = false;
					wipingEnvSave = false;
					wipingEndSave = false;
					viewingLog = false;
					mainmenu.nothingAnim = true;
					Audio.MENU.stop();
				} else
					if (xx > 548 && xx <= 578 && yy >= 219 && yy <= 249) {
						if (soundOn) {
							Audio.REALLY.play();
						}
						playingEnd = false;
						playingOpt = false;
						playingEnv = false;
						onMenu = false;
						wipingEnvSave = true;
						wipingEndSave = false;
						viewingLog = false;
						mainmenu.nothingAnim = true;
						Audio.MENU.stop();
					} else
						if (xx >= 388 && xx <= 548 && yy >= 259 && yy <= 289) {
							if (soundOn) {
								Audio.BEGINGAME.play();
							}
							playingEnv = false;
							playingOpt = false;
							playingEnd = true;
							onMenu = false;
							wipingEnvSave = false;
							wipingEndSave = false;
							viewingLog = false;
							mainmenu.nothingAnim = true;
							Audio.MENU.stop();
						} else
							if (xx > 548 && xx <= 578 && yy >= 259 && yy <= 289) {
								if (soundOn) {
									Audio.REALLY.play();
								}
								playingEnd = false;
								playingOpt = false;
								playingEnv = false;
								onMenu = false;
								wipingEnvSave = false;
								wipingEndSave = true;
								viewingLog = false;
								mainmenu.nothingAnim = true;
								Audio.MENU.stop();
							} else
								if (xx >= 388 && xx <= 578 && yy >= 299 && yy <= 329) {
									if (soundOn) {
										Audio.CLICK.play();
									}
									playingEnv = false;
									playingEnd = false;
									playingOpt = true;
									options.playAudio();
									onMenu = false;
									wipingEnvSave = false;
									wipingEndSave = false;
									viewingLog = false;
									mainmenu.nothingAnim = true;
									Audio.MENU.stop();
								} else
									if (xx >= 388 && xx <= 578 && yy >= 339 && yy <= 369) {
										quitGame();
									} else
										if (xx >= 388 && xx <= 578 && yy >= 419 && yy <= 449) {
											if (soundOn) {
												Audio.CLICK.play();
											}
											playingEnv = false;
											playingEnd = false;
											playingOpt = false;
											onMenu = false;
											wipingEnvSave = false;
											wipingEndSave = false;
											viewingLog = true;
											mainmenu.nothingAnim = true;
											Audio.MENU.stop();
											if (musicOn) {
												Audio.RLOG.loop();
											}
										} else
											if (yy >= 499 && yy <= 529) {
												try {
													Runtime.getRuntime().exec("cmd.exe /c start https://docs.google.com/forms/d/1fI-Hk3a6obh09oyUmu0Ffisha7YSSioPYvqTq5fQVk8/viewform");
												} catch (IOException e) {
													e.printStackTrace();
												}
											} else
												if (yy >= 459 && yy <= 489) {
													try {
														Runtime.getRuntime().exec("cmd.exe /c start https://sites.google.com/site/adoxian/blockery");
													} catch (IOException e) {
														e.printStackTrace();
													}
												}
			}

			private void pauseButton(int x, int y) {
				if (x < 720 || y < 30 || y > 60 || x > 930 || onMenu == true) {
					return;
				}

				if (playingEnv) {
					envgame.pause(x, y);
				} else
					if (playingEnd) {
						endgame.pause(x, y);
					}
			}

			private void itemButtons(int itemCol, int itemRow) {
				if (playingEnv) {
					envgame.items(itemCol, itemRow);
				} else
					if (playingEnd) {
						endgame.items(itemCol, itemRow);
					}
			}

			private void elimBlocks(int row, int column) {
				if (column < 0 || column > 9 || row < 0 || row > 13 || onMenu == true) {
					return;
				}
				if (playingEnv) {
					envgame.startElimBlocks(row, column);
				} else
					if (playingEnd) {
						endgame.startElimBlocks(row, column);
					}
			}

		});

		// TODO
		addMouseMotionListener(new MouseMotionListener() {

			int x;
			int y;

			public void mouseDragged(MouseEvent e) {

			}

			public void mouseMoved(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				mouseAtX = e.getX();
				mouseAtY = e.getY();

				if (playingOpt) {
					if (x >= 283 && x <= 483 && y >= 175 && y <= 210) {
						options.highlight = 0;
						frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} else
						if (x >= 283 && x <= 483 && y >= 225 && y <= 260) {
							options.highlight = 1;
							frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						} else
							if (x >= 283 && x <= 483 && y >= 302 && y <= 329) {
								options.highlight = 2;
								frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							} else
								if (x >= 488 && x <= 504 && y >= 175 && y <= 191) {
									options.switchesHighlight = 0;
									frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								} else
									if (x >= 488 && x <= 504 && y >= 205 && y <= 221) {
										options.switchesHighlight = 1;
										frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
									} else
										if (x >= 488 && x <= 504 && y >= 235 && y <= 251) {
											options.switchesHighlight = 2;
											frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										} else
											if (x >= 488 && x <= 504 && y >= 265 && y <= 281) {
												options.switchesHighlight = 3;
												frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
											} else
												if (x >= 488 && x <= 504 && y >= 295 && y <= 311) {
													options.switchesHighlight = 4;
													frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
												} else
													if (x >= 448 && x <= 518 && y >= 390 && y <= 420) {
														options.yesNoHighlight = 1;
														frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
													} else {
														options.highlight = -1;
														options.yesNoHighlight = -1;
														options.switchesHighlight = -1;
														frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
													}
				} else
					if (viewingLog) {
						if (x >= 448 && x <= 518 && y >= 390 && y <= 420) {
							relLog.yesNoHighlight = 1;
							frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						} else {
							relLog.yesNoHighlight = 2;
							frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
					} else
						if (wipingEndSave) {
							if (x >= 448 && x <= 518 && y >= 350 && y <= 380) {
								wipey.yesNoHighlight = 0;
								frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							} else
								if (x >= 448 && x <= 518 && y >= 390 && y <= 420) {
									wipey.yesNoHighlight = 1;
									frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								} else {
									wipey.yesNoHighlight = 2;
									frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								}
						} else
							if (wipingEnvSave) {
								if (x >= 448 && x <= 518 && y >= 350 && y <= 380) {
									envwipey.yesNoHighlight = 0;
									frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								} else
									if (x >= 448 && x <= 518 && y >= 390 && y <= 420) {
										envwipey.yesNoHighlight = 1;
										frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
									} else {
										envwipey.yesNoHighlight = 2;
										frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
									}
							} else
								if (wipingHighs) {
									if (x >= 448 && x <= 518 && y >= 350 && y <= 380) {
										wipeyHighs.yesNoHighlight = 0;
										frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
									} else
										if (x >= 448 && x <= 518 && y >= 390 && y <= 420) {
											wipeyHighs.yesNoHighlight = 1;
											frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										} else {
											wipeyHighs.yesNoHighlight = 2;
											frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
										}
								} else
									if (onMenu) {
										if (y >= 219 && y <= 249 && x >= 388 && x < 548) {
											mainmenu.highlight = 0;
											frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										} else
											if (y >= 219 && y <= 249 && x >= 548 && x <= 578) {
												mainmenu.highlight = 5;
												frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
											} else
												if (y >= 259 && y <= 289 && x >= 388 && x < 548) {
													mainmenu.highlight = 1;
													frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
												} else
													if (y >= 259 && y <= 289 && x >= 548 && x <= 578) {
														mainmenu.highlight = 6;
														frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
													} else
														if (y >= 299 && y <= 329 && x >= 388 && x <= 578) {
															mainmenu.highlight = 2;
															frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
														} else
															if (y >= 339 && y <= 369 && x >= 388 && x <= 578) {
																mainmenu.highlight = 3;
																frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
															} else
																if (y >= 419 && y <= 449 && x >= 388 && x <= 578) {
																	mainmenu.highlight = 4;
																	frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
																} else
																	if (y >= 459 && y <= 489) {
																		mainmenu.highlight = 7;
																		frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
																	} else
																		if (y >= 499 && y <= 529) {
																			mainmenu.highlight = 8;
																			frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
																		} else {
																			mainmenu.highlight = -1;
																			frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
																		}
									} else
										if (playingEnv) {
											if (x >= 448 && x <= 518 && y >= 350 && y <= 380) {
												envgame.yesNoHighlight = 0;
											} else
												if (x >= 448 && x <= 518 && y >= 390 && y <= 420) {
													envgame.yesNoHighlight = 1;
												} else
													if (x >= 720 && x <= 930 && y >= 30 && y <= 60 && !envgame.gameover && !envgame.pauseGame) {
														envgame.highlight = 0;
													} else
														if (x >= 510 && x <= 930 && y >= 225 && y <= 270 && !envgame.gameover && !envgame.pauseGame) {
															envgame.highlight = 1;
														} else
															if (x >= 510 && x <= 930 && y >= 285 && y <= 330 && !envgame.gameover && !envgame.pauseGame) {
																envgame.highlight = 2;
															} else
																if (x >= 510 && x <= 930 && y >= 345 && y <= 390 && !envgame.gameover && !envgame.pauseGame) {
																	envgame.highlight = 3;
																} else
																	if (x >= 510 && x <= 930 && y >= 405 && y <= 450 && !envgame.gameover && !envgame.pauseGame) {
																		envgame.highlight = 4;
																	} else
																		if (x >= 510 && x <= 930 && y >= 465 && y <= 510 && !envgame.gameover && !envgame.pauseGame) {
																			envgame.highlight = 5;
																		} else {
																			envgame.highlight = 6;
																			envgame.yesNoHighlight = 2;
																			frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
																		}
										} else
											if (playingEnd) {
												if (x >= 448 && x <= 518 && y >= 350 && y <= 380) {
													endgame.yesNoHighlight = 0;
												} else
													if (x >= 720 && x <= 930 && y >= 30 && y <= 60 && !endgame.pauseGame) {
														endgame.highlight = 0;
													} else
														if (x >= 510 && x <= 930 && y >= 225 && y <= 270 && !endgame.pauseGame) {
															endgame.highlight = 1;
														} else
															if (x >= 510 && x <= 930 && y >= 285 && y <= 330 && !endgame.pauseGame) {
																endgame.highlight = 2;
															} else
																if (x >= 510 && x <= 930 && y >= 345 && y <= 390 && !endgame.pauseGame) {
																	endgame.highlight = 3;
																} else
																	if (x >= 510 && x <= 930 && y >= 405 && y <= 450 && !endgame.pauseGame) {
																		endgame.highlight = 4;
																	} else
																		if (x >= 510 && x <= 930 && y >= 465 && y <= 510 && !endgame.pauseGame) {
																			endgame.highlight = 5;
																		} else {
																			endgame.highlight = -1;
																			endgame.yesNoHighlight = -1;
																			frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
																		}
											} else {
												frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
											}
				//End TODO
			}

		});
		setFocusable(true);
	}

	public void go() {

		if (!playingEnv) {
			if (envgame.smoke.size() > 0) {
				envgame.smoke.remove(envgame.smoke.size() - 1);
			}
		}
		if (playingEnv) {
			if (doReadFiles) {
				envfr.readFiles();
				hsfr.readScoreFiles();
				doReadFiles = false;
			}
			if (!doReadFiles) {
				envgame.autoSave();
				envgame.goCO2();
				envgame.goBlocks();
				envgame.goCounters();
				envgame.gravityBlocks();
			}
		} else
			if (playingEnd) {
				if (doReadFiles) {
					endfr.readFiles();
					doReadFiles = false;
				}
				if (!doReadFiles) {
					endgame.autoSave();
					endgame.go();
					endgame.gravityBlocks();
				}
			} else
				if (playingOpt) {
					if (noAudioOpt) {
						options.playAudio();
						noAudioOpt = false;
					}
				} else
					if (onMenu) {
						mainmenu.go();
					}
	}

	int count = 0;

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(235, 235, 238));
		g2d.fillRect(0, 0, getWidth(), getHeight());

		if (showingTitle) {
			optfr.readFiles();

			title.paint(g2d);
			count++;
			if (doInitialize) {
				if (count > 10 && !title.initialize) {
					title.initialize = true;
				}
				if (count > 80) {

				}
				if (count > 150 && title.initialize) {
					showingTitle = false;
					onMenu = true;
				}
			} else
				if (!doInitialize) {
					if (count > 40) {
						showingTitle = false;
						onMenu = true;
					}
				}
		}

		if (onMenu) {
			mainmenu.paint(g2d);
		}

		if (wipingEnvSave) {
			envwipey.paint(g2d);
		}
		if (wipingEndSave) {
			wipey.paint(g2d);
		}
		if (wipingHighs) {
			wipeyHighs.paint(g2d);
		}

		if (playingEnv) {
			envgame.paint(g2d);
		}

		if (playingEnd) {
			endgame.paint(g2d);
		}

		if (playingOpt) {
			options.paint(g2d);
		}

		if (viewingLog) {
			relLog.paint(g2d);
		}

		notiBar.paint(g2d);
		/*if (dimScreen) {
			if (dimAlpha < 150) {
				dimAlpha += 50;
				if (dimAlpha >= 255) {
					dimAlpha = 255;
				}
			}
			g2d.setColor(new Color(0, 0, 0, dimAlpha));
			g2d.fillRect(0, 0, frame.getWidth(), 568);
		}
		if (!dimScreen) {
			dimAlpha = 0;
		}*/
		//g2d.fillRect(mouseAtX - 3, mouseAtY - 3, 6, 6);
	}

	public void quitGame() {
		Audio.MENU.stop();
		Audio.PLAY.stop();
		Audio.WARNING.stop();
		if (soundOn) {
			Audio.CLOSE.play();
		}
		optfr.saveFiles();
		endfr.saveFiles();
		envfr.saveFiles();
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(ABORT);
	}

	public void maintain() {
		long lastTime = System.nanoTime();
		final double ns = 1_000_000_000.0 / 20.0;
		double delta = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				go();
				repaint();
				delta--;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Runner game = new Runner();
		game.frame.setTitle(Runner.barTitle);
		game.frame.add(game);
		game.frame.setSize(966, 568);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setResizable(false);

		game.maintain();
	}
}

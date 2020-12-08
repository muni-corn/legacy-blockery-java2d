package musicaflight.blockery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class HighScoreFiler {

	private Runner runner;

	Properties hsprops = new Properties();

	public HighScoreFiler(Runner runner) {
		this.runner = runner;
	}

	public void saveScoreFiles() {

		try {

			hsprops.setProperty("first", String.valueOf(runner.envgame.highScoreFirst));
			hsprops.setProperty("second", String.valueOf(runner.envgame.highScoreSecond));
			hsprops.setProperty("third", String.valueOf(runner.envgame.highScoreThird));
			hsprops.setProperty("fourth", String.valueOf(runner.envgame.highScoreFourth));
			hsprops.setProperty("fifth", String.valueOf(runner.envgame.highScoreFifth));

			hsprops.store(new FileOutputStream("highScores.properties"), null);

		} catch (FileNotFoundException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Could not find high scores. (java.lang.FileNotFoundException)";
			runner.notiBar.timer = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readScoreFiles() {
		try {
			hsprops.load(new FileInputStream("highScores.properties"));

			runner.envgame.highScoreFirst = Long.parseLong(hsprops.getProperty("first"));
			runner.envgame.highScoreSecond = Long.parseLong(hsprops.getProperty("second"));
			runner.envgame.highScoreThird = Long.parseLong(hsprops.getProperty("third"));
			runner.envgame.highScoreFourth = Long.parseLong(hsprops.getProperty("fourth"));
			runner.envgame.highScoreFifth = Long.parseLong(hsprops.getProperty("fifth"));

		} catch (FileNotFoundException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Could not find high scores. (java.lang.FileNotFoundException)";
			runner.notiBar.timer = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetScoreFiles() {
		runner.envgame.highScoreFirst = 0;
		runner.envgame.highScoreSecond = 0;
		runner.envgame.highScoreThird = 0;
		runner.envgame.highScoreFourth = 0;
		runner.envgame.highScoreFifth = 0;

		try {
			hsprops.setProperty("first", String.valueOf(runner.envgame.highScoreFirst));
			hsprops.setProperty("second", String.valueOf(runner.envgame.highScoreSecond));
			hsprops.setProperty("third", String.valueOf(runner.envgame.highScoreThird));
			hsprops.setProperty("fourth", String.valueOf(runner.envgame.highScoreFourth));
			hsprops.setProperty("fifth", String.valueOf(runner.envgame.highScoreFifth));

			hsprops.store(new FileOutputStream("highScores.properties"), null);

		} catch (FileNotFoundException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Could not find high scores. (java.lang.FileNotFoundException)";
			runner.notiBar.timer = 0;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			runner.notiBar.highScoresReset = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.timer = 0;
		}

	}

}

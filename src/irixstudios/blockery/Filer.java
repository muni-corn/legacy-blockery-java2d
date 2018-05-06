package irixstudios.blockery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Filer {

	private Runner runner;

	Properties props = new Properties();

	public Filer(Runner runner) {
		this.runner = runner;
	}

	public void saveFiles() {

		try {
			props.setProperty("cottOwn", String.valueOf(runner.endgame.cottOwn));
			props.setProperty("factOwn", String.valueOf(runner.endgame.factOwn));
			props.setProperty("powerOwn", String.valueOf(runner.endgame.powerOwn));
			props.setProperty("megaOwn", String.valueOf(runner.endgame.megaOwn));
			props.setProperty("doublerOwn", String.valueOf(runner.endgame.doublerOwn));
			//props.setProperty("spinsLeft", String.valueOf(runner.endgame.spinsLeft));
			//props.setProperty("totalSpins", String.valueOf(runner.endgame.totalSpins));

			runner.endgame.blocksNR = (Math.round(runner.endgame.blocksNR));
			props.setProperty("blocks", String.valueOf(runner.endgame.blocksNR));

			props.store(new FileOutputStream("endProg.properties"), null);

		} catch (FileNotFoundException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Could not find Endless progress, creating new game instead. (java.lang.FileNotFoundException)";
			runner.notiBar.timer = 0;
		} catch (IOException e) {

		} finally {
			runner.notiBar.timer = 0;
			runner.notiBar.endHasSaved = true;
			runner.notiBar.newNoti = true;
		}
	}

	public void readFiles() {
		try {
			props.load(new FileInputStream("endProg.properties"));

			runner.endgame.cottOwn = Integer.parseInt(props.getProperty("cottOwn"));
			runner.endgame.factOwn = Integer.parseInt(props.getProperty("factOwn"));
			runner.endgame.powerOwn = Integer.parseInt(props.getProperty("powerOwn"));
			runner.endgame.megaOwn = Integer.parseInt(props.getProperty("megaOwn"));
			runner.endgame.doublerOwn = Integer.parseInt(props.getProperty("doublerOwn"));
			//runner.endgame.spinsLeft = Integer.parseInt(props.getProperty("spinsLeft"));
			//runner.endgame.totalSpins = Integer.parseInt(props.getProperty("totalSpins"));

			runner.endgame.blocksNR = Double.parseDouble(props.getProperty("blocks"));

		} catch (NumberFormatException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Missing or illegal parameters in ''endProg.properties''. (java.lang.NumberFormatException)";
			runner.notiBar.timer = 0;
		} catch (FileNotFoundException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Could not find Endless progress, creating new game instead. (java.lang.FileNotFoundException)";
			runner.notiBar.timer = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetFiles() {
		runner.endgame.cottOwn = 0;
		runner.endgame.factOwn = 0;
		runner.endgame.powerOwn = 0;
		runner.endgame.megaOwn = 0;
		runner.endgame.doublerMulti = 1;
		//runner.endgame.spinsLeft = 0;
		//runner.endgame.totalSpins = 0;

		runner.endgame.blocksNR = 0;

		try {
			props.setProperty("cottOwn", String.valueOf(runner.endgame.cottOwn));
			props.setProperty("factOwn", String.valueOf(runner.endgame.factOwn));
			props.setProperty("powerOwn", String.valueOf(runner.endgame.powerOwn));
			props.setProperty("megaOwn", String.valueOf(runner.endgame.megaOwn));
			props.setProperty("doublerOwn", String.valueOf(runner.endgame.doublerOwn));
			//props.setProperty("spinsLeft", String.valueOf(runner.endgame.spinsLeft));
			//props.setProperty("totalSpins", String.valueOf(runner.endgame.totalSpins));

			runner.endgame.blocksNR = (Math.round(runner.endgame.blocksNR));
			props.setProperty("blocks", String.valueOf(runner.endgame.blocksNR));

			props.store(new FileOutputStream("endProg.properties"), null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			runner.notiBar.endHasReset = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.timer = 0;
		}

	}
}

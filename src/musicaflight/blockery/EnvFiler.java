package musicaflight.blockery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvFiler {

	private Runner runner;

	Properties envprops = new Properties();

	public EnvFiler(Runner runner) {
		this.runner = runner;
	}

	public void saveFiles() {

		try {
			envprops.setProperty("cottOwn", String.valueOf(runner.envgame.cottOwn));
			envprops.setProperty("factOwn", String.valueOf(runner.envgame.factOwn));
			envprops.setProperty("powerOwn", String.valueOf(runner.envgame.powerOwn));
			envprops.setProperty("megaOwn", String.valueOf(runner.envgame.megaOwn));
			envprops.setProperty("treeOwn", String.valueOf(runner.envgame.treeOwn));

			runner.envgame.blocksNR = (Math.round(runner.envgame.blocksNR));
			envprops.setProperty("blocks", String.valueOf(runner.envgame.blocksNR));
			runner.envgame.cO2 = (Math.round(runner.envgame.cO2));
			envprops.setProperty("cO2", String.valueOf(runner.envgame.cO2));

			envprops.store(new FileOutputStream("envProg.properties"), null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			runner.notiBar.envHasSaved = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.timer = 0;
		}
	}

	public void readFiles() {
		try {
			envprops.load(new FileInputStream("envProg.properties"));

			runner.envgame.cottOwn = Integer.parseInt(envprops.getProperty("cottOwn"));
			runner.envgame.factOwn = Integer.parseInt(envprops.getProperty("factOwn"));
			runner.envgame.powerOwn = Integer.parseInt(envprops.getProperty("powerOwn"));
			runner.envgame.megaOwn = Integer.parseInt(envprops.getProperty("megaOwn"));
			runner.envgame.treeOwn = Integer.parseInt(envprops.getProperty("treeOwn"));

			runner.envgame.blocksNR = Double.parseDouble(envprops.getProperty("blocks"));
			runner.envgame.cO2 = Double.parseDouble(envprops.getProperty("cO2"));

		} catch (NumberFormatException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Missing or illegal parameters in ''envProg.properties''. (java.lang.NumberFormatException)";
			runner.notiBar.timer = 0;
		} catch (FileNotFoundException e) {
			runner.notiBar.readFailed = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.errorMess = "Could not find Environment progress, creating new game instead. (java.lang.FileNotFoundException)";
			runner.notiBar.timer = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void resetFiles() {
		runner.envgame.cottOwn = 0;
		runner.envgame.factOwn = 0;
		runner.envgame.powerOwn = 0;
		runner.envgame.megaOwn = 0;
		runner.envgame.treeOwn = 0;

		runner.envgame.blocksNR = 0;
		runner.envgame.cO2 = 0;

		try {
			envprops.setProperty("cottOwn", String.valueOf(runner.envgame.cottOwn));
			envprops.setProperty("factOwn", String.valueOf(runner.envgame.factOwn));
			envprops.setProperty("powerOwn", String.valueOf(runner.envgame.powerOwn));
			envprops.setProperty("megaOwn", String.valueOf(runner.envgame.megaOwn));
			envprops.setProperty("treeOwn", String.valueOf(runner.envgame.treeOwn));

			runner.envgame.blocksNR = (Math.round(runner.envgame.blocksNR));
			envprops.setProperty("blocks", String.valueOf(runner.envgame.blocksNR));
			runner.envgame.cO2 = (Math.round(runner.envgame.cO2));
			envprops.setProperty("cO2", String.valueOf(runner.envgame.cO2));

			envprops.store(new FileOutputStream("envProg.properties"), null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			runner.notiBar.envHasReset = true;
			runner.notiBar.newNoti = true;
			runner.notiBar.timer = 0;
		}

	}

}

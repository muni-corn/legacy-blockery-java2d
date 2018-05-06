package irixstudios.blockery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OptionsFiler {

	Runner runner;

	Properties props = new Properties();

	public OptionsFiler(Runner runner) {
		this.runner = runner;
	}

	public void saveFiles() {

		try {
			props.setProperty("soundOn", String.valueOf(runner.soundOn));
			props.setProperty("musicOn", String.valueOf(runner.musicOn));
			props.setProperty("doInitialize", String.valueOf(runner.doInitialize));
			props.setProperty("useTimers", String.valueOf(runner.useTimers));
			props.setProperty("showClouds", String.valueOf(runner.showClouds));
			props.setProperty("showNums", String.valueOf(runner.showNums));
			props.setProperty("useNotiBar", String.valueOf(runner.useNotiBar));

			props.store(new FileOutputStream("options.properties"), null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			runner.notiBar.setsSaved = true;
			runner.notiBar.newNoti = true;
		}
	}

	public void readFiles() {
		try {
			props.load(new FileInputStream("options.properties"));

			runner.soundOn = Boolean.parseBoolean(props.getProperty("soundOn"));
			runner.musicOn = Boolean.parseBoolean(props.getProperty("musicOn"));
			runner.doInitialize = Boolean.parseBoolean(props.getProperty("doInitialize"));
			runner.useTimers = Boolean.parseBoolean(props.getProperty("useTimers"));
			runner.showClouds = Boolean.parseBoolean(props.getProperty("showClouds"));
			runner.showNums = Boolean.parseBoolean(props.getProperty("showNums"));
			runner.useNotiBar = Boolean.parseBoolean(props.getProperty("useNotiBar"));

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package irixstudios.blockery;

import java.applet.Applet;
import java.applet.AudioClip;

public class Audio {

	public static final AudioClip TUNE = Applet.newAudioClip(Audio.class.getResource("tuning.wav"));

	public static final AudioClip CLICK = Applet.newAudioClip(Audio.class.getResource("Select.wav"));
	public static final AudioClip BEGINGAME = Applet.newAudioClip(Audio.class.getResource("BeginGame.wav"));
	public static final AudioClip CLOSE = Applet.newAudioClip(Audio.class.getResource("Quit.wav"));
	public static final AudioClip CANCEL = Applet.newAudioClip(Audio.class.getResource("cancel.wav"));
	public static final AudioClip USEOPT = Applet.newAudioClip(Audio.class.getResource("optionOn.wav"));
	public static final AudioClip IGNOREOPT = Applet.newAudioClip(Audio.class.getResource("optionOff.wav"));

	public static final AudioClip SOUNDON = Applet.newAudioClip(Audio.class.getResource("soundison.wav"));

	public static final AudioClip REALLY = Applet.newAudioClip(Audio.class.getResource("yousure.wav"));
	public static final AudioClip OHWELL = Applet.newAudioClip(Audio.class.getResource("ohwell.wav"));

	public static final AudioClip PAUSED = Applet.newAudioClip(Audio.class.getResource("Paused.wav"));

	public static final AudioClip MENU = Applet.newAudioClip(Audio.class.getResource("MainScreen.wav"));
	public static final AudioClip PLAY = Applet.newAudioClip(Audio.class.getResource("GamePlay.wav"));
	public static final AudioClip OPTIONS = Applet.newAudioClip(Audio.class.getResource("options.wav"));
	public static final AudioClip RLOG = Applet.newAudioClip(Audio.class.getResource("rlog.wav"));

	public static final AudioClip BCLICK = Applet.newAudioClip(Audio.class.getResource("BlockClick.wav"));
	public static final AudioClip WARNING = Applet.newAudioClip(Audio.class.getResource("GameWarning.wav"));
	public static final AudioClip GAMEOVER = Applet.newAudioClip(Audio.class.getResource("gameover.wav"));
	public static final AudioClip HIGHSCORE = Applet.newAudioClip(Audio.class.getResource("newHigh.wav"));
}

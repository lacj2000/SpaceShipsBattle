package game;

import jplay.Sound;
import jplay.URL;

public class AudioEffect{
	private static Sound effect = null;

	public static void play(String archive) {
		stop();
		effect = new Sound(URL.audio(archive));
		effect.play();
		effect.setRepeat(true);
	}
	
	public static void stop() {
		if (effect != null) {
			effect.stop();
		}
	}
	
	
}

package utility;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;




public class SoundClipPlayer implements Runnable{
	private static final URL NULL_SOUND_URL = SoundClipPlayer.class.getClass().getResource("/res/audio/nullsound.wav");
	public static final SoundClipPlayer NULL_SOUND_PLAYER = new SoundClipPlayer(NULL_SOUND_URL);
	
	
	private final URL soundUrl;
	private final Clip soundClip;
	
	public SoundClipPlayer(URL givenUrl){
		soundUrl = givenUrl;
		soundClip = loadSoundClip(soundUrl);
	}
	public SoundClipPlayer(String filepath){
		this(SoundClipPlayer.class.getClass().getResource(filepath));
	}
	
	
	
	
	
	public void playSound(){
		soundClip.setFramePosition(0);
		soundClip.start();
	}
	
	private static Clip loadSoundClip(URL url){
		try{
			Clip clip = AudioSystem.getClip();			
			AudioInputStream audioIS = AudioSystem.getAudioInputStream(url);
			clip.open(audioIS);
			
			return clip;
		}
		catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	
	
	@Override
	public void run() {
		playSound();
		
	}
	
	
	
	
	
	public static void playSoundFromUrl(URL url){
		(new SoundClipPlayer(url)).playSound();
	}
	
	
	
	
	public static void main(String[] args) {
		URL url = SoundClipPlayer.class.getClass().getResource("/res/audio/thunk.wav");
		System.out.println(url);
		
		SoundClipPlayer sp = new SoundClipPlayer(url);
		
		sp.run();
		Pauser.pauseFor(2000);
		sp.run();
		Pauser.pauseFor(2000);
	}
}

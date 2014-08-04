package com.sports.runner;

import java.util.HashMap;

import processing.core.PApplet;
import apwidgets.APMediaPlayer;

public class GameSounds {
	
	  float volume = 1;
	  String extension="mp3";
	  HashMap<String,APMediaPlayer> sounds;
	  PApplet parent;
	  
	  public GameSounds(PApplet parent){
		  sounds =new HashMap<String,APMediaPlayer>();
		  this.parent=parent;
	  }
	  
	  
	  
	  public void loadSounds(String[] args,boolean loop){
		  for(int i=0;i<args.length;i++){
			  sounds.put(args[i],new APMediaPlayer(parent)); 
			  sounds.get(args[i]).setMediaFile("sounds/"+args[i]+"."+extension); 
			  sounds.get(args[i]).setLooping(loop); 
			  sounds.get(args[i]).setVolume((float)1*volume, (float)1*volume);
		 }
	  }
	  
	  
	  public void playSound(String soundName){
		  sounds.get(soundName).start();
	  }
	  
	  public void playSound(String soundName,float left, float right,float v){

		  sounds.get(soundName).setVolume(left*v, right*v);
		  sounds.get(soundName).start();
	  }
	  
}

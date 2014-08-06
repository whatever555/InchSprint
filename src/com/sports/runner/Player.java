package com.sports.runner;

public class Player extends Athlete{
	
	boolean raceOn=false;
	
	long serverTime=-1;
	int framesHit = 0;
	
	public Player(RaceClass parent,int track){
		super(parent,track);
		this.bot=false;

	}
	

		 public void moveMe(float moved){
			 super.moveMe(moved);
			 if(parent.hurdlesOn && distanceTravelled<parent.track.trackWidth)
				if((distanceTravelled)%(parent.track.ho)>parent.track.ho-parent.convertInchesToPixels((float)2) && 
						(distanceTravelled)%(parent.track.ho)<parent.track.ho-parent.convertInchesToPixels((float).3)){
					if(jumpY<parent.track.hurdleHeight/1.6 && jumping==false){
						String entry = "("+((int)((distanceTravelled+parent.convertInchesToPixels(3))/parent.track.ho))+"-1)";
						if(parent.track.knockedHurdles.indexOf(entry)==-1){
							
							parent.track.knockedHurdles+=entry;
							parent.gameSounds.playSound("hurdle_drop",1,1,1);
							hurdlesHit++;
							hurdleLag= moved/2;
							if(parent.practiceMode.equals("Hurdles")){
								finished=true;
								parent.endRace(false);
								
							}
						}
					}
				}
		 }
		 
	
	public void endRace(){
		
		if(!finished){
		finished=true;
		
		floatEndTime=parent.millis();
		longEndTime = System.nanoTime();
		myRaceTime=(float)((longEndTime-parent.longStartTime)/1000000000.0f);
		
		if(parent.training==false)
		parent.verifyTime(parent.millis(),System.nanoTime());
		System.gc();
		parent.endRace(true);
		
		//parent.noLoop();
		
		}
		
	}
	
	
	/*
	

	public void drawMe(){
		framesHit++;
		
		int myX=40;
		int myY=56;
		float div = (float)1.6;
		
		if(parent.runningSprites!=null)
		  parent.parent.image(parent.runningSprites[mySpriteNum][(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),(int)(spw/div),(int)(sph/div)); 
		  parent.fill(250);

		 
		  
	}*/
	
}

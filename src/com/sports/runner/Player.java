package com.sports.runner;

public class Player extends Athlete{
	
	boolean raceOn=false;
	
	long serverTime=-1;
	int framesHit = 0;
	
	
	public Player(RaceClass parent,int track){
		super(parent,track);
		this.bot=false;

		mySpriteNum=1;
	}
	

		 
	
	public void endRace(){
		
		if(!finished){
		finished=true;
		
		floatEndTime=parent.millis();
		longEndTime = System.nanoTime();
		myRaceTime=(float)((longEndTime-parent.longStartTime)/1000000000.0f);
		
		parent.verifyTime(parent.millis(),System.nanoTime());
		System.gc();
		parent.endRace();
		
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

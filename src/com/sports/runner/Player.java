package com.sports.runner;

import java.util.ArrayList;

public class Player extends Athlete{
	
	boolean raceOn=false;
	
	long serverTime=-1;
	int framesHit = 0;
	ArrayList<Float> sandBitsX;
	ArrayList<Float> sandBitsY;
	
	public Player(RaceClass parent,int track){
		super(parent,track);
		this.bot=false;

	}
	
	public void drawSittingPosition(){
		
		x = 3;
		y = 5;

		parent.moveY=0;
		parent.movingY=0;
		jumpingVelocity=0;
		atEaseSpeed=0;
		jumping=false;
		jumpY=0;
		
		drawSprites();
		//sandBlast();
		
	}
	
	
	public void gatherSand(float amt){
		amt/=100;
		sandBitsX= new ArrayList<Float>();
		sandBitsY= new ArrayList<Float>();
		for(int i=0;i<amt;i++){
			float xx = XOFF-amt + (parent.parent.random((amt)*2));
			float yy = (myY+mh)-amt/4 + (parent.parent.random((amt/2)));
			
			sandBitsX.add(xx);
			sandBitsY.add(yy);
		}

	}
	public void sandBlast(){
		
		for(int i=0;i<sandBitsX.size();i++){
			int ra = (int)parent.parent.random(5);
			int ra2 = 100+(int)parent.parent.random(150);
			parent.parent.fill(ra2,ra2/2,0);
			parent.parent.ellipse(sandBitsX.get(i),sandBitsY.get(i),ra,ra);
			sandBitsY.set(i,sandBitsY.get(i)+(3-parent.parent.random(7)));
			sandBitsX.set(i,sandBitsX.get(i)+(1-parent.parent.random(3)));
			if(sandBitsY.get(i)> (myY+mh)+20){
				sandBitsY.remove(i);
				sandBitsX.remove(i);
			}
			
		}
		if(sandBitsX.size()>0)
sandBitsX.remove(0);
moveMe(sandBitsX.size());
	}

		 public void moveMe(float moved){
			 

			 super.moveMe(moved);
			 
			 if(parent.hurdlesOn && distanceTravelled<parent.track.trackWidth  && distanceTravelled>parent.track.ho/2)
				if((distanceTravelled)%(parent.track.ho)>parent.track.ho-1
						||
						(distanceTravelled)%(parent.track.ho)<(mw*5)){
					if(jumpY<parent.track.hurdleHeight/1.2 && jumping==false){
						String entry = "("+((int)((distanceTravelled+parent.convertInchesToPixels(3))/parent.track.ho))+"-1)";
						if(parent.track.knockedHurdles.indexOf(entry)==-1){
							parent.parent.v.vibrate(100);
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
			 
			 
			 if(parent.longJumpOn==true && !finished){
					
					if((distanceTravelled)>parent.track.jo-(mw*5)){
						if(jumpY<=0){
							if((distanceTravelled)-(parent.track.jo)<0.05)
								longJumpLength=0;
							else{
							
							}
							drawSittingPosition();
						
							endRace();
							
						}
					}
					
				}
			 if(parent.longJumpOn && finished)
					drawSittingPosition();
		 }
		 
	
	public void endRace(){
		
		if(!finished){
		finished=true;
		
		floatEndTime=parent.millis();
		longEndTime = System.nanoTime();
		myRaceTime=(float)((longEndTime-parent.longStartTime)/1000000000.0f);
		if(parent.longJumpOn){
			jumpingVelocity=0;
			longJumpLength=parent.convertPixelsToInches((((distanceTravelled)-(parent.track.jo-(mw*5)))/5)+mw/5);
		  parent.raceStage=7;
		}
		else
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

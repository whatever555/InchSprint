package com.sports.runner;

import com.swarmconnect.Swarm;

import android.os.Bundle;
import android.view.KeyEvent;

public class BotRaceClass extends RaceClass{

	Game parent;
	
	Bot[] bots;
	float[] speeds;
	
	
	
	public BotRaceClass(Game parent) {
		super(parent);
		this.parent=parent;
		loadMe();
	}

	public void setMeUpOnce(){

		track=new Track(this,trackInInches,2,hurdlesOn);
		displayMessageBool=true;
		if(raceMode.equals("Time Trial") && !ghostType.equals("No Ghost Runner"))
			botCount = 1;
		bots = new Bot[botCount];
		for(int i=0;i<bots.length;i++){
			bots[i] = new Bot(this,i+2);
		}
		
		fastestTimeYet=999999999;
		
		if(raceMode.equals("Time Trial"))
		if(raceAgainst.equals("PB"))
		fastestTimeYet = parent.pbs[parent.getRaceIndex(trackInInches,hurdlesOn)];
		
		
		setMeUp();

		player = new Player(this,1);
		
	}
	
	
	public void setMeUp(){
		super.setMeUp();
		
		
		speeds=new float[botCount];
		finishTimes=new float[botCount];
		

		
		trackHeight = track.trackHeight;
		for(int i=0;i<bots.length;i++){
			bots[i].reset();
			float inchPerSec = 10 - parent.random(5);
			finishTimes[i] = (((trackInInches/difficulty)+(trackInInches/parent.max(difficulty,10))) - parent.random(trackInInches/difficulty+1));
			//float speed = track.distance/inchPerSec;
			speeds[i] = convertInchesToPixels(inchPerSec)/fps;
		}

		
		if(raceMode.equals("Time Trial") && !ghostType.equals("No Ghost Runner")){
			finishTimes[0]=fastestTimeYet;
			bots[0].alpha=80;
			bots[0].ghost=true;
		}
		
		
	}
	
public void soundShotBeginRace(boolean falseStart){
	
	super.soundShotBeginRace(falseStart);
	
	if(!raceBegan && raceCancelled==false && bots!=null)
	for(int i=0;i<bots.length;i++){
		if(bots[i]!=null)
		bots[i].startRace();
	}
}

public void drawBeginPositions(){
	track.moveMe(0);
	
	if(!setSaid){
		if(!(raceMode.equals("Time Trial") && fastestTimeYet == 999999999))
		for(int i=bots.length-1;i>=0;i--){
		
		bots[i].drawSetPosition();
	}
	player.drawSetPosition();
	}else{
		if(!(raceMode.equals("Time Trial") && fastestTimeYet == 999999999))
		for(int i=bots.length-1;i>=0;i--){
			bots[i].drawReadyPosition();
		}
		player.drawReadyPosition();
	}

	parent.noTint();
}
	
	public void updatePositions(){
		
		if(moveY==0)
			if(movingY>0)
			movingY-=(movingY/2);
			else
			movingY+=(parent.abs(movingY/2));
		else
			movingY=moveY;
		
		track.moveMe(movingY);
		

		moveBots();
		player.moveMe(movingY);
		parent.noTint();
		drawBotMarkers();
	}
	
	
	
	public void drawBotMarkers(){
		for(int i=0;i<bots.length;i++){
		 parent.noStroke();
		 if((bots[i].distanceTravelled>player.distanceTravelled))
			 parent.fill(parent.min(255,100+((bots[i].distanceTravelled-player.distanceTravelled)/50)),0,0);
		 else
		 parent.fill(255,parent.min(255,100+(player.distanceTravelled-bots[i].distanceTravelled)/50),0);
		parent.rect((parent.displayWidth/track.trackWidth)*bots[i].distanceTravelled,track.trackHeight+(track.trackHeight/9),(track.trackHeight/30),(track.trackHeight/15));
		}
	}
	
	
	
	public void moveBots(){
	
		
		for(int i=bots.length-1;i>-1;i--){
			
		
			float mSpeed=0;
			float distanceLeft = track.trackWidth - bots[i].distanceTravelled;
			float timeGone = (parent.millis()-floatStartTime)/1000;
			float timeLeft = finishTimes[i]-timeGone;
			//3000 50000
			if(timeLeft<=0.0){
				if(distanceLeft>=0)
					mSpeed=distanceLeft+2;
				else
				mSpeed=bots[i].atEaseSpeed;
			}
			else
				mSpeed=(distanceLeft/timeLeft)/(parent.frameRate);
			if(raceMode.equals("Time Trial") && !ghostType.equals("No Ghost Runner")){
if(fastestTimeYet!=999999999){
	
				bots[i].moveMe(mSpeed);
				parent.noTint();
}
			}else{

				bots[i].moveMe(mSpeed);
			}
			
			
			 
		}
		
	
	}
	
	
	
	  /*  
	   * public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	
	    botCount = getIntent().getExtras().getInt("Racers");
	    hurdlesOn = getIntent().getExtras().getBoolean("Hurdles");
	    trackInInches = getIntent().getExtras().getInt("Inches");
	    raceMode = getIntent().getExtras().getString("Race Mode");
	    Swarm.setActive(this);
	
	  
	   
	}
  */
	
	
}

package com.sports.runner;

import java.util.ArrayList;

import android.graphics.Color;

public class Athlete {
	
	ArrayList<Float> last3Speeds;
	float jumpingVelocity=0;
	float longJumpLength=0;
	RaceClass parent;
	int track;
	boolean bot;
	float distanceTravelled;
	float x;
	int y;
	int mx;
	int my;
	boolean jumping;
	boolean landed;
	boolean finished=false;

	int alpha=255;
	float hurdleLag=0;
	
int hurdlesHit=0;
	int XOFF=45;
	
	float jumpY=0;
	float jumpingTargetHeight=0;
	boolean ghost=false;

	int bodyCol;
	int tshirtCol;
	int shortsCol;
	int shoesCol;
	int outlineCol;
	
	float atEaseSpeed;
	
	
	float speed=0;
	float targetTime = 0; // used to calculate cpu speed

	

	long serverEndTime;
	 long longEndTime;
	 float floatEndTime;
	
	
	public Athlete(RaceClass parent,int track){
		
		this.parent=parent;
		this.track=track;
		this.bot=true;
		atEaseSpeed=(70-parent.random(50));
		reset();
		
		int bcol = rand(255);
		int bcol2=parent.parent.max(bcol-20,rand(bcol));
		int bcol3=parent.parent.max(bcol2-19,rand(bcol2));
		bodyCol = Color.rgb(bcol,bcol2,bcol3);
		tshirtCol = Color.rgb(rand(255),rand(255),rand(255));
		shortsCol = Color.rgb(rand(255),rand(255),rand(255));
		int scol=rand(255);
		shoesCol = Color.rgb(scol,scol,scol);
		outlineCol = Color.rgb(0,0,0);
		
	}
	int myY=-200;
	float div =1;

	int spw = 120;
	int sph=148;
	int mh=(int) (sph/div);
	int mw=(int) (spw/div);
	public void reset(){
		jumpingVelocity=0;
		longJumpLength=0;
		last3Speeds=new ArrayList<Float>();
		int lastHowMany = 10;
		if(parent.longJumpOn)
			lastHowMany=20;
		for(int i=0;i<lastHowMany;i++){
		last3Speeds.add((float) 0.0);
		}
		
		hurdlesHit=0;
		div = (float)1.7+((float)(track)/(float)8);
		mh = (int)((float)sph/div);
		myY=(int) ((parent.trackHeight/((float)(track+2)/3))-(mh));
		myY = (int) ((myY+((parent.trackHeight/((float)(track+3)/3))-(mh)))/2);
		mw = (int)(spw/div);
		x=0;
		y=0;
		mx=6;
		my=5;
		jumping=false;
		landed=false;
		distanceTravelled=0;
		finished=false;
	}
	
	boolean onScreen=false;
	
	public int rand(int r){
		return (int)parent.random(r);
	}
	
	
	public void moveMe(float moved){
		
		if(jumpingVelocity<=0){
			jumpY=0;
			jumping=false;
		}else{
			jumpingVelocity-=3;
		}
		
		
		if(jumpY>0 && track==1){
			moved=jumpingVelocity;
			parent.parent.println("VELOCITY: "+jumpingVelocity);
		}
		
		
		if(hurdleLag>0){
			moved-=parent.parent.min(hurdleLag,moved);
			hurdleLag-=4;
		}
		
		last3Speeds.remove(0);
		last3Speeds.add(moved);
		
		x+=moved/20;
		distanceTravelled+=moved;
		
		if(distanceTravelled > (parent.track.trackWidth)){
			
			endRace();
		  
		}
		
		if(x>=mx){
			x=0;
			y++;
		}
		if(y>=my){
			y=0;
		}
		if(x<0){
			x=mx-1;
			y--;
		}
		if(y<0)
		{
		y=my-1;	
		}
		
	
		
		if(jumpY<jumpingTargetHeight-1 && jumping==true){
			jumpY+=(jumpingTargetHeight/3);
		}else{
			int jumpLandInt=14;
			if(parent.longJumpOn)
			jumpLandInt=3;
			if(jumpY>jumpLandInt){
				jumpY-=(jumpY/2);
			}else{
				
				jumpY=0;	
			}
			jumping=false;
		}
		
		if(jumpY>0){
			x=0;
			y=5;
			
			
		}
		
		drawMe();
	}
	
	
	public void startRace(){
		x=0;
		y=0;
		jumping=false;
		landed=false;
		finished=false;
		distanceTravelled=0;
		
	}
	float myRaceTime;
	public void endRace(){
		
		if(!finished){
		finished=true;
		
		floatEndTime=parent.millis();
		longEndTime = System.nanoTime();
		myRaceTime=(float)((longEndTime-parent.longStartTime)/1000000000.0f);
		
		if(parent.raceMode.equals("Time Trial") && track>1 ){
			System.out.println("track = "+track);
			System.out.println(parent.fastestTimeYet+" BOT was supposedto finish IN: "+(parent.finishTimes[track-2]));
		}
			
		System.out.println("BOT_ FINISHED THE RACE IN: "+((floatEndTime-parent.floatStartTime)/1000));
		if(!parent.player.finished)
			parent.playerPosition++;
		}
		
	}
	
	
	
	
	
	
	
	public void drawSetPosition(){
		
		x = 1;
		y = 5;
		
		
		
		
		
		drawSprites();
		
		
	}
	
public void drawReadyPosition(){
		
		x = 2;
		y = 5;
	
		drawSprites();
		
		
	}
	
	
	public void drawMe(){
	
		 onScreen=false;
		
		
		drawSprites();
		
		
	}
	
	public void drawSprites(){
		
		int myX = (int) ((distanceTravelled-parent.player.distanceTravelled)/5);
		myX+=XOFF;
		myX+=(parent.player.mw-mw);
		
		 float offX = (float)(myX/2.8);
		 float nearX = myX-offX;
		 myX = (int) (nearX+((offX/parent.track.barSize)*(myY-parent.track.trackHeight/parent.track.divAmt)));
		 if(myX+(int)(mw)>0&&myX<parent.parent.displayWidth){
			 onScreen=true;
		if(parent.shoeSprites!=null){
			parent.parent.tint(255,parent.parent.min(alpha,parent.parent.max(1,130-track*3)));
			int mxh=parent.parent.max(1,18-track);
			float mw2=(float) (mw*1+(parent.shadowWidth));
			parent.parent.image(parent.shadow,myX+(mw/2)-(mw2/2), myY+mh-mxh/2,mw2,mxh); 
		
			parent.parent.tint(tshirtCol,alpha);
		    parent.parent.image(parent.tshirtSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(shortsCol,alpha);
		    parent.parent.image(parent.shortsSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(bodyCol,alpha);
		    parent.parent.image(parent.bodySprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(shoesCol,alpha);
		    parent.parent.image(parent.shoeSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(tshirtCol,alpha);
		    parent.parent.image(parent.outlineSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.rect(myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh/5); 
		    
		}
	    
	     
		}
	}

}

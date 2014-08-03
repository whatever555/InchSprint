package com.sports.runner;

import android.graphics.Color;
import processing.core.PImage;

public class Athlete {
	
	
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

	float jumpY=0;
	float jumpingTargetHeight=0;
	
	int mySpriteNum;

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
		
		int bcol = rand(200);
		bodyCol = Color.rgb(255-bcol,240-bcol,240-bcol);
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

		mySpriteNum = (int) (parent.random(parent.totalSpritesOnDisk));
		
		if(mySpriteNum<=0)
			mySpriteNum=0;
		
		if(mySpriteNum>parent.totalSpritesOnDisk-1)
		mySpriteNum=parent.totalSpritesOnDisk-1;
	}
	
	
	
	public int rand(int r){
		return (int)parent.random(r);
	}
	
	
	public void moveMe(float moved){
		
		
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
			jumpY+=(jumpingTargetHeight)/3;
		}else{
			
			if(jumpY>20){
				jumpY-=(jumpY/3);
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
		
		System.out.println("BOT_ FINISHED THE RACE IN: "+((floatEndTime-parent.floatStartTime)/1000));
		if(!parent.player.finished)
			parent.playerPosition++;
		}
		
	}
	
	
	
	
	
	public void drawSetPosition(){
		
		x = 1;
		y = 5;
		
		
		
		//	div = (float) ((div)*((float)track+(-((float)(0.3)))));
			int myX = (int) ((distanceTravelled-parent.player.distanceTravelled)/5);
			

		myX+=40;
		myX+=(parent.player.mw-mw);
		if(myX+(int)(spw/div)>0&&myX<parent.parent.displayWidth){
			drawSprites(myX);
		}
		
	}
	
public void drawReadyPosition(){
		
		x = 2;
		y = 5;
	int myX = (int) ((distanceTravelled-parent.player.distanceTravelled)/5);
			
		myX+=40;
		myX+=(parent.player.mw-mw);
		if(myX+(int)(spw/div)>0&&myX<parent.parent.displayWidth){
			drawSprites(myX);
		}
		
	}
	
	
	public void drawMe(){
	
		
		int myX = (int) ((distanceTravelled-parent.player.distanceTravelled)/5);
		myX+=40;
		myX+=(parent.player.mw-mw);
		
		
		if(myX+(int)(spw/div)>0&&myX<parent.parent.displayWidth){
			drawSprites(myX);
		}
		
	}
	
	public void drawSprites(int myX){
		if(parent.shoeSprites!=null){
		//	parent.parent.tint(255,parent.parent.max(1,130-track*3));
			int mxh=parent.parent.max(1,18-track);
			parent.parent.image(parent.shadow,myX-15, myY+mh-mxh/2,(float) (mw*1.5),mxh); 
		
			parent.parent.tint(tshirtCol);
		    parent.parent.image(parent.tshirtSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(shortsCol);
		    parent.parent.image(parent.shortsSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(bodyCol);
		    parent.parent.image(parent.bodySprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(shoesCol);
		    parent.parent.image(parent.shoeSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		    parent.parent.tint(tshirtCol);
		    parent.parent.image(parent.outlineSprites[(int)x][y],myX, myY-parent.parent.min(jumpY,parent.track.hurdleHeight+5),mw,mh); 
		  
	    
	     
		}
	}

}

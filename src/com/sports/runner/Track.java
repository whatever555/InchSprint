package com.sports.runner;

import android.graphics.Color;
import processing.core.PGraphics;
import processing.core.PImage;

public class Track {

RaceClass parent;
int distance;
PImage trackImage;
PImage trackImageFlat;
float offsetY=0;
float offsetX=0;
int trackHeight = 180;
float trackWidth = 0;

float XDIV=5;
float interfaceWidth=100;
float interfaceHeight=100;

float ho;
float lo;

int skyColour = Color.rgb(200,200,255);
int trackY;
int trackX;

String knockedHurdles="";
String knockedHurdlesTT="";
int trackDisplayWidth;
int trackDisplayHeight;
boolean hurdlesOn =false;

int lineCount = 200;
int hurdleCount=0;
int hurdleHeight=60;

int colorIntensity;
//PGraphics trackPG;
int pdh,pdw;
int dw;
float m;
int hurdDistributionSize;
int lineDistributionSize;



float topY;
float bottomY;
float barSize;


 public Track(RaceClass parent,int distance,int trackType,boolean hurdlesOn){
	 hurdDistributionSize=25;
	 lineDistributionSize=25;
	 m = (float)1.5;//sumthin to do with hurdles
	 int rand = (int)parent.random(200);
	 if(rand>50){
		 rand = (int)parent.random(200);
	 }
	 skyColour = Color.rgb(200-rand,200-rand,255-rand);
	this.hurdlesOn=hurdlesOn;
	 this.parent=parent;
	 dw = parent.parent.displayWidth;
	 this.distance=distance;
	 trackImage=parent.loadImage("images/tracks/"+trackType+".jpg");
	 trackImageFlat=parent.loadImage("images/tracks/flat_"+trackType+".jpg");
	 
	 trackWidth=parent.convertInchesToPixels(distance);

	 trackDisplayWidth = parent.parent.displayWidth;//(int) parent.convertInchesToPixels(3);
	 trackDisplayHeight = (int) parent.convertInchesToPixels(2);

	 pdw=parent.parent.displayWidth/2;
	 pdh=parent.parent.displayHeight/2;
	 trackY=(parent.parent.displayHeight)-(trackDisplayHeight);
	 trackX=(pdw)-(trackDisplayWidth/2);
	 
	 trackHeight = trackY;
	 
	 
	 colorIntensity=(int) (100+parent.parent.random(155));
	 
	 if(hurdlesOn)
		 hurdleCount = (int) (distance/hurdDistributionSize);
	 
	// lineCount = (int) (distance/lineDistributionSize);
	 
	 parent.parent.println("HURDLE COUNT: "+hurdleCount +" TRACKWIDTH: "+trackWidth+" ");
	 ho = parent.convertInchesToPixels(hurdDistributionSize);
	 lo = parent.convertInchesToPixels(hurdDistributionSize);
	 
	 topY=(trackHeight/divAmt)-hurdleHeight;
	 bottomY=trackHeight-hurdleHeight;
	 barSize = bottomY-topY;
	 knockedHurdles="";
 }
 
 public void moveMe(float y){
	 offsetY+=y;
	 offsetX+=(y/XDIV);
	 
	 if(offsetX>parent.parent.displayWidth*2){
		 offsetX-=parent.parent.displayWidth*2;
	 }
	 if(offsetX<-0){
		 offsetX+=parent.parent.displayWidth*2;
	 }
	 
	 if(offsetY>trackDisplayHeight){
		 offsetY-=trackDisplayHeight;
	 }
	 if(offsetY<-0){
		 offsetY+=trackDisplayHeight;
	 }
	 
	 drawMe();
 }
 
 public void drawMe(){
	 drawTracks();
 }
 float divAmt=10;
 public void drawTracks(){

		divAmt=(parent.parent.max(18,parent.botCount+4))/3;
	 for(int i=0;i<3;i++){
		 parent.noFill();
		// trackPG.beginDraw();
		// trackPG.image(trackImageFlat,0,(0+offsetY),trackDisplayWidth,trackDisplayHeight);
		 //trackPG.image(trackImageFlat,0,(trackDisplayHeight+offsetY),trackDisplayWidth,trackDisplayHeight);
		/// trackPG.image(trackImageFlat,0,((-trackDisplayHeight)+offsetY),trackDisplayWidth,trackDisplayHeight);
//trackPG.endDraw();
	//	 parent.parent.imageMode(parent.parent.CENTER);

		 parent.parent.image(trackImageFlat,trackX,trackY+(0+offsetY),(float) (trackDisplayWidth*1.5),trackDisplayHeight);
		 parent.parent.image(trackImageFlat,trackX,trackY+(trackDisplayHeight+offsetY),(float) (trackDisplayWidth*1.5),trackDisplayHeight);
		 parent.parent.image(trackImageFlat,trackX,trackY+((-trackDisplayHeight)+offsetY),(float) (trackDisplayWidth*1.5),trackDisplayHeight);
		// parent.parent.image(trackPG, parent.parent.displayWidth/2, parent.parent.displayHeight/2);
		 colorTrack(100);
		 parent.parent.rect(trackX,trackY,(float) (trackDisplayWidth*1.5),trackDisplayHeight);
			 
		 parent.fill(0);
		 parent.parent.rect(0,0,parent.parent.displayWidth,(trackY));
		 parent.parent.rect(0,(trackY)+(trackDisplayHeight),parent.parent.displayWidth,parent.parent.displayHeight-((trackY)+(trackDisplayHeight)));
		 
		// parent.parent.imageMode(parent.parent.CORNER);
		 parent.parent.image(trackImage,0-(offsetX),0,parent.parent.displayWidth*2,trackHeight);
		 parent.parent.image(trackImage,(parent.parent.displayWidth*2-(offsetX)),0,parent.parent.displayWidth*2,trackHeight);
		 parent.parent.image(trackImage,(-(parent.parent.displayWidth*2))-(offsetX),0,parent.parent.displayWidth*2,trackHeight);
		 
		 colorTrack(105);
		 parent.parent.rect(0,0, parent.parent.displayWidth,trackHeight);
		 }

	 
	colorTrack(150);
	 
	 parent.parent.strokeWeight(0);
	 parent.parent.rect(0,0,dw,trackHeight);
	
	 parent.parent.strokeWeight(0);
	
	 
	 colorTrack(255);
	 parent.parent.rect(0,trackHeight,dw,(trackHeight/9));
	 parent.fill(0);
	 parent.parent.rect(0,trackHeight+(trackHeight/9),dw,(trackHeight/15));
	 
	
	 drawTrackVertiLines();
	 
	
	 parent.parent.noStroke();
	 parent.fill(skyColour);
	 parent.parent.rect(0,0,dw,(trackHeight/divAmt));
	 
	 
	 lastYY=-1;
	 
	 
	 int hurdleID=0;
	 for(float i=3;i<=parent.parent.max(18,(parent.botCount+3));i++){
	
		 parent.parent.stroke(255,150);
		 parent.parent.strokeWeight(0);
		 parent.parent.line(0,(trackHeight/(i/3)),dw,(trackHeight/(i/3)));
		
		 if(hurdlesOn){
		 parent.parent.stroke(255);
		 parent.parent.strokeWeight(5);
		 drawHurdleStick((trackHeight/(i/3)),hurdleID);
		 hurdleID++;
		 }
		
	 }
	 
	 if(hurdlesOn){
		// drawHurdles();
		// parent.parent.line((int)(dw*m-offsetX)+30,trackHeight-(trackHeight/9)-(hurdleHeight),(int)(dw*m-offsetX)+30,trackHeight+(trackHeight/9));
			
	}
		
	 parent.parent.noStroke();
	 parent.fill(0,125,0);
	 parent.parent.rect(0,trackHeight+(trackHeight/9),(int)((float)((float)((float)dw/distance)*(parent.convertPixelsToInches(parent.player.distanceTravelled)))),(trackHeight/15));
		
	 
	  
	 
	 
 }
 
 public void reset(){
	 
	 knockedHurdles="";
 }
 public void colorTrack(int alpha){
	 parent.fill(colorIntensity,0,0,alpha);
	 if(parent.training==true)
		parent.fill(0,colorIntensity/2,0,alpha);
	 else if(parent.raceMode.equals("Time Trial"))
		parent.fill(colorIntensity,colorIntensity/2,0,alpha);
 }

 
 float lastYY;
 public void drawHurdleStick(float yy,int hurdleID){
	 
	 
	 if(lastYY!=-1){
	for(int i=1;i<=hurdleCount;i++){

		parent.parent.stroke(240,255);
		 float hurdleX = (ho*i);
		 float hurdleDispX = (hurdleX/XDIV-(parent.player.distanceTravelled/XDIV) );
		 float offX = (float)(hurdleDispX/2.8);
		 float nearX = hurdleDispX-offX;
		 
		if(nearX<dw+parent.player.XOFF+20 && hurdleDispX+parent.player.XOFF>-20){
		 
		 
	parent.parent.strokeWeight(3);
	float compareVar = (yy-(float)trackHeight/(float)divAmt);
		float hh1 = (float) (((float)hurdleHeight/barSize)*(compareVar+(barSize-compareVar)/3.5));
	
		 float newX = nearX+((offX/barSize)*(yy-trackHeight/divAmt));
		 
		 float ke=0;//for knocked hurdles
		 if(knockedHurdles.indexOf("("+i+"-"+hurdleID+")")>-1){
			 ke=hh1;
		 }
		 //upright
		 parent.parent.line(newX+2,yy+2,newX+2+ke,yy-hh1+ke);
	
		 compareVar = (lastYY-(float)trackHeight/(float)divAmt);
		 float hh2 = (float) (((float)hurdleHeight/barSize)*(compareVar+(barSize-compareVar)/3.5));
		 float newX2 = nearX+((offX/barSize)*(lastYY-trackHeight/divAmt));
		 
		 
		 //upright
		 parent.parent.line(newX2-2,lastYY-2,newX2-2+ke,lastYY-2-hh2+ke);
		 
		 //bar
		 if(hurdleID>0){

				parent.parent.strokeWeight(5);
		 parent.parent.line(newX+2+ke,yy-hh1+ke,newX2-2+ke,lastYY-2-hh2+ke);

			parent.parent.strokeWeight(2);
			parent.parent.stroke(200,100);
		 parent.parent.line(newX+2+ke,yy-(hh1/2)+ke,newX2-2+ke,lastYY-2-(hh2/2)+ke);
		 }
		} 
	}
	 
	 }
	 lastYY=yy;
	 
	/* float hurldeSX = (int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30;
	 float hurldeSY = (trackHeight/divAmt)-hurdleHeight;
	 
	 parent.parent.line(hurdleSX,hurdleSY,(int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30,(trackHeight/divAmt));
		*/
 }

public void drawTrackVertiLines(){
	// parent.parent.line((int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30,(trackHeight/divAmt),(int)(dw*m-offsetX)+30,trackHeight);
	// parent.parent.line((int)(dw*m-offsetX)+30,trackHeight,(int)(dw*m-offsetX)+30,trackHeight+(trackHeight/9));
	  
	 for(int i=0;i<lineCount;i++){
	 
	 float lineX = (lo*i);
	 float lineDispX = (lineX/XDIV-(parent.player.distanceTravelled/XDIV) );
	 float offX = (float)(lineDispX/2.8);
	 float nearX = lineDispX-offX;
	 
	if(nearX<dw+parent.player.XOFF+20 && lineDispX+parent.player.XOFF>-20){
		parent.parent.strokeWeight(8);
	 parent.parent.stroke(255,120);
	 float newX = nearX+((offX/barSize)*(trackHeight-trackHeight/divAmt));
	 parent.parent.line(nearX,(float)trackHeight/(float)divAmt,newX,trackHeight);
	 parent.parent.line(newX,trackHeight,newX,trackHeight+trackHeight/9);
	 
	}
	 }
}


public void drawFinishLine(){
	
}
}

package com.sports.runner;

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

float interfaceWidth=100;
float interfaceHeight=100;

int trackY;
int trackX;

int trackDisplayWidth;
int trackDisplayHeight;
boolean hurdlesOn =false;
int hurdleHeight=40;

int colorIntensity;
//PGraphics trackPG;
int pdh,pdw;
 public Track(RaceClass parent,int distance,int trackType,boolean hurdlesOn){
	
	this.hurdlesOn=hurdlesOn;
	 this.parent=parent;
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
 
 }
 
 public void moveMe(float y){
	 offsetY+=y;
	 offsetX+=(y/5);
	 
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

	 int dw = parent.parent.displayWidth;
	colorTrack(150);
	 
	 parent.parent.strokeWeight(0);
	 parent.parent.rect(0,0,dw,trackHeight);
	 parent.parent.stroke(200,120);
	 parent.parent.strokeWeight(2);
	 for(float i=3;i<=parent.parent.max(18,(parent.botCount+3));i++){
		 parent.parent.line(0,(trackHeight/(i/3)),dw,(trackHeight/(i/3)));
	 }
	 parent.parent.strokeWeight(0);
	 parent.fill(200,200,255);
	 parent.parent.rect(0,0,dw,(trackHeight/divAmt));
	 
	 colorTrack(255);
	 parent.parent.rect(0,trackHeight,dw,(trackHeight/9));
	 parent.fill(0);
	 parent.parent.rect(0,trackHeight+(trackHeight/9),dw,(trackHeight/15));
	 
	 parent.parent.strokeWeight(7);
	 parent.parent.stroke(255,60);
	
	 float m = (float)1.5;
	parent.parent.line((int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30,(trackHeight/divAmt),(int)(dw*m-offsetX)+30,trackHeight);
	 parent.parent.line((int)(dw*m-offsetX)+30,trackHeight,(int)(dw*m-offsetX)+30,trackHeight+(trackHeight/9));
	  
	 
	 if(hurdlesOn){
	 parent.parent.strokeWeight(10);
	 parent.parent.stroke(255,255);
		parent.parent.line((int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30,(trackHeight/divAmt)-hurdleHeight,(int)(dw*m-offsetX)+30,trackHeight-hurdleHeight);
		
		
		 parent.parent.strokeWeight(8);
		 parent.parent.stroke(255,255);
		parent.parent.line((int)(dw*m-offsetX)+30,trackHeight-hurdleHeight,(int)(dw*m-offsetX)+30,trackHeight+(trackHeight/9));
		parent.parent.strokeWeight(4);
		parent.parent.line((int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30,(trackHeight/divAmt)-hurdleHeight,(int)(dw*m-offsetX-(dw/2-(offsetX/2.8)))+30,(trackHeight/divAmt));
		}
		
	 parent.parent.noStroke();
	 parent.fill(0,125,0);
	 parent.parent.rect(0,trackHeight+(trackHeight/9),(int)((float)((float)((float)dw/distance)*(parent.convertPixelsToInches(parent.player.distanceTravelled)))),(trackHeight/15));
		
	 
	  
	 
	 
 }
 public void colorTrack(int alpha){
	 parent.fill(colorIntensity,0,0,alpha);
	 if(parent.training==true)
	 parent.fill(0,0,colorIntensity,alpha);	
	 else if(parent.raceMode.equals("Time Trial"))
		parent.fill(0,colorIntensity/2,0,alpha);
 }

 
}

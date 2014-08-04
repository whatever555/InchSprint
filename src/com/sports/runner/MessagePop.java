package com.sports.runner;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import android.graphics.Color;

public class MessagePop {
	Game parent;
	String header;
	ArrayList<String> optionsStr;
	ArrayList<Boolean> optionsButs;
	ArrayList<Integer> txtSizes;
	String type;
	int x=0;
	int y=0;
	int w=0;
	int h=0;
	int yOff=10;
	int strHeight=30;
	boolean active;
	int alpha = 250;
	MyCallback callback;
	int creationMilliTime;
	int minLifeMillis;
	int style;
	int format;
	int bgCol;
	
	public MessagePop(Game parent,String header,MyCallback callback,int style,int format){
		initMe(parent,header,callback,style,format);
	}
	public MessagePop(Game parent,String header,MyCallback callback){
		initMe(parent,header,callback,1,1);
	}
	
	public void initMe(Game parent,String header,MyCallback callback,int format,int style){
		this.type=type;
		this.style=style;
		this.parent=parent;
		this.header=header;
		optionsStr=new ArrayList<String>();
		optionsButs=new ArrayList<Boolean>();
		txtSizes=new ArrayList<Integer>();
		x=parent.displayWidth/24;
		w=parent.displayWidth-x*2;
		this.callback=callback;
		creationMilliTime=parent.millis();
		minLifeMillis = 1000;
		bgCol=Color.argb(0,0,100,alpha);
		if(style==2)
			bgCol=Color.argb(100,100,250,alpha);
		if(style==3)
			bgCol=Color.argb(100,2500,100,alpha);
		
	}
	
	public void addOption(String str,Boolean isBut,int txtSize){
		optionsStr.add(str);
		optionsButs.add(isBut);
		txtSizes.add(txtSize);
		h=(optionsButs.size()*30)+yOff*2;
		y=(parent.displayHeight-h)/2;
	}
	
	public void showMe(){
		if(active){
			
			if(format==2){
				parent.textAlign(parent.CENTER);
				yOff=3;
				y = parent.displayHeight-(h+parent.displayHeight/12);
			}
			if(format==3){

				yOff=3;
				y = parent.displayHeight-((h*2)+parent.displayHeight/24);
			}
				parent.textFont(parent.messageFont);
				
			parent.noStroke();
			parent.fill(0,200);
			parent.rect(0,0,parent.displayWidth,parent.displayHeight);
			
			parent.textFont(parent.messageFont);
		parent.fill(bgCol);
		parent.strokeWeight(2);
		parent.stroke(255,alpha);
		
		parent.rect(x,y,w,h,7);
		

		parent.noStroke();
		drawStrings();
			
		
		
		}
		
	}
	
	public void callCloseFunction() throws Exception{

		parent.println("callable CLOSE function ");
		callback.onMessageClose();
	}
	
	
	public void drawStrings(){


		parent.textAlign(parent.LEFT,parent.TOP);
		parent.textSize(12);
		//parent.textAlign(parent.LEFT);
		for(int i=0;i<optionsStr.size();i++){

			parent.fill(220,alpha);
			
			parent.textSize(txtSizes.get(i));
		

			if(optionsButs.get(i)){
				//parent.fill(0,0,110,alpha);
				//parent.rect(x,y+yOff+(i*strHeight),w,strHeight,3);

				//parent.fill(220,alpha);

				parent.fill(250,alpha);
			}
			parent.text(optionsStr.get(i),x*2,y+yOff+(i*strHeight)+(strHeight/2));
			
			
		}
	}
}

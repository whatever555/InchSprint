package com.sports.runner;

import android.graphics.Color;


public class MenuButton {

	MenuClass parent;
	int index=0;
	int bgCol;
	int bgColPressed;
	int txtCol;
	String text="";
	int y=0;
	int x = 0;
	int w;
	int h;
	int tx,ty,th,tw;
	int textX,tTextX;
	boolean pressed=false;
	
	int LOCKED;
	boolean tweening=false;
	
	public MenuButton(MenuClass parent,String text,int index,int LOCKED){
		this.parent=parent;
		this.LOCKED=LOCKED;
		
		this.text=text;
		bgCol = Color.rgb(20,20,120);
		bgColPressed = Color.rgb(0,0,100);
		txtCol = Color.rgb(255,255,255);
		this.index=index;
		ty = (index * (parent.buttonHeight)) + ((parent.buttonPaddingTop));
		tx = (parent.buttonPaddingLeft);
		tw = (parent.parent.displayWidth - (parent.buttonPaddingLeft*2));
		th = (parent.buttonHeight - (parent.buttonPaddingTop*2));
		
		x=tx+parent.parent.displayWidth;
		y=ty;
		h=th;
		w=tw;
		tweening=true;
		textX=(int) (parent.parent.displayWidth*1.5);
		tTextX=parent.parent.displayWidth/2;
	}
	
	int showY;
	@SuppressWarnings("static-access")
	public void drawMe(){
		showY = y+parent.scrollY;
		
		
		parent.parent.fill(bgCol);
		if(pressed)
			parent.parent.fill(bgColPressed);	
		parent.parent.stroke(240);
		parent.parent.strokeWeight(1);
		parent.parent.rect(x,showY,w,h,3);
		
		if(LOCKED >= 0 ){
			parent.parent.image(parent.parent.lockedImage,x+(w-h),showY+(h/12),h-(h/3),h-(h/6));
		}
		
		parent.parent.fill(0);
		parent.parent.textAlign(parent.parent.LEFT,parent.parent.CENTER);
		parent.parent.text(text, 8+x-1, showY+(h/2)+1);
		
		parent.parent.fill(txtCol);
		parent.parent.textAlign(parent.parent.LEFT,parent.parent.CENTER);
		parent.parent.text(text, 8+x+1, showY+(h/2));
		
		
		
		animate();
	}
	
	public boolean hitMe(int mx, int my){
		if(mx>x&&mx<x+w&&my>showY&&my<showY+h)
			return true;
		return false;
	}
	
	
	public void animate(){
		x=tweenInt((float)x,(float)tx);
		textX=tweenInt((float)textX,(float)tTextX);
	}
	
	int tweenStage = 0;
	public int tweenInt(float x, float tx){
		if(tweening){
		float div=3+(index);
		if(tx>x)
			x+=(tx-x)/div;
		else
			if(x>tx)
			x-=(x-tx)/div;
		
		if(parent.parent.abs(x-tx)<=1){
			x=tx;
		tweening=false;	
		}
		}
		
		return (int)x;
	}
	
}

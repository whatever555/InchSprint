package com.sports.runner;

import java.util.ArrayList;

public class RaceSummary {
	String[] strings;
	Game parent;
	String continueAction="";
	String retryAction="";
	String heading;
	
	public RaceSummary(Game parent,String heading,String[] strings,String continueAction,String retryAction){
		initMe(parent,heading,strings,continueAction,retryAction);
	}
	public RaceSummary(Game parent){
		initMe(parent,null,new String[]{},null,null);
	}
	
	public void initMe(Game parent,String heading,String[] strings,String continueAction,String retryAction){
		this.parent=parent;
		this.strings=strings;
		this.heading=heading;
		this.retryAction=retryAction;
		this.continueAction=continueAction;
	}
	
	
	public void showMe(){
		showHomeButton();
	}
	
	
	public void showHomeButton(){
		
		parent.tint(0,200,0);
		parent.showButton(10,10,40,40,-1,null);
		parent.noTint();
		parent.image(parent.homeIcon, 15,15,30,30);
		
	}
	
	
	
	
	public void mouseClicked(){
		
	}
	
}

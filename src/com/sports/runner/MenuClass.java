package com.sports.runner;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import android.view.KeyEvent;

import com.swarmconnect.Swarm;

public class MenuClass extends Screen{

	String practiceMode;
	
	int scrollY=-100;

	PImage bgImage;
	PFont sportsFont;
	
	GameSounds gameSounds;
	int buttonHeight;
	int buttonPaddingTop;
	int buttonPaddingLeft;
	
	ArrayList<MenuButton> buttons;
	
	int raceLength = 100;
	boolean hurdlesOn=false;
	String raceMode="Race";
	int botCount = 0;
	String ghostType;
	
	  Game parent;
	  
	  int menuHeight;
	  
	  public MenuClass(Game parent){
		  this.parent=parent;
		  loadMe();
	  }
	  
	public void loadMe(){
		System.gc();
		menuHeight=parent.displayHeight;
		  sportsFont=parent.createFont("fonts/sport.ttf", 24, true);
			bgImage = parent.loadImage("images/back.jpg");
		gameSounds=new GameSounds(parent);
		gameSounds.loadSounds(new String[]{"click"},false);
		buttons=new ArrayList<MenuButton>();
		buttonHeight = parent.displayHeight/8;
		buttonPaddingLeft = parent.displayWidth/20;
		buttonPaddingTop=parent.displayHeight/60;
		
		beginAct();
	}
	
	
	public void beginRace(){
		 
		    
		    parent.showRaceScreen(botCount,hurdlesOn,raceLength,raceMode,ghostType,practiceMode);
		
	}
	public void showHurdleOptions(){
		
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"No Hurdles",0,-1));
		buttons.add(new MenuButton(this,"Enable Hurdles",1,1));
		
	}
	
	public void showTimetrialOptions(){
		
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"No Ghost Runner",0,-1));
		buttons.add(new MenuButton(this,"Include Ghost",1,-1));
		
	}
	
	public void showRaceLengthOptions(){
		
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"60 Inches",0,-1));
		buttons.add(new MenuButton(this,"100 Inches",1,-1));
		buttons.add(new MenuButton(this,"200 Inches",2,-1));
		buttons.add(new MenuButton(this,"400 Inches",3,-1));
		buttons.add(new MenuButton(this,"800 Inches",4,-1));
		buttons.add(new MenuButton(this,"1500 Inches",5,-1));
		
	}
	
	public void showLeaderboardOptions(){
		
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"Fastest Sprinters",0,-1));
		buttons.add(new MenuButton(this,"World Records",1,-1));
		
	}
	
	public void showTrainingOptions(){
		
		buttons=new ArrayList<MenuButton>();
		int tp = parent.trainingProgress;
		System.out.println("TRAINGIN PROGRESS: "+parent.trainingProgress);
		buttons.add(new MenuButton(this,"Practice Running",0,0-tp));
		buttons.add(new MenuButton(this,"Practice Starts",1,1-tp));
		buttons.add(new MenuButton(this,"Practice Hurldes",2,2-tp));
		
		botCount=0;
		raceMode="Time Trial";
	}
	
	
public void showFastestLeaderboardOptions(){
		
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"60 Inches Fastest",0,-1));
		buttons.add(new MenuButton(this,"100 Inches Fastest",1,-1));
		buttons.add(new MenuButton(this,"200 Inches Fastest",2,-1));
		buttons.add(new MenuButton(this,"400 Inches Fastest",3,-1));
		buttons.add(new MenuButton(this,"800 Inches Fastest",4,-1));
		buttons.add(new MenuButton(this,"1500 Inches Fastest",5,-1));
		
	}
	
	public void showOppositionOptions(){
		
		if(!raceMode.equals("Time Trial")){
		buttons=new ArrayList<MenuButton>();

		buttons.add(new MenuButton(this,"No Opponents",0,-1));
		buttons.add(new MenuButton(this,"1 Opponent",1,-1));
		buttons.add(new MenuButton(this,"5 Opponents",2,-1));
		buttons.add(new MenuButton(this,"10 Opponents",3,-1));
		buttons.add(new MenuButton(this,"15 Opponents",4,-1));
		buttons.add(new MenuButton(this,"20 Opponents",5,-1));
		buttons.add(new MenuButton(this,"25 Opponents",6,1));
		buttons.add(new MenuButton(this,"30 Opponents",7,1));
		buttons.add(new MenuButton(this,"35 Opponents",8,1));
		buttons.add(new MenuButton(this,"40 Opponents",9,-1));
		}else{
			botCount=0;
			showTimetrialOptions();
		}
	}
	
	
	public void showWorldRecords(){
		Swarm.showLeaderboards();
	}
	
	public void showLeaderBoardForRace(int raceLength){
		Swarm.showLeaderboards();
	}
	public void drawMe(){

		parent.textFont(sportsFont);
		menuHeight = buttons.size()*buttonHeight;
		
		if(scrollY<parent.displayHeight-menuHeight)
			scrollY+=((parent.displayHeight-menuHeight)-scrollY)/3;
		if(scrollY>0)
			scrollY-=(scrollY/3);
		
		
		showScreen();
		
	}
	
	public void p(String s){
		System.out.println(s);
	}
	
	



	
	
	public void beginAct(){
		practiceMode = "OFF";
		ghostType="No Ghost";
		botCount=1;
		hurdlesOn=false;raceLength=100;
		raceMode="Race";ghostType="No Ghost Runner";practiceMode="OFF";
		
		buttons=new ArrayList<MenuButton>();
			
			buttons.add(new MenuButton(this,"Quick Race",0,-1));
			buttons.add(new MenuButton(this,"Race",1,-1));
			buttons.add(new MenuButton(this,"Time Trial",2,1));
			buttons.add(new MenuButton(this,"Leaderboards",3,-1));
			buttons.add(new MenuButton(this,"Championship",4,-1));
			buttons.add(new MenuButton(this,"Training",5,-1));
			buttons.add(new MenuButton(this,"Controls",6,-1));
			buttons.add(new MenuButton(this,"Login",7,-1));
			

			
			menuHeight = buttons.size()*buttonHeight;
			
			showScreen();
	}
	
	public void showControlsScreen(){
		
	}

	
	public void showScreen(){
		
		parent.background(230,0,0);
		parent.image(bgImage,0,0,parent.displayWidth,parent.displayHeight);
	//	parent.fill(200,200,9,120);
	//	parent.rect(0,0,parent.displayWidth,parent.displayHeight);
	
		for(int i=0;i<buttons.size();i++){
			buttons.get(i).drawMe();
		}
	}
	
	public void mousePressed(){
		lastT=parent.millis();
		bY=parent.mouseY;
		if(!dragging)
			for(int i=0;i<buttons.size();i++){
			if(buttons.get(i).hitMe(parent.mouseX, parent.mouseY)){
				buttons.get(i).pressed=true;
			}else{
				buttons.get(i).pressed=false;
			}
			}
	}
	
int bY=0;
	boolean dragging=false;
	public void mouseDragged(){
		dragging=true;
			scrollY+=parent.mouseY-parent.pmouseY;
		
		
	}
	int lastT=0;
	public void mouseReleased(){

		if(!dragging || (parent.abs(bY - parent.mouseY)<10 && lastT - parent.millis() < 1000))
		for(int i=0;i<buttons.size();i++){
		if(buttons.get(i).hitMe(parent.mouseX, parent.mouseY)){
			

			gameSounds.playSound("click");
			
			if(buttons.get(i).LOCKED>0){
				parent.showSingleMessagePop(new String[]{"Locked Content"},new MyCallback(){ 
					  public void onMessageClose(){ 
					  }
				});
			}else
			
			if(buttons.get(i).text.equals("Quick Race")){
				parent.showRaceScreen(12,false,60,"Race","No Ghost",practiceMode);
			}else
				if(buttons.get(i).text.equals("Training")){
					showTrainingOptions();
				}else
				if(buttons.get(i).text.equals("Leaderboards")){
						

					showLeaderboardOptions();
					
					}else
				
				if(buttons.get(i).text.equals("Time Trial")){
				raceMode="Time Trial";
				showHurdleOptions();
			}else
				if(buttons.get(i).text.indexOf("Ghost")>0){
					ghostType = buttons.get(i).text;
					showRaceLengthOptions();
				}else
			if(buttons.get(i).text.equals("Race")){
				raceMode="Race";
				showHurdleOptions();
			   
			}
			else
			if(buttons.get(i).text.equals("No Hurdles")){
				
				hurdlesOn=false;
				showOppositionOptions();
			    
			}else
			if(buttons.get(i).text.indexOf("Hurdles")>0){
				hurdlesOn = true;
				showOppositionOptions();
			    
			}else
			if(buttons.get(i).text.equals("No Opponents")){
				hurdlesOn=false;
				showRaceLengthOptions();
				
			    
			}else
				if(buttons.get(i).text.indexOf("Practice")==0){

					String[] str = buttons.get(i).text.split(" ");
					raceLength = 5;
					practiceMode = (str[1]);
					beginRace();
					
				    
				}else
			if(buttons.get(i).text.indexOf("Opponent")>0){

				String[] str = buttons.get(i).text.split(" ");
				botCount = Integer.parseInt(str[0]);
				showRaceLengthOptions();
				
			    
			}else
				if(buttons.get(i).text.equals("Fastest Sprinters")){
			
					showFastestLeaderboardOptions();
					
				    
				}else
					if(buttons.get(i).text.indexOf("Fastest")>0){
						String[] str = buttons.get(i).text.split(" ");
						raceLength = Integer.parseInt(str[0]);
						showLeaderBoardForRace(raceLength);
						
					    
					}else
					if(buttons.get(i).text.equals("World Records")){
						
						showWorldRecords();
						
					    
					}
			else
				if(buttons.get(i).text.indexOf("Inches")>0){

					String[] str = buttons.get(i).text.split(" ");
					raceLength = Integer.parseInt(str[0]);
					beginRace();
					
				    
				}else
					if(buttons.get(i).text.equals("Controls")){

						parent.showMessage("controls");
						
					    
					}
			
		}else{
			buttons.get(i).pressed=false;
		}
	}
		dragging=false;
	}
	
	
	
		
	}




	


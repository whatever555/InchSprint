package com.sports.runner;

import java.util.ArrayList;

import processing.core.PFont;
import processing.core.PImage;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.delegates.SwarmLoginListener;

public class MenuClass extends Screen{

	String menuHeading = "Inch Sprint";
	String practiceMode;
	
	int scrollY=-100;

	PFont sportsFont;
	
	GameSounds gameSounds;
	int buttonHeight;
	int buttonPaddingTop;
	int buttonPaddingLeft;
	
	ArrayList<MenuButton> buttons;
	
	int raceLength = 100;
	boolean hurdlesOn=false;
	boolean longJumpOn=false;
	String raceMode="Race";
	int botCount = 0;
	String ghostType;
	
	int top=20;
	
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
			
		gameSounds=new GameSounds(parent);
		gameSounds.loadSounds(new String[]{"click"},false);
		buttons=new ArrayList<MenuButton>();
		buttonHeight = parent.displayHeight/8;
		buttonPaddingLeft = parent.displayWidth/20;
		buttonPaddingTop=parent.displayHeight/60;
		top = buttonHeight;
		
		beginAct();
	}
	
	
	public void beginRace(){
		 
		    
		    parent.showRaceScreen(botCount,hurdlesOn,raceLength,raceMode,ghostType,practiceMode,raceAgainst,longJumpOn);
		
	}
	public void showHurdleOptions(){
		
		menuHeading = "Hurdles";
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"No Hurdles",0,-1));
		
		if(parent.trainingProgress>2)
			buttons.add(new MenuButton(this,"Enable Hurdles",1,-1));
		else
		buttons.add(new MenuButton(this,"Enable Hurdles",1,1,"Requires completion of hurdle training"));

		
		
	}
	
	public void showTimetrialOptions(){

		menuHeading = "Time Trial";
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"No Ghost Runner",0,-1));
		buttons.add(new MenuButton(this,"Include Ghost",1,-1));
		
	}
	String raceAgainst="session";
	public void showGhostOptions(){

		menuHeading = "Ghost Options";
		buttons=new ArrayList<MenuButton>();
		buttons.add(new MenuButton(this,"Race PB Ghost",0,-1));
		buttons.add(new MenuButton(this,"Session Ghost",1,-1));
	}
	public void showRaceLengthOptions(){

		menuHeading = "Race Length";
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"60 Inches",0,-1));
		buttons.add(new MenuButton(this,"100 Inches",1,-1));
		buttons.add(new MenuButton(this,"200 Inches",2,-1));
		buttons.add(new MenuButton(this,"400 Inches",3,-1));
		buttons.add(new MenuButton(this,"800 Inches",4,-1));
		buttons.add(new MenuButton(this,"1500 Inches",5,-1));
		
	}
	
	public void showLeaderboardOptions(){

		menuHeading = "Records";
		buttons=new ArrayList<MenuButton>();
		
		buttons.add(new MenuButton(this,"Personal Records",0,-1));
		buttons.add(new MenuButton(this,"World Records",1,-1));
		
	}
	
	public void showTrainingOptions(){

		menuHeading = "Training Menu";
		buttons=new ArrayList<MenuButton>();
		int tp = parent.trainingProgress;
		//System.out.println("TRAINGIN PROGRESS: "+parent.trainingProgress);
		buttons.add(new MenuButton(this,"Practice Running",0,0-tp));
		buttons.add(new MenuButton(this,"Practice Starts",1,1-tp));
		buttons.add(new MenuButton(this,"Practice Hurdles",2,2-tp));
		buttons.add(new MenuButton(this,"Practice Long Jump",3,3-tp));
		
		botCount=0;
		raceMode="Practice";
	}
	
	
public void showPersonalBestOptions(){

	    menuHeading = "PB Records";
		buttons=new ArrayList<MenuButton>();

		buttons.add(new MenuButton(this,"60 Inches PB",0,-1));
		buttons.add(new MenuButton(this,"100 Inches PB",1,-1));
		buttons.add(new MenuButton(this,"200 Inches PB",2,-1));
		buttons.add(new MenuButton(this,"400 Inches PB",3,-1));
		buttons.add(new MenuButton(this,"800 Inches PB",4,-1));
		buttons.add(new MenuButton(this,"1500 Inches PB",5,-1));
		buttons.add(new MenuButton(this,"Off the Blocks PB",6,-1));
		
	}
	
	public void showOppositionOptions(){
		menuHeading = "Competitors";
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
		
		if(scrollY<parent.displayHeight-menuHeight+buttonPaddingTop)
			scrollY+=((parent.displayHeight-menuHeight)-scrollY)/3;
		if(scrollY>top+buttonPaddingTop)
			scrollY-=(scrollY/5);
		
		
		showScreen();
		showHeader();
		
	}
	
	public void showSwarmDashBoard(){
		 Swarm.showDashboard();
		 }
	
	public void p(String s){
		System.out.println(s);
	}
	
	



	
	
	public void beginAct(){
		menuHeading="Main Menu";
		
		practiceMode = "OFF";
		ghostType="No Ghost";
		botCount=1;
		hurdlesOn=false;raceLength=100;
		raceMode="Race";ghostType="No Ghost Runner";practiceMode="OFF";
		
		buttons=new ArrayList<MenuButton>();
			
		if(parent.trainingProgress>1){
			buttons.add(new MenuButton(this,"Quick Race",0,-1));
			buttons.add(new MenuButton(this,"Race",1,-1));
			buttons.add(new MenuButton(this,"Time Trial",2,-1));
		}else{
			buttons.add(new MenuButton(this,"Quick Race",0,1,"You must complete basic training to play"));
			buttons.add(new MenuButton(this,"Race",1,1,"You must complete basic training to play"));
			buttons.add(new MenuButton(this,"Time Trial",2,1,"Requires completion of basic training"));
		}
			buttons.add(new MenuButton(this,"Leaderboards",3,-1));
		if(parent.trainingProgress>2){
			buttons.add(new MenuButton(this,"Championship",4,-1));
		}else{
			buttons.add(new MenuButton(this,"Championship",4,1,"You must complete training to play"));
		}
			buttons.add(new MenuButton(this,"Training",5,-1));
			buttons.add(new MenuButton(this,"Controls",6,-1));
			
			int ai = 7;
			if(parent.championshipProgress>20){
			buttons.add(new MenuButton(this,"RATE",ai,-1));
			ai++;
			}
			
			if(Swarm.isLoggedIn()){
				buttons.add(new MenuButton(this,"Account",ai,-1));
				ai++;
			}else{

				buttons.add(new MenuButton(this,"Log In",ai,-1));
				ai++;
				
				
			}
			buttons.add(new MenuButton(this,"Play Online",ai,1));
			ai++;
			
			menuHeight = buttons.size()*buttonHeight;
			
			showScreen();
	}
	
	public void showControlsScreen(){
		
	}

	public void showHeader(){
		parent.fill(0);
		parent.stroke(255);
		parent.strokeWeight(0);
		parent.rect(-2,-2,parent.displayWidth+4,buttonHeight+2);
		parent.image(parent.homeIcon, buttonPaddingLeft, 4,buttonHeight-6,buttonHeight-6);
		parent.textFont(parent.messageFont);
		parent.textSize(22);
		parent.textAlign(parent.CENTER,parent.CENTER);
		parent.fill(255);
		parent.text(menuHeading, parent.displayWidth/2,buttonHeight/2);
		
		
	}
	
	public void showScreen(){
		
		parent.background(230,0,0);
		parent.image(parent.bgImage,0,0,parent.displayWidth,parent.displayHeight);
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
				
			parent.println("TRAINING PROGRESS "+parent.trainingProgress);
			
			gameSounds.playSound("click");
			
			
			if (!Swarm.isLoggedIn()) {
				Swarm.showLogin();
			}else
			if(buttons.get(i).LOCKED>0){
				parent.showSingleMessagePop(new String[]{"Locked Content",buttons.get(i).contentLockedMessage},new MyCallback(){ 
					  public void onMessageClose(){ 
					  }
				});
			}else
			
				if(buttons.get(i).text.equals("Quick Race")){
					parent.showRaceScreen(12,false,60,"Race","No Ghost",practiceMode,"session");
				}else
					if(buttons.get(i).text.equals("Account")){
						showSwarmDashBoard();
					}else
						if(buttons.get(i).text.equals("Race PB Ghost")){
						raceAgainst="PB";
						showRaceLengthOptions();
					}else
						if(buttons.get(i).text.equals("Session Ghost")){
						
						raceAgainst="session";
						showRaceLengthOptions();
					}
					
					else
				
				
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
					if(!ghostType.equals("No Ghost"))
						showGhostOptions();
					else
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
				if(buttons.get(i).text.indexOf(" PB")>-1){

					String[] str = buttons.get(i).text.split(" ");
					parent.showPersonalRecord(str[0]);
					
					
				}else
				if(buttons.get(i).text.indexOf("Practice")==0){

					String[] str = buttons.get(i).text.split(" ");
					if(str[1].equals("Running"))
					raceLength = 40;
					else if(str[1].equals("Hurdles")){
						raceLength=70;
						hurdlesOn=true;
					}else 
						if(str[1].equals("Long Jump")){
						raceLength=100;
						longJumpOn=true;
					}
					else
						raceLength=5;
					
					practiceMode = (str[1]);
					
					beginRace();
				}else
			if(buttons.get(i).text.indexOf("Hurdles")>0){
				hurdlesOn = true;
				showOppositionOptions();
			    
			}else
			if(buttons.get(i).text.equals("No Opponents")){
				hurdlesOn=false;
				showRaceLengthOptions();
				
			    
			}else
			if(buttons.get(i).text.indexOf("Opponent")>0){

				String[] str = buttons.get(i).text.split(" ");
				botCount = Integer.parseInt(str[0]);
				showRaceLengthOptions();
				
			    
			}else
				if(buttons.get(i).text.equals("Personal Records")){
			
					showPersonalBestOptions();
					
				    
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




	


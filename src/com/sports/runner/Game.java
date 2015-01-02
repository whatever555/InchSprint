package com.sports.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmActiveUser.GotCloudDataCB;
import com.swarmconnect.SwarmNotification;
import com.swarmconnect.delegates.SwarmLoginListener;
import com.swarmconnect.delegates.SwarmNotificationDelegate;

public class Game extends PApplet{
	
	Vibrator v; // Vibrate for 500 milliseconds

	String currentMode="NORMAL";
	int maxBots = 5;
	PImage sandGraphic;
	PImage buttonGraphic;
	PImage buttonLeftGraphic;
	PImage buttonRightGraphic;
	PImage homeIcon;
	PImage flegs;
	PImage goldMedal;
	PImage silverMedal;
	PImage bronzeMedal;
	int TouchEvents;
	String  championshipData;
	int championshipProgress=0;
	int trainingProgress=0;
	Screen activeScreen;
	MenuClass mainMenu;
	BotRaceClass race;
	String currentScreen;
	PImage lockedImage;
	PFont messageFont;
	
	CDS cds;
	
	PImage bgImage;	
	int setVarCount=0;

	
	RaceSummary rc;
	MessagePop controlsMP;
	MessagePop activeMP;
	boolean loaded=false;
	
	String[] countryList = new String[233];
    String countryName="";
    int countryIndex=0;
	PImage myFlag;
	
	ArrayList<String> flashMessages;
	int flashTimer=0;
	
	float[] pbs;
	
	public void setup(){
		orientation(PORTRAIT);
		cds = new CDS();
		goldMedal=loadImage("graphics/medals/gold.png");
		silverMedal=loadImage("graphics/medals/silver.png");
		bronzeMedal=loadImage("graphics/medals/bronze.png");
		homeIcon=loadImage("graphics/home.png");
		buttonGraphic=loadImage("graphics/button.png");
		sandGraphic=loadImage("graphics/sand.jpg");
		buttonLeftGraphic=loadImage("graphics/buttonLeft.png");
		buttonRightGraphic=loadImage("graphics/buttonRight.png");
		bgImage = loadImage("images/back.jpg");
		
		showLoadingMessage();
    	
		flashMessages=new ArrayList<String>();
		pbs = new float[50];
		for(int i = 0; i <pbs.length;i++){
			pbs[i]=99999;
		}
		
		championshipData="55555";
		
		flegs = loadImage("images/flegs.png");
		countryList=new String[]{"Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antigua and Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Colombia","Comoros","Cook Islands","Costa Rica","Croatia","Cuba","Cyprus","Czech Republic","C�te d'Ivoire","Democratic Republic of the Congo","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macao","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","North Korea","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn Islands","Poland","Portugal","Puerto Rico","Qatar","Republic of the Congo","Romania","Russian Federation","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Pierre","Saint Vicent and the Grenadines","Samoa","San Marino","Sao Tom� and Pr�ncipe","Saudi Arabia","Senegal","Serbia and Montenegro","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Soloman Islands","Somalia","South Africa","South Georgia","South Korea","Soviet Union","Spain","Sri Lanka","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Tibet","Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","UAE","Uganda","Ukraine","United Kingdom","United States of America","Uruguay","US Virgin Islands","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Wallis and Futuna","Yemen","Zambia","Zimbabwe"};
		
		countryName=getCountryName();
		myFlag = getMyFlag();
		
		
		
		v= (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		
		setupControlsMP();
		
		activeMP = controlsMP;
		
		loaded=false;
		noLoop();
		background(200,200,230);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		loadSwarm();
		rc=new RaceSummary(this);
	}
	

	
	public void showMessage(String message){
		if(message.equals("controls")){
			controlsMP.active=true;
			activeMP=controlsMP;
		}
	}
	
	
	public Callable fakeFunc(){
		return null;}
	
	public void hideMessages(){
		if(activeMP.active){
		try {
			activeMP.callCloseFunction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activeMP.active=false;
		}
	}
	
	public void loadGame(){
		
		lockedImage=loadImage("images/lock.png");
		mainMenu=new MenuClass(this);
		race = new BotRaceClass(this);
		currentScreen="mainMenu";
		activeScreen=mainMenu;
		loaded=true;
		loop();
	}
	
	public void draw(){
		if(loaded){
		activeScreen.drawMe();
		
		activeMP.showMe();
		
		showFlashMessages();
		
		if(showRaceLoadingScreen==true){
			
			showLoadRaceScreen();
		}
		}else{
		showLoadingMessage();	
		}
	}
	public void showLoadingMessage(){
		image(bgImage,0,0,displayWidth,displayHeight);
		fill(30);
		textSize(displayWidth/20);
		textAlign(CENTER,CENTER);
		messageFont=createFont("fonts/messageFont.ttf", 34, true);
		imageMode(CENTER);
		fill(255);
		image(homeIcon,displayWidth/2,(int)(displayHeight/2.1-homeIcon.height));
		text("Loading...",displayWidth/2,displayHeight/2);
		imageMode(CORNER);
	}
	public void showFlashMessages(){
		if(race!=null)
		if(flashMessages.size()>0){
			showFlashMessage(flashMessages.get(0));
			
			if(millis() - flashTimer > 2000){
				flashMessages.remove(0);
				flashTimer=millis();
			}
		}else{
			flashTimer=millis();
		}
	}
	
	public void showFlashMessage(String str){
		//println("showing message" +str);
		fill(0);
		strokeWeight(0);
		stroke(255,100);
		rect(-1,-1,displayWidth+2,38);
		textAlign(CENTER);
		textFont(messageFont);
		textSize((int)((float)((float)16/480)*displayWidth));
		fill(255);
		text(str,displayWidth/2,28);
		noStroke();
	}
	ChampionshipEvent currentEvent;
	
	public void setMedal(char med){
		while(championshipData.length()<currentEvent.index+1){
			championshipData+="0";
		}
		
		if(Integer.parseInt(""+med)<2){
			if(maxBots<race.botCount){
				maxBots = race.botCount;

				flashMessages.add("Content Unlocked");
			}
		}
		char[] myNameChars = championshipData.toCharArray();
		if(Integer.parseInt(""+med)<Integer.parseInt(""+myNameChars[currentEvent.index]) || Integer.parseInt(""+myNameChars[currentEvent.index])==0){
		myNameChars[currentEvent.index] = med;
		championshipData = String.valueOf(myNameChars);
		}
		while(championshipData.length()<currentEvent.index+2)
			championshipData+='0';
		
		saveToCloud("Championship Data",championshipData);
		
		
	}
	boolean showRaceLoadingScreen=false;
	public void loadStage(int i){
		race.championshipRace=true;
		ChampionshipEvent c = cds.ce.get(i);
		currentEvent = c;
		
		showRaceLoadingScreen=true;
		
	}
	
	public void loadTheRace(){
		showRaceScreen(currentEvent.minPos,currentEvent.difficulty,currentEvent.medalRace,currentEvent.bots,currentEvent.hurdles,currentEvent.trackLength,currentEvent.raceMode,"No Ghost","OFF",null,currentEvent.longJump);
		
	}
	public void showRaceScreen(float minPos,float difficulty,boolean medalRace,int botCount,boolean hurdlesOn,int trackInInches,String raceMode,String ghostType,String practiceMode,String raceAgainst,boolean longJumpOn){
		noLoop();	
		if(minPos==-1)
			race.championshipRace=false;
		else
			race.championshipRace=true;
		
		race.minPos=minPos;
		race.difficulty=difficulty;
		race.medalRace=medalRace;
		race.longJumpOn=longJumpOn;
		race.raceAgainst=raceAgainst;
			race.fastestTimeYet=999999999;
			race.ghostType=ghostType;
			race.practiceMode=practiceMode;
			race.botCount = botCount;
		    race.hurdlesOn = hurdlesOn;
		    race.trackInInches = trackInInches;
		    race.raceMode = raceMode;
		    race.setMeUpOnce();
		    currentScreen="race";
		    activeScreen=race;
		    loop();
	}
	
	
	public void showLoadRaceScreen(){
		fill(0,240);
		noStroke();
		noTint();
		rect(0,0,displayWidth,displayHeight);
		int inc=30;
		textAlign(LEFT,TOP);
		textFont(messageFont);
		textSize((int)((float)((float)40/480)*displayWidth));
		
		fill(255);
		text("Event Details",5,fixNumber(5));
		textSize((int)((float)((float)30/480)*displayWidth));
		int yy = 25+inc;
		fill(120);
		line(0,yy,0,yy);
		fill(255);
		yy+=18+inc;
		text("Competitors",5,fixNumber(yy));
		text(currentEvent.bots,displayWidth/2,fixNumber(yy));

		String hurdlesOnString = "No";
		if(currentEvent.hurdles){
			hurdlesOnString="Yes";
		}
		yy+=18+inc;
		text("Hurdles",5,fixNumber(yy));
		text(hurdlesOnString,displayWidth/2,fixNumber(yy));

		yy+=18+inc;
		text("Track Length",5,fixNumber(yy));
		text(currentEvent.trackLength+ " Inches",displayWidth/2,fixNumber(yy));
		
		yy+=18+inc;
		text("Target Position",5,fixNumber(yy));
		text((int)currentEvent.minPos+ "",displayWidth/2,fixNumber(yy));
		

		yy+=18+inc*2;
		text("Tap Anywhere to Continue..",5,fixNumber(yy));
	}
	
	public void showMainMenuScreen(){
		noLoop();
		currentScreen="mainMenu";
	    race.raceStage=1;
	    mainMenu.beginAct();
	    activeScreen=mainMenu;
	    loop();
}
	
	public void loadSwarm(){
		
		Swarm.setAllowGuests(true);
			Swarm.init(this, 12348, "4a2bcfb705936e7b715c61563c450636",mySwarmLoginListener);
		
			Swarm.addNotificationDelegate(new SwarmNotificationDelegate() {

			    public boolean gotNotification(SwarmNotification notification) {

			        if (notification != null) {

			            // Check to see if the notification is an achievement notification
			          //  if (notification instanceof NotificationAchievement) {

			                // Get the notification's data so you can use it
			                String message = notification.getMessage(getBaseContext());
			                String title = notification.getTitle(getBaseContext());
			                int iconId = notification.getIconId(getBaseContext());
if(message.indexOf("0:00:00")<0){
			                // Display your own custom toast
			                Context context = getApplicationContext();
			                int duration = Toast.LENGTH_SHORT;

			               /* Toast toast = Toast.makeText(context, message, duration);
			                toast.show();
			                */
}
			                // Return true to tell Swarm you've consumed the event
			                // and thus Swarm will not display the default toast.
			                return true;

			         //   } else if (notification instanceof NotificationLeaderboard) {

			                // Leaderboard notifications can be handled the same way
			                // as achievement notifications...

			          //      return true;
			               
			         //   } else {

			            //    return false;

			         //   }
			        }

			        return false;
			    }
			});
			
	}
	
	public int fixNumber(int n){
		return (int)(((float)n/480)*displayWidth);
	}
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Swarm.setActive(this);
	  
	}
	
	public void onResume() {
	    super.onResume();
	    Swarm.setActive(this);
	}

	public void onPause() {
	    super.onPause();
	    Swarm.setInactive(this);
	}
	
	
	public boolean surfaceKeyDown(int code, KeyEvent event) {
		  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

				 if(showRaceLoadingScreen==true){
					 showRaceLoadingScreen=false;
				 }else{
				
			  noStroke();
			  noTint();
			  noLoop();
			  if(activeMP.active)
			  hideMessages();
			  else
			  if(loaded){ 
			  if(currentScreen.equals("race")){

				  race.raceStage=1;
				  showMainMenuScreen();
				  
			  }else
				 if(currentScreen.equals("mainMenu")){
					 race.raceStage=1;
					 showMainMenuScreen();
			  }
			  }
			 loop();
				 }
		  }
		  return false;
		}

		public boolean surfaceKeyUp(int code, KeyEvent event) {
		  return super.surfaceKeyDown(code, event);
		}

		
		public void mousePressed(){
			
			
		
			if(!activeMP.active){
			if(loaded)
			activeScreen.mousePressed();

			mouseDownOnMessage=false;
			}else{
				mouseDownOnMessage=true;
			}
			
		}
		public void mouseReleased(){
			if(showRaceLoadingScreen==true){
				showRaceLoadingScreen=false;
				 loadTheRace();
				 
			}else{
			if(activeMP.active && mouseDownOnMessage==true){
				if(millis() - activeMP.creationMilliTime > activeMP.minLifeMillis)
				hideMessages();
				mouseDownOnMessage=false;
			}
			else
			if(loaded)
			activeScreen.mouseReleased();
			
			}
		}
		
	
		boolean mouseDownOnMessage=false;

		public void mouseDragged(){
			if(!activeMP.active)
			if(loaded)
				activeScreen.mouseDragged();
		}
		
		
		
		
		
		  SwarmLoginListener mySwarmLoginListener = new SwarmLoginListener() {

		    	// This method is called when the login process has started
		    	// (when a login dialog is displayed to the user).
		    	public void loginStarted() {
		    		
		    		//getCloudVars();
		    	}

		    	// This method is called if the user cancels the login process.
		    	public void loginCanceled() {
		    		getCloudVars();
		    	}

		    	// This method is called when the user has successfully logged in.
		    	public void userLoggedIn(SwarmActiveUser user) {
		    		getCloudVars();
		    		
		    	}

		    	// This method is called when the user logs out.
		    	public void userLoggedOut() {
		    		getCloudVars();
		    	}

		    };
		    
		    public void showPersonalRecord(String rec){
		    	String recordName = rec+ " Inches PB";
		    	int i=0;
		    	if(rec.equals("Off")){
		    		recordName = "Off the Blocks PB";
		    		i=0;
		    	}
		    	else{
		    		i=getRaceIndex(Integer.parseInt(rec), false);
		    	}
		    	
						    
		    	String[] dets;
		    	String rec1="";
		    	String rec2="";
		    	if(i==0){
		    		if(pbs[i]>0.0 && pbs[i]<99998)
		    		rec1 = ""+pbs[i]+" Seconds";
		    		else
		    		rec1 = "No Record Set Yet";
		    		
		    		dets=new String[]{recordName,rec1,""};
		    	}else{
		    		if(pbs[i]>0.0 && pbs[i]<99998)
		    			rec1 = ""+pbs[i]+" Seconds";
		    		else
		    		rec1 = "No Record Set Yet";
		    		
		    		if(pbs[i+24]>0.0 && pbs[i+24]<99998)
		    			rec2 = ""+pbs[i+24]+" Seconds";
		    		else
		    		rec2 = "No Record Set Yet";
		    		dets=new String[]{recordName,rec1,"",recordName+" (Hurdles)",rec2,""};
		    	}
		    	
		    	showSingleMessagePop(dets,new MyCallback(){ 
					  public void onMessageClose(){ 
					  }
				});
		    }
		    int totalVarCount;
		    public void getCloudVars(){

	    		showLoadingMessage();
		    	setVarCount=0;
		    	totalVarCount=5;
	    		setFromCloud("Training Progress");
	    		setFromCloud("Championship Data");
	    		setFromCloud("Championship Progress");
	    		setFromCloud("Personal Bests");
	    		setFromCloud("Max Bots");
		    }
		    
			public boolean surfaceTouchEvent(MotionEvent event) {

				 TouchEvents = event.getPointerCount();
				
				  if(loaded){
				  if(TouchEvents>1 && race.player.jumpY<1 && race.jumpLoading==false && race.player.jumping==false){
					  race.jumpLoading=true;
				  }
				  if(TouchEvents<2&&race.jumpLoading==true&&race.raceOn==true){
					  if(race.jumpLoading && (race.hurdlesOn==true || race.longJumpOn==true)){
						  race.moveY+=(race.getLastY(mouseY)-race.lastY);
							
							
							race.lastY=race.getLastY(mouseY);
							race.player.jumping=true;
							race.jumpLoading=false;
							race.player.jumpingTargetHeight = (float) (race.track.hurdleHeight*2.8);
							float avgSpeed = 0;
							for(int i=0;i<race.player.last3Speeds.size();i++){
								avgSpeed+=race.player.last3Speeds.get(i);
							}
							avgSpeed=avgSpeed/race.player.last3Speeds.size();
							
							if( race.longJumpOn==true)
								avgSpeed+=(race.moveY)+race.player.last3Speeds.get(race.player.last3Speeds.size()-1)+race.player.last3Speeds.get(race.player.last3Speeds.size()-2);
							
								race.player.jumpingVelocity=(float) ((avgSpeed*1.3)+25);
							
						}
				  }
				  }
				 
				  return super.surfaceTouchEvent(event);
				}
			
			public void updatePersonalBests(int trackInInches,boolean hurdlesOn,float racetime){
				int index=getRaceIndex(trackInInches,hurdlesOn);
				if(racetime<pbs[index])
				pbs[index]=racetime;
			}
			
			public String convertPBSToString(){
				String outStr="";
				for(int i=0;i<pbs.length;i++){
					outStr+=pbs[i];
					if(i<pbs.length-1)
						outStr+=",";
				}
				return outStr;
			}
			boolean paused=false;
			public void saveToCloud(final String varName,final String newVal){
				
				if (Swarm.isLoggedIn() && newVal!="0.0") {
					Swarm.user.saveCloudData(varName, ""+newVal);
				}
				
				
				
			}
			
			
			
			public void setFromCloud(final String varName){
			
				if (Swarm.isLoggedIn()) {
					paused=true;
				    
					GotCloudDataCB callback = new GotCloudDataCB() {
					    public void gotData(String data) {
					        // Did our request fail (network offline, and uncached)?
					        if (data == null) {

					        	 setVarCount++;
								    if(setVarCount==totalVarCount){
								    	paused=false;
								    	loadGame();
								    }
					           
						    	
					            return;
					        }

					        // Has this key never been set?  Default it to a value...
					        if (data.length() == 0) {
					            // In this case, we're storing levelProgress, default them to level 1.
					            data = "0";
					        }

					        if(varName.equals("Training Progress"))
					        	trainingProgress=Integer.parseInt(data);
					        
					    	 if(varName.equals("Championship Progress"))
						        	championshipProgress=Integer.parseInt(data);
					    	 
					    	 if(varName.equals("Championship Data"))
						        	championshipData=(data);
					    	 
					        if(varName.equals("Personal Bests")){
					        	if(data.equals("0"))
				        			data="";
					        
					        if(varName.equals("Personal Bests"))
					        	localisePBS(data);
					        
					        }
					        
					        paused=false;
					        setVarCount++;
					        if(setVarCount==totalVarCount){
						    	loadGame();
						    }
						    
					    }
					}; 
					
					
					    Swarm.user.getCloudData(varName, callback);
					   
					}
			}
			
			public void localisePBS(String str){
				
				String[] stra = str.split(",");
				for(int i=0;i<stra.length;i++)
					try{
					pbs[i] =Float.parseFloat(stra[i]);
					}catch(Exception e){
					}
			}
			
			public void setupControlsMP(){
				controlsMP=new MessagePop(this,"Controls",new MyCallback(){ 
					  public void onMessageClose(){ 
						   
					  }
				});

				controlsMP.addOption("Controls",false,20);
				controlsMP.addOption("Running",false,18);
				controlsMP.addOption("Drag your fingers on the track to run",false,14);
				controlsMP.addOption("",false,12);
				

				controlsMP.addOption("Jumping",false,18);
				controlsMP.addOption("Drag two fingers on the track to jump",false,14);
				controlsMP.addOption("",false,12);

				controlsMP.addOption("Tap anywhere to hide this message",true,14);
			}
			
			public void showSingleMessagePop(String[] messages,MyCallback callback,int format,int style){
				showTheSingleMessagePop(messages,callback,format,style);
				
				//TODO add continue buttons etc
			}
			public void showSingleMessagePop(String[] messages,MyCallback callback){
				showTheSingleMessagePop(messages,callback,1,1);
				
				
			}
			
			
			public void showTheSingleMessagePop(String[] messages,MyCallback callback,int format,int style){
				activeMP = new MessagePop(this,"Message",callback,format,style);
				for(int i=0;i<messages.length;i++)
					if(i==0)
				activeMP.addOption(messages[i],false,20);
					else
				activeMP.addOption(messages[i],false,16);
				if(activeMP.format==1)
				activeMP.addOption("Tap anywhere to hide this message",true,14);
				activeMP.active=true;
				
			}
		 
			
			public String getCountryName(){
				
				String countryName= "United States of America";
				String[] html = loadStrings("http://www.whereisip.net/");
				if(html!=null){
				String str = TextUtils.join(" ", html);   
			
				String str1 = "Region:  <b>";
				String str2 = ",";
				int strpos = str.indexOf(str1)+str1.length();
				countryName = str.substring(strpos, str.indexOf(str2,strpos));
				}else{

					countryIndex = Arrays.asList(countryList).indexOf(countryName);
					return countryName;
				}
				
				countryIndex = Arrays.asList(countryList).indexOf(countryName);
				if(countryIndex>-1)
					return countryName;
				
				LevenshteinDistance lv = new LevenshteinDistance();
				double bestMatch=0;
				int bindex=0;
				for(int i=0;i<countryList.length;i++){
					double lv_m = lv.similarity(countryList[i], countryName);
					if(lv_m>bestMatch){
					bestMatch=lv_m;
						bindex = i;
						
						countryIndex = i;
					}
				}
				countryName = countryList[countryIndex];
				return countryName;
			}
			
			public PImage getMyFlag(){
				PImage tmp;
				
				
				int x = countryIndex*20;
				int y=0;
				while(x>400){
					x-=400;
					y+=12;
				}
				tmp = flegs.get(x,y,20,12);
				return tmp;
				
			}
			
			public int getRaceIndex(int i,boolean hurdlesOn){
				int extra = 0;
				if(hurdlesOn)
					extra=24;
				if(i==60)
					return 1+extra;
				else
				if(i==100)
					return 2+extra;
				else
					if(i==200)
						return 3+extra;
					else
						if(i==400)
							return 4+extra;
						else
							if(i==800)
								return 5+extra;
							else
								if(i==1500)
									return 6+extra;
				return -1;
			}
			
			
			public void showButton(int x, int y, int w, int h){
				displayButton(x,y,w,h,-1,null,null,null);
			}
			public void showButton(int x, int y, int w, int h,int tintCol){
				displayButton(x,y,w,h,tintCol,null,null,null);
			}
			public void showButton(int x, int y, int w, int h,int tintCol,String text){
				displayButton(x,y,w,h,tintCol,text,null,null);
			}
			public void showButton(int x, int y, int w, int h,int tintCol,String text,String threeMedals){
				displayButton(x,y,w,h,tintCol,text,null,threeMedals);
			}
			public void showButton(int x, int y, int w, int h,int tintCol,String text,PImage extraIcon){
				displayButton(x,y,w,h,tintCol,text,extraIcon,null);
			}
			
			public void displayButton(int x, int y, int w, int h,int tintCol,String text,PImage extraIcon,String threeMedals){

				textSize((int)(h/2.5));
				if(tintCol!=-1)
					tint(tintCol);
				
				int sw=fixNumber(10);
				image(buttonLeftGraphic,x,y,sw,h);
				image(buttonGraphic,x+sw,y,w-(sw*2),h);
				image(buttonRightGraphic,x+w-sw,y,sw,h);
				
				if(tintCol!=-1)
					noTint();
				
				if(text!=null){
					fill(0);
					textAlign(LEFT,CENTER);
					text(text,(x)+sw-1,y+h/2-1);

					fill(255);
					textAlign(LEFT,CENTER);
					text(text,(x)+sw+1,y+h/2+1);
				}
				
				if(extraIcon!=null){
					imageMode(CENTER);
					image(extraIcon,x+w-sw-(h/2),y+(h/2),(int)(h*.8),(int)(h*.8));
					imageMode(CORNER);
				}
				
				if(threeMedals!=null && threeMedals.length()>0){

					imageMode(CENTER);
					
					
					for(int i=0;i<3;i++){
						if(threeMedals.length()>i){
							if(threeMedals.charAt(i)=='1'){
								image(goldMedal,(float) (x+w-sw-(h/(1.5)+(i*(int)(h*.3)))),y+(h/2),(int)(h*.3),(int)(h*.3));
							}else
							if(threeMedals.charAt(i)=='2'){
								image(silverMedal,(float) (x+w-sw-(h/(1.5)+(i*(int)(h*.3)))),y+(h/2),(int)(h*.3),(int)(h*.3));
							}else
							if(threeMedals.charAt(i)=='3'){
								image(bronzeMedal,(float) (x+w-sw-(h/(1.5)+(i*(int)(h*.3)))),y+(h/2),(int)(h*.3),(int)(h*.3));
							}else
								{
								tint(0);
									image(silverMedal,(float) (x+w-sw-(h/(1.5)+(i*(int)(h*.3)))),y+(h/2),(int)(h*.3),(int)(h*.3));
								}
							noTint();
						}
					}
					imageMode(CORNER);
				}
				
			}
			
			
}

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
	
	
	PImage flegs;
	int TouchEvents;
	int championshipProgress=0;
	int trainingProgress=0;
	Screen activeScreen;
	MenuClass mainMenu;
	BotRaceClass race;
	String currentScreen;
	PImage lockedImage;
	PFont messageFont;
	
	
	PImage bgImage;	
	int setVarCount=0;

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
		bgImage = loadImage("images/back.jpg");
		size(displayWidth,displayHeight,P2D);
		showLoadingMessage();
    	
		flashMessages=new ArrayList<String>();
		pbs = new float[7];
		for(int i = 0; i <pbs.length;i++){
			pbs[i]=99999;
		}
		flegs = loadImage("images/flegs.png");
		countryList=new String[]{"Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Anguilla","Antigua and Barbuda","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bermuda","Bhutan","Bolivia","Bosnia","Botswana","Brazil","British Virgin Islands","Brunei","Bulgaria","Burkina Faso","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Cayman Islands","Central African Republic","Chad","Chile","China","Christmas Island","Colombia","Comoros","Cook Islands","Costa Rica","Croatia","Cuba","Cyprus","Czech Republic","Côte d'Ivoire","Democratic Republic of the Congo","Denmark","Djibouti","Dominica","Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Falkland Islands","Faroe Islands","Fiji","Finland","France","French Polynesia","Gabon","Gambia","Georgia","Germany","Ghana","Gibraltar","Greece","Greenland","Grenada","Guam","Guatemala","Guinea","Guinea Bissau","Guyana","Haiti","Honduras","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macao","Macedonia","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Martinique","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montserrat","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","Netherlands Antilles","New Zealand","Nicaragua","Niger","Nigeria","Niue","Norfolk Island","North Korea","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Pitcairn Islands","Poland","Portugal","Puerto Rico","Qatar","Republic of the Congo","Romania","Russian Federation","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Pierre","Saint Vicent and the Grenadines","Samoa","San Marino","Sao Tomé and Príncipe","Saudi Arabia","Senegal","Serbia and Montenegro","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Soloman Islands","Somalia","South Africa","South Georgia","South Korea","Soviet Union","Spain","Sri Lanka","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Tibet","Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Turks and Caicos Islands","Tuvalu","UAE","Uganda","Ukraine","United Kingdom","United States of America","Uruguay","US Virgin Islands","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Wallis and Futuna","Yemen","Zambia","Zimbabwe"};
		
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
		for(int i=0;i<pbs.length;i++){
			println("PBS: "+i+ " == "+pbs[i]);
		}
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
		}else{
		showLoadingMessage();	
		}
	}
	public void showLoadingMessage(){
		image(bgImage,0,0,displayWidth,displayHeight);
		fill(30);
		textSize(26);
		textAlign(CENTER,CENTER);
		messageFont=createFont("fonts/messageFont.ttf", 34, true);
		filter(BLUR,2);
		text("Loading...",displayWidth/2,displayHeight/2);
		
	}
	public void showFlashMessages(){
		if(race!=null)
		if(race.raceOn==false)
		if(flashMessages.size()>0){
			showFlashMessage(flashMessages.get(0));
			
			if(millis() - flashTimer > 1000){
				flashMessages.remove(0);
				flashTimer=millis();
			}
		}else{
			flashTimer=millis();
		}
	}
	
	public void showFlashMessage(String str){
		//println("showing message" +str);
		fill(40);
		noStroke();
		rect(displayWidth/10,10,displayWidth-(displayWidth/5),38,3);
		textAlign(CENTER);
		textFont(messageFont);
		textSize(16);
		fill(255);
		text(str,displayWidth/2,28);
	}
	
	public void showRaceScreen(int botCount,boolean hurdlesOn,int trackInInches,String raceMode,String ghostType,String practiceMode){
		noLoop();	
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

			                Toast toast = Toast.makeText(context, message, duration);
			                toast.show();
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
			
			if(activeMP.active && mouseDownOnMessage==true){
				if(millis() - activeMP.creationMilliTime > activeMP.minLifeMillis)
				hideMessages();
				mouseDownOnMessage=false;
			}
			else
			if(loaded)
			activeScreen.mouseReleased();
			
			
		}
		
		boolean mouseDownOnMessage=false;

		public void mouseDragged(){
			if(!activeMP.active)
			if(loaded)
			activeScreen.mouseDragged();
		}
		
		
		public void loadRace(){
			
		}
		
		
		  SwarmLoginListener mySwarmLoginListener = new SwarmLoginListener() {

		    	// This method is called when the login process has started
		    	// (when a login dialog is displayed to the user).
		    	public void loginStarted() {
		    		println("LOGIN STARTESD");
		    		//getCloudVars();
		    	}

		    	// This method is called if the user cancels the login process.
		    	public void loginCanceled() {

		    		println("LOGIN CANCELLED");
		    		getCloudVars();
		    	}

		    	// This method is called when the user has successfully logged in.
		    	public void userLoggedIn(SwarmActiveUser user) {
		    		println("LOGIN LOGGED IN");
		    		getCloudVars();
		    		
		    	}

		    	// This method is called when the user logs out.
		    	public void userLoggedOut() {
		    		println("LOGIN LOGIN OUT");
		    		getCloudVars();
		    	}

		    };
		    
		    public void getCloudVars(){
		    	
		    	setVarCount=0;
	    		setFromCloud("Training Progress");
	    		setFromCloud("Championship Progress");
	    		setFromCloud("Personal Best 60 Inches");
	    		setFromCloud("Personal Best 100 Inches");
	    		setFromCloud("Personal Best 200 Inches");
	    		setFromCloud("Personal Best 400 Inches");
	    		setFromCloud("Personal Best 800 Inches");
	    		setFromCloud("Personal Best 1500 Inches");
	    		setFromCloud("Personal Best Off The Blocks");
		    }
		    
			public boolean surfaceTouchEvent(MotionEvent event) {

				 TouchEvents = event.getPointerCount();
				
				
				  if(loaded){
				  if(TouchEvents>1 && race.player.jumpY<1 && race.jumpLoading==false && race.player.jumping==false){
					  race.jumpLoading=true;
				  }
				  if(TouchEvents<2&&race.jumpLoading==true&&race.raceOn==true){
					  if(race.jumpLoading && race.hurdlesOn==true){
						  race.moveY+=(race.getLastY(mouseY)-race.lastY);
							
							
							race.lastY=race.getLastY(mouseY);
							race.player.jumping=true;
							race.jumpLoading=false;
							race.player.jumpingTargetHeight = (float) (race.track.hurdleHeight*1.5);
							
						}
				  }
				  }
				  return super.surfaceTouchEvent(event);
				}
			
			
			boolean paused=false;
			public void saveToCloud(final String varName,final String newVal){
				if (Swarm.isLoggedIn() && newVal!="0.0") {
				paused=true;
			    
				GotCloudDataCB callback = new GotCloudDataCB() {
				    public void gotData(String data) {

				        // Did our request fail (network offline, and uncached)?
				        if (data == null) {
				        	paused=false;

				    		println("DATANULL IN SAVE");
					        updateLocalData(varName,newVal,"0");
				            return;
				        }

				        // Has this key never been set?  Default it to a value...
				        if (data.length() == 0) {
				        
				            // In this case, we're storing levelProgress, default them to level 1.
				            data = "1";
				        }

				        updateLocalData(varName,newVal,data);
				        paused=false;
				    }
				}; 
				
				
				    Swarm.user.getCloudData(varName, callback);
				}
				
				
				
			}
			
			
			public void updateLocalData(String varName, String newVal,String data){
			    // Parse the level data for later use
		        if(varName.equals("Training Progress")){
		        	if(Integer.parseInt(data)<Integer.parseInt(newVal)){
		        		Swarm.user.saveCloudData(varName, newVal);
		        		trainingProgress=Integer.parseInt(newVal);
		        	}
		        	else
		        		trainingProgress=Integer.parseInt(data);
		        	
		       
		        	
		        }else
		        	 if(varName.equals("Championship Progress")){
				        	if(Integer.parseInt(data)<Integer.parseInt(newVal)){
				        		Swarm.user.saveCloudData(varName, newVal);
				        		championshipProgress=Integer.parseInt(newVal);
				        	}
				        	else
				        		championshipProgress=Integer.parseInt(data);
		        	 }else
		        	if(varName.equals("Personal Best Off The Blocks")){
		        		if(data.equals("0"))
		        			data="999";
				   		if(Float.parseFloat(data)>Float.parseFloat(newVal)){
			        		Swarm.user.saveCloudData(varName, newVal);
			        		pbs[6]=Float.parseFloat(newVal);
			        	}
			        	else
			        		pbs[6]=Float.parseFloat(data);
				   		
					        
				   	 }else
		        
		        
		   	 if(varName.indexOf("Personal Best")==0){
		   		if(data.equals("0"))
        			data="99999";
		   		if(Float.parseFloat(data)>Float.parseFloat(newVal)){
	        		Swarm.user.saveCloudData(varName, newVal);
	        		pbs[getRaceIndex(Integer.parseInt(varName.split(" ")[2]))]=Float.parseFloat(newVal);
	        	}
	        	else
	        		pbs[getRaceIndex(Integer.parseInt(varName.split(" ")[2]))]=Float.parseFloat(data);
		   		
			        
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
							    	loadGame();
							    
					            // Handle failure case.
						    	paused=false;
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
					    	 
					        if(varName.indexOf("Personal Best")==0){
					        	if(data.equals("0"))
				        			data="9999";
					        
					        if(varName.equals("Personal Best 60 Inches"))
					        	pbs[0]=Float.parseFloat(data);
					        if(varName.equals("Personal Best 100 Inches"))
					        	pbs[1]=Float.parseFloat(data);
					        if(varName.equals("Personal Best 200 Inches"))
					        	pbs[2]=Float.parseFloat(data);
					        if(varName.equals("Personal Best 400 Inches"))
					        	pbs[3]=Float.parseFloat(data);
					        if(varName.equals("Personal Best 800 Inches"))
					        	pbs[4]=Float.parseFloat(data);
					        if(varName.equals("Personal Best 1500 Inches"))
					        	pbs[5]=Float.parseFloat(data);
					        if(varName.equals("Personal Best Off The Blocks"))
					        	pbs[6]=Float.parseFloat(data);
					        }
					        
					        paused=false;
					        setVarCount++;
						    if(setVarCount>8){
						    	loadGame();
						    }
						    
					    }
					}; 
					
					
					    Swarm.user.getCloudData(varName, callback);
					   
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
			
			public int getRaceIndex(int i){
				if(i==60)
					return 0;
				else
				if(i==100)
					return 1;
				else
					if(i==200)
						return 2;
					else
						if(i==400)
							return 3;
						else
							if(i==800)
								return 4;
							else
								if(i==1500)
									return 5;
				return -1;
			}
			
			
			
			
			
			
			
}

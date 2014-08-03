package com.sports.runner;

import java.util.concurrent.Callable;

import processing.core.PFont;
import processing.core.PImage;
import android.content.Intent;
import android.util.DisplayMetrics;

import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmAchievement;
import com.swarmconnect.SwarmLeaderboard;
public class RaceClass extends Screen {

	
	Game parent;
	PImage shadow;
	
	boolean jumpingEnabled=false;
	
	float floatWaitTime;
	float reactionSpeed=0;
	
	boolean displayMessageBool=true;
	
	String ghostType;
	PFont sportsFont;
	String raceMode = "Race";
	int trackHeight;
	String practiceMode;
	boolean training;
	int falseStarts = 0;
	
	boolean setSaid = false;
	
	float fastestTimeYet=999999999;
	boolean dragging=false;
	int lastX=0;
	int lastY=0;
	
	float moveY=0;
	
	boolean hurdlesOn=false;

	int botCount=0;
	int maxBots=40;
	int raceStage = 0;
	int lastMillis = 0;
	int lastTouchMillis = 0;
	
	public float INCHHEIGHT = 100;//pixels per inch
	public float INCHWIDTH = 100;//pixels per inch
	Player player;
	
	Track track;
	int fps = 20;
	
	float floatStartTime=0;
	long longStartTime = 0;
	long serverStartTime=0;

	PImage[][][] runningSprites;
	int totalSpritesOnDisk=7;
	
	int trackInInches=100;
	
	GameSounds gameSounds;
	int playerPosition;
	boolean raceOn=false;

	float[] xTouch,yTouch;
	
	Intent menuIntent;
	
	
	int falseStartCount = 0;
	
	public RaceClass(Game parent){
		this.parent=parent;
	}
	
		public void loadMe(){
			
			shadow=loadImage("images/shadow.png");
			gameSounds=new GameSounds(parent);
			 xTouch = new float [2];
			  yTouch = new float [2]; 
			//menuIntent=new Intent(this, Menu.class);

		    System.gc();
			loadSprites();
			parent.background(255,0,0);
			 sportsFont=parent.createFont("fonts/sport.ttf", 24, true);
			 parent.frameRate(60);
			getInches();
			parent.textFont(sportsFont);
			parent.textSize(24);
			loadSounds();

			player = new Player(this,1);
		}
		
		boolean startMeasured=false;
		
		public void setMeUpOnce(){
			displayMessageBool=true;
			setMeUp();
		}
		
		public void setMeUp(){
			
			falseStartCount=0;
			player = new Player(this,1);
			reactionSpeed=0;
			if(practiceMode=="OFF")
				training=false;
			else{
				training=true;
				parent.flashMessages.add("Training Mode");
			}
			
			if(training){
				if(practiceMode.equals("Hurdles")){
					hurdlesOn=true;
				}
			}
			raceBegan=false;
			lastMillis=parent.millis();

			raceBegan=false;
			raceOn=false;
			player.finished=false;
			raceStage = 3;
			
			startMeasured=false;
			
		}
		
		public void loadSounds(){
			gameSounds.loadSounds(new String[]{"gshot1","set"},false);
		}
		
		public void loadSprites(){
			
			runningSprites=null;
			int spw = 120;
			int sph=148;
			System.gc();
			
			PImage spriteBlock;
			runningSprites=new PImage[totalSpritesOnDisk][6][6];
			
			for(int i=1;i<totalSpritesOnDisk+1;i++){
			spriteBlock = parent.loadImage("images/runner"+i+".gif");
			   
		    for(int x=0;x<6;x++)
		    	for(int y=0;y<6;y++){
		    		
		    		runningSprites[i-1][x][y] = spriteBlock.get(spw*(int)x,sph*(int)y,spw,sph);
		    	
		}
		    spriteBlock=null;
System.gc();
		}
		}
	  
	
		
		public void submitScore(float racetime){
			int leaderboardID = -1;
			
			if(trackInInches==800){
				leaderboardID = 16950;
			}else
				if(trackInInches==400){
					leaderboardID = 16948;
				}else
					if(trackInInches==200){
						leaderboardID = 16946;
					}else
						if(trackInInches==1500){
							leaderboardID = 16952;
						}
			if(trackInInches==100){
				leaderboardID = 16920;
			}else
			if(trackInInches==60){
				leaderboardID = 16922;
			}
			
			
			
			if (!Swarm.isLoggedIn()) {
				}else{

					Swarm.showLogin();
				}
			
			if(leaderboardID!=-1){
			SwarmLeaderboard.submitScore(leaderboardID, (float)(racetime));
			if(parent.getRaceIndex(trackInInches)>-1)
    		if(racetime<(parent.pbs[(parent.getRaceIndex(trackInInches))])){
    			parent.saveToCloud("Personal Best "+trackInInches+" Inches", ""+racetime);
    			parent.flashMessages.add("New Personal Best Time!");
    		}
			}
			if(reactionSpeed<1){
				if(reactionSpeed<parent.pbs[6]){
			SwarmLeaderboard.submitScore(17101,reactionSpeed);
			parent.saveToCloud("Personal Best Off The Blocks", ""+reactionSpeed);
			parent.flashMessages.add("New Personal Best Reaction Speed!");
				}
			
			}
		}
		
		
		public void drawMe(){

			if(raceStage == 3){
			//	if(millis()-lastMillis>=1000){
					
					lastMillis = parent.millis();
					dragging=false;
					raceStage=4;
					startRace();
			//	}
			}
			
			
			if(raceStage == 99){
				
					///if(millis()-lastMillis>=100){
						if(dragging && player.jumping==false){
							//if(jumpLoading==false)
							moveY+=(parent.min(parent.mouseY,track.trackY+track.trackDisplayHeight)-lastY);
							
							
							//lastX=mouseX;
							lastY=getLastY(parent.mouseY);
							
						}
					updatePositions();
					
					moveY=0;
					lastMillis = parent.millis();
					
				//}
			}
			if(raceStage == 5){//race Over
				moveY=player.atEaseSpeed;
				updatePositions();
				lastMillis=parent.millis();
				raceStage=6;
				if(training)
					raceStage=7;
				floatWaitTime = parent.millis();
			}
			if(raceStage==6 || raceStage == 7){
				parent.textAlign(parent.CENTER,parent.CENTER);
				parent.textSize(22);
				moveY=player.atEaseSpeed;
				updatePositions();
				parent.fill(0,190);
				parent.rect(0,0,parent.displayWidth,parent.displayHeight,3);
				parent.fill(255);
				if(!training)
				parent.text("Race time: "+player.myRaceTime,parent.displayWidth/2,(parent.displayHeight/2)-40);
				else{
					if(practiceMode.equals("Starts")){
						if(reactionSpeed!=999999999){
						parent.text("Reaction Speed:",parent.displayWidth/2,(parent.displayHeight/2)-40);
						parent.text(""+reactionSpeed,parent.displayWidth/2,(parent.displayHeight/2));
						}
						else{
						parent.text("Too Fast!",parent.displayWidth/2,(parent.displayHeight/2)-40);
						parent.text("Don't run before the gun!",parent.displayWidth/2,(parent.displayHeight/2));
						}
						
					}
				}
				
				if(raceStage == 7 && parent.millis() - floatWaitTime > 2500){
				//	rect(0,0,parent.displayWidth,parent.displayHeight,3);
					
					parent.fill(255);
					parent.text("Tap Screen to Try Again",parent.displayWidth/2,(parent.displayHeight/2)+40);
					
					if(parent.mousePressed){
						setMeUp();
					}
				
				}else{
					parent.fill(255);
					parent.text("Verifying Time",parent.displayWidth/2,(parent.displayHeight/2)+40);
				}
				if(!training)
				parent.text("Position: "+playerPosition,parent.displayWidth/2,(parent.displayHeight/2)+120);
				
			}
			
			if(!raceBegan){
				drawBeginPositions();
			}
			
		}
		
		boolean jumpLoading=false;
		
		public void mousePressed(){
			if(!startMeasured){
				if(!raceOn){
					reactionSpeed=999999999;
					soundShotBeginRace(true);
				}
				else{
					reactionSpeed = ((System.nanoTime()-longStartTime)/1000000000.0f);
					if(reactionSpeed<.2f){
						if(training){
						//SwarmAchievement.unlock(21413);
						if(parent.trainingProgress<3){
							parent.showSingleMessagePop(new String[]{"Content Unlocked!","Hurdle Practice now available"},null);
						}
						parent.saveToCloud("Training Progress", "3");
						
						}
					}
				}
				
				startMeasured=true;
				if(training){
					if(practiceMode.equals("Starts"))
						restartStartsTraining();
					
				}
			}
			
			if(raceOn){
			
			
				
			if((parent.TouchEvents>1 || dragging==true) && hurdlesOn){
				jumpLoading=true;
			}

			
			dragging=true;
			lastX=parent.mouseX;
			lastY=getLastY(parent.mouseY);
			
			}

			lastTouchMillis=millis();
		}
		
	
		
		
		public int getLastY(int my){
			
			int ret = my;
			if(ret<track.trackY)
				ret=track.trackY;
			if(ret>track.trackY+track.trackDisplayHeight)
				ret=track.trackY+track.trackDisplayHeight;
		
			return ret;
		}
		
		
		public void mouseDragged(){
			if(parent.TouchEvents>1){
				jumpLoading=true;
				//jumpPower=mouseY;
			}
		}
		
		
		public void mouseReleased(){
			
			if(jumpLoading && hurdlesOn==true){
				player.jumping=true;
				jumpLoading=false;
				player.jumpingTargetHeight = (float) (track.hurdleHeight*1.8);
			}
			dragging=false;
			moveY+=(getLastY(parent.mouseY)-lastY);
			lastY=getLastY(parent.mouseY);
		}
		
		float movingY=0;
		int ld=0;
		
		public void updatePositions(){
		
			if(player.jumping==false){
			if(moveY==0)
				if(movingY>0)
				movingY-=(movingY/2);
				else
				movingY+=(parent.abs(movingY/2));
			else
				movingY=moveY;
			}else{
				movingY = player.jumpY;
			}
		
				if(millis()-lastTouchMillis>10000){
					displayMessageBool=true;
					parent.println("yes it has happened");
					endRace();
				}
			
			track.moveMe(movingY);
			player.moveMe(movingY);
		}
		
	
		public void getInches(){
			
			DisplayMetrics dm = new DisplayMetrics();
			parent.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int widthPX=dm.widthPixels;
			int heightPX=dm.heightPixels;
			int dens=dm.densityDpi;
			double wi=(double)widthPX/(double)dens;
			double hi=(double)heightPX/(double)dens;
			double x = Math.pow(wi,2);
			double y = Math.pow(hi,2);
			double screenInches = Math.sqrt(x+y);
			INCHHEIGHT = (float) (heightPX/hi);
			INCHWIDTH = (float) (widthPX/wi);
		} 
		
		public float convertPixelsToInches(float pix){
			return (float)(pix/INCHWIDTH);
		}
		
		public float convertInchesToPixels(float inc){
			return (float)(inc * INCHWIDTH);
		}
		
		
		public void endRace(){
			
			displayMessageBool=false;
			
			setSaid=false;
			raceStage=5;
			raceOn=false;
			if(practiceMode.equals("Running")){
				if(parent.trainingProgress<2){
					parent.showSingleMessagePop(new String[]{"Content Unlocked!","'Starts' Practice now available"},null);
				}
				parent.saveToCloud("Training Progress", "2");
			}
			
		}
		
		public void startRace(){
			
			Thread t = new Thread() {
			public void run() {
				String[] temp = parent.loadStrings("http://imaga.me/now.php?"+parent.random(999999));
				if(temp!=null){
					try {
					serverStartTime = Long.parseLong(temp[0]);
					}
				catch(Exception e){
					serverStartTime=-1;
				}
				}else
					serverStartTime=-1;
				
				if(raceStage==4){
					if(practiceMode.equals("Running"))
					beginRunningTraining();
					else if(practiceMode.equals("Starts"))
					beginStartsTraining();
					else
					saySet(0);
				}
			
				
	}
			};
			t.start();
		}
		
		
		public void beginRunningTraining(){
			if(displayMessageBool){
			parent.showSingleMessagePop(new String[]{"How To Run","Drag you fingers on the track below"},new MyCallback(){ 
				  public void onMessageClose(){ 
					  soundShotBeginRace(true);
				  }
			});
			}else{
				soundShotBeginRace(true);
			}
		}
		
		public void beginStartsTraining(){
			if(displayMessageBool){
			parent.showSingleMessagePop(new String[]{"Wait for the gun shot before running"},new MyCallback(){ 
				  public void onMessageClose(){ 
					  saySet((int)random(200)+200);
				  }
			});}
			else{
				 saySet((int)random(200)+200);
			}
		}
		
		
		
		
		public void restartStartsTraining(){
			Thread t = new Thread() {
				public void run() {
					
					try {
						Thread.sleep(2000);
						if(raceStage==99){
							player.finished=true;
						endRace();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			};
			t.start();
		}
		
		boolean raceBegan = false;
		
		
		
		public void saySet(final int initialSleep){
			
			Thread t = new Thread() {
				public void run() {
					try {
						Thread.sleep(initialSleep);
						gameSounds.playSound("set");
						setSaid=true;
						Thread.sleep((int)parent.random(1500)+1000);
						
						soundShotBeginRace(false);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			};
			t.start();
				
		}
		
	
		
		public void soundShotBeginRace(boolean falseStart){
					
			dragging=false;
			if(raceOn==false && raceBegan == false && raceStage==4){
				playerPosition=1;
				player.startRace();
				floatStartTime=parent.millis();
				longStartTime = System.nanoTime();
				raceBegan=true;	
				raceOn=true;
				raceStage=99;
			}
			if(!falseStart && (raceStage == 99)){
				 parent.v.vibrate(100);
				gameSounds.playSound("gshot1");
			
			}
		}
		
		public void falseStart(){
			
		}
		
		
		public void drawBeginPositions(){

			track.moveMe(0);
			
			if(!setSaid){
			player.drawSetPosition();
			}else{
				player.drawReadyPosition();
			}
			
		}
		
		
		public void verifyTime(final float floatEnd, final long longEnd){
			
			Thread t = new Thread() {
			    public void run() {
			    
			    	float f = parent.millis();
			    	String[] temp = parent.loadStrings("http://imaga.me/now.php?"+parent.random(999999));
					
			    	long serverRaceTime=-1;
					
			    	if(temp!=null)
			    		{
							try {
								serverRaceTime = (long) ((Long.parseLong(temp[0])-serverStartTime)-(parent.millis()-f));
							}
						catch(Exception e){
							serverRaceTime=-1;
						}
						
					}
					
					if(serverRaceTime!=-1){
						float longRaceTime = (float)((longEnd - longStartTime)/1000000000.0f);
						float floatRaceTime = floatEnd-floatStartTime;
						
						System.out.println("RESULTS: SERVER="+(float)((float)serverRaceTime/1000)+" LONG="+longRaceTime+" FLOAT="+(floatRaceTime/1000)+" FRAMETIME="+player.framesHit/fps);
						

						
						
						
						submitScore(longRaceTime);
						if(longRaceTime<fastestTimeYet || fastestTimeYet==999999999){
							fastestTimeYet=longRaceTime;
						}
						
					}else{
						//PRESENT ERROR
					}
					
					
					
					raceStage=7;
					}
			    
			};
			t.start();
			
		}
		
		
		
		public float random(int r){
			return parent.random(r);
		}
		public int millis(){
			return parent.millis();
		}
		
		public void fill(int f){
			parent.fill(f);
		}
		public void fill(int r,int g, int b){
			parent.fill(r,g,b);
		}

		public void fill(int r,int g, int b,int a){
			parent.fill(r,g,b,a);
		}
		
		public PImage loadImage(String str){
			return parent.loadImage(str);
		}
		public void noFill(){
			parent.noFill();
		}
}

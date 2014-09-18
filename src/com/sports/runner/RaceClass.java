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

	boolean verified=false;
	Game parent;
	PImage shadow;
	boolean clickSetAllow=false;
	boolean clickSet=false;
	
	float difficulty=3;
	
	float minPos = 2;
	boolean championshipRace=false;
	String raceAgainst = "session";
	float shadowWidth;

	boolean longJumpOn=false;
	
	boolean raceCancelled=false;
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
	int maxFalseStarts = 3;
	
	boolean setSaid = false;
	
	float fastestTimeYet=999999999;
	boolean dragging=false;
	int lastX=0;
	int lastY=0;
	int r;
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

	PImage[][] shoeSprites;
	PImage[][] bodySprites;
	PImage[][] outlineSprites;
	PImage[][] tshirtSprites;
	PImage[][] shortsSprites;
	
	int totalSpritesOnDisk=7;
	
	int trackInInches=100;
	
	GameSounds gameSounds;
	int playerPosition;
	boolean raceOn=false;

	float[] xTouch,yTouch;
	
	Intent menuIntent;
	
	boolean medalRace=false;

	float[] finishTimes;
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

			if(practiceMode.equals("Running")){
				trackInInches=30;
			}
			if(practiceMode.equals("Hurdles")){
				trackInInches=100;
			}
			if(practiceMode.equals("Long Jump")){
				trackInInches=100;
			}
			shadowWidth = parent.random(1);
			//track=new Track(this,trackInInches,1,hurdlesOn);
			displayMessageBool=true;
			
			
			setMeUp();

			player = new Player(this,1);
		}
		
		public void setMeUp(){
			clickSetAllow=false;
			clickSet=false;
			verified=false;
track.reset();
			raceCancelled=false;
			falseStartCount=0;
			reactionSpeed=0;
			if(practiceMode=="OFF")
				training=false;
			else{
				training=true;
				parent.flashMessages.add("Training Mode: "+practiceMode);
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
			player.reset();
		}
		
		public void loadSounds(){
			gameSounds.loadSounds(new String[]{"gshot1","set","hurdle_drop"},false);
		}
		
		public void loadSprites(){
			
			
			int spw = 120;
			int sph=148;
			System.gc();

			PImage shoeBlock;
			PImage bodyBlock;
			PImage outlineBlock;
			PImage tshirtBlock;
			PImage shortsBlock;
			
			shoeSprites = new PImage[6][6];
			bodySprites = new PImage[6][6];
			outlineSprites = new PImage[6][6];
			tshirtSprites = new PImage[6][6];
			shortsSprites = new PImage[6][6];
			

			shoeBlock = parent.loadImage("images/runner/shoes.gif");
			shortsBlock = parent.loadImage("images/runner/shorts.gif");
			bodyBlock = parent.loadImage("images/runner/body.gif");
			outlineBlock = parent.loadImage("images/runner/outline.gif");
			tshirtBlock = parent.loadImage("images/runner/tshirt.gif");
			   
		    for(int x=0;x<6;x++)
		    	for(int y=0;y<6;y++){

		    		shoeSprites[x][y] = shoeBlock.get(spw*(int)x,sph*(int)y,spw,sph);
		    		shortsSprites[x][y] = shortsBlock.get(spw*(int)x,sph*(int)y,spw,sph);
		    		bodySprites[x][y] = bodyBlock.get(spw*(int)x,sph*(int)y,spw,sph);
		    		outlineSprites[x][y] = outlineBlock.get(spw*(int)x,sph*(int)y,spw,sph);
		    		tshirtSprites[x][y] = tshirtBlock.get(spw*(int)x,sph*(int)y,spw,sph);
		    	
		}
		    shoeBlock = null;
			shortsBlock = null;
			bodyBlock = null;
			outlineBlock =null;
			tshirtBlock = null;
System.gc();
		}
		
	  
	
		
		public void submitScore(float racetime){
			if((racetime)>0)
			if(raceCancelled==false && training == false){
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
			parent.println("RACETIME: "+racetime+" PBID "+parent.getRaceIndex(trackInInches,hurdlesOn)+" currentPB "+parent.pbs[(parent.getRaceIndex(trackInInches,hurdlesOn))]);
			if(parent.getRaceIndex(trackInInches,hurdlesOn)>-1)
    		if(racetime<(parent.pbs[(parent.getRaceIndex(trackInInches,hurdlesOn))]) && racetime>0){
    			parent.updatePersonalBests(trackInInches,hurdlesOn,racetime);

    			parent.println("SAVING TO CLOUD 2");
    			parent.saveToCloud("Personal Bests", parent.convertPBSToString());
    			parent.flashMessages.add("New PB!");
    		}
			}
			}
		}
		
		
		public void submitReactionTime(){
			if(!training){
			if(reactionSpeed<99 && reactionSpeed>0 && raceCancelled==false){
				
			SwarmLeaderboard.submitScore(17101,reactionSpeed);
			if(reactionSpeed<parent.pbs[0]){
			parent.pbs[0]=reactionSpeed;
			parent.println("SAVING TO CLOUD");
			parent.saveToCloud("Personal Bests",parent.convertPBSToString());
			parent.flashMessages.add("New Reaction Speed PB!");
				}
			
			}
			}
		}
		
		
		public void drawMe(){

			
			
			if(raceStage == 3){
			//	if(millis()-lastMillis>=1000){
					
					lastMillis = parent.millis();
					dragging=false;
					raceStage=4;
					r=(int)parent.random(4);
					parent.println("r=(int)parent.random(4) =: "+r);
					startRace();
			//	}
			}
			
			
			if(raceStage == 99){
				
					///if(millis()-lastMillis>=100){
						if(dragging){
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
				if(!longJumpOn)
				moveY=player.atEaseSpeed;
				
				updatePositions();
				
				
			
				lastMillis=parent.millis();
				if(verified==false)
				raceStage=6;
				if(training)
					raceStage=7;
				floatWaitTime = parent.millis();
				
			}
			if(raceStage==6 || raceStage == 7){
				parent.textAlign(parent.CENTER,parent.CENTER);
				parent.textSize(22);
				if(!longJumpOn)
				moveY=player.atEaseSpeed;
				
				
				updatePositions();
				
				
				parent.fill(0,190);
				parent.rect(0,0,parent.displayWidth,parent.displayHeight,3);
				parent.fill(255);
				if(!training==true){
				parent.text("Race time: "+player.myRaceTime,parent.displayWidth/2,(parent.displayHeight/2)-40);
				}else{
					if(practiceMode.equals("Starts")){
						if(reactionSpeed!=999999999){
						parent.text("Reaction Speed:",parent.displayWidth/2,(parent.displayHeight/2)-40);
						if(reactionSpeed>.5)
						parent.fill(200,0,0);
						else
							if(reactionSpeed>=.3)
								parent.fill(200,120,0);
						if(reactionSpeed<.3){
							parent.fill(0,200,0);
						parent.text(""+reactionSpeed,parent.displayWidth/2,(parent.displayHeight/2));
						parent.fill(255);
						parent.text("Awesome",parent.displayWidth/2,(parent.displayHeight/2)+40);
						
						}else{
						parent.text(""+reactionSpeed,parent.displayWidth/2,(parent.displayHeight/2));
						parent.fill(255);
						parent.text("Try get it below .3 seconds.",parent.displayWidth/2,(parent.displayHeight/2)+40);
						}
						parent.fill(255);
						}
						else{
						
						if(r==0){
							parent.text("Too Fast!",parent.displayWidth/2,(parent.displayHeight/2)-40);
							parent.text("Don't run before the gun!",parent.displayWidth/2,(parent.displayHeight/2));
							}else
							if(r==1){
								parent.text("Too Fast!",parent.displayWidth/2,(parent.displayHeight/2)-40);
								parent.text("Practice, practice, practice!",parent.displayWidth/2,(parent.displayHeight/2));
							}else
								if(r==2){
									parent.text("Too Fast!",parent.displayWidth/2,(parent.displayHeight/2)-40);
									parent.text("The gun comes just after the whistle!",parent.displayWidth/2,(parent.displayHeight/2));
								}else
									if(r==3){
										parent.text("Too Fast!",parent.displayWidth/2,(parent.displayHeight/2)-40);
										parent.text("Wait for the gun/vibration",parent.displayWidth/2,(parent.displayHeight/2));
									}
						}
						
					}else
					if(practiceMode.equals("Hurdles")){
						if(player.hurdlesHit>0){
							
							if(r==0){
							parent.text("Ouch!",parent.displayWidth/2,(parent.displayHeight/2)-40);
							parent.text("Hurdles can be difficult to master",parent.displayWidth/2,(parent.displayHeight/2));
							}else
							if(r==1){
								parent.text("Close!",parent.displayWidth/2,(parent.displayHeight/2)-40);
								parent.text("Remember to drag with two fingers.",parent.displayWidth/2,(parent.displayHeight/2));
								
							}else
							if(r==2){
								parent.text("Getting Better!",parent.displayWidth/2,(parent.displayHeight/2)-40);
								parent.text("Release touch before reaching hurdle",parent.displayWidth/2,(parent.displayHeight/2));
							}else
								if(r==3){
									parent.text("Nope!",parent.displayWidth/2,(parent.displayHeight/2)-40);
									parent.text("Run faster for longer jumps",parent.displayWidth/2,(parent.displayHeight/2));
								}
						}
					
					}
					
					if(practiceMode.equals("Long Jump")){
						if(player.longJumpLength<=2){
							
							if(r==0){
							parent.text("Nope!",parent.displayWidth/2,(parent.displayHeight/2)-40);
							parent.text("Remember to gain mometum before jumping",parent.displayWidth/2,(parent.displayHeight/2));
							}else
							if(r==1){
								parent.text("Close!",parent.displayWidth/2,(parent.displayHeight/2)-40);
								parent.text("Don't jump too late.",parent.displayWidth/2,(parent.displayHeight/2));
								
							}else
							if(r==2){
								parent.text("Getting Better!",parent.displayWidth/2,(parent.displayHeight/2)-40);
								parent.text("Drag fast before releasing to jump further",parent.displayWidth/2,(parent.displayHeight/2));
							}else
								if(r==3){
									parent.text("Nope!",parent.displayWidth/2,(parent.displayHeight/2)-40);
									parent.text("Run faster for longer jumps",parent.displayWidth/2,(parent.displayHeight/2));
								}
						}
					
					}
					
				}
				
				if(raceStage == 7 && parent.millis() - floatWaitTime > 2500){
				//	rect(0,0,parent.displayWidth,parent.displayHeight,3);
					
					parent.fill(255);
					parent.text("Tap Screen to Try Again",parent.displayWidth/2,(parent.displayHeight/2)+80);
					
					
					clickSetAllow=true;
					
				
				}else{
					parent.fill(255);
					if(!training)
					parent.text("Verifying Time",parent.displayWidth/2,(parent.displayHeight/2)+40);
				}
				if(!training){
					if(raceMode.equals("Time Trial") && botCount>0 && player.myRaceTime < fastestTimeYet )
						playerPosition=1;
					else if(raceMode.equals("Time Trial") && botCount>0)
						playerPosition=2;
					if(!longJumpOn)
				parent.text("Position: "+playerPosition,parent.displayWidth/2,(parent.displayHeight/2)+120);
				}
				if(longJumpOn){
					parent.text("Jump Length: "+(player.longJumpLength)+ " inches",parent.displayWidth/2,(parent.displayHeight/2)+120);
				}
				
			}
			
			if(!raceBegan){
				drawBeginPositions();
			}
			
		}
		
		boolean jumpLoading=false;
		
		public void mousePressed(){
			if(raceStage == 7  && clickSetAllow==true){
				clickSet=true;
			}
			
			if(!startMeasured){
				if(!raceOn && raceCancelled==false){
					raceCancelled=true;
					reactionSpeed=999999999;
					soundShotBeginRace(true);
					if(!training==true){
						falseStarts++;
						parent.showSingleMessagePop(new String[]{"False Start!","",falseStarts+"/"+maxFalseStarts+" False starts","","Tap Screen to Restart",""},new MyCallback(){ 
							  public void onMessageClose(){ 
								  setMeUp();
							  }
						});
					}else if(practiceMode.equals("Starts")){

						soundShotBeginRace(true);
						restartStartsTraining();
					}

					//todo//soundShotBeginRace(true);
				}
				else{
					reactionSpeed = ((System.nanoTime()-longStartTime)/1000000000.0f);
					if(practiceMode.equals("Starts")){
					if(reactionSpeed<.3f){
						
						//SwarmAchievement.unlock(21413);
						if(parent.trainingProgress<2){
							parent.trainingProgress=2;
							parent.showSingleMessagePop(new String[]{"Content Unlocked!","Hurdle Practice now available"},
									new MyCallback(){ 
								  public void onMessageClose(){ 

										restartStartsTraining();
								  }
							});
									
							startMeasured=true;
							parent.saveToCloud("Training Progress", "2");
						}else{
							startMeasured=true;
							restartStartsTraining();
						}
						
						}else{
							startMeasured=true;
							restartStartsTraining();
						}

					}
				}
				
				startMeasured=true;
				
			}
			
			if(raceOn){
			
			
				
			

			
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
			
		}
		
		
		public void mouseReleased(){
			if(raceStage == 7 && clickSet==true && clickSetAllow==true){
				clickSet=false;
				parent.println("NOW I AM IN HERE ");
				setMeUp();
			}
			if(dragging==true){
			
			dragging=false;
			moveY+=(getLastY(parent.mouseY)-lastY);
			lastY=getLastY(parent.mouseY);
			}
		}
		
		float movingY=0;
		int ld=0;
		
		public void updatePositions(){
		
			
			if(moveY==0)
				if(movingY>0)
				movingY-=(movingY/2);
				else
				movingY+=(parent.abs(movingY/2));
			else
			
			if(player.jumping==false){
				movingY=moveY;
			}else{
			movingY += player.jumpingTargetHeight;
			}
		
				if(millis()-lastTouchMillis>10000){
					displayMessageBool=true;
					parent.println("yes it has happened");
					endRace(false);
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
		
		boolean champAdvance=false;
		public void endRace(boolean legitFinish){
			champAdvance=false;
			displayMessageBool=false;
			
			setSaid=false;
			raceStage=5;
			raceOn=false;
			
			
			if(championshipRace){
				float comparisonFloat=1;
				
				if(longJumpOn==true){
				comparisonFloat=player.longJumpLength;
				if(comparisonFloat<minPos){
					champAdvance=true;
					parent.setMedal('1');
				}else
					if(comparisonFloat-(minPos/7)<minPos){
						parent.setMedal('2');
					}
					else
						if(comparisonFloat-(minPos/3)<minPos){
							parent.setMedal('3');
						}
				}else if(playerPosition<=9){
					parent.setMedal((""+playerPosition).charAt(0));
				}
					
				
				
			}
			
			if(practiceMode.equals("Running")){
				if(parent.trainingProgress<1){
					parent.showSingleMessagePop(new String[]{"Content Unlocked!","'Starts' Practice now available"},null);
					parent.trainingProgress=1;
					parent.saveToCloud("Training Progress", "1");
				
					
				}
				parent.showSingleMessagePop(new String[]{"Tap Here to Advance","Next Stage: Starts Practice"},
						new MyCallback(){ 
					  public void onMessageClose(){ 
						  if(parent.mouseY>parent.activeMP.y){
						  practiceMode ="Starts";
							parent.showRaceScreen(-1,(int)parent.random(10)+1,false,0,false,10,"Training","No Ghost","Starts","session",false);
						  }else{
							  setMeUp();
						  }
					  }
				},2,2);
			}
			
			if(practiceMode.equals("Long Jump")){
				if(parent.trainingProgress<4 && player.longJumpLength>2){
					parent.showSingleMessagePop(new String[]{"Content Unlocked!","Championship mode now available"},null);
					parent.trainingProgress=4;
					parent.saveToCloud("Training Progress", "4");
				
					
				}
				if(parent.trainingProgress>3){
				parent.showSingleMessagePop(new String[]{"Tap here to open menu"},
						new MyCallback(){ 
					  public void onMessageClose(){ 
						  if(parent.mouseY>parent.activeMP.y){
						  parent.showMainMenuScreen();
						  }else{
							 // setMeUp();
						  }
					  }
				},2,2);
			}
			}
			
			if(practiceMode.equals("Hurdles") ){
				if(player.hurdlesHit==0){
				if(parent.trainingProgress<3){
					parent.trainingProgress=3;
					parent.showSingleMessagePop(new String[]{"Content Unlocked!","You can now compete in hurdle races"},	new MyCallback(){ 
						  public void onMessageClose(){ 
							 
								  showLongJumpAdvanceScreen(); 
							
						  }
					});				
					parent.saveToCloud("Training Progress", "3");
				}else if(parent.trainingProgress>=3){
				showLongJumpAdvanceScreen(); 
				}
				}
			}
			if(practiceMode.equals("Starts")){
				if(reactionSpeed<.3)
				parent.showSingleMessagePop(new String[]{"Tap Here to Advance","Next Stage: Hurdles Practice"},
						new MyCallback(){ 
					  public void onMessageClose(){ 
						  if(parent.mouseY>parent.activeMP.y){
							  //todo check if this is always called 
						  practiceMode ="Hurdles";
							parent.showRaceScreen(-1,(int)parent.random(10)+1,false,0,true,30,"Training","No Ghost","Hurdles","session",false);
						  }else{
							 // setMeUp();
						  }
					  }
				},2,2);
			}
			
		}
		
		public void startRace(){
			verified=false;
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
					else if(practiceMode.equals("Hurdles"))
					beginHurdleTraining();
					else if(practiceMode.equals("Starts"))
					beginStartsTraining();
					else if(practiceMode.equals("Long Jump"))
					beginLongJumpTraining();
					else
					saySet(0);
				}
			
				
	}
			};
			t.start();
		}
		
		
		public void showLongJumpAdvanceScreen(){
			parent.showSingleMessagePop(new String[]{"Tap Here to Advance","Next Stage: Long Jump Practice"},
					new MyCallback(){ 
				  public void onMessageClose(){ 
					  if(parent.mouseY>parent.activeMP.y){
					  practiceMode ="Long Jump";
						parent.showRaceScreen(-1,(int)parent.random(10)+1,false,0,false,200,"Training","No Ghost","Long","session",true);
					  }else{
						  setMeUp();
					  }
				  }
			},2,2);
		}

		public void beginRunningTraining(){
			if(displayMessageBool){
			parent.showSingleMessagePop(new String[]{"How To Run","Drag you fingers on the track below","Run the length of the track above"},new MyCallback(){ 
				  public void onMessageClose(){ 
					  soundShotBeginRace(true);
				  }
			});
			}else{
				soundShotBeginRace(true);
			}
		}
		

		public void beginHurdleTraining(){
			if(displayMessageBool){
			parent.showSingleMessagePop(new String[]{"How To Jump","Drag two fingers on the track at the same time",""," a) Run the length of the track"," b) Don't knock any hurdles"},new MyCallback(){ 
				  public void onMessageClose(){ 
					  soundShotBeginRace(true);
				  }
			});
			}else{
				soundShotBeginRace(true);
			}
		}
		

		public void beginLongJumpTraining(){
			if(displayMessageBool){
			parent.showSingleMessagePop(new String[]{"Long Jump","Build up speed and jump before the white line",""," a) Jump further than 50 inches to advance"," b) Don't jump after the white line"},new MyCallback(){ 
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
			parent.showSingleMessagePop(new String[]{"Starts","Wait for the gun shot before running","Try get a reaction speed of less than .3 secs"},new MyCallback(){ 
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
						floatWaitTime=parent.millis(); 
						Thread.sleep(1000);

						floatWaitTime=parent.millis(); 
						if(raceStage==99){
							//submitReactionTime();
							player.finished=true;
						endRace(false);
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
		
		int currentRaceIndex=0;
		
		public void saySet(final int initialSleep){
			currentRaceIndex++;
			final int CI = currentRaceIndex;
			Thread t = new Thread() {
				public void run() {
					try {
						Thread.sleep(initialSleep);
						gameSounds.playSound("set");
						setSaid=true;
						Thread.sleep((int)parent.random(1500)+1000);
						if(currentRaceIndex == CI)
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
			if(!falseStart && (raceStage > 5)){
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
						
					}else{
						parent.flashMessages.add("Could not submit score");
						parent.flashMessages.add("No Internet Connection");
					}
					
					if(serverRaceTime!=-1){
						parent.println("VERIFY SERVERTIME WAS OK");
						float longRaceTime = (float)((longEnd - longStartTime)/1000000000.0f);
						float floatRaceTime = floatEnd-floatStartTime;
						
						System.out.println("RESULTS: SERVER="+(float)((float)serverRaceTime/1000)+" LONG="+longRaceTime+" FLOAT="+(floatRaceTime/1000)+" FRAMETIME="+player.framesHit/fps);
						

						
						
						if(!training){
						submitScore(longRaceTime);
						submitReactionTime();
						}
						parent.println("is it? aye");
						if(longRaceTime<fastestTimeYet || fastestTimeYet==999999999){
							parent.println("fuckin aye");
							//todo::this way is elegant but maybe not supportive of future changes
							fastestTimeYet=longRaceTime;
							track.knockedHurdlesTT=track.knockedHurdles;
						}
						
					}else{
						//PRESENT ERROR
					}
					
					
					parent.println("VERIFY GONNA CHANGE RACE STAGE");
					raceStage=7;
					verified=true;
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

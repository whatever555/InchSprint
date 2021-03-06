package com.sports.runner;

public class Bot extends Athlete{
	
	int hurdlesToHit=0;//for ghosts
	int hurdDist;
	public Bot(RaceClass parent,int track){
		super(parent,track);
	}
	
	
	public void moveMe(float moved){
		super.moveMe(moved);
		if(parent.track.hurdlesOn &&jumpY<=0){
			if(jumping==false && finished==false && distanceTravelled>parent.track.ho/2){
				
				if((distanceTravelled)%(parent.track.ho)>parent.track.ho-parent.convertInchesToPixels((float).05)
						||
					(distanceTravelled)%(parent.track.ho)>parent.track.ho-moved
						){
				jumping=true;
					jumpingTargetHeight=(float) ((parent.track.hurdleHeight/2)+(parent.parent.random((float) (parent.track.hurdleHeight*1.5))));
					jumpingVelocity=jumpingTargetHeight;
				
				}
			}
		}
		
		
		if(parent.hurdlesOn && distanceTravelled<parent.track.trackWidth  && distanceTravelled>parent.track.ho/2)
			if((distanceTravelled)%(parent.track.ho)>parent.track.ho-1
					||
					(distanceTravelled)%(parent.track.ho)<(parent.player.mw*3)){
				
				if(jumpY<parent.track.hurdleHeight){
					String entry = "("+((int)((distanceTravelled+parent.convertInchesToPixels(3))/parent.track.ho))+"-"+track+")";
					if(ghost==true){
						String e2="("+((int)((distanceTravelled+parent.convertInchesToPixels(3))/parent.track.ho))+"-1)";
						
						if(parent.track.knockedHurdlesTT.indexOf(e2)>-1){
							jumpingTargetHeight=parent.track.hurdleHeight-5;
							jumpingVelocity=jumpingTargetHeight;
							
						}else{
							jumpingTargetHeight=parent.track.hurdleHeight;
						}
					}
					if(jumpingTargetHeight<parent.track.hurdleHeight){
						parent.track.knockedHurdles+=entry;
						if(onScreen){
						float l=1;
					float r=1;
					float d = parent.parent.min(parent.track.trackWidth,distanceTravelled) - parent.parent.min(parent.track.trackWidth,parent.player.distanceTravelled);
					float dd = (parent.parent.abs(d)/parent.track.trackWidth)*1;
					if(d>0)
						r=dd;
					else
						r=1-dd;
					l=1-r;
					float v = 1-(parent.parent.abs(r-l)/2);
					parent.gameSounds.playSound("hurdle_drop",l,r,v);
					
						}
						hurdlesHit++;
						hurdleLag= moved/2;
						//System.out.println("TRCKNUM: "+track+" length  "+parent.finishTimes.length);
						if(!ghost)
						parent.finishTimes[track-2]+=.5;
					}
					
				}
			}
		
	}
}

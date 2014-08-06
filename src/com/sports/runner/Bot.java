package com.sports.runner;

public class Bot extends Athlete{
	
	int hurdlesToHit=0;//for ghosts
	int hurdDist;
	public Bot(RaceClass parent,int track){
		super(parent,track);
	}
	
	
	public void moveMe(float moved){
		super.moveMe(moved);
		if(parent.track.hurdlesOn){
			if(jumping==false && finished==false){
				String entry = "("+((int)((distanceTravelled+parent.convertInchesToPixels(3))/parent.track.ho))+"-"+track+")";
				if(parent.track.knockedHurdles.indexOf(entry)==-1)
				if((distanceTravelled)%(parent.track.ho)>parent.track.ho-parent.convertInchesToPixels((float)3)){
				jumping=true;
					jumpingTargetHeight=(parent.track.hurdleHeight/2)+(parent.parent.random((float) (parent.track.hurdleHeight*2.5)));
					
					if(ghost==true){
						String e2="("+((int)((distanceTravelled+parent.convertInchesToPixels(3))/parent.track.ho))+"-1)";
						
						if(parent.track.knockedHurdlesTT.indexOf(e2)>-1){
							jumpingTargetHeight=parent.track.hurdleHeight-5;
							System.out.println("FOUND IN THER: "+e2);
						}else{
							jumpingTargetHeight=parent.track.hurdleHeight;
							System.out.println("NOT NOT FOUND IN THER: "+e2);
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
					System.out.println("VOLUME: "+l+"  Right>> "+r);
					parent.gameSounds.playSound("hurdle_drop",l,r,v);
					
						}
						hurdlesHit++;
						hurdleLag= moved/2;
						//System.out.println("TRCKNUM: "+track+" length  "+parent.finishTimes.length);
						if(!ghost)
						parent.finishTimes[track-2]+=2;
					}
				}
			}
		}
	}
}

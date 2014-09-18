package com.sports.runner;

import java.util.ArrayList;

public class CDS {

	ArrayList<ChampionshipEvent> ce;
	public CDS(){
		ce = new ArrayList<ChampionshipEvent>();
		pop();
	}
	
	public void pop(){
		ChampionshipEvent c = new ChampionshipEvent();
		c.index=0;
		c.bots=3;
		c.qualifier = true;
		c.minPos=2;
		c.name = "Heat";
		c.trackLength=40;
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=1;
		c.bots=3;
		c.qualifier = true;
		c.minPos=1;
		c.trackLength=40;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=2;
		c.bots=9;
		c.minPos=2;
		c.trackLength=60;
		c.name="Medal Race";
		ce.add(c);
		
		c = new ChampionshipEvent();
		c.index=3;
		c.bots=9;
		c.hurdles=true;
		c.difficulty=(float)1.5;
		c.qualifier = true;
		c.minPos=2;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=4;
		c.bots=12;
		c.difficulty=(float)1.5;
		c.qualifier = true;
		c.minPos=1;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=5;
		c.bots=9;
		c.hurdles=true;
		c.difficulty=(float)1.5;
		c.minPos=2;
		c.name="Medal Race";
		ce.add(c);
		

		c = new ChampionshipEvent();
		c.index=6;
		c.bots=12;
		c.trackLength=100;
		c.hurdles=true;
		c.difficulty=(float)2;
		c.qualifier = true;
		c.minPos=3;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=7;
		c.bots=12;
		c.difficulty=(float)2;
		c.qualifier = true;
		c.minPos=2;
		c.trackLength=200;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=8;
		c.bots=15;
		c.hurdles=true;
		c.difficulty=(float)2.5;
		c.minPos=2;
		c.name="Medal Race";
		ce.add(c);
		

		c = new ChampionshipEvent();
		c.index=9;
		c.bots=1;
		c.trackLength=60;
		c.hurdles=false;
		c.difficulty=(float)3;
		c.qualifier = true;
		c.minPos=1;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=10;
		c.bots=12;
		c.difficulty=(float)3;
		c.qualifier = true;
		c.minPos=2;
		c.trackLength=200;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=11;
		c.bots=15;
		c.trackLength=100;
		c.hurdles=true;
		c.difficulty=(float)3.1;
		c.minPos=3;
		c.name="Medal Race";
		ce.add(c);
	}
}

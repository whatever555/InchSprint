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
		c.minPos=2;
		c.trackLength=40;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=2;
		c.bots=22;
		c.minPos=5;
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
		c.bots=36;
		c.difficulty=(float)1.1;
		c.qualifier = true;
		c.minPos=12;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=5;
		c.bots=9;
		c.hurdles=true;
		c.difficulty=(float)1.8;
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
		c.difficulty=(float)2.3;
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
		c.difficulty=(float)2.8;
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
		

		c = new ChampionshipEvent();
		c.index=12;
		c.bots=40;
		c.trackLength=40;
		c.hurdles=true;
		c.difficulty=(float)3.5;
		c.qualifier = true;
		c.minPos=5;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=13;
		c.bots=40;
		c.difficulty=(float)3.8;
		c.qualifier = true;
		c.minPos=7;
		c.trackLength=100;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=14;
		c.bots=4;
		c.trackLength=60;
		c.hurdles=false;
		c.difficulty=(float)4;
		c.minPos=1;
		c.name="Medal Race";
		ce.add(c);
		
		

		c = new ChampionshipEvent();
		c.index=15;
		c.bots=11;
		c.trackLength=200;
		c.hurdles=true;
		c.difficulty=(float)4.4;
		c.qualifier = true;
		c.minPos=2;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=16;
		c.bots=25;
		c.difficulty=(float)4.8;
		c.qualifier = true;
		c.minPos=3;
		c.trackLength=100;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=17;
		c.bots=34;
		c.trackLength=400;
		c.hurdles=false;
		c.difficulty=(float)5;
		c.minPos=6;
		c.name="Medal Race";
		ce.add(c);
		

		c = new ChampionshipEvent();
		c.index=18;
		c.bots=6;
		c.trackLength=50;
		c.hurdles=true;
		c.difficulty=(float)5.1;
		c.qualifier = true;
		c.minPos=2;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=19;
		c.bots=12;
		c.difficulty=(float)5.2;
		c.qualifier = true;
		c.minPos=2;
		c.trackLength=50;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=20;
		c.bots=26;
		c.trackLength=100;
		c.hurdles=false;
		c.difficulty=(float)5.3;
		c.minPos=3;
		c.name="Medal Race";
		ce.add(c);
		

		c = new ChampionshipEvent();
		c.index=21;
		c.bots=6;
		c.trackLength=80;
		c.hurdles=false;
		c.difficulty=(float)5.4;
		c.qualifier = true;
		c.minPos=3;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=22;
		c.bots=6;
		c.difficulty=(float)5.5;
		c.qualifier = true;
		c.minPos=1;
		c.trackLength=60;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=23;
		c.bots=22;
		c.trackLength=100;
		c.hurdles=false;
		c.difficulty=(float)5.6;
		c.minPos=2;
		c.name="Medal Race";
		ce.add(c);
		

		c = new ChampionshipEvent();
		c.index=24;
		c.bots=62;
		c.trackLength=100;
		c.hurdles=true;
		c.difficulty=(float)5.7;
		c.qualifier = true;
		c.minPos=12;
		c.name = "Hurdle Heat";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=25;
		c.bots=16;
		c.difficulty=(float)5.8;
		c.qualifier = true;
		c.minPos=3;
		c.trackLength=60;
		c.name = "Qualifier";
		ce.add(c);

		c = new ChampionshipEvent();
		c.index=26;
		c.bots=17;
		c.trackLength=55;
		c.hurdles=true;
		c.difficulty=(float)6;
		c.minPos=3;
		c.name="Medal Race";
		ce.add(c);
	}
}

import java.util.Random;

import processing.core.PApplet;

public class main {
	public static gameState game = new gameState();
	public static graph graph = new graph();
	public static agentActions action = new agentActions();
	public static int turnCount = 0;
	public static Random coinToss = new Random();

	public static void main(String[] args) {

		graph.initialization();

		PApplet.main(new String[] { "--present", "NewGUI" });

		agent agent1 = new randomAgent();
		agent agent2 = new randomAgent();
		WorldUpdate world = new WorldUpdate();
		
		//new CLIPS interface - uses test template
		CLIPS_Interface testAgent = new CLIPS_Interface("testAgent.clp");
		testAgent.CLIPS(graph);
		
		int tossResult = -1; 
		
		//commented out so you can see how CLIPS works
		/*
		while(!game.getState()) {
			turnCount++;
			
			tossResult = coinToss.nextInt(2);
			
			System.out.println("---------------Roud "+turnCount+" is stared by player "+(tossResult+1)+"---------------");

			pause(200);
			if (tossResult == 0)
			{	
				agent1.performAction(1);
				agent2.performAction(2);
			}
			else if (tossResult == 1)
			{	
				agent2.performAction(2);
				agent1.performAction(1);
			}
			
			world.Update();
			game.check();
		}
		System.out.println("Game finished in round "+ turnCount);
		*/
	}
	public static void pause(int time){
		try {
		    Thread.sleep(time);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
}

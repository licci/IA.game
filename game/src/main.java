import java.util.Random;

import processing.core.PApplet;

public class main {
	public static gameState game = new gameState();
	public static graph graph = new graph();
	public static agentActions action = new agentActions();
	public static Random coinToss = new Random();

	
	public static void main(String[] args) {
		
		graph.initialization();
		
		PApplet.main(new String[] { "--present", "NewGUI" });

	
		
		//agent agent1 = new agent(1);
		//agent agent2 = new agent(2);
		
		
		//I think it is how the game turn might look like
		//broken agents are commented out
		//all agent actions should be in "if"
		//game stuff - world update etc. in "while"
		////
		int tossResult = -1;
		
		while(!game.getState()) {
			tossResult = coinToss.nextInt(2);
			System.out.println(tossResult);
			
			if (tossResult == 0)
			{
				//agent1.randomAction(0);
				//agent2.randomAction(13);
			}
			else if (tossResult == 1)
			{	
				//agent2.randomAction(13);
				//agent1.randomAction(0);
			}
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			game.check();
		}
		
		
		/*
		action.attack(1,0,1,10,10,10);
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		//step 1
		graph.map[0].changeRoadState(2, false);
		graph.map[5].changeRoadState(7, false);
		action.attack(1,0,1,10,10,10);
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
	
		graph.map[0].changeRoadState(2, true);
		graph.map[5].changeRoadState(7, true);
		//graph.map[0].repairRoadTo(2);
		action.attack(2, 13, 12, 10, 10, 10);

		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 3
		action.move(1,1,0,0,10,0);
		
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 4
		action.attack(2,12,8,0,0,10);
		
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 5
		action.attack(1, 0, 1, 10, 0, 0);
		*/
	}

}

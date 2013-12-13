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
		
		clipsPerformAction agents = new clipsPerformAction ("testAgent.clp","testAgent.clp");
		
		//agents.performAction(1);
		//agents.performAction(2);
		int tossResult = -1; 
		
		//Commented out so you can see how CLIPS works
		
		while(!game.getState()) {
			turnCount++;
			
			tossResult = coinToss.nextInt(2);
			
			System.out.println("---------------Roud "+turnCount+" is stared by player "+(tossResult+1)+"---------------");

			pause(200);
			if (tossResult == 0)
			{	
				
				System.out.println("---Player 1---------------");
				Statistics.P1.addCoinWin();
				agent1.performAction(1);
				game.check();
				if (game.getState()) break;
				
				System.out.println("---Player 2---------------");
				agent2.performAction(2);
				game.check();
				if (game.getState()) break;
			}
			else if (tossResult == 1)
			{	
				System.out.println("---Player 2---------------");
				Statistics.P2.addCoinWin();
				agent2.performAction(2);
				game.check();
				if (game.getState()) break;
				System.out.println("---Player 1---------------");
				agent1.performAction(1);
				game.check();
				if (game.getState()) break;
			}
			
			world.Update();
			game.check();
		}
		System.out.println("Game finished in round "+ turnCount);
		
	}
	public static void pause(int time){
		try {
		    Thread.sleep(time);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
}

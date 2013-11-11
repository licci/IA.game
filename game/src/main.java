import processing.core.PApplet;

public class main {
	public static gameState game = new gameState();
	public static graph graph = new graph();
	public static agentActions action = new agentActions();

	
	public static void main(String[] args) {
		
		graph.initialization();
		
		PApplet.main(new String[] { "--present", "NewGUI" });

	
	
		agent agent1 = new agent();
		

		
		for (int i = 0; i < 36; i++) {
	
		
	
			agent1.randomAction();
		
		}
		//action.attack(1,0,1,10,10,10);
		/*	try {
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
		//action.move(1,1,0,0,10,0);
		
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 4
		//action.attack(2,12,8,0,0,10);
		
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 5
		//action.attack(1, 0, 1, 10, 0, 0);
	
	*/}

}

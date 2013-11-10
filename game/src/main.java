import processing.core.PApplet;

public class main {
	public static gameState game = new gameState();
	public static graph graph = new graph();
	public static agentActions action = new agentActions();
	public static void main(String[] args) {
		
		graph.initialization();
		
		PApplet.main(new String[] { "--present", "NewGUI" });

		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 1
		action.attack(1,0,1,10,10,10);
		graph.map[0].changeRoadState(2, false);
		graph.map[5].changeRoadState(7, false);
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 2
		action.attack(2, 13, 12, 10, 10, 10);
		graph.map[0].changeRoadState(2, true);
		graph.map[5].changeRoadState(7, true);
		
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
		action.attack(1, 1, 4, 10, 0, 0);

	}

}

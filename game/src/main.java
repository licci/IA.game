import processing.core.PApplet;

public class main {
	public static graph graph = new graph();
	public static agentActions action = new agentActions();

	
	public static void main(String[] args) {
		
		graph.initialization();
		
		PApplet.main(new String[] { "--present", "NewGUI" });

	
		
		agent agent1 = new agent(1);
		agent agent2 = new agent(2);
		

		
		for (int i = 0; i < 36; i++) {
		int	randomNum = 1+(int)(Math.random()*2); 
		
		
		if (randomNum == 1)
		{
			agent1.randomAction(0);
		}
		else
		{	
			agent2.randomAction(13);
		}
		}
		//action.attack(1,0,1,10,10,10);
		
		/*	try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
	
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

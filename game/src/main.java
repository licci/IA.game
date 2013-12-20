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
		
		//agents
		
		//Agnieszka åwiderska
		agneseAgent Agnieszka = new agneseAgent();
		String agnieszka = "agnieszkaAgent.clp";
		
		//Dariusz Woüniak
		agentDarek DarekL = new agentDarek(0, 13);
		agentDarek DarekP = new agentDarek(13, 0);
		
		//String Darek = ""; 
		
		//Jakub Matusiak
		agent Jakub = new JakubSearch();
		String jakub = "JakubAgent.clp";
		
		//Joanna Olczyk
		JoannaAgent Joanna = new JoannaAgent();
		String joanna = "JoannaKBAgent.clp";
		
		//Maciej Kraúkiewicz
		MaciejKAgent Maciej = new MaciejKAgent();
		String maciej = "MaciejClipsAgent.clp";
		
		//Marcos Meirino
		agent MarcosL = new SearchingAgentMarcosMeirino(1);
		agent MarcosP = new SearchingAgentMarcosMeirino(2);
		
		//String marcos = "";
		
		//Pawe≥ Gonciarek
		LaughingManSearch PaweL = new LaughingManSearch(1);
		LaughingManSearch PaweP = new LaughingManSearch(2);
		String pawel = "LaughingMan.clp";
		
		//LaughingManRandom randomL = new LaughingManRandom(1);
		LaughingManRandom randomP = new LaughingManRandom(2);
		
		String laughingman = "LaughingMan.clp";
		
		//Sylwia Karbowiak
		
		agent Sylwia = new SylwiaAgent();
		
		String sylwia = "SylwiaKBAgent.clp";
		
		//Tomasz Pajπk
		
		
		//end agents
		clipsPerformAction agents = new clipsPerformAction(jakub,jakub);
		

		int tossResult = -1; 
		
		int wait = 1000;
		
		while(!game.getState()) {
			turnCount++;
			
			tossResult = coinToss.nextInt(2);
			
			System.out.println("---------------Roud "+turnCount+" is stared by player "+(tossResult+1)+"---------------");

			pause(200);
			if (tossResult == 0)
			{	
				pause(wait);
				System.out.println("---Player 1---------------");
				Statistics.P1.addCoinWin();
				agents.performAction(1);

				game.check();
				
				if(game.getState()) break;
				pause(wait);
				System.out.println("---Player 2---------------");
				agents.performAction(2);
				
				game.check();
			}
			else if (tossResult == 1)
			{	
				pause(wait);
				System.out.println("---Player 2---------------");
				Statistics.P2.addCoinWin();
				
				agents.performAction(2);

				
				game.check();
				
				if(game.getState()) break;
				pause(wait);
				System.out.println("---Player 1---------------");
				agents.performAction(1);
				
				game.check();
			}
			
			world.Update();
			//game.check();
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

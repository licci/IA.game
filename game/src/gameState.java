
public class gameState {
	boolean gameFinished = false;
	
	public boolean getState()
	{
		return gameFinished;
	}
	
	public void setGameState(boolean state)
	{
		gameFinished = state;
	}
	
	public void check()
	{
		if(graph.map[0].belongs() == 2 ) {
			gameFinished = true; 
			System.out.println("Player 2 WON!");
			
			//FOR STATISTICS
			Statistics.P2.getFulldata();
			Statistics.P1.getFulldata();
			//END FOR STATISTICS

		}
		else if (graph.map[13].belongs() == 1){
			gameFinished = true; 
			System.out.println("Player 1 WON!");
			
			//FOR STATISTICS
			Statistics.P1.getFulldata();
			Statistics.P2.getFulldata();
			//END FOR STATISTICS

		}else System.out.println("Game in progress!");
		
	}

}


public class gameState {
	boolean gameFinished = false;
	
	public boolean getGameState()
	{
		return gameFinished;
	}
	
	public void setGameState(boolean state)
	{
		gameFinished = state;
	}
	
	public void gameCheck()
	{
		if(graph.map[0].belongs() == 2 ) {
			gameFinished = true; 
			System.out.println("Player 2 WON!");
		}
		else if (graph.map[13].belongs() == 1){
			gameFinished = true; 
			System.out.println("Player 1 WON!");
		}else System.out.println("Game in progress!");
		
	}

}

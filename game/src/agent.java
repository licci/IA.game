import java.util.ArrayList;
import java.util.List;


public class agent {


	int counter=0;
	int turnNr = 1;
	boolean aid1move = false;
	boolean aid2move = false;
	int aid1LastNode = 0;
	int aid2LastNode = 13;

	
	
	public static agentActions action = new agentActions();
	
	
	
	public void randomAttack(int agentId)
	{
		

		List<Integer> availableNodes = new ArrayList<Integer>();
		
		for(int x = 0; x < 14; x = x+1) {
	        
			if (graph.map[x].belongs() == agentId)
			{
			
			availableNodes.add(x);
			}
	      }
		
		
		
        int RandomNodeIndex = 0+(int)(Math.random()*availableNodes.size());
        int RandomNode = availableNodes.get(RandomNodeIndex);
        
        
		System.out.println("PRE-ATTACK DEBUG - "+agentId+" / "+RandomNode);
		int availableStart[] = graph.map[RandomNode].getAvailableNodes();
		boolean ifSuccessAttack = false;
	    for(int x : availableStart){
	    	
	    	
			System.out.println("ATTACK DEBUG - "+x+" / "+RandomNode);
	    	
	    	if (x>0)
	    	{
	   ifSuccessAttack = action.attack(agentId,RandomNode,x,graph.map[RandomNode].numberOfSquares(),graph.map[RandomNode].numberOfCircles(),graph.map[RandomNode].numberOfTriangles());	
	    	
	    	if (ifSuccessAttack == true)
	    	{
	    	
    			if (agentId == 1) { aid1move = true; aid1LastNode = x; }
    			if (agentId == 2) { aid2move = true;  aid2LastNode = x; }
             }
    		else
    		{
    			
    			if (agentId == 1) { aid1move = true; }
    			if (agentId == 2) { aid2move = true; }
    			
    		}
        }
	    
	    if (ifSuccessAttack == false){
	    	 randomAction();
	    }
 	}
	}
	
	public void randomMove(int agentId)
	{
		
		
	List<Integer> availableNodes = new ArrayList<Integer>(); // getting nodes that belongs to given player
		
		for(int x = 0; x < 14; x = x+1) {
	        
			if (graph.map[x].belongs() == agentId)
			{
			
			availableNodes.add(x);
			}
	      }
		
		
		
        int RandomNodeIndex = 0+(int)(Math.random()*availableNodes.size()); // random choice of player's node
        int RandomNode = availableNodes.get(RandomNodeIndex);
		
		
		int availableStart[] = graph.map[RandomNode].getAvailableNodes();
		boolean ifSuccessMove = false;
	    for(int x : availableStart)
	    {
	    	if (x>0)
	    	{
	    		System.out.println("MOVE DEBUG - "+x+" / "+RandomNode);
			
	    		ifSuccessMove =   action.move(agentId,RandomNode,x,graph.map[RandomNode].numberOfSquares(),graph.map[RandomNode].numberOfCircles(),graph.map[RandomNode].numberOfTriangles());	
       
	    		if (ifSuccessMove == true)
	    		{
	    			
	    			if (agentId == 1) { aid1move = true; aid1LastNode = x; }
	    			if (agentId == 2) { aid2move = true;  aid2LastNode = x; }
	             }
	    		else
	    		{
	    			
	    			if (agentId == 1) { aid1move = true; }
	    			if (agentId == 2) { aid2move = true; }
	    			
	    		}
	    		
	    	}

	    	
        }
	    
	    if (ifSuccessMove == false)
	    {
	    	
	    	 randomAction();
	    	
	    }

	}
	
	
	public void randomAction()
	{
	int currentAgent = 1;
	
	if (aid1move == true && aid2move == true)
	{
		turnNr++;
		aid1move = aid2move = false; // start new turn;
		
		System.out.println("------------------------------------------ START TURN  - "+turnNr+" ----------------------------------");
		
	}
	
	if (aid1move == true) currentAgent = 2;
	if (aid2move == true) currentAgent = 1;
		
	if (aid1move == false && aid2move == false)
	{
	if (1+(int)(Math.random()*2) == 1)
	{
		
		currentAgent = 1;
		
	}
	else
	{
		
		currentAgent = 2;
		
	}
	}
	
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		
		int	randomNum = 1+(int)(Math.random()*2); 
		
		
		System.out.println("ACTION - "+currentAgent);
		
		if (randomNum == 1) randomMove(currentAgent);
		if (randomNum == 2) randomAttack(currentAgent);
		
	}
	

	
	

	
	
}

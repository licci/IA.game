
public class agent {

	int agentId;
	int counter=0;
	
	public static agentActions action = new agentActions();
	
	public void setAgentId(int aid)
	{
		
		
		agentId = aid;
		
	}
	
	public void randomAttack(int startNode)
	{
		int availableStart[] = graph.map[startNode].getAvailableNodes();
		boolean ifPass = false;
	    for(int x : availableStart)
        
	    {
	    	
	    	
			System.out.println("ATTACK DEBUG - "+x+" / "+startNode);
	    	
	    	if (x>0)
	    	{
	   boolean ifSuccessAttack = action.attack(agentId,startNode,x,graph.map[startNode].numberOfSquares(),graph.map[startNode].numberOfCircles(),graph.map[startNode].numberOfTriangles());	
	    	
	    	if (ifSuccessAttack == true)
	    	{
	    		ifPass = true;
	    		System.out.println("ATTACK SuCC - "+x+" / "+startNode);
	    		 randomAction(x);  
	    		
	    	}
	    	
	    	}
	   
        
        }
	    
	    if (ifPass == false)
	    {
	    	
	    	 randomAction(startNode);
	    	
	    }
 	}
	
	public void randomMove(int startNode)
	{
		int availableStart[] = graph.map[startNode].getAvailableNodes();
		boolean IfPass = false;
	    for(int x : availableStart)
        
	    {
	    	if (x>0)
	    	{
	    		System.out.println("MOVE DEBUG - "+x+" / "+startNode);
			
	    		boolean ifSuccessMove =   action.move(agentId,startNode,x,graph.map[startNode].numberOfSquares(),graph.map[startNode].numberOfCircles(),graph.map[startNode].numberOfTriangles());	
       
	    		if (ifSuccessMove == true)
	    		{
	    			IfPass = true;
	    		    randomAction(x);  
	    			
	    		}
	    		
	    	}

	    	
        }
	    
	    if (IfPass == false)
	    {
	    	
	    	 randomAction(startNode);
	    	
	    }

	}
	
	
	public void randomAction(int startNode)
	{
	
		if (counter > 70) return;
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		
		int	randomNum = 1+(int)(Math.random()*2); 
		
		counter++;
		if (randomNum == 1) randomMove(startNode);
		if (randomNum == 2) randomAttack(startNode);
		
	}
	
	public agent(int aid)
	{
		

		
		
	setAgentId(aid);
	


		
	}
	
	
}

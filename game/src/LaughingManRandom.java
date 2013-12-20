import java.util.Random;

//CLASS___________________________________________________________________________________________________________________________________________________________________
public class LaughingManRandom {
	
	/*
	 * IMPORTANT NOTES
	 * If agent decides to move units - it moves ALL units from one node to other node.
	 * If agent decides to attack - it has few options:
	 * CAUTIOS ATTACK - use optimal unit count
	 * STRONG ATTACK - use more then optimal unit count
	 * ALL OUT ATTACK - use all forces
	 * 
	 * Agent will decide to attack if result of such attack is beneficial - it may not always result in taking a node.
	 * 
	 */
	
	public static Random value = new Random();
	
	//agent's copy of game world
	public copyOfGraph world = new copyOfGraph();
	
	//agents id 
	int ID;
	int enemyHQ;
	int enemyID;
	int [] action;
	
	//sets how many moves ahead will be checked 
	int actionCount = 3;
	
	int attackType = 0;
	/*place to store actions that can be taken
	 * 90 - maximal theoretical number of possible moves in current graph
	 * 0 - moveId
	 * 1 - actionType ( 0 - move; 1 -attack )
	 * 2 - startNode
	 * 3 - targetNode
	 * 4 - overall action value - agent will test all possible moves "x" steps forward and return the sum of their value here
	 * 5 - attack type (0 - its a move, 1 - CA, 2 - SA, 3 - AA) (firstAction only)
	 */
	int [][] possibleActions = new int [5][90];
	int [][] firstAction = new int [6][90];
	
	int [][] actionsList = new int [5][5];
	
	int actionCounter = 1;
	
	//constructor
	//create new agent with given ID and get current world state for him
	public LaughingManRandom(int player){
		ID = player;
		if(ID == 1) { enemyHQ = 13; enemyID = 2;}
		else { enemyHQ = 0; enemyID = 1;}
		
		world.initialization();
	}
	
	public void performAction(){
		getWorldState();
		actionCounter = 1;
		//check moves
		action = dijkstra();
		if(action[1]==0){
			if( main.action.move(ID,action[2],action[3],action[5],action[6],action[7]) ) System.out.println(ID+" Move succesful");
			else {
				System.out.println(ID+" Move NOT succesful!!!!!!!!!!!!!!!!!!");
				//main.pause(20000);
			}
		}
		else if(action[1]==1){
			if( main.action.attack(ID,action[2],action[3],action[5],action[6],action[7]) ) System.out.println(ID+" Attack succesful");
			else {
				System.out.println(ID+" Attack NOT succesful!!!!!!!!!!!!!!!!!!");
				//main.pause(20000);
			}
		}
		else if(action[1] == -1) System.out.println("No moves to do - waiting!");
	}
	

//WORLD STATE AND ESTIMATES______________________________________________________________________________________________________________________________________________
	
	//before each move world state will be updated
	void getWorldState()
	{
		
		//get world state
		//this data has some values that should not be accessible by an agent
		//so for each node that is not in view range (hidden by Fog of War) prediction about owner and unit count is made
		//no assumption is made about road states - agent gets current state of all roads 
		for(int i = 0; i < 14; i++){
			world.map[i].setOwner(graph.map[i].belongs());
			world.map[i].setNumberOfSquares(graph.map[i].numberOfSquares());
			world.map[i].setNumberOfCircles(graph.map[i].numberOfCircles());
			world.map[i].setNumberOfTriangles(graph.map[i].numberOfTriangles());
			world.map[i].setAvailableNodes(graph.map[i].getAvailableNodes());
		}
	}
	

	
//ACTIONS____________________________________________________________________________________________________________________________________________________________________
	//main search algorithm
	//commits each of actions that are possible in current turn and checks 4 next moves
	//initial action with best value and best following actions values is selected as action to commit
	/*place to store actions that can be taken
	 * 90 - maximal theoretical number of possible moves in current graph
	 * 0 - moveId
	 * 1 - actionType ( 0 - move; 1 -attack )
	 * 2 - startNode
	 * 3 - targetNode
	 * 4 - overall action value - agent will test all possible moves "x" steps forward and return the sum of their value here
	 */
	public int [] dijkstra(){
		int [] actionToCommit = new int [8];
		int [] optimalUnitCount = new int [3];
		//take all currently possible actions
		firstAction = getPossibleActions(firstAction);
		
		//for each possible action
		for(int i = 0; i < 90; i++){
			//if action exists
	    	if(firstAction[0][i] > 0){
	    		//estimate action value
	    		//firstAction[4][i] = estimateActionValue();
	    		firstAction[4][i] = estimateActionValue();
	    		firstAction[5][i] = value.nextInt(3);
	    		
	    	}
		}
			int maxValue = firstAction[4][0];
			int maxValueRowNumber = firstAction[0][0];
			for(int i = 0; i < 90; i++){ 
				//check if action exists
				if(firstAction[0][i] > 0){ 
					//check if max value is higher
	    			//if (maxValue < firstAction[4][i]) {
					//System.out.println(firstAction[4][i]+" is bigger then "+maxValue+"?");
					if (firstAction[4][i] > maxValue) {
						//System.out.println("YES");
	    				//check if there are units in start node
	    				if(world.map[firstAction[2][i]].numberOfSquares() >= 1 || world.map[firstAction[2][i]].numberOfCircles() >= 1 || world.map[firstAction[2][i]].numberOfTriangles() >= 1){
	    					//check if nodes belong to right players for given move type
	    					if((firstAction[1][i] == 1 && world.map[firstAction[2][i]].belongs() == ID) || (firstAction[1][i] == 0 && world.map[firstAction[3][i]].belongs() == ID && world.map[firstAction[2][i]].belongs() == ID))
	    					{
	    						//if all are true set new max value
	    						//System.out.println("Changing lid");
			    				maxValue = firstAction[4][i];
			    				//it is a row number 
			    				maxValueRowNumber = firstAction[0][i] -1;
	    					}
	    				}
	    			}
				}
			}
			System.out.println(firstAction[0][maxValueRowNumber]+" | "+firstAction[1][maxValueRowNumber]+" | "+firstAction[2][maxValueRowNumber]+" "+firstAction[3][maxValueRowNumber]+" | "+firstAction[4][maxValueRowNumber]+" | "+firstAction[5][maxValueRowNumber]);
			
			for (int i = 0; i<5; i++){
				actionToCommit[i] = firstAction[i][maxValueRowNumber];
			}
			
			optimalUnitCount= getOptimalUnitCount(firstAction[2][maxValueRowNumber],firstAction[3][maxValueRowNumber]);
			
			//action is a move or AA we use all units
			if (firstAction[5][maxValueRowNumber] == 0 || firstAction[5][maxValueRowNumber] == 3){
				actionToCommit[5] = world.map[firstAction[2][maxValueRowNumber]].numberOfSquares();
				actionToCommit[6] = world.map[firstAction[2][maxValueRowNumber]].numberOfCircles();
				actionToCommit[7] = world.map[firstAction[2][maxValueRowNumber]].numberOfTriangles();
			}
			else if (firstAction[5][maxValueRowNumber] == 1){
				actionToCommit[5] = optimalUnitCount[0];
				actionToCommit[6] = optimalUnitCount[1];
				actionToCommit[7] = optimalUnitCount[2];
			}
			else if (firstAction[5][maxValueRowNumber] == 2){
				actionToCommit[5] = (int) (optimalUnitCount[0] + 0.4*(world.map[firstAction[2][maxValueRowNumber]].numberOfSquares() - optimalUnitCount[0]));
				actionToCommit[6] = (int) (optimalUnitCount[1] + 0.4*(world.map[firstAction[2][maxValueRowNumber]].numberOfCircles() - optimalUnitCount[1]));
				actionToCommit[7] = (int) (optimalUnitCount[2] + 0.4*(world.map[firstAction[2][maxValueRowNumber]].numberOfTriangles() - optimalUnitCount[2]));
			}
			
			System.out.println("Commiting action: "+actionToCommit[0]+" | "+actionToCommit[1]+" | "+actionToCommit[2]+" "+actionToCommit[3]+" | "+actionToCommit[4]+" | "+firstAction[5][maxValueRowNumber]+" | "+actionToCommit[5]+" "+actionToCommit[6]+" "+actionToCommit[7]);
		return actionToCommit;
		
	}
//get possible actions that can be taken from current world state - used to get "first" moves
	public int[][] getPossibleActions (int [][] actions){
		//for each node
		for(int i = 0; i <= 13; i++){
			//check if the node belongs to agent
			if(world.map[i].belongs() == ID){
				//and that node is not empty (because agent can't do anything with empty node
				//if(isNotEmpty(i)){
				if(world.map[i].numberOfSquares() >= 1 || world.map[i].numberOfCircles() >= 1 || world.map[i].numberOfTriangles() >= 1){
					//get possible targets for agent actions
					int [] targets = world.map[i].getAvailableNodes();
					//decide what action is possible and add it to the list
					//for each of available nodes
					for (int j = 0; j < 5; j++){
						//to avoid checking not existing nodes
						if(targets[j] != -1){ // -1 marks lack of connection to a node and node Id means that there is connection to that node
							actions [0][actionCounter-1] = actionCounter; //set action ID number
							//if target is a node that belongs to agent
							if (world.map[targets[j]].belongs() == ID) actions [1][actionCounter-1] = 0; //set action to move
							//if it is enemy/neutral node
							else actions [1][actionCounter-1] = 1; //set action to attack
							//set start and target
							actions [2][actionCounter-1] = i;
							actions [3][actionCounter-1] = targets[j];
							actions [4][actionCounter-1] = 0;
							actionCounter++;
						}
					}
				}
			}
		}
		return actions;
	}
	
	public void clearDijkstra(){
		 for(int i = 0; i < 90; i++){
			 for(int j = 0; j < 5 ; j++){
				 possibleActions[j][i] = 0;
				// firstAction[j][i] = 0;
			 }
			 //firstAction[5][i] = 0;
		 }
		 actionCounter = 1;
	}

	//DEBUG RANDOM VALUES
	//estimates value of selected action - higher the value - the more attractive action is to an agent
	public int estimateActionValue(){
		return value.nextInt(70);
	}
	public int [] getOptimalUnitCount(int startNode, int targetNode){
		
		int best = 0;
		int isItBetter = 0;	
		
		int [] optimalUnitCount = new int [3];
		int[] currentUnitCount = new int [3];
		
		int[] friendlyCasualties = new int [3];
		int[] currentCasualties = new int [3];
		
		int [] enemyCasualties = new int [3];
		int [] currentEnemyCasualties = new int [3];
		

		//attackers
		int attackingSquares = world.map[startNode].numberOfSquares();
		int attackingCircles = world.map[startNode].numberOfCircles();
		int attackingTriangles = world.map[startNode].numberOfTriangles();
		
		//defenders
		int defendingSquares = world.map[targetNode].numberOfSquares();
		int defendingCircles = world.map[targetNode].numberOfCircles();
		int defendingTriangles = world.map[targetNode].numberOfTriangles();
		
		for(int i = 0; i < 3; i++){	enemyCasualties[i] = 0;}
		
		optimalUnitCount [0] = attackingSquares;
		optimalUnitCount [1] = attackingCircles;
		optimalUnitCount [2] = attackingTriangles;
		
		friendlyCasualties [0] = attackingSquares;
		friendlyCasualties [1] = attackingCircles;
		friendlyCasualties [2] = attackingTriangles;
		
		
		
		//special property of a node (decide whenever node is easier or harder to take)
		int special = world.map[targetNode].getSpecial();
		
		if(defendingSquares >= 1 || defendingCircles >= 1 || defendingTriangles >= 1){
			/*
			for(int squares = 0; squares < attackingSquares; squares++)
			{
				for(int circles = 0; circles < attackingCircles; circles++)
				{
					for(int triangles = 0; triangles < attackingTriangles; triangles++)
					{*/
			for(int triangles = 0; triangles < attackingTriangles; triangles++)
			{
				for(int circles = 0; circles < attackingCircles; circles++)
				{
					for(int squares = 0; squares < attackingSquares; squares++)
					{
						currentUnitCount[0] = squares;
						currentUnitCount[1] = circles;
						currentUnitCount[2] = triangles;
						
							//computing result for each unit count option
							if(defendingSquares != 0){
								if( attackingSquares > (int) (defendingSquares*special) ) {
									attackingSquares -= (int) (defendingSquares*special);
									defendingSquares = 0;
								}
								else {
									defendingSquares -= (int) attackingSquares/special;
									attackingSquares = 0;
								}
							}
							if(defendingCircles != 0){
								if( attackingCircles > (int) (defendingCircles*special) ) {
									attackingCircles -= (int) (defendingCircles*special);
									defendingCircles = 0;
								}
								else {
									defendingCircles -= (int) attackingCircles/special;
									attackingCircles = 0;
								}
							}
							if(defendingTriangles !=0){
								if( attackingTriangles > (int) (defendingTriangles*special) ) {
									attackingTriangles -= (int) (defendingTriangles*special);
									defendingTriangles = 0;
								}
								else {
									defendingTriangles -= (int) attackingTriangles/special;
									attackingTriangles = 0;
								}
							}
							
							if(defendingTriangles !=0){
								if( attackingSquares > (int) (0.5*defendingTriangles*special) ) {
									attackingSquares -= (int) (0.5*defendingTriangles*special);
									defendingTriangles = 0;
								}
								else {
									defendingTriangles -= (int) 2*attackingSquares/special;
									attackingSquares = 0;
								}
							}
							if(defendingSquares != 0){
								if( attackingCircles > (int) (0.5*defendingSquares*special) ) {
									attackingCircles -= (int) (0.5*defendingSquares*special);
									defendingSquares = 0;
								}
								else {
									defendingSquares -= (int) 2*attackingCircles/special;
									attackingCircles = 0;
								}
							}
							if(defendingCircles != 0){
								if( attackingTriangles > (int) (0.5*defendingCircles*special) ) {
									attackingTriangles -= (int) (0.5*defendingCircles*special);
									defendingCircles = 0;
								}
								else {
									defendingCircles -= (int) 2*attackingTriangles/special;
									attackingTriangles = 0;
								}
							}
							
							if(defendingCircles !=0){
								if( attackingSquares > (int) (2*defendingCircles*special) ) {
									attackingSquares -= (int) (2*defendingCircles*special);
									defendingCircles = 0;
								}
								else {
									defendingCircles -= (int) 0.5*attackingSquares/special;
									attackingSquares = 0;
								}
							}
							if(defendingTriangles != 0){
								if( attackingCircles > (int) (2*defendingTriangles*special) ) {
									attackingCircles -= (int) (2*defendingTriangles*special);
									defendingTriangles = 0;
								}
								else {
									defendingTriangles -= (int) 0.5*attackingCircles/special;
									attackingCircles = 0;
								}
							}
							if(defendingSquares != 0){
								if( attackingTriangles > (int) (2*defendingSquares*special) ) {
									attackingTriangles -= (int) (2*defendingSquares*special);
									defendingSquares = 0;
								}
								else {
									defendingSquares -= (int) 0.5*attackingTriangles/special;
									attackingTriangles = 0;
								}
							}
							
						
						currentCasualties[0] = world.map[startNode].numberOfSquares() - attackingSquares;
						currentCasualties[1] = world.map[startNode].numberOfCircles() - attackingCircles;
						currentCasualties[2] = world.map[startNode].numberOfTriangles() - attackingTriangles;
							
						currentEnemyCasualties[0] = world.map[targetNode].numberOfSquares() - defendingSquares;
						currentEnemyCasualties[1] = world.map[targetNode].numberOfCircles() - defendingCircles; 
						currentEnemyCasualties[2] = world.map[targetNode].numberOfTriangles() - defendingTriangles; 
						
						
						if(currentUnitCount[0] > 10)isItBetter++;
						else isItBetter--;
						if(currentUnitCount[1] > 10)isItBetter++;
						else isItBetter--;
						if(currentUnitCount[2] > 10)isItBetter++;
						else isItBetter--;
						
						if(currentCasualties[0] < friendlyCasualties[0]) isItBetter++;
						else isItBetter--;
						if(currentCasualties[1] < friendlyCasualties[1]) isItBetter++;
						else isItBetter--;
						if(currentCasualties[2] < friendlyCasualties[2]) isItBetter++;
						else isItBetter--;
						
						if(currentEnemyCasualties[0] > enemyCasualties[0]) isItBetter++;
						else isItBetter--;
						if(currentEnemyCasualties[1] > enemyCasualties[1]) isItBetter++;
						else isItBetter--;
						if(currentEnemyCasualties[2] > enemyCasualties[2]) isItBetter++;
						else isItBetter--;
						if(getBattleResult(startNode,targetNode,currentUnitCount[0],currentUnitCount[1],currentUnitCount[2]) == 1)isItBetter += 6;
						else isItBetter -= 4;
							
						
						if (isItBetter > best) {
							//System.out.println(best+" | "+isItBetter);
							best = isItBetter;
							for(int i = 0 ; i< 3; i++) optimalUnitCount[i] = currentUnitCount[i];
						}
						isItBetter = 0;
					}
				}
			}
		}
		else if(optimalUnitCount[0] == 0 && optimalUnitCount[1] == 0 && optimalUnitCount[2] == 0){
				if (world.map[startNode].numberOfSquares() >=1) optimalUnitCount[0] = (int) (world.map[startNode].numberOfSquares()*0.3)+1;
				else if (world.map[startNode].numberOfCircles() >=1) optimalUnitCount[1] = (int) (world.map[startNode].numberOfCircles()*0.2)+1;
				else if (world.map[startNode].numberOfTriangles() >=1) optimalUnitCount[2] = (int) (world.map[startNode].numberOfTriangles()*0.1)+1;
		}

		//System.out.println(startNode+" "+targetNode+" | "+optimalUnitCount[0]+" "+optimalUnitCount[1]+" "+optimalUnitCount[2]);
		return optimalUnitCount;
}
//standard battle result
public int getBattleResult(int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){
	int result = 0;
	
	int defendingSquares = world.map[targetNode].numberOfSquares();
	int defendingCircles = world.map[targetNode].numberOfCircles();
	int defendingTriangles = world.map[targetNode].numberOfTriangles();
	int special = world.map[targetNode].getSpecial(); //just in case
	
	//compute battle result
			if(defendingSquares != 0){
			if( attackingSquares > (int) (defendingSquares*special) ) {
				attackingSquares -= (int) (defendingSquares*special);
				defendingSquares = 0;
			}
			else {
				defendingSquares -= (int) attackingSquares/special;
				attackingSquares = 0;
			}
		}
		if(defendingCircles != 0){
			if( attackingCircles > (int) (defendingCircles*special) ) {
				attackingCircles -= (int) (defendingCircles*special);
				defendingCircles = 0;
			}
			else {
				defendingCircles -= (int) attackingCircles/special;
				attackingCircles = 0;
			}
		}
		if(defendingTriangles !=0){
			if( attackingTriangles > (int) (defendingTriangles*special) ) {
				attackingTriangles -= (int) (defendingTriangles*special);
				defendingTriangles = 0;
			}
			else {
				defendingTriangles -= (int) attackingTriangles/special;
				attackingTriangles = 0;
			}
		}
		if(defendingTriangles !=0){
			if( attackingSquares > (int) (0.5*defendingTriangles*special) ) {
				attackingSquares -= (int) (0.5*defendingTriangles*special);
				defendingTriangles = 0;
			}
			else {
				defendingTriangles -= (int) 2*attackingSquares/special;
				attackingSquares = 0;
			}
		}
		if(defendingSquares != 0){
			if( attackingCircles > (int) (0.5*defendingSquares*special) ) {
				attackingCircles -= (int) (0.5*defendingSquares*special);
				defendingSquares = 0;
			}
			else {
				defendingSquares -= (int) 2*attackingCircles/special;
				attackingCircles = 0;
			}
		}
		if(defendingCircles != 0){
			if( attackingTriangles > (int) (0.5*defendingCircles*special) ) {
				attackingTriangles -= (int) (0.5*defendingCircles*special);
				defendingCircles = 0;
			}
			else {
				defendingCircles -= (int) 2*attackingTriangles/special;
				attackingTriangles = 0;
			}
		}
		if(defendingCircles !=0){
			if( attackingSquares > (int) (2*defendingCircles*special) ) {
				attackingSquares -= (int) (2*defendingCircles*special);
				defendingCircles = 0;
			}
			else {
				defendingCircles -= (int) 0.5*attackingSquares/special;
				attackingSquares = 0;
			}
		}
		if(defendingTriangles != 0){
			if( attackingCircles > (int) (2*defendingTriangles*special) ) {
				attackingCircles -= (int) (2*defendingTriangles*special);
				defendingTriangles = 0;
			}
			else {
				defendingTriangles -= (int) 0.5*attackingCircles/special;
				attackingCircles = 0;
			}
		}
		if(defendingSquares != 0){
			if( attackingTriangles > (int) (2*defendingSquares*special) ) {
				attackingTriangles -= (int) (2*defendingSquares*special);
				defendingSquares = 0;
			}
			else {
				defendingSquares -= (int) 0.5*attackingTriangles/special;
				attackingTriangles = 0;
			}
		}
		
	//draw
	if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0 & attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0) {result = 0;}
	//attacker wins
	else if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0) {
		result = 1;
	}
	//attacker loses
	else if (attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0){result = 2;}
	
	return result;
}
//battle result with selected unit counts
public int getBattleResult(int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles,int defendingSquares, int defendingCircles, int defendingTriangles){
	
	int result = 0;
	int special = world.map[targetNode].getSpecial(); //just in case
	
	//compute battle result
			if(defendingSquares != 0){
			if( attackingSquares > (int) (defendingSquares*special) ) {
				attackingSquares -= (int) (defendingSquares*special);
				defendingSquares = 0;
			}
			else {
				defendingSquares -= (int) attackingSquares/special;
				attackingSquares = 0;
			}
		}
		if(defendingCircles != 0){
			if( attackingCircles > (int) (defendingCircles*special) ) {
				attackingCircles -= (int) (defendingCircles*special);
				defendingCircles = 0;
			}
			else {
				defendingCircles -= (int) attackingCircles/special;
				attackingCircles = 0;
			}
		}
		if(defendingTriangles !=0){
			if( attackingTriangles > (int) (defendingTriangles*special) ) {
				attackingTriangles -= (int) (defendingTriangles*special);
				defendingTriangles = 0;
			}
			else {
				defendingTriangles -= (int) attackingTriangles/special;
				attackingTriangles = 0;
			}
		}
		if(defendingTriangles !=0){
			if( attackingSquares > (int) (0.5*defendingTriangles*special) ) {
				attackingSquares -= (int) (0.5*defendingTriangles*special);
				defendingTriangles = 0;
			}
			else {
				defendingTriangles -= (int) 2*attackingSquares/special;
				attackingSquares = 0;
			}
		}
		if(defendingSquares != 0){
			if( attackingCircles > (int) (0.5*defendingSquares*special) ) {
				attackingCircles -= (int) (0.5*defendingSquares*special);
				defendingSquares = 0;
			}
			else {
				defendingSquares -= (int) 2*attackingCircles/special;
				attackingCircles = 0;
			}
		}
		if(defendingCircles != 0){
			if( attackingTriangles > (int) (0.5*defendingCircles*special) ) {
				attackingTriangles -= (int) (0.5*defendingCircles*special);
				defendingCircles = 0;
			}
			else {
				defendingCircles -= (int) 2*attackingTriangles/special;
				attackingTriangles = 0;
			}
		}
		if(defendingCircles !=0){
			if( attackingSquares > (int) (2*defendingCircles*special) ) {
				attackingSquares -= (int) (2*defendingCircles*special);
				defendingCircles = 0;
			}
			else {
				defendingCircles -= (int) 0.5*attackingSquares/special;
				attackingSquares = 0;
			}
		}
		if(defendingTriangles != 0){
			if( attackingCircles > (int) (2*defendingTriangles*special) ) {
				attackingCircles -= (int) (2*defendingTriangles*special);
				defendingTriangles = 0;
			}
			else {
				defendingTriangles -= (int) 0.5*attackingCircles/special;
				attackingCircles = 0;
			}
		}
		if(defendingSquares != 0){
			if( attackingTriangles > (int) (2*defendingSquares*special) ) {
				attackingTriangles -= (int) (2*defendingSquares*special);
				defendingSquares = 0;
			}
			else {
				defendingSquares -= (int) 0.5*attackingTriangles/special;
				attackingTriangles = 0;
			}
		}
		
	//draw
	if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0 & attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0) {result = 0;}
	//attacker wins
	else if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0) {
		result = 1;
	}
	//attacker loses
	else if (attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0){result = 2;}
	
	return result;
}
}


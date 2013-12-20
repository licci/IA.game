import java.util.Random;

//CLASS___________________________________________________________________________________________________________________________________________________________________
public class LaughingManSearch {
	
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
	
	//agent's copy of game myWorld
	public copyOfGraph myWorld = new copyOfGraph();
	
	public copyOfGraph restore = new copyOfGraph();
	
	//agents id 
	int ID = 0;
	int enemyHQ = 0;
	int enemyID = 0;
	int [] action;
	
	//sets how many moves ahead will be checked 
	int actionCount = 0;
	
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
	
	
	int actionCounter = 1;
	
	//constructor
	//create new agent with given ID and get current myWorld state for him
	public LaughingManSearch(int player){
		ID = player;
		if(ID == 1) { enemyHQ = 13; enemyID = 2;}
		else if(ID == 2) { enemyHQ = 0; enemyID = 1;}
		
		myWorld.initialization();
	}
	
	public void performAction(){
		getWorldState();
		actionCounter = 1;
		//check moves
		action = dijkstra();
		if(action[1]==0){
			if( main.action.move(ID,action[2],action[3],action[5],action[6],action[7]) ); //System.out.println(ID+" Move succesful");
			else {
				System.out.println(ID+" Move NOT succesful!!!!!!!!!!!!!!!!!!");
				main.pause(5000);
			}
		}
		else if(action[1]==1){
			if( main.action.attack(ID,action[2],action[3],action[5],action[6],action[7]) );//System.out.println(ID+" Attack succesful");
			else {
				System.out.println(ID+" Attack NOT succesful!!!!!!!!!!!!!!!!!!");
				main.pause(5000);
			}
		}
		else if(action[1] == -1) ;//System.out.println("No moves to do - waiting!");
		actionCounter = 1;
		for (int i = 0; i < 90; i++){
			firstAction [0][i] = 0;
			firstAction [1][i] = 0;
			firstAction [2][i] = 0;
			firstAction [3][i] = 0;
			firstAction [4][i] = 0;
			firstAction [5][i] = 0;
		}
	}
	

//myWorld STATE AND ESTIMATES______________________________________________________________________________________________________________________________________________
	
	//before each move myWorld state will be updated
	void getWorldState()
	{
		
		//get myWorld state
		//this data has some values that should not be accessible by an agent
		//so for each node that is not in view range (hidden by Fog of War) prediction about owner and unit count is made
		//no assumption is made about road states - agent gets current state of all roads 
		for(int i = 0; i < 14; i++){
			myWorld.map[i].setOwner(graph.map[i].belongs());
			myWorld.map[i].setNumberOfSquares(graph.map[i].numberOfSquares());
			myWorld.map[i].setNumberOfCircles(graph.map[i].numberOfCircles());
			myWorld.map[i].setNumberOfTriangles(graph.map[i].numberOfTriangles());
			myWorld.map[i].setAvailableNodes(graph.map[i].getAvailableNodes());
		}
		
		int [] visibleNodes = new int [14];
		for(int i = 0; i < 14; i++){visibleNodes[i] = -1;} //-1 not in visual range / 1 - in visual range
		
		//create an array that stores whenever the node is in visual range of an agent
		//for each node
		for(int i = 0; i < 14; i++){
			//check if the node belongs to agent
			if(myWorld.map[i].belongs() == ID){
				visibleNodes[i] = 1;
				//get possible targets for agent actions
				int [] nodes = myWorld.map[i].getAvailableNodes();
				for (int j = 0; j < 5; j++){
					if(nodes[j] != -1){
						visibleNodes[nodes[j]] = 1;
					}
				}
			}
		}
		
		for(int i = 0; i < 14; i++){
			if(visibleNodes[i] < 1){
				estimateNode(i);
			}
		}
		
		//for(int i = 0; i < 14; i++){//System.out.println("Node "+i+" units "+myWorld.map[i].numberOfSquares()+" "+myWorld.map[i].numberOfCircles()+" "+myWorld.map[i].numberOfTriangles());}
	}
	
	void estimateNode(int nodeId){
		if(nodeId == enemyHQ){
			myWorld.map[nodeId].setOwner(enemyID);
			myWorld.map[nodeId].setNumberOfSquares(15+main.turnCount);
			myWorld.map[nodeId].setNumberOfCircles(15);
			myWorld.map[nodeId].setNumberOfTriangles(15);
		}
		
		//before turn 4 all nodes outside vision range are assumed to be neutral
		//and have default unit count + standard growth rate
		else if (main.turnCount<4){
			if(nodeId == enemyHQ){
				myWorld.map[nodeId].setOwner(enemyID);
				myWorld.map[nodeId].setNumberOfSquares(15+main.turnCount);
				myWorld.map[nodeId].setNumberOfCircles(15);
				myWorld.map[nodeId].setNumberOfTriangles(15);
			}
			else{
				myWorld.map[nodeId].setOwner(0); 
				//square production node
				if(myWorld.map[nodeId].creationType() == 1){
					myWorld.map[nodeId].setNumberOfSquares(15+main.turnCount);
					myWorld.map[nodeId].setNumberOfCircles(0);
					myWorld.map[nodeId].setNumberOfTriangles(0);
				}
				//circle production node
				else if(myWorld.map[nodeId].creationType() == 2){
					myWorld.map[nodeId].setNumberOfSquares(0);
					myWorld.map[nodeId].setNumberOfCircles(15+(main.turnCount/2));
					myWorld.map[nodeId].setNumberOfTriangles(0);
				}
				//triangle production node
				else {
					myWorld.map[nodeId].setNumberOfSquares(0);
					myWorld.map[nodeId].setNumberOfCircles(0);
					myWorld.map[nodeId].setNumberOfTriangles(15+(main.turnCount/3));
				}
			}
		}
		//after turn 4 all nodes outside vision range are assumed to be enemy nodes
		//enemy HQ is assumed to have low amount of units (they were sent to take neutral nodes)
		//nodes closest to enemy HQ are assumed to have low unit counts (because units will be sent closer to the front line)
		//second column from enemy HQ is assumed to have highest unit counts
		else if (main.turnCount>4){
			//enemy HQ has only units acquired from standard growth
			if(nodeId == enemyHQ){
				myWorld.map[nodeId].setOwner(enemyID);
				myWorld.map[nodeId].setNumberOfSquares(main.turnCount);
				myWorld.map[nodeId].setNumberOfCircles(0);
				myWorld.map[nodeId].setNumberOfTriangles(0);
			}
			//other nodes have a combination of units in them - based on growth rate and unit best suited to take them
			else{
				myWorld.map[nodeId].setOwner(enemyID); 
				//square production node
				//squares are vulnerable to circles so the node has only production squares and some circles
				if(myWorld.map[nodeId].creationType() == 1){
					myWorld.map[nodeId].setNumberOfSquares(main.turnCount);
					myWorld.map[nodeId].setNumberOfCircles(main.turnCount/2);
					myWorld.map[nodeId].setNumberOfTriangles(0);
				}
				//circle production node
				//circles are vulnerable to triangles so the node has only production circles and some triangles
				else if(myWorld.map[nodeId].creationType() == 2){
					myWorld.map[nodeId].setNumberOfSquares(0);
					myWorld.map[nodeId].setNumberOfCircles(main.turnCount/2);
					myWorld.map[nodeId].setNumberOfTriangles(main.turnCount/3);
				}
				//triangle production node
				//triangles are vulnerable to squares so the node has only production triangles and some squares
				else {
					myWorld.map[nodeId].setNumberOfSquares(main.turnCount);
					myWorld.map[nodeId].setNumberOfCircles(0);
					myWorld.map[nodeId].setNumberOfTriangles(main.turnCount/3);
				}
			}
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
	    		firstAction[4][i] = estimateActionValue(firstAction[1][i],firstAction[2][i],firstAction[3][i]);
	    		firstAction[5][i] = attackType;
	    		attackType = 0;
	    		
	    		//and commit it in test environment
	    		if(firstAction[1][i] == 0) {
	    			testMove(firstAction[2][i],firstAction[3][i],myWorld.map[firstAction[2][i]].numberOfSquares(),myWorld.map[firstAction[2][i]].numberOfCircles(),myWorld.map[firstAction[2][i]].numberOfTriangles());
	    		}
	    		else if (firstAction[1][i] == 1){
	    			optimalUnitCount= getOptimalUnitCount(firstAction[2][i],firstAction[3][i]);
	    			testAttack(firstAction[2][i],firstAction[3][i],optimalUnitCount[0],optimalUnitCount[1],optimalUnitCount[2]);
	    		}
	    		
	    		//after that check next 4 best actions
	    		for (int counter = 0; counter < actionCount ; counter++)
	    		{
		    		clearDijkstra();
		    		//get all possible actions that are available after fist action was committed
		    		possibleActions = getPossibleActions(possibleActions);
		    		for(int j = 0; j < 90; j++){
		    	    	if(possibleActions[0][j] > 0){
		    	    		//estimate value of each action
		    	    		//possibleActions[4][j] = estimateActionValue();
		    	    		possibleActions[4][j] = ((int)estimateActionValue(firstAction[1][i],firstAction[2][i],firstAction[3][i])/(actionCount+1));
		    	    	}
		    		}
		    		
		    		//select highest value action 
		    		int maxValue = possibleActions[4][0];
		    		int maxValueRowNumber = possibleActions[0][0];
		    		for(int j = 0; j < 90; j++){
		    			if(possibleActions[0][j] > 0){
			    			if (maxValue < possibleActions[4][j]) {
			    				maxValue = possibleActions[4][j];
			    				maxValueRowNumber = j;
			    			}
		    			}
		    		}
		    		
		    		//add highest action value to first action value
		    		firstAction[4][i] += maxValue;
		    		
		    		//commit highest value action
		    		if(possibleActions[2][maxValueRowNumber] == 0) {
		    			testMove(possibleActions[2][maxValueRowNumber],possibleActions[3][maxValueRowNumber],myWorld.map[possibleActions[2][maxValueRowNumber]].numberOfSquares(),myWorld.map[possibleActions[2][maxValueRowNumber]].numberOfCircles(),myWorld.map[possibleActions[2][maxValueRowNumber]].numberOfTriangles());
		    		}
		    		else if (possibleActions[2][maxValueRowNumber] == 1){
		    			testAttack(possibleActions[2][maxValueRowNumber],possibleActions[3][maxValueRowNumber],myWorld.map[possibleActions[2][maxValueRowNumber]].numberOfSquares(),myWorld.map[possibleActions[2][maxValueRowNumber]].numberOfCircles(),myWorld.map[possibleActions[2][maxValueRowNumber]].numberOfTriangles());
		    		}
	    		}
		    		getWorldState();
	    		}
		}
		
		for(int i = 0; i < 90; i++){ 
			if(firstAction[0][i] > 0){ 
				System.out.println(i+" l|l "+firstAction[0][i]+" | "+firstAction[1][i]+" | "+firstAction[2][i]+" "+firstAction[3][i]+" | "+firstAction[4][i]+" | "+firstAction[5][i]);
			}
		}
		
		//select action to commit
			int maxValue = -10000;
			int maxValueRowNumber = 1;
			
			////System.out.println("1 "+maxValue+" | "+maxValueRowNumber);
			
			for(int i = 0; i < 90; i++){ 
				//check if action exists
				if(firstAction[0][i] > 0){ 
					//check if max value is higher
	    			//if (maxValue < firstAction[4][i]) {
					////System.out.println(firstAction[4][i]+" is bigger then "+maxValue+"?");
					if (firstAction[4][i] > maxValue) {
						////System.out.println("YES");
	    				//check if there are units in start node
	    				if(myWorld.map[firstAction[2][i]].numberOfSquares() >= 1 || myWorld.map[firstAction[2][i]].numberOfCircles() >= 1 || myWorld.map[firstAction[2][i]].numberOfTriangles() >= 1){
	    					//check if nodes belong to right players for given move type
	    					if((firstAction[1][i] == 1 && myWorld.map[firstAction[2][i]].belongs() == ID) || (firstAction[1][i] == 0 && myWorld.map[firstAction[3][i]].belongs() == ID && myWorld.map[firstAction[2][i]].belongs() == ID))
	    					{
	    						//if all are true set new max value
	    						////System.out.println("Changing lid");
			    				maxValue = firstAction[4][i];
			    				//it is a row number 
			    				maxValueRowNumber = firstAction[0][i] -1;
			    				////System.out.println("2 "+maxValue+" | "+maxValueRowNumber);
	    					}
	    				}
	    			}
					//else //System.out.println("NO");
				}
			}
			////System.out.println("3 "+maxValue+" | "+maxValueRowNumber);
			////System.out.println("Best action "+firstAction[0][maxValueRowNumber]+" | "+firstAction[1][maxValueRowNumber]+" | "+firstAction[2][maxValueRowNumber]+" "+firstAction[3][maxValueRowNumber]+" | "+firstAction[4][maxValueRowNumber]+" | "+firstAction[5][maxValueRowNumber]);
			
			
			for (int i = 0; i<5; i++){
				actionToCommit[i] = firstAction[i][maxValueRowNumber];
			}
			
			optimalUnitCount = getOptimalUnitCount(firstAction[2][maxValueRowNumber],firstAction[3][maxValueRowNumber]);
			
			//action is a move or AA we use all units
			if (firstAction[5][maxValueRowNumber] == 0 || firstAction[5][maxValueRowNumber] == 3){
				actionToCommit[5] = myWorld.map[firstAction[2][maxValueRowNumber]].numberOfSquares();
				actionToCommit[6] = myWorld.map[firstAction[2][maxValueRowNumber]].numberOfCircles();
				actionToCommit[7] = myWorld.map[firstAction[2][maxValueRowNumber]].numberOfTriangles();
			}
			else if (firstAction[5][maxValueRowNumber] == 1){
				actionToCommit[5] = optimalUnitCount[0];
				actionToCommit[6] = optimalUnitCount[1];
				actionToCommit[7] = optimalUnitCount[2];
			}
			else if (firstAction[5][maxValueRowNumber] == 2){
				actionToCommit[5] = (int) (optimalUnitCount[0] + 0.4*(myWorld.map[firstAction[2][maxValueRowNumber]].numberOfSquares() - optimalUnitCount[0]));
				actionToCommit[6] = (int) (optimalUnitCount[1] + 0.4*(myWorld.map[firstAction[2][maxValueRowNumber]].numberOfCircles() - optimalUnitCount[1]));
				actionToCommit[7] = (int) (optimalUnitCount[2] + 0.4*(myWorld.map[firstAction[2][maxValueRowNumber]].numberOfTriangles() - optimalUnitCount[2]));
			}
			
			
			/* send all
				actionToCommit[5] = myWorld.map[firstAction[2][maxValueRowNumber]].numberOfSquares();
				actionToCommit[6] = myWorld.map[firstAction[2][maxValueRowNumber]].numberOfCircles();
				actionToCommit[7] = myWorld.map[firstAction[2][maxValueRowNumber]].numberOfTriangles();
			*/
			System.out.println("Commiting action: "+actionToCommit[0]+" | "+actionToCommit[1]+" | "+actionToCommit[2]+" "+actionToCommit[3]+" | "+actionToCommit[4]+" | "+firstAction[5][maxValueRowNumber]+" | "+actionToCommit[5]+" "+actionToCommit[6]+" "+actionToCommit[7]);
		//}
		//else actionToCommit[1] = -1;
		return actionToCommit;
		
	}
//get possible actions that can be taken from current myWorld state - used to get "first" moves
	public int[][] getPossibleActions (int [][] actions){
		//for each node
		for(int i = 0; i <= 13; i++){
			//check if the node belongs to agent
			if(myWorld.map[i].belongs() == ID){
				//and that node is not empty (because agent can't do anything with empty node
				//if(isNotEmpty(i)){
				if(myWorld.map[i].numberOfSquares() >= 1 || myWorld.map[i].numberOfCircles() >= 1 || myWorld.map[i].numberOfTriangles() >= 1){
					//get possible targets for agent actions
					int [] targets = myWorld.map[i].getAvailableNodes();
					//decide what action is possible and add it to the list
					//for each of available nodes
					for (int j = 0; j < 5; j++){
						//to avoid checking not existing nodes
						if(targets[j] != -1){ // -1 marks lack of connection to a node and node Id means that there is connection to that node
							actions [0][actionCounter-1] = actionCounter; //set action ID number
							//if target is a node that belongs to agent
							int owner = myWorld.map[targets[j]].belongs();
							if (owner == ID) {actions [1][actionCounter-1] = 0;} //set action to move
							//if it is enemy/neutral node
							else if (owner != ID) {actions [1][actionCounter-1] = 1;}//set action to attack
							//set start and target
							actions [2][actionCounter-1] = i;
							actions [3][actionCounter-1] = targets[j];
							actions [4][actionCounter-1] = 0;
							//actions [5][actionCounter-1] = 0;
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
	
	public int estimateActionValue(int type, int startNode, int targetNode ){
		//how attractive action is for an agent
		int actionValue = 0;
		
		int counter = 0;
		
		//distance from HQ (for each node in move)
		int startDistance = distanceFromBase(startNode);
		int targetDistance = distanceFromBase(targetNode);
		////System.out.println(startDistance+"  "+targetDistance);
		
		//number of owned nodes (whole and of each type)
		int myNodesNumber [] = howManyNodesIHave();
		
		//GENRAL RULES
		//If unit count is very low do not take action from this node
		if(myWorld.map[startNode].numberOfSquares() <= 1 && myWorld.map[startNode].numberOfCircles() <= 1 && myWorld.map[startNode].numberOfTriangles() <= 1){
			actionValue -= 50;
		}
		
		
		//MOVE ESTIMATE - if action type is MOVE
		if(type == 0){
			attackType = 0;
			counter = 0;
			
			//try to move bigger unit groups (if there is no unit counting more than 15 - don't move units)
			if(myWorld.map[startNode].numberOfSquares() <= 15 ) counter++;
			if(myWorld.map[startNode].numberOfCircles() <= 15) counter++;
			if(myWorld.map[startNode].numberOfTriangles() <= 15) counter++;
			if(counter > 2)actionValue -= 20; 
			
			counter = 0;
			
			//if you have units near base move them to front line
			if (startDistance == 0) actionValue+= 5;
			if (startDistance <= 1) actionValue+= 3;
			if (startDistance <= 2) actionValue+= 1;
			
			
			
			//retreating with no reason is not encouraged
			if (targetDistance >= 2) actionValue++;
			else if (targetDistance < 2) actionValue -= 10;
			
			//are enemy nodes adjacent to startNode?
			if(!isEnemyClose(startNode))actionValue+=5; //if not its OK
			else{
				//if the fight will result in loss
				int [] neighbours = myWorld.map[startNode].getAvailableNodes();
				for (int i = 0; i < 5; i++){
					if (neighbours[i]>-1){
						if (myWorld.map[neighbours[i]].belongs() == enemyID) {
							//how many enemy nodes are there
							counter++;
							//if battle will be lost or fight result will be a draw
							if (getBattleResult(neighbours[i],startNode,myWorld.map[neighbours[i]].numberOfSquares(),myWorld.map[neighbours[i]].numberOfCircles(),myWorld.map[neighbours[i]].numberOfTriangles()) != 2){
								//retreat your units
								actionValue += 5; //add 5 to move order for each bigger enemy
								
								//do not retreat if node is close to HQ
								if (startDistance <= 1) actionValue -= 10;
								//do not remove units from endangered HQ
								if (startDistance == 0) actionValue -= 20;
							}
							else actionValue -= 10;
						}
					}
				}
			}
			if (counter > 2) actionValue -= 10;
			counter = 0;
			
			//are enemy nodes close to targetNode?
			//if yes
			if(isEnemyClose(targetNode)){
				
				int [] neighbours = myWorld.map[targetNode].getAvailableNodes();
				for (int i = 0; i < 5; i++){
					if (neighbours[i]>-1){
						//if yes then
						if (myWorld.map[neighbours[i]].belongs() == enemyID) {
							counter++; //how many enemies are close
							
							//if node may be lost
							if (getBattleResult(neighbours[i],targetNode,myWorld.map[neighbours[i]].numberOfSquares(),myWorld.map[neighbours[i]].numberOfCircles(),myWorld.map[neighbours[i]].numberOfTriangles()) != 2){
								
								// will reinforced node be lost?
								int [] reinforcements = new int [3];
								reinforcements [0] = myWorld.map[targetNode].numberOfSquares() + myWorld.map[startNode].numberOfSquares();
								reinforcements [1] = myWorld.map[targetNode].numberOfCircles() + myWorld.map[startNode].numberOfCircles();
								reinforcements [2] = myWorld.map[targetNode].numberOfTriangles() + myWorld.map[startNode].numberOfTriangles();
								//battle result in case of attack after reinforcing
								if(getBattleResult(neighbours[i],targetNode,myWorld.map[neighbours[i]].numberOfSquares(),myWorld.map[neighbours[i]].numberOfCircles(),myWorld.map[neighbours[i]].numberOfTriangles(),reinforcements[0],reinforcements[1],reinforcements[2]) != 2)
								{
									//node will not be held even with help
									actionValue -= 5;
								}
								else{
									//reinforce node
									actionValue += 5;
									//especially if it is node close to HQ
									if (targetDistance <= 2) actionValue += 10; //prevent breakthroughs
									//o
									if (targetDistance == 0) actionValue += 20; //help HQ in danger!
									
									//if(counter > 2)actionValue -= 20; //do not send reinforcements to heavily endangered nodes if they are not close to HQ
								}
							}
							//if not
							else actionValue += 2; //concentrate units before attack
						}
					}
				}
			}
			

			
			counter = 0;
		}//END MOVE ESTIMATE
		
		
		else if (type == 1){//ATTACK ESTIMATE - if action type is ATTACK
			counter = 0;
			
			//0 - CAUTIOUS ATTACK - use optimal unit count
			//1 - STRONG ATTACK - use optimal unit count + 40% of remaining units
			//2 -  ALL OUT ATTACK - use all forces
			int CA = 0; 
			int SA = 0; 
			int AA = 0; 
			
			//if attack is to be done with very small unit count and will result in loss do not attack
			if(myWorld.map[startNode].numberOfSquares() < 10 && myWorld.map[startNode].numberOfSquares() < 10 && myWorld.map[startNode].numberOfTriangles() < 10){
				if (getBattleResult(startNode,targetNode,myWorld.map[startNode].numberOfSquares(),myWorld.map[startNode].numberOfSquares(),myWorld.map[startNode].numberOfTriangles()) != 1){
					actionValue-=30;
				}
			}
			
			
			//take nodes neighboring HQ
			if (targetDistance < 2) {
				actionValue++;
				SA += 2;
			}
			
			//take nodes close to HQ
			if (targetDistance <= 2) {
				actionValue++;
				SA++;
			}
			//strong attack recommended in order to create strong foothold
			
			//take nodes further from HQ, but be cautious
			if (targetDistance > 3) {
				actionValue++;
				CA += 10;
			}
			
			//take six node closest to HQ and try to hold them
			if(myWorld.map[targetNode].get_id() > 0 && myWorld.map[targetNode].get_id() < 7) actionValue += 4;
			//establish foothold
			if(myWorld.map[targetNode].get_id() > 0 && myWorld.map[targetNode].get_id() < 4){
				actionValue += 8;
				SA += 2;
			}else {
				CA += 2;
			}
			
			//try to control at least half of total node number
			if(myNodesNumber[0] < 7) actionValue += 2;
			//if you control at least half of all nodes try attacking forward
			if(myNodesNumber[0] >= 7 && targetDistance > 2) {
				actionValue ++;
				//but be cautious
				CA += 5;
			}
			
			//take nodes closest to HQ
			if(targetDistance < 2) actionValue += 10;
			
			//try to have at least 1 node of each type
			if(myNodesNumber[1] <= 1 && myWorld.map[targetNode].creationType() == 1) actionValue += 2;
			if(myNodesNumber[2] <= 1 && myWorld.map[targetNode].creationType() == 2) actionValue += 2;
			if(myNodesNumber[3] <= 1 && myWorld.map[targetNode].creationType() == 3) actionValue += 2;
			
			//check how many neighbors of startNode are enemies
			int [] neighbours = myWorld.map[startNode].getAvailableNodes();
			for (int i = 0; i < 5; i++){
				if (neighbours[i]>-1){
					//if neighbor is an enemy
					if (myWorld.map[neighbours[i]].belongs() == enemyID) {
						
						//if enemy neighbor is not target
						if(neighbours[i] != targetNode){
							//if enemy attack on start node will not be a failure
							if (getBattleResult(neighbours[i],startNode,myWorld.map[neighbours[i]].numberOfSquares(),myWorld.map[neighbours[i]].numberOfCircles(),myWorld.map[neighbours[i]].numberOfTriangles()) != 2){
								counter++;
								//each extra enemy node close to startNode lowers possibility of using all out attack (leaving node empty)
								AA -= 2;
								//defend nodes close to HQ at all cost
								if(startDistance < 2) actionValue -= 10;
								else actionValue ++;
							}
							else {
								int [] units = getOptimalUnitCount(startNode,targetNode);
								units[0] = myWorld.map[startNode].numberOfSquares() - units[0];
								units[1] = myWorld.map[startNode].numberOfCircles() - units[1];
								units[2] = myWorld.map[startNode].numberOfTriangles() - units[2];
								//if enemy can take start node after cautious attack
								if (getBattleResult(neighbours[i],startNode,myWorld.map[neighbours[i]].numberOfSquares(),myWorld.map[neighbours[i]].numberOfCircles(),myWorld.map[neighbours[i]].numberOfTriangles(),units[0],units[1],units[2]) != 2){
									//defend nodes close to HQ at all cost
									if(startDistance < 2) actionValue -= 10;
									else actionValue ++;
								}
								else {actionValue += 2; CA += 5;}
							}
						}
					}
				}
			}
			if(counter > 2){actionValue -= 6;}
			counter = 0;
			
			//if targetNode is enemy HQ
			if (targetNode == enemyHQ){
				//and attack is not failure 
				if(getBattleResult(startNode,targetNode,myWorld.map[startNode].numberOfSquares(),myWorld.map[startNode].numberOfCircles(),myWorld.map[startNode].numberOfTriangles()) != 2 ){
					//attack
					actionValue += 100;
					AA += 100;
				}
				//else, still attack
				else {actionValue += 10; CA += 5;}
			}
			
			//check if attack will succeed if all units will be used
			//purpose of this is to stop agent from taking with stupidly small unit counts
			//so even if such attack might be successful it will not affect action value
			//but if a winning is impossible in such situation it will lower the action value - attacks where only gain is inflicting casualties are less favorable then ones resulting in gaining a node
			if(getBattleResult(startNode,targetNode,myWorld.map[startNode].numberOfSquares(),myWorld.map[startNode].numberOfCircles(),myWorld.map[startNode].numberOfTriangles()) != 1){
				actionValue -= 20;
			}

			
			int [] units = getOptimalUnitCount(startNode,targetNode);
			//check if cautions attack will succeed
			if(getBattleResult(startNode,targetNode,units[0],units[1],units[2]) == 1){
				actionValue += 5;
				CA += 5;
			}
			//check if strong attack will succeed
			else {
				units[0] = (int) (units[0] + ((myWorld.map[startNode].numberOfSquares() - units[0])*0.4));
				units[1] = (int) (units[0] + ((myWorld.map[startNode].numberOfCircles() - units[1])*0.4));
				units[2] = (int) (units[0] + ((myWorld.map[startNode].numberOfTriangles() - units[2])*0.4));
				
				if(getBattleResult(startNode,targetNode,units[0],units[1],units[2]) == 1);
				{
					//if yes it still does not mean strong attack will happen - it also depends on distance from HQ
					if(startDistance < 2) {
						actionValue += 6;
						SA += 5;
					}
					if(startDistance > 3) {
						actionValue += 2;
						SA += 2;
					}
				}
				//if battle will not be won with SA
				if (startDistance > 3) actionValue -=10;
			}
			
			units = getOptimalUnitCount(startNode,targetNode);//optimal unit count
			int [] unitsSA =  getOptimalUnitCount(startNode,targetNode);//SA unit count
			unitsSA[0] = (int) (unitsSA[0] + ((myWorld.map[startNode].numberOfSquares() - unitsSA[0])*0.4));
			unitsSA[1] = (int) (unitsSA[0] + ((myWorld.map[startNode].numberOfCircles() - unitsSA[1])*0.4));
			unitsSA[2] = (int) (unitsSA[0] + ((myWorld.map[startNode].numberOfTriangles() - unitsSA[2])*0.4));
			
			//if CA & SA give no good results try AA
			//note that we don't like attacking with all of our units
			if(getBattleResult(startNode,targetNode,units[0],units[1],units[2]) != 1 && getBattleResult(startNode,targetNode,unitsSA[0],unitsSA[1],unitsSA[2]) != 1){
				if(getBattleResult(startNode,targetNode,myWorld.map[startNode].numberOfSquares(),myWorld.map[startNode].numberOfCircles(),myWorld.map[startNode].numberOfTriangles()) == 1){
					if(startDistance < 2) {
						actionValue += 6;
						AA += 10;
					}
					if(startDistance > 3) {
						actionValue ++;
						AA ++;
					}
				}
			}
			//if CA is at least as big ass both other attacks use CA
			if(CA >= SA && CA >= AA) attackType = 1;
			//if SA is bigger then CA and at least as big as AA use SA
			if(SA > CA && SA >= AA) attackType = 2;
			//if AA is bigger than CA and SA use AA
			if(AA > CA && AA >SA) attackType = 3;

		}//END ATTACK ESTIMATE
		
		return actionValue;
	}
	
	public int[] howManyNodesIHave(){
		int [] numberOfNodes = new int [4];
		
		for(int i = 0; i <= 13; i++){
			if(myWorld.map[i].belongs() == ID){
				numberOfNodes[0]++;
				if(myWorld.map[i].creationType() == 1){numberOfNodes[1]++;}
				else if(myWorld.map[i].creationType() == 2){numberOfNodes[2]++;}
				else if(myWorld.map[i].creationType() == 3){numberOfNodes[3]++;}
			}
		}	
		return numberOfNodes;
	}
	public int distanceFromBase(int node){
		if (node == 0) return 0;
		else if (node > 0 && node < 4) return 1;  //1st line of nodes
		else if (node > 3 && node < 7) return 2;  //2nd
		else if (node > 6 && node <10) return 3;  //3rd
		else if (node > 9 && node < 13) return 4; //4th
		else return 5; 						   	  //enemy HQ
	}
	
	public boolean isEnemyClose(int node){
		int [] neighbours = myWorld.map[node].getAvailableNodes();
		for (int i = 0; i < 5; i++){
			if (neighbours[i]>-1){
				if (myWorld.map[neighbours[i]].belongs() == enemyID) return true;
			}
		}
		return false; 
	}
	
	//TEST ACTIONS___________________________________________________________________________________________________________________________________________________________
		//moving units on myWorld copy - in order to get possible moves resulting from such action
		public void testMove(int startNode, int targetNode, int movingSquares, int movingCircles, int movingTriangles){
				////System.out.println("Moving!");
				//removing moving units from startNode
				myWorld.map[startNode].setNumberOfSquares(myWorld.map[startNode].numberOfSquares()-movingSquares);
				myWorld.map[startNode].setNumberOfCircles(myWorld.map[startNode].numberOfCircles()-movingCircles);
				myWorld.map[startNode].setNumberOfTriangles(myWorld.map[startNode].numberOfTriangles()-movingTriangles);
				
				//adding moving units to targetNode
				myWorld.map[targetNode].setNumberOfSquares(myWorld.map[targetNode].numberOfSquares()+movingSquares);
				myWorld.map[targetNode].setNumberOfCircles(myWorld.map[targetNode].numberOfCircles()+movingCircles);
				myWorld.map[targetNode].setNumberOfTriangles(myWorld.map[targetNode].numberOfTriangles()+movingTriangles);
			
			////System.out.println("Player "+playerId+" moved units from "+startNode+" to "+targetNode+"!\n Units remaining in "+startNode+":");
			////System.out.println("Squares: "+graph.map[startNode].numberOfSquares()+"\n Circles: "+graph.map[startNode].numberOfCircles()+"\n Triangles: "+graph.map[startNode].numberOfTriangles());
			////System.out.println("Unit count in "+targetNode+": ");
			////System.out.println("Squares: "+graph.map[targetNode].numberOfSquares()+"\n Circles: "+graph.map[targetNode].numberOfCircles()+"\n Triangles: "+graph.map[targetNode].numberOfTriangles());
		}
	//attacking node on myWorld copy in order to compute result of the fight and get possible actions resulting from such action
		public boolean testAttack(int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){
			
				////System.out.println("Attacking!");
				//removing attacking units from start node (current value - units send to attack)
				myWorld.map[startNode].setNumberOfSquares(myWorld.map[startNode].numberOfSquares()-attackingSquares);
				myWorld.map[startNode].setNumberOfCircles(myWorld.map[startNode].numberOfCircles()-attackingCircles);
				myWorld.map[startNode].setNumberOfTriangles(myWorld.map[startNode].numberOfTriangles()-attackingTriangles);
				
				//defenders
				int defendingSquares = myWorld.map[targetNode].numberOfSquares();
				int defendingCircles = myWorld.map[targetNode].numberOfCircles();
				int defendingTriangles = myWorld.map[targetNode].numberOfTriangles();
				
				//special property of a node (decide whenever node is easier or harder to take)
				int special = myWorld.map[targetNode].getSpecial();
				
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				//1st stage of fight
				//units fight with units of the same type - inflicting normal casualties 
					if(defendingSquares != 0){
						if( attackingSquares > (int) (defendingSquares*special) ) {
							attackingSquares -= (int) (defendingSquares*special);
							defendingSquares = 0;
						}
						//otherwise all squares die, inflicting higher casualties upon enemy
						else {
							defendingSquares -= (int) attackingSquares/special;
							attackingSquares = 0;
						}
					}
		
					
					//same reasoning for circles
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
		
					//and triangles
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
					
					//check if fight has ended
					if(battleEnded (startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)){return true;}
				
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//2nd stage
					//squares vs. triangles - inflicting biggest casualties
					//if there are more squares that weaker enemies all enemies die
					if(defendingTriangles !=0){
						if( attackingSquares > (int) (0.5*defendingTriangles*special) ) {
							attackingSquares -= (int) (0.5*defendingTriangles*special);
							defendingTriangles = 0;
						}
						//otherwise all squares die, inflicting higher casualties upon enemy
						else {
							defendingTriangles -= (int) 2*attackingSquares/special;
							attackingSquares = 0;
						}
					}
					
					//same reasoning for circles
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
					
					//and triangles
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
					
					//check if fight has ended
					if(battleEnded (startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)){return true;}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//3rd stage - last step
					if(defendingCircles !=0){
						if( attackingSquares > (int) (2*defendingCircles*special) ) {
							attackingSquares -= (int) (2*defendingCircles*special);
							defendingCircles = 0;
						}
						//otherwise all squares die, inflicting higher casualties upon enemy
						else {
							defendingCircles -= (int) 0.5*attackingSquares/special;
							attackingSquares = 0;
						}
					}
					
					//same reasoning for circles
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
					
					//and triangles
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
				return true;
		}
		
		public boolean battleEnded (int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles,int defendingSquares, int defendingCircles, int defendingTriangles){
			//draw
			if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0 & attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0) {
				//node does not change hands
				//all units are dead so no unit movement
				////System.out.println("Fight in the node: "+targetNode+" was a draw!");
				return true;
			}
			//attacker wins
			else if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0) {
				//node change hands
				myWorld.map[targetNode].setOwner(ID);
				//remaining attackers arrive at node
				myWorld.map[targetNode].setNumberOfSquares(attackingSquares);
				myWorld.map[targetNode].setNumberOfCircles(attackingCircles);
				myWorld.map[targetNode].setNumberOfTriangles(attackingTriangles);
				////System.out.println("Node "+targetNode+" taken by Player "+attackerId+"!\n Remaining attacker units: \n Squares: "+attackingSquares+"\n Circles: "+attackingCircles+"\n Triangles: "+attackingTriangles);
				return true;
			}
			//attacker loses
			else if (attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0){
				//remaining defenders
				myWorld.map[targetNode].setNumberOfSquares(defendingSquares);
				myWorld.map[targetNode].setNumberOfCircles(defendingCircles);
				myWorld.map[targetNode].setNumberOfTriangles(defendingTriangles);
				return true;
			}
			return false;
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
			int attackingSquares = myWorld.map[startNode].numberOfSquares();
			int attackingCircles = myWorld.map[startNode].numberOfCircles();
			int attackingTriangles = myWorld.map[startNode].numberOfTriangles();
			
			//defenders
			int defendingSquares = myWorld.map[targetNode].numberOfSquares();
			int defendingCircles = myWorld.map[targetNode].numberOfCircles();
			int defendingTriangles = myWorld.map[targetNode].numberOfTriangles();
			
			for(int i = 0; i < 3; i++){	enemyCasualties[i] = 0;}
			
			optimalUnitCount [0] = attackingSquares;
			optimalUnitCount [1] = attackingCircles;
			optimalUnitCount [2] = attackingTriangles;
			
			friendlyCasualties [0] = attackingSquares;
			friendlyCasualties [1] = attackingCircles;
			friendlyCasualties [2] = attackingTriangles;
			
			
			
			//special property of a node (decide whenever node is easier or harder to take)
			int special = myWorld.map[targetNode].getSpecial();
			
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
								
							
							currentCasualties[0] = myWorld.map[startNode].numberOfSquares() - attackingSquares;
							currentCasualties[1] = myWorld.map[startNode].numberOfCircles() - attackingCircles;
							currentCasualties[2] = myWorld.map[startNode].numberOfTriangles() - attackingTriangles;
								
							currentEnemyCasualties[0] = myWorld.map[targetNode].numberOfSquares() - defendingSquares;
							currentEnemyCasualties[1] = myWorld.map[targetNode].numberOfCircles() - defendingCircles; 
							currentEnemyCasualties[2] = myWorld.map[targetNode].numberOfTriangles() - defendingTriangles; 
							
							
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
								////System.out.println(best+" | "+isItBetter);
								best = isItBetter;
								for(int i = 0 ; i< 3; i++) optimalUnitCount[i] = currentUnitCount[i];
							}
							isItBetter = 0;
						}
					}
				}
			}
			else if(optimalUnitCount[0] == 0 && optimalUnitCount[1] == 0 && optimalUnitCount[2] == 0){
					if (myWorld.map[startNode].numberOfSquares() >=1) optimalUnitCount[0] = (int) (myWorld.map[startNode].numberOfSquares()*0.3)+1;
					else if (myWorld.map[startNode].numberOfCircles() >=1) optimalUnitCount[1] = (int) (myWorld.map[startNode].numberOfCircles()*0.2)+1;
					else if (myWorld.map[startNode].numberOfTriangles() >=1) optimalUnitCount[2] = (int) (myWorld.map[startNode].numberOfTriangles()*0.1)+1;
			}

			////System.out.println(startNode+" "+targetNode+" | "+optimalUnitCount[0]+" "+optimalUnitCount[1]+" "+optimalUnitCount[2]);
			return optimalUnitCount;
	}
	//standard battle result
	public int getBattleResult(int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){
		int result = 0;
		
		int defendingSquares = myWorld.map[targetNode].numberOfSquares();
		int defendingCircles = myWorld.map[targetNode].numberOfCircles();
		int defendingTriangles = myWorld.map[targetNode].numberOfTriangles();
		int special = myWorld.map[targetNode].getSpecial(); //just in case
		
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
		int special = myWorld.map[targetNode].getSpecial(); //just in case
		
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



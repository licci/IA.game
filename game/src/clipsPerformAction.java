public class clipsPerformAction {
	
	//agent's copy of game world
	public copyOfGraph world = new copyOfGraph();
	
	//CLIPS_Interface LaughingMan =  new CLIPS_Interface("LaughingMan.clp");
	CLIPS_Interface playerOne = null;
	CLIPS_Interface playerTwo = null;
	
	//constructor
	//create new agent with given ID and get current world state for him
	public clipsPerformAction(String file1, String file2){
		
		playerOne = new CLIPS_Interface(file1);
		playerOne = new CLIPS_Interface(file2);
		
		world.initialization();
	}
	
	//action id,startNode,tergetNode,squares,circles,triangles
	public void performAction(int id){
		
		int [] action;
		//get world and prepare it to be sent to agent
		getWorldState(id);
		
		//action = LaughingMan.CLIPS(world,1);
		action = playerOne.CLIPS(world,1);
		
		//commit action
		if(action[0]==0){
			if( main.action.move(id,action[1],action[2],action[3],action[4],action[5]) ) System.out.println("Player - "+id+" - Move succesful");
			else {
				System.out.println(id+" Move NOT succesful!!!!!!!!!");
				//main.pause(20000);
			}
		}
		else if(action[0]==1){
			if( main.action.attack(id,action[1],action[2],action[3],action[4],action[5]) ) System.out.println("Player - "+id+" - Attack succesful");
			else {
				System.out.println(id+" Attack NOT succesful!!!!!!!!!!");
				//main.pause(20000);
			}
		}
		else if(action[0] == -1) System.out.println("No moves to do - waiting!");
	}
	

//WORLD STATE AND ESTIMATES______________________________________________________________________________________________________________________________________________
	
	//before each move world state will be updated
	void getWorldState(int id)
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
		
		int [] visibleNodes = new int [14];
		for(int i = 0; i < 14; i++){visibleNodes[i] = -1;} //-1 not in visual range / 1 - in visual range
		
		//create an array that stores whenever the node is in visual range of an agent
		//for each node
		for(int i = 0; i < 14; i++){
			//check if the node belongs to agent
			if(world.map[i].belongs() == id){
				visibleNodes[i] = 1;
				//get possible targets for agent actions
				int [] nodes = world.map[i].getAvailableNodes();
				for (int j = 0; j < 5; j++){
					if(nodes[j] != -1){
						visibleNodes[nodes[j]] = 1;
					}
				}
			}
		}
		
		for(int i = 0; i < 14; i++){
			if(visibleNodes[i] < 1){
				estimateNode(i,id);
			}
		}
		
		//for(int i = 0; i < 14; i++){System.out.println("Node "+i+" units "+world.map[i].numberOfSquares()+" "+world.map[i].numberOfCircles()+" "+world.map[i].numberOfTriangles());}
	}
	
	void estimateNode(int nodeId,int id){
		int enemyHQ = 0;
		int enemyID = 0;
		
		if(id == 0){
			enemyHQ = 13;
			enemyID = 2;
		}
		else {
			enemyHQ = 0;
			enemyID = 1;
		}
		if(nodeId == enemyHQ){
			world.map[nodeId].setOwner(enemyID);
			world.map[nodeId].setNumberOfSquares(15+main.turnCount);
			world.map[nodeId].setNumberOfCircles(15);
			world.map[nodeId].setNumberOfTriangles(15);
		}
		
		//before turn 4 all nodes outside vision range are assumed to be neutral
		//and have default unit count + standard growth rate
		else if (main.turnCount<4){
			if(nodeId == enemyHQ){
				world.map[nodeId].setOwner(enemyID);
				world.map[nodeId].setNumberOfSquares(15+main.turnCount);
				world.map[nodeId].setNumberOfCircles(15);
				world.map[nodeId].setNumberOfTriangles(15);
			}
			else{
				world.map[nodeId].setOwner(0); 
				//square production node
				if(world.map[nodeId].creationType() == 1){
					world.map[nodeId].setNumberOfSquares(15+main.turnCount);
					world.map[nodeId].setNumberOfCircles(0);
					world.map[nodeId].setNumberOfTriangles(0);
				}
				//circle production node
				else if(world.map[nodeId].creationType() == 2){
					world.map[nodeId].setNumberOfSquares(0);
					world.map[nodeId].setNumberOfCircles(15+(main.turnCount/2));
					world.map[nodeId].setNumberOfTriangles(0);
				}
				//triangle production node
				else {
					world.map[nodeId].setNumberOfSquares(0);
					world.map[nodeId].setNumberOfCircles(0);
					world.map[nodeId].setNumberOfTriangles(15+(main.turnCount/3));
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
				world.map[nodeId].setOwner(enemyID);
				world.map[nodeId].setNumberOfSquares(main.turnCount);
				world.map[nodeId].setNumberOfCircles(0);
				world.map[nodeId].setNumberOfTriangles(0);
			}
			//other nodes have a combination of units in them - based on growth rate and unit best suited to take them
			else{
				world.map[nodeId].setOwner(enemyID); 
				//square production node
				//squares are vulnerable to circles so the node has only production squares and some circles
				if(world.map[nodeId].creationType() == 1){
					world.map[nodeId].setNumberOfSquares(main.turnCount);
					world.map[nodeId].setNumberOfCircles(main.turnCount/2);
					world.map[nodeId].setNumberOfTriangles(0);
				}
				//circle production node
				//circles are vulnerable to triangles so the node has only production circles and some triangles
				else if(world.map[nodeId].creationType() == 2){
					world.map[nodeId].setNumberOfSquares(0);
					world.map[nodeId].setNumberOfCircles(main.turnCount/2);
					world.map[nodeId].setNumberOfTriangles(main.turnCount/3);
				}
				//triangle production node
				//triangles are vulnerable to squares so the node has only production triangles and some squares
				else {
					world.map[nodeId].setNumberOfSquares(main.turnCount);
					world.map[nodeId].setNumberOfCircles(0);
					world.map[nodeId].setNumberOfTriangles(main.turnCount/3);
				}
			}
		}
	}
}
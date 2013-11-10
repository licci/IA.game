
public class agentActions {

	/*This attack function is based on following rules:
	 * - fight has 3 steps
	 * - in first step attacking units fight with enemy units that are most vulnerable (squares attack triangle and so on)
	 * - in second step attacking units fight with enemy units of the same type they are (squares attack squares and so on)
	 * - in third step attacking units fight with enemy units that are resistant to their type (squares attack circle and so on)
	 * I assumed that advantage over weaker units is equal 2 - this means that attacking squares will kill 2 times more enemies that their own number
	 * ex. 30 squares attack a node with 50 circles inside, node has no special property (special = 1)
	 * result of the fight is calculated as follows
	 * 30 - 0.5*50*special
	 * 30 - 0.5*50*1 = 5
	 * so the attacker won, node now belongs to him and he has 5 squares in the node
	 * 
	 * This works both was - if we attack node with 20 squares and no special property with 30 triangle result will be:
	 * 20 - 0.5*30/special
	 * 20 - 0.5*30/1 = 20 - 15 = 5 
	 * Defender won and still has 5 triangles inside the node
	 */
	public int move(int playerId, int startNode, int targetNode, int movingSquares, int movingCircles, int movingTriangles){
		
		boolean movePossible = false;

		if(playerId == graph.map[startNode].belongsTo & playerId == graph.map[targetNode].belongsTo);
		else {
			System.out.println("One of the nodes is not yours Player "+playerId+" - action impossible!");
			return 1;
		}
	
		for (int i=0; i<5; i++){
			int[] checkStrikePossibility = new int [5];
			checkStrikePossibility = graph.map[startNode].getAvailableNodes();
			
			if (checkStrikePossibility[i] == targetNode) {
				movePossible = true;
				System.out.println("Node in range!");
			}
		}
		
		if(movePossible){
			System.out.println("Moving!");
			//removing moving units from startNode
			graph.map[startNode].setNumberOfSquares(graph.map[startNode].numberOfSquares()-movingSquares);
			graph.map[startNode].setNumberOfCircles(graph.map[startNode].numberOfCircles()-movingCircles);
			graph.map[startNode].setNumberOfTriangles(graph.map[startNode].numberOfTriangles()-movingTriangles);
			
			//adding moving units to targetNode
			graph.map[targetNode].setNumberOfSquares(graph.map[targetNode].numberOfSquares()+movingSquares);
			graph.map[targetNode].setNumberOfCircles(graph.map[targetNode].numberOfCircles()+movingCircles);
			graph.map[targetNode].setNumberOfTriangles(graph.map[targetNode].numberOfTriangles()+movingTriangles);
		}
		System.out.println("Player "+playerId+" moved units from "+startNode+" to "+targetNode+"!\n Units remaining in "+startNode+":");
		System.out.println("Squares: "+graph.map[startNode].numberOfSquares()+"\n Circles: "+graph.map[startNode].numberOfCircles()+"\n Triangles: "+graph.map[startNode].numberOfTriangles());
		System.out.println("Unit count in "+targetNode+": ");
		System.out.println("Squares: "+graph.map[targetNode].numberOfSquares()+"\n Circles: "+graph.map[targetNode].numberOfCircles()+"\n Triangles: "+graph.map[targetNode].numberOfTriangles());
			
		return 0;
	}
	public int attack(int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){
		boolean strikePossible = false;

		//check if startNode belongs to provided user and if targetNode is in range (there is a path from startNode that leads there)
		if(attackerId == graph.map[startNode].belongsTo);
			else {
				System.out.println("Node is not yours Player "+attackerId+" - action impossible!");
			return 1;
			}
		
			for (int i=0; i<5; i++)
			{
				int[] checkStrikePossibility = new int [5];
				checkStrikePossibility = graph.map[startNode].getAvailableNodes();
				
				if (checkStrikePossibility[i] == targetNode) {
					strikePossible = true;
					System.out.println("Node in range!");
				}
			}
		
		if(strikePossible){
			System.out.println("Attacking!");
			//removing attacking units from start node (current value - units send to attack)
			graph.map[startNode].setNumberOfSquares(graph.map[startNode].numberOfSquares()-attackingSquares);
			graph.map[startNode].setNumberOfCircles(graph.map[startNode].numberOfCircles()-attackingCircles);
			graph.map[startNode].setNumberOfTriangles(graph.map[startNode].numberOfTriangles()-attackingTriangles);
			
			//defenders
			int defendingSquares = graph.map[targetNode].numberOfSquares();
			int defendingCircles = graph.map[targetNode].numberOfCircles();
			int defendingTriangles = graph.map[targetNode].numberOfTriangles();
			
			//special property of a node (decide whenever node is easier or harder to take)
			int special = graph.map[targetNode].getSpecial();
			
			/*
			I am really sorry for all of code copy-pasting but I think it is easier to understand this way, as well as easier to debug
			*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//1st stage of fight
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
			if(battleEnded (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) return 1;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//2nd stage
			//units remaining from 1st stage fight with units of the same type - inflicting normal casualties 
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
				if(battleEnded (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) return 0;
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
				return 0; 	
		}else
		{
			
			System.out.println("Attack impossible - node not in range!");
			
			
	
		return 1;
		}
		}
	
	public boolean battleEnded (int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles,int defendingSquares, int defendingCircles, int defendingTriangles){
		//attacker wins
		if(defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0) {
			//node change hands
			graph.map[targetNode].setOwner(attackerId);
			//remaining attackers arrive at node
			graph.map[targetNode].setNumberOfSquares(attackingSquares);
			graph.map[targetNode].setNumberOfCircles(attackingCircles);
			graph.map[targetNode].setNumberOfTriangles(attackingTriangles);
			System.out.println("Node "+targetNode+" taken by Player "+attackerId+"!\n Remaining attacker units: \n Squares: "+attackingSquares+"\n Circles: "+attackingCircles+"\n Triangles: "+attackingTriangles);
			return true;
		}
		//attacker loses
		else if (attackingSquares == 0 & attackingCircles == 0 & attackingTriangles ==0){
			//remaining defenders
			graph.map[targetNode].setNumberOfSquares(defendingSquares);
			graph.map[targetNode].setNumberOfCircles(defendingCircles);
			graph.map[targetNode].setNumberOfTriangles(defendingTriangles);
			return true;
		}
		return false;
	}

}



public class agentActions {

	public boolean move(int playerId, int startNode, int targetNode, int movingSquares, int movingCircles, int movingTriangles){
		
		boolean movePossible = false;

		if(playerId == graph.map[startNode].belongsTo && playerId == graph.map[targetNode].belongsTo);
		else {
			//System.out.println("One of the nodes is not yours Player "+playerId+" - action impossible!");
			return false;
		}
	
		for (int i=0; i<5; i++){
			int[] checkStrikePossibility = new int [5];
			checkStrikePossibility = graph.map[startNode].getAvailableNodes();
			
			if (checkStrikePossibility[i] == targetNode) {
				movePossible = true;
				//System.out.println("Node in range!");
			}
		}
		
		if (graph.map[startNode].numberOfSquares()>= movingSquares && graph.map[startNode].numberOfCircles()>= movingCircles && graph.map[startNode].numberOfTriangles()>= movingTriangles){
			movePossible = true;
			//System.out.println("Units available!");
		} else {
			movePossible = false;
			//System.out.println("Units unavailable!");
		}
		
		if(movePossible){
			//System.out.println("Moving!");
			//removing moving units from startNode
			graph.map[startNode].setNumberOfSquares(graph.map[startNode].numberOfSquares()-movingSquares);
			graph.map[startNode].setNumberOfCircles(graph.map[startNode].numberOfCircles()-movingCircles);
			graph.map[startNode].setNumberOfTriangles(graph.map[startNode].numberOfTriangles()-movingTriangles);
			
			//adding moving units to targetNode
			graph.map[targetNode].setNumberOfSquares(graph.map[targetNode].numberOfSquares()+movingSquares);
			graph.map[targetNode].setNumberOfCircles(graph.map[targetNode].numberOfCircles()+movingCircles);
			graph.map[targetNode].setNumberOfTriangles(graph.map[targetNode].numberOfTriangles()+movingTriangles);
			
			if(playerId==1)Statistics.P1.addMove(movingSquares,movingCircles,movingTriangles); 
			if(playerId==2)Statistics.P2.addMove(movingSquares,movingCircles,movingTriangles); 
		}
		else return false; //returns false if move impossible
		
		//System.out.println("Player "+playerId+" moved units from "+startNode+" to "+targetNode+"!\n Units remaining in "+startNode+":");
		//System.out.println("Squares: "+graph.map[startNode].numberOfSquares()+"\n Circles: "+graph.map[startNode].numberOfCircles()+"\n Triangles: "+graph.map[startNode].numberOfTriangles());
		//System.out.println("Unit count in "+targetNode+": ");
		//System.out.println("Squares: "+graph.map[targetNode].numberOfSquares()+"\n Circles: "+graph.map[targetNode].numberOfCircles()+"\n Triangles: "+graph.map[targetNode].numberOfTriangles());
			
		return true; //returns true if move was successful
	}
	public boolean attack(int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){
		boolean strikePossible = false;

		//check if startNode belongs to provided user and if targetNode is in range (there is a path from startNode that leads there)
		if(attackerId == graph.map[startNode].belongsTo);
			else {
				//System.out.println("Node is not yours Player "+attackerId+" - action impossible!");
				return false;
			}
		if(attackerId == graph.map[targetNode].belongsTo) {
			//System.out.println("Attacking your own node - action impossible!");
			return false;
		}
		
			for (int i=0; i<5; i++){
				int[] checkStrikePossibility = new int [5];
				checkStrikePossibility = graph.map[startNode].getAvailableNodes();
				
				if (checkStrikePossibility[i] == targetNode) {
					strikePossible = true;
					//System.out.println("Node in range!");
				}
			}
			
			if (graph.map[startNode].numberOfSquares()>= attackingSquares && graph.map[startNode].numberOfCircles()>= attackingCircles && graph.map[startNode].numberOfTriangles()>= attackingTriangles){
				strikePossible = true;
				//System.out.println("Units available!");
			} else {
				strikePossible = false;
				//System.out.println("Units unavailable!");
			}
		
		if(strikePossible){
			//System.out.println("Attacking!");
			//removing attacking units from start node (current value - units send to attack)
			graph.map[startNode].setNumberOfSquares(graph.map[startNode].numberOfSquares()-attackingSquares);
			graph.map[startNode].setNumberOfCircles(graph.map[startNode].numberOfCircles()-attackingCircles);
			graph.map[startNode].setNumberOfTriangles(graph.map[startNode].numberOfTriangles()-attackingTriangles);
			
 			//FOR STATISTICS 
			if(attackerId==1)Statistics.P1.addAttack(attackingSquares,attackingCircles,attackingTriangles); 
			if(attackerId==2)Statistics.P2.addAttack(attackingSquares,attackingCircles,attackingTriangles); 
			//END FOR STATISTICS
			
			//defenders
			int defendingSquares = graph.map[targetNode].numberOfSquares();
			int defendingCircles = graph.map[targetNode].numberOfCircles();
			int defendingTriangles = graph.map[targetNode].numberOfTriangles();
			
			//FOR STATISTICS 
			int startattackingSquares=attackingSquares, startattackingCircles=attackingCircles, startattackingTriangles=attackingTriangles;
			int startdefendingSquares=defendingSquares, startdefendingCircles=defendingCircles, startdefendingTriangles=defendingTriangles;
			//END FOR STATISTICS
			
			//special property of a node (decide whenever node is easier or harder to take)
			int special = graph.map[targetNode].getSpecial();
			
			/*
			I am really sorry for all of code copy-pasting but I think it is easier to understand this way, as well as easier to debug
			*/
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
				if(battleEnded (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)){
					
					if(attackerId==1)Statistics.P1.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
					if(attackerId==1)Statistics.P2.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
					if(attackerId==2)Statistics.P2.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
					if(attackerId==2)Statistics.P1.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
					
					return true;
				}
			
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
				if(battleEnded (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) {
					
					//FOR STATISTICS 215
					if(attackerId==1)Statistics.P1.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
					if(attackerId==1)Statistics.P2.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
					if(attackerId==2)Statistics.P2.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
					if(attackerId==2)Statistics.P1.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
					//FOR STATISTICS END
					
					return true;
				}
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
				
				if(attackerId==1)Statistics.P1.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
				if(attackerId==1)Statistics.P2.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
				if(attackerId==2)Statistics.P2.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
				if(attackerId==2)Statistics.P1.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
		}
		else {
			//System.out.println("Attack impossible - node not in range!");
			return false;
		}
		return true;
	}
	
	public boolean battleEnded (int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles,int defendingSquares, int defendingCircles, int defendingTriangles){
		//draw
		if(defendingSquares == 0 && defendingCircles == 0 && defendingTriangles == 0 && attackingSquares == 0 && attackingCircles == 0 && attackingTriangles ==0) {
			//node does not change hands
			//all units are dead so no unit movement
			//System.out.println("Fight in the node: "+targetNode+" was a draw!");
			return true;
		}
		//attacker wins
		else if(defendingSquares == 0 && defendingCircles == 0 && defendingTriangles == 0) {
			//node change hands
			graph.map[targetNode].setOwner(attackerId);
			//remaining attackers arrive at node
			graph.map[targetNode].setNumberOfSquares(attackingSquares);
			graph.map[targetNode].setNumberOfCircles(attackingCircles);
			graph.map[targetNode].setNumberOfTriangles(attackingTriangles);
			//System.out.println("Node "+targetNode+" taken by Player "+attackerId+"!\n Remaining attacker units: \n Squares: "+attackingSquares+"\n Circles: "+attackingCircles+"\n Triangles: "+attackingTriangles);
			return true;
		}
		//attacker loses
		else if (attackingSquares == 0 && attackingCircles == 0 && attackingTriangles ==0){
			//remaining defenders
			graph.map[targetNode].setNumberOfSquares(defendingSquares);
			graph.map[targetNode].setNumberOfCircles(defendingCircles);
			graph.map[targetNode].setNumberOfTriangles(defendingTriangles);
			return true;
		}
		return false;
	}

}


import java.util.ArrayList;
import java.util.List;

public class JakubSearch implements agent {
	
	public static agentActions action = new agentActions();
	
	boolean canIWin=false;
	
	
	int[][] movesArray = new int[1000][3];
	int globalIndex = 0;
	//all moves that i can perform will be stored here
	//[x][y] x=storage y=(-1 empty),(0 move),(1 attack)
	
	 public void cleanArray(){//Cleaning array
		 for (int x = 0; x < 1000; x = x + 1) {
			 for (int y = 0; y < 3; y = y + 1) {
				 movesArray[x][y]=(-1);
			 }
		 }
		 globalIndex = 0; 
		 
	 }
	 
	 
	 //Adding element to array
	 public void addElement(int start, int target, int type){
		 
		movesArray[globalIndex][0]=start;
		movesArray[globalIndex][1]=target;
		movesArray[globalIndex][2]=type;
		globalIndex++;
		 
		 
	 }
	 
	 
	 //Displaying the array data
	 public void displayElements(){
		 for (int x = 0; x < globalIndex; x = x + 1){
			 System.out.println("Position in array: "+x+" Start: "+movesArray[x][0]+" Target: "+movesArray[x][1]+" Type of onteraction: "+movesArray[x][2]);
			  }
		 
	 }
	 
	 
	 //Performing given action
	 public void performAction(int agentId,int index){
		 if(movesArray[index][2]==0){
			 
				action.move(agentId, movesArray[index][0], movesArray[index][1],
						graph.map[movesArray[index][0]].numberOfSquares(),
						graph.map[movesArray[index][0]].numberOfCircles(),
						graph.map[movesArray[index][0]].numberOfTriangles());
		 }
		 if(movesArray[index][2]==1){
			 
				action.attack(agentId, movesArray[index][0], movesArray[index][1],
						graph.map[movesArray[index][0]].numberOfSquares(),
						graph.map[movesArray[index][0]].numberOfCircles(),
						graph.map[movesArray[index][0]].numberOfTriangles());
			 
			 
		 }
		 
	 }

	 
	 //Gets all the actions that can be performed by agent an stores them in movesArray
	 public void actionList(int agentId){
		 cleanArray();

			List<Integer> availableNodes = new ArrayList<Integer>();

			for (int x = 0; x < 14; x = x + 1) {

				if (graph.map[x].belongs() == agentId) {

					availableNodes.add(x);
				}
			}

			int myNodeAmmount = availableNodes.size();
			for(int i=0;i<myNodeAmmount;i++){//go via all the nodes i own
				int RandomNode = availableNodes.get(i);
				//System.out.println("My agent ID: " + agentId + " Node i own: " + RandomNode);
				int[] nextTo =graph.map[RandomNode].getAvailableNodes();
					for (int x = 0; x < 5; x = x +1) {//go via all the nodes that can be connected to my node
						if(nextTo[x]!=(-1)){//connection exists
							
							if(graph.map[nextTo[x]].belongs()==agentId) addElement(RandomNode,nextTo[x],0);//add to array
							
							//System.out.println("Node next to mine: "+ nextTo[x]);
								if(graph.map[nextTo[x]].belongs()!=agentId){//node connected to mine is not mine
									//System.out.println("Enemy node next to mine: "+ nextTo[x]);
									addElement(RandomNode,nextTo[x],1);//add to array
								}
						}
						
					}
			
			}

	 }	
			
		 	 
	 //Evaluates action and selects the best one	 
	 public void actionSelector(int agentId){
		 //Bonus values for actions
		 int attacValue=15;
		 int moveValue=10;
		 int directionBonusValue=10;
		 int victoryBonusValue=20;
		 int finallNodeBonus=30;
		 int enemyBonus=5;
		 int node5and8Bonus=3;
		 int enemyWinBlockBonus=15;
		 
		 int bestMoveIndex=0;
		 int bestMoveValue=0;
		 int tempValue=0;
		 
		 for (int x = 0; x < globalIndex; x = x + 1){
			 //System.out.println("Start: "+movesArray[x][0]+" Target: "+movesArray[x][1]+" Type of onteraction: "+movesArray[x][2]);
			 
			 if(movesArray[x][2]==1){//Bonus for attack
				 tempValue+=attacValue;
				 attackTest(agentId,movesArray[x][0],movesArray[x][1],graph.map[movesArray[x][0]].numberOfSquares(),
							graph.map[movesArray[x][0]].numberOfCircles(),
							graph.map[movesArray[x][0]].numberOfTriangles());
				 if(canIWin)tempValue+=victoryBonusValue;//Bonus for wining a node
			 }
			 if(movesArray[x][2]==0)tempValue+=moveValue;//Bonus for move
			 
			 if((agentId==1)&&(movesArray[x][0]<movesArray[x][1])) tempValue+=directionBonusValue;//Bonus for proper direction of attack/move
			 if((agentId==2)&&(movesArray[x][0]>movesArray[x][1])) tempValue+=directionBonusValue;//Bonus for proper direction of attack/move
			 
			
			 
			 if((movesArray[x][1]==5)||(movesArray[x][1]==8)) tempValue+=node5and8Bonus;//Bonus for actions at central nodes

			 if((graph.map[movesArray[x][1]].belongs()!=agentId)&&(graph.map[movesArray[x][1]].belongs()!=0)){
				 tempValue+=enemyBonus;//Bonus for fighting enemy
				 if((movesArray[x][1]==1)||(movesArray[x][1]==2)||(movesArray[x][1]==3)&&(agentId==1))tempValue+=enemyWinBlockBonus;//Bonus for protecting the base
			 }
			 
			 if((agentId==1)&&(movesArray[x][1])==13) tempValue+=finallNodeBonus;//Bonus for attacking final node
			 if((agentId==2)&&(movesArray[x][1])==0) tempValue+=finallNodeBonus;//Bonus for attacking final node
			 
			 
			 if(tempValue>bestMoveValue){
				 bestMoveIndex=x;
				 bestMoveValue=tempValue;
			 }
			 //System.out.println("Temp: "+tempValue+" Best found: "+bestMoveValue+" Best index: "+bestMoveIndex);
			 tempValue=0;
			 
		  }
		 performAction(agentId,bestMoveIndex);
		 
	 }
	 
	 
	 	//Battle simulation for testing if the victory can be achieved
		public boolean attackTest(int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){

			//check if startNode belongs to provided user and if targetNode is in range (there is a path from startNode that leads there)

			
			if(true){

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
					if(battleEndedTest (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)){ 
				
						
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
					if(battleEndedTest (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) {
						
						
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

				
			}
			return true;
		}
		
		
		//Battle output analyzer 
		public boolean battleEndedTest (int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles,int defendingSquares, int defendingCircles, int defendingTriangles){
			canIWin=false;
			//draw
			if(defendingSquares == 0 && defendingCircles == 0 && defendingTriangles == 0 && attackingSquares == 0 && attackingCircles == 0 && attackingTriangles ==0) {
				return true;
			}
			//attacker wins
			else if(defendingSquares == 0 && defendingCircles == 0 && defendingTriangles == 0) {
				canIWin=true;
				//node change hands
				return true;
			}
			//attacker loses
			else if (attackingSquares == 0 && attackingCircles == 0 && attackingTriangles ==0){
			
				return true;
			}
			return false;
		}
	 
	 
	 

	
	@Override
	public void performAction(int currentAgent) {


		//System.out.println("ACTION OF AGENT  - " + currentAgent);
		actionList(currentAgent);
		actionSelector(currentAgent);


	}

}


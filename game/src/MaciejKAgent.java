import java.util.ArrayList;
import java.util.List;

public class MaciejKAgent implements agent {
//Maciej Kraskiewicz Agent
	public static agentActions action = new agentActions();
//All variables that we use for the agent
	int counter = 0;
static boolean worthAttacking=false;
	
	 int aid1LastNode = 0; 
	 int aid2LastNode = 13;
	 int tablicapomocnicza[][] = new int [14][14];
	 int potrzebneWsparcie[][] = new int [14][14];
	 int dostepneNody[] = new int[15];
	 int dostepneNodyTemp;
	 boolean needDefence=false;
	 boolean moveMade=false;
	 int effect;
	 public static MaciejKagent_graph decisionGraph = new  MaciejKagent_graph();
	
	 //Agent creates graph of nodes. Each of the nodes has got a value of attractivness.
	 //Agent looks for the optimal choice and executes it. Value is created using a few functions, which gives values
	 //depending on situation in the world. 
	public void Attack(int agentId) {
		System.out.println("________________________________");
		List<Integer> availableNodes = new ArrayList<Integer>();
for (int x=0;x<14;x++)  for(int y=0;y<14;y++) tablicapomocnicza[x][y]=0;
for (int x=0;x<14;x++)  for(int y=0;y<14;y++) MaciejKagent_graph.decisionMap[x][y].setEffectivness(0);		
		defence(agentId);
		for (int x = 0; x < 14; x = x + 1) {
			
			if (graph.map[x].belongs() == agentId) {
				
				availableNodes.add(x);
				dostepneNody=graph.map[x].getAvailableNodes();
				
				for( int y=0;y<5;y++)
				{
					
					//This function is giving each move some number of attractivness. Agent will make a move that is more attractive than the other ones.
					dostepneNodyTemp=dostepneNody[y];
					
					if(dostepneNodyTemp!=-1) {
					tablicapomocnicza[x][dostepneNodyTemp]=tablicapomocnicza[x][dostepneNodyTemp]+checkCreation(dostepneNodyTemp);
					tablicapomocnicza[x][dostepneNodyTemp]=tablicapomocnicza[x][dostepneNodyTemp]+checkAdvantage(x,dostepneNodyTemp);
					tablicapomocnicza[x][dostepneNodyTemp]=tablicapomocnicza[x][dostepneNodyTemp]+DistanceAttack(agentId, dostepneNodyTemp);
					tablicapomocnicza[x][dostepneNodyTemp]=tablicapomocnicza[x][dostepneNodyTemp]+checkOpponent(agentId,dostepneNodyTemp);
					
					if(needDefence==false) if(agentId==graph.map[dostepneNodyTemp].belongs()) tablicapomocnicza[x][dostepneNodyTemp]=0;
					
				
					effect=tablicapomocnicza[x][dostepneNodyTemp];
					MaciejKagent_graph.decisionMap[x][dostepneNodyTemp].setEffectivness(effect);
					
					
					}
				}
				
				
				
				
			}
		}
		
		
		
		
				
			
				int bestMove=0, attackStart=0,attackTarget=0;
		//Here we are searching for the best move in the graph. We need to check all the nodes in the graph to check the best solution.
	
				for (int x = 0; x < 14; x = x + 1)
				{
					for (int y = 0; y < 14; y = y + 1)
						if (bestMove<MaciejKagent_graph.decisionMap[x][y].getEffectivness())bestMove = MaciejKagent_graph.decisionMap[x][y].getEffectivness();
				}System.out.println("Best attack have got the value " + bestMove);
				for (int x = 0; x < 14; x = x + 1)
				{
					for (int y = 0; y < 14; y = y + 1)
						if (bestMove==MaciejKagent_graph.decisionMap[x][y].getEffectivness()){System.out.println("Attack from "+x+" to "+y);attackStart=x;attackTarget=y; break;}
				}	
		System.out.println("Best attack from the graph have got the value " + bestMove+" from "+attackStart+" to "+attackTarget);
		
			minimaliseAttack(agentId,attackStart,attackTarget);
		
		
		
		
		
	
	}
//This function perform the action of the agent.

	@Override
	public void performAction(int currentAgent) {

		int randomNum = 1 + (int) (Math.random() * 2);

	
		if (randomNum == 1)
			//randomMove(currentAgent);
			Attack(currentAgent);
		if (randomNum == 2)
			Attack(currentAgent);
		
	}
	
	//This function is checking if there is a possible support for the attacking node. We check it, if forces of attacking node are too small.
	//We are checking the neightbours of attacking node, and the neightbours of the neightbours to find the node with the biggest number of units.
	// If further nodes have got even higher number of units, we use them as support, only if the value of units is three times bigger, than those
	// from the closer nodes. (This is because the support will take too much time, and is worth only if it will be really big.
	public void backup(int agent,int startNode,int targetNode, int whatBackup,int amount)
	{   int nerbynods[]=graph.map[startNode].getAvailableNodes();
	//int nerbynods2[];
    int backupNode=0;
    int backupNode2=0;
    int backupNode3=0;
    int previoustempNode=-1;
   int previoustempNode2=-1;
    
    int tempNode=-1;
    int totalunits=0,testtotalunits=0;
	
    totalunits=0;testtotalunits=0;
    for( int y=0;y<5;y++)
   	{   
   		backupNode=nerbynods[y];
   		
   		
   		if(backupNode!=-1)
   		{   if(graph.map[backupNode].belongs()==agent&&backupNode!=startNode)
   			{testtotalunits=(graph.map[backupNode].numberOfTriangles()+graph.map[backupNode].numberOfCircles()+graph.map[backupNode].numberOfSquares());
   			System.out.println("Support possible from "+backupNode+" With strength "+testtotalunits);
   				if(testtotalunits>totalunits){totalunits=testtotalunits; previoustempNode=-1; tempNode=backupNode; previoustempNode2=-1;}}
   				
   				
   				int nerbynods2[]=graph.map[backupNode].getAvailableNodes();
   				for( int k=0;k<5;k++)	
   				{  
   					backupNode2=nerbynods2[k];
   				if(backupNode2!=-1&&graph.map[backupNode2].belongs()==agent&&backupNode2!=startNode)
   				 {testtotalunits=(graph.map[backupNode2].numberOfTriangles()+graph.map[backupNode2].numberOfCircles()+graph.map[backupNode2].numberOfSquares());
   					System.out.println("Support possible from "+backupNode2+" With strength "+testtotalunits);
   					if(testtotalunits>totalunits){totalunits=testtotalunits; previoustempNode=backupNode; tempNode=backupNode2;previoustempNode2=-1;}
   				 
   				 
   				 
   				 }
   				if(backupNode2!=-1)
   				{int nerbynods3[]=graph.map[backupNode2].getAvailableNodes();
   				for( int m=0;m<5;m++)	
   				{  
   					backupNode3=nerbynods3[m];
   				if(backupNode3!=-1&&graph.map[backupNode3].belongs()==agent&&backupNode3!=startNode)
   				 {testtotalunits=(graph.map[backupNode3].numberOfTriangles()+graph.map[backupNode3].numberOfCircles()+graph.map[backupNode3].numberOfSquares());
   					System.out.println("Support possible from "+backupNode3+" With strength "+testtotalunits);
   					if(testtotalunits>totalunits*3){totalunits=testtotalunits; previoustempNode=backupNode; tempNode=backupNode3; previoustempNode2=backupNode2;}
   				 }}}
   				
   				
   				 }
   		}
   	}
    System.out.println("PrevioustempNode:"+previoustempNode+" tempNode"+tempNode);
    
    if( tempNode>=0)
    {
    	if(previoustempNode<=0&&previoustempNode<=0){{action.move(agent, tempNode,startNode,graph.map[tempNode].numberOfSquares(),graph.map[tempNode].numberOfCircles(),graph.map[tempNode].numberOfTriangles()); }
    	
    	} else
    
    {if(previoustempNode2<=0){System.out.println("Najlepsze wsparcie:"+tempNode+ " O wartosci: "+totalunits);
    if(graph.map[previoustempNode].belongs()==agent)
    action.move(agent, tempNode,previoustempNode,graph.map[tempNode].numberOfSquares(),graph.map[tempNode].numberOfCircles(),graph.map[tempNode].numberOfTriangles()); 
    else action.attack(agent, tempNode,previoustempNode,graph.map[tempNode].numberOfSquares(),graph.map[tempNode].numberOfCircles(),graph.map[tempNode].numberOfTriangles()); 
    
    }
    else {
    	 if(graph.map[previoustempNode2].belongs()==agent)
    		    action.move(agent, tempNode,previoustempNode2,graph.map[tempNode].numberOfSquares(),graph.map[tempNode].numberOfCircles(),graph.map[tempNode].numberOfTriangles()); 
    		    else action.attack(agent, tempNode,previoustempNode2,graph.map[tempNode].numberOfSquares(),graph.map[tempNode].numberOfCircles(),graph.map[tempNode].numberOfTriangles()); 
    		    
    	}
	}}
  
		
	}
	

	//****************************************************************************************************/
	//Below are functions which determines the attractivness of the field.
	
	
	//The nodes near HQ and opponent HQ are more valuable then those  in the middle (Because of creating proper defence, and setting up attack.
	public int DistanceAttack(int agent, int x)
	{   int Attractivness = 0;
		if(agent==1)switch(x)
		{
		case 1: Attractivness=4;break;case 2: Attractivness=4;break;case 3: Attractivness=4;break;
		case 4: Attractivness=2;break;case 6: Attractivness=2;break;case 5: Attractivness=3;break;case 8: Attractivness=3;break;
		case 7: Attractivness=4;break;case 9: Attractivness=4;break;
		case 10: Attractivness=7;break;case 11: Attractivness=7;break;case 12: Attractivness=7;break;
		case 13: Attractivness=10;break;
		}
		if(agent==2)
			switch(x)
			{
			case 0: Attractivness=10;break;
			case 1: Attractivness=7;break;case 2: Attractivness=7;break;case 3: Attractivness=7;break;
			case 4: Attractivness=4;break;case 6: Attractivness=4;break;case 5: Attractivness=3;break;case 8: Attractivness=3;break;
			case 7: Attractivness=2;break;case 9: Attractivness=2;break;
			case 10: Attractivness=4;break;case 11: Attractivness=4;break;case 12: Attractivness=4;break;
			}
		return Attractivness;
	}
	
	//Nodes with bigger creation speed are more valuable then those with slower creation speed.
	public int checkCreation(int x)
	{   int add=graph.map[x].speed();
		switch(add)
		{case 1: add=add*3; break;
		case 2: add=add*3; break;
		case 3: add=add*3; break;
		}
		return add;
	}
	
	//If we have got more forces that the defender, it is better to attack.
	public int checkAdvantage(int startNode, int targetNode)
	{   int advantageAttractivness=0;
		int circle=graph.map[targetNode].numberOfCircles();
		int squares=graph.map[targetNode].numberOfSquares();
		int triangles=graph.map[targetNode].numberOfTriangles();
		if(circle<graph.map[startNode].numberOfCircles()) advantageAttractivness=advantageAttractivness+10;
		if(squares<graph.map[startNode].numberOfSquares())advantageAttractivness=advantageAttractivness+10;
		if(triangles<graph.map[startNode].numberOfTriangles())advantageAttractivness=advantageAttractivness+10;
		return advantageAttractivness;
	}
	
	//Agent should remember, that the most important thing is to attack opponent, not the neutral nodes!
	public int checkOpponent(int agent,int targetNode)
	{   if(agent==1||agent==2)
		switch(agent)
		{ case 1: if(graph.map[targetNode].belongs()==2) return 15; break;
		case 2: if(graph.map[targetNode].belongs()==1) return 15; break;
		}  
			return 0;
	
	}
	//If opponnent will take one of the nodes next to the HQ, agent should prepare the defence.
	public void defence(int agent)
	{  
		
	 int dostepneNodyDefence[] = new int[6];
	 int dostepneNodyTempDefence=-1;
		switch(agent)
		{
		case 1: if(graph.map[1].belongs()==2) { dostepneNodyDefence=graph.map[1].getAvailableNodes(); for( int y=0;y<5;y++) {dostepneNodyTempDefence=dostepneNodyDefence[y]; if(dostepneNodyTempDefence!=-1){ System.out.println("Obrona 1 mo퓄iwa z " + dostepneNodyTempDefence); tablicapomocnicza[dostepneNodyTempDefence][1]=tablicapomocnicza[dostepneNodyTempDefence][1]+30; }}}
				if(graph.map[2].belongs()==2) { dostepneNodyDefence=graph.map[2].getAvailableNodes(); for( int y=0;y<5;y++) {dostepneNodyTempDefence=dostepneNodyDefence[y]; if(dostepneNodyTempDefence!=-1){ System.out.println("Obrona 2 mo퓄iwa z " + dostepneNodyTempDefence); tablicapomocnicza[dostepneNodyTempDefence][2]=tablicapomocnicza[dostepneNodyTempDefence][2]+30;}}}
				if(graph.map[2].belongs()==2) { dostepneNodyDefence=graph.map[3].getAvailableNodes(); for( int y=0;y<5;y++) {dostepneNodyTempDefence=dostepneNodyDefence[y]; if(dostepneNodyTempDefence!=-1){ System.out.println("Obrona 3 mo퓄iwa z " + dostepneNodyTempDefence); tablicapomocnicza[dostepneNodyTempDefence][2]=tablicapomocnicza[dostepneNodyTempDefence][2]+30;}}}
				break;
		
		
		
		
		
		case 2: if(graph.map[10].belongs()==1) {dostepneNodyDefence=graph.map[10].getAvailableNodes(); for( int y=0;y<5;y++) {dostepneNodyTempDefence=dostepneNodyDefence[y]; if(dostepneNodyTempDefence!=-1){ System.out.println("Obrona 10 mo퓄iwa z " + dostepneNodyTempDefence); tablicapomocnicza[dostepneNodyTempDefence][10]=tablicapomocnicza[dostepneNodyTempDefence][10]+30;}}}
				if(graph.map[11].belongs()==1) {dostepneNodyDefence=graph.map[11].getAvailableNodes(); for( int y=0;y<5;y++) {dostepneNodyTempDefence=dostepneNodyDefence[y]; if(dostepneNodyTempDefence!=-1){ System.out.println("Obrona 11 mo퓄iwa z " + dostepneNodyTempDefence); tablicapomocnicza[dostepneNodyTempDefence][11]=tablicapomocnicza[dostepneNodyTempDefence][11]+30;}}}
				if(graph.map[12].belongs()==1) {dostepneNodyDefence=graph.map[12].getAvailableNodes(); for( int y=0;y<5;y++) {dostepneNodyTempDefence=dostepneNodyDefence[y]; if(dostepneNodyTempDefence!=-1){ System.out.println("Obrona 12 mo퓄iwa z " + dostepneNodyTempDefence); tablicapomocnicza[dostepneNodyTempDefence][12]=tablicapomocnicza[dostepneNodyTempDefence][12]+30;}}}
				break;	
		}
	}
	
	
	//Attacking function. We are checking if attack is worth attacking (We use a copy of attack function from the game client and calculate how the battle will finish.
	// Then if the agent will know if he will win, we attack with minimum of required forces.
	//Agent also knows that attacking is more profitable than defending himself. That is why, it calculates, how many opponent units will survive after the attack.
	//If the % of survived enemies is less then 35 he attacks.
	public void minimaliseAttack(int agent, int startNode, int targetNode)
	{	int circle;
		int squares;
		int triangles;
		boolean needCircles=false, needSquares=false,needTriangles=false;
		int amountOfNeedCircles=0,amountOfNeedTriangles=0,amountOfNeedSquares=0;
		int whatbackup;
		whatbackup=0;

		if(graph.map[startNode].numberOfCircles()>graph.map[targetNode].numberOfCircles()) circle=graph.map[targetNode].numberOfCircles()+1; else {circle=graph.map[startNode].numberOfCircles(); amountOfNeedCircles=graph.map[targetNode].numberOfCircles()-graph.map[startNode].numberOfCircles(); needCircles=true;}
		if(graph.map[startNode].numberOfSquares()>graph.map[targetNode].numberOfSquares()) squares=graph.map[targetNode].numberOfSquares()+1; else {squares=graph.map[startNode].numberOfSquares();  amountOfNeedSquares=graph.map[targetNode].numberOfSquares()-graph.map[startNode].numberOfSquares();needSquares=true;}
		if(graph.map[startNode].numberOfTriangles()>graph.map[targetNode].numberOfTriangles()) triangles=graph.map[targetNode].numberOfTriangles()+1; else {triangles=graph.map[startNode].numberOfTriangles();amountOfNeedTriangles=graph.map[targetNode].numberOfTriangles()-graph.map[startNode].numberOfTriangles();needTriangles=true;}
		worthAttacking=false;
		testattack(agent, startNode, targetNode,squares,circle, triangles);
		if(worthAttacking==true) action.attack(agent, startNode, targetNode,squares,circle, triangles);
		else {System.out.println("Not worth attacking");
		if(amountOfNeedCircles>amountOfNeedTriangles&&amountOfNeedCircles>amountOfNeedSquares) whatbackup=1;
		if(amountOfNeedTriangles>amountOfNeedCircles&&amountOfNeedTriangles>amountOfNeedSquares) whatbackup=2;
		if(amountOfNeedSquares>amountOfNeedTriangles&&amountOfNeedSquares>amountOfNeedCircles) whatbackup=3;
		
		switch(whatbackup)
		{
		case 1:backup(agent, startNode,targetNode,whatbackup,amountOfNeedCircles); break;
		case 2:backup(agent, startNode,targetNode,whatbackup,amountOfNeedSquares); break;
		case 3:backup(agent, startNode,targetNode,whatbackup,amountOfNeedTriangles); break;
		}
		
		}
		
		
	}
//This function is the copy of attack function in the game engine.
	public boolean testattack(int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles){
		boolean strikePossible = false;

		//check if startNode belongs to provided user and if targetNode is in range (there is a path from startNode that leads there)
		if(attackerId == graph.map[startNode].belongsTo);
			else {
				//System.out.println("Node is not yours Player "+attackerId+" - action impossible!");
				return false;
			}
		/*if(attackerId == graph.map[targetNode].belongsTo) {
			//System.out.println("Attacking your own node - action impossible!");
			return false;
		}*/
		
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
		
			//defenders
			int defendingSquares = graph.map[targetNode].numberOfSquares();
			int defendingCircles = graph.map[targetNode].numberOfCircles();
			int defendingTriangles = graph.map[targetNode].numberOfTriangles();
			
			//special property of a node (decide whenever node is easier or harder to take)
			int special = graph.map[targetNode].getSpecial();
			int totaldefendersbegin=defendingSquares+defendingCircles+defendingTriangles;
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
				if(battleEnded (totaldefendersbegin,attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) return true;
			
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
				if(battleEnded (totaldefendersbegin,attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) return true;
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
				} 	if(battleEnded (totaldefendersbegin,attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles)) return true;

			
		}
		else {
			//System.out.println("Attack impossible - node not in range!");
			return false;
		}
		return true;
	}
	
	//This function is a modified copy of battleEnded function. We do not make any changes in nodes. We just check
	//if attack is worth making it. So we are interested only in the results of battles. Even if defenders will survive,
	// if their amount is lower than 35% agent attacks.
	public boolean battleEnded (int total,int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles,int defendingSquares, int defendingCircles, int defendingTriangles){
		
		int totaldefendersend=0;
		int oppLeft=0;
		//draw
		if(defendingSquares == 0 && defendingCircles == 0 && defendingTriangles == 0 && attackingSquares == 0 && attackingCircles == 0 && attackingTriangles ==0) {
			
			worthAttacking=false;
			return true;
		}
		//attacker wins
		else if(defendingSquares == 0 && defendingCircles == 0 && defendingTriangles == 0) {
			worthAttacking=true;
			return true;
		}
		//attacker loses
		else if (attackingSquares == 0 && attackingCircles == 0 && attackingTriangles ==0){
			//remaining defenders
			totaldefendersend=defendingSquares+defendingCircles+defendingTriangles;
			oppLeft=(totaldefendersend*100)/total;
			if(oppLeft<35)worthAttacking=true;
			else
				worthAttacking=false;
			System.out.println("Opp survived % = "+oppLeft);
			return true;
		}
		return false;
	}
	
}

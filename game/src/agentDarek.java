import java.util.ArrayList; //for the list of possible targets
import java.util.Arrays; //arrays for simple queue, list of visited nodes, levels, etc.


public class agentDarek {
	
	private int totalNodesNumber=14; //since the map doesn't have a get method for size
	private int myRoot;
	private int enemyRoot;
	private int[] rootLevels;
	private int endLevel;
	agentActions action = new agentActions();
	
	public agentDarek (int myNode, int enemyNode)
	{
		myRoot=myNode;
		enemyRoot=enemyNode;
		resultOfBFS r = levelsBFS(myRoot,enemyRoot);
		rootLevels=r.levels;
		endLevel=r.distance;
	}
	
	public void performAction()
	{
		//can I win now?
		int[] finishers = graph.map[enemyRoot].getAvailableNodes();
		for(int i=0; i<5 && finishers[i]>-1; i++) //loop through all neighbours
		{
			if(whoseNodeIsIt(finishers[i])==whoseNodeIsIt(myRoot))
			{
				if(willItWin(graph.map[finishers[i]].numberOfCircles(), graph.map[finishers[i]].numberOfSquares(), graph.map[finishers[i]].numberOfTriangles(),
							 graph.map[enemyRoot].numberOfCircles(), 	graph.map[enemyRoot].numberOfSquares(),	   graph.map[enemyRoot].numberOfTriangles())>0)
				{
					System.out.println("WINNING");
					action.attack(whoseNodeIsIt(myRoot),finishers[i],enemyRoot,graph.map[finishers[i]].numberOfSquares(),graph.map[finishers[i]].numberOfCircles(),graph.map[finishers[i]].numberOfTriangles());
					return; //job is done, game is finished
				}
			}
		}
		
		//am i in danger? (can I lose a node?)
		boolean danger=false;
		boolean cannnotDefend=false;
		boolean[] nodesInDanger = new boolean [totalNodesNumber];
		for(int i=0; i<totalNodesNumber; i++) //check if one of my nodes is in danger of losing
		{
			if(isNodeSafe(i)==false) {if(!danger)System.out.print("Danger: "); danger=true; nodesInDanger[i]=true; System.out.print(i+", ");}
		}
		if(danger)System.out.println("");
		
		if(danger) //if danger than defend
		{
			int dangerLevel=endLevel;
			int nodeToProtect=myRoot;
			//which node is in the biggest danger?
			for(int i=0; i<totalNodesNumber; i++)
			{
				if(nodesInDanger[i]==true)
				{
					if(dangerLevel>levelOf(i)) //checking the level of a node
					{
						dangerLevel=levelOf(i);
						nodeToProtect=i;
					}
					else if(dangerLevel==levelOf(i)) //if the level is the same
					{
						if(howManyEnemies(i)>howManyEnemies(nodeToProtect)) nodeToProtect=i; //check where is more enemies
					}
				}
			}
			System.out.println("Protect: "+nodeToProtect);
			
			//check if there is an attack that will counter the danger
			int enemyLevel=endLevel;
			int source=myRoot;
			int target=enemyRoot;
			int attackEfficiency=0;
			int[] enemiesAround = graph.map[nodeToProtect].getAvailableNodes(); //get all neighbours of the one that needs to be protected
			for(int i=0; i<5 && enemiesAround[i]>-1; i++) //loop through all neighbours
			{
				if(whoseNodeIsIt(enemiesAround[i])==whoseNodeIsIt(enemyRoot)) //check if node belongs to enemy
				{
					int[] friendlies = graph.map[enemiesAround[i]].getAvailableNodes();
					for(int j=0; j<5 && friendlies[j]>-1; j++) //loop through all neighbours of each neighbour
					{
						if(whoseNodeIsIt(friendlies[j])==whoseNodeIsIt(myRoot)) //check if node is mine
						{
							//if this attack is more efficient then a previous candidate
							if(attackEfficiency<willItWin(graph.map[friendlies[j]].numberOfCircles(),    graph.map[friendlies[j]].numberOfSquares(),    graph.map[friendlies[j]].numberOfTriangles(),
									 					  graph.map[enemiesAround[i]].numberOfCircles(), graph.map[enemiesAround[i]].numberOfSquares(), graph.map[enemiesAround[i]].numberOfTriangles()))
							{
								source=friendlies[j];
								target=enemiesAround[i];
								enemyLevel=levelOf(target);
								attackEfficiency=willItWin(graph.map[source].numberOfCircles(), graph.map[source].numberOfSquares(), graph.map[source].numberOfTriangles(),
							 			  				   graph.map[target].numberOfCircles(), graph.map[target].numberOfSquares(), graph.map[target].numberOfTriangles());
								System.out.println("source="+source+" target="+target+" efficiency="+attackEfficiency);
							}
							else if(attackEfficiency==willItWin(graph.map[friendlies[j]].numberOfCircles(),    graph.map[friendlies[j]].numberOfSquares(),    graph.map[friendlies[j]].numberOfTriangles(),
									 							graph.map[enemiesAround[i]].numberOfCircles(), graph.map[enemiesAround[i]].numberOfSquares(), graph.map[enemiesAround[i]].numberOfTriangles()))
							{
								if(enemyLevel>levelOf(enemiesAround[i])) //if new enemy is deeper
								{
									enemyLevel=levelOf(enemiesAround[i]); //than save it
									source=friendlies[j];
									target=enemiesAround[i];
									attackEfficiency=willItWin(graph.map[source].numberOfCircles(), graph.map[source].numberOfSquares(), graph.map[source].numberOfTriangles(),
								 			  				   graph.map[target].numberOfCircles(), graph.map[target].numberOfSquares(), graph.map[target].numberOfTriangles());
									System.out.println("source="+source+" target="+target+" efficiency="+attackEfficiency);
								}
							}
						}
					}
				}
			}
			if(attackEfficiency>0) //if you can, attack the danger
			{
//				System.out.println("Counter-attacking from "+source+" to "+target);
				action.attack(whoseNodeIsIt(myRoot),source,target,graph.map[source].numberOfSquares(),graph.map[source].numberOfCircles(),graph.map[source].numberOfTriangles());
			}		
			else //else look for backup forces
			{
				int backupNode=lookForStrongestBackup(nodeToProtect); //find your strongest backup
				
				if(backupNode>-1) //if there is a safe backup
				{
//					System.out.println("the strongest backup is in: "+backupNode);
					if(isNodeSafe(backupNode) && backupNode!=myRoot) //check if this backup is not under attack and it is not a root
					{
						action.move(whoseNodeIsIt(myRoot), backupNode, nodeToProtect, graph.map[backupNode].numberOfSquares(), graph.map[backupNode].numberOfCircles(), graph.map[backupNode].numberOfTriangles());
					}
					else
					{
//						System.out.println("backup cannot be used");
						cannnotDefend=true;
					}
				}
				else
				{
//					System.out.println("no backup in range");
					cannnotDefend=true;
				}
			}
		}
		if(danger==false || cannnotDefend==true) //no danger OR defenseless = expand
		{
			//check to which level you own the graph
			int levelOwned=0;
			while(levelOwned<=endLevel)
			{
				if(isLevelMine(levelOwned+1)==true)
				{
					levelOwned++;
				}
				else break;				
			}
//			System.out.println("levelOwned= "+levelOwned);
			
			//expand to the next level
			//find possible targets in the next level
			int nextLevel=levelOwned+1;
			ArrayList<Integer> nextLevelTargets = new ArrayList<Integer>();
			for(int i=0; i<totalNodesNumber; i++)
			{
				if(levelOf(i)==nextLevel && whoseNodeIsIt(i)!=whoseNodeIsIt(myRoot))
				{
					nextLevelTargets.add(i);
				}
			}
			
			//look for enemies in the next level
			boolean enemyInRange=false;
			for(int i=0; i<nextLevelTargets.size(); i++)
	        {
				if(whoseNodeIsIt(nextLevelTargets.get(i))==whoseNodeIsIt(enemyRoot)) enemyInRange=true;
	        }
			if(enemyInRange) //if there is an enemy then remove all non-enemy targets
			{
				for(int x=nextLevelTargets.size()-1; x>=0; x--)
		        {
					if(whoseNodeIsIt(nextLevelTargets.get(x))!=whoseNodeIsIt(enemyRoot)) nextLevelTargets.remove(x);
		        }
			}
			
			//pick a target from the list
			int bestTarget=-1;
			int bestSource=-1;
			int bestEfficiency=0;
			while(nextLevelTargets.size()>0)
			{
				int randomPick = (int) (Math.random() * nextLevelTargets.size()); //select a target
				int target = nextLevelTargets.get(randomPick); //store selected target
				nextLevelTargets.remove(randomPick); //remove the pick from the list
//				System.out.println("losuj cel: "+target);
				
				//find the best source for the given target
				int source = -1;
				int efficiency = 0;
				int[] availableSources = graph.map[target].getAvailableNodes();
				for(int i=0; i<5 && availableSources[i]>-1; i++) //loop through all neighbours
				{
					if(whoseNodeIsIt(availableSources[i])==whoseNodeIsIt(myRoot)) //check if it is yours
					{
//						System.out.println("source: "+availableSources[i]);
//						System.out.println(willItWin(graph.map[availableSources[i]].numberOfCircles(), graph.map[availableSources[i]].numberOfSquares(), graph.map[availableSources[i]].numberOfTriangles(),
//								 graph.map[target].numberOfCircles(),              graph.map[target].numberOfSquares(), 			 graph.map[target].numberOfTriangles()));
						//check if you can successfully attack the target from this source
						if(willItWin(graph.map[availableSources[i]].numberOfCircles(), graph.map[availableSources[i]].numberOfSquares(), graph.map[availableSources[i]].numberOfTriangles(),
									 graph.map[target].numberOfCircles(),              graph.map[target].numberOfSquares(), 			 graph.map[target].numberOfTriangles())>efficiency)
						{
							source=availableSources[i];
							efficiency=willItWin(graph.map[availableSources[i]].numberOfCircles(), graph.map[availableSources[i]].numberOfSquares(), graph.map[availableSources[i]].numberOfTriangles(),
									 			 graph.map[target].numberOfCircles(),              graph.map[target].numberOfSquares(), 			 graph.map[target].numberOfTriangles());
//							System.out.println("source="+source+" target="+target+" efficiency="+efficiency);
						}
					}
				}
				if(efficiency>bestEfficiency)
				{
					bestEfficiency=efficiency;
					bestTarget=target;
					bestSource=source;
				}
			}
			if(bestEfficiency>0)
			{
//				System.out.println("best source="+bestSource+" best target="+bestTarget+" best efficiency="+bestEfficiency);
				
				int targetCircles = graph.map[bestTarget].numberOfCircles();
				int targetSquares = graph.map[bestTarget].numberOfSquares();
				int targetTriangles = graph.map[bestTarget].numberOfTriangles();
				
				int neededCircles=0;
				int neededSquares=0;
				int neededTriangles=0;
				
				if(targetCircles==0 && targetSquares==0)
				{
					while(willItWin(neededCircles, neededSquares, neededTriangles, targetCircles, targetSquares, targetTriangles)==0)
					{
						if(graph.map[bestSource].numberOfSquares()>neededSquares) neededSquares++;
						else break;
					}
					if(neededSquares<graph.map[bestSource].numberOfSquares()/2) neededSquares=graph.map[bestSource].numberOfSquares()/2;
				}
				else if(targetSquares==0 && targetTriangles==0)
				{
					while(willItWin(neededCircles, neededSquares, neededTriangles, targetCircles, targetSquares, targetTriangles)==0)
					{
						if(graph.map[bestSource].numberOfTriangles()>neededTriangles) neededTriangles++;
						else break;
					}
					if(neededTriangles<graph.map[bestSource].numberOfTriangles()/2) neededTriangles=graph.map[bestSource].numberOfTriangles()/2;
				}
				else if(targetTriangles==0 && targetCircles==0)
				{
					while(willItWin(neededCircles, neededSquares, neededTriangles, targetCircles, targetSquares, targetTriangles)==0)
					{
						if(graph.map[bestSource].numberOfCircles()>neededCircles) neededCircles++;
						else break;
					}
					if(neededCircles<graph.map[bestSource].numberOfCircles()/2) neededCircles=graph.map[bestSource].numberOfCircles()/2;
				}
				
				if(neededCircles==0 && neededSquares==0 && neededTriangles==0)
				{
					neededCircles=graph.map[bestSource].numberOfCircles();
					neededSquares=graph.map[bestSource].numberOfSquares();
					neededTriangles=graph.map[bestSource].numberOfTriangles();
				}

				if(neededCircles>0 || neededSquares>0 || neededTriangles>0)
				{
//					System.out.println("attacking with C="+neededCircles+" S="+neededSquares+" T="+neededTriangles);
					action.attack(whoseNodeIsIt(myRoot),bestSource,bestTarget,neededSquares,neededCircles,neededTriangles);
				}
			}
			else System.out.println("no ways to expand");
		}
	}
	
	private int willItWin(int sourceCircles, int sourceSquares, int sourceTriangles, int targetCircles, int targetSquares, int targetTriangles)
	{
		if(targetCircles==0 && targetSquares==0 && targetTriangles==0) return 4;

		//stage 1
		
//		System.out.println("START");
//		System.out.println("TC="+targetCircles+" SC="+sourceCircles);
//		System.out.println("TS="+targetSquares+" SS="+sourceSquares);
//		System.out.println("TT="+targetTriangles+" ST="+sourceTriangles);
		if(targetCircles > 0)
		{
			if(targetCircles < sourceCircles)
			{
				sourceCircles-=targetCircles;
				targetCircles=0;
			}
			else
			{
				targetCircles-=sourceCircles;
				sourceCircles=0;
			}
		}
		if(targetSquares > 0)
		{
			if(targetSquares < sourceSquares)
			{
				sourceSquares-=targetSquares;
				targetSquares=0;
			}
			else
			{
				targetSquares-=sourceSquares;
				sourceSquares=0;
			}
		}
		if(targetTriangles > 0)
		{
			if(targetTriangles < sourceTriangles)
			{
				sourceTriangles-=targetTriangles;
				targetTriangles=0;
			}
			else
			{
				targetTriangles-=sourceTriangles;
				sourceTriangles=0;
			}
		}
//		System.out.println("AFTER STAGE 1");
//		System.out.println("TC="+targetCircles+" SC="+sourceCircles);
//		System.out.println("TS="+targetSquares+" SS="+sourceSquares);
//		System.out.println("TT="+targetTriangles+" ST="+sourceTriangles);
		//eo stage 1
		if(targetCircles==0 && targetSquares==0 && targetTriangles==0)
		{
			if(sourceCircles>0 || sourceSquares>0 || sourceTriangles>0) return 2;
		}

		//stage 2
		if(targetCircles > 0)
		{
			if(0.5*targetCircles < sourceTriangles)
			{
				sourceTriangles-=0.5*targetCircles;
				targetCircles=0;
			}
			else
			{
				targetCircles-=2*sourceTriangles;
				sourceTriangles=0;
			}
		}
		if(targetSquares > 0)
		{
			if(0.5*targetSquares < sourceCircles)
			{
				sourceCircles-=0.5*targetSquares;
				targetSquares=0;
			}
			else
			{
				targetSquares-=2*sourceCircles;
				sourceCircles=0;
			}
		}
		if(targetTriangles > 0)
		{
			if(0.5*targetTriangles < sourceSquares)
			{
				sourceSquares-=0.5*targetTriangles;
				targetTriangles=0;
			}
			else
			{
				targetTriangles-=2*sourceSquares;
				sourceSquares=0;
			}
		}
//		System.out.println("AFTER STAGE 2");
//		System.out.println("TC="+targetCircles+" SC="+sourceCircles);
//		System.out.println("TS="+targetSquares+" SS="+sourceSquares);
//		System.out.println("TT="+targetTriangles+" ST="+sourceTriangles);
		//eo stage 2
		if(targetCircles==0 && targetSquares==0 && targetTriangles==0)
		{
			if(sourceCircles>0 || sourceSquares>0 || sourceTriangles>0) return 3;
		}

		//stage 3
		if(targetCircles > 0)
		{
			if(2*targetCircles < sourceSquares)
			{
				sourceSquares-=2*targetCircles;
				targetCircles=0;
			}
			else
			{
				targetCircles-=0.5*sourceSquares;
				sourceSquares=0;
			}
		}
		if(targetSquares > 0)
		{
			if(2*targetSquares < sourceTriangles)
			{
				sourceTriangles-=2*targetSquares;
				targetSquares=0;
			}
			else
			{
				targetSquares-=0.5*sourceTriangles;
				sourceTriangles=0;
			}
		}
		if(targetTriangles > 0)
		{
			if(2*targetTriangles < sourceCircles)
			{
				sourceCircles-=2*targetTriangles;
				targetTriangles=0;
			}
			else
			{
				targetTriangles-=0.5*sourceCircles;
				sourceCircles=0;
			}
		}
//		System.out.println("AFTER STAGE 3");
//		System.out.println("TC="+targetCircles+" SC="+sourceCircles);
//		System.out.println("TS="+targetSquares+" SS="+sourceSquares);
//		System.out.println("TT="+targetTriangles+" ST="+sourceTriangles);
		//eo stage 3
		if(targetCircles==0 && targetSquares==0 && targetTriangles==0)
		{
			if(sourceCircles>0 || sourceSquares>0 || sourceTriangles>0) return 1;
		}
		return 0;
	}
	
	private int lookForStrongestBackup(int nn)
	{
		int strongest=-1;
		int biggestSum=0;
		int[] available = graph.map[nn].getAvailableNodes();
		for(int i=0; i<5 && available[i]>-1; i++) //loop through all neighbours
		{
			if(whoseNodeIsIt(available[i])==whoseNodeIsIt(myRoot)) //if node is mine
			{
				int sum= graph.map[available[i]].numberOfCircles()
						+graph.map[available[i]].numberOfSquares()
						+graph.map[available[i]].numberOfTriangles(); //sum up all units
				if(sum>biggestSum) //if current sum is bigger than the biggest so far then we have a new biggest one
				{
					biggestSum=sum;
					strongest=available[i];
				}
			}
		}
		return strongest;
	}
	
	private int howManyEnemies(int nn) //counts the surrounding enemies for a given node
	{
		int enemyNumber=0;
		int[] available = graph.map[nn].getAvailableNodes();
		for(int i=0; i<5 && available[i]>-1; i++) //loop through all neighbours
		{
			if(whoseNodeIsIt(available[i])==whoseNodeIsIt(enemyRoot)) enemyNumber++; //increase the enemy number
		}
		return enemyNumber;
	}
	
	private boolean isNodeSafe(int nn)
	{
		boolean safe=true;
		if(whoseNodeIsIt(nn)==whoseNodeIsIt(myRoot)) //if node is mine
		{
			int[] available = graph.map[nn].getAvailableNodes();
			for(int i=0; i<5 && available[i]>=0; i++)
			{
				if(graph.map[available[i]].belongs()==whoseNodeIsIt(enemyRoot)) //if node belongs to enemy
				{
					//if enemy can win this node then is not safe
					if(willItWin(graph.map[available[i]].numberOfCircles(), graph.map[available[i]].numberOfSquares(), graph.map[available[i]].numberOfTriangles(),
							 	 graph.map[nn].numberOfCircles(),           graph.map[nn].numberOfSquares(), 		   graph.map[nn].numberOfTriangles())>0)
						safe=false;
				}
			}
		}
		return safe;
	}
	
	private int whoseNodeIsIt(int nn)
	{
		return graph.map[nn].belongs();
	}
	
	private int levelOf(int nn)
	{
		return rootLevels[nn];
	}
	
	private boolean isLevelMine(int level)
	{
		boolean flag=true;
		if(level>endLevel) flag=false;
		else for(int i=0; i<totalNodesNumber; i++) if(levelOf(i)==level && whoseNodeIsIt(i)!=whoseNodeIsIt(myRoot)) flag=false;
		return flag;
	}
	
	private class resultOfBFS //a weird class for multiple output from levelsBFS
	{
		int[] levels = new int [totalNodesNumber];
		int distance;
		
		resultOfBFS(int d, int[] a)
		{
			distance = d;
			levels = a.clone();
		}
	}

	private resultOfBFS levelsBFS(int rootOfSearch, int targetOfSearch)
	{
		//BFS variables
		int searchingQueue[] = new int [totalNodesNumber]; //array that will serve the purpose of a queue (FIFO)
		Arrays.fill(searchingQueue, -1); //filling array with -1 because 0 is a number of a node
		int visited[] = new int [totalNodesNumber]; //array for visited nodes (FINO)
		Arrays.fill(visited, -1); //because one of the nodes is zero
		int endOfQueue=0; //index of the end of the queue
		int searchingSteps=0; //loop counter of the search
		
		//variables for measuring depth
		int level[] = new int[totalNodesNumber]; //array of levels with reference to the root
		Arrays.fill(level, -1); //zero level is reserved for root
		level[rootOfSearch]=0; //setting root level as zero
		
		
		searchingQueue[0]=rootOfSearch; //set root as a first in queue
		while(searchingQueue[0]!=targetOfSearch && searchingQueue[0]!=-1) //as long as target is not the first in queue
		{
			searchingSteps++; //one more step
			for(int i=0; i<5 && graph.map[searchingQueue[0]].adjacentNodes[i]>-1; i++) //get numbers of adjacent nodes for the first one in queue
			{
				int nextNeighbour=graph.map[searchingQueue[0]].adjacentNodes[i]; //storing the variable as it is going to be called many times
				boolean notVisited=true;
				for(int v=0; v<totalNodesNumber; v++) //loop through the visited table
				{
					if(visited[v]==nextNeighbour) notVisited=false; //if adjacent node was already visited
				}
				boolean notInQueue=true;
				for(int q=0; q<totalNodesNumber; q++) //loop through the queue
				{
					if(searchingQueue[q]==nextNeighbour) notInQueue=false; //if adjacent node is already in queue
				}
				if(notVisited && notInQueue) //if adjacent node wasn't visited and it is not in queue already
				{
					searchingQueue[endOfQueue+1]=nextNeighbour; //add adjacent node to the end of the queue
					endOfQueue++; //move the end of the queue one step back
					for(int a=0; a<5 && graph.map[nextNeighbour].adjacentNodes[a]>-1 ; a++) //adjacent nodes of the node you have just queued
					{
						if(level[graph.map[nextNeighbour].adjacentNodes[a]]>level[nextNeighbour]) //if one of the previous levels is bigger the your current
						{
							level[nextNeighbour]=level[graph.map[nextNeighbour].adjacentNodes[a]]+1; //set one level higher than the lowest found in the neigbourhood
						}
					}
				}
				//DEBUG
//				System.out.print(nextNeighbour+"  ");
			}
			//DEBUG
//			System.out.print("\nQueue:"); for(int i=0; i<14; i++) System.out.print(searchingQueue[i]);
//			System.out.print(" EOQ="+endOfQueue);
//			System.out.print("\nVisited:"); for(int i=0; i<14; i++) System.out.print(visited[i]);
//			System.out.print("\nLevel:"); for(int i=0; i<14; i++) System.out.println(i+"="+level[i]);
//			System.out.println("");
			
			visited[searchingSteps]=searchingQueue[0]; //mark visited
			for(int i=0; i<totalNodesNumber-1; i++) //move the queue by one
			{
				searchingQueue[i]=searchingQueue[i+1]; //if made to keep index within the array boundaries
			}
			endOfQueue--; //after moving the whole queue its end indicator has to be moved as well
		}//end of while
		
		resultOfBFS result;
		if(searchingQueue[0]==-1) result = new resultOfBFS(-1,level); //if the target was not found return -1
		result = new resultOfBFS(level[targetOfSearch],level);
		return result; //return level of the target and the complete level table
	}
}

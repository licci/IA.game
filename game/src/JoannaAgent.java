import java.util.ArrayList;
import java.util.List;


public class JoannaAgent implements agent{

	private int myAgentId;
	
	private int endNode;
	
	public static agentActions action = new agentActions();
	
	public boolean checkIfBattleEnded(int attackerId, int startNode, int targetNode, int attackingSquares, int attackingCircles, int attackingTriangles, int defendingSquares, int defendingCircles, int defendingTriangles) 
	{
		if (defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0 & attackingSquares == 0 & attackingCircles == 0 & attackingTriangles == 0) {
			return true;
		}

		else if (defendingSquares == 0 & defendingCircles == 0 & defendingTriangles == 0) 
		{
			return true;
		}

		else if (attackingSquares == 0 & attackingCircles == 0 & attackingTriangles == 0)
		{
			return true;
		}
		return false;
	}
	
	public ArrayList <ArrayList<Integer>> unitsBilans(int playerId, int startNode, int targetNode, int squares, int circles, int triangles) 
	{
			ArrayList <ArrayList<Integer>> UnitsBilans = new ArrayList <ArrayList<Integer>>();
			
			ArrayList<Integer> MyUnitsLeft = new ArrayList<Integer>(); //my units after the battle (squares, circles, triangles)
			ArrayList<Integer> OponentUnitsLeft = new ArrayList<Integer>(); //opponent units after the battle (squares, circles, triangles)
			
			UnitsBilans.add(MyUnitsLeft);
			UnitsBilans.add(OponentUnitsLeft);
		
			int defendingSquares = graph.map[targetNode].numberOfSquares();
			int defendingCircles = graph.map[targetNode].numberOfCircles();
			int defendingTriangles = graph.map[targetNode].numberOfTriangles();

			int special = graph.map[targetNode].getSpecial();

			// 1st stage of fight
			// units fight with units of the same type - inflicting normal casualties
			if (defendingSquares != 0) 
			{
				if (squares > (int) (defendingSquares * special)) 
				{
					squares -= (int) (defendingSquares * special);
					defendingSquares = 0;
				}
				else 
				{
					defendingSquares -= (int) squares / special;
					squares = 0;
				}
			}

			// same reasoning for circles
			if (defendingCircles != 0) 
			{
				if (circles > (int) (defendingCircles * special)) 
				{
					circles -= (int) (defendingCircles * special);
					defendingCircles = 0;
				} 
				else 
				{
					defendingCircles -= (int) circles / special;
					circles = 0;
				}
			}

			// and triangles
			if (defendingTriangles != 0) 
			{
				if (triangles > (int) (defendingTriangles * special)) 
				{
					triangles -= (int) (defendingTriangles * special);
					defendingTriangles = 0;
				} 
				else 
				{
					defendingTriangles -= (int) triangles / special;
					triangles = 0;
				}
			}
			
			//after 1st stage
			MyUnitsLeft.add(0, squares);
			MyUnitsLeft.add(1, circles);
			MyUnitsLeft.add(2, triangles);
			
			//System.out.println("My units after 1st stage: " + MyUnitsLeft);
			
			OponentUnitsLeft.add(0, defendingSquares);
			OponentUnitsLeft.add(1, defendingCircles);
			OponentUnitsLeft.add(2, defendingTriangles);
			
			//System.out.println("Opponent units after 1st stage: " + OponentUnitsLeft);

			// check if fight has ended
			if (checkIfBattleEnded(playerId, startNode, targetNode, squares, circles, triangles, defendingSquares, defendingCircles, defendingTriangles))
				return UnitsBilans;

			// 2nd stage
			// squares vs. triangles - inflicting biggest casualties
			// if there are more squares than weaker enemies all enemies die
			if (defendingTriangles != 0) 
			{
				if (squares > (int) (0.5 * defendingTriangles * special)) 
				{
					squares -= (int) (0.5 * defendingTriangles * special);
					defendingTriangles = 0;
				}
				else 
				{
					defendingTriangles -= (int) 2 * squares / special;
					squares = 0;
				}
				
			}

			// same reasoning for circles
			if (defendingSquares != 0) 
			{
				if (circles > (int) (0.5 * defendingSquares * special)) 
				{
					circles -= (int) (0.5 * defendingSquares * special);
					defendingSquares = 0;
				} 
				else 
				{
					defendingSquares -= (int) 2 * circles / special;
					circles = 0;
				}
			}

			// and triangles
			if (defendingCircles != 0) 
			{
				if (triangles > (int) (0.5 * defendingCircles * special)) 
				{
					triangles -= (int) (0.5 * defendingCircles * special);
					defendingCircles = 0;
				} 
				else 
				{
					defendingCircles -= (int) 2 * triangles / special;
					triangles = 0;
				}
			}

			//after 2nd stage
			MyUnitsLeft.clear();
			MyUnitsLeft.add(0, squares);
			MyUnitsLeft.add(1, circles);
			MyUnitsLeft.add(2, triangles);
			
			//System.out.println("My units after 2nd stage: " + MyUnitsLeft);
			
			OponentUnitsLeft.clear();
			OponentUnitsLeft.add(0, defendingSquares);
			OponentUnitsLeft.add(1, defendingCircles);
			OponentUnitsLeft.add(2, defendingTriangles);
			
			//System.out.println("Opponent units after 2nd stage: " + OponentUnitsLeft);
			
			// check if fight has ended
			if (checkIfBattleEnded(playerId, startNode, targetNode, squares, circles, triangles, defendingSquares, defendingCircles, defendingTriangles))
				return UnitsBilans;

			// 3rd stage - last step
			if (defendingCircles != 0) 
			{
				if (squares > (int) (2 * defendingCircles * special)) 
				{
					squares -= (int) (2 * defendingCircles * special);
					defendingCircles = 0;
				}
				else 
				{
					defendingCircles -= (int) 0.5 * squares / special;
					squares = 0;
				}
			}

			// same reasoning for circles
			if (defendingTriangles != 0) {
				if (circles > (int) (2 * defendingTriangles * special)) {
					circles -= (int) (2 * defendingTriangles * special);
					defendingTriangles = 0;
				} else {
					defendingTriangles -= (int) 0.5 * circles
							/ special;
					circles = 0;
				}
			}

			// and triangles
			if (defendingSquares != 0) {
				if (triangles > (int) (2 * defendingSquares * special)) {
					triangles -= (int) (2 * defendingSquares * special);
					defendingSquares = 0;
				} else {
					defendingSquares -= (int) 0.5 * triangles
							/ special;
					triangles = 0;
				}
			}
			
			//after 3rd stage
			MyUnitsLeft.clear();
			MyUnitsLeft.add(0, squares);
			MyUnitsLeft.add(1, circles);
			MyUnitsLeft.add(2, triangles);
			
			//System.out.println("My units after 3rd stage: " + MyUnitsLeft);
			
			OponentUnitsLeft.clear();
			OponentUnitsLeft.add(0, defendingSquares);
			OponentUnitsLeft.add(1, defendingCircles);
			OponentUnitsLeft.add(2, defendingTriangles);
			
			//System.out.println("Opponent units after 3rd stage: " + OponentUnitsLeft);
			
			return UnitsBilans;
	}
	
	public List<Integer> GetAllYourNodes()
	{
		List<Integer> myNodes = new ArrayList<Integer>();

		for (int x = 0; x < 14; x++) {

			if (graph.map[x].belongs() == myAgentId) 
			{ 
				myNodes.add(x);
			}
		}
		//System.out.println("My nodes: " + myNodes);
		return myNodes;
	}
	
	public ArrayList <ArrayList<Integer>> GetShortestPathForAllMyNodes(List<Integer> myNodes)
	{
		ArrayList <ArrayList<Integer>> ShortestPathsForAllOfMyNodes = new ArrayList <ArrayList<Integer>>();
		
		for (int x=0; x<myNodes.size(); x++)
		{
			ArrayList<Integer> Path = new ArrayList<Integer>();
			
			//System.out.println("Shortest path for Node = " + myNodes.get(x));
			
			BFSPaths bfs = new BFSPaths(myNodes.get(x));
			
			for (int currentNode : bfs.getPathTo(endNode)) 
			{
				Path.add(currentNode);
				System.out.print(currentNode + " ");	
			}
			ShortestPathsForAllOfMyNodes.add(Path);
		}
		return ShortestPathsForAllOfMyNodes;
	}
	
	public void ChooseTheBestPath(ArrayList <ArrayList<Integer>> ShortestPathsForAllOfMyNodes, int sizeOfTheShortestPath)
	{
		ArrayList<Integer> shortestPath = new ArrayList<Integer>();
		
		int pathIndex = 0; //in the case of remis or loss to know which path to remove
		
		//System.out.println("Choosing shortest path from " + ShortestPathsForAllOfMyNodes.size() + " paths.");
		
		for (int x=0; x < ShortestPathsForAllOfMyNodes.size(); x++)
		{
			//System.out.println(sizeOfTheShortestPath + " vs." + ShortestPathsForAllOfMyNodes.get(x).size() + "elements");
			if (sizeOfTheShortestPath > ShortestPathsForAllOfMyNodes.get(x).size())
			{
				sizeOfTheShortestPath = ShortestPathsForAllOfMyNodes.get(x).size();
				
				//System.out.println(sizeOfTheShortestPath + " is shortest");
				
				shortestPath = ShortestPathsForAllOfMyNodes.get(x);
				
				//System.out.println("Looks like " + shortestPath);
				
				pathIndex = x;
			}
		}
		
		if (shortestPath.size() == 0)
		{
				for (int y=0; y < ShortestPathsForAllOfMyNodes.size(); y++)
				{
					if (ShortestPathsForAllOfMyNodes.get(y).size() == sizeOfTheShortestPath)
					{
						shortestPath = ShortestPathsForAllOfMyNodes.get(y);
					}
				}
		}
		
		int startingNode = shortestPath.get(0);
		int secondNode = shortestPath.get(1);
		
		//System.out.println("Starting node: " + startingNode + " Second node: " + secondNode);
		
		int mySquares = graph.map[startingNode].numberOfSquares();
		int myCircles = graph.map[startingNode].numberOfCircles();
		int myTriangles = graph.map[startingNode].numberOfTriangles();
		
		//System.out.println("My squares: " + mySquares + " My circles: " + myCircles + " My triangles: " + myTriangles);
		
		//******if the node is mine, I am moving to it
		if (graph.map[secondNode].belongsTo == myAgentId)
		{
			//System.out.println("Node is mine. I am not moving any units.");
			action.move(myAgentId, startingNode, secondNode, 0, 0, 0);
		}
		//******if it doesn't belong to anyone
		else if (graph.map[secondNode].belongsTo == 0)
		{
			//System.out.println("Node doesn't belong to anyone.");
			
			ArrayList <ArrayList<Integer>> UnitsBilans = unitsBilans(myAgentId, startingNode, secondNode, mySquares, myCircles, myTriangles);
			ArrayList<Integer> MyUnitsLeft = UnitsBilans.get(0);
			ArrayList<Integer> EmptyNodeUnitsLeft = UnitsBilans.get(1);
			
			//******if after the battle he has no units left
			if (EmptyNodeUnitsLeft.get(0) == 0 && EmptyNodeUnitsLeft.get(1) == 0 && EmptyNodeUnitsLeft.get(2) == 0)
			{
				//******and I have some left - I attack
				if (MyUnitsLeft.get(0) != 0 || MyUnitsLeft.get(1) != 0 || MyUnitsLeft.get(2) != 0) //moja wygrana
				{
					//System.out.println("I'll get this node because:");
					//System.out.println("Mine: " + MyUnitsLeft.get(0) + ", " + MyUnitsLeft.get(1) + ", " + MyUnitsLeft.get(2));
					//System.out.println("Empty node's: " + EmptyNodeUnitsLeft.get(0) + ", " + EmptyNodeUnitsLeft.get(1) + ", " + EmptyNodeUnitsLeft.get(2));
					
					//System.out.println("That is why I am sending: " + mySquares + ", " + myCircles + ", " + myTriangles + " from node: " + startingNode + " to node: " + secondNode);
					
					action.attack(myAgentId, startingNode, secondNode, mySquares, myCircles, myTriangles);
					
					//System.out.println("Mine: " + graph.map[startingNode].squares + ", " + graph.map[startingNode].circles + ", " + graph.map[startingNode].triangles);
					//System.out.println("Empty node's: " + graph.map[secondNode].squares + ", " + graph.map[secondNode].circles + ", " + graph.map[secondNode].triangles);
				}
				//******if both of us will have 0
				else //remis
				{
					//System.out.println("It will be remis because:");
					//System.out.println("Mine: " + MyUnitsLeft.get(0) + ", " + MyUnitsLeft.get(1) + ", " + MyUnitsLeft.get(2));
					//System.out.println("Empty node's: " + EmptyNodeUnitsLeft.get(0) + ", " + EmptyNodeUnitsLeft.get(1) + ", " + EmptyNodeUnitsLeft.get(2));
					
					//******if I have already checked all paths - I wait
					if (ShortestPathsForAllOfMyNodes.size() == 1)
					{
						//System.out.println("I will wait as there are no reasonable actions to take.");
					}
					//******if no - I am checking next path
					else
					{
						ShortestPathsForAllOfMyNodes.remove(pathIndex);
						
						//System.out.println("That is why I don't need path no: " + pathIndex);
						
						ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
					}
				}
			}
			//******if after the battle, he will have units left
			else
			{
				//System.out.println("I won't get this node because:");
				//System.out.println("Mine: " + MyUnitsLeft.get(0) + ", " + MyUnitsLeft.get(1) + ", " + MyUnitsLeft.get(2));
				//System.out.println("Empty node's: " + EmptyNodeUnitsLeft.get(0) + ", " + EmptyNodeUnitsLeft.get(1) + ", " + EmptyNodeUnitsLeft.get(2));
				//System.out.println("That's why I will look for my neighbors");
				
				//******I check if I have neighbors
				ArrayList<Integer> availableNodes = new ArrayList<Integer>();
				for (int i=0; i<5; i++)
				{
					int adjecentNodeId = graph.map[startingNode].adjacentNodes[i];
					if (graph.map[startingNode].adjacentNodes[i] != -1 && graph.map[adjecentNodeId].belongs() == myAgentId)
					{
						availableNodes.add(graph.map[startingNode].adjacentNodes[i]);
					}
				}
				//System.out.println("Neighbors for " + startingNode + " are " + availableNodes);
				
				//******if I have neighbors
				if(availableNodes.size() > 0)
				{
					//System.out.println("I have " + availableNodes.size() + " neighbors.");
					
					boolean actionPerformed = false;
					
					for (int i=0; i<availableNodes.size(); i++)
					{
						int neighbourSquares = graph.map[availableNodes.get(i)].squares;
						int neighbourCircles = graph.map[availableNodes.get(i)].circles;
						int neighbourTriangles = graph.map[availableNodes.get(i)].triangles;
						
						//System.out.println("Neighbors: " + neighbourSquares + ", " + neighbourCircles + ", " + neighbourTriangles);
						
						//******and the neighbor has more units than the empty node after the battle - I am moving the neighbor to my node
						if (neighbourSquares >= EmptyNodeUnitsLeft.get(0) && neighbourCircles >= EmptyNodeUnitsLeft.get(1) && neighbourTriangles >= EmptyNodeUnitsLeft.get(2))
						{
							//System.out.println("Neighbor has more units than the empty node after the battle, so he can move them to me");
							
							action.move(myAgentId, availableNodes.get(i), startingNode, neighbourSquares, neighbourCircles, neighbourTriangles);
							
							//System.out.println("Neighbor is moving to me: " + neighbourSquares + ", " + neighbourCircles + ", " + neighbourTriangles + " from node: " + availableNodes.get(i) + " to node: " + startingNode);
							
							actionPerformed = true;
							break;
						}
					}
					
					//******if the neighbor doesn't have enough units to help me
					if (!actionPerformed)
					{
						//System.out.println("Neighbors don't have enough units to help me.");

						//******if I have already checked all paths - I wait
						if (ShortestPathsForAllOfMyNodes.size() == 1)
						{
							//System.out.println("I will wait as there are no reasonable actions to take.");
							return;
						}
						else
						{
							//******if no - I am checking next path
							ShortestPathsForAllOfMyNodes.remove(pathIndex);
							
							//System.out.println("That is why I don't need path no: " + pathIndex);
							
							ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
						}
					}
				}
				//******if I don't have neighbors
				else
				{
					//System.out.println("I don't have any neighbours.");
					
					//******if I have already checked all paths - I wait
					if (ShortestPathsForAllOfMyNodes.size() == 1)
					{
						//System.out.println("I will wait as there are no reasonable actions to take.");
						return;
					}
					//******if no - I am checking next path
					else
					{
						ShortestPathsForAllOfMyNodes.remove(pathIndex);
						
						//System.out.println("That is why I don't need path no: " + pathIndex);
						
						ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
					}
				}
			}
			//System.out.print("Shortest path starts at node " + startingNode + " and looks like: " + shortestPath);
		}
		//******if it belongs to the opponent
		else
		{
			ArrayList <ArrayList<Integer>> UnitsBilans = unitsBilans(myAgentId, startingNode, secondNode, mySquares, myCircles, myTriangles);
			ArrayList<Integer> MyUnitsLeft = UnitsBilans.get(0);
			ArrayList<Integer> OponentUnitsLeft = UnitsBilans.get(1);
			
			//******if after the battle he has no units left
			if (OponentUnitsLeft.get(0) == 0 && OponentUnitsLeft.get(1) == 0 && OponentUnitsLeft.get(2) == 0)
			{
				//and I have some left - I attack
				if (MyUnitsLeft.get(0) != 0 || MyUnitsLeft.get(1) != 0 || MyUnitsLeft.get(2) != 0) //moja wygrana
				{
					//System.out.println("I'll win because:");
					//System.out.println("Mine: " + MyUnitsLeft.get(0) + ", " + MyUnitsLeft.get(1) + ", " + MyUnitsLeft.get(2));
					//System.out.println("His: " + OponentUnitsLeft.get(0) + ", " + OponentUnitsLeft.get(1) + ", " + OponentUnitsLeft.get(2));
					
					//System.out.println("That is why I am sending: " + mySquares + ", " + myCircles + ", " + myTriangles + " from node: " + startingNode + " to node: " + secondNode);
					
					action.attack(myAgentId, startingNode, secondNode, mySquares, myCircles, myTriangles);
				}
				//******if I won't have any unit left too - remis
				else //remis
				{
					//System.out.println("It will be remis because:");
					//System.out.println("Mine: " + MyUnitsLeft.get(0) + ", " + MyUnitsLeft.get(1) + ", " + MyUnitsLeft.get(2));
					//System.out.println("His: " + OponentUnitsLeft.get(0) + ", " + OponentUnitsLeft.get(1) + ", " + OponentUnitsLeft.get(2));
					
					//******if I have already checked all paths - I wait
					if (ShortestPathsForAllOfMyNodes.size() == 1)
					{
						//System.out.println("I will wait as there are no reasonable actions to take.");
						return;
					}
					//******if no - I am checking next path
					else
					{
						ShortestPathsForAllOfMyNodes.remove(pathIndex);
						
						//System.out.println("That is why I don't need path no: " + pathIndex);
						
						ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
					}
				}
			}
			//******if after the battle, he will have units left
			else
			{
				//System.out.println("I'll loose because:");
				//System.out.println("Mine: " + MyUnitsLeft.get(0) + ", " + MyUnitsLeft.get(1) + ", " + MyUnitsLeft.get(2));
				//System.out.println("His: " + OponentUnitsLeft.get(0) + ", " + OponentUnitsLeft.get(1) + ", " + OponentUnitsLeft.get(2));
				//System.out.println("That's why I will look for my neighbors");
				
				//I check if I have mu neighbors
				ArrayList<Integer> availableNodes = new ArrayList<Integer>();
				for (int i=0; i<5; i++)
				{
					int adjecentNodeId = graph.map[startingNode].adjacentNodes[i];
					if (graph.map[startingNode].adjacentNodes[i] != -1 && graph.map[adjecentNodeId].belongs() == myAgentId)
					{
						availableNodes.add(graph.map[startingNode].adjacentNodes[i]);
					}
				}
				//System.out.println("Neighbors for " + startingNode + " are " + availableNodes);
				
				//******if I have neighbors
				if(availableNodes.size() > 0)
				{
					//System.out.println("I have " + availableNodes.size() + " neighbors.");
					
					boolean actionPerformed = false;
					
					for (int i=0; i<availableNodes.size(); i++)
					{
						int neighbourSquares = graph.map[availableNodes.get(i)].squares;
						int neighbourCircles = graph.map[availableNodes.get(i)].circles;
						int neighbourTriangles = graph.map[availableNodes.get(i)].triangles;
						
						//System.out.println("Neighbors: " + neighbourSquares + ", " + neighbourCircles + ", " + neighbourTriangles);
						
						//******and the neighbor has more units than the empty node after the battle - I am moving the neighbor to my node
						if (neighbourSquares >= OponentUnitsLeft.get(0) && neighbourCircles >= OponentUnitsLeft.get(1) && neighbourTriangles >= OponentUnitsLeft.get(2))
						{
							//System.out.println("Neighbor has more units than the opponent after the battle, so he can move them to me");
							
							action.move(myAgentId, availableNodes.get(i), startingNode, neighbourSquares, neighbourCircles, neighbourTriangles);
							
							//System.out.println("Neighbor is moving to me: " + neighbourSquares + ", " + neighbourCircles + ", " + neighbourTriangles + " from node: " + availableNodes.get(i) + " to node: " + startingNode);
							
							actionPerformed = true;
							break;
						}
					}
					
					//******if the neighbor doesn't have enough units to help me
					if (!actionPerformed)
					{
						//System.out.println("Neighbors don't have enough units to help me.");
						
						//******if I have already checked all paths - I wait
						if (ShortestPathsForAllOfMyNodes.size() == 1)
						{
							//System.out.println("I will wait as there are no reasonable actions to take.");
							return;
						}
						//******if no - I am checking next path
						else
						{
							ShortestPathsForAllOfMyNodes.remove(pathIndex);
							
							//System.out.println("That is why I don't need path no: " + pathIndex);
							
							ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
						}
					}
				}
				//******if I don't have neighbors
				else
				{
					//System.out.println("I don't have any neighbors.");
					//******if I have already checked all paths - I wait
					if (ShortestPathsForAllOfMyNodes.size() == 1)
					{
						//System.out.println("I will wait as there are no reasonable actions to take.");
						return;
					}
					//******if no - I am checking next path
					else
					{
						ShortestPathsForAllOfMyNodes.remove(pathIndex);
						
						//System.out.println("That is why I don't need path no: " + pathIndex);
						
						ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
					}
				}
			}
			//System.out.print("Shortest path starts at node " + startingNode + " and looks like: " + shortestPath);
		}
	}
	
	
	@Override
	public void performAction(int currentAgent) {
		// TODO Auto-generated method stub
		this.myAgentId = currentAgent;
		
		if (myAgentId == 1)
		{
			this.endNode = 13;
		}
		else
		{
			this.endNode = 0;
		}

		List<Integer> myNodes = GetAllYourNodes();
		
		if (myNodes.size() > 0)
		{
			ArrayList <ArrayList<Integer>> ShortestPathsForAllOfMyNodes = GetShortestPathForAllMyNodes(myNodes);
			
			ChooseTheBestPath(ShortestPathsForAllOfMyNodes, 100);
		}
		else
		{
			//System.out.println("I don't have any node.");
			return;
		}
	}

}

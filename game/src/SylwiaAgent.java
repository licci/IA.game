import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SylwiaAgent implements agent {

	int agentId = 0;

	@Override
	public void performAction(int agentId) {

		this.agentId = agentId;

		int startNode = -1;
		int targetNode = -1;
		int bestCost = 4000;
		List<Integer> chosenPath = null;
		agentActions action = new agentActions();
		Random coinToss = new Random();
		int tossResult = -1;

		/*
		 * int[][] paths = new int[][] { { 10, 10, 10, 10, 10, 10, 10, 10, 10,
		 * 10, 10, 10, 10, 10 }, { 4000, 4000, 4000, 4000, 4000, 4000, 4000,
		 * 4000, 4000, 4000, 4000, 4000, 4000, 4000 } };
		 */

		if (agentId == 1)
			targetNode = 13;
		else if (agentId == 2)
			targetNode = 0;

		for (int x = 0; x < 14; x++) {

			if (graph.map[x].belongs() == agentId) {
				List<Integer> path = findPath(x, targetNode);
				int totalCost = getCost(x, path.get(0))
						+ getEstimatedCost(path.get(0), targetNode);
				System.out.println("Total cost: from " + x + " to "+ path.get(0)+" and from this to end = " + totalCost);
				if (totalCost < bestCost) {
					bestCost = totalCost;
					startNode = x;
					chosenPath = path;
				} else if (totalCost == bestCost) {
					// System.out.println("paths[0]["+x+"] ="+paths[0][x]+" & paths[0]["+startNode+"] ="+paths[0][startNode]);
					if (path.size() < chosenPath.size()) {
						startNode = x;
						chosenPath = path;
					} else {
						tossResult = coinToss.nextInt(2);
						if (tossResult == 1) {
							startNode = x;
							chosenPath = path;
						}
					}
				}

			}
		}

		int[] neededUnits = { 0, 0, 0 };
		neededUnits = checkAttackPossibility(startNode, chosenPath.get(0));
		
		if (neededUnits[0] <= graph.map[startNode].numberOfSquares()
				&& neededUnits[1] <= graph.map[startNode].numberOfCircles()
				&& neededUnits[2] <= graph.map[startNode].numberOfTriangles()) {
			System.out.println(agentId
					+ " - attack possible - attack from node " + startNode
					+ " to node " + chosenPath.get(0));
			
			System.out.println("Cost: " + neededUnits[0] + " "
					+ neededUnits[1] + " " + neededUnits[2]);
			action.attack(
					agentId,
					startNode,
					chosenPath.get(0),
					neededUnits[0]
							+ (int)((graph.map[startNode].numberOfSquares() - neededUnits[0])/ 1.3),
					neededUnits[1]
							+ (int)((graph.map[startNode].numberOfCircles() - neededUnits[1])/ 1.3),
					neededUnits[2]
							+ (int)((graph.map[startNode].numberOfTriangles() - neededUnits[2])/ 1.3));
			System.out.println("IN CONQUERED NODE: "
					+ graph.map[chosenPath.get(0)].numberOfSquares() + " squares, "
					+ graph.map[chosenPath.get(0)].numberOfCircles() + " circles, "
					+ graph.map[chosenPath.get(0)].numberOfTriangles() + " triangles.");

		}

		else if (pathParent[startNode] != -1) {
			System.out.println(agentId + " - attack impossible  - move units");
			action.move(agentId, pathParent[startNode], startNode,
					(int)(graph.map[pathParent[startNode]].numberOfSquares()/1.5),
					(int)(graph.map[pathParent[startNode]].numberOfCircles()/1.5),
					(int)(graph.map[pathParent[startNode]].numberOfTriangles()/1.5));
		}
	}

	int pathParentSearch[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1 };
	int pathParent[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	int costFromStart[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	int estimatedCostToGoal[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	// BFS first shortest path
	public List<Integer> search(int startNode, int goalNode) {
		// list of visited nodes
		LinkedList<Integer> closedList = new LinkedList<Integer>();
		List<Integer> neighbors = new ArrayList<Integer>();

		// list of nodes to visit (sorted)
		LinkedList<Integer> openList = new LinkedList<Integer>();
		openList.add(startNode);
		pathParentSearch[startNode] = -1;

		while (!openList.isEmpty()) {
			int node = openList.removeFirst();
			if (node == goalNode) {
				// path found!
				// System.out.println("Path found!");
				return constructSearchPath(goalNode);
			} else {
				closedList.add(node);

				// add neighbors to the open list

				for (int i = 0; i < graph.map[node].availableAdjacentNodes.length; i++) {
					if (graph.map[node].availableAdjacentNodes[i] > -1) {
						/*
						 * System.out.println("SEARCH NODE - " + node +
						 * " neighbor: " +
						 * graph.map[node].availableAdjacentNodes[i]);
						 */
						neighbors
								.add(graph.map[node].availableAdjacentNodes[i]);
					}
				}

				Iterator<Integer> i = neighbors.iterator();
				while (i.hasNext()) {
					int neighborNode = i.next();
					if (!closedList.contains(neighborNode)
							&& !openList.contains(neighborNode)) {
						pathParentSearch[neighborNode] = node;
						openList.add(neighborNode);
					}
				}
			}
		}

		// no path found
		// System.out.println("Sorry no path");
		return null;
	}

	protected List<Integer> constructSearchPath(int node) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		while (pathParentSearch[node] != (int) -1) {
			path.addFirst(node);
			node = pathParentSearch[node];
			// System.out.println("Element of constructSearchPath - " + node
			// + " and its parent: " + pathParentSearch[node]);
		}
		return path;
	}

	protected List<Integer> constructPath(int node) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		while (pathParent[node] >= 0) {
			path.addFirst(node);
			node = pathParent[node];
			// System.out.println("Element of constuctPath - " + node
			// + " and its parent: " + pathParent[node]);
		}

		return path;
	}

	/*
	 * public int compareTo(int node1, int node2) { int thisValue =
	 * getCost(node1); int otherValue = getCost(node2);
	 * 
	 * int v = thisValue - otherValue; return (v > 0) ? 1 : (v < 0) ? -1 : 0; //
	 * sign function }
	 */

	/*
	 * //** Gets the cost between this node and the specified adjacent (AKA
	 * "neighbor" or "child") node.
	 */
	private int getCost(int node1, int node2) {
		int cost = 0;
		if (getNeighbors(node1).contains(node2)) {
			if (graph.map[node1].belongs() == agentId
					&& graph.map[node2].belongs() == agentId) {
				// System.out.println("Punkt1 "+cost);
				return cost=3000;
			}

			else if (graph.map[node1].belongs() == agentId) {
				int units[] = checkAttackPossibility(node1, node2);
				cost = units[0] + units[1] + units[2];
				// System.out.println("Punkt2 "+cost);
				return cost;
			} else {// System.out.println("Punkt3 ");
				return getEstimatedCost(node1, node2);
			}
		}

		// System.out.println("Punkt4 "+cost);
		return getEstimatedCost(node1, node2);
	}

	private int[] checkAttackPossibility(int startNode, int targetNode) {

		int[] units = { 0, 0, 0 };

		int special = graph.map[targetNode].special;

		int attackingSquares = graph.map[startNode].numberOfSquares();
		int attackingCircles = graph.map[startNode].numberOfCircles();
		int attackingTriangles = graph.map[startNode].numberOfTriangles();

		int allAttackingSquares = graph.map[startNode].numberOfSquares();
		int allAttackingCircles = graph.map[startNode].numberOfCircles();
		int allAttackingTriangles = graph.map[startNode].numberOfTriangles();

		// defenders
		int defendingSquares = graph.map[targetNode].numberOfSquares();
		int defendingCircles = graph.map[targetNode].numberOfCircles();
		int defendingTriangles = graph.map[targetNode].numberOfTriangles();

		if (defendingSquares != 0) {
			if (attackingSquares > (int) (defendingSquares * special)) {
				attackingSquares -= (int) (defendingSquares * special);
				units[0] = (int) (defendingSquares * special);
				defendingSquares = 0;
			}
			// otherwise all squares die, inflicting higher casualties upon
			// enemy
			else {
				defendingSquares -= (int) attackingSquares / special;
				attackingSquares = 0;
				units[0] = allAttackingSquares;
			}
		}

		// same reasoning for circles
		if (defendingCircles != 0) {
			if (attackingCircles > (int) (defendingCircles * special)) {
				attackingCircles -= (int) (defendingCircles * special);
				units[1] = (int) (defendingCircles * special);
				defendingCircles = 0;
			} else {
				defendingCircles -= (int) attackingCircles / special;
				attackingCircles = 0;
				units[1] = allAttackingCircles;
			}
		}

		// and triangles
		if (defendingTriangles != 0) {
			if (attackingTriangles > (int) (defendingTriangles * special)) {
				attackingTriangles -= (int) (defendingTriangles * special);
				units[2] = (int) (defendingTriangles * special);
				defendingTriangles = 0;
			} else {
				defendingTriangles -= (int) attackingTriangles / special;
				attackingTriangles = 0;
				units[2] = allAttackingTriangles;
			}
		}

		// check if fight has ended
		// if(battleEnded
		// (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles))
		// return true;

		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 2nd stage
		// squares vs. triangles - inflicting biggest casualties
		// if there are more squares that weaker enemies all enemies die
		if (defendingTriangles != 0) {
			if (attackingSquares > (int) (0.5 * defendingTriangles * special)) {
				attackingSquares -= (int) (0.5 * defendingTriangles * special);
				units[0] += (int) (0.5 * defendingTriangles * special);
				defendingTriangles = 0;
			}
			// otherwise all squares die, inflicting higher casualties upon
			// enemy
			else {
				defendingTriangles -= (int) 2 * attackingSquares / special;
				attackingSquares = 0;
				units[0] = allAttackingSquares;
			}
		}

		// same reasoning for circles
		if (defendingSquares != 0) {
			if (attackingCircles > (int) (0.5 * defendingSquares * special)) {
				attackingCircles -= (int) (0.5 * defendingSquares * special);
				units[1] += (int) (0.5 * defendingSquares * special);
				defendingSquares = 0;
			} else {
				defendingSquares -= (int) 2 * attackingCircles / special;
				attackingCircles = 0;
				units[1] = allAttackingCircles;
			}
		}

		// and triangles
		if (defendingCircles != 0) {
			if (attackingTriangles > (int) (0.5 * defendingCircles * special)) {
				attackingTriangles -= (int) (0.5 * defendingCircles * special);
				units[2] += (int) (0.5 * defendingCircles * special);
				defendingCircles = 0;
			} else {
				defendingCircles -= (int) 2 * attackingTriangles / special;
				attackingTriangles = 0;
				units[2] = allAttackingTriangles;
			}
		}

		// check if fight has ended
		// if(battleEnded
		// (attackerId,startNode,targetNode,attackingSquares,attackingCircles,attackingTriangles,defendingSquares,defendingCircles,defendingTriangles))
		// return true;
		// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 3rd stage - last step
		if (defendingCircles != 0) {
			if (attackingSquares > (int) (2 * defendingCircles * special)) {
				attackingSquares -= (int) (2 * defendingCircles * special);
				units[0] += (int) (2 * defendingCircles * special);
				defendingCircles = 0;
			}
			// otherwise all squares die, inflicting higher casualties upon
			// enemy
			else {
				defendingCircles -= (int) 0.5 * attackingSquares / special;
				attackingSquares = 0;
				units[0] = allAttackingSquares;
			}
		}

		// same reasoning for circles
		if (defendingTriangles != 0) {
			if (attackingCircles > (int) (2 * defendingTriangles * special)) {
				attackingCircles -= (int) (2 * defendingTriangles * special);
				units[1] += (int) (2 * defendingTriangles * special);
				defendingTriangles = 0;
			} else {
				defendingTriangles -= (int) 0.5 * attackingCircles / special;
				attackingCircles = 0;
				units[1] = allAttackingCircles;
			}
		}

		// and triangles
		if (defendingSquares != 0) {
			if (attackingTriangles > (int) (2 * defendingSquares * special)) {
				attackingTriangles -= (int) (2 * defendingSquares * special);
				units[2] += (int) (2 * defendingSquares * special);
				defendingSquares = 0;
			} else {
				defendingSquares -= (int) 0.5 * attackingTriangles / special;
				attackingTriangles = 0;
				units[2] = allAttackingTriangles;
			}
		}

		if (units[0] == allAttackingTriangles
				&& units[1] == allAttackingCircles
				&& units[2] == allAttackingTriangles) {
			units[0] = 1000;
			units[1] = 1000;
			units[2] = 1000;
		}
		// System.out.println("lost squares: " + units[0] + " lost circles: "
		// + units[1] + " lost tringles: " + units[2]);

		return units;
	}

	/*
	 * /** Gets the estimated cost between this node and the specified node. The
	 * estimated cost should never exceed the true cost. The better the
	 * estimate, the more effecient the search.
	 */

	public int getEstimatedCost(int node1, int node2) {
		int estimatedCost = 0;
		if (getNeighbors(node1).contains(node2)) {
			if (graph.map[node1].belongs() == agentId
					&& graph.map[node2].belongs() == agentId) { // System.out.println("Punkt5 ");
				return estimatedCost=3000;
			} else if (graph.map[node1].belongs() == agentId) { // System.out.println("Punkt6 ");
				return getCost(node1, node2);
			}
		} else

		{// System.out.println("Punkt7 ");
			estimatedCost = search(node1, node2).size() * 10;
		}
		// estimate cost by specimen and some cooefs
		// System.out.println("Punkt8 ");
		return estimatedCost = search(node1, node2).size() * 10;
	}

	// =======================================================================================

	// public class AStarSearch {

	/*
	 * /** A simple priority list, also called a priority queue. Objects in the
	 * list are ordered by their priority, determined by the object's Comparable
	 * interface. The highest priority item is first in the list.
	 */
	public static class PriorityList extends LinkedList<Integer> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void add(int object) {
			for (int i = 0; i < size(); i++) {
				if (object <= get(i)) {
					add(i, object);
					return;
				}
			}
			addLast(object);
		}
	}

	private List<Integer> getNeighbors(int node) {
		List<Integer> neighbors = new ArrayList<Integer>();

		for (int i = 0; i < graph.map[node].availableAdjacentNodes.length; i++) {
			if (graph.map[node].availableAdjacentNodes[i] > -1) {
				neighbors.add(graph.map[node].availableAdjacentNodes[i]);
			}
		}

		return neighbors;
	}

	/*
	 * /** Find the path from the start node to the end node. A list of
	 * AStarNodes is returned, or null if the path is not found.
	 */
	public List<Integer> findPath(int startNode, int goalNode) {

		PriorityList openList = new PriorityList();
		LinkedList<Integer> closedList = new LinkedList<Integer>();

		costFromStart[startNode] = 0;
		estimatedCostToGoal[startNode] = getEstimatedCost(startNode, goalNode);
		pathParent[startNode] = -1;
		openList.add(startNode);

		while (!openList.isEmpty()) {
			int node = openList.removeFirst();
			// System.out.println("REMOVED FIRST: " + node);
			if (node == goalNode) {
				// construct the path from start to goal
				// System.out.println("THIS IS THE A-STAR ANSWER: ");
				// cost(getCost())
				return constructPath(goalNode);
			}

			List<Integer> neighbors = getNeighbors(node);

			for (int i = 0; i < neighbors.size(); i++) {

				int neighborNode = neighbors.get(i);
				boolean isOpen = openList.contains(neighborNode);
				boolean isClosed = closedList.contains(neighborNode);
				int costFromStartInt = costFromStart[node]
						+ getCost(node, neighborNode);

				// check if the neighbor node has not been
				// traversed or if a shorter path to this
				// neighbor node is found.

				if ((!isOpen && !isClosed)
						|| costFromStartInt < costFromStart[neighborNode]) {
					pathParent[neighborNode] = node;
					// System.out.println("pathParent[neighborNode] "
					// + neighborNode + " node: " + node);
					costFromStart[neighborNode] = costFromStartInt;
					estimatedCostToGoal[neighborNode] = getEstimatedCost(
							neighborNode, goalNode);
					// System.out.println("costFromStart[neighborNode] "
					// + costFromStart[neighborNode]
					// + " estimatedCostToGoal[neighborNode]: "
					// + estimatedCostToGoal[neighborNode]);
					/*
					 * if (isClosed) { closedList.remove(neighborNode); }
					 */
					if (!isOpen) {
						openList.add(neighborNode);
					}
				}
			}
			closedList.add(node);
		}

		// no path found
		return null;
	}
}

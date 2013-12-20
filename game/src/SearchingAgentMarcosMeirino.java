import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Vector;

// This searching agent does take in consideration the possible disappearing of roads in the node

public class SearchingAgentMarcosMeirino implements agent {

	// WAIT_SOURCE_NUMBER stores the number given to the source to identify it
	// as a wait action

	private final int WAIT_SOURCE_NUMBER = -1;

	// the field agentId is used to distinguish which player the agent is
	// associated to

	private int agentId;

	// turnNumber and firstMove are used to store info about whether the user
	// has the first or second move and also the number of turns.

	private int turnNumber;
	private boolean firstMove;

	// agentActions is used to invoke the actions of the game when the decision
	// of what to do has been taken

	private agentActions agentActions;

	// In the following field, a copy of the graph, with a copy of each node and
	// not the originals ones, is stored.

	private Vector<node> clonedGraph;

	public SearchingAgentMarcosMeirino(int agentId) {

		// Initializing fields

		turnNumber = 0;
		agentActions = new agentActions();

		this.agentId = agentId;

		clonedGraph = new Vector<node>();
	}

	public void performAction(int currentAgent) {

		// Check if player is first or second:

		clonedGraph = new Vector<node>();

		if (currentAgent == 1)
			firstMove = true;
		else
			firstMove = false;

		// Clone the graph (it works properly assuming we are not working on a
		// multithread environment)

		// clonedGraph = predictGraph();

		for (int i = 0; i < graph.map.length; i++)

			clonedGraph.add(new node(graph.map[i].identificationNumber,
					graph.map[i].belongsTo, graph.map[i].squares,
					graph.map[i].circles, graph.map[i].triangles,
					graph.map[i].unitCreationType,
					graph.map[i].unitCreationSpeed,
					graph.map[i].adjacentNodes[0],
					graph.map[i].adjacentNodes[1],
					graph.map[i].adjacentNodes[2],
					graph.map[i].adjacentNodes[3],
					graph.map[i].adjacentNodes[4], graph.map[i].special));

		// Building minimaxGraph

		double startClock = System.currentTimeMillis();

		MinimaxGraph searchingGraph = new MinimaxGraph(agentId, turnNumber,
				firstMove, clonedGraph);

		double endClock = System.currentTimeMillis();

		System.out.println(" TIME ----> " + (endClock - startClock));

		// Making a search in the graph using my Dijkstra algorithm

		DijkstraAlgorithm dijkstraSearch = new DijkstraAlgorithm(searchingGraph);

		MinimaxEdge bestMovement = dijkstraSearch.search();

		if (bestMovement == null)

			System.out.println("DIJKSTRA RETURNED NULL");

		else {

			// Extracting results from the returned node and converting them to
			// an action, if the action produces an error, an error message is
			// displayed

			int bestSource = bestMovement.getSourceNumber();
			int bestTarget = bestMovement.getTargetNumber();

			// checking if the best action is waiting:

			if (bestSource == WAIT_SOURCE_NUMBER) {

				// I make a non-available movement for waiting

				boolean waitingSuccess;

				// System.out.println("SEARCHING AGENT / PERFORM ACTION: WAITING");

				waitingSuccess = agentActions.move(-1, 13, 0, 100000, 100000,
						100000);

				/*
				 * if (waitingSuccess == true)
				 * 
				 * System.out
				 * .println("SEARCHING AGENT / PERFORM ACTION: WAIT ERROR");
				 */
				// checking if target is owned by me, if so, it will be a
				// movement,
				// otherwise, it will be an attack:

			} else if (clonedGraph.get(bestTarget).belongsTo == currentAgent) {

				boolean movingSuccess;

				// System.out.println("SEARCHING AGENT / PERFORM ACTION: MOVING FROM "
				// + bestSource + " TO " + bestTarget);

				movingSuccess = agentActions.move(currentAgent, bestSource,
						bestTarget, bestMovement.getMovingSquares(),
						bestMovement.getMovingCircles(),
						bestMovement.getMovingTriangles());

				/*
				 * if (movingSuccess == false) System.out.println(
				 * "SEARCHING AGENT / PERFORM ACTION: MOVEMENT ERROR");
				 */
			} else {

				boolean atackingSuccess;

				// System.out.println("SEARCHING AGENT / PERFORM ACTION: ATACKING FROM "
				// + bestSource + " TO " + bestTarget);

				atackingSuccess = agentActions.attack(currentAgent, bestSource,
						bestTarget, bestMovement.getMovingSquares(),
						bestMovement.getMovingCircles(),
						bestMovement.getMovingTriangles());

				/*
				 * if (atackingSuccess == false)
				 * 
				 * System.out
				 * .println("SEARCHING AGENT / PERFORM ACTION: ATTACK ERROR"); }
				 * /* try { Thread.sleep(10000000); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } /*
				 */
			}
		}
	}

	// In this method we copy the graph taking in consideration the fog of war.
	// Moreover, enemy nodes and units are predicted

	private Vector<node> predictGraph() {

		Vector<node> predictedGraph = new Vector<node>();

		// First of all we insert our own nodes

		for (int i = 0; i < graph.map.length; i++)

			predictedGraph.add(graph.map[i]);

		// After that, we insert adjacentNodes

		int numOwnedNodes = predictedGraph.size();

		for (int i = 0; i < numOwnedNodes; i++)
			for (int j = 0; j < predictedGraph.get(i).availableAdjacentNodes.length; j++) {

				boolean found = false;
				int k = 0;
				while (!found && k < predictedGraph.size()) {

					if (predictedGraph.get(i).availableAdjacentNodes[j] != -1
							&& predictedGraph.get(k).identificationNumber == clonedGraph
									.get(predictedGraph.get(i).availableAdjacentNodes[j]).identificationNumber)
						found = true;

					k++;
				}
				if (found == false
						&& predictedGraph.get(i).availableAdjacentNodes[j] != -1)
					predictedGraph
							.add(clonedGraph.get(predictedGraph.get(i).availableAdjacentNodes[j]));
			}

		// Now we already have the graph with the fog of war. The last thing is
		// to predict the enemy positions and units.

		int knownEnemySquares = 0;
		int knownEnemyCircles = 0;
		int knownEnemyTriangles = 0;

		int enemyId = 1;

		if (agentId == 1)
			enemyId = 2;

		// First of all, we gather all the possible information about the enemy
		// we have with the fog of war.

		boolean[] enemyNodes = new boolean[14];

		for (int i = 0; i < enemyNodes.length; i++)
			enemyNodes[i] = false;

		for (int i = 0; i < predictedGraph.size(); i++)

			if (predictedGraph.get(i).belongs() == enemyId) {

				enemyNodes[i] = true;

				knownEnemySquares += predictedGraph.get(i).numberOfSquares();
				knownEnemyCircles += predictedGraph.get(i).numberOfCircles();
				knownEnemyTriangles += predictedGraph.get(i)
						.numberOfTriangles();
			}

		// If we don't have information about the enemy

		if (knownEnemySquares == 0 && knownEnemyCircles == 0
				&& knownEnemyTriangles == 0)

			// Añadir neutrales

			if (predictedGraph.size() != 14)
				System.out.println("PREDICTGRAPH ---- WRONG GRAPH SIZE");

		return predictedGraph;
	}

	// This class is used to store the state of the world on each node of the
	// world, that is a graph, the turn and the player (first or second)

	private class World {

		private Vector<node> worldGraph;
		private int agentId;
		private int turnNumber;
		private boolean firstPlayer;

		// This is the main constructor

		public World(int agentId, boolean firstPlayer, int turnNumber,
				Vector<node> worldGraph) {

			this.turnNumber = turnNumber;
			this.agentId = agentId;
			this.firstPlayer = firstPlayer;
			this.worldGraph = worldGraph;
		}

		// This constructor is used to get a copy of another world

		public World(World otherWorld, int agentId) {

			this.agentId = agentId;
			this.turnNumber = otherWorld.getTurnNumber();
			this.firstPlayer = otherWorld.getFirstPlayer();

			this.worldGraph = new Vector<node>();

			for (int i = 0; i < otherWorld.getGraph().size(); i++)

				this.worldGraph.add(new node(
						otherWorld.getGraph().get(i).identificationNumber,
						otherWorld.getGraph().get(i).belongsTo, otherWorld
								.getGraph().get(i).squares, otherWorld
								.getGraph().get(i).circles, otherWorld
								.getGraph().get(i).triangles, otherWorld
								.getGraph().get(i).unitCreationType, otherWorld
								.getGraph().get(i).unitCreationSpeed,
						otherWorld.getGraph().get(i).adjacentNodes[0],
						otherWorld.getGraph().get(i).adjacentNodes[1],
						otherWorld.getGraph().get(i).adjacentNodes[2],
						otherWorld.getGraph().get(i).adjacentNodes[3],
						otherWorld.getGraph().get(i).adjacentNodes[4],
						otherWorld.getGraph().get(i).special))

			;
		}

		public Vector<node> getGraph() {

			return worldGraph;
		}

		public boolean getFirstPlayer() {

			return firstPlayer;
		}

		public int getTurnNumber() {

			return turnNumber;
		}

		// This method is used to simulate a movement (including waiting
		// option). After the movement is done, the world is updated like in the
		// game. For doing it, I have only used the data belonging to this class
		// and the parameters of this method. All the code of the methods used
		// in this method have been copied by me from the game and adapted to
		// work with my classes

		public void applyAction(int sourceNumber, int targetNumber,
				int numSquares, int numCircles, int numTriangles) {

			turnNumber = turnNumber + 1;

			if (sourceNumber != -1) {

				if (worldGraph.get(targetNumber).belongs() == agentId) {

					worldGraph.get(sourceNumber).setNumberOfSquares(
							worldGraph.get(sourceNumber).numberOfSquares()
									- numSquares);
					worldGraph.get(sourceNumber).setNumberOfCircles(
							worldGraph.get(sourceNumber).numberOfCircles()
									- numCircles);
					worldGraph.get(sourceNumber).setNumberOfTriangles(
							worldGraph.get(sourceNumber).numberOfTriangles()
									- numTriangles);

					// adding moving units to targetNode
					worldGraph.get(targetNumber).setNumberOfSquares(
							worldGraph.get(targetNumber).numberOfSquares()
									+ numSquares);
					worldGraph.get(targetNumber).setNumberOfCircles(
							worldGraph.get(targetNumber).numberOfCircles()
									+ numCircles);
					worldGraph.get(targetNumber).setNumberOfTriangles(
							worldGraph.get(targetNumber).numberOfTriangles()
									+ numTriangles);

				} else {

					worldGraph.get(sourceNumber).setNumberOfSquares(
							worldGraph.get(sourceNumber).numberOfSquares()
									- numSquares);
					worldGraph.get(sourceNumber).setNumberOfCircles(
							worldGraph.get(sourceNumber).numberOfCircles()
									- numCircles);
					worldGraph.get(sourceNumber).setNumberOfTriangles(
							worldGraph.get(sourceNumber).numberOfTriangles()
									- numTriangles);

					// defenders
					int defendingSquares = worldGraph.get(targetNumber)
							.numberOfSquares();
					int defendingCircles = worldGraph.get(targetNumber)
							.numberOfCircles();
					int defendingTriangles = worldGraph.get(targetNumber)
							.numberOfTriangles();

					// 1st stage of fight
					// units fight with units of the same type - inflicting
					// normal casualties
					if (defendingSquares != 0)

						if (numSquares > defendingSquares) {
							numSquares -= defendingSquares;
							defendingSquares = 0;

						} else {
							// otherwise all squares die, inflicting higher
							// casualties upon enemy
							defendingSquares -= numSquares;
							numSquares = 0;
						}

					// same reasoning for circles
					if (defendingCircles != 0)

						if (numCircles > defendingCircles) {
							numCircles -= defendingCircles;
							defendingCircles = 0;

						} else {
							defendingCircles -= numCircles;
							numCircles = 0;
						}

					// and triangles
					if (defendingTriangles != 0)

						if (numTriangles > defendingTriangles) {
							numTriangles -= defendingTriangles;
							defendingTriangles = 0;

						} else {
							defendingTriangles -= numTriangles;
							numTriangles = 0;
						}

					// check if fight has ended
					if (defendingSquares == 0 && defendingCircles == 0
							&& defendingTriangles == 0) {

						if (numSquares > 0 || numCircles > 0
								|| numTriangles > 0) {
							// node change hands
							worldGraph.get(targetNumber).setOwner(agentId);

							// remaining attackers arrive at node
							worldGraph.get(targetNumber).setNumberOfSquares(
									numSquares);
							worldGraph.get(targetNumber).setNumberOfCircles(
									numCircles);
							worldGraph.get(targetNumber).setNumberOfTriangles(
									numTriangles);

						}

					} else if (numSquares == 0 && numCircles == 0
							&& numTriangles == 0) {
						// remaining defenders
						worldGraph.get(targetNumber).setNumberOfSquares(
								defendingSquares);
						worldGraph.get(targetNumber).setNumberOfCircles(
								defendingCircles);
						worldGraph.get(targetNumber).setNumberOfTriangles(
								defendingTriangles);

					} else {

						// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						// 2nd stage
						// squares vs. triangles - inflicting biggest casualties
						// if there are more squares that weaker enemies all
						// enemies die
						if (defendingTriangles != 0)

							if (numSquares > (0.5 * defendingTriangles)) {
								numSquares -= (0.5 * defendingTriangles);
								defendingTriangles = 0;

							} else {
								// otherwise all squares die, inflicting higher
								// casualties upon enemy
								defendingTriangles -= 2 * numSquares;
								numSquares = 0;
							}

						// same reasoning for circles
						if (defendingSquares != 0)

							if (numCircles > 0.5 * defendingSquares) {
								numCircles -= 0.5 * defendingSquares;
								defendingSquares = 0;

							} else {
								defendingSquares -= 2 * numCircles;
								numCircles = 0;
							}

						// and triangles
						if (defendingCircles != 0)

							if (numTriangles > 0.5 * defendingCircles) {
								numTriangles -= 0.5 * defendingCircles;
								defendingCircles = 0;

							} else {
								defendingCircles -= 2 * numTriangles;
								numTriangles = 0;
							}
					}

					// check if fight has ended
					if (defendingSquares == 0 && defendingCircles == 0
							&& defendingTriangles == 0) {

						if (numSquares > 0 || numCircles > 0
								|| numTriangles > 0) {
							// node change hands
							worldGraph.get(targetNumber).setOwner(agentId);

							// remaining attackers arrive at node
							worldGraph.get(targetNumber).setNumberOfSquares(
									numSquares);
							worldGraph.get(targetNumber).setNumberOfCircles(
									numCircles);
							worldGraph.get(targetNumber).setNumberOfTriangles(
									numTriangles);

						}

					} else if (numSquares == 0 && numCircles == 0
							&& numTriangles == 0) {
						// remaining defenders
						worldGraph.get(targetNumber).setNumberOfSquares(
								defendingSquares);
						worldGraph.get(targetNumber).setNumberOfCircles(
								defendingCircles);
						worldGraph.get(targetNumber).setNumberOfTriangles(
								defendingTriangles);

					} else {

						// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						// 3rd stage - last step
						if (defendingCircles != 0)

							if (numSquares > 2 * defendingCircles) {
								numSquares -= 2 * defendingCircles;
								defendingCircles = 0;

							} else {
								// otherwise all squares die, inflicting higher
								// casualties upon enemy
								defendingCircles -= 0.5 * numSquares;
								numSquares = 0;
							}

						// same reasoning for circles
						if (defendingTriangles != 0)

							if (numCircles > 2 * numTriangles) {
								numCircles -= 2 * defendingTriangles;
								defendingTriangles = 0;

							} else {
								defendingTriangles -= 0.5 * numCircles;
								numCircles = 0;
							}

						// and triangles
						if (defendingSquares != 0)

							if (numTriangles > 2 * defendingSquares) {
								numTriangles -= 2 * defendingSquares;
								defendingSquares = 0;

							} else {
								defendingSquares -= 0.5 * numTriangles;
								numTriangles = 0;
							}

					}

					if (defendingSquares == 0 && defendingCircles == 0
							&& defendingTriangles == 0) {

						if (numSquares > 0 || numCircles > 0
								|| numTriangles > 0) {
							// node change hands
							worldGraph.get(targetNumber).setOwner(agentId);

							// remaining attackers arrive at node
							worldGraph.get(targetNumber).setNumberOfSquares(
									numSquares);
							worldGraph.get(targetNumber).setNumberOfCircles(
									numCircles);
							worldGraph.get(targetNumber).setNumberOfTriangles(
									numTriangles);

						}

					} else if (numSquares == 0 && numCircles == 0
							&& numTriangles == 0) {
						// remaining defenders
						worldGraph.get(targetNumber).setNumberOfSquares(
								defendingSquares);
						worldGraph.get(targetNumber).setNumberOfCircles(
								defendingCircles);
						worldGraph.get(targetNumber).setNumberOfTriangles(
								defendingTriangles);

					}

				}
			}

			// World update:
			worldUpdate();
		}

		private void worldUpdate() {

			// All production speeds can be divide by 6, that is why we look at
			// their multiplication 1,2 and 3. That is why, while every time
			// times
			// grows speed 1 nodes product units. Every even cycle speed 2 nodes
			// product units, and twice a cycle speed 3 nodes product units.

			int times = turnNumber;

			times = times % 7;
			// We are taking speed and type production for each node and look at
			// the each node of the graph.
			int type, speed;

			for (int i = 0; i < worldGraph.size(); i++) {

				type = worldGraph.get(i).creationType();
				speed = worldGraph.get(i).speed();

				// Depending on the speed of the production we add units.

				switch (speed) {

				case 1:
					addUnits(type, i);
					break;
				case 2:
					if (times % 2 == 0)
						addUnits(type, i);
					break;
				case 3:
					if (times == 3 || times == 6)
						addUnits(type, i);
				}

			}

		}

		// Depending on the type of production we add a specific unit type to
		// the chosen node.
		private void addUnits(int type, int i) {

			int amount; // Amount of chosen units in the node.
			switch (type) {
			case 1:
				amount = worldGraph.get(i).numberOfSquares();
				amount++;
				worldGraph.get(i).setNumberOfSquares(amount);
				break;

			case 2:
				amount = worldGraph.get(i).numberOfCircles();
				amount++;
				worldGraph.get(i).setNumberOfCircles(amount);
				break;

			case 3:
				amount = worldGraph.get(i).numberOfTriangles();
				amount++;
				worldGraph.get(i).setNumberOfTriangles(amount);
				break;
			}
		}

	}

	// This class is used for storing the nodes of minimax graph and all its
	// information needed both for calculating the cost and for making Dijkstra
	// algorithm. I have chosen to store the punctuation of the current world in
	// nodes. This punctuation is calculated using heuristics in the method
	// calculatePunctuation() of MinimaxGraph class

	private class MinimaxNode implements Comparable<MinimaxNode> {

		// The punctuation is inverse, is a value between 0 and 1. The smaller
		// the value is, the best.

		private double gamePunctuation;

		// The three following fields are needed for the Dijkstra algorithm

		private double dijkstraPunctuation;
		private Vector<MinimaxEdge> adjacencies;
		private MinimaxNode previous;

		// There is only one constructor

		public MinimaxNode() {

			dijkstraPunctuation = Double.POSITIVE_INFINITY;

			gamePunctuation = Double.NEGATIVE_INFINITY;

			adjacencies = new Vector<MinimaxEdge>();
		}

		// We use this method when comparing nodes in Dijkstra

		public int compareTo(MinimaxNode other) {

			return Double.compare(gamePunctuation, other.getGamePunctuation());
		}

		public boolean equals(MinimaxNode other) {

			return gamePunctuation == other.getGamePunctuation();
		}

		private double getDijkstraPunctuation() {

			return dijkstraPunctuation;
		}

		// Those are getters for some attributes:

		public double getGamePunctuation() {

			return gamePunctuation;
		}

		public Vector<MinimaxEdge> getAdjacencies() {

			return adjacencies;
		}

		// This setter is used when constructing the graph

		public void setGamePunctuation(double punctuation) {

			this.gamePunctuation = punctuation;
		}

		// This method is a setter used in Dijkstra algorithm

		public void setDijkstraPunctuation(double punctuation) {

			this.dijkstraPunctuation = punctuation;
		}

		// This method adds one adjacent edge to the adjacents array

		public void addAdjacent(MinimaxEdge edge) {

			adjacencies.add(edge);
		}

		public void setPrevious(MinimaxNode previous) {

			this.previous = previous;
		}

		public MinimaxNode getPrevious() {

			return previous;
		}

		// In this method, nodes are compared by its punctuation on Dijkstra.

		public boolean isEqualInDijkstra(MinimaxNode other) {

			boolean equal = false;

			if (this.dijkstraPunctuation == other.getDijkstraPunctuation())

				equal = true;

			return equal;
		}

	}

	// This class is used for storing the edges of minimax graph and all its
	// information needed. It only has fields and getters. The weight of edges
	// is calculated on the method edgeCostFunction() of MinimaxGraph class, and
	// represents the risk of taking that action

	private class MinimaxEdge implements Comparable<MinimaxEdge> {

		// The following fields contains information about what is being done in
		// the action of the current edge

		private int sourceNumber;
		private int targetNumber;
		private int movingSquares;
		private int movingCircles;
		private int movingTriangles;

		// The two following fields are needed for the Dijkstra algorithm

		private MinimaxNode target;
		private double weight;

		// This is the only constructor

		public MinimaxEdge(int sourceNumber, int targetNumber,
				int movingSquares, int movingCircles, int movingTriangles,
				MinimaxNode target, double weight) {

			this.sourceNumber = sourceNumber;
			this.targetNumber = targetNumber;
			this.movingSquares = movingSquares;
			this.movingCircles = movingCircles;
			this.movingTriangles = movingTriangles;

			this.target = target;
			this.weight = weight;
		}

		// Those methods are getters for some attributes:

		public int getSourceNumber() {

			return sourceNumber;
		}

		public int getTargetNumber() {

			return targetNumber;
		}

		public int getMovingSquares() {

			return movingSquares;
		}

		public int getMovingCircles() {

			return movingCircles;
		}

		public int getMovingTriangles() {

			return movingTriangles;
		}

		public MinimaxNode getTarget() {

			return target;
		}

		public double getWeight() {

			return weight;
		}

		@Override
		public int compareTo(MinimaxEdge o) {

			return Double.compare(weight, o.getWeight());
		}

		public void setWeight(double weight) {

			this.weight = weight;
		}

	}

	// The class MinimaxGraph is used to build the graph

	private class MinimaxGraph {

		// The following constant represents the number of turns of both players
		// are going to be simulated in order to get the best decision

		private final int MINIMAX_DEPTH = 2;

		private int recursionNumber;

		private Vector<MinimaxNode> nodes;
		private Vector<MinimaxEdge> edges;
		private Deque<World> worldStack;
		private Deque<MinimaxNode> nodeStack;
		private MinimaxNode bestTarget;

		// In this constructor, the graph to make the search is build. The
		// process to build it is the following:
		//
		// A. Graph construction:
		//
		// The order the nodes are constructed is similar to the order of DFS
		// search.
		//
		// 1º The first node, that will be the source for Dijkstra, is a node
		// with the current state of the world
		//
		// 2º The first possible movement generates a new node, connected with
		// the previous node by an edge with information about the action or
		// movement made.
		// The new node has its own punctuation. If this punctuation is the same
		// than the punctuation of any other node, the node won't be created.
		// Instead, just and edge connecting the current source and the node
		// with the same punctuation will be created.
		// The new world is added to the worlds' stack.
		//
		// 3º Now is the turn of the enemy. Enemy movements will be estimated.
		//
		// 4º The second step is repeated with the node generated on that step
		// and the first of the possible movements of the player. In this case,
		// edges won't always have information about the movement.
		//
		// 5º Back to step 1 with the world generated in the previous step.
		//
		// 6º If we have already generated six successive movements, the last
		// world on the stack is removed. If there are more possible movements,
		// we will continue with the following movement and step 1. Otherwise,
		// the last world on the stack is also removed. Construction process is
		// finished when there are no more worlds in the stack.
		//
		//
		// B. Edge cost calculation:
		//
		// - This process is made using the method edgeCostFunction() of this
		// class. A
		// more detailed explanation of how it works can be found in this method

		public MinimaxGraph(int agentId, int turnNumber, boolean firstPlayer,
				Vector<node> clonedGraph) {

			// We are going to use a stack to store worlds in order to be able
			// to restore the previous situation

			worldStack = new ArrayDeque<World>();

			// Moreover, we use another stack to store visited nodes

			nodeStack = new ArrayDeque<MinimaxNode>();

			nodes = new Vector<MinimaxNode>();

			edges = new Vector<MinimaxEdge>();

			bestTarget = new MinimaxNode();

			World initialWorld = new World(agentId, firstPlayer, turnNumber,
					clonedGraph);

			MinimaxNode initialNode = new MinimaxNode();

			// We set the node punctuation using the initial world

			initialNode.setGamePunctuation(calculatePunctuation(initialWorld,
					agentId));

			worldStack.addFirst(initialWorld);

			nodeStack.addFirst(initialNode);

			nodes.add(initialNode);

			recursionNumber = 1;

			recursiveBuild(false);

			System.out.print(" ----------- RECURSION NUMBER ---------------: "
					+ recursionNumber);

			edgeCostFunction(false, nodes.get(0), new Vector<MinimaxNode>());

		}

		private void recursiveBuild(boolean enemyTurn) {

			// We take all the nodes in the last world added to the queue

			Vector<node> worldNodes = worldStack.peekFirst().getGraph();

			// For each owned node, we generate all possible movements (Step
			// 1)

			// If it is the enemy turn, we change the ID

			int currentAgentId = 0;

			if (enemyTurn) {

				if (agentId == 1)
					currentAgentId = 2;
				if (agentId == 2)
					currentAgentId = 1;
			}
			// If the turn is ours, we move
			else {

				currentAgentId = agentId;
			}

			if ((agentId != 1) && (agentId != 2))
				System.out.println("RECURSIVE BUILD / ERROR AGENT ID");

			for (node selectedNode : worldNodes) {

				// In the following if clause, we select all the nodes where
				// we can move from

				if (selectedNode.belongs() == currentAgentId) {

					// This for is used to use all available nodes from the
					// one selected before

					for (int i = 0; i < selectedNode.availableAdjacentNodes.length; i++)

						// The following if checks that the available node is a
						// real node

						if (selectedNode.availableAdjacentNodes[i] != -1) {

							// The following for and if clauses are used to
							// combine all the different possibilities when
							// moving each kind of unit. To make it simply, we
							// can only move the maximum amount of units of each
							// type.

							for (int numUnits = 1; numUnits < 8; numUnits++) {

								int moveSquares = numUnits / 4;
								int moveCircles = (numUnits / 2) % 2;
								int moveTriangles = numUnits % 2;

								if ((moveSquares == 0 || selectedNode.squares > 0)
										&& (moveCircles == 0 || selectedNode.circles > 0)
										&& (moveTriangles == 0 || selectedNode.triangles > 0)) {

									// First of all we create a copy of the
									// first world in the stack

									World newWorld = new World(
											worldStack.peekFirst(),
											currentAgentId);

									// Then we make a movement with that world

									newMovement(newWorld, selectedNode,
											moveSquares, moveCircles,
											moveTriangles, i, currentAgentId);

									// After, we are checking if the game has
									// ended

									boolean victory = false;

									if ((currentAgentId == 2 && newWorld
											.getGraph().get(0).belongs() == currentAgentId)
											|| (currentAgentId == 1 && newWorld
													.getGraph().get(13)
													.belongs() == currentAgentId))

										victory = true;

									boolean defeat = false;

									if ((currentAgentId == 1 && newWorld
											.getGraph().get(0).belongs() == 2)
											|| (currentAgentId == 2 && newWorld
													.getGraph().get(13)
													.belongs() == 1))

										defeat = true;
									/*
									 * System.out.println("TURN NUMBER: " +
									 * worldStack.size());
									 */
									if (worldStack.size() <= MINIMAX_DEPTH
											&& !victory && !defeat) {

										// We add the world and node in order to
										// stacks in order to use them in the
										// following recursive calls.

										worldStack.addFirst(newWorld);

										nodeStack.addFirst(nodes.lastElement());

										recursionNumber++;

										recursiveBuild(!enemyTurn);

										// When the recursive call has finished,
										// we remove the node and world from the
										// stacks, because we have already used
										// them.

										worldStack.removeFirst();

										nodeStack.removeFirst();

										// If the node is the last on its path,
										// it could be the best target

									} else if (nodes.lastElement()
											.getGamePunctuation() > bestTarget
											.getGamePunctuation())

										bestTarget = nodes.lastElement();
								}
							}
						}
				}

			}

			// Here is made the wait movement like the previous ones

			World newWorld = new World(worldStack.peekFirst(), currentAgentId);

			newMovement(newWorld, null, 0, 0, 0, -1, currentAgentId);

			boolean victory = false;

			if ((currentAgentId == 2 && newWorld.getGraph().get(0).belongs() == currentAgentId)
					|| (currentAgentId == 1 && newWorld.getGraph().get(13)
							.belongs() == currentAgentId))

				victory = true;

			boolean defeat = false;

			if ((currentAgentId == 1 && newWorld.getGraph().get(0).belongs() == 2)
					|| (currentAgentId == 2 && newWorld.getGraph().get(13)
							.belongs() == 1))

				defeat = true;

			//System.out.println("TURN NUMBER: " + worldStack.size());

			if (worldStack.size() <= MINIMAX_DEPTH && !victory && !defeat) {

				worldStack.addFirst(newWorld);

				nodeStack.addFirst(nodes.lastElement());

				recursiveBuild(!enemyTurn);

				worldStack.removeFirst();

				nodeStack.removeFirst();

			} else if (nodes.lastElement().getGamePunctuation() > bestTarget
					.getGamePunctuation())

				bestTarget = nodes.lastElement();
		}

		private void newMovement(World newWorld, node selectedNode,
				int moveSquares, int moveCircles, int moveTriangles,
				int adjacentIndex, int currentAgentId) {

			// First of all, we check if it is a wait "momvement"

			if (selectedNode != null) {

				// If it isn't a wait movement, we simulate the movement of the
				// player in our world

				newWorld.applyAction(selectedNode.identificationNumber,
						selectedNode.availableAdjacentNodes[adjacentIndex],
						moveSquares * selectedNode.squares, moveCircles
								* selectedNode.circles, moveTriangles
								* selectedNode.triangles);
			} else {

				newWorld.applyAction(-1, -1, 0, 0, 0);
			}

			double newPunctuation = calculatePunctuation(newWorld,
					currentAgentId);

			// If there is another node with the same punctuation, we don't
			// create a new one, just link it with the previous node.

			boolean repeatedNode = false;
			MinimaxNode equalMinimaxNode = null;

			for (int k = 0; k < nodes.size(); k++)
				if (nodes.get(k).getGamePunctuation() == newPunctuation) {

					repeatedNode = true;
					equalMinimaxNode = nodes.get(k);
				}

			if (repeatedNode == true) {

				MinimaxEdge newMinimaxEdge;

				// If it is not a wait movement, we use the correct information

				if (selectedNode != null) {
					newMinimaxEdge = new MinimaxEdge(
							selectedNode.identificationNumber,
							selectedNode.availableAdjacentNodes[adjacentIndex],
							moveSquares * selectedNode.squares, moveCircles
									* selectedNode.circles, moveTriangles
									* selectedNode.triangles, equalMinimaxNode,
							Double.POSITIVE_INFINITY);
				} else {

					// If it is the wait movement, we use bad information to
					// know it was a waiting movement

					newMinimaxEdge = new MinimaxEdge(-1, -1, 0, 0, 0,
							equalMinimaxNode, Double.POSITIVE_INFINITY);
				}

				// Finally, we add the edge to the first node of the stack and t
				// our vector of edges

				nodeStack.peekFirst().addAdjacent(newMinimaxEdge);

				edges.add(newMinimaxEdge);

			} else {

				MinimaxNode newMinimaxNode = new MinimaxNode();

				newMinimaxNode.setGamePunctuation(newPunctuation);

				MinimaxEdge newMinimaxEdge;

				if (selectedNode != null) {

					newMinimaxEdge = new MinimaxEdge(
							selectedNode.identificationNumber,
							selectedNode.availableAdjacentNodes[adjacentIndex],
							moveSquares * selectedNode.squares, moveCircles
									* selectedNode.circles, moveTriangles
									* selectedNode.triangles, newMinimaxNode,
							Double.POSITIVE_INFINITY);

				} else {

					newMinimaxEdge = new MinimaxEdge(-1, -1, 0, 0, 0,
							newMinimaxNode, Double.POSITIVE_INFINITY);
				}

				newMinimaxNode.setPrevious(nodeStack.peekFirst());

				nodeStack.peekFirst().addAdjacent(newMinimaxEdge);

				edges.add(newMinimaxEdge);

				nodes.add(newMinimaxNode);
			}

		}

		// This method provides all edges on the graph with its weight. To get
		// this, we follow this process if is the turn of the enemy:
		//
		// 1º We take all neighbour nodes to the source which have not been
		// visited yet.
		//
		// 2º After that, we take the nodes with extreme punctuation, to give
		// more importance to the most dangerous risks.
		//
		// 3º The middle punctuation of taken nodes in the previous step is
		// assigned as the punctuation of all edges, cause the risk taken by
		// them all is the same, as you can't predict which move the player is
		// going to make.
		//
		// If it is our turn, the weight is the difference among the
		// punctuations of the source and the target nodes

		private void edgeCostFunction(boolean enemyTurn, MinimaxNode source,
				Vector<MinimaxNode> visitedNodes) {

			// First, we get all the nodes connected to the source that haven't
			// been visited yet:

			Vector<MinimaxNode> nodes = new Vector<MinimaxNode>();

			for (MinimaxEdge edge : source.getAdjacencies())

				if (visitedNodes.contains(edge.getTarget()) == false)
					nodes.add(edge.getTarget());

			// If it is our turn, the weight of the edge is calculated comparing
			// the scores of the two nodes

			if (enemyTurn == false)

				for (int i = 0; i < source.getAdjacencies().size(); i++) {

					double score = source.getGamePunctuation()
							- source.getAdjacencies().get(i).getTarget()
									.getGamePunctuation();

					score = 5000 - score;

					source.getAdjacencies().get(i).setWeight(score);
				}
			else {

				// Now is the turn of the enemy:

				// We sort the vector of nodes to get the ends of both sides

				Collections.sort(nodes);

				// Now we calculate how many nodes are we going to take from the
				// ends of the vector

				int nTakenNodes = nodes.size() / 3;

				if (nTakenNodes % 2 == 1)
					nTakenNodes += 1;

				if (nTakenNodes < 2)
					nTakenNodes = 2;

				// After, we calculate the middle cost

				double middleScore = 0.0;

				for (int i = 0; i < nTakenNodes; i++) {

					middleScore += nodes.get(i).getGamePunctuation();
					middleScore += nodes.get((nodes.size() - 1 - i))
							.getGamePunctuation();
				}

				middleScore = middleScore / nTakenNodes;

				// Using the following ecuation, we get a positive value (hoping
				// that punctuation will never be higher than 5000) that
				// assigns low punctuations to the edges with good or high
				// punctuation. Also, the values follow a linear distribution,
				// so
				// extreme values won't be distorted.

				middleScore = 5000 - middleScore;

				if (middleScore < 0)
					System.out
							.println("COST FUNCTION / ERROR NEGATIVE MIDDLESCORE");

				for (int i = 0; i < source.getAdjacencies().size(); i++)

					source.getAdjacencies().get(i).setWeight(middleScore);
			}

		}

		// To calculate punctuation we give a little importance to the
		// punctuation derived from the part of the world we have predict

		private double calculatePunctuation(World newWorld, int currentAgentId) {

			// First of all we calculate the number of nodes we know
			// (exploration punctuation)

			double punctuation = 0;

			Vector<node> knownGraph = new Vector<node>();

			// First of all we insert our own nodes

			for (int i = 0; i < newWorld.getGraph().size(); i++)

				if (newWorld.getGraph().get(i).belongsTo == currentAgentId)
					knownGraph.add(newWorld.getGraph().get(i));

			// After that, we insert adjacentNodes

			int auxOwnedNodes = knownGraph.size();

			for (int i = 0; i < auxOwnedNodes; i++)
				for (int j = 0; j < knownGraph.get(i).availableAdjacentNodes.length; j++) {

					boolean found = false;
					int k = 0;
					while (!found && k < knownGraph.size()) {

						if (knownGraph.get(i).availableAdjacentNodes[j] != -1
								&& knownGraph.get(k).identificationNumber == newWorld
										.getGraph()
										.get(knownGraph.get(i).availableAdjacentNodes[j]).identificationNumber)
							found = true;

						k++;
					}
					if (found == false
							&& knownGraph.get(i).availableAdjacentNodes[j] != -1)
						knownGraph
								.add(clonedGraph.get(knownGraph.get(i).availableAdjacentNodes[j]));
				}

			// Now we have the part of the graph we know stored

			int knownNodesNum = knownGraph.size();
			int unknownNodesNum = 14 - knownNodesNum;

			// Now we are going to store the predicted part of the graph

			Vector<node> unknownGraph = new Vector<node>();

			for (int i = 0; i < newWorld.getGraph().size(); i++) {

				boolean found = false;
				int j = 0;
				while (!found && j < knownGraph.size()) {

					if (knownGraph.get(j).identificationNumber == newWorld
							.getGraph().get(i).identificationNumber)
						found = true;
					j++;
				}
				if (found == false)
					unknownGraph.add(newWorld.getGraph().get(i));
			}

			if (unknownNodesNum != unknownGraph.size()) {
				System.out
						.println("CALCULATE PUNCTUATION / ERROR UNKNOWN GRAPH SIZE.  UNKNOWN: "
								+ unknownGraph.size()
								+ " KNOWN: "
								+ knownGraph.size());
				// System.exit(-1);
			}
			// Now we have the known and unknown graph stored in different
			// objects. We are going to calculate all the data for the
			// punctuation

			// In the following variables we have the known number of units of
			// each type and player, number of effective units (units they can
			// use to attack or defend) of each player, and unit production
			// number

			int knownOwnedSquares = 0;
			int knownOwnedCircles = 0;
			int knownOwnedTriangles = 0;
			int knownEnemySquares = 0;
			int knownEnemyCircles = 0;
			int knownEnemyTriangles = 0;

			int knownEffectiveOwnedSquares = 0;
			int knownEffectiveOwnedCircles = 0;
			int knownEffectiveOwnedTriangles = 0;
			int knownEffectiveEnemySquares = 0;
			int knownEffectiveEnemyCircles = 0;
			int knownEffectiveEnemyTriangles = 0;

			int knownOwnSquareProduction = 0;
			int knownOwnCircleProduction = 0;
			int knownOwnTriangleProduction = 0;
			int knownEnemySquareProduction = 0;
			int knownEnemyCircleProduction = 0;
			int knownEnemyTriangleProduction = 0;

			int knownOwnedNodes = 0;
			int knownEnemyNodes = 0;

			for (int i = 0; i < knownGraph.size(); i++) {

				// If this node is owned by me:

				if (knownGraph.get(i).belongs() == agentId) {

					knownOwnedNodes += 1;

					knownOwnedSquares += knownGraph.get(i).numberOfSquares();
					knownOwnedCircles += knownGraph.get(i).numberOfCircles();
					knownOwnedTriangles += knownGraph.get(i)
							.numberOfTriangles();

					if (knownGraph.get(i).creationType() == 1)
						knownOwnSquareProduction += knownGraph.get(i).speed();

					else if (knownGraph.get(i).creationType() == 2)
						knownOwnCircleProduction += knownGraph.get(i).speed();

					else if (knownGraph.get(i).creationType() == 3)
						knownOwnTriangleProduction += knownGraph.get(i).speed();

					// Here we calculate effective units:
					boolean frontier = false;

					for (int j = 0; j < knownGraph.get(i).getAvailableNodes().length; j++) {
						for (int k = 0; k < knownGraph.size(); k++)

							// Here we check if the current available neighbour
							// is owned by the enemy

							if ((knownGraph.get(k).identificationNumber == knownGraph
									.get(i).availableAdjacentNodes[j])
									&& ((knownGraph.get(k).belongs() == 1 && agentId == 2) || (knownGraph
											.get(k).belongs() == 2 && agentId == 1)))
								frontier = true;
					}

					if (frontier == true) {

						knownEffectiveOwnedSquares += knownGraph.get(i)
								.numberOfSquares();
						knownEffectiveOwnedCircles += knownGraph.get(i)
								.numberOfCircles();
						knownEffectiveOwnedTriangles += knownGraph.get(i)
								.numberOfTriangles();
					}

					// if is owned by the enemy:
				} else {

					knownEnemyNodes += 1;

					knownEnemySquares += knownGraph.get(i).numberOfSquares();
					knownEnemyCircles += knownGraph.get(i).numberOfCircles();
					knownEnemyTriangles += knownGraph.get(i)
							.numberOfTriangles();

					if (knownGraph.get(i).creationType() == 1)
						knownEnemySquareProduction += knownGraph.get(i).speed();

					else if (knownGraph.get(i).creationType() == 2)
						knownEnemyCircleProduction += knownGraph.get(i).speed();

					else if (knownGraph.get(i).creationType() == 3)
						knownEnemyTriangleProduction += knownGraph.get(i)
								.speed();

					// Here we calculate effective units:
					boolean frontier = false;

					for (int j = 0; j < knownGraph.get(i).getAvailableNodes().length; j++) {
						for (int k = 0; k < knownGraph.size(); k++)

							// Here we check if the current available neighbour
							// is owned by the enemy

							if ((knownGraph.get(k).identificationNumber == knownGraph
									.get(i).availableAdjacentNodes[j])
									&& ((knownGraph.get(k).belongs() == 1 && agentId == 2) || (knownGraph
											.get(k).belongs() == 2 && agentId == 1)))
								frontier = true;
					}

					if (frontier == true) {

						knownEffectiveEnemySquares += knownGraph.get(i)
								.numberOfSquares();
						knownEffectiveEnemyCircles += knownGraph.get(i)
								.numberOfCircles();
						knownEffectiveEnemyTriangles += knownGraph.get(i)
								.numberOfTriangles();
					}
				}

			}
			// Now we are collecting the predicted data:

			int unknownEnemySquares = 0;
			int unknownEnemyCircles = 0;
			int unknownEnemyTriangles = 0;

			int unknownEnemySquareProduction = 0;
			int unknownEnemyCircleProduction = 0;
			int unknownEnemyTriangleProduction = 0;

			int unknownEnemyNodes = 0;

			for (int a = 0; a < unknownGraph.size(); a++) {

				// If this node is owned by the enemy:

				if ((unknownGraph.get(a).belongs() == 1 && agentId == 2)
						|| (unknownGraph.get(a).belongs() == 2 && agentId == 1)) {

					unknownEnemyNodes += 1;

					unknownEnemySquares += unknownGraph.get(a)
							.numberOfSquares();
					unknownEnemyCircles += unknownGraph.get(a)
							.numberOfCircles();
					unknownEnemyTriangles += unknownGraph.get(a)
							.numberOfTriangles();

					if (unknownGraph.get(a).creationType() == 1)
						unknownEnemySquareProduction += unknownGraph.get(a)
								.speed();

					else if (unknownGraph.get(a).creationType() == 2)
						unknownEnemyCircleProduction += unknownGraph.get(a)
								.speed();

					else if (unknownGraph.get(a).creationType() == 3)
						unknownEnemyTriangleProduction += unknownGraph.get(a)
								.speed();
				}
			}

			// First of all, we calculate punctuation relative to unit amount.
			// To get this, we make the difference between owned units and the
			// best enemy units to defeat this kind of units. Moreover, units of
			// predicted world have lees importance, because we are not sure
			// about its existence.

			punctuation = (knownOwnedSquares * 0.5)
					- (knownEnemyCircles + 0.3 * unknownEnemyCircles)
					+ (knownOwnedCircles * 0.5)
					- (knownEnemyTriangles + 0.3 * unknownEnemyTriangles)
					+ (knownOwnedTriangles * 0.5)
					- (knownEnemySquares + 0.3 * unknownEnemySquares);

			// Secondly, we calculate punctuation relative to effective unit
			// amount. It is done in the same way, but we can see all effective
			// units, so they are real and a direct danger for us.

			punctuation = ((knownEffectiveOwnedSquares * 0.5)
					- knownEffectiveEnemyCircles
					+ (knownEffectiveOwnedCircles * 0.5)
					- knownEffectiveEnemyTriangles
					+ (knownEffectiveOwnedTriangles * 0.5) - knownEffectiveEnemySquares) * 1.5;

			// At the third time, we calculate punctuation relative to unit
			// production. It is done in the same way than common units

			punctuation = ((knownOwnSquareProduction * 0.5)
					- (knownEnemyCircleProduction + 0.3 * unknownEnemyCircleProduction)
					+ (knownOwnCircleProduction * 0.5)
					- (knownEnemyTriangleProduction + 0.3 * unknownEnemyTriangleProduction)
					+ (knownOwnTriangleProduction * 0.5) - (knownEnemySquareProduction + 0.3 * unknownEnemySquareProduction)) * 1.5;

			// At the fourth time, we calculate punctuation relative to owned
			// nodes by each player

			punctuation = knownOwnedNodes
					- (knownEnemyNodes + 0.3 * unknownEnemyNodes)
					+ knownOwnCircleProduction
					- (knownEnemyTriangleProduction + 0.3 * unknownEnemyTriangleProduction)
					+ knownOwnTriangleProduction
					- (knownEnemySquareProduction + 0.3 * unknownEnemySquareProduction)
					* 1.5;

			// Now we check if the player has won

			if ((agentId == 2 && newWorld.getGraph().get(0).belongs() == agentId)
					|| (agentId == 1 && newWorld.getGraph().get(13).belongs() == agentId))

				punctuation += punctuation;

			// And, finally, we check if our player has lost

			if ((agentId == 1 && newWorld.getGraph().get(0).belongs() == 2)
					|| (agentId == 2 && newWorld.getGraph().get(13).belongs() == 1))

				punctuation = 0;

			return punctuation;
		}

		public MinimaxNode getNode(int i) {

			return nodes.get(i);
		}

		public MinimaxNode getBestTarget() {

			return bestTarget;
		}

	}

	// I find the best path, for that I have to decide over the whole graph, but
	// I'm returning only the first movement, that is the first node of the best
	// path

	private class DijkstraAlgorithm {

		private MinimaxGraph graph;
		private MinimaxNode bestTarget;

		public DijkstraAlgorithm(MinimaxGraph graph) {

			this.graph = graph;
			this.bestTarget = graph.getBestTarget();
		}

		public MinimaxEdge search() {

			// Source is the source node of the graph

			MinimaxNode source = graph.getNode(0);

			source.setDijkstraPunctuation(0);

			// This queue is used to store relaxed nodes

			PriorityQueue<MinimaxNode> nodeQueue = new PriorityQueue<MinimaxNode>();
			nodeQueue.add(source);

			while (!nodeQueue.isEmpty()) {

				MinimaxNode u = nodeQueue.poll();

				// Visit each edge whose source node is u
				for (MinimaxEdge e : u.getAdjacencies()) {

					MinimaxNode node = e.getTarget();
					double weight = e.getWeight();

					if (weight < 0)

						System.out.println("NEGATIVE WEIGHT!!!!" + weight);

					// Calculating the puntuation of the node reached through
					// current node, which is u

					double punctuationUsingU = u.getDijkstraPunctuation()
							+ weight;

					// If the punctuation using u is lower than the current
					// punctuation of the target, the target gets "relaxed"

					if (punctuationUsingU < node.getDijkstraPunctuation()) {

						nodeQueue.remove(node);

						// System.out.println("SETTING PUNCTUATION");

						node.setDijkstraPunctuation(punctuationUsingU);

						node.setPrevious(u);
						nodeQueue.add(node);
					}
				}
			}

			// We obtain the BEST path from the source to the best target

			MinimaxEdge bestMovement = null;

			Vector<MinimaxNode> path = new Vector<MinimaxNode>();

			// We chack if the best node is the source. This happens when player
			// has lost. In that case, we will make a wait.

			if (bestTarget.getGamePunctuation() == source.getGamePunctuation()) {
				System.out
						.println("DIJKSTRA SEARCH / THE BEST NODE IS THE SOURCE!!");

				bestMovement = new MinimaxEdge(WAIT_SOURCE_NUMBER,
						WAIT_SOURCE_NUMBER, WAIT_SOURCE_NUMBER,
						WAIT_SOURCE_NUMBER, WAIT_SOURCE_NUMBER, null,
						WAIT_SOURCE_NUMBER);
			} else {
				for (MinimaxNode node = bestTarget; node != null; node = node
						.getPrevious())

					path.add(node);

				// System.out.println("PATH LENGHT: " + path.size());

				Collections.reverse(path);

				// Now, we get the edge from the source to second node on the
				// BEST
				// path

				for (MinimaxEdge e : path.get(0).getAdjacencies())
					if (e.getTarget().isEqualInDijkstra(path.get(1)))
						bestMovement = e;

				// Finally, we return it.

				System.out.println("DIJKSTRA FINISHED!!");
			}

			if (bestMovement.getSourceNumber() > -1)
				if (clonedGraph.get(bestMovement.getSourceNumber()).belongs() != agentId)

					System.out.println("ENEMY SOURCE: "
							+ bestMovement.getSourceNumber() + "   AGENT_ID: "
							+ agentId);

			return bestMovement;
		}
	}

}

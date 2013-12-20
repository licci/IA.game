import java.util.ArrayList;
import java.util.List;

public class agneseAgent implements agent {

	public static agentActions action = new agentActions();

	@Override
	public void performAction(int currentAgent) {
		List<node> availableNodes = new ArrayList<node>();

		for (node actualnode : graph.map) {
			if (actualnode.belongs() == currentAgent) {
				availableNodes.add(actualnode);
			}
		}

		int cost = -1, minCost = Integer.MAX_VALUE, minCostId = -1, startId = -1;
		for (node actualStartNode : availableNodes) {
			int[] connectedTo = actualStartNode.getAvailableNodes();
			for (int connectedNodeId : connectedTo) {
				if (connectedNodeId > -1 && graph.map[connectedNodeId].belongs() != currentAgent) {
					cost = calculateCost(currentAgent,
							actualStartNode.get_id(), connectedNodeId,
							actualStartNode.squares, actualStartNode.circles,
							actualStartNode.triangles);
					
					if (cost > -1) {
						System.out.println("Stan {" + actualStartNode.squares + ", " + actualStartNode.circles + ", " + actualStartNode.triangles + "}");
						System.out.println("Zaatakowanie [" + connectedNodeId + "] z [" + actualStartNode.get_id() + "] kosztuje [" + cost + "]");
					}
					
					if (cost > -1 && cost < minCost) {
						minCost = cost;
						startId = actualStartNode.get_id();
						minCostId = connectedNodeId;
					}
				}
			}
		}
		
		if (minCostId > -1) {
			System.out.println("Zaatakowano [" + minCostId + "] z [" + startId + "], kosztowalo to [" + minCost + "]");
			action.attack(currentAgent, startId, minCostId,
					graph.map[startId].squares, graph.map[startId].circles,
					graph.map[startId].triangles);
		}

	}

	public int calculateCost(int attackerId, int startNode, int targetNode,
			int attackingSquares, int attackingCircles, int attackingTriangles) {

		int originalAttackingSquares = attackingSquares;
		int originalAttackingCircles = attackingCircles;
		int originalAttackingTriangles = attackingTriangles;

		int defendingSquares = graph.map[targetNode].numberOfSquares();
		int defendingCircles = graph.map[targetNode].numberOfCircles();
		int defendingTriangles = graph.map[targetNode].numberOfTriangles();

		int special = graph.map[targetNode].getSpecial();

		int cost = -1;

		if (defendingSquares != 0) {
			if (attackingSquares > (int) (defendingSquares * special)) {
				attackingSquares -= (int) (defendingSquares * special);
				defendingSquares = 0;
			} else {
				defendingSquares -= (int) attackingSquares / special;
				attackingSquares = 0;
			}
		}
		if (defendingCircles != 0) {
			if (attackingCircles > (int) (defendingCircles * special)) {
				attackingCircles -= (int) (defendingCircles * special);
				defendingCircles = 0;
			} else {
				defendingCircles -= (int) attackingCircles / special;
				attackingCircles = 0;
			}
		}
		if (defendingTriangles != 0) {
			if (attackingTriangles > (int) (defendingTriangles * special)) {
				attackingTriangles -= (int) (defendingTriangles * special);
				defendingTriangles = 0;
			} else {
				defendingTriangles -= (int) attackingTriangles / special;
				attackingTriangles = 0;
			}
		}

		if ( ! battleEnded(attackerId, startNode, targetNode, attackingSquares,
				attackingCircles, attackingTriangles, defendingSquares,
				defendingCircles, defendingTriangles)) {
			if (defendingTriangles != 0) {
				if (attackingSquares > (int) (0.5 * defendingTriangles * special)) {
					attackingSquares -= (int) (0.5 * defendingTriangles * special);
					defendingTriangles = 0;
				} else {
					defendingTriangles -= (int) 2 * attackingSquares / special;
					attackingSquares = 0;
				}
			}
			if (defendingSquares != 0) {
				if (attackingCircles > (int) (0.5 * defendingSquares * special)) {
					attackingCircles -= (int) (0.5 * defendingSquares * special);
					defendingSquares = 0;
				} else {
					defendingSquares -= (int) 2 * attackingCircles / special;
					attackingCircles = 0;
				}
			}
			if (defendingCircles != 0) {
				if (attackingTriangles > (int) (0.5 * defendingCircles * special)) {
					attackingTriangles -= (int) (0.5 * defendingCircles * special);
					defendingCircles = 0;
				} else {
					defendingCircles -= (int) 2 * attackingTriangles / special;
					attackingTriangles = 0;
				}
			}

			// check if fight has ended
			if ( ! battleEnded(attackerId, startNode, targetNode,
					attackingSquares, attackingCircles, attackingTriangles,
					defendingSquares, defendingCircles, defendingTriangles)) {
				if (defendingCircles != 0) {
					if (attackingSquares > (int) (2 * defendingCircles * special)) {
						attackingSquares -= (int) (2 * defendingCircles * special);
						defendingCircles = 0;
					} else {
						defendingCircles -= (int) 0.5 * attackingSquares
								/ special;
						attackingSquares = 0;
					}
				}
				if (defendingTriangles != 0) {
					if (attackingCircles > (int) (2 * defendingTriangles * special)) {
						attackingCircles -= (int) (2 * defendingTriangles * special);
						defendingTriangles = 0;
					} else {
						defendingTriangles -= (int) 0.5 * attackingCircles
								/ special;
						attackingCircles = 0;
					}
				}
				if (defendingSquares != 0) {
					if (attackingTriangles > (int) (2 * defendingSquares * special)) {
						attackingTriangles -= (int) (2 * defendingSquares * special);
						defendingSquares = 0;
					} else {
						defendingSquares -= (int) 0.5 * attackingTriangles
								/ special;
						attackingTriangles = 0;
					}
				}
			} else {
				return -1;
			}
		}

		if ((defendingSquares + defendingCircles + defendingTriangles) == 0 && (attackingSquares+attackingCircles+attackingTriangles) > 0) {
			cost = (originalAttackingSquares - attackingSquares)
					+ (originalAttackingCircles - attackingCircles)
					+ (originalAttackingTriangles - attackingTriangles);
		}
		
		return cost;
	}

	public boolean battleEnded(int attackerId, int startNode, int targetNode,
			int attackingSquares, int attackingCircles, int attackingTriangles,
			int defendingSquares, int defendingCircles, int defendingTriangles) {
		if (defendingSquares == 0 && defendingCircles == 0
				&& defendingTriangles == 0 && attackingSquares == 0
				&& attackingCircles == 0 && attackingTriangles == 0) {
			return false;
		} else if (defendingSquares == 0 && defendingCircles == 0
				&& defendingTriangles == 0) {
			return true;
		} else if (attackingSquares == 0 && attackingCircles == 0
				&& attackingTriangles == 0) {
			return true;
		}
		return false;
	}
}

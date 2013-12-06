import java.util.ArrayList;
import java.util.List;

public class randomAgent implements agent {

	public static agentActions action = new agentActions();

	int counter = 0;

	
	 int aid1LastNode = 0; 
	 int aid2LastNode = 13;
	 

	public void randomAttack(int agentId) {

		List<Integer> availableNodes = new ArrayList<Integer>();

		for (int x = 0; x < 14; x = x + 1) {

			if (graph.map[x].belongs() == agentId) {

				availableNodes.add(x);
			}
		}

		int RandomNodeIndex = 0 + (int) (Math.random() * availableNodes.size());
		int RandomNode = availableNodes.get(RandomNodeIndex);

		System.out
				.println("PRE-ATTACK DEBUG - " + agentId + " / " + RandomNode);
		int availableStart[] = graph.map[RandomNode].getAvailableNodes();
		boolean ifSuccessAttack = false;
		for (int x : availableStart) {

			System.out.println("ATTACK DEBUG - " + x + " / " + RandomNode);

			if (x >= 0) {
				System.out.println("ATTACK DEBUG - " + x + " / " + RandomNode);
				
				ifSuccessAttack = action.attack(agentId, RandomNode, x,
						graph.map[RandomNode].numberOfSquares(),
						graph.map[RandomNode].numberOfCircles(),
						graph.map[RandomNode].numberOfTriangles());
				
				if (ifSuccessAttack == true) {
					System.out.println("Succes in attack!");
					return;
				}
			}

			if (ifSuccessAttack == true) {
				System.out.println("No success, let's try again!");
				performAction(agentId);
			}
		}
	}

	public void randomMove(int agentId) {

		List<Integer> availableNodes = new ArrayList<Integer>(); // getting nodes that belongs to given player

		for (int x = 0; x < 14; x = x + 1) {

			if (graph.map[x].belongs() == agentId) {

				availableNodes.add(x);
			}
		}

		int RandomNodeIndex = 0 + (int) (Math.random() * availableNodes.size()); // random choice of player's node
		int RandomNode = availableNodes.get(RandomNodeIndex);

		int availableStart[] = graph.map[RandomNode].getAvailableNodes();
		boolean ifSuccessMove = false;
		for (int x : availableStart) {
			if (x > 0) {
				System.out.println("MOVE DEBUG - " + x + " / " + RandomNode);

				ifSuccessMove = action.move(agentId, RandomNode, x,
						graph.map[RandomNode].numberOfSquares(),
						graph.map[RandomNode].numberOfCircles(),
						graph.map[RandomNode].numberOfTriangles());

				/*
				 * if (ifSuccessMove == true) {
				 * 
				 * if (agentId == 1) { aid1move = true; aid1LastNode = x; } if
				 * (agentId == 2) { aid2move = true; aid2LastNode = x; } } else
				 * {
				 * 
				 * if (agentId == 1) { aid1move = true; } if (agentId == 2) {
				 * aid2move = true; }
				 * 
				 * }
				 */

			}

		}

		if (ifSuccessMove == false) {

			System.out.println("No success, let's try again!");
			performAction(agentId);

		}

	}

	@Override
	public void performAction(int currentAgent) {

		int randomNum = 1 + (int) (Math.random() * 2);

		System.out.println("ACTION - " + currentAgent);

		if (randomNum == 1)
			randomMove(currentAgent);
		if (randomNum == 2)
			randomAttack(currentAgent);

	}

}

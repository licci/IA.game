package pruebaJNI;

import CLIPSJNI.Environment;

public class CLIPS_Interface {

	private String agentFile; // The name of the CLIPS file where the agent is
								// stored

	public CLIPS_Interface(String agentFile) {

		this.agentFile = agentFile;
	}

	public void getAction(graph graph) {

		// A random node to test the interface with a node

		node testNode = new node(0, 5, 0, 0, 10, 3, 3, 1, 2, 8, -1, -1, 1);

		Environment clips = new Environment();

		// Here loads the .clp file

		clips.load(agentFile);

		// this is made to reset the values of the agent

		clips.reset();

		// Asserts; information about the world is passed within those asserts,
		// that will be added to the fact-list of the agent. This fact-list is
		// basically the information the agent has

		clips.assertString("(node (idNumber 0) (belongsTo 5) (availableAdjacentNodes 1 2 3 4 5))");
		
		clips.assertString("(node (idNumber 1) (belongsTo 6)))");

		// Runs the agent, gets the response and puts it on the screen

		clips.run();
/*
		String response = clips.getInputBuffer();

		System.out.println(response);
/**/
	}

	public static void main(String[] args) {

		CLIPS_Interface clipsInterface = new CLIPS_Interface(
				"CLIPS_Interface.clp");

		// I'm passing a null graph to the interface cause is still not finished
		// to work with a graph. The data passed to the graph is no the method
		// getAction()

		clipsInterface.getAction(null);

	}

}

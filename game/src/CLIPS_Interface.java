
import CLIPSJNI.*;

/*
 * Interface used to get decisions from CLIPS
 * Right now it is created in such a way that constructor takes your rules file, so each agent will have his own interface
 * CLIPS function is supposed to return action your agent wont's to commit.
 * I am not forcing anyone to use it in any particular way, if you want to copy this interface to your agent class change function name from "CLIPS" to "performAction" and use it to decide and then commit action - fine.
 * The only thing you HAVE TO remember is to prepare averaging function, so you will not send original graph to CLIPS to decide upon moves. Graph sent should include you assumptions about nodes hidden by FOG OF WAR.
 */

public class CLIPS_Interface {

	// The name of the CLIPS file where the agent is stored
	public String agentFile;
	public int [] actionToCommit = new int [6]; 

	public CLIPS_Interface(String file) {

		agentFile = file;
	}

	/*VERY IMPORTANT!!!
	 * Because we have for of war in our game graph sent to this agent MUST NOT be the game graph, but copyOfGraph.
	 * You HAVE TO create copy of the world AND estimate certain values (owner of the node, unit counts) FOR EVERY node your agent cannot see! 
	*/
	public int [] CLIPS(copyOfGraph graph, int id) {
		//create CLIPS environment
		Environment clips = new Environment();
		
		PrimitiveValue action; 
		String chosenAction = "?*global*"; 
		
		PrimitiveValue square; 
		String sq = "?*squares*"; 
		
		PrimitiveValue circle; 
		String cr = "?*circles*"; 
		
		PrimitiveValue triangle; 
		String tr = "?*triangles*"; 
		

		
		clips.reset();

		// Here loads the .clp file with your agent rules
		//this file has to contain basic temple for world data
		clips.load(agentFile);
		// this is made to reset the values of the agent
		clips.reset();

		/* Asserting all facts about the current world state
		 * For some reason, asserting all those things using a loop gives me weird results.
		 * I did not want to spend few hours trying to figure out why - so copypasta happened.
		 * Not pretty but works fine.
		 */
		int a,b,c,d,e;
		//asserting current agent ID
		clips.assertString( "(player (ID "+id+"))" );
		
		a = graph.map[0].getAvailableNodes()[0];
		b = graph.map[0].getAvailableNodes()[1];
		c = graph.map[0].getAvailableNodes()[2];
		d = graph.map[0].getAvailableNodes()[3];
		e = graph.map[0].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[0].get_id()+") (belongsTo "+graph.map[0].belongs()+") (squares "+graph.map[0].numberOfSquares()+") (circles "+graph.map[0].numberOfCircles()+") (triangles "+graph.map[0].numberOfTriangles()+") (unitCreationType "+graph.map[0].creationType()+") (unitCreationSpeed "+graph.map[0].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[1].getAvailableNodes()[0];
		b = graph.map[1].getAvailableNodes()[1];
		c = graph.map[1].getAvailableNodes()[2];
		d = graph.map[1].getAvailableNodes()[3];
		e = graph.map[1].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[1].get_id()+") (belongsTo "+graph.map[1].belongs()+") (squares "+graph.map[1].numberOfSquares()+") (circles "+graph.map[1].numberOfCircles()+") (triangles "+graph.map[1].numberOfTriangles()+") (unitCreationType "+graph.map[1].creationType()+") (unitCreationSpeed "+graph.map[1].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[2].getAvailableNodes()[0];
		b = graph.map[2].getAvailableNodes()[1];
		c = graph.map[2].getAvailableNodes()[2];
		d = graph.map[2].getAvailableNodes()[3];
		e = graph.map[2].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[2].get_id()+") (belongsTo "+graph.map[2].belongs()+") (squares "+graph.map[2].numberOfSquares()+") (circles "+graph.map[2].numberOfCircles()+") (triangles "+graph.map[2].numberOfTriangles()+") (unitCreationType "+graph.map[2].creationType()+") (unitCreationSpeed "+graph.map[2].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[3].getAvailableNodes()[0];
		b = graph.map[3].getAvailableNodes()[1];
		c = graph.map[3].getAvailableNodes()[2];
		d = graph.map[3].getAvailableNodes()[3];
		e = graph.map[3].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[3].get_id()+") (belongsTo "+graph.map[3].belongs()+") (squares "+graph.map[3].numberOfSquares()+") (circles "+graph.map[3].numberOfCircles()+") (triangles "+graph.map[3].numberOfTriangles()+") (unitCreationType "+graph.map[3].creationType()+") (unitCreationSpeed "+graph.map[3].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[4].getAvailableNodes()[0];
		b = graph.map[4].getAvailableNodes()[1];
		c = graph.map[4].getAvailableNodes()[2];
		d = graph.map[4].getAvailableNodes()[3];
		e = graph.map[4].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[4].get_id()+") (belongsTo "+graph.map[4].belongs()+") (squares "+graph.map[4].numberOfSquares()+") (circles "+graph.map[4].numberOfCircles()+") (triangles "+graph.map[4].numberOfTriangles()+") (unitCreationType "+graph.map[4].creationType()+") (unitCreationSpeed "+graph.map[4].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[5].getAvailableNodes()[0];
		b = graph.map[5].getAvailableNodes()[1];
		c = graph.map[5].getAvailableNodes()[2];
		d = graph.map[5].getAvailableNodes()[3];
		e = graph.map[5].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[5].get_id()+") (belongsTo "+graph.map[5].belongs()+") (squares "+graph.map[5].numberOfSquares()+") (circles "+graph.map[5].numberOfCircles()+") (triangles "+graph.map[5].numberOfTriangles()+") (unitCreationType "+graph.map[5].creationType()+") (unitCreationSpeed "+graph.map[5].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[6].getAvailableNodes()[0];
		b = graph.map[6].getAvailableNodes()[1];
		c = graph.map[6].getAvailableNodes()[2];
		d = graph.map[6].getAvailableNodes()[3];
		e = graph.map[6].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[6].get_id()+") (belongsTo "+graph.map[6].belongs()+") (squares "+graph.map[6].numberOfSquares()+") (circles "+graph.map[6].numberOfCircles()+") (triangles "+graph.map[6].numberOfTriangles()+") (unitCreationType "+graph.map[6].creationType()+") (unitCreationSpeed "+graph.map[6].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[7].getAvailableNodes()[0];
		b = graph.map[7].getAvailableNodes()[1];
		c = graph.map[7].getAvailableNodes()[2];
		d = graph.map[7].getAvailableNodes()[3];
		e = graph.map[7].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[7].get_id()+") (belongsTo "+graph.map[7].belongs()+") (squares "+graph.map[7].numberOfSquares()+") (circles "+graph.map[7].numberOfCircles()+") (triangles "+graph.map[7].numberOfTriangles()+") (unitCreationType "+graph.map[7].creationType()+") (unitCreationSpeed "+graph.map[7].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[8].getAvailableNodes()[0];
		b = graph.map[8].getAvailableNodes()[1];
		c = graph.map[8].getAvailableNodes()[2];
		d = graph.map[8].getAvailableNodes()[3];
		e = graph.map[8].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[8].get_id()+") (belongsTo "+graph.map[8].belongs()+") (squares "+graph.map[8].numberOfSquares()+") (circles "+graph.map[8].numberOfCircles()+") (triangles "+graph.map[8].numberOfTriangles()+") (unitCreationType "+graph.map[8].creationType()+") (unitCreationSpeed "+graph.map[8].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[9].getAvailableNodes()[0];
		b = graph.map[9].getAvailableNodes()[1];
		c = graph.map[9].getAvailableNodes()[2];
		d = graph.map[9].getAvailableNodes()[3];
		e = graph.map[9].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[9].get_id()+") (belongsTo "+graph.map[9].belongs()+") (squares "+graph.map[9].numberOfSquares()+") (circles "+graph.map[9].numberOfCircles()+") (triangles "+graph.map[9].numberOfTriangles()+") (unitCreationType "+graph.map[9].creationType()+") (unitCreationSpeed "+graph.map[9].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[10].getAvailableNodes()[0];
		b = graph.map[10].getAvailableNodes()[1];
		c = graph.map[10].getAvailableNodes()[2];
		d = graph.map[10].getAvailableNodes()[3];
		e = graph.map[10].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[10].get_id()+") (belongsTo "+graph.map[10].belongs()+") (squares "+graph.map[10].numberOfSquares()+") (circles "+graph.map[10].numberOfCircles()+") (triangles "+graph.map[10].numberOfTriangles()+") (unitCreationType "+graph.map[10].creationType()+") (unitCreationSpeed "+graph.map[10].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") )");
		
		a = graph.map[11].getAvailableNodes()[0];
		b = graph.map[11].getAvailableNodes()[1];
		c = graph.map[11].getAvailableNodes()[2];
		d = graph.map[11].getAvailableNodes()[3];
		e = graph.map[11].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[11].get_id()+") (belongsTo "+graph.map[11].belongs()+") (squares "+graph.map[11].numberOfSquares()+") (circles "+graph.map[11].numberOfCircles()+") (triangles "+graph.map[11].numberOfTriangles()+") (unitCreationType "+graph.map[11].creationType()+") (unitCreationSpeed "+graph.map[11].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") ))");
		
		a = graph.map[12].getAvailableNodes()[0];
		b = graph.map[12].getAvailableNodes()[1];
		c = graph.map[12].getAvailableNodes()[2];
		d = graph.map[12].getAvailableNodes()[3];
		e = graph.map[12].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[12].get_id()+") (belongsTo "+graph.map[12].belongs()+") (squares "+graph.map[12].numberOfSquares()+") (circles "+graph.map[12].numberOfCircles()+") (triangles "+graph.map[12].numberOfTriangles()+") (unitCreationType "+graph.map[12].creationType()+") (unitCreationSpeed "+graph.map[12].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") ))");
		
		a = graph.map[13].getAvailableNodes()[0];
		b = graph.map[13].getAvailableNodes()[1];
		c = graph.map[13].getAvailableNodes()[2];
		d = graph.map[13].getAvailableNodes()[3];
		e = graph.map[13].getAvailableNodes()[4];
		clips.assertString("(node (idNumber "+graph.map[13].get_id()+") (belongsTo "+graph.map[13].belongs()+") (squares "+graph.map[13].numberOfSquares()+") (circles "+graph.map[13].numberOfCircles()+") (triangles "+graph.map[13].numberOfTriangles()+") (unitCreationType "+graph.map[13].creationType()+") (unitCreationSpeed "+graph.map[13].speed()+") (availableAdjacentNodes "+a+" "+b+" "+c+" "+d+" "+e+") ))");

		// Runs the agent, gets the response and puts it on the screen
		clips.run();

		/* action - string value will hold chosen action data - start and target nodes, and action type - maybe even unit counts if you want
		 * You can add more variable like this to your agent following the same procedure like the one with "action" variable.
		 */
	
		//get action iformation
		String data = "000000";
		int start;
		int target;
		int type;

		action = clips.eval(chosenAction); 
		try { 
			//System.out.println(action.stringValue()); 
			data = action.stringValue();
			//System.out.println("data in try " +data); 
			} 
		catch (Exception error){ System.out.println("Invalid string value"); } 
		
		if (data.length() < 6) data = "000000"; //wait - agent will wait if such code is in data
		System.out.println("data out try " +data); 
		
		start = Integer.parseInt(data.charAt(0) +""+ data.charAt(1));
		target = Integer.parseInt(data.charAt(2) +""+ data.charAt(3));
		type =  Integer.parseInt(data.charAt(4)+""+ data.charAt(5));
		
		
		System.out.println("Start " +start); 
		System.out.println("Target " +target); 
		System.out.println("Type " +type); 
		
		if(data.equals("000000")){
			type = -1;
		}
		
		actionToCommit[0] = type;
		actionToCommit[1] = start;
		actionToCommit[2] = target;
		
		//get units information

		
		square = clips.eval(sq); 
		circle = clips.eval(cr); 
		triangle = clips.eval(tr); 
		
		int squares =0;
		int circles =0;
		int triangles =0;
		try { 
			//System.out.println(action.stringValue());
			squares = square.intValue();
			circles = circle.intValue();
			triangles = triangle.intValue();
			//System.out.println("data in try " +data); 
			} 
		catch (Exception error){ System.out.println("Invalid string value"); } 
	
		System.out.println("units out try " +squares+" "+circles+" "+triangles); 
		
		actionToCommit[3] = squares;
		actionToCommit[4] = circles;
		actionToCommit[5] = triangles;
		/*
		String squares = "0";
		String circles = "0";
		String triangles = "0";
		try { 
			//System.out.println(action.stringValue());
			squares = square.stringValue();
			circles = circle.stringValue();
			triangles = triangle.stringValue();
			//System.out.println("data in try " +data); 
			} 
		catch (Exception error){ System.out.println("Invalid string value"); } 
		
		if (squares.length() < 1) squares = "0";
		if (circles.length() < 1) circles = "0";
		if (triangles.length() < 1) triangles = "0";
		
		System.out.println("squares " +Integer.parseInt(squares)); 
		System.out.println("circles " +Integer.parseInt(circles)); 
		System.out.println("triangles " +Integer.parseInt(triangles)); 
		
		System.out.println("units out try " +squares+" "+circles+" "+triangles); 
		
		actionToCommit[3] = Integer.parseInt(squares);
		actionToCommit[4] = Integer.parseInt(circles);
		actionToCommit[5] = Integer.parseInt(triangles);
		*/
		return actionToCommit;
	}
}

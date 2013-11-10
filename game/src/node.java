public class node {
	
	//number of node in graph
	int identificationNumber;
	//number of player node belongs to (0 - neutral)
	int belongsTo;
	
	//units
	int squares;
	int circles;
	int triangles;
	
	//production of units
	//type of created units (1- squares, 2 - circles, 3 - triangles)
	int unitCreationType;
	//amount of turns required to create a given unit amount (0 - no production)
	int unitCreationSpeed;
	
	//node placement and surroundings
	//numbers of nodes adjacent to this one (-1 - no connection)
	int[] adjacentNodes = new int[5];
	//nodes that units can travel to from this node
	int[] availableAdjacentNodes = new int[5];
	
	//possible special abilities of a node
	int special;
	
	//constructors
	public node () {
		System.out.println("You seem to have called a default constructor - you sure what you are doing?");
		System.out.println("By the way no node was created!");
	}
	
	public node (int id, int player, int squ, int cir, int tra, int type, int speed, int a,int b, int c, int d,int e, int spec){
		identificationNumber = id;
		//number of player node belongs to (0 - neutral)
		belongsTo = player;
		
		//units
		squares = squ;
		circles = cir;
		triangles = tra;
		
		//production of units
		unitCreationType = type;
		unitCreationSpeed = speed;
		
		//node placement and surroundings
		adjacentNodes [0]= a;
		adjacentNodes [1]= b;
		adjacentNodes [2]= c;
		adjacentNodes [3]= d;
		adjacentNodes [4]= e;
		
		//upon creation all adjacent nodes are available
		//USE AVAILABLE NODES FOR PATHFIDING!
		availableAdjacentNodes = adjacentNodes;

		
		//possible special abilities of a node
		special = spec;
		System.out.println("Node " +id+ " was succesfully created!");
	}
	//methods for getting data about node
	public int get_id(){
		return identificationNumber;
	}
	public int belongs(){
		return belongsTo;
	}
	
	public int numberOfSquares(){
		return squares;
	}
	public int numberOfCircles(){
		return circles;
	}
	public int numberOfTriangles(){
		return triangles;
	}
	
	public int [] getAdjacentNodes(){
		return adjacentNodes;
	}
	public int[] getAvailableNodes(){
		return availableAdjacentNodes;
	}
	
	public int getSpecial(){
		return special;
	}
	

	
	//methods for setting data for node
	public void set_id(int id){
		identificationNumber = id;
	}
	
	public void setOwner(int owner){
		belongsTo = owner;
	}
	
	public void setNumberOfSquares(int value){
		squares = value;
	}
	
	public void setNumberOfCircles(int value){
		circles= value;
	}
	
	public void setNumberOfTriangles(int value){
		triangles = value;
	}
	
	//this one is here just in case - it probably wont be used - adjacent nodes should not change during simulation
	public void setAdjacentNodes(int a,int b, int c, int d,int e){
		adjacentNodes [0]= a;
		adjacentNodes [1]= b;
		adjacentNodes [2]= c;
		adjacentNodes [3]= d;
		adjacentNodes [4]= e;
	}
	//in case paths between nodes can be destroyed/repaired
	public void setAvailableNodes(int a,int b, int c, int d,int e){
		availableAdjacentNodes [0]= a;
		availableAdjacentNodes [1]= b;
		availableAdjacentNodes [2]= c;
		availableAdjacentNodes [3]= d;
		availableAdjacentNodes [4]= e;
	}
	
	public void changeRoadState(int nodeId, boolean state){
		for(int i = 0; i < 5; i++)
		{
			//check if given node is adjacent to this node
			if (adjacentNodes[i] == nodeId)
				{
					//if given node id is adjacent to this node change connection according to the rule
					if (state == true) availableAdjacentNodes[i] = nodeId; //create connection to given node
					else availableAdjacentNodes[i] = -1; //destroy connection to given node
				}
		}
	}
	
	public boolean isNodeAvailable(int nodeId){
		for(int i = 0; i < 5; i++)
		{
			//check if given node is adjacent to this node
			if (adjacentNodes[i] == nodeId)
				{
					//if given node id is adjacent to this node return true
					if (availableAdjacentNodes[i] == nodeId){
						return true;
					}
				}
		}
		return false;
	}
}

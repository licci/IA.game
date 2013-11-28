public class copyOfGraph {
	
	//This class is designed to store copy of game world
	//it is useful for testing moves etc.
	//you can use it to (for example) check what moves you can do few steps ahead, or predict enemy moves
	//It is needed because in our "graph" class "map" is static - so each object of "graph" will use same "map"
	//it makes it accessible from all classes and is necessary for GUI and other things
	//so to avoid conflicts use this class for all your test purposes
	//at creation this "map" will look the same as main "map", i will leave updating it to you but note that fields that are changing during the game are:
	//owner of a node - player, unit counts - squ,cir,tra and available adjacent nodes (which you extract using the right functions declared in class "node")
	
	//array holding our graph
	public node[] map = new node [14];
	
	public copyOfGraph(){
		initialization();
	}
	public  void initialization ()
	{
		//id,player,squ,cir,tra,type,speed,a,b,c,d,e,f,spec
		//hq1
		map [0] = 	new node (0,1,	10,10,10,	1,1,	1,2,3,-1,-1,	1);
		
		map [1] =	new node (1,0,	10,0,0,		1,1,	0,4,5,-1,-1,	1);
		map [2] =	new node (2,0,	0,10,0,		2,2,	0,4,6,-1,-1,	1);
		map [3] =	new node (3,0,	0,0,10,		3,3,	0,5,6,-1,-1,	1);
		
		map [4] =	new node (4,0,	0,0,10,		3,3,	1,2,8,-1,-1,	1);
		map [5] =	new node (5,0,	0,10,0,		2,2,	1,3,7,8,9,		1);
		map [6] =	new node (6,0,	10,0,0,		1,1,	2,3,8,-1,-1,	1);
		
		map [7] =	new node (7,0,	10,0,0,		1,1,	5,10,11,-1,-1,	1);
		map [8] =	new node (8,0,	0,10,0,		2,2,	4,5,6,10,12,	1);
		map [9] =	new node (9,0,	0,0,10,		3,3,	5,11,12,-1,-1,	1);
		
		map [10] =	new node (10,0,	0,0,10,		3,3,	7,8,13,-1,-1,	1);
		map [11] =	new node (11,0,	0,10,0,		2,2,	7,9,13,-1,-1,	1);
		map [12] =	new node (12,0,	10,0,0,		1,1,	8,9,13,-1,-1,	1);
		
		//hq2
		map [13] = 	new node (13,2,	10,10,10,	1,1,	10,11,12,-1,-1,	1);
	}
	
	}

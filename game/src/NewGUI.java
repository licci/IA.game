import processing.core.*;

public class NewGUI extends PApplet
{
	private static final long serialVersionUID = 1L;
	
	int mouseSeparation = 200; //distance from the mouse
	int separation  = 100; //expected length of connections lines (edges)
	int repelRadius = 300; //distance from which repulsion works
	float acceleration = (float) 0.05; //fixed acceleration coefficient
	
	int radius = 60; //node radius
	int N_number = 14; //total number of nodes
	PVector[] Nodes = new PVector[N_number]; //array of nodes (PVector object)

	public void setup() 
	{
		size(1000, 800); //screen resolution (x,y)
		smooth(); //anti-aliasing function
		frameRate(23); //expected frame rate in FPS
		
		try {
		    Thread.sleep(100);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		Nodes[0]  = new PVector(1 * width / 7,3 * height / 6);
		Nodes[1]  = new PVector(2 * width / 7,2 * height / 6);
		Nodes[2]  = new PVector(2 * width / 7,3 * height / 6);
		Nodes[3]  = new PVector(2 * width / 7,4 * height / 6);
		Nodes[4]  = new PVector(3 * width / 7,2 * height / 6);
		Nodes[4]  = new PVector(3 * width / 7,2 * height / 6);
		Nodes[5]  = new PVector(3 * width / 7,3 * height / 6);
		Nodes[6]  = new PVector(3 * width / 7,4 * height / 6);
		Nodes[7]  = new PVector(4 * width / 7,2 * height / 6);
		Nodes[8]  = new PVector(4 * width / 7,3 * height / 6);
		Nodes[9]  = new PVector(4 * width / 7,4 * height / 6);
		Nodes[10] = new PVector(5 * width / 7,2 * height / 6);
		Nodes[11] = new PVector(5 * width / 7,3 * height / 6);
		Nodes[12] = new PVector(5 * width / 7,4 * height / 6);
		Nodes[13] = new PVector(6 * width / 7,3 * height / 6);
	}

	public void draw() //the main drawing loop
	{
		background(190, 206, 250); //fill the screen with given RGB color
		
		repelAllNodes(); //push all nodes away from each other
		makeEdges(); //pull connected nodes to each other and draw edges between them

		for (int i=0; i<N_number; i++) //loop through the total number of nodes
		{
			drawNode(i); //draw a single node
			escapeMouse(i); //push a node away from the mouse
			keepInside(i); //lock a node inside the screen area
		}
		
    	if(Nodes[0].x>70) Nodes[0].x=70; //lock node 0 close to left edge
    	if(Nodes[13].x<width-70) Nodes[13].x=width-70; //lock node 13 close to right edge
    	
    	fill(130,140,149); //gray
    	text("Circles",width/2-40,30);
		fill(120,85,70); //beige
		text("Squares",width/2-40,60);
		fill(10,140,160); //blue
		text("Triangles",width/2-40,90);
		fill(255,0,0);
		text("RED",40,60);
		fill(0,0,255);
		text("BLUE",width-100,60);
	}
	
	public void makeEdges()
	{
		strokeWeight(2); //thickness of edge
		
    	connect(0,1);
    	connect(0,2);
    	connect(0,3);
    	connect(1,4);
    	connect(1,5);
    	connect(2,4);
    	connect(2,6);
    	connect(3,5);
    	connect(3,6);
    	connect(4,8);
    	connect(5,7);
    	connect(5,8);
    	connect(5,9);
    	connect(6,8);
    	connect(7,10);
    	connect(7,11);
    	connect(8,10);
    	connect(8,12);
    	connect(9,11);
    	connect(9,12);
    	connect(10,13);
    	connect(11,13);
    	connect(12,13);
	}

	public void drawElipses(int color, int node, int[] angles, int allUnits) 
	{
		switch (color) 
		{
		case 0:
			noStroke();
			fill(255); // white
			break;
		case 1:
			strokeWeight(4);
			stroke(255, 0, 0); //red
			fill(255); // white
			break;
		case 2:
			strokeWeight(4);
			stroke(0, 0, 255); //blue
			fill(255); // white
			break;
		}

		if (allUnits != 0) 
		{
			float lastAngle = 0;
			
			fill(130,140,149); //gray
			arc(Nodes[node].x, Nodes[node].y, radius, radius, lastAngle, lastAngle + radians(angles[0]));
			lastAngle += radians(angles[0]);
		
			fill(120,85,70); //beige
			arc(Nodes[node].x, Nodes[node].y, radius, radius, lastAngle, lastAngle + radians(angles[1]));
			lastAngle += radians(angles[1]);
		
			fill(10,140,160); //blue
			arc(Nodes[node].x, Nodes[node].y, radius, radius, lastAngle, (float)(2*Math.PI));
		} 
		else 
		{
			ellipse(Nodes[node].x, Nodes[node].y, radius, radius);
		}
	}
	
	//draw a single node
    void drawNode(int i)
    {
    	//ellipse(Nodes[i].x,Nodes[i].y,radius,radius); //draw Node number "i"
    	
    	int allUnits = 0; //total number of units in a single node
		int circlesAngle = 0;
		int squaresAngle = 0;
		int trianglesAngle = 0;
    	allUnits = graph.map[i].numberOfSquares()
				 + graph.map[i].numberOfCircles()
				 + graph.map[i].numberOfTriangles();
		
		if (allUnits > 0)
		{
			circlesAngle = (360 * graph.map[i].numberOfCircles()) / allUnits;
			squaresAngle = (360 * graph.map[i].numberOfSquares()) / allUnits;
			trianglesAngle = (360 * graph.map[i].numberOfTriangles()) / allUnits;
		}
		
		int[] angles = { circlesAngle, squaresAngle, trianglesAngle };

		textSize(25);
		fill(120,85,70); //beige
		text(graph.map[i].numberOfSquares()  ,Nodes[i].x+radius/2, Nodes[i].y+radius/2);
		fill(130,140,149); //gray
		text(graph.map[i].numberOfCircles()  ,Nodes[i].x+radius/2, Nodes[i].y+radius);
		fill(10,140,160); //blue
		text(graph.map[i].numberOfTriangles(),Nodes[i].x+radius/2, Nodes[i].y+(float)(radius*1.5));
		drawElipses(graph.map[i].belongs(), i, angles, allUnits);
    }
    
    //push node away from the mouse pointer coordinates
    void escapeMouse(int i)
    {
	    float distance  = dist( Nodes[i].x, Nodes[i].y, mouseX, mouseY ); //calculate distance between Node and mouse
	    if(distance < mouseSeparation) //if current distance is lower than required
	    {
	    	float angle     = atan2( Nodes[i].y - mouseY, Nodes[i].x - mouseX); //calculate angle between Node and mouse
	    	float move	= mouseSeparation - distance ; //calculate difference between expected and current distances
	    	Nodes[i].x += move * cos(angle) * acceleration ;
	    	Nodes[i].y += move * sin(angle) * acceleration ;	
	    }
    }
    
    //lock node position inside the visible area
    void keepInside(int i)
    {
    	if(Nodes[i].x<radius/2) Nodes[i].x=radius/2;
    	if(Nodes[i].y<radius/2) Nodes[i].y=radius/2;
    	if(Nodes[i].x>width-radius/2) Nodes[i].x=width-radius/2;
    	if(Nodes[i].y>height-radius/2) Nodes[i].y=height-radius/2;
    }
    
    //repel all nodes from each other (skipping duplicates or apply this to a single node)
    void repelAllNodes()
    {
		for(int i=0; i<N_number; i++)
    	{
    		 for(int j=i+1; j<N_number;j++)
    		 {
    			 repel(Nodes[i],Nodes[j],repelRadius); //call repel function for a node
    		 }
    	}
    }
	
    //applying repulsion forces to a pair of nodes (works if current distance is lower than expected)
    void repel(PVector vector1, PVector vector2, float expectedDistance)
    {
	    float distance  = dist( vector2.x, vector2.y, vector1.x, vector1.y ); //calculate distance between Nodes
	    if(distance < expectedDistance) //if current distance is lower than required
	    {
	    	float angle = atan2( vector2.y - vector1.y, vector2.x - vector1.x); //calculate angle between Nodes
	    	float move	= expectedDistance - distance ; //calculate difference between expected and current distances
	    	vector2.x += 0.5 * move * cos(angle) * acceleration ;
	    	vector2.y += 0.5 * move * sin(angle) * acceleration ;
	    	vector1.x -= 0.5 * move * cos(angle) * acceleration ;
	    	vector1.y -= 0.5 * move * sin(angle) * acceleration ;
	    }
    }
    
    //applying forces to a pair of nodes (works when current distance is different than expected)
    void keepDistanceElastic(PVector vector1, PVector vector2, float expectedDistance)
    {
	    float distance  = dist( vector2.x, vector2.y, vector1.x, vector1.y ); //calculate distance between Nodes
	    if(distance != expectedDistance) //if current distance is different from expected
	    {
	    	float angle = atan2( vector2.y - vector1.y, vector2.x - vector1.x); //calculate angle between Nodes
	    	float move	= expectedDistance - distance ; //calculate difference between expected and current distances
	    	vector2.x += 0.5 * move * cos(angle) * acceleration ;
	    	vector2.y += 0.5 * move * sin(angle) * acceleration ;
	    	vector1.x -= 0.5 * move * cos(angle) * acceleration ;
	    	vector1.y -= 0.5 * move * sin(angle) * acceleration ;
	    }
    }
    
    //create a connection
    void connect(int First, int Second)
    {
    	//draw edge if the node is available

    	stroke(80, 100, 160, 255); //edge color
    	//make edge invisible (background color) if not available
    	//else stroke(80, 100, 160, 0); //transparent edge color
		if (graph.map[First].isNodeAvailable(Second))
		{
	    	line(Nodes[First].x,Nodes[First].y,Nodes[Second].x,Nodes[Second].y); //draw lines to show the connection
		    keepDistanceElastic(Nodes[First],Nodes[Second],separation); //attraction forces between connected nodes
		}
    }
}

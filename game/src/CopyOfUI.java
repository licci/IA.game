import processing.core.*;


public class CopyOfUI extends PApplet
{
	private static final long serialVersionUID = 1L;
	
	int color = 0;
	
	int radius = 60; //node radius
	int N_number = 14; //total number of nodes
	PVector[] Nodes = new PVector[N_number]; //array of nodes (PVector object)

	public void setup() 
	{
		size(800, 800);
		background(190, 206, 250);
		//stroke(80, 100, 160);
		smooth();
		frameRate(23);
		
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

	public void draw() 
	{
		int allUnits = 0;
		int circlesAngle = 0;
		int squaresAngle = 0;
		int trianglesAngle = 0;

		drawEdges();

		for (int i = 0; i < N_number; i++)
		{
			allUnits = graph.map[i].numberOfSquares()
					 + graph.map[i].numberOfCircles()
					 + graph.map[i].numberOfTriangles();
			
			if (allUnits != 0) 
			{
				circlesAngle = (360 * graph.map[i].numberOfCircles()) / allUnits;
				squaresAngle = (360 * graph.map[i].numberOfSquares()) / allUnits;
				trianglesAngle = (360 * graph.map[i].numberOfTriangles()) / allUnits;
			}
			
			int[] angles = { circlesAngle, squaresAngle, trianglesAngle };

			if (graph.map[i].belongs() == 0) // if node does no belong to any player
			{
				color = 0;
				drawElipses(color, i, angles, allUnits);
			} 
			else if (graph.map[i].belongs() == 1) // if node belongs to player1
			{
				color = 1;
				drawElipses(color, i, angles, allUnits);
			} 
			else if (graph.map[i].belongs() == 2) // if node belongs to player2
			{
				color = 2;
				drawElipses(color, i, angles, allUnits);
			}
		}
	}
	
	public void drawEdges() 
	{
		strokeWeight(2); //thickness
		stroke(80, 100, 160); //color
		
		line(Nodes[0].x, Nodes[0].y, Nodes[1].x, Nodes[1].y);
		line(Nodes[0].x, Nodes[0].y, Nodes[2].x, Nodes[2].y);
		line(Nodes[0].x, Nodes[0].y, Nodes[3].x, Nodes[3].y);
		line(Nodes[1].x, Nodes[1].y, Nodes[4].x, Nodes[4].y);
		line(Nodes[1].x, Nodes[1].y, Nodes[5].x, Nodes[5].y);
		line(Nodes[2].x, Nodes[2].y, Nodes[4].x, Nodes[4].y);
		line(Nodes[2].x, Nodes[2].y, Nodes[6].x, Nodes[6].y);
		line(Nodes[3].x, Nodes[3].y, Nodes[5].x, Nodes[5].y);
		line(Nodes[3].x, Nodes[3].y, Nodes[6].x, Nodes[6].y);
		line(Nodes[4].x, Nodes[4].y, Nodes[8].x, Nodes[8].y);
		line(Nodes[5].x, Nodes[5].y, Nodes[7].x, Nodes[7].y);
		line(Nodes[5].x, Nodes[5].y, Nodes[8].x, Nodes[8].y);
		line(Nodes[5].x, Nodes[5].y, Nodes[9].x, Nodes[9].y);
		line(Nodes[6].x, Nodes[6].y, Nodes[8].x, Nodes[8].y);
		line(Nodes[7].x, Nodes[7].y, Nodes[10].x, Nodes[10].y);
		line(Nodes[7].x, Nodes[7].y, Nodes[11].x, Nodes[11].y);
		line(Nodes[8].x, Nodes[8].y, Nodes[10].x, Nodes[10].y);
		line(Nodes[8].x, Nodes[8].y, Nodes[12].x, Nodes[12].y);
		line(Nodes[9].x, Nodes[9].y, Nodes[11].x, Nodes[11].y);
		line(Nodes[9].x, Nodes[9].y, Nodes[12].x, Nodes[12].y);
		line(Nodes[10].x, Nodes[10].y, Nodes[13].x, Nodes[13].y);
		line(Nodes[11].x, Nodes[11].y, Nodes[13].x, Nodes[13].y);
		line(Nodes[12].x, Nodes[12].y, Nodes[13].x, Nodes[13].y);
	}

	public void drawElipses(int color, int node, int[] angles, int allUnits) 
	{
		float lastAngle = 0;

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
			for (int i = 0; i < angles.length; i++) 
			{
				float gray = map(i, 0, angles.length, 0, 255);
				fill(gray);
				arc(Nodes[node].x, Nodes[node].y, radius, radius, lastAngle, lastAngle + radians(angles[i]));
				textSize(30);
				fill(255, 255, 255);
				text(node,Nodes[node].x+radius/2, Nodes[node].y+radius/2);
				lastAngle += radians(angles[i]);
			}
		} 
		else 
		{
			ellipse(Nodes[node].x, Nodes[node].y, radius, radius);
		}

	}
}

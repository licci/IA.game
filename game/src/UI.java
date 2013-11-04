import processing.core.*;

public class UI extends PApplet 
{
	UIGraph _UIGraph = new UIGraph(this);
	//graph graph = new graph();
	int color = 0;

	public void setup() 
	{
		//graph.initialization();

		size(800, 800);
		background(135, 206, 250);
		// stroke(80, 100, 160);
		smooth();
		// frameRate(2);
	}

	public void draw() 
	{
		int allUnits = 0;
		int circlesAngle = 0;
		int squaresAngle = 0;
		int trianglesAngle = 0;

		_UIGraph.drawEdges();

		for (int i = 0; i < 14; i++) 
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
				_UIGraph.drawElipses(color, i, angles, allUnits);
			} 
			else if (graph.map[i].belongs() == 1) // if node belongs to player1
			{
				color = 1;
				_UIGraph.drawElipses(color, i, angles, allUnits);
			} 
			else if (graph.map[i].belongs() == 2) // if node belongs to player2
			{
				color = 2;
				_UIGraph.drawElipses(color, i, angles, allUnits);
			}
		}
	}
}

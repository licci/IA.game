import processing.core.*;
import java.util.*;

public class UIGraph 
{
	PApplet parent; // The parent PApplet that we will render ourselves onto

	int width = 800;
	int height = 800;

	// node 0
	int x0 = 1 * width / 7;
	int y0 = 3 * height / 6;
	// node 1
	int x1 = 2 * width / 7;
	int y1 = 2 * height / 6;
	// node 2
	int x2 = 2 * width / 7;
	int y2 = 3 * height / 6;
	// node 3
	int x3 = 2 * width / 7;
	int y3 = 4 * height / 6;
	// node 4
	int x4 = 3 * width / 7;
	int y4 = 2 * height / 6;
	// node 5
	int x5 = 3 * width / 7;
	int y5 = 3 * height / 6;
	// node 6
	int x6 = 3 * width / 7;
	int y6 = 4 * height / 6;
	// node 7
	int x7 = 4 * width / 7;
	int y7 = 2 * height / 6;
	// node 8
	int x8 = 4 * width / 7;
	int y8 = 3 * height / 6;
	// node 9
	int x9 = 4 * width / 7;
	int y9 = 4 * height / 6;
	// node 10
	int x10 = 5 * width / 7;
	int y10 = 2 * height / 6;
	// node 11
	int x11 = 5 * width / 7;
	int y11 = 3 * height / 6;
	// node 12
	int x12 = 5 * width / 7;
	int y12 = 4 * height / 6;
	// node 13
	int x13 = 6 * width / 7;
	int y13 = 3 * height / 6;

	UIGraph(PApplet p) 
	{
		parent = p;
	}

	public void drawEdges() 
	{
		parent.strokeWeight(1); //thickness
		parent.stroke(80, 100, 160); //color
		
		parent.line(x0, y0, x1, y1);
		parent.line(x0, y0, x2, y2);
		parent.line(x0, y0, x3, y3);
		parent.line(x1, y1, x4, y4);
		parent.line(x1, y1, x5, y5);
		parent.line(x2, y2, x4, y4);
		parent.line(x2, y2, x6, y6);
		parent.line(x3, y3, x5, y5);
		parent.line(x3, y3, x6, y6);
		parent.line(x4, y4, x8, y8);
		parent.line(x5, y5, x7, y7);
		parent.line(x5, y5, x8, y8);
		parent.line(x5, y5, x9, y9);
		parent.line(x6, y6, x8, y8);
		parent.line(x7, y7, x10, y10);
		parent.line(x7, y7, x11, y11);
		parent.line(x8, y8, x10, y10);
		parent.line(x8, y8, x12, y12);
		parent.line(x9, y9, x11, y11);
		parent.line(x9, y9, x12, y12);
		parent.line(x10, y10, x13, y13);
		parent.line(x11, y11, x13, y13);
		parent.line(x12, y12, x13, y13);
	}

	public void drawElipses(int color, int node, int[] angles, int allUnits) 
	{
		float lastAngle = 0;

		switch (color) 
		{
		case 0:
			parent.noStroke();
			parent.fill(255); // white
			break;
		case 1:
			parent.strokeWeight(4);
			parent.stroke(255, 0, 0);
			parent.fill(255); // white

			break;
		case 2:
			parent.strokeWeight(4);
			parent.stroke(0, 255, 0);
			parent.fill(255); // white
			break;
		}

		switch (node) 
		{
		case 0:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x0, y0, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x0, y0, 60, 60);
			}
			break;
		case 1:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x1, y1, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x1, y1, 60, 60);
			}
			break;
		case 2:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x2, y2, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x2, y2, 60, 60);
			}
			break;
		case 3:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x3, y3, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x3, y3, 60, 60);
			}
			break;
		case 4:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x4, y4, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x4, y4, 60, 60);
			}
			break;
		case 5:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x5, y5, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x5, y5, 60, 60);
			}
			break;
		case 6:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x6, y6, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x6, y6, 60, 60);
			}
			break;
		case 7:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x7, y7, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x7, y7, 60, 60);
			}
			break;
		case 8:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x8, y8, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x8, y8, 60, 60);
			}
			break;
		case 9:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x9, y9, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x9, y9, 60, 60);
			}
			break;
		case 10:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x10, y10, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x10, y10, 60, 60);
			}
			break;
		case 11:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x11, y11, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x11, y11, 60, 60);
			}
			break;
		case 12:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x12, y12, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x12, y12, 60, 60);
			}
			break;
		case 13:
			if (allUnits != 0) 
			{
				for (int i = 0; i < angles.length; i++) 
				{
					float gray = parent.map(i, 0, angles.length, 0, 255);
					parent.fill(gray);
					parent.arc(x13, y13, 60, 60, lastAngle, lastAngle + parent.radians(angles[i]));
					lastAngle += parent.radians(angles[i]);
				}
			} 
			else 
			{
				parent.ellipse(x13, y13, 60, 60);
			}
			break;
		}
	}
}

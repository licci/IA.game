import processing.core.PApplet;

public class main {
	public static graph graph = new graph();
	public static void main(String[] args) {
		
		graph.initialization();
		
		PApplet.main(new String[] { "--present", "UI" });

		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 1
		
		graph.map[1].setNumberOfTriangles(50);
		graph.map[1].setOwner(1);
		
		graph.map[12].setNumberOfTriangles(20);
		graph.map[12].setNumberOfCircles(30);
		graph.map[12].setOwner(2);
		
		PApplet.main(new String[] { "--present", "UI" });
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 2
		
		graph.map[5].setNumberOfSquares(2);
		graph.map[5].setOwner(1);
		
		graph.map[10].setNumberOfSquares(25);
		graph.map[10].setNumberOfCircles(8);
		graph.map[10].setOwner(2);
		
		PApplet.main(new String[] { "--present", "UI" });
		
		//System.out.println("Hello World!");
		
//		graph Test = new graph();
//		Test.initialization();
//		
//		int tes[] = new int [5];
//		tes = Test.map[5].getAvailableNodes();
//		
//		System.out.println("Possible roads lead to nodes:");
//		for(int i = 0; i < 5; i++)
//		{
//			if (tes[i]>=0) System.out.println(tes [i]);
//		}
//		System.out.println("Destroying bridge to node 7");
//		Test.map[5].changeRoadState(7, false);
//		System.out.println("Possible roads lead to nodes:");
//		for(int i = 0; i < 5; i++)
//		{
//			if (tes[i]>=0) System.out.println(tes [i]);
//		}
	}

}

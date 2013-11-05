import processing.core.PApplet;

public class main {
	public static graph graph = new graph();
	public static void main(String[] args) {
		
		graph.initialization();
		
		PApplet.main(new String[] { "--present", "CopyOfUI" });

		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 1
		graph.map[1].setNumberOfTriangles(50);
		graph.map[1].setOwner(1);
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 2
		graph.map[12].setNumberOfTriangles(20);
		graph.map[12].setNumberOfCircles(30);
		graph.map[12].setOwner(2);
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 3
		graph.map[5].setNumberOfSquares(2);
		graph.map[5].setOwner(1);
		
		
		try {
		    Thread.sleep(2000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//step 4
		graph.map[10].setNumberOfSquares(25);
		graph.map[10].setNumberOfCircles(8);
		graph.map[10].setOwner(2);
	}

}

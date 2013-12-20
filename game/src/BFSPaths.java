import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;

public class BFSPaths {

	final int NUMBER_OF_VERTICES = 14; 
	
	// wierzcholki z ktorych mozna sie dostac do biezacego
	// okreslonego indeksem tablicy
	private int[] edgeTo;

	private boolean[] visited; //table of visited nodes
	
	private int startingNode;

	private Queue<Integer> priorityQueue;

	//begining stadium, adds starting node to the queue
	public BFSPaths(int startingNode) 
	{
		this.startingNode = startingNode;
		
		System.out.println("Starting Node = " + startingNode);
		
		edgeTo = new int[NUMBER_OF_VERTICES];
		visited = new boolean[NUMBER_OF_VERTICES];
		priorityQueue = new PriorityQueue<Integer>(NUMBER_OF_VERTICES);
		priorityQueue.offer(startingNode);
		bfs(startingNode);
	}


	//true jesli istnieje sciezka z wierzcholka startingNode do goalVertex
	public boolean hasPathTo(int goalVertex) 
	{
		return visited[goalVertex];
	}


	//return stos wierzcholkow prowadzacych ze startingNode do goalVertex
	//jesli sciezka nie istnieje zwracana jest pusta kolekcja
	public Iterable<Integer> getPathTo(int goalVertex) 
	{
		Deque<Integer> path = new ArrayDeque<Integer>();
		
		if (!hasPathTo(goalVertex)) //jesli nie istnieje sciezka zwroc pusta sciezke
		{
			return path;
		}
		
		for (int w = goalVertex; w != startingNode; w = edgeTo[w]) //dopoki istnieje wierzcholek dodawaj go do stosu
		{
			path.push(w);
		}
		
		path.push(startingNode); // dodaj na koniec krawedz zrodlowa
		return path;
	}

	private void bfs(int node) 
	{
		visited[node] = true;
		
		//System.out.println("Vertex = " + node + " visited.");

		//priorityQueue.offer(node); //dodajemy wierzcholek zrodlowy do kolejki (chyba niepotrzebne)
		//System.out.println("Vertex = " + node + " added to priority queue.");

		// dopoki kolejka nie jest pusta, wybieramy krawedz o najnizszym
		// priorytecie
		// i oznaczamy jako odwiedzone wierzcholki z listy sasiedztwa usuwanego
		// wierzcholka
		// oraz dodajemy wierzcholki z listy sasiedztwa do kolejki
		while (!priorityQueue.isEmpty()) 
		{
			//System.out.println("Queue is not empty. It has " + priorityQueue.size() + " elements.");
			//System.out.println("Queue elements: " + priorityQueue);
			
			int processedNode = priorityQueue.remove();
			
			//System.out.println("Removing " + processedNode + " from queue.");
			
			//obejscie -1 w availableAdjecentNodes
			ArrayList<Integer> availableNodes = new ArrayList<Integer>();
			for (int i=0; i<5; i++)
			{
				if (graph.map[processedNode].adjacentNodes[i] != -1 && graph.map[processedNode].isNodeAvailable(graph.map[processedNode].adjacentNodes[i]) == true)
				{
					availableNodes.add(graph.map[processedNode].adjacentNodes[i]);
				}
			}
			//System.out.println("Available nodes for " + processedNode + " are " + availableNodes);
			//

			for (int processedNeighbour : availableNodes) 
			{
				if (!visited[processedNeighbour]) 
				{
					//System.out.println("Node " + processedNeighbour + " wasn't visited before.");
					edgeTo[processedNeighbour] = processedNode;
					visited[processedNeighbour] = true;
					//System.out.println("Visit node " + processedNeighbour);
					priorityQueue.offer(processedNeighbour);
				}
			}
		}
	}
}

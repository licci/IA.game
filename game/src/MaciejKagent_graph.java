
public class MaciejKagent_graph {

	//array holding our graph
	public static MaciejKagent_node[][] decisionMap = new MaciejKagent_node [14][14];
	
	public  MaciejKagent_graph()
	{
		
		
		for(int i=0;i<14;i++)
		{
			for(int j=0;j<14;j++)
				decisionMap[i][j] = new MaciejKagent_node(i,j);
		}
		
		
	}
}

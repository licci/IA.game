


//Komentarz

public class WorldUpdate {

	 static int times = 0;
	
	
	
	 static void Update()
		{
		 
		 times++;
         if(times>6) times=0;
         
			int type,speed;
			for(int i=0;i<13;i++)
			{
				type=graph.map[i].creationType();
				speed=graph.map[i].speed();
				switch(speed)
				{
				case 1:  Add(type,i);
					break;
				case 2: if(times % 2 == 0)  Add(type,i);
					break;
				case 3: if(times==3||times==6) Add(type,i);
				}
				
				
				
			}
		}
	 
	
	 static void Add(int type, int i)
	    {
	    	int amount;
			switch(type){
	        case 1:
	            amount=graph.map[i].numberOfSquares();
	            amount++;
	            graph.map[i].setNumberOfSquares(amount);
	            break;

	        case 2:
	        	amount=graph.map[i].numberOfCircles();
	            amount++;
	            graph.map[i].setNumberOfCircles(amount);
	            break;

	        case 3:
	        	amount=graph.map[i].numberOfTriangles();
	            amount++;
	            graph.map[i].setNumberOfTriangles(amount);
	            	break;
	    }
	    }
}

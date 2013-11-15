


//Komentarz

public class WorldUpdate {

	 static int times = 0;
	
	static int roadTimer=0;
	static int roadRand=0;
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
			dynamicRoad();
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
	 
	 static void dynamicRoad()
	 {
		 roadTimer++;
			if(roadTimer==1)
			{ 
				roadRand=(int) (Math.random() * 14);
				switch(roadRand)
				{
				case 0: graph.map[1].changeRoadState(4,false); break;
				case 1: graph.map[4].changeRoadState(2,false); break;
				case 2: graph.map[2].changeRoadState(6,false); break;
				case 3: graph.map[3].changeRoadState(6,false); break;
				case 4: graph.map[3].changeRoadState(5,false); break;
				case 5: graph.map[4].changeRoadState(8,false); break;
				case 6: graph.map[5].changeRoadState(7,false); break;
				case 7: graph.map[5].changeRoadState(8,false); break;
				case 8: graph.map[5].changeRoadState(9,false); break;
				case 9: graph.map[6].changeRoadState(8,false); break;
				case 10: graph.map[8].changeRoadState(10,false); break;
				case 11: graph.map[7].changeRoadState(11,false); break;
				case 12: graph.map[7].changeRoadState(10,false); break;
				case 13: graph.map[8].changeRoadState(12,false); break;
				case 14: graph.map[9].changeRoadState(11,false); break;
				}
			}else if(roadTimer==5){
				roadTimer=0;
				switch(roadRand)
				{
				case 0: graph.map[1].changeRoadState(4,true); break;
				case 1: graph.map[4].changeRoadState(2,true); break;
				case 2: graph.map[2].changeRoadState(6,true); break;
				case 3: graph.map[3].changeRoadState(6,true); break;
				case 4: graph.map[3].changeRoadState(5,true); break;
				case 5: graph.map[4].changeRoadState(8,true); break;
				case 6: graph.map[5].changeRoadState(7,true); break;
				case 7: graph.map[5].changeRoadState(8,true); break;
				case 8: graph.map[5].changeRoadState(9,true); break;
				case 9: graph.map[6].changeRoadState(8,true); break;
				case 10: graph.map[8].changeRoadState(10,true); break;
				case 11: graph.map[7].changeRoadState(11,true); break;
				case 12: graph.map[7].changeRoadState(10,true); break;
				case 13: graph.map[8].changeRoadState(12,true); break;
				case 14: graph.map[9].changeRoadState(11,true); break;
				}
			}
	 }
}

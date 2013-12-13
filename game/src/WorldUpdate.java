


//Updating the world in time, for every turn. Basically adding new units in each node depending on the speed and the type of their production.

public class WorldUpdate {

	 static int times = 0; //Cycle of production, 
	
	static int roadTimer=0; // When timer will reach 5, the road will appear again.
	static int roadRand=0; //Which road will disappear
	 static void Update()
		{
		 //All production speeds can be divide by 6, that is why we look at their multiplication 1,2 and 3. That is why, while every time times 
		 //grows speed 1 nodes product units. Every even cycle speed 2 nodes product units, and twice a cycle speed 3 nodes product units.
		 times++;
         if(times>6) times=0;
         //We are taking speed and type production for each node and look at the each node of the graph. 
			int type,speed;
			for(int i=0;i<14;i++)
			{
				
				type=graph.map[i].creationType();
				speed=graph.map[i].speed();
				//Depending on the speed of the production we add units.
				switch(speed)
				{
				case 1:  Add(type,i);
					break;
				case 2: if(times % 2 == 0)  Add(type,i);
					break;
				case 3: if(times==3||times==6) Add(type,i);
				}
				
			}
			//Changing roads in the graph.
			dynamicRoad();
		}
	 
	//Depending on the type of production we add a specific unit type to the chosen node.
	 static void Add(int type, int i)
	    {
	    	int amount; //Amount of chosen units in the node.
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
		 //Changing the timer
		 roadTimer++;
			if(roadTimer==1)
			{   
				
				//FOR STATISTICS
				Statistics.P1.addRoadBroken();
				Statistics.P2.addRoadBroken();
				//END FOR STATISTICS
				
				//Making a random road disappear. 
				roadRand=(int) (Math.random() * 14);
				switch(roadRand)
				{
				case 0: graph.map[1].changeRoadState(4,false); graph.map[4].changeRoadState(1,false); break;
				case 1: graph.map[4].changeRoadState(2,false); graph.map[2].changeRoadState(4,false);break;
				case 2: graph.map[2].changeRoadState(6,false); graph.map[6].changeRoadState(2,false);break;
				case 3: graph.map[3].changeRoadState(6,false); graph.map[6].changeRoadState(3,false); break;
				case 4: graph.map[3].changeRoadState(5,false); graph.map[5].changeRoadState(3,false);break;
				case 5: graph.map[4].changeRoadState(8,false); graph.map[8].changeRoadState(4,false);break;
				case 6: graph.map[5].changeRoadState(7,false); graph.map[7].changeRoadState(5,false);break;
				case 7: graph.map[5].changeRoadState(8,false); graph.map[8].changeRoadState(5,false);break;
				case 8: graph.map[5].changeRoadState(9,false); graph.map[9].changeRoadState(5,false);break;
				case 9: graph.map[6].changeRoadState(8,false); graph.map[8].changeRoadState(6,false);break;
				case 10: graph.map[8].changeRoadState(10,false); graph.map[10].changeRoadState(8,false);break;
				case 11: graph.map[7].changeRoadState(11,false); graph.map[11].changeRoadState(7,false);break;
				case 12: graph.map[7].changeRoadState(10,false); graph.map[10].changeRoadState(7,false);break;
				case 13: graph.map[8].changeRoadState(12,false); graph.map[12].changeRoadState(8,false);break;
				case 14: graph.map[9].changeRoadState(11,false); graph.map[11].changeRoadState(9,false);break;
				}
			}else if(roadTimer==5){
				//After some period of time, the road appears again, and the timer is set to 0 again.
				roadTimer=0;
				switch(roadRand)
				{
				case 0: graph.map[1].changeRoadState(4,true); graph.map[4].changeRoadState(1,true);break;
				case 1: graph.map[4].changeRoadState(2,true); graph.map[2].changeRoadState(4,true);break;
				case 2: graph.map[2].changeRoadState(6,true); graph.map[6].changeRoadState(2,true);break;
				case 3: graph.map[3].changeRoadState(6,true); graph.map[6].changeRoadState(3,true);break;
				case 4: graph.map[3].changeRoadState(5,true); graph.map[5].changeRoadState(3,true);break;
				case 5: graph.map[4].changeRoadState(8,true); graph.map[8].changeRoadState(4,true);break;
				case 6: graph.map[5].changeRoadState(7,true); graph.map[7].changeRoadState(5,true);break;
				case 7: graph.map[5].changeRoadState(8,true); graph.map[8].changeRoadState(5,true);break;
				case 8: graph.map[5].changeRoadState(9,true); graph.map[9].changeRoadState(5,true);break;
				case 9: graph.map[6].changeRoadState(8,true); graph.map[8].changeRoadState(6,true);break;
				case 10: graph.map[8].changeRoadState(10,true); graph.map[10].changeRoadState(8,true);break;
				case 11: graph.map[7].changeRoadState(11,true); graph.map[11].changeRoadState(7,true);break;
				case 12: graph.map[7].changeRoadState(10,true); graph.map[10].changeRoadState(7,true);break;
				case 13: graph.map[8].changeRoadState(12,true); graph.map[12].changeRoadState(8,true);break;
				case 14: graph.map[9].changeRoadState(11,true); graph.map[11].changeRoadState(9,true);break;
				}
			}
	 }
}


//Class for storage of statistical data for game


/* Lines of code injected to the other classes:
 
IN MAIN:
  	//FOR STATISTICS ~line 41
	Statistics.P1.addCoinWin();
	//END FOR STATISTICS

	//FOR STATISTICS ~line 49
	Statistics.P2.addCoinWin();
	//END FOR STATISTICS

IN GAMESTATE:
	//FOR STATISTICS  ~line 20
	Statistics.P2.getFulldata();
	Statistics.P1.getFulldata();
	//END FOR STATISTICS
			 
	//FOR STATISTICS  ~line 29
	Statistics.P1.getFulldata();
	Statistics.P2.getFulldata();
	//END FOR STATISTICS

IN AGENTACTIONS:
	//FOR STATISTICS ~line 44
	if(playerId==1)Statistics.P1.addMove(movingSquares,movingCircles,movingTriangles); 
	if(playerId==2)Statistics.P2.addMove(movingSquares,movingCircles,movingTriangles); 
	//END FOR STATISTICS

	//FOR STATISTICS ~line 98
	if(attackerId==1)Statistics.P1.addAttack(attackingSquares,attackingCircles,attackingTriangles); 
	if(attackerId==2)Statistics.P2.addAttack(attackingSquares,attackingCircles,attackingTriangles); 
	//END FOR STATISTICS

	//FOR STATISTICS ~line 108
	int startattackingSquares=attackingSquares, startattackingCircles=attackingCircles, startattackingTriangles=attackingTriangles;
	int startdefendingSquares=defendingSquares, startdefendingCircles=defendingCircles, startdefendingTriangles=defendingTriangles;
	//END FOR STATISTICS

	//FOR STATISTICS ~line 163
	if(attackerId==1)Statistics.P1.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
	if(attackerId==1)Statistics.P2.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
	if(attackerId==2)Statistics.P2.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
	if(attackerId==2)Statistics.P1.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
	//FOR STATISTICS END

	//FOR STATISTICS ~line 215
	if(attackerId==1)Statistics.P1.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
	if(attackerId==1)Statistics.P2.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
	if(attackerId==2)Statistics.P2.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
	if(attackerId==2)Statistics.P1.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
	//FOR STATISTICS END
		
	//FOR STATISTICS ~line 262
	if(attackerId==1)Statistics.P1.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
	if(attackerId==1)Statistics.P2.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
	if(attackerId==2)Statistics.P2.addKilled((startdefendingSquares-defendingSquares),(startdefendingCircles-defendingCircles),(startdefendingTriangles-defendingTriangles));
	if(attackerId==2)Statistics.P1.addKilled((startattackingSquares-attackingSquares),(startattackingCircles-attackingCircles),(startattackingTriangles-attackingTriangles));
	//FOR STATISTICS END

IN WORLDUPDATE: 
	//FOR STATISTICS ~line 72
	Statistics.P1.addRoadBroken();
	Statistics.P2.addRoadBroken();
	//END FOR STATISTICS


*/




public class Statistics {
	
//STORAGE
public static Statistics P1 = new Statistics(1);
public static Statistics P2 = new Statistics(2);

//VARIABLES
private	int AgentNumber=0; //Number of agent (1 or 2)

private int Moves=0; //Total number of "move" commands performed
private int MovedSquares=0; //Total number of Squares moved
private int MovedCircles=0; //Total number of Circles moved
private int MovedTriangles=0; //Total number of Triangles moved 

private int Attacks=0; //Total number of "attack" command performed
private int AttackedWithSquares=0; //Total number of Squares used to attack
private int AttackedWithCircles=0; //Total number of Circles used to attack
private int AttackedWithTriangles=0; //Total number of Triangles used to attack
private int CoinTossesWon=0;

private int UnitsKilled=0; //Total number of units killed by a Player
private int KilledSquares=0; //Total number of Squares killed by a Player
private int KilledCircles=0; //Total number of Circles killed by a Player
private int KilledTriangles=0; //Total number of Triangles killed by a Player

private int RoadsBroken=0; 

private int unitsLost=0; //Total number of units that Player lost
private int producedUnits=0; //Total number of produced Units

private int Waits=0; //Total number of "wait" commands performed



//CONSTRUCTOR
public Statistics(int AgentNumberIn) {
	AgentNumber=AgentNumberIn;
}


//SET
public void addUnit(int producedUnitsIn){
	producedUnits+=producedUnitsIn;
}
public void addMove(int MovedSquaresIn, int MovedCirclesIn,  int MovedTrianglesIn){
	Moves++;
	MovedSquares+=MovedSquaresIn;
	MovedCircles+=MovedCirclesIn;
	MovedTriangles+=MovedTrianglesIn;
	
}

public void addAttack(int AttackedWithSquaresIn, int AttackedWithCirclesIn,  int AttackedWithTrianglesIn){
	Attacks++;
	AttackedWithSquares+=AttackedWithSquaresIn;
	AttackedWithCircles+=AttackedWithCirclesIn;
	AttackedWithTriangles+=AttackedWithTrianglesIn;
}

public void addKilled(int KilledSquaresIn, int KilledCirclesIn,  int KilledTrianglesIn){
	UnitsKilled+=(KilledSquaresIn+KilledCirclesIn+KilledTrianglesIn);
	KilledSquares+=KilledSquaresIn;
	KilledCircles+=KilledCirclesIn;
	KilledTriangles+=KilledTrianglesIn;
}

public void addCoinWin(){
	CoinTossesWon++;
}

public void addRoadBroken(){
	RoadsBroken++;
}



public void addKiled(int UnitsKilledIn){
	UnitsKilled+=UnitsKilledIn;
}


public void addLost(int unitsLostIn){
	unitsLost+=unitsLostIn;
}


//GET
public int getUnit(){
	return producedUnits;
}

public int getMoves(){
	return Moves;
}

public int getWait(){
	return Waits;
}

public int getAttack(){
	return Attacks;
}

public int getKiled(){
	return UnitsKilled;
}

public int getLost(){
	return unitsLost;
}

public void getFulldata(){
	System.out.println("++++++++++++++Statistics for agent "+ AgentNumber+" ++++++++++++++"); 
	System.out.println("Move commands performed: "+ Moves); //Total number of "move" commands performed
	System.out.println("Squares moved: "+MovedSquares); //Total number of Squares moved
	System.out.println("Circles moved: "+MovedCircles); //Total number of Circles moved
	System.out.println("Triangles moved: " +MovedTriangles);
	
	System.out.println("Attack commands performed: "+ Attacks); //Total number of "attack" commands performed
	System.out.println("Squares used to attack: "+AttackedWithSquares);
	System.out.println("Circles used to attack: "+AttackedWithCircles);
	System.out.println("Triangles used to attack: "+AttackedWithTriangles);
	
	System.out.println("Units killed: "+ UnitsKilled); //Total number of "attack" commands performed
	System.out.println("Squares killed: "+KilledSquares);
	System.out.println("Circles killed: "+KilledCircles);
	System.out.println("Triangles killed: "+KilledTriangles);
	
	System.out.println("Coin tosses won: "+CoinTossesWon);
	System.out.println("Waits performed: "+(main.turnCount-(Attacks+Moves)));	
	System.out.println("Random roads failures: "+RoadsBroken);
	System.out.println(); 
}

}


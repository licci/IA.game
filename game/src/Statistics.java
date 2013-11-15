//testing git changes
import java.lang.String;//Used for storing Player name

public class Statistics {

//VARIABLES
private String Name="";	//Name of a Player
private int producedUnits=0; //Total number of produced Units
private int Moves=0; //Total number of "move" commands performed
private int Waits=0; //Total number of "wait" commands performed
private int Attacks=0; //Total number of "attack" command performed
private int unitsKilled=0; //Total number of units killed by a Player
private int burnedBridges=0; //Total number of bridges destroyed by a Player 
private int buildBridges=0; //Total number of bridges reconstructed by a Player
private int unitsLost=0; //Total number of units that Player lost

//DEFINE
private static final int COSTOFDESTRUCTION = 0; //Fixed cost of a bridge build(in units)
private static final int COSTOFBUILD = 0; //Fixed cost of rebuilding a bridge(in units)


//CONSTRUCTOR
public Statistics(String NameIn) {
	Name=NameIn;
}


//SET
public void addUnit(int producedUnitsIn){
	producedUnits+=producedUnitsIn;
}

public void addMove(){
	Moves++;
}
public void addWait(){
	Waits++;
}

public void addAttack(){
	Attacks++;
}

public void addKiled(int unitsKilledIn){
	unitsKilled+=unitsKilledIn;
}

public void addBridgeburned(int burnedBridgesIn){
	burnedBridges += burnedBridgesIn;
	unitsLost += COSTOFDESTRUCTION;
	}

public void addBridgebuild(int buildBridgesIn){
	buildBridges+=buildBridgesIn;
	unitsLost += COSTOFBUILD;
}

public void addLost(int unitsLostIn){
	unitsLost+=unitsLostIn;
}


//GET
public String getName(){
	return Name;
}
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
	return unitsKilled;
}

public int getBridgeburned(){
	return burnedBridges;
}

public int getBridgebuild(){
	return buildBridges;
}

public int getLost(){
	return unitsLost;
}


}

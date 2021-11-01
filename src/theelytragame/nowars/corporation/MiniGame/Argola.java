package theelytragame.nowars.corporation.MiniGame;

import org.bukkit.Location;
import org.bukkit.Material;
import theelytragame.nowars.corporation.Utils.Loc;

public class Argola {
    private String name;
    private Location positionOne;
    private Location positionTwo;
private Value value;


    enum Value{
        EASY,NORMAL,HARD
    }

    public Argola(String name,Location positionOne, Location positionTwo, Value value){
        this.name = name;
        this.positionOne = positionOne;
        this.positionTwo = positionTwo;
        this.value = value;
    }

    public Location getPositionOne() {
        return positionOne;
    }

    public Location getPositionTwo() {
        return positionTwo;
    }

    public String getName(){
        return name;
    }
    public Value getValue(){
        return value;
    }


}

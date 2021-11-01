package theelytragame.nowars.corporation.MiniGame;

import org.bukkit.Location;

public class Smoke {
    private String name;
    private Location positionOne;
    private Location positionTwo;

    public Smoke(String name, Location positionOne, Location positionTwo){
        this.name = name;
        this.positionOne = positionOne;
        this.positionTwo = positionTwo;
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


}

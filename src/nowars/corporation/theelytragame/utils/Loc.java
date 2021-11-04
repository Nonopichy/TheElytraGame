package nowars.corporation.theelytragame.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Loc {
    public static Location d(String serialized) {
        String[] divPoints = serialized.split("; ");
        Location deserialized = new Location(Bukkit.getWorld(divPoints[0]), Double.parseDouble(divPoints[1]), Double.parseDouble(divPoints[2]), Double.parseDouble(divPoints[3]));
        deserialized.setYaw(Float.parseFloat(divPoints[4]));
        deserialized.setPitch(Float.parseFloat(divPoints[5]));
        return deserialized;
    }
    public static String s(Location unserialized) {
        return unserialized.getWorld().getName() + "; " + unserialized.getX() + "; " + unserialized.getY() + "; " + unserialized.getZ() + "; " + unserialized
                .getYaw() + "; " + unserialized.getPitch();
    }
    public static boolean inArea(Location targetLocation, Location inAreaLocation1, Location inAreaLocation2, boolean checkY){
        if(inAreaLocation1.getWorld().getName() == inAreaLocation2.getWorld().getName()){
            if(targetLocation.getWorld().getName() == inAreaLocation1.getWorld().getName()){
                if((targetLocation.getBlockX() >= inAreaLocation1.getBlockX() && targetLocation.getBlockX() <= inAreaLocation2.getBlockX()) || (targetLocation.getBlockX() <= inAreaLocation1.getBlockX() && targetLocation.getBlockX() >= inAreaLocation2.getBlockX())){
                    if((targetLocation.getBlockZ() >= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() <= inAreaLocation2.getBlockZ()) || (targetLocation.getBlockZ() <= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() >= inAreaLocation2.getBlockZ())){
                        if(checkY == true){
                            if((targetLocation.getBlockY() >= inAreaLocation1.getBlockY() && targetLocation.getBlockY() <= inAreaLocation2.getBlockY()) || (targetLocation.getBlockY() <= inAreaLocation1.getBlockY() && targetLocation.getBlockY() >= inAreaLocation2.getBlockY())){
                                return true;
                            }
                        }else{
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}


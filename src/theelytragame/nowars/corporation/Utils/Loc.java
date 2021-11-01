package theelytragame.nowars.corporation.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Loc {
    public static Location deserializeLocation(String serialized) {
        String[] divPoints = serialized.split("; ");
        Location deserialized = new Location(Bukkit.getWorld(divPoints[0]), Double.parseDouble(divPoints[1]), Double.parseDouble(divPoints[2]), Double.parseDouble(divPoints[3]));
        deserialized.setYaw(Float.parseFloat(divPoints[4]));
        deserialized.setPitch(Float.parseFloat(divPoints[5]));
        return deserialized;
    }
    public static String serializeLocation(Location unserialized) {
        return unserialized.getWorld().getName() + "; " + unserialized.getX() + "; " + unserialized.getY() + "; " + unserialized.getZ() + "; " + unserialized
                .getYaw() + "; " + unserialized.getPitch();
    }
    public static boolean inArea(Location targetLocation, Location inAreaLocation1, Location inAreaLocation2, boolean checkY){
        if(inAreaLocation1.getWorld().getName() == inAreaLocation2.getWorld().getName()){ // Check for worldName location1, location2
            if(targetLocation.getWorld().getName() == inAreaLocation1.getWorld().getName()){ // Check for worldName targetLocation, location1
                if((targetLocation.getBlockX() >= inAreaLocation1.getBlockX() && targetLocation.getBlockX() <= inAreaLocation2.getBlockX()) || (targetLocation.getBlockX() <= inAreaLocation1.getBlockX() && targetLocation.getBlockX() >= inAreaLocation2.getBlockX())){ // Check X value
                    if((targetLocation.getBlockZ() >= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() <= inAreaLocation2.getBlockZ()) || (targetLocation.getBlockZ() <= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() >= inAreaLocation2.getBlockZ())){ // Check Z value
                        if(checkY == true){ // If should check for Y value
                            if((targetLocation.getBlockY() >= inAreaLocation1.getBlockY() && targetLocation.getBlockY() <= inAreaLocation2.getBlockY()) || (targetLocation.getBlockY() <= inAreaLocation1.getBlockY() && targetLocation.getBlockY() >= inAreaLocation2.getBlockY())){ // Check Y value
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


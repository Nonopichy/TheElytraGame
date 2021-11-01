package theelytragame.nowars.corporation.Utils;


import org.bukkit.Location;
import theelytragame.nowars.corporation.Main;

import java.util.List;

public class Conf {

    public static String getStringConf(String str){ return Chat.cColor( Main.getInstance().getConfig().getString(str)); }
    public static List<String> getListConf(String str){
        List<String> list = (List<String>) Main.getInstance().getConfig().getList(str);
        for(int x = 0 ; x < list.size() ; x ++)
            list.set(x,Chat.cColor(list.get(x)));
        return list;
    }
    public static int getIntConf(String str){
        return Main.getInstance().getConfig().getInt(str);
    }
    public static Location getLocation(String str){
        return Loc.deserializeLocation(Main.getInstance().getConfig().getString(str));
    }
    public static void setLocation(String str, Location loc){
        Main.getInstance().getConfig().set(str,Loc.serializeLocation(loc));
        saveConf();
    }
    public static void saveConf(){
        Main.getInstance().saveConfig();
    }
}

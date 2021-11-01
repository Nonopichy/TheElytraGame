package theelytragame.nowars.corporation;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import theelytragame.nowars.corporation.Utils.Loc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManagerObject {

    public HashMap<String, Object> objects = new HashMap<>();
    public FileConfiguration fileConfiguration;
    public boolean debug;

    public ManagerObject(FileConfiguration fileConfiguration, boolean debug){
        this.fileConfiguration = fileConfiguration;
        this.debug = debug;
    }
    public boolean clear(){
        if(objects.size() > 0){
            objects.clear();
            return true;
        }
        return false;
    }
    public Set<Map.Entry<String, Object>> getMapObjects(){
        return objects.entrySet();
    }

    public Object getValue(String str){
        if(objects.containsKey(str))
            return objects.get(str);
        Object o = fileConfiguration.get(str);
        if(o!=null){
            try{
               o = Loc.deserializeLocation(o.toString());
            } catch (Exception e){
                sendDebug("Is not Location.class", "null");
            }
            objects.put(str, o);
            return o;
        }
        sendDebug("Object", "null");
        return null;
    }

    public void sendDebug(Object... o){
        if(!debug)
            return;
        String out = "[DEBUG]";
        for(int i = 0 ; i < o.length ; i ++)
            out = out  + " : " + o[i].toString();
        System.out.println(out);
    }

    public void setHashValue(String str, Object obj){
        objects.put(str,obj);
    }
}

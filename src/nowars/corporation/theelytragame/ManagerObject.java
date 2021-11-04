package nowars.corporation.theelytragame;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import nowars.corporation.theelytragame.utils.Loc;

import java.util.HashMap;
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
    public void clear(){
        objects.clear();
    }
    public Set<Map.Entry<String, Object>> getMapObjects(){
        return objects.entrySet();
    }
    public Object get(String str){
        if(objects.containsKey(str))
            return objects.get(str);
        Object o = fileConfiguration.get(str);
        if(o!=null){
            try { o = Loc.d(o.toString()); } catch (Exception e){}
            objects.put(str, o);
            return o;
        }
        return null;
    }
    public void set(String str, Object obj){
        fileConfiguration.set(str, obj);
    }
}

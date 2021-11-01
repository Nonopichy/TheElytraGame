package theelytragame.nowars.corporation;

import org.bukkit.plugin.java.JavaPlugin;
import theelytragame.nowars.corporation.Bukkit.Command.*;
import theelytragame.nowars.corporation.Bukkit.Event.Elytra;
import theelytragame.nowars.corporation.MiniGame.Level;

import java.util.HashMap;

public class Main extends JavaPlugin {
    public static Main instance;
    public static ManagerObject managerObject;

    public static HashMap<String, Level> levels = new HashMap<>();


    public void onEnable() {
        instance = this;
        managerObject = new ManagerObject(getConfig(), true);
        registerCommands();
        registerEvents();

        Level level;
        getServer().getPluginManager().registerEvents(level = new Level("test"), this);
        levels.put("test", level);
    }
    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new Elytra(), this);
    }
    private void registerCommands(){
        getServer().getPluginCommand("join").setExecutor(new Join());
        getServer().getPluginCommand("quit").setExecutor(new Quit());
        getServer().getPluginCommand("define").setExecutor(new Define());
        getServer().getPluginCommand("argola").setExecutor(new ArgolaCmmd());
        getServer().getPluginCommand("boost").setExecutor(new BoostCmmd());
        getServer().getPluginCommand("smoke").setExecutor(new SmokeCmmd());
    }
    public static Main getInstance(){
        return instance;
    }
    public static ManagerObject getManagerObject(){
        return managerObject;
    }
}

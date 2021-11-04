package nowars.corporation.theelytragame;

import lombok.Getter;
import nowars.corporation.theelytragame.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import nowars.corporation.theelytragame.game.Level;

import java.util.HashMap;

public class Main extends JavaPlugin {
    @Getter public static Main instance;
    @Getter public static ManagerObject managerObject;
    @Getter public static HashMap<String, Level> levels = new HashMap<>();

    public void onEnable() {
        instance = this;
        loadOthers();
    }
    private void loadOthers(){
        managerObject = new ManagerObject(getConfig(), true);
        registerCommands();
            // test
        levels.put("test", new Level("test"));
    }
    private void registerCommands(){
        getServer().getPluginCommand("join").setExecutor(new Join());
        getServer().getPluginCommand("quit").setExecutor(new Quit());
        getServer().getPluginCommand("define").setExecutor(new Define());
        getServer().getPluginCommand("argola").setExecutor(new ArgolaCmmd());
        getServer().getPluginCommand("boost").setExecutor(new BoostCmmd());
        getServer().getPluginCommand("smoke").setExecutor(new SmokeCmmd());
    }
}

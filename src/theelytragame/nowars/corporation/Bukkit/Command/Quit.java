package theelytragame.nowars.corporation.Bukkit.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import theelytragame.nowars.corporation.Main;

public class Quit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("quit"))
            return false;
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        Main.levels.get("test").playerQuit(p);

        p.sendMessage("saindo...");

        return false;
    }
}

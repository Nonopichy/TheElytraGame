package nowars.corporation.theelytragame.bukkit.command;

import nowars.corporation.theelytragame.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Join implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        Main.levels.get("test").playerJoin(p);

        p.sendMessage("entrando...");

        return false;
    }
}

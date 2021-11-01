package theelytragame.nowars.corporation.Bukkit.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import theelytragame.nowars.corporation.Main;
import theelytragame.nowars.corporation.Utils.Conf;

public class Define implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("define"))
            return false;
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("definir")){
                if(args.length > 1){
                    Conf.setLocation(args[1], p.getLocation());
                    p.sendMessage("Localização "+args[1]+" definida!");
                }
            }
        }

        return false;
    }
}

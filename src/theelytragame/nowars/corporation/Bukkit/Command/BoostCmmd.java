package theelytragame.nowars.corporation.Bukkit.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import theelytragame.nowars.corporation.Main;
import theelytragame.nowars.corporation.Utils.Conf;

public class BoostCmmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("boost"))
            return false;
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        if(args.length >= 3){
            String map = args[0];
            String boost = args[1];
            String pos = args[2];

            String root = "Levels."+map +"."+".boosts."+boost + ".";
            Conf.setLocation(root+pos, p.getLocation());

            p.sendMessage("Map = "+map);
            p.sendMessage("boost = "+boost);
            p.sendMessage("pos = "+pos);

        }

        p.sendMessage("boost");
        return false;
    }
}

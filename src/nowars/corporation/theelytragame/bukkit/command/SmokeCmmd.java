package nowars.corporation.theelytragame.bukkit.command;

import nowars.corporation.theelytragame.Main;
import nowars.corporation.theelytragame.utils.Loc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SmokeCmmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        if(args.length >= 3){
            String map = args[0];
            String smoke = args[1];
            String pos = args[2];

            String root = "Levels."+map +"."+".smokes."+smoke + ".";
            Main.getManagerObject().set(root+pos, Loc.s(p.getLocation()));

            p.sendMessage("Map = "+map);
            p.sendMessage("smokes = "+smoke);
            p.sendMessage("pos = "+pos);

        }

        p.sendMessage("smoke");
        return false;
    }
}

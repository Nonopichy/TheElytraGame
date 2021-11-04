package nowars.corporation.theelytragame.bukkit.command;

import nowars.corporation.theelytragame.Main;
import nowars.corporation.theelytragame.utils.Loc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Define implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("definir")){
                if(args.length > 1){
                    Main.getManagerObject().set(args[1], Loc.s(p.getLocation()));
                    p.sendMessage("Localização "+args[1]+" definida!");
                }
            }
        }

        return false;
    }
}

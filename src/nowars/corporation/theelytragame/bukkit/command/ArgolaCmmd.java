package nowars.corporation.theelytragame.bukkit.command;

import nowars.corporation.theelytragame.Main;
import nowars.corporation.theelytragame.utils.Loc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArgolaCmmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!(s  instanceof Player))
            return false;
        Player p = ((Player) s).getPlayer();

        if(args.length >= 4){
            String map = args[0];
            String argola = args[1];
            String dif = args[2];
            String pos = args[3];

            String root = "Levels."+map +"."+".argolas."+argola + ".";

            Main.getInstance().getConfig().set(root + "value", dif);
            Main.getManagerObject().set(root+pos, Loc.s(p.getLocation()));

            p.sendMessage("Map = "+map);
            p.sendMessage("argola = "+argola);
            p.sendMessage("dificuldade = "+dif);
            p.sendMessage("pos = "+pos);

        }

        p.sendMessage("argola");
        return false;
    }
}

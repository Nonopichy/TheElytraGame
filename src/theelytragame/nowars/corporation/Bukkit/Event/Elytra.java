package theelytragame.nowars.corporation.Bukkit.Event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class Elytra implements Listener {
    /**
    @onElytraPress
    Quando jogador envia o packet de planar com a Elytra
     */

    public void onElytraPress(EntityToggleGlideEvent e){
        Player p = (Player) e.getEntity();


    }

    /**
     @onElytraDespise
     Quando jogador envia o packet de parar de planar com a Elytra
     */

    public void onElytraDespise(EntityToggleGlideEvent e){
        Player p = (Player) e.getEntity();



    }

    @EventHandler (priority = EventPriority.LOW)
    public void onEntityToggleGlideEvent(EntityToggleGlideEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(e.isGliding()) onElytraPress(e);
        else onElytraDespise(e);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        e.getEntity().sendMessage(e.getCause().toString());
    }
    public void debug(Object... o){
        String out = "[DEBUG]";
        for(int i = 0 ; i < o.length ; i ++)
            out = out  + " : " + o[i].toString();
        System.out.println(out);
    }
}

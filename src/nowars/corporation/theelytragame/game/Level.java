package nowars.corporation.theelytragame.game;

import lombok.Getter;
import nowars.corporation.theelytragame.Main;
import nowars.corporation.theelytragame.game.objects.argola.Argola;
import nowars.corporation.theelytragame.game.objects.argola.TypeArgola;
import nowars.corporation.theelytragame.game.objects.boost.Boost;
import nowars.corporation.theelytragame.game.objects.smoke.Smoke;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import nowars.corporation.theelytragame.utils.Loc;

import java.util.*;

public class Level extends BukkitRunnable implements Listener {

    public String name;

    public int tempo = 0;
    public int tempo_restante_final= 30;
    public int tempo_final  = 10;

    public int min = 2;
    public int comeca = 10;
    public int aguardando = 10;

    public String root;

    public HashMap<Player, PlayerData> players = new HashMap<>();

    public ArrayList<Player> getWins(){
        ArrayList<Player> wins = new ArrayList<>();
        for(Map.Entry<Player, PlayerData> m : players.entrySet()){
            if(m.getValue().isWin()) wins.add(m.getKey());
        }
        return wins;
    }
    public ArrayList<Player> getPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        players.forEach(p -> players.add(p));
        return players;
    }
    public PlayerData getPlayerData(Player p){
        return players.get(p);
    }

    public State state = State.AGUARDANDO;

    public HashMap<String, Argola> argolas = new HashMap<>();
    public HashMap<String, Smoke> smokes = new HashMap<>();
    public HashMap<String, Boost> boosts = new HashMap<>();
    public HashMap<Player, ArrayList<String>> passed_argolas = new HashMap<>();

    enum State {
        AGUARDANDO,COMECANDO,JOGANDO,TEMPO_FINAL,FINAL
    }
    public Level(String name) {
        this.name = name;
        this.root = "Levels."+name+".";
        loadObjectsGame();
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
        runTaskTimer(Main.getInstance(), 20L,20L);
    }
    public void loadObjectsGame(){
        for (String i : Main.getInstance().getConfig().getConfigurationSection("Levels."+name+".argolas").getKeys(false)) {
            argolas.put(i, new Argola(i, (Location) Main.getManagerObject().get("Levels."+name+".argolas."+i+".pos1"),(Location) Main.getManagerObject().get("Levels."+name+".argolas."+i+".pos2"), TypeArgola.valueOf(Main.getManagerObject().get("Levels."+name+".argolas."+i+".value").toString().toUpperCase())));
        }
        for (String i : Main.getInstance().getConfig().getConfigurationSection("Levels."+name+".boosts").getKeys(false)) {
            boosts.put(i, new Boost(i, (Location) Main.getManagerObject().get("Levels."+name+".boosts."+i+".pos1"),(Location) Main.getManagerObject().get("Levels."+name+".boosts."+i+".pos2")));
        }
        for (String i : Main.getInstance().getConfig().getConfigurationSection("Levels."+name+".smokes").getKeys(false)) {
            smokes.put(i, new Smoke(i, (Location) Main.getManagerObject().get("Levels."+name+".smokes."+i+".pos1"), (Location) Main.getManagerObject().get("Levels."+name+".smokes."+i+".pos2")));
        }
    }
    @Override
    public void run() {
        if(state == State.AGUARDANDO) {
            AGUARDANDO();
        } else if (state == State.COMECANDO) {
            COMECANDO();
        } else {
            if (state == State.JOGANDO)
                JOGANDO();
            if(state == State.TEMPO_FINAL)
                TEMPO_FINAL();
            if(state == State.FINAL)
                FINAL();
        }
        tempo++;
    }
    public void AGUARDANDO(){
        send("aguardando ("+players.size()+"/"+min+"/"+"NaN");

        if(state != State.AGUARDANDO){
            tp((Location) Main.getManagerObject().get("Levels."+name+".loc.aguardando"));
            state = State.AGUARDANDO;
        }
        if (players.size() < min){
            tempo = 0;
        }
        else if (tempo >= aguardando && players.size() >= min){
            tempo = 0;
            state = State.COMECANDO;
        }
    }
    public void COMECANDO(){
        send("comecando em "+tempo+"/"+comeca);

        if(state == State.AGUARDANDO){
            tp((Location) Main.getManagerObject().get("Levels."+name+".loc.comecando"));

            state = State.COMECANDO;
            tempo = 0;
        }
        else if (players.size() < min){
            tempo = 0;
            state = State.AGUARDANDO;
        }
        if(tempo >= comeca) {

            tp((Location) Main.getManagerObject().get("Levels."+name+".loc.jogando"));

            tempo = 0;
            state = State.JOGANDO;
        }
    }
    public void JOGANDO(){
        send("jogando :) "+tempo);

        if(players.size() <  2 || getWins().size() >= players.size()){
            tp((Location) Main.getManagerObject().get("Levels."+name+".loc.final"));
            tempo = 0;
            state = State.FINAL;
        }
         if (getWins().size() > 0){
            tempo = 0;
            state = State.TEMPO_FINAL;
        }
    }
    public void TEMPO_FINAL(){
        send("tempo restante :) "+tempo+"/"+tempo_restante_final);

        System.out.println(tempo+" >= "+tempo_restante_final+"//"+players.size()+"<= 1 //"+getWins().size() + ">="+players.size());
        if(tempo >= tempo_restante_final || players.size() <=  1
                || getWins().size() >= players.size()){
                tp((Location) Main.getManagerObject().get("Levels."+name+".loc.final"));
            tempo = 0;
            state = State.FINAL;
        }
    }
    public void FINAL(){
        send("fim :C ");

        if(tempo > tempo_final){
            getWins().clear();
            passed_argolas.clear();
            for(int i = 0 ; i < getPlayers().size() ; i ++){
                Player p =  getPlayers().get(i);
                passed_argolas.put(p, new ArrayList<>());
            }
            tp((Location) Main.getManagerObject().get("Levels."+name+".loc.aguardando"));
            tempo = 0;
            state = State.AGUARDANDO;
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(!(state == State.JOGANDO || state == State.TEMPO_FINAL) || !players.containsKey(p) || !(e.getFrom().getBlockX() != e.getTo().getBlockX () || e.getFrom().getBlockY()!= e.getTo().getBlockY() || e.getFrom().getBlockZ()!= e.getTo().getBlockZ()))
            return;
        if(p.isOnGround()) {
            p.damage(0.5);
        } else {
            p.setGliding(true);
            if(!getWins().contains(p))
            verificarEnd(p, e.getTo());
            verificarArgola(p, e.getTo());
            verificarBoost(p, e.getTo());
            verificarSmoke(p, e.getTo());
        }
    }
    public void verificarEnd(Player p, Location loc){
        if(Loc.inArea(loc, (Location) Main.getManagerObject().get(root+"loc.end1"), (Location) Main.getManagerObject().get(root+"loc.end2"), true))
            getPlayerData(p).setWin(true);
    }
    public void verificarArgola(Player p, Location loc){
        a: for(Argola r : argolas.values()){
            for(int y = 0 ; y < passed_argolas.get(p).size() ; y++){
                if(passed_argolas.get(p).get(y)==r.getName())
                    continue a;
            }
            if(Loc.inArea(loc, r.getPos1(), r.getPos2(), true)){
                 if(r.getValue() == TypeArgola.EASY)
                   p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10,10);
                 if(r.getValue() == TypeArgola.NORMAL)
                     p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10,5);
                 if(r.getValue() == TypeArgola.HARD)
                     p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10,1);
                 passed_argolas.get(p).add(r.getName());
                 return;
            }
        }
    }
    public void verificarBoost(Player p, Location loc){
        for(Boost b : boosts.values()){
            if(Loc.inArea(loc, b.getPos1(), b.getPos2(), true)){
                p.setVelocity(p.getLocation().getDirection().multiply(p.getLocation().getDirection().length()+0.9));
                return;
            }
        }
    }
    public void verificarSmoke(Player p, Location loc){
        for(Smoke s : smokes.values()){
            if(Loc.inArea(loc, s.getPos1(), s.getPos2(), true)){
                p.setVelocity(new Vector(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY() + 0.5, p.getLocation().getDirection().getZ()).multiply(p.getLocation().getDirection().length()+0.1));
                return;
            }
        }
    }
    public void send(String s){ players.keySet().forEach(p -> p.sendMessage(s)); }
    public void tp(Location l){ players.keySet().forEach( p -> p.teleport(l)); }
    public void playerJoin(Player p){
        if(state != State.AGUARDANDO || playerInside(p))
            return;
        p.setMaxHealth(4.5);
        players.put(p, new PlayerData(false, 0));
        passed_argolas.put(p, new ArrayList<>());
        p.teleport((Location) Main.getManagerObject().get(root+"loc.aguardando"));
    }
    public void playerQuit(Player p){
        players.remove(p);
        passed_argolas.remove(p);
    }
    public boolean playerInside(Player p){
        for (Player l: players.keySet()) {
            if (p.getName() == l.getName()) return true;
        }
        return false;
    }
}

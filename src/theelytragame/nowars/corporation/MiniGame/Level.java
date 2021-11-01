package theelytragame.nowars.corporation.MiniGame;

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
import theelytragame.nowars.corporation.Main;
import theelytragame.nowars.corporation.Utils.Loc;
import theelytragame.nowars.corporation.Utils.ScoreHelper;

import java.util.*;

public class Level extends BukkitRunnable implements Listener {

    public String name ;

    public ArrayList<Player> players;
    public HashMap<Integer, Player> wins;
    public State state;

    public int tempo = 0;
    public int tempo_restante_final= 30;
    public int tempo_final  = 10;

    public int min;
    public int comeca;

    public HashMap<String, Argola> argolas = new HashMap<>();
    public HashMap<String, Smoke> smokes = new HashMap<>();
    public HashMap<String, Boost> boosts = new HashMap<>();
    public HashMap<Player, ArrayList<String>> passed_argolas = new HashMap<>();

    public Location location_final;

    public Level(String name) {
        this.name = name;

        this.state = State.AGUARDANDO;
        this.players = new ArrayList<>();
        this.wins = new HashMap<>();

        this.min = 2;
        this.comeca = 2;
        this.location_final = (Location) Main.getManagerObject().getValue("Levels."+name+".loc.final");

        for (String i : Main.getInstance().getConfig().getConfigurationSection("Levels."+name+".argolas").getKeys(false)) {
             Location pos1 = (Location) Main.getManagerObject().getValue("Levels."+name+".argolas."+i+".pos1");
             Location pos2 = (Location) Main.getManagerObject().getValue("Levels."+name+".argolas."+i+".pos2");


            Argola.Value value = Argola.Value.valueOf(Main.getManagerObject().getValue("Levels."+name+".argolas."+i+".value").toString().toUpperCase());
            Argola argola = new Argola(i, pos1,pos2, value);

            System.out.println(i+","+pos1+","+pos2+","+value);

            argolas.put(i, argola);
        }

        for (String i : Main.getInstance().getConfig().getConfigurationSection("Levels."+name+".boosts").getKeys(false)) {
            Location pos1 = (Location) Main.getManagerObject().getValue("Levels."+name+".boosts."+i+".pos1");
            Location pos2 = (Location) Main.getManagerObject().getValue("Levels."+name+".boosts."+i+".pos2");
            Boost boost = new Boost(i, pos1,pos2);

            System.out.println(i+","+pos1+","+pos2);

            boosts.put(i, boost);
        }


        for (String i : Main.getInstance().getConfig().getConfigurationSection("Levels."+name+".smokes").getKeys(false)) {
            Location pos1 = (Location) Main.getManagerObject().getValue("Levels."+name+".smokes."+i+".pos1");
            Location pos2 = (Location) Main.getManagerObject().getValue("Levels."+name+".smokes."+i+".pos2");
            Smoke smoke = new Smoke(i, pos1,pos2);

            System.out.println(i+","+pos1+","+pos2);

            smokes.put(i, smoke);
        }



        runTaskTimer(Main.getInstance(), 20L,20L);
    }

    enum State {
        AGUARDANDO,COMECANDO,JOGANDO,TEMPO_FINAL,FINAL
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
        if(state != State.AGUARDANDO){

                tp((Location) Main.getManagerObject().getValue("Levels."+name+".loc.aguardando"));

            state = State.AGUARDANDO;
        }
        int x = 3;
        if (players.size() < min){
            tempo = 0;
        }
        if (tempo >= x){
            if (players.size() >= min){
                tempo = 0;
                state = State.COMECANDO;
            }
        }

        send("aguardando ("+players.size()+"/"+min+"/"+"NaN");
    }
    public void COMECANDO(){
        if(state == State.AGUARDANDO){

                tp((Location) Main.getManagerObject().getValue("Levels."+name+".loc.comecando"));

            state = State.COMECANDO;
            tempo = 0;
        }
        send("comecando em "+tempo+"/"+comeca);
        if (players.size() < min){
            tempo = 0;
            state = State.AGUARDANDO;
        }

        if(tempo >= comeca) {

                tp((Location) Main.getManagerObject().getValue("Levels."+name+".loc.jogando"));

            tempo = 0;
            state = State.JOGANDO;
        }
    }
    public void JOGANDO(){
        if(players.size() <  2
        || wins.size() >= players.size()){

                tp(location_final);

            tempo = 0;
            state = State.FINAL;
        } else if (wins.size() > 0){
            tempo = 0;
            state = State.TEMPO_FINAL;
        }

        send("jogando :) "+tempo);
    }
    public void TEMPO_FINAL(){
        if(tempo >= tempo_restante_final || players.size() <  2
                || wins.size() >= players.size()){

                tp((Location) Main.getManagerObject().getValue("Levels."+name+".loc.final"));

            tempo = 0;
            state = State.FINAL;
        }
        send("tempo restante :) "+tempo+"/"+tempo_restante_final);

    }
    public void FINAL(){
        send("fim :C ");
        if(tempo > tempo_final){
            wins.clear();
            passed_argolas.clear();
            for(int i = 0 ; i < players.size() ; i ++){
                Player p = players.get(i);

                passed_argolas.put(p, new ArrayList<>());

            }

                tp((Location) Main.getManagerObject().getValue("Levels."+name+".loc.aguardando"));

            tempo = 0;
            state = State.AGUARDANDO;
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!(state == State.JOGANDO || state == State.TEMPO_FINAL))
            return;
        Player p = e.getPlayer();
        if(!players.contains(p))
            return;
        if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() == Material.ELYTRA)
               p.setGliding(true);
        if(p.isOnGround())
            p.damage(0.5);
        verificarEnd(p, e.getTo());
        verificarArgola(p, e.getTo());
        verificarBoost(p, e.getTo());
        verificarSmoke(p,e.getTo());
    }

    public void verificarEnd(Player p, Location loc){
            if(!wins.containsValue(p)) {

                Location   pos1 =(Location) Main.getManagerObject().getValue("Levels."+name+".loc.end1");
                Location   pos2 =(Location) Main.getManagerObject().getValue("Levels."+name+".loc.end2");

                    boolean in = Loc.inArea(loc, pos1, pos2, true);
                    if(in == true){
                        p.sendMessage(":) vc jegou au fin");
                        wins.put(wins.size() + 1, p);
                    }

            }

    }
    public boolean verificarArgola(Player p, Location loc){
        loop_argolas: for(Argola argola : argolas.values()){
        loop_passed: for(int y = 0 ; y < passed_argolas.get(p).size() ; y++){
            String passed = passed_argolas.get(p).get(y);
            if(passed ==argola.getName()) {
                continue loop_argolas;
            }
        }
        boolean in = Loc.inArea(loc, argola.getPositionOne(), argola.getPositionTwo(), true);
             if(in == true){
                 Firework fw = (Firework) p.getWorld().spawn(p.getLocation(), Firework.class);
                 if(argola.getValue() == Argola.Value.EASY)
                   p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10,10);
                 if(argola.getValue() == Argola.Value.NORMAL)
                     p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10,5);
                 if(argola.getValue() == Argola.Value.HARD)
                     p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10,1);

                 passed_argolas.get(p).add(argola.getName());
                 return true;
             }
        }
        return false;

    }
    public boolean verificarBoost(Player p, Location loc){
        loop_argolas: for(Boost boost : boosts.values()){
            boolean in = Loc.inArea(loc, boost.getPositionOne(), boost.getPositionTwo(), true);
            if(in == true){

                p.setVelocity(p.getLocation().getDirection().multiply(p.getLocation().getDirection().length()+0.9));

                return true;
            }
        }
        return false;

    }
    public boolean verificarSmoke(Player p, Location loc){
        loop_argolas: for(Smoke smoke : smokes.values()){
            boolean in = Loc.inArea(loc, smoke.getPositionOne(), smoke.getPositionTwo(), true);
            if(in == true){
                Vector unitVector = new Vector(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY() + 0.5, p.getLocation().getDirection().getZ());

                p.setVelocity(unitVector.multiply(p.getLocation().getDirection().length()+0.1));
                return true;
            }
        }
        return false;

    }

    /*
    public void scoreGame(){

        for(int i = 0 ; i < players.size() ; i++){
            Player p = players.get(i);

            ScoreHelper sc;
            if(ScoreHelper.hasScore(p))
                sc = ScoreHelper.getByPlayer(p);
            else
                sc = ScoreHelper.createScore(p);

            sc.setTitle("&c&lTheGameElytra");
            sc.setSlot(7,"&7");
            sc.setSlot(6,"Iniciando em: &7");
            sc.setSlot(5,"Jogadores: &7");
            sc.setSlot(4, "&c");
            sc.setSlot(3, "Kit: ");
            sc.setSlot(2, "&b");
            sc.setSlot(1,"&enowars.net");

        }
    }

     */

    public void send(String str){
        for(int i = 0 ; i < players.size() ; i++) {
            Player p = players.get(i);
            p.sendMessage(str);
        }
    }
    public void tp(Location loc){
        for(int i = 0 ; i < players.size() ; i++) {
            Player p = players.get(i);
            p.teleport(loc);
        }
    }
    public void setLife(double life){
        for(int i = 0 ; i < players.size() ; i++) {
            Player p = players.get(i);
            p.setMaxHealth(life);
        }
    }

    public void playerJoin(Player p){
        if(state != State.AGUARDANDO)
            return;
        if(playerInside(p))
            return;
        p.setMaxHealth(4.5);
        players.add(p);
        passed_argolas.put(p, new ArrayList<>());

        try {
            p.teleport((Location) Main.getManagerObject().getValue("Levels."+name+".loc.aguardando"));
        } catch (Exception e) { e.printStackTrace(); }

    }
    public void playerQuit(Player p){
        if(!playerInside(p))
            return;
        players.remove(p);
        passed_argolas.remove(p);
        wins.remove(p);
    }
    public boolean playerInside(Player p){
        for(int i = 0 ; i < players.size() ; i++){
            Player loop = players.get(i);
            if(p.getName().equalsIgnoreCase(loop.getName()))
                return true;
        }
        return false;
    }
}

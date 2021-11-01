package theelytragame.nowars.corporation.Utils;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreHelper {
    private static HashMap<UUID, ScoreHelper> players = new HashMap<>();

    private Scoreboard scoreboard;

    private Objective sidebar;

    public static boolean hasScore(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static ScoreHelper createScore(Player player) {
        return new ScoreHelper(player);
    }

    public static ScoreHelper getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static ScoreHelper removeScore(Player player) {
        return players.remove(player.getUniqueId());
    }

    private ScoreHelper(Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.sidebar = this.scoreboard.registerNewObjective("sidebar", "dummy");
        this.sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 1; i <= 15; i++) {
            Team team = this.scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
        player.setScoreboard(this.scoreboard);
        players.put(player.getUniqueId(), this);
    }

    public void setTitle(String title) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        this.sidebar.setDisplayName((title.length() > 32) ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text) {
        Team team = this.scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if (!this.scoreboard.getEntries().contains(entry))
            this.sidebar.getScore(entry).setScore(slot);
        text = ChatColor.translateAlternateColorCodes('&', text);
        String pre = getFirstSplit(text);
        String suf = getFirstSplit(String.valueOf(ChatColor.getLastColors(pre)) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if (this.scoreboard.getEntries().contains(entry))
            this.scoreboard.resetScores(entry);
    }

    public void setSlotsFromList(List<String> list) {
        while (list.size() > 15)
            list.remove(list.size() - 1);
        int slot = list.size();
        if (slot < 15)
            for (int i = slot + 1; i <= 15; i++)
                removeSlot(i);
        for (String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return (s.length() > 16) ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if (s.length() > 32)
            s = s.substring(0, 32);
        return (s.length() > 16) ? s.substring(16) : "";
    }
}
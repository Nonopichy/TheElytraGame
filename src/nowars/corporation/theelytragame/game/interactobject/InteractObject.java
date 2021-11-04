package nowars.corporation.theelytragame.game.interactobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;

@AllArgsConstructor
public abstract class InteractObject {
    @Getter private String name;
    @Getter private Location pos1;
    @Getter private Location pos2;
}
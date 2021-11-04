package nowars.corporation.theelytragame.game.objects.argola;

import nowars.corporation.theelytragame.game.interactobject.InteractObject;
import org.bukkit.Location;

public class Argola extends InteractObject {
    private TypeArgola type;
    public Argola(String name, Location pos1, Location pos2, TypeArgola type) {
        super(name, pos1, pos2);
        this.type = type;
    }
    public TypeArgola getValue(){
        return type;
    }
}

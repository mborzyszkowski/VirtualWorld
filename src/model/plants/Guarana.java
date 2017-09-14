package model.plants;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;


public class Guarana extends Plant {
    
    public Guarana(Position position, World world) {
        super(position, world);
    }
    
    public Guarana(Position position) {
        super(position, null);
    }
    
    public Guarana(Guarana guarana){
        super(guarana);
    }
    
    @Override
    public Guarana clone() {
        return new Guarana(this);
    }
    
    @Override
    public void initParam() {
        this.setPower(0);
        this.setInitiative(0);
        this.setReproductionProbability(30);
    }
    
    @Override
    public ArrayList<Action> consequences(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<Action>();
        if (this.getPower() > atackingOrganism.getPower()) {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        } else {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, this));
            result.add(new Action(ActionEnum.A_INCREASEPOWER, new Position(-1, -1), 3, atackingOrganism));
        }
        return (result);
    }
}

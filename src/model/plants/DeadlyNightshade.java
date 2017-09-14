package model.plants;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;


public class DeadlyNightshade extends Plant {
    
    public DeadlyNightshade(Position position, World world) {
        super(position, world);
    }
    
    public DeadlyNightshade(Position position) {
        super(position, null);
    }
    
    public DeadlyNightshade(DeadlyNightshade deadlyNightshade){
        super(deadlyNightshade);
    }
    
    @Override
    public DeadlyNightshade clone() {
        return new DeadlyNightshade(this);
    }
    
    @Override
    public void initParam() {
        this.setPower(99);
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
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        }
        return (result);
    }
}

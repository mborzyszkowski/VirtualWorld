package model.plants;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;

public class SosnowskiHogweed extends Plant {
    
    public SosnowskiHogweed(Position position, World world) {
        super(position, world);
    }

    public SosnowskiHogweed(Position position) {
        super(position, null);
    }

    public SosnowskiHogweed(SosnowskiHogweed sosnowskiHogweed){
        super(sosnowskiHogweed);
    }
    
    public SosnowskiHogweed clone() {
        return new SosnowskiHogweed(this);
    }
    
    @Override
    public void initParam() {
        this.setPower(10);
        this.setInitiative(0);
        this.setReproductionProbability(30);
    }
    
    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
//        Position newPosition;
//        Plant newPlant = null;
        if (this.ifReproduce()) {
            result.addAll(super.move());
//            newPosition = world.getFreeNeighboringPosition(this.position);
//            if (world.positionOnBoard(newPosition)) {
//                newPlant = this.clone();
//                newPlant.initParam();
//                newPlant.setPosition(newPosition, false);
//                result.add(new Action(ActionEnum.A_ADD, newPosition, 0, newPlant));
//            }
        }
        ArrayList<Position> neighboringPositions = world.getListOfNeighboringPositions(position, 1);
        Organism pomOrganism = null;
        for(Position pomPosition : neighboringPositions)
            if((pomOrganism = world.getOrganismFromPosition(pomPosition)) != null)
                 if(! (pomOrganism instanceof Plant)) 
                        result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, pomOrganism));
        return result;
    }
    
    @Override
    public ArrayList<Action> consequences(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<>();
        if (this.getPower() > atackingOrganism.getPower()) {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        } else {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, this));
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        }
        return (result);
    }
}

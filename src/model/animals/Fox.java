package model.animals;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;
import java.util.Collections;

public class Fox extends Animal {

    public Fox(Position position, World world) {
        super(position, world);
    }

    public Fox(Position position) {
        super(position, null);
    }

    public Fox(Fox fox) {
        super(fox);
    }

    @Override
    public Fox clone() {
        return new Fox(this);
    }

    @Override
    public void initParam() {
        this.setPower(3);
        this.setInitiative(7);
    }

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        Organism tmpOrganism;
        ArrayList<Position> positionProposals = world.getListOfNeighboringPositions(position, 1);

        if (!positionProposals.isEmpty()) {
            Collections.shuffle(positionProposals);
            for (Position newPos : positionProposals) {
                tmpOrganism = world.getOrganismFromPosition(newPos);
                if (tmpOrganism == null || tmpOrganism.getPower() <= this.getPower()) {
                    result.add(new Action(ActionEnum.A_MOVE, newPos, 0, this));
                    return result;
                }
            }
        }
        return result;
    }
}

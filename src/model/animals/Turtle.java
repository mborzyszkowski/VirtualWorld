package model.animals;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;
import java.util.Random;

public class Turtle extends Animal {

    private final double moveProbability;

    public Turtle(Position position, World world) {
        super(position, world);
        this.moveProbability = 25;
    }

    public Turtle(Position position) {
        super(position, null);
        this.moveProbability = 25;
    }

    public Turtle(Turtle turtle) {
        super(turtle);
        this.moveProbability = turtle.moveProbability;
    }

    @Override
    public Turtle clone() {
        return new Turtle(this);
    }

    @Override
    public void initParam() {
        this.setPower(2);
        this.setInitiative(1);
    }

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        if (this.ifAction()) {
            ArrayList<Position> positionProposals = world.getListOfNeighboringPositions(position, 1);

            if (!positionProposals.isEmpty()) {
                int newPositionIdx = (new Random()).nextInt(positionProposals.size());
                Position newPosition = positionProposals.get(newPositionIdx);
                result.add(new Action(ActionEnum.A_MOVE, newPosition, 0, this));
            }
        }
        return result;
    }

    @Override
    public ArrayList<Action> diplomacy(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<Action>();
        if (atackingOrganism.getPower() < 5) {
            result.add(new Action(ActionEnum.A_MOVE, atackingOrganism.getLastPosition(), 0, atackingOrganism));
        }
        return (result);
    }

    private boolean ifAction() {
        return (new Random()).nextDouble() * 100 <= moveProbability;
    }
}

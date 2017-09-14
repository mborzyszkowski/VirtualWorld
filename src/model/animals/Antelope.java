package model.animals;

import model.Action;
import model.ActionEnum;
import model.Organism;
import model.World;
import model.Position;
import java.util.ArrayList;
import java.util.Random;

public class Antelope extends Animal {

    private final double escapeProbability;
    
    public Antelope(Position position, World world) {
        super(position, world);
        this.escapeProbability = 50;
    }
    
    public Antelope(Position position) {
        super(position, null);
        this.escapeProbability = 50;
    }
    
    public Antelope(Antelope antelope) {
        super(antelope);
        this.escapeProbability = antelope.escapeProbability;
    }
    
    @Override
    public Antelope clone() {
        return new Antelope(this);
    }
    
    @Override
    public void initParam() {
        this.setPower(4);
        this.setInitiative(4);
    }
    
    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        
        ArrayList<Position> positionProposals = world.getListOfNeighboringPositions(position, 2);
        
        if (!positionProposals.isEmpty()) {
            int newPositionIdx = (new Random()).nextInt(positionProposals.size());
            Position newPosition = positionProposals.get(newPositionIdx);
            result.add(new Action(ActionEnum.A_MOVE, newPosition, 0, this));
        }
        return result;
    }    
    
    @Override
    public ArrayList<Action> diplomacy(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<>();
        Position escapePosition = null;
        if (this.ifRun()) {
            escapePosition = world.getFreeNeighboringPosition(this.getPosition());
            if (world.positionOnBoard(escapePosition)) {
                result.add(new Action(ActionEnum.A_MOVE, escapePosition, 0, this));
            }
        }
        return (result);
    }
    
    boolean ifRun() {
        return (new Random()).nextDouble() * 100 <= escapeProbability;
    }
}

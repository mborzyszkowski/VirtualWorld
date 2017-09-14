package model.animals;

import java.util.ArrayList;
import java.util.Random;
import model.Action;
import model.ActionEnum;
import model.Organism;
import model.Position;
import model.World;


public abstract class Animal extends Organism {

    protected Position lastPosition;

    public Animal(Position position, World world) {
        super(position, world);
        this.lastPosition = new Position(position);
    }

    public Animal(Animal animal){
        super(animal);
        this.lastPosition = new Position(animal.lastPosition);
    }
    
    public abstract Animal clone();

    
    @Override
    public ArrayList<Action> move() { 
        ArrayList<Action> result = new ArrayList<Action>();
        
        ArrayList<Position> positionProposals = world.getListOfNeighboringPositions(position, 1);
        
        if(!positionProposals.isEmpty()) {
            int newPositionIdx = (new Random()).nextInt(positionProposals.size());
            Position newPosition = positionProposals.get(newPositionIdx);
            result.add(new Action(ActionEnum.A_MOVE, newPosition, 0, this));
        }
        return result;
    }

    @Override
    public ArrayList<Action> collision(Organism collisionOrganism) {
        ArrayList<Action> result = new ArrayList<>();
        ArrayList<Action> pomArray = new ArrayList<>();
        Position newPosition = null;
        Animal newAnimal = null;
        if (collisionOrganism != null) {
            if ( this.getClass().equals(collisionOrganism.getClass())  ){
                result.add(new Action(ActionEnum.A_MOVE, this.lastPosition, 0, this));
                newPosition = world.getFreeNeighboringPosition(this.position);

                if (newPosition.getX() != -1 && newPosition.getY() != -1 && newPosition.getX() != this.getLastPosition().getX() && newPosition.getY() != this.getLastPosition().getY()) {
                    newAnimal = this.clone();
                    newAnimal.initParam();
                    newAnimal.position = newPosition;
                    result.add(new Action(ActionEnum.A_ADD, newPosition, 0, newAnimal));
                }
            } else {
                pomArray = collisionOrganism.diplomacy(this);
                if (pomArray.isEmpty()) {
                    result = collisionOrganism.consequences(this);
                } else {
                    result = pomArray;
                }
            }
        }
        return result;
    }

    @Override
    public void setPosition(Position position, boolean back) {
        if (back) {
            this.lastPosition = this.position;
        }
        this.position = position;
    }

    @Override
    public Position getLastPosition() {
        return this.lastPosition;
    }

    @Override
    public String toString() {
        return super.toString() + " lastPosition: " + lastPosition;
    }
}

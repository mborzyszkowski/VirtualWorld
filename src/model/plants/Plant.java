package model.plants;

import java.util.ArrayList;
import java.util.Random;
import model.Action;
import model.ActionEnum;
import model.Organism;
import model.Position;
import model.World;

public abstract class Plant extends Organism {

    protected double reproductionProbability;

    public Plant(Position position, World world) {
        super(position, world);
        this.reproductionProbability = 30;
    }

    public Plant(Plant plant) {
        super(plant);
        this.reproductionProbability = plant.reproductionProbability;
    }

    public abstract Plant clone();

    protected double getReproductionProbability() {
        return reproductionProbability;
    }

    protected void setReproductionProbability(double reproductionProbability) {
        this.reproductionProbability = reproductionProbability;
    }

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        Position newPosition;
        Plant newPlant;
        if (this.ifReproduce()) {
            newPosition = world.getFreeNeighboringPosition(this.position);
            if (world.positionOnBoard(newPosition)) {
                newPlant = this.clone();
                newPlant.initParam();
                newPlant.position = newPosition;
                result.add(new Action(ActionEnum.A_ADD, newPosition, 0, newPlant));
            }
        }
        return result;
    }

    @Override
    public ArrayList<Action> collision(Organism CollisionOrganism) {
        ArrayList<Action> result = new ArrayList<>();
        return (result);
    }

    @Override
    public Position getLastPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Position position, boolean back) {
        this.position = position;
    }

    protected boolean ifReproduce() {
        return (new Random()).nextDouble() * 100 <= reproductionProbability;
    }
}

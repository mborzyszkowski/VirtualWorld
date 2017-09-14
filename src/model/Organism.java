package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;


public abstract class Organism implements Serializable{

    protected int power;
    protected int initiative;
    protected Position position;
    protected World world; 
    protected Color color;

    public abstract void initParam();
    
    public abstract Organism clone();
    
    public abstract ArrayList<Action> move();

    public abstract ArrayList<Action> collision(Organism CollisionOrganism);

    public abstract Position getLastPosition();

    public abstract void setPosition(Position position, boolean back);

    public Organism(Position position, World world) {
        this.position = new Position(position);
        this.world = world; 
    }
    
    public Organism(Organism organizm){
        this.power = organizm.power;
        this.initiative = organizm.initiative;
        this.position = new Position(organizm.position);
        this.world = organizm.world;
        this.color = organizm.color;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public Position getPosition() {
        return position;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Action> diplomacy(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<Action>();
        return result;
    }

    public ArrayList<Action> consequences(Organism atackingOrganism) {
        ArrayList<Action> result = new ArrayList<Action>();
        if (this.getPower() > atackingOrganism.getPower()) {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, atackingOrganism));
        } else {
            result.add(new Action(ActionEnum.A_REMOVE, new Position(-1, -1), 0, this));
        }
        return result;
    }

    public boolean isInteractive() {
        return false;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + 
                ": power: " + power + 
                " initiative: " + initiative + 
                " position: " + position;
    }
    
}

package model.animals;

import model.World;
import model.Position;


public class Sheep extends Animal {

    public Sheep(Position position, World world) {
        super(position, world);
    }
    
    public Sheep(Position position) {
        super(position, null);
    }
    
    public Sheep(Sheep sheep){
        super(sheep);
    }
    
    @Override
    public Sheep clone() {
        return new Sheep(this);
    }
    
    @Override
    public void initParam() {
        this.setPower(4);
        this.setInitiative(4);
    }
}

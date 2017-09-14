package model.animals;

import model.World;
import model.Position;


public class Wolf extends Animal{
   
    public Wolf(Position position, World world) {
        super(position, world);
    }
    
    public Wolf(Position position) {
        super(position, null);
    }
    
    public Wolf(Wolf wolf){
        super(wolf);
    }
    
    @Override
    public Wolf clone() {
        return new Wolf(this);
    }
    
   @Override
    public void initParam() {
        this.setPower(9);
        this.setInitiative(5);
    } 
}

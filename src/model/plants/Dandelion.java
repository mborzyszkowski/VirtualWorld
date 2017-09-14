package model.plants;

import model.Action;
import model.World;
import model.Position;
import java.util.ArrayList;

public class Dandelion extends Plant {

    public Dandelion(Position position, World world) {
        super(position, world);
    }

    public Dandelion(Position position) {
        super(position, null);
    }

    public Dandelion(Dandelion dandelion) {
        super(dandelion);
    }

    @Override
    public Dandelion clone() {
        return new Dandelion(this);
    }

    @Override
    public void initParam() {
        this.setPower(0);
        this.setInitiative(0);
        this.setReproductionProbability(30);
    }

    @Override
    public ArrayList<Action> move() {
        ArrayList<Action> result = new ArrayList<Action>();
        for (int i = 0; i < 3; i++) {
            result.addAll(super.move());
            if (!result.isEmpty()) {
                break;
            }
        }
        return result;
    }
}

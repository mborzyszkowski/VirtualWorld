package view;

import java.awt.Point;
import java.awt.Polygon;

public class Hexagon extends Polygon {
    public static final int NUM_OF_SIDES = 6;
    
    public Hexagon(Point center, int sideLenght){
        npoints = NUM_OF_SIDES;
        xpoints = new int[NUM_OF_SIDES];
        ypoints = new int[NUM_OF_SIDES];

        for(int i = 0; i < NUM_OF_SIDES; i++){
            xpoints[i] = (int) (center.x + Math.cos(2* Math.PI *(double)i / NUM_OF_SIDES) * sideLenght);
            ypoints[i] = (int) (center.y + Math.sin(2* Math.PI *(double)i / NUM_OF_SIDES) * sideLenght);
        }
    }
}

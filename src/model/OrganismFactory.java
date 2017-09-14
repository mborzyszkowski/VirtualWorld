package model;

import java.awt.Color;
import model.animals.*;
import model.plants.Dandelion;
import model.plants.DeadlyNightshade;
import model.plants.Grass;
import model.plants.Guarana;
import model.plants.SosnowskiHogweed;

public class OrganismFactory {

    public Organism createOrganism(Species species, Position position) {
        Organism organism = null;
        if (position.getX() != -1 && position.getY() != -1) {
            switch (species) {
                case ANTELOPE:
                    organism = new Antelope(position);
                    organism.setColor(Color.ORANGE);
                    break;
                case SOSNOWSKIHOGWEED:
                    organism = new SosnowskiHogweed(position);
                    organism.setColor(Color.CYAN);
                    break;
                case HUMAN:
                    organism = new Human(position);
                    organism.setColor(Color.BLACK);
                    break;
                case GUARANA:
                    organism = new Guarana(position);
                    organism.setColor(Color.BLUE);
                    break;
                case FOX:
                    organism = new Fox(position);
                    organism.setColor(Color.RED);
                    break;
                case DANDELION:
                    organism = new Dandelion(position);
                    organism.setColor(Color.YELLOW);
                    break;
                case SHEEP:
                    organism = new Sheep(position);
                    organism.setColor(Color.LIGHT_GRAY);
                    break;
                case GRASS:
                    organism = new Grass(position);
                    organism.setColor(Color.GREEN);
                    break;
                case DEADLYNIGHTSHADE:
                    organism = new DeadlyNightshade(position);
                    organism.setColor(Color.MAGENTA);
                    break;
                case WOLF:
                    organism = new Wolf(position);
                    organism.setColor(Color.DARK_GRAY);
                    break;
                case TURTLE:
                    organism = new Turtle(position);
                    organism.setColor(Color.PINK);
                    break;
            }
            organism.initParam();
        }
        return organism;
    }
}

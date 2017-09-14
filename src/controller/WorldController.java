package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import model.Organism;
import model.OrganismFactory;
import model.Position;
import model.Species;
import model.World;
import model.animals.Human;
import view.Hexagon;
import view.WorldView;

public class WorldController {

    private World model;
    private WorldView view;

    public WorldController(World model, WorldView view) {
        this.model = model;
        this.view = view;

        view.setController(this);
    }

    public World getModel() {
        return model;
    }

    public WorldView getView() {
        return view;
    }

    public int getTurnNumber() {
        return model.getTurn();
    }

    public void performNextTurn() {
        model.makeTurn();
    }

    public String getActionLoggerReport() {
        return "" + model.getActionLogger();
    }

    public boolean isActionLoggerEmpty() {
        return model.getActionLogger().isEmpty();
    }

    public void clearActionLogger() {
        model.getActionLogger().clear();
    }

    public boolean  canTurnHumanSkillOn(){
        Organism org = model.getHuman();
        if (org != null) {
            if (org instanceof Human){
                Human human = (Human)org;
                if (human.getCounterTurnOfSkill() == 0 && !human.isIsASkill())
                    return true;
            }
        }
        return false;
    }
    
    public void turnHumanSkillOn(){
        Organism org = model.getHuman();
        if (org != null) {
            if (org instanceof Human){
                Human human = (Human)org;
                if (human.getCounterTurnOfSkill() == 0 && !human.isIsASkill())
                    human.setRunSkill(true);
            }
        }
    }
    
    public String getHumanStatus() {
        Organism org = model.getHuman();
        String result = "Human: ";
        if (org != null) {
            if (org instanceof Human) {
                switch (((Human) org).getDirection()) {
                    case FOUR_DIR_UP:
                    case SIX_DIR_UP:
                        result += "up";
                        break;
                    case FOUR_DIR_DOWN:
                    case SIX_DIR_DOWN:
                        result += "down";
                        break;
                    case FOUR_DIR_LEFT:
                        result += "left";
                        break;
                    case FOUR_DIR_RIGHT:
                        result += "right";
                        break;
                    case SIX_DIR_DOWN_LEFT:
                        result += "down left";
                        break;
                    case SIX_DIR_DOWN_RIGHT:
                        result += "down right";
                        break;
                    case SIX_DIR_UP_RIGHT:
                        result += "up right";
                        break;
                    case SIX_DIR_UP_LEFT:
                        result += "up left";
                        break;
                    case STOP:
                        result += "stop";
                        break;
                }
            }
        } else result +="no Human";
        return result;
    }
    
    public int getHumanCounterTurnOfSkill(){
        int counter = -1;
        Organism org = model.getHuman();
        if (org != null) {
            if (org instanceof Human){
                Human human = (Human)org;
                counter = human.getCounterTurnOfSkill();
            }
        }
        return counter;
    }
    public boolean addOrganism(Species species, Position position) {
        OrganismFactory factory = new OrganismFactory();
        Organism organism = factory.createOrganism(species, position);
        organism.setWorld(model);
        return model.addOrganism(organism);
    }

    public void saveModel(String fileName) {
        try (FileOutputStream outFile = new FileOutputStream(new File(fileName));
                ObjectOutputStream objOut = new ObjectOutputStream(outFile)) {
            objOut.writeObject(model);
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + ex);
        } catch (IOException ex) {
            System.err.println("IO Error: " + ex);
        }
    }

    public void loadModel(String fileName) {
        try (FileInputStream inFile = new FileInputStream(new File(fileName));
                ObjectInputStream objIn = new ObjectInputStream(inFile)) {
            model = (World) objIn.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + ex);
        } catch (IOException ex) {
            System.err.println("IO Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("ClassNotFoundException: " + ex);
        }
    }

    public ArrayList<ArrayList<WorldCell>> getWorldMap() {
        ArrayList<ArrayList<WorldCell>> map = new ArrayList<>();
        ArrayList<WorldCell> pomWorldRow = new ArrayList<>();
        Organism org;
        Shape shape;
        int siteLenght = view.getSideMapLenght();
        double triangleHeight = siteLenght * Math.sqrt(3) / 2;
        for (int y = 0; y < model.getBoardY(); y++) {
            for (int x = 0; x < model.getBoardX(); x++) {
                org = model.getOrganismFromPosition(new Position(x, y));
                if (model.getCompasRose() == 8) {
                    shape = new Rectangle(x * siteLenght, y * siteLenght, siteLenght, siteLenght);
                } else if (x % 2 == 0) {
                    shape = new Hexagon(new Point((int) (x * (siteLenght + triangleHeight / 2)
                            + siteLenght),
                            (int) ((y + 1) * 2 * triangleHeight)), siteLenght);
                } else {
                    shape = new Hexagon(new Point((int) (x * (siteLenght + triangleHeight / 2) + siteLenght),
                            (int) (y * 2 * triangleHeight + triangleHeight)), siteLenght);
                }
                if (org != null) {
                    pomWorldRow.add(new WorldCell(x, y, org.getColor(), false, shape));
                } else {
                    pomWorldRow.add(new WorldCell(x, y, Color.WHITE, true, shape));
                }
            }
            map.add(pomWorldRow);
            pomWorldRow = new ArrayList<>();
        }
        return map;
    }

}

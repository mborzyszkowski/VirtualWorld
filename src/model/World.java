package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import model.animals.Human;


public class World implements Serializable {

    private ArrayList<Organism> organisms;
    private ArrayList<Organism> newOrganisms;
    private ActionLogger actionLogger;
    private int compasRose;
    private int boardX;
    private int boardY;
    private int turn;

    public World(int boardX, int boardY, int turn, int compasRose) {
        this.organisms = new ArrayList<>();
        this.newOrganisms = new ArrayList<>();
        this.actionLogger = new ActionLogger();
        this.boardX = boardX;
        this.boardY = boardY;
        this.turn = 0;
        this.compasRose = compasRose;
    }

    public int getBoardX() {
        return boardX;
    }

    public void setBoardX(int boardX) {
        this.boardX = boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public void setBoardY(int boardY) {
        this.boardY = boardY;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getCompasRose() {
        return compasRose;
    }

    public void setCompasRose(int compasRose) {
        this.compasRose = compasRose;
    }

    public ArrayList<Organism> getOrganisms() {
        return organisms;
    }

    public ActionLogger getActionLogger() {
        return actionLogger;
    }
    
    private void makeMove(Action action) {
        switch (action.getAction()) {
            case A_MOVE:
                actionLogger.addDescriptions(action.getOrganism().getClass().getSimpleName() + 
                        ": zmiana pozycji z: " + action.getOrganism().getPosition() + " na: " + 
                        action.getPosition());
                action.getOrganism().setPosition(action.getPosition(), true);
                break;
            case A_REMOVE:
                actionLogger.addDescriptions( action.getOrganism().getClass().getSimpleName() + 
                        ": usunięcie z pozycji: " + action.getOrganism().getPosition());
                action.getOrganism().setPosition(new Position(-1, -1), true);
                break;
            case A_ADD:
                actionLogger.addDescriptions(action.getOrganism().getClass().getSimpleName() + 
                        ": dodanie na pozycję: " + action.getPosition());
                this.newOrganisms.add(action.getOrganism());
                break;
            case A_INCREASEPOWER:
                actionLogger.addDescriptions(action.getOrganism().getClass().getSimpleName() + 
                        ": zwiększenie siły o: " + action.getValue());
                action.getOrganism().setPower(action.getOrganism().getPower() + action.getValue());
                break;
        }
    }

    public void makeTurn() {
        ArrayList<Action> actions = new ArrayList<Action>();
        Position pomPosition;
        Organism collisionOrganism;

        for (Organism o : this.organisms) {
            //Move
            pomPosition = o.getPosition();
            if (pomPosition.getX() >= 0 && pomPosition.getY() >= 0) {
                actions = o.move();
                for (Action a : actions) {
                    makeMove(a);
                }
                actions.clear();
                pomPosition = o.getPosition();
                o.setPosition(new Position(-1, -1), false);
                collisionOrganism = getOrganismFromPosition(pomPosition);
                o.setPosition(pomPosition, false);
                //Collision
                actions = o.collision(collisionOrganism);
                for (Action a : actions) {
                    makeMove(a);
                }
            }
            actions.clear();
        }
        // remove each Organism with position (-1, -1)
        organisms.removeIf(org -> org.getPosition().getX() == -1 && org.getPosition().getY() == -1);
        newOrganisms.removeIf(org -> org.getPosition().getX() == -1 && org.getPosition().getY() == -1); 
        // add newOrganisms to Organisms
        organisms.addAll(newOrganisms);
        Collections.sort(organisms, new OrganismInitiativeComparator());
        this.newOrganisms.clear();
        this.turn++;
    }

    public boolean addOrganism(Organism newOrganism) {
        int pomX = newOrganism.getPosition().getX();
        int pomY = newOrganism.getPosition().getY();
        if (pomX >= 0 && pomY >= 0 && pomX < this.boardX && pomY < boardY) {
            organisms.add(newOrganism);
            Collections.sort(organisms, new OrganismInitiativeComparator());
            return true;
        } else {
            return false;
        }
    }

    public Organism getOrganismFromPosition(Position position) {
        Organism pomOrganism = null;
        for (Organism o : this.organisms) {
            if (o.getPosition().equals(position)) {
                pomOrganism = o;
                break;
            }
        }
        for (Organism o : this.newOrganisms) {
            if (o.getPosition().equals(position)) {
                pomOrganism = o;
                break;
            }
        }
        return pomOrganism;
    }

    public Position getFreePositionInWorld() {
        ArrayList<Position> freePosition = new ArrayList<>();
        for (int y = 0; y < this.boardY; y++) {
            for (int x = 0; x < this.boardX; x++) {
                if (getOrganismFromPosition(new Position(x, y)) == null)
                {
                    freePosition.add(new Position(x, y));
                }
            }
        }
        if (freePosition.isEmpty()) {
            return (new Position(-1, -1));
        }
        return freePosition.get((new Random()).nextInt(freePosition.size()));
    }

    public boolean positionOnBoard(Position position) { 
        return position.getX() >= 0 && position.getY() >= 0 && position.getX() < this.boardX && position.getY() < this.boardY;
    }

    public Position getFreeNeighboringPosition(Position position) {

        ArrayList<Position> pomNeighboringPositions = getListOfNeighboringPositions(position, 1);
        for (Position pos : pomNeighboringPositions) {
            if (getOrganismFromPosition(pos) == null) {
                return pos;
            }
        }
        return new Position(-1, -1);
   }

    public ArrayList<Position> getListOfNeighboringPositions(Position position, int distance) {
        ArrayList<Position> result = new ArrayList<>();
        ArrayList<Position> shifts = new ArrayList<>();
        if (this.compasRose == 8) {
            if (distance == 1) {
                Position[] shiftsArray = new Position[]{
                    new Position(-1, -1), new Position(0, -1), new Position(1, -1),
                    new Position(1, 0), new Position(1, 1), new Position(0, 1),
                    new Position(-1, 1), new Position(-1, 0)
                };
                shifts.addAll(Arrays.asList(shiftsArray));
            } else if (distance == 2) {
                Position[] shiftsArray = new Position[]{
                    new Position(-2, -2), new Position(-1, -2), new Position(0, -2), new Position(1, -2),
                    new Position(2, -2), new Position(2, -1), new Position(2, 0), new Position(2, 1),
                    new Position(2, 2), new Position(1, 2), new Position(0, 2), new Position(-1, 2),
                    new Position(-2, 2), new Position(-2, 1), new Position(-2, 0), new Position(-2, -1)
                };
                shifts.addAll(Arrays.asList(shiftsArray));
            }
        } else if (this.compasRose == 6) {
            if (distance == 1) {
                if (position.getX() % 2 == 0) { // dolny w łańcuchu
                    Position[] shiftsArray = new Position[]{
                        new Position(0, -1), new Position(1, 0), new Position(1, 1),
                        new Position(0, 1), new Position(-1, 1), new Position(-1, 0)
                    };
                    shifts.addAll(Arrays.asList(shiftsArray));
                } else { // górny w łańcuchu
                    Position[] shiftsArray = new Position[]{
                        new Position(0, -1), new Position(1, -1), new Position(1, 0),
                        new Position(0, 1), new Position(-1, 0), new Position(-1, -1)
                    };
                    shifts.addAll(Arrays.asList(shiftsArray));
                }
            } else if (distance == 2) {
                if (position.getX() % 2 == 0) { // dolny w łańcuchu
                    Position[] shiftsArray = new Position[]{
                        new Position(-2, -1), new Position(-1, -1), new Position(0, -2),
                        new Position(1, -1), new Position(2, -1), new Position(2, 0),
                        new Position(2, 1), new Position(1, 2), new Position(0, 2),
                        new Position(-1, 2), new Position(-2, 1), new Position(-2, 0)
                    };
                    shifts.addAll(Arrays.asList(shiftsArray));
                } else { // górny w łańcuchu
                    Position[] shiftsArray = new Position[]{
                        new Position(-2, -1), new Position(-1, -2), new Position(0, -2),
                        new Position(1, -2), new Position(2, -1), new Position(2, 0),
                        new Position(2, 1), new Position(1, 1), new Position(0, 2),
                        new Position(-1, 1), new Position(-2, 1), new Position(-2, 0)
                    };
                    shifts.addAll(Arrays.asList(shiftsArray));
                }
            }
        }
        Position pomPosition;
        for (Position sh : shifts) {
            pomPosition = new Position(position.getX() + sh.getX(), position.getY() + sh.getY());
            if (positionOnBoard(pomPosition)) {
                result.add(pomPosition);
            }
        }
        return result;
    }
    public Organism getHuman(){
        List<Organism> humanList = organisms.stream()
                .filter(a->(a instanceof Human))
                .collect(Collectors.toList());
        if(humanList.size() > 0)
            return humanList.get(0);
        return null;
    }
}

package WorldWindow;

import controller.WorldController;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import model.Organism;
import model.OrganismFactory;
import model.Species;
import model.World;
import view.WorldView;

public class Main {

    public static void main(String args[]) {

        Properties prop = new Properties();
        World model;

        int worldSizeX, worldSizeY, worldTurns, compasRose;
        try (FileReader reader = new FileReader("src/resources/app.properties")) {
            prop.load(reader);
            worldSizeX = Integer.parseInt(prop.getProperty("world.size.x"));
            worldSizeY = Integer.parseInt(prop.getProperty("world.size.y"));
            worldTurns = Integer.parseInt(prop.getProperty("world.turns"));
            compasRose = Integer.parseInt(prop.getProperty("compasRose"));

            model = new World(worldSizeX, worldSizeY, worldTurns, compasRose);

            String propName, propValue;
            int propValInt;
            OrganismFactory factory = new OrganismFactory();
            Organism org;

            // add one human
            org = factory.createOrganism(Species.HUMAN, model.getFreePositionInWorld());
            org.setWorld(model);
            model.addOrganism(org);
            // add the rest
            for (Species sp : Species.values()) {
                propName = "number.of." + sp.name().toLowerCase();
                propValue = prop.getProperty(propName);
                if (propValue != null && !propValue.equals("human")) {
                    propValInt = Integer.parseInt(propValue);
                    for (int i = 0; i < propValInt; i++) {
                        org = factory.createOrganism(sp, model.getFreePositionInWorld());
                        if (org != null) {
                            org.setWorld(model);
                            model.addOrganism(org);
                        }
                    }
                }
            }
            //--------------
            // Simple MVC
            //--------------

            WorldView view = new WorldView();
            new WorldController(model, view);

        } catch (IOException e) {
            System.err.println("app.properties: " + e);
        } catch (NumberFormatException e) {
            System.err.println("ParseInt error: " + e);
        }
    }
}

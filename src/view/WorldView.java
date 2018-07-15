package view;

import controller.WorldCell;
import controller.WorldController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.*;
import model.Organism;
import model.Position;
import model.Species;
import model.animals.Human;
import model.DirectionEnum;

public class WorldView extends JFrame {

    public final int WINDOW_HEIGHT = 600;
    public final int WINDOW_WIDTH = 800;
    public final String saveFileName = "saveWorld.ser";

    private WorldController controller;
    private int sideMapLenght;
    private JLabel lbOrganismStatus;

    public void setController(WorldController controller) {
        this.controller = controller;
    }

    public int getSideMapLenght() {
        return sideMapLenght;
    }

    public void setSideMapLenght(int sideMapLenght) {
        this.sideMapLenght = sideMapLenght;
    }

    public JLabel getLbOrganismStatus() {
        return lbOrganismStatus;
    }

    public void setLbOrganismStatus(JLabel lbOrganismStatus) {
        this.lbOrganismStatus = lbOrganismStatus;
    }

    public WorldView() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle("Maciej Borzyszkowski 165407");
        this.sideMapLenght = 15;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(50, 50));

        JTextArea txtActionLogger = new JTextArea("Tura: 0\n", 30, 25);
        txtActionLogger.setEditable(false);
        JScrollPane scrActionLogger = new JScrollPane(txtActionLogger);
        scrActionLogger.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton btNextTurn = new JButton("Next Turn");
        JButton btSave = new JButton("Save");
        JButton btLoad = new JButton("Load");
        JLabel lbTurnNum = new JLabel("Turn number: 0");
        lbOrganismStatus = new JLabel("   Info: ");
        JLabel lbHumanStatus = new JLabel();
        WordMapPanel wmPanel = new WordMapPanel();
        JScrollPane scrWordMapPanel = new JScrollPane(wmPanel);
        scrWordMapPanel.setPreferredSize(new Dimension(470, 400));
        scrWordMapPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrWordMapPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JButton btAbility = new JButton("Human ability: 0");
        btAbility.addActionListener((ActionEvent e) -> {
            controller.turnHumanSkillOn();
            wmPanel.setFocusable(true);
            wmPanel.requestFocusInWindow();
        });

        btNextTurn.addActionListener((ActionEvent e) -> {
            controller.performNextTurn();
            lbTurnNum.setText("Turn number: " + controller.getTurnNumber());
            if (!controller.isActionLoggerEmpty()) {
                txtActionLogger.append("\n" + lbTurnNum.getText() + "\n"
                        + controller.getActionLoggerReport());
                txtActionLogger.setCaretPosition(txtActionLogger.getDocument().getLength());
                controller.clearActionLogger();
                lbHumanStatus.setText(controller.getHumanStatus());
                if (controller.canTurnHumanSkillOn()) {
                    btAbility.setEnabled(true);
                } else {
                    btAbility.setEnabled(false);
                }
                btAbility.setText("Human ability: " + controller.getHumanCounterTurnOfSkill());
            }
            lbOrganismStatus.setText("   Info: ");
            scrWordMapPanel.repaint();
            wmPanel.setFocusable(true);
            wmPanel.requestFocusInWindow();
        });

        btSave.addActionListener((ActionEvent e) -> {
            controller.saveModel(saveFileName);
            wmPanel.setFocusable(true);
            wmPanel.requestFocusInWindow();
        });

        btLoad.addActionListener((ActionEvent e) -> {
            controller.loadModel(saveFileName);
            lbTurnNum.setText("Turn number: " + controller.getTurnNumber());
            btAbility.setText("Human ability: " + controller.getHumanCounterTurnOfSkill());
            wmPanel.setFocusable(true);
            wmPanel.requestFocusInWindow();
        });

        wmPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(".keyPressed()");
                Organism org = controller.getModel().getHuman();
                if (org != null) {
                    if (org instanceof Human) {
                        if (controller.getModel().getCompasRose() == 8) {
                            switch (e.getKeyCode()) {
                                case KeyEvent.VK_UP:
                                    ((Human) org).setDirection(DirectionEnum.FOUR_DIR_UP);
                                    break;
                                case KeyEvent.VK_DOWN:
                                    ((Human) org).setDirection(DirectionEnum.FOUR_DIR_DOWN);
                                    break;
                                case KeyEvent.VK_RIGHT:
                                    ((Human) org).setDirection(DirectionEnum.FOUR_DIR_RIGHT);
                                    break;
                                case KeyEvent.VK_LEFT:
                                    ((Human) org).setDirection(DirectionEnum.FOUR_DIR_LEFT);
                                    break;
                            }
                        } else if (controller.getModel().getCompasRose() == 6) {
                            switch (e.getKeyCode()) {
                                case KeyEvent.VK_UP:
                                    ((Human) org).setDirection(DirectionEnum.SIX_DIR_UP);
                                    break;
                                case KeyEvent.VK_DOWN:
                                    ((Human) org).setDirection(DirectionEnum.SIX_DIR_DOWN);
                                    break;
                                case KeyEvent.VK_RIGHT:
                                    if (e.isShiftDown()) {
                                        ((Human) org).setDirection(DirectionEnum.SIX_DIR_UP_RIGHT);
                                    } else {
                                        ((Human) org).setDirection(DirectionEnum.SIX_DIR_DOWN_RIGHT);
                                    }
                                    break;
                                case KeyEvent.VK_LEFT:
                                    if (e.isShiftDown()) {
                                        ((Human) org).setDirection(DirectionEnum.SIX_DIR_UP_LEFT);
                                    } else {
                                        ((Human) org).setDirection(DirectionEnum.SIX_DIR_DOWN_LEFT);
                                    }
                                    break;
                            }
                        }
                    }
                }
                lbHumanStatus.setText(controller.getHumanStatus());
            }

        });

        lbHumanStatus.setText("Human: stop");

        wmPanel.setFocusable(true);
        wmPanel.requestFocusInWindow();
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btNextTurn);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btSave);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btLoad);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(btAbility);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(lbTurnNum);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(lbHumanStatus);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        lbOrganismStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPanel.add(lbOrganismStatus);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(scrActionLogger, BorderLayout.EAST);
        mainPanel.add(scrWordMapPanel, BorderLayout.CENTER);

        Container mainContainer = getContentPane();
        mainContainer.add(mainPanel);
        setVisible(true);
    }

    class WordMapPanel extends JPanel {

        private int pref_size_w = WINDOW_WIDTH;
        private int pref_size_h = WINDOW_HEIGHT;

        private ArrayList<ArrayList<WorldCell>> worldCellMap;

        public WordMapPanel() {
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mouseClicked(e);
                    for (ArrayList<WorldCell> wcList : worldCellMap) {
                        for (WorldCell wc : wcList) {
                            if (wc.getShape().contains(e.getPoint())) {
                                if (wc.isIsEmpty()) {
                                    //System.out.println("Empty: " + wc.getX() + ", " + wc.getY());
                                    String[] possibilities = Stream
                                            .of(Species.values())
                                            .filter(s -> (!s.equals(Species.HUMAN)))
                                            .map(Species::name)
                                            .toArray(String[]::new);
                                    String replay;
                                    replay = (String) JOptionPane.showInputDialog(
                                            controller.getView(), "Choose organism:",
                                            "Choose organism", JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            possibilities, possibilities[0]);
                                    //System.out.println(replay);
                                    if (replay != null) {
                                        if (controller.addOrganism(Species.valueOf(replay),
                                                new Position(wc.getX(), wc.getY()))) {
                                            repaint();
                                        }
                                    }
                                } else {
                                    //System.out.println("Not: " + wc.getX() + ", " + wc.getY());
                                    getLbOrganismStatus().setText("   Info: "
                                            + controller
                                                    .getModel()
                                                    .getOrganismFromPosition(new Position(wc.getX(), wc.getY())));
                                    repaint();
                                }
                            }
                        }
                    }
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(pref_size_w, pref_size_h);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (controller.getModel().getCompasRose() == 8) {
                pref_size_w = sideMapLenght * controller.getModel().getBoardX() + 5;
                pref_size_h = sideMapLenght * controller.getModel().getBoardY() + 5;
            } else if (controller.getModel().getCompasRose() == 6) {
                pref_size_w = (int) ((sideMapLenght + Math.sqrt(3) * sideMapLenght / 4) * controller.getModel().getBoardX() + Math.sqrt(3) * sideMapLenght / 4 + 5);
                pref_size_h = (int) (sideMapLenght * Math.sqrt(3) * controller.getModel().getBoardY() + sideMapLenght * Math.sqrt(3) / 4 + 5);
            }

            this.setPreferredSize(new Dimension(pref_size_w, pref_size_h));
            worldCellMap = controller.getWorldMap();
            Graphics2D g2 = (Graphics2D) g;
            for (int y = 0; y < controller.getModel().getBoardY(); y++) {
                for (int x = 0; x < controller.getModel().getBoardX(); x++) {
                    if (!this.worldCellMap.get(y).get(x).isIsEmpty()) {
                        g2.setColor(this.worldCellMap.get(y).get(x).getColor());
                        g2.fill(this.worldCellMap.get(y).get(x).getShape());
                    }
                    g2.setColor(Color.WHITE);
                    g2.draw(this.worldCellMap.get(y).get(x).getShape());
                }
            }
        }

    }
}

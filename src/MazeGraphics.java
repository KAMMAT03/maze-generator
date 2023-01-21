import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeGraphics extends JPanel implements Runnable {
    private MazeGenerator maze;

    public MazeGraphics(MazeSize size) {
        maze = new MazeGenerator(size);
    }



    public void display() {
        JFrame window = new JFrame("Maze Generator");
        window.setContentPane(this);

        //menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("New");
        JMenuItem smallMaze = new JMenuItem("SMALL");
        JMenuItem mediumMaze = new JMenuItem("MEDIUM");
        JMenuItem bigMaze = new JMenuItem("BIG");

        fileMenu.add(smallMaze);
        fileMenu.addSeparator();
        fileMenu.add(mediumMaze);
        fileMenu.addSeparator();
        fileMenu.add(bigMaze);
        menuBar.add(fileMenu);

        JMenu solveMenu = new JMenu("Solve");
        JMenuItem solveItem = new JMenuItem("Solve");
        solveMenu.add(solveItem);
        menuBar.add(solveMenu);

        JMenu saveAsImageMenu = new JMenu("Save");
        JMenuItem saveAsImageItem = new JMenuItem("Save as image");
        saveAsImageMenu.add(saveAsImageItem);
        menuBar.add(saveAsImageMenu);


        JMenu exitMenu = new JMenu("Exit");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitMenu.add(exitItem);
        menuBar.add(exitMenu);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        smallMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                MazeGenerator small = new MazeGenerator(MazeSize.SMALL);
                small.makeMaze();
                repaint();
                MazeGraphics graphics = new MazeGraphics(MazeSize.SMALL);
                graphics.display();
            }
        });

        mediumMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                MazeGenerator medium = new MazeGenerator(MazeSize.MEDIUM);
                medium.makeMaze();
                repaint();
                MazeGraphics graphics = new MazeGraphics(MazeSize.MEDIUM);
                graphics.display();
            }
        });

        bigMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.dispose();
                MazeGenerator big = new MazeGenerator(MazeSize.BIG);
                big.makeMaze();
                repaint();
                MazeGraphics graphics = new MazeGraphics(MazeSize.BIG);
                graphics.display();
            }
        });

        solveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(maze.mazeExists) {
                    maze.solveMaze(1, 1);
                }
                maze.mazeExists = false;
                repaint();
            }
        });

        saveAsImageItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage image = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics g = image.getGraphics();
                    window.paint(g);
                    g.dispose();
                    JFileChooser fileChooser =   new JFileChooser();

                    File file = new File("maze.png");
                    ImageIO.write(image, "PNG", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



        window.setJMenuBar(menuBar);



        //koniec menu

        setPreferredSize(new Dimension(maze.getCellSize() * maze.getColumns(), maze.getCellSize() * maze.getRows()));
        new Thread(this).start();

        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void run(){
        try {
            Thread.sleep(1000);
        } // wait a bit before starting
        catch (InterruptedException e) {
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = maze.getCellSize();
        int h = maze.getCellSize();
        for (int j = 0; j < maze.getColumns(); j++) {
            for (int i = 0; i < maze.getRows(); i++) {
                Color cellColor = maze.getCellColor(i, j);
                g.setColor(cellColor);
                int x = j * w;
                int y = i * h;
                g.fillRect(x, y, w, h);
            }
        }
    }
}

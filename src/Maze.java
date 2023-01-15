import java.awt.*;
import javax.swing.*;
import java.util.Scanner;



public class Maze extends JPanel implements Runnable {

    public static void main(String[] args) {
        System.out.println("Select size of the maze: 1 - small 2 - medium, 3 - big");
        Scanner sc = new Scanner(System.in);
        int initialSize = -1;
        while(initialSize != 1 && initialSize != 2 && initialSize != 3)
            initialSize = sc.nextInt();



        JFrame window = new JFrame("Maze Generator");
        window.setContentPane(new Maze(initialSize));
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }


    int[][] maze;

    final static int wallColor = 0;
    final static int pathColor = 1;


    Color[] color;
    /*
    Można wykorzystać wartości rows i columns do modyfikowania wielkości labiryntu. Wartości rows i columns muszą być.
    nieparzyste!!!
     */
    int rows = 27;
    int columns = 33;
    int buildSpeed = 100;
    int blockSize = 24;
    int width = -1;
    int height = -1;
    boolean mazeExists = false;

    public Maze(int size) {

        setSize(size);
        color = new Color[]{
                Color.BLACK,
                Color.WHITE,
                Color.GREEN,
                Color.RED
        };
        setPreferredSize(new Dimension(blockSize * columns, blockSize * rows));
        new Thread(this).start();

    }

    void checkSize() {
        if (getWidth() != width || getHeight() != height) {
            width = getWidth();
            height = getHeight();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        checkSize();
        makeMaze();
        redrawMaze(g);
    }

    void redrawMaze(Graphics g) {
        if (mazeExists) {
            int w = getCellWidth();
            int h = getCellHeight();
            for (int j = 0; j < columns; j++) {
                for (int i = 0; i < rows; i++) {
                    Color cellColor = getCellColor(i, j);
                    g.setColor(cellColor);
                    int x = getCellXCoordinate(j, w);
                    int y = getCellYCoordinate(i, h);
                    g.fillRect(x, y, w, h);
                }
            }
        }
    }

    int getCellWidth() {
        return width / columns;
    }

    int getCellHeight() {
        return height / rows;
    }

    Color getCellColor(int i, int j) {
        if (maze[i][j] < 0 && !(i==1 && j==1 || i==(rows - 2) && j==(columns - 2))) {
            return color[pathColor];
        } else if (i==1 && j==1) {
            return color[2];
        } else if (i==(rows - 2) && j==(columns - 2)) {
            return color[3];
        } else {
            return color[maze[i][j]];
        }
    }

    int getCellXCoordinate(int j, int cellWidth) {
        return (j * cellWidth);
    }

    int getCellYCoordinate(int i, int cellHeight) {
        return (i * cellHeight);
    }

    public void run() {
        makeMaze();
        mazeExists = false;
    }

    void makeMaze() {
        if (maze == null)
            maze = new int[rows][columns];
        int i, j;
        int roomCount = 0;
        for (i = 0; i < rows; i++)
            for (j = 0; j < columns; j++)
                maze[i][j] = wallColor;
        for (i = 1; i < rows - 1; i += 2)
            for (j = 1; j < columns - 1; j += 2) {
                roomCount++;
                maze[i][j] = -roomCount;
            }
        mazeExists = true;
        paintNet();
    }

    synchronized void paintNet(){
        repaint();
        try {
            wait(buildSpeed);
        } catch (InterruptedException e) {
        }
    }
    public void setSize(int size) {
        switch (size) {
            case 1:
                setColumns(21);
                setRows(21);
                break;
            case 2:
                setColumns(31);
                setRows(31);
                break;
            case 3:
                setColumns(41);
                setRows(41);
                break;
        }
    }

    public void setColumns(int size) {
        this.columns = size;
    }

    public void setRows(int size) {
        this.rows = size;
    }

}
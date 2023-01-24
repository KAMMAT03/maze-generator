import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        String size = JOptionPane.showInputDialog("Select size of the maze: SMALL , MEDIUM , BIG");

        MazeSize initialSize;
        do {
            initialSize = MazeSize.valueOf(size.toUpperCase());
        } while (initialSize != MazeSize.BIG && initialSize != MazeSize.MEDIUM && initialSize != MazeSize.SMALL);

        MazeGraphics graphics = new MazeGraphics(initialSize);
        graphics.display();
    }
}

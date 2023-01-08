import java.awt.*;
import javax.swing.*;

public class Maze extends JPanel {

    final static int backgroundCode = 0;
    int[][] maze;
    Color[] color;
    int rows = 20;
    int columns = 20;
    int blockSize = 12;
    public Maze() {
        color = new Color[]{
                new Color(0, 0, 0)
        };
        setBackground(color[backgroundCode]);
        setPreferredSize(new Dimension(blockSize * columns, blockSize * rows));

    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Maze Generator");
        window.setContentPane(new Maze());
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}

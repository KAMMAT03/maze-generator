import javax.swing.*;
import java.awt.*;

abstract class MazeAttributes extends JPanel {
    public int[][] maze;
    public int rows = 21;
    public int columns = 31;
    public int cellSize = 24;
    final static int wallColor = 0;
    final static int roomColor = 1;
    final static int pathColor = 4;
    public boolean mazeExists = false;
    Color[] color;

    public void setColumns(int size) {
        this.columns = size;
    }

    public void setRows(int size) {
        this.rows = size;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setSize(MazeSize size) {
        switch (size) {
            case SMALL -> {
                setColumns(21);
                setRows(21);
            }
            case MEDIUM -> {
                setColumns(27);
                setRows(27);
            }
            case BIG -> {
                setColumns(33);
                setRows(33);
            }
        }
    }

    MazeCell getCell(int i, int j) {
        if (maze[i][j] < 0 && !(i==1 && j==1 || i==(rows - 2) && j==(columns - 2))) {
            return MazeCell.PATH;
        } else if (i==1 && j==1) {
            return MazeCell.START;
        } else if (i==(rows - 2) && j==(columns - 2)) {
            return MazeCell.END;
        } else {
            return MazeCell.WALL;
        }
    }
    Color getCellColor(int i, int j) {
        switch (getCell(i,j)) {
            case PATH -> {
                return color[roomColor];
            }
            case START -> {
                return color[2];
            }
            case END -> {
                return color[3];
            }
            case WALL -> {
                return color[maze[i][j]];
            }
        }
        return null;
    }
}

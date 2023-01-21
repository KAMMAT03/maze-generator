import java.awt.*;

class MazeGenerator extends MazeAttributes{

    public MazeGenerator(MazeSize size) {
        color = new Color[]{
                Color.BLACK,
                Color.WHITE,
                Color.GREEN,
                Color.RED,
                Color.CYAN
        };
        setSize(size);
        makeMaze();
    }
    void makeMaze() {
        if (maze == null)
            maze = new int[rows][columns];
        int i, j;
        int roomCount = 0;
        int wallCount = 0;
        int[] wallRow = new int[(rows * columns) / 2];
        int[] wallCol = new int[(rows * columns) / 2];
        for (i = 0; i < rows; i++)
            for (j = 0; j < columns; j++)
                maze[i][j] = wallColor;
        for (i = 1; i < rows - 1; i += 2)
            for (j = 1; j < columns - 1; j += 2) {
                roomCount++;
                maze[i][j] = -roomCount;
                if (i < rows - 2) {
                    wallRow[wallCount] = i + 1;
                    wallCol[wallCount] = j;
                    wallCount++;
                }
                if (j < columns - 2) {
                    wallRow[wallCount] = i;
                    wallCol[wallCount] = j + 1;
                    wallCount++;
                }
            }
        mazeExists = true;
        repaint();
        int r;
        for (i = wallCount - 1; i > 0; i--) {
            r = (int) (Math.random() * i);  // choose a wall randomly and maybe tear it down
            tearDown(wallRow[r], wallCol[r]);
            wallRow[r] = wallRow[i];
            wallCol[r] = wallCol[i];
        }
        for (i = 1; i < rows - 1; i++)  // replace negative values in maze[][] with emptyCode
            for (j = 1; j < columns - 1; j++)
                if (maze[i][j] < 0)
                    maze[i][j] = roomColor;
    }

    synchronized void tearDown(int row, int col) {

        if (row % 2 == 1 && maze[row][col - 1] != maze[row][col + 1]) {
            // row is odd; wall separates rooms horizontally
            fill(row, col - 1, maze[row][col - 1], maze[row][col + 1]);
            maze[row][col] = maze[row][col + 1];
            repaint();
        } else if (row % 2 == 0 && maze[row - 1][col] != maze[row + 1][col]) {
            // row is even; wall separates rooms vertically
            fill(row - 1, col, maze[row - 1][col], maze[row + 1][col]);
            maze[row][col] = maze[row + 1][col];
            repaint();
        }
    }

    void fill(int row, int col, int replace, int replaceWith) {
        if (maze[row][col] == replace) {
            maze[row][col] = replaceWith;
            fill(row + 1, col, replace, replaceWith);
            fill(row - 1, col, replace, replaceWith);
            fill(row, col + 1, replace, replaceWith);
            fill(row, col - 1, replace, replaceWith);
        }
    }

    boolean solveMaze(int row, int col) {
        if (maze[row][col] == roomColor) {
            maze[row][col] = pathColor;
            repaint();
            if (row == getRows() - 2 && col == getColumns() - 2)
                return true;  // path has reached goal
            if (solveMaze(row - 1, col) ||     // try to solve maze by extending path
                    solveMaze(row, col - 1) ||     //    in each possible direction
                    solveMaze(row + 1, col) ||
                    solveMaze(row, col + 1))
                return true;
            maze[row][col] = roomColor;
            //repaint();
        }
        return false;
    }
}

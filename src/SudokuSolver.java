public class SudokuSolver {
    private final String SEPARATOR = " ";
    private final int GRID_SIZE = 9;
    private final int BOX_SIZE = 3;
    private final Pair FULL_GRID = new Pair(GRID_SIZE, GRID_SIZE);

    private final int[][] grid;
    private long startTime;
    private long endTime;

    public SudokuSolver(int[][] grid) {
        this.grid = grid;
        startTime = 0;
        endTime = 0;
    }

    public boolean bruteForce() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        Pair nextLocation = getNextLocation();
        if (nextLocation.getX() == GRID_SIZE && nextLocation.getY() == GRID_SIZE) {
            endTime = System.currentTimeMillis();
            return true;
        }

        int row = nextLocation.getX();
        int col = nextLocation.getY();

        for (int num = 1; num < 10; num++) {
            if (canAssign(row, col, num)) {
                grid[row][col] = num;

                if (bruteForce()) {
                    return true;
                }

                grid[row][col] = 0;
            }
        }

        return false;
    }

    public void printGrid() {
        for (int[] row : grid) {
            System.out.print(SEPARATOR);
            for (int col: row) {
                System.out.print(col + SEPARATOR);
            }
            System.out.print("\n");
        }
    }

    public void printTime() {
        System.out.printf("Took: %d ms", endTime - startTime);
    }

    private Pair getNextLocation() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == 0) {
                    return new Pair(row, col);
                }
            }
        }
        return FULL_GRID;
    }

    private boolean canAssign(int row, int col, int num) {
        int boxRow = getBoxCoordinate(row);
        int boxCol = getBoxCoordinate(col);
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(boxRow, boxCol, num);
    }

    private int getBoxCoordinate(int coordinate) {
        return coordinate - (coordinate % 3);
    }

    private boolean isInRow(int row, int needle) {
        for (int num : grid[row]) {
            if (num == needle) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCol(int col, int needle) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (grid[row][col] == needle) {
                return true;
            }
        }
        return false;
    }

    // boxRow = y coordinate of top left tile of box
    // boxCol = x coordinate of top left tile of box
    private boolean isInBox(int boxRow, int boxCol, int needle) {
        for (int row = 0; row < BOX_SIZE; row++) {
            for (int col = 0; col < BOX_SIZE; col++) {
                if (grid[row + boxRow][col + boxCol] == needle) {
                    return true;
                }
            }
        }
        return false;
    }
}

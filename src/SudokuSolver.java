public class SudokuSolver {
    private final String SEPARATOR = " ";
    private final int GRID_SIZE = 9;
    private final int BOX_SIZE = 3;
    private final Pair FULL_GRID = new Pair(GRID_SIZE, GRID_SIZE);

    private final int[][] grid;
    private final boolean[][][] excluded;

    public SudokuSolver(int[][] grid) {
        this.grid = grid;
        excluded = initializeExcluded(grid);
    }

    // use brute force approach with backtracking
    public boolean bruteForce() {
        Pair nextLocation = getNextLocation();
        // if we're already at the end of board, the board is solved - exit
        if (nextLocation.getX() == GRID_SIZE && nextLocation.getY() == GRID_SIZE) {
            return true;
        }

        // get the coordinates of the next tile
        int row = nextLocation.getX();
        int col = nextLocation.getY();

        for (int num = 1; num < GRID_SIZE + 1; num++) {
            if (canAssign(row, col, num)) {
                // if the number can be assigned, do it
                grid[row][col] = num;

                // advance to next tile on board
                if (bruteForce()) {
                    return true;
                }

                // if last operation was unsuccessful, we have to backtrack and try again
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
        if (excluded[row][col][num]) {
            return false;
        }
        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(boxRow, boxCol, num);
    }

    private int getBoxCoordinate(int coordinate) {
        return coordinate - (coordinate % BOX_SIZE);
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

    private boolean[][][] initializeExcluded(int[][] grid) {
        // pre-compute a grid of excluded values
        boolean[][][] excluded = new boolean[GRID_SIZE][GRID_SIZE][GRID_SIZE + 1];

        // for every tile
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int num = grid[row][col];
                int boxRow = getBoxCoordinate(row);
                int boxCol = getBoxCoordinate(col);
                // if that tile is occupied, mark the number in the corresponding column, row and box as excluded
                if (num != 0) {
                    for (int c = 0; c < GRID_SIZE; c++) {
                        excluded[row][c][num] = true;
                    }
                    for (int r = 0; r < GRID_SIZE; r++) {
                        excluded[r][col][num] = true;
                    }
                    for (int r = 0; r < BOX_SIZE; r++) {
                        for (int c = 0; c < BOX_SIZE; c++) {
                            excluded[r + boxRow][c + boxCol][num] = true;
                        }
                    }
                }
            }
        }
        return excluded;
    }
}

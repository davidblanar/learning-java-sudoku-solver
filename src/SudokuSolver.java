public class SudokuSolver {
    private final String SEPARATOR = " ";
    private final int GRID_SIZE = 9;
    private final int BOX_SIZE = 3;
//    private final int GRID_SIZE = 3;
//    private final int BOX_SIZE = 0;
//    private final int GRID_SIZE = 4;
//    private final int BOX_SIZE = 2;
    private final Pair FULL_GRID = new Pair(GRID_SIZE, GRID_SIZE);

    private final int[][] grid;
    private Excluded excluded;

    public SudokuSolver(int[][] grid) {
        this.grid = grid;
        excluded = new Excluded(grid);
    }

    public boolean bruteForce() {
        Pair nextLocation = getNextLocation();
        if (nextLocation.getX() == GRID_SIZE && nextLocation.getY() == GRID_SIZE) {
            return true;
        }

        int row = nextLocation.getX();
        int col = nextLocation.getY();

        for (int num = 1; num < GRID_SIZE + 1; num++) {
            if (canAssign(row, col, num)) {
                grid[row][col] = num;
                excluded.setExcluded(row, col, num, true);

                if (bruteForce()) {
                    return true;
                }

                grid[row][col] = 0;
                excluded.setExcluded(row, col, num, false);
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
        return !excluded.isExcluded(row, col, num);
//        return !excluded.isExcluded(row, col, num) && !isInBox(boxRow, boxCol, num);
//        return !isInRow(row, num) && !isInCol(col, num) && !isInBox(boxRow, boxCol, num);
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

    private class Excluded {
        private final boolean[][][] excluded;

        public Excluded(int[][] grid) {
            excluded = initializeExcluded(grid);
        }

        public boolean isExcluded(int row, int col, int num) {
            return excluded[row][col][num];
        }

        public void setExcluded(int row, int col, int num, boolean isExcluded) {
            for (int c = 0; c < GRID_SIZE; c++) {
                excluded[row][c][num] = isExcluded;
            }
            for (int r = 0; r < GRID_SIZE; r++) {
                excluded[r][col][num] = isExcluded;
            }
            int boxRow = getBoxCoordinate(row);
            int boxCol = getBoxCoordinate(col);
            for (int r = 0; r < BOX_SIZE; r++) {
                for (int c = 0; c < BOX_SIZE; c++) {
                    excluded[r + boxRow][c + boxCol][num] = isExcluded;
                }
            }
        }

        private boolean[][][] initializeExcluded(int[][] grid) {
            boolean[][][] excluded = new boolean[GRID_SIZE][GRID_SIZE][GRID_SIZE + 1];

            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    int num = grid[row][col];
                    int boxRow = getBoxCoordinate(row);
                    int boxCol = getBoxCoordinate(col);
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
}

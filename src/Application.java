public class Application {
    // TODO add optimized solving
    public static void main(String[] args) {
        int[][] grid1 = {
                {5,3,0,0,7,0,0,0,0},
                {6,0,0,1,9,5,0,0,0},
                {0,9,8,0,0,0,0,6,0},
                {8,0,0,0,6,0,0,0,3},
                {4,0,0,8,0,3,0,0,1},
                {7,0,0,0,2,0,0,0,6},
                {0,6,0,0,0,0,2,8,0},
                {0,0,0,4,1,9,0,0,5},
                {0,0,0,0,8,0,0,7,9}
        };
        int[][] grid2 = {
                {0,0,0,0,0,0,6,8,0},
                {0,0,0,0,7,3,0,0,9},
                {3,0,9,0,0,0,0,4,5},
                {4,9,0,0,0,0,0,0,0},
                {8,0,3,0,5,0,9,0,2},
                {0,0,0,0,0,0,0,3,6},
                {9,6,0,0,0,0,3,0,8},
                {7,0,0,6,8,0,0,0,0},
                {0,2,8,0,8,0,0,0,0}
        };
        // grid designed to work against the brute-force algorithm
        int[][] grid3 = {
                {0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,3,0,8,5},
                {0,0,1,0,2,0,0,0,0},
                {0,0,0,5,0,7,0,0,0},
                {0,0,4,0,0,0,1,0,0},
                {0,9,0,0,0,0,0,0,0},
                {5,0,0,0,0,0,0,7,3},
                {0,0,2,0,1,0,0,0,0},
                {0,0,0,0,4,0,0,0,9}
        };
        int[][] grid4 = {
                {8,0,0,0,0,0,0,0,0},
                {0,0,3,6,0,0,0,0,0},
                {0,7,0,0,9,0,2,0,0},
                {0,5,0,0,0,7,0,0,0},
                {0,0,0,0,4,5,7,0,0},
                {0,0,0,1,0,0,0,3,0},
                {0,0,1,0,0,0,0,6,8},
                {0,0,8,5,0,0,0,1,0},
                {0,9,0,0,0,0,4,0,0}
        };
        int[][] easyGrid = {
                {1,0,0},
                {0,0,0},
                {0,0,3}
        };
        int[][] easyGrid2 = {
                {0,0,0,0},
                {1,0,0,4},
                {0,2,0,0},
                {0,0,3,0}
        };
        long startTime = System.currentTimeMillis();

        SudokuSolver sudokuSolver = new SudokuSolver(grid1);
        System.out.println("Solving sudoku...");
        sudokuSolver.printGrid();
        if (sudokuSolver.bruteForce()) {
            System.out.println("Solution found");
            sudokuSolver.printGrid();
        } else {
            System.out.println("No solution found");
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("Took: %d ms", endTime - startTime);
    }
}

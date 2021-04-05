public class GameOfLife {

    /**
     * cell state: true => alive, false => dead
     */
    private final boolean[][] grid;

    public GameOfLife(final int size) {
        grid = new boolean[size][size];
    }

    /**
     * computes new generation of cells
     */
    public void update() {
        boolean[][] grid_copy = new boolean[grid.length][grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) grid_copy[i][j] = grid[i][j];
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                boolean cell_state = grid_copy[i][j];
                int num_alive_neighbouring_cells = 0;
                // iterate through neighbouring cells to determine the number of alive cells
                for (int tmp_i = i - 1; tmp_i <= i + 1; tmp_i++) {
                    for (int tmp_j = j - 1; tmp_j <= j + 1; tmp_j++) {
                        if (is_valid_position(tmp_i, tmp_j) && grid_copy[tmp_i][tmp_j] && (tmp_i != i || tmp_j != j)) num_alive_neighbouring_cells++;
                    }
                }
                // use the given rules to determine next state of current cell
                if (cell_state) grid[i][j] = num_alive_neighbouring_cells == 2 || num_alive_neighbouring_cells == 3;
                else grid[i][j] = num_alive_neighbouring_cells == 3;
            }
        }
    }

    /**
     * creates a grid with a random distribution of alive cells
     */
    public void random(double alive_probability) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = Math.random() <= alive_probability;
            }
        }
    }

    /**
     * sets all cells to state: dead
     */
    public void clear() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) grid[i][j] = false;
        }
    }

    private boolean is_valid_position(int row, int col) {
        return (row >= 0 && col >= 0 && row < grid.length && col < grid.length);
    }

    public int size() {
        return grid.length;
    }

    public boolean getCellStateAt(int row, int col) {
        if (!is_valid_position(row, col)) return false;
        else return grid[row][col];
    }

    public void changeCellStateAt(int row, int col) {
        if (is_valid_position(row, col)) grid[row][col] = !grid[row][col];
    }
}

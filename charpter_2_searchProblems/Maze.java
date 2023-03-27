package classic_cs_problems_in_Java.charpter_2_searchProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classic_cs_problems_in_Java.charpter_2_searchProblems.GenericSearch.Node;

public class Maze {
    public enum Cell {
        EMPTY(" "), BLOCKED("X"), START("S"), GOAL("G"), PATH("*");

        private final String code;

        private Cell(String c) {
            code = c;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static class MazeLocation {
        public final int row;
        public final int column;

        public MazeLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        // auto-generate by IDE
        // they will ensure two instances of MazeLocation with the same row and column
        // are seen as equivalent to one another.
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + column;
            result = prime * result + row;
            return result;
        }

        // auto-generate by IDE
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MazeLocation other = (MazeLocation) obj;
            if (column != other.column)
                return false;
            if (row != other.row)
                return false;
            return true;
        }
    }

    private final int rows, columns;

    // instance variables for the number of rows,
    // number of columns, start location and end location
    private final MazeLocation start, goal;

    // keep track of a grid (a two-dimensional array) representing its state
    private Cell[][] grid;

    // it's grid will be randomly fillled with blocked cells
    public Maze(int rows, int columns, MazeLocation start, MazeLocation goal, double spareseness) {
        // initialize basic instance variables
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.goal = goal;
        // fill the grid with empty cells
        grid = new Cell[rows][columns];
        for (Cell[] row : grid) {
            Arrays.fill(row, Cell.EMPTY);
        }

        // populate the grid with blocked cells
        randomlyFill(spareseness);

        // fill the start and goal locations
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public Maze() {
        this(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
    }

    private void randomlyFill(double spareseness) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (Math.random() < spareseness) {
                    grid[row][column] = Cell.BLOCKED;
                }
            }
        }
    }

    // return a nicely formatted version of the maze for printing
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Maze m = new Maze();
        System.out.println(m);

        Node<MazeLocation> solution1 = GenericSearch.dfs(m.start, m::goalTest, m::successors);
        if (solution1 == null) {
            System.out.println("No solution found using DFS search!");
        } else {
            List<MazeLocation> path1 = GenericSearch.nodeToPath(solution1);
            m.mark(path1);
            System.out.println(m);
            m.clear(path1);
        }

        Node<MazeLocation> solution2 = GenericSearch.bfs(m.start, m::goalTest, m::successors);
        if (solution2 == null) {
            System.out.println("No solution found using BFS search!");
        } else {
            List<MazeLocation> path2 = GenericSearch.nodeToPath(solution2);
            m.mark(path2);
            System.out.println(m);
            m.clear(path2);
        }

        Node<MazeLocation> solution3 = GenericSearch.astar(m.start, m::goalTest, m::successors, m::manhattanDistance);
        if (solution3 == null) {
            System.out.println("No solution found using A*!");
        } else {
            List<MazeLocation> path3 = GenericSearch.nodeToPath(solution3);
            m.mark(path3);
            System.out.print(m);
            m.clear(path3);
        }
    }

    public boolean goalTest(MazeLocation ml) {
        return goal.equals(ml);
    }

    public List<MazeLocation> successors(MazeLocation m1) {
        // put every possible MazeLocation that it finds into a list 
        // that is ultimately returns to the caller
        List<MazeLocation> locations = new ArrayList<>();
        if (m1.row + 1 < rows && grid[m1.row + 1][m1.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(m1.row + 1, m1.column));
        }

        // avoid checking locations beyond the edges of the Maze
        if (m1.row - 1 >= 0 && grid[m1.row - 1][m1.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(m1.row - 1, m1.column));
        } 

        if (m1.column + 1 < columns && grid[m1.row][m1.column+1] != Cell.BLOCKED){
            locations.add(new MazeLocation(m1.row, m1.column+1));
        }

        if (m1.column - 1 >= 0 && grid[m1.row][m1.column-1] != Cell.BLOCKED){
            locations.add(new MazeLocation(m1.row, m1.column-1));
        }
        return locations;
    }

    public void mark (List<MazeLocation> path) {
        for (MazeLocation m1 : path) {
            grid[m1.row][m1.column] = Cell.PATH;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public void clear(List<MazeLocation> path) {
        for (MazeLocation m1 : path) {
            grid[m1.row][m1.column] = Cell.EMPTY;
        }
        grid[start.row][start.column] = Cell.START;
        grid[goal.row][goal.column] = Cell.GOAL;
    }

    public double euclideanDistance(MazeLocation m1) {
        // this function "knows" the goal, because it is actually a method on Maze, 
        // and Maze has goal as an instance variable
        int xdist = m1.column - goal.column;
        int ydist = m1.row - goal.row;
        return Math.sqrt((xdist * xdist) + (ydist * ydist));
    }

    // finding the difference in rows between two maze locations and 
    // summing it with the difference in columns
    public double manhattanDistance(MazeLocation m1) {
        int xdist = Math.abs(m1.column - goal.column);
        int ydist = Math.abs(m1.row - goal.row);
        return (xdist + ydist);
    }


}
package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.*;

public class WordGrid {
    private int columns, rows;
    private char[][] grid;
    final char FIRST_LETTER = 'A';
    final char ALPHABET_LENGTH = 26;

    public static class GridLocation {
        public final int column, row;

        public GridLocation(int row, int column) {
            this.column = column;
            this.row = row;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + column;
            result = prime * result + row;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            GridLocation other = (GridLocation) obj;
            if (column != other.column)
                return false;
            if (row != other.row)
                return false;
            return true;
        }
    }

    public WordGrid(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        this.grid = new char[rows][columns];

        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = (char) (random.nextInt(ALPHABET_LENGTH) + FIRST_LETTER);
            }
        }
    }

    public void mark(String word, List<GridLocation> locations) {
        for (int i = 0; i < word.length(); i++) {
            grid[locations.get(i).row][locations.get(i).column] = word.charAt(i);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] item : grid )
        {
            sb.append(item);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
    
    public List<List<GridLocation>> generateDomain(String word) {
        List<List<GridLocation>> domain =  new ArrayList<>();
        int word_len = word.length();

        for(int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                if (j + word_len <= columns){
                    fillRight(domain, i, j, word_len);
                    if (i + word_len <= rows) {
                        fillDiagonalRight(domain, i, j, word_len);
                    }
                }
                if (i + word_len <= rows) {
                    fillDown(domain, i, j, word_len);
                    if (j - word_len >= 0) {
                        fillDiagonalLeft(domain, i, j, word_len);
                    }
                }
            }
        }

        return domain;
    }

    private void fillRight(List<List<GridLocation>> domain, int row, int column, int word_len){
        List<GridLocation> locations = new ArrayList<>();

        for (int j = column; j < column + word_len; j++){
            locations.add(new GridLocation(row, j));
        }
        domain.add(locations);
    }

    private void fillDown(List<List<GridLocation>> domain, int row, int column, int word_len){
        List<GridLocation> locations = new ArrayList<>();
        for (int i = row; i < row + word_len; i++){
            locations.add(new GridLocation(i, column));
        }
        domain.add(locations);
    }
    private void fillDiagonalRight(List<List<GridLocation>> domain, int row, int column, int word_len){
        List<GridLocation> locations = new ArrayList<>();
        int j = column;
        for (int i = row; i < row + word_len; i++){
            locations.add(new GridLocation(i, j));
            j++;
        }
        domain.add(locations);
    }
    private void fillDiagonalLeft(List<List<GridLocation>> domain, int row, int column, int word_len){
        List<GridLocation> locations = new ArrayList<>();
        int j = column;
        for (int i = row; i < row + word_len; i++){
            locations.add(new GridLocation(i, j));
            j--;
        }
        domain.add(locations);
    }


}
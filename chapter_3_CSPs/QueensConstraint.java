package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class QueensConstraint extends Constraint<Integer, Integer>{
    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {
        for (Entry<Integer, Integer> item: assignment.entrySet()) {
            // q1c = queen 1 column, q1r = queen 1 row
            int q1c = item.getKey();
            int q1r = item.getValue();
            // q2c = queen 2 column
            for (int q2c = q1c+1; q2c <= columns.size(); q2c++){
                if (assignment.containsKey(q2c)) {
                    // q2r = queen 2 row
                    int q2r = assignment.get(q2c);
                    // same row?
                    if (q1r == q2r) {
                        return false;
                    }
                    // same diagonal?
                    if (Math.abs(q1r - q2r) ==  Math.abs(q1c - q2c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private List<Integer> columns;
    public QueensConstraint(List<Integer> columns) {
        super(columns);
        this.columns = columns;
    }
    
    public static void main(String[] args) {
        List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        Map<Integer, List<Integer>> rows = new HashMap<>();
        for (int column: columns) {
            rows.put(column, List.of(1, 2, 3, 4, 5, 6, 7, 8));
        }
        CSP<Integer, Integer> csp = new CSP<>(columns, rows);
        csp.addConstraint(new QueensConstraint(columns));
        Map<Integer, Integer> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No Solution found!");
        } else {
            System.out.print(solution);
        }
    }

    
}
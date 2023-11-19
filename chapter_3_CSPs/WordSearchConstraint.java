package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import classic_cs_problems_in_Java.chapter_3_CSPs.WordGrid.GridLocation;

public class WordSearchConstraint extends Constraint<String, List<GridLocation>>{

    public static void main(String[] args) {
        WordGrid grid = new WordGrid(9, 9);
        List<String> words = List.of("MATTHEW", "JOE", "SARAH", "MARY", "SALLY");
        Map<String, List<List<GridLocation>>> domains = new HashMap<>();

        for (String item: words) {
            domains.put(item, grid.generateDomain(item));
        }
        CSP<String, List<GridLocation>> csp = new CSP<>(words, domains);

        csp.addConstraint(new WordSearchConstraint(words));

        Map<String, List<GridLocation>> solution =  csp.backtrackingSearch();
        if (solution == null) {
            System.out.print("No solution found");
        } else {
            Random random = new Random();
            for (Entry<String, List<GridLocation>> entry: solution.entrySet() ){
                // System.out.println(entry);
                String word = entry.getKey();
                List<GridLocation> locations = entry.getValue();
                for( GridLocation grid1 : locations)
                {
                    System.out.println(word + " " + grid1.row + " " + grid1.column);
                }
                if (random.nextBoolean()) {
                    Collections.reverse(locations);
                }
                grid.mark(word.toLowerCase(), locations);
            }
            System.out.println(grid);
        }

    }
    public WordSearchConstraint(List<String> variables) {
        super(variables);
    }

    @Override
    public boolean satisfied(Map<String, List<GridLocation>> assignment) {
        List<GridLocation> allLocation = assignment.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        
        Set<GridLocation> removeDupLocation = new HashSet<>(allLocation);
        return removeDupLocation.size() == allLocation.size();
    }

    
}
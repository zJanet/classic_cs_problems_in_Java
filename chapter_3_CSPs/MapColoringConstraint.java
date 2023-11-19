package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MapColoringConstraint extends Constraint<String, String> {
    private String place1, place2;

    public MapColoringConstraint(String place1, String place2) {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        // if either place is not in the assignment, then it is not yet possible for their colors to be conflicting
        if (!assignment.containsKey(place1) || !assignment.containsKey(place2)) {
            return true;
        }
        // check the color assigned to place1 is not the same as the color assigned to place2
        return !assignment.get(place1).equals(assignment.get(place2));
    }

    public static void main(String[] args) {
        List<String> variables = List.of("Western Australia", "Northern Territory", "South Australia", "Queensland", "New South Wales", "Victoria", "Tasmania");
        Map<String, List<String>> domains = new HashMap<>();
        for (String variable : variables) {
            domains.put(variable, List.of("red", "green", "blue"));
        }
        CSP<String, String> csp = new CSP<>(variables, domains);
        csp.addConstraint(new MapColoringConstraint("Western Australia", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Western Australia", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("South Australia","Northern Territory"));

        csp.addConstraint(new MapColoringConstraint("Queensland","Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Queensland","South Australia"));
        csp.addConstraint(new MapColoringConstraint("Queensland","New South Wales"));
        csp.addConstraint(new MapColoringConstraint("New South Wales","South Australia"));
        csp.addConstraint(new MapColoringConstraint("Victoria","South Australia"));
        csp.addConstraint(new MapColoringConstraint("Victoria","New South Wales"));
        csp.addConstraint(new MapColoringConstraint("South Australia","Tasmania"));


        Map<String, String> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No Solutioin found!");
        } else {
            System.out.println(solution);
        }
    }

   
    
}
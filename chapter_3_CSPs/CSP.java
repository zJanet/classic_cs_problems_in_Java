package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSP<V, D> {
    private List<V> variables;
    private Map<V, List<D>> domains; // a Map mapping variables to lists of possible values (the domains of those variables)
    private Map<V, List<Constraint<V,D>>> constraints = new HashMap<>(); //  a Map that maps each variable to a List of the constraints imposed on it.
    
    // the constructor creates the constraints Map
    public CSP(List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;
        for (V variable : variables) {
            constraints.put(variable, new ArrayList<>());
            if (!domains.containsKey(variable)) {
                // error-checking: if a variable is missing a domain
                throw new IllegalArgumentException("Every variable should have a domain assigned to it.");
            }
        }
    }

    // goes through all of the variables touched by a given constraint
    // and adds itself to the constraints mapping for each of them
    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable : constraint.variables) {
            if (!variables.contains(variable)) {
                // error-checking: if a constraint is on a nonexistent variable 
                throw new IllegalArgumentException("Variable in constraint not in CSP");
            }
            constraints.get(variable).add(constraint);
        }
     }

    //  an assignment is a specific domain value selected for each variable
    // check if the value assignment is consistent by checking all
    // constraints for the given variable against it

    // consistent() goes through every constraint for a given variable and checks if the 
    // constraint is satisfied, given the new assignment.
    // if the assignment satisfies every constraint, true is returned.
    // if any constraint imposed on the variable is not satisfied, false is returned
    public boolean consistent(V variable, Map<V, D> assignment) {
        for (Constraint<V,D> constraint : constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }
    public Map<V, D> backtrackingSearch(Map<V,D> assignment) {
        // assignment is complete if every variable is assigned
     5
        // get the first variable in the CSP but not in the assignment
        V unassigned = variables.stream().filter(v -> 
            !assignment.containsKey(v)).findFirst().get();
            // look through every domain value of the first unassignmend variable
            for (D value : domains.get(unassigned)) {
                // shallow copy of assignment that we can change
                Map<V,D> localAssignment = new HashMap<>(assignment);
                localAssignment.put(unassigned, value);
                // if we're still consistent, we recurse (continue)
                if (consistent(unassigned, localAssignment)) {
                    Map<V,D> result = backtrackingSearch(localAssignment);
                    // if we didn't find the result, we end up backtracking
                    if (result != null) {
                        return result;
                    }
                }
            }
            return null;
    }
    // helper for backtrackingSearch when nothing know yet
    public Map<V,D> backtrackingSearch() {
        return backtrackingSearch(new HashMap<>());
    }
}
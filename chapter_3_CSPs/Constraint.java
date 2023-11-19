package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.List;
import java.util.Map;

// V is the variable type, and D is the domain type
public abstract class Constraint<V, D> {
    // the variables that constraint is bewtween
    protected List<V> variables;

    public Constraint(List<V> variables) {
        this.variables = variables;
    }

    // must be overridden by subclasses
    public abstract boolean satisfied(Map<V, D> assignment);
}
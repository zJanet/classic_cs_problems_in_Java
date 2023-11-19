package classic_cs_problems_in_Java.chapter_3_CSPs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SendMoreMoneyConstraint extends Constraint<Character, Integer> {
    private List<Character> letters;

    public SendMoreMoneyConstraint(List<Character> variables) {
        super(variables);
        this.letters = variables;
    }

    public static void main(String[] args) {
        List<Character> letters = List.of('S','E', 'N','D','M','O','R','Y');
        Map<Character,List<Integer>> possibleValue = new HashMap<>();
        for (Character c : letters) {
            possibleValue.put(c, List.of(0,1,2,3,4,5,6,7,8,9));
        }
        possibleValue.replace('M', List.of(1));
        CSP<Character,Integer> csp = new CSP<>(letters, possibleValue);
        csp.addConstraint(new SendMoreMoneyConstraint(letters));
        Map<Character,Integer> solution = csp.backtrackingSearch();
        if (solution == null){
            System.out.println("no solution found");
        }else {
            System.out.println(solution.toString());
        }
    }


    @Override
    public boolean satisfied(Map<Character, Integer> assignment) {
        // if duplicate
        HashSet<Integer> hashSet = new HashSet<>(assignment.values());
        if (hashSet.size() < assignment.values().size()){
         
            return false;
        }

        // if fully assigned
        if (letters .size() == assignment.values().size()){
            int s = assignment.get('S');
            int e = assignment.get('E');
            int n = assignment.get('N');
            int d = assignment.get('D');
            int m = assignment.get('M');
            int o = assignment.get('O');
            int r = assignment.get('R');
            int y = assignment.get('Y');

            int send = s * 1000 + e *100 + n * 10 + d;
            int more = m * 1000 + o * 100 + r * 10 + e;

            int money = m * 10000 + o *1000 + n *100 + e*10 + y;

            return money == send + more;
        }
        return true;
    }

}
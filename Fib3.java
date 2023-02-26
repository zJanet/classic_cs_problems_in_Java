package classic_cs_problems_in_Java;

import java.util.HashMap;
import java.util.Map;

public class Fib3 {
    // Map.of() was introduced in Java 9 but returns
    // an immutable Map
    // This creates a map with 0->0 and 1->1
    // which represent our base cases
    public static void main(String[] args) {
        System.out.println(fib3(5));
        System.out.println(fib3(40));
    }
    static Map<Integer, Integer> memo = new HashMap<>(Map.of(0, 0, 1, 1));

    private static int fib3(int n) {
        if (!memo.containsKey(n)){
            // memorization step
            memo.put(n, fib3(n-1)+fib3(n-2));
        }
        return memo.get(n);
    }

}
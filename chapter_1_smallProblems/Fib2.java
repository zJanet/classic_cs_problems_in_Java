package classic_cs_problems_in_Java.chapter_1_smallProblems;

public class Fib2 {
    static int count = 0;
    public static void main(String[] args) {
        // System.out.println(fib2(1));
        // System.out.println("fib2(1)"+"count: " +count);
        // System.out.println(fib2(2));
        // System.out.println("fib2(2)"+"count: " +count);
        // System.out.println(fib2(3));
        // System.out.println("fib2(3)"+"count: " +count);
        // System.out.println(fib2(4));
        // System.out.println("fib2(4)" +"count: " +count);
        // System.out.println(fib2(5));
        // System.out.println("fib2(5)" +"count: " +count);
        // System.out.println(fib2(6));
        // System.out.println("count: " +count);
        // System.out.println(fib2(7));
        // System.out.println("count: " +count);
        // System.out.println(fib2(8));
        // System.out.println("count: " +count);
        // System.out.println(fib2(9));
        // System.out.println("count: " +count);
        // System.out.println(fib2(10));
        // System.out.println("fib2(10)"+"count: " +count);
        System.out.println(fib2(20));
        System.out.println("count: " +count);
    }
    private static int fib2(int n){
        count++;
        if (n < 2) return n; // stoping point or base case
        return fib2(n-1) + fib2(n-2);
    }
    
}
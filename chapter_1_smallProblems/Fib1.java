package classic_cs_problems_in_Java.chapter_1_smallProblems;

public class Fib1 {
    public static void main(String[] args) {
        System.out.println(fib1(5));
    }
    // 
    private static int fib1(int n){
        return fib1(n-1) + fib1(n-2);
        //infinite recursion/loop
    }
}
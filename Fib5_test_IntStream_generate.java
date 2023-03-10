package classic_cs_problems_in_Java;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Fib5_test_IntStream_generate {
            // where each element is generated by the provideded IntSupplier
        // Java8提供了Stream.iterate()和Stream.generate()来生成无限流，这两个方法会根据给定的表达式来生成包含无限个数据的流，所以一般结合limit()来使用。
        // reference: https://developer.aliyun.com/article/949637
    public static void main(String[] args) {
            // take a function, wihout input parameter, but has return 
        // Random random = new Random();
        // Stream.generate(()-> random.nextInt(100))
        // .limit(10)
        // .forEach(System.out::println);   
            
        // take a function, with input parameter, and return 
        Stream.iterate(10, x -> x+5)
                .limit(10)
                .forEach(System.out::println); 
    }

}
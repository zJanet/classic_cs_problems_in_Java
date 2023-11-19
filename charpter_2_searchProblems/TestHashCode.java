package classic_cs_problems_in_Java.charpter_2_searchProblems;

public class TestHashCode {
    public static void main(String[] args) {
        TestHashCode t1 = new TestHashCode();
        System.out.println(t1.hashCode());
        TestHashCode t2 = t1;
        System.out.println(t2.hashCode());
        System.out.println(t2 == t1);

    }
    
}
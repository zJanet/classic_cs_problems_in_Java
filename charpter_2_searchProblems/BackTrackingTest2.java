package classic_cs_problems_in_Java.charpter_2_searchProblems;

import java.lang.System.Logger.Level;

public class BackTrackingTest2 {
    public static void main(String[] args) {
        recusive(1);
    }
    
    public static void recusive(int level) {
        if(level > 3){
            for(int i=0;i < level;i++)
                System.out.print('\t');
            System.out.println("stop at level: " +level);
            return;
        } 
            for(int i=0;i < level;i++)
                System.out.print('\t');
            System.out.println(" >>0 level:"+level);
            recusive(level + 1);
            for(int i=0;i < level;i++)
                System.out.print('\t');
            System.out.println("<<0 level:"+level);

            for(int i=0;i < level;i++)
                System.out.print('\t');
            System.out.println(">>1 level:"+level);
            recusive(level + 1);
            for(int i=0;i < level;i++)
                System.out.print('\t');
            System.out.println("<<1 level:"+level);
        }
}
package classic_cs_problems_in_Java.charpter_2_searchProblems;

import java.lang.System.Logger.Level;

public class BackTrackingTest1 {
    public static void main(String[] args) {
        recusive(1);
    }
    
    public static void recusive(int level) {
        if(level > 3){
            for (int l =0; l <level; l++){
                System.out.print("\t");
            }
            System.out.println("stop at level: " +level);
            return;
        } 
            for (int l =0; l <level; l++){
                System.out.print("\t");
            }
            System.out.println(">> level:"+level);

            recusive(level + 1);

            for (int l =0; l <level; l++){
                System.out.print("\t");
            }
            System.out.println("<< level:"+level);
        }
}
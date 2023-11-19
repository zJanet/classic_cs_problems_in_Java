package classic_cs_problems_in_Java.charpter_2_searchProblems;

public class TestEnum {
    enum Mode{
        LOVE("blind"),
        TIRED("Cheer up"),
        SAD("I am sad");

        private final String modeDetails;
        Mode(String code) {
            modeDetails = code;
        }
        
        @Override
        public String toString(){
            return modeDetails;
        }
    }
    public static void main(String[] args) {
        System.out.println(Mode.SAD);
    }
    
}
package classic_cs_problems_in_Java.chapter_1_smallProblems;

// an intermediary form to store the key pair, 
// which consit of two arrays of byte
public class KeyPair {
    public final byte[] key1;
    public final byte[] key2;

    KeyPair(byte[] key1, byte[] key2) {
        this.key1 = key1;
        this.key2 = key2;
    }
}
package classic_cs_problems_in_Java.chapter_1_smallProblems;

import java.util.Random;

public class UnbreakableEncryption {
    public static void main(String[] args) {
        KeyPair kp = encrypt("U");
        String result = decrypt(kp);
        System.out.println(result);
    }
    // Generate *length* random bytes
    // generate a random key for use as dummy data
    private static byte[] randomKey(int length){
        byte[] dummy = new byte[length];
        Random random = new Random();
        random.nextBytes(dummy);
        return dummy;
    }

    public static KeyPair encrypt (String original) {
        byte[] originalBytes = original.getBytes();
        for (int i = 0; i < originalBytes.length; i++) {
            System.out.println( originalBytes[i]);
        }
        byte[] dummyKey = randomKey(original.length());
        byte[] encryptedKey = new byte[originalBytes.length];
        for (int i = 0; i < originalBytes.length; i++) {
            // XOR every byte
            encryptedKey[i] = (byte) (originalBytes[i] ^ dummyKey[i]);
        }
        return new KeyPair(dummyKey, encryptedKey);
    }

    public static String decrypt(KeyPair kp) {
        byte[] decrypted = new byte[kp.key1.length];
        for (int i = 0; i < kp.key1.length; i++) {
            // XOR every byte
            decrypted[i] = (byte)(kp.key1[i] ^ kp.key2[i]);
        }
        return new String(decrypted);
    }
}
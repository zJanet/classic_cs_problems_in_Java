package classic_cs_problems_in_Java.chapter_1_smallProblems;

import java.util.BitSet;
// The following code converts a String composed of As, Cs, Gs, and 7s into a string of bits and back again.
// The string of bits is stored within a BitSet via the compress() method.
public class CompressedGene {
    private BitSet bitSet;
    private int length;

    public CompressedGene(String gene) {
        compress(gene);
    }
    public static void main(String[] args) {
        final String original = "TAG";
        CompressedGene compressed = new CompressedGene(original);
        // System.out.println(compressed.bitSet);
        final String decompressed = compressed.decompresss();
        System.out.println(decompressed);
        System.out.print("Original is the same as decompressed: " + original.equalsIgnoreCase(decompressed));
    }

    private void compress(String gene){
        length = gene.length();
        // reserve enough capacity for all of the bits
        bitSet = new BitSet(length * 2);
        // convert to upper case for consistency
        final String upperGene = gene.toUpperCase();
        // convert String to bit representation
        for (int i = 0; i < length; i++) {
            final int firstLocation = 2 * i;
            final int secondLocation = 2 * i + 1;
            switch (upperGene.charAt(i)){
                case 'A': // 00 are next two bits
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, false);
                    break;
                case 'C': // 01 are next two bits
                    bitSet.set(firstLocation, false);
                    bitSet.set(secondLocation, true);
                    break;
                case 'G': // 10 are next two bits
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, false);
                    break;
                case 'T': // 11 are next two bits
                // For the BitSet class, the Boolean values true and false serve as 
                // markers for 1 and 0, respectively
                    bitSet.set(firstLocation, true);
                    bitSet.set(secondLocation, true);
                    break; 
                default:
                    throw new IllegalArgumentException("The provided gene String contains characters other than ACGT");
            }
        }
    }

    public String decompresss(){
        if (bitSet == null) {
            return "";
        }
        // create a mutable place for characters with the right capacity
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < (length * 2); i += 2){
            final int firstBit = (bitSet.get(i)) ? 1 : 0;
            final int secondBit = (bitSet.get(i + 1)) ? 1: 0;
            final int lastBit = firstBit << 1 | secondBit;
            switch (lastBit) {
                case 0b00: // 00 is 'A'
                    builder.append('A');
                    break;
                case 0b01:  // 01 is 'C'
                    builder.append('C');
                    break;
                case 0b10: // 10 is 'G'
                    builder.append('G');
                    break;
                case 0b11: // 11 is 'T'
                    builder.append('T');
                    break;
            }
        }
        return builder.toString();
    }
}
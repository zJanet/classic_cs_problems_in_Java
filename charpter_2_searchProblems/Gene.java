package classic_cs_problems_in_Java.charpter_2_searchProblems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gene {
    private ArrayList<Codon> codons = new ArrayList<>();
    public Gene(String geneStr) {
        for (int i = 0; i < geneStr.length() - 3; i += 3) {
            // Take every 3 characters in the String and form a Codon
            codons.add(new Codon(geneStr.substring(i, i+3)));
        }
    }

    public enum Nucleotides {
        A, C, G, T;
    }

    // Codons can be defined as a comnination of threee Nucleotides.
    // The constructor for the Codon class converts a String of three letters into a Codon
    
    public static class Codon implements Comparable<Codon> {
        // To implement search methods, we will need to be able to compare one Codon to another.
            // Java has an interface for that, Comparable 
        // Implementing the Comparable interface requires the construction of one method, compareTo().
        // compareTo(): 
        public final Nucleotides first, second, third;
        private final Comparator<Codon> comparator = 
            Comparator.comparing((Codon c) -> c.first)
                    .thenComparing((Codon c) -> c.second)
                    .thenComparing((Codon c) -> c.third);

        public Codon(String condonStr) {
            first = Nucleotides.valueOf(condonStr.substring(0,1));
            second =  Nucleotides.valueOf(condonStr.substring(1, 2));
            third = Nucleotides.valueOf(condonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon other) {
            return comparator.compare(this, other);
        }
    }

    public boolean linearContains(Codon key) {
        for (Codon codon : codons) {
            if (codon.compareTo(key) == 0) {
                return true; // found a match
            }
        }
        return false;
    }

    public boolean binaryContains(Codon key) {
        // https://www.geeksforgeeks.org/copy-elements-of-one-arraylist-to-another-arraylist-in-java/
        //  pass the first ArrayList in the second ArrayList’s constructor.
        //  By using this approach if we change one ArrayList element/value it will not affect the other one, 
        // so this is the approach where we actually created the duplicates. 
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        
        // binary search only works on sorted collections
        Collections.sort(sortedCodons);

        // we start by looking at a range that encompasses the entire list (gene)
        int low = 0;
        int high = sortedCodons.size() - 1;

        // keep searching as long as there is still a search space to search within
        // when low is greater than high, it means that there are no longer any slots to look at within the list
        while (low <= high) { 
            
            // we calculate the middle by using integer division 
            int middle = (low + high) / 2;
            int comparison = codons.get(middle).compareTo(key);
            if (comparison < 0) { // middle codon is less than key
                low = middle + 1; // modify the range that we will look at during the next iteration of the loop by moving the low to be one past in the current middle element 
            } else if (comparison > 0) { // middle codon is > key
                high = middle -1;
            } else {  // middle codon is equal to key
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String geneStr = "ACGTGGCTC";
        Gene myGene = new Gene(geneStr);
        Codon acg = new Codon("ACG");
        Codon tcc = new Codon("TGG");
        Codon att = new Codon("ATT");

        System.out.println(myGene.linearContains(acg));
        System.out.println(myGene.linearContains(tcc));
        System.out.println(myGene.linearContains(att));

        System.out.println(myGene.binaryContains(tcc));
        System.out.println(myGene.binaryContains(att));
    }
}
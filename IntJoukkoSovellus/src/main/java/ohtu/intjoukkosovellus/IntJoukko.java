
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] joukko;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenMaara;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti tulee olla vähintään 0");
        }
        if (kasvatuskoko < 1) {
            throw new IndexOutOfBoundsException("Kasvatuskoko tulee olla vähintään 1");
        }
        joukko = new int[kapasiteetti];
        for (int i = 0; i < joukko.length; i++) {
            joukko[i] = 0;
        }
        alkioidenMaara = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            joukko[alkioidenMaara] = luku;
            alkioidenMaara++;
            if (alkioidenMaara == joukko.length) {
                int[] uusi = new int[joukko.length + kasvatuskoko];
                kopioiTaulukko(joukko, uusi);
                joukko = uusi;
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        return etsi(luku) != -1;
    }
    
    public int etsi(int luku) {
        for (int i = 0; i < alkioidenMaara; i++) {
            if (luku == joukko[i]) {
                return i;
            }
        }
        
        return -1;
    }

    public boolean poista(int luku) {
        int kohta = etsi(luku);
        if (kohta != -1) {
            System.arraycopy(joukko, kohta+1, joukko, kohta, joukko.length - (1 + kohta));
            alkioidenMaara--;
            
            return true;
        } else {
            return false;
        }
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        System.arraycopy(vanha, 0, uusi, 0, vanha.length);
    }

    public int mahtavuus() {
        return alkioidenMaara;
    }


    @Override
    public String toString() {
        return "{" + String.join(", ", Arrays.stream(Arrays.copyOfRange(joukko, 0, alkioidenMaara)).mapToObj(String::valueOf).collect(Collectors.toList())) + "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenMaara];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = joukko[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        Stream.concat(Arrays.stream(a.toIntArray()).boxed(), Arrays.stream(b.toIntArray()).boxed()).forEach(joukko::lisaa);
        return joukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        Arrays.stream(a.toIntArray()).filter(b::kuuluu).forEach(joukko::lisaa);
        return joukko;
    }
    
    public static IntJoukko erotus (IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        Arrays.stream(a.toIntArray()).filter(luku -> !b.kuuluu(luku)).forEach(joukko::lisaa);
 
        return joukko;
    }
        
}
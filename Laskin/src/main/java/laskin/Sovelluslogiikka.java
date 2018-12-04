package laskin;

import java.util.ArrayDeque;

public class Sovelluslogiikka {
    private ArrayDeque<Integer> historia = new ArrayDeque<>();
    
    private int tulos;
 
    public void plus(int luku) {
        historia.push(tulos);
        tulos += luku;
    }
     
    public void miinus(int luku) {
        historia.push(tulos);
        tulos -= luku;
    }
 
    public void nollaa() {
        historia.push(tulos);
        tulos = 0;
    }
 
    public int tulos() {
        return tulos;
    }
    
    public void undo() {
        if (!historia.isEmpty()) {
            tulos = historia.pop();
        }
    }
}
package laskin;

import java.util.HashMap;
import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Tapahtumankuuntelija implements EventHandler {
    private TextField tuloskentta; 
    private TextField syotekentta; 
    
    private Map<Button, Komento> komennot = new HashMap<>();
    
    private Button nollaa;
    private Button undo;
    
    private Sovelluslogiikka sovellus;

    public Tapahtumankuuntelija(TextField tuloskentta, TextField syotekentta, Button plus, Button miinus, Button nollaa, Button undo) {
        this.tuloskentta = tuloskentta;
        this.syotekentta = syotekentta;
        this.nollaa = nollaa;
        this.undo = undo;
        komennot.put(plus, (tila) -> tila.plus(getInput()));
        komennot.put(miinus, (tila) -> tila.miinus(getInput()));
        komennot.put(nollaa, (tila) -> tila.nollaa());
        komennot.put(undo, (tila) -> tila.undo());
        this.sovellus = new Sovelluslogiikka();
    }
    
    @Override
    public void handle(Event event) {
        komennot.get(event.getTarget()).suorita(sovellus);
        
        int laskunTulos = sovellus.tulos();
        
        syotekentta.setText("");
        tuloskentta.setText("" + laskunTulos);
        
        if (laskunTulos == 0) {
            nollaa.disableProperty().set(true);
        } else {
            nollaa.disableProperty().set(false);
        }
        undo.disableProperty().set(false);
    }
    
    private int getInput() {
        try {
            return Integer.parseInt(syotekentta.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

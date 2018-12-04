/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.matcher;

import java.util.Arrays;
import statistics.Player;

/**
 *
 * @author jaakko
 */
public class Or implements Matcher {

    private Matcher[] matchers;

    public Or(Matcher... matchers) {
        this.matchers = matchers;
    }
    
    @Override
    public boolean matches(Player p) {
        return Arrays.stream(matchers).anyMatch(matcher -> matcher.matches(p));
    }
    
}

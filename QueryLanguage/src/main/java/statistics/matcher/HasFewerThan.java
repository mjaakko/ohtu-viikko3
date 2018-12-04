/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.matcher;

import statistics.Player;

/**
 *
 * @author jaakko
 */
public class HasFewerThan implements Matcher {
    private HasAtLeast atLeast;

    public HasFewerThan(int value, String category) {
        atLeast = new HasAtLeast(value, category);
    }

    @Override
    public boolean matches(Player p) {
        return !atLeast.matches(p);
    }
    
}

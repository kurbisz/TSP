package org.example.algorithm.taboo.tabooList;

import org.example.algorithm.taboo.Neighbourhoods.Moves.Move;

/**
 * Interfejs ogólny dla wszystkich list tabu
 * Zawiera metody, które są niezbędne do działania listy tabu
 *
 * Co myślisz o takim rozwiązaniu?
 * Te parametry, które dodałeś, przeniosłem do abstrakcyjnej klasy dziedziczącej po tej tak, że
 * w Twoim kodzie nie ma żadnych zmian
 */
public interface TabooList {

    void add(Move move);
    boolean contains(Move move);
    TabooList clone();

}

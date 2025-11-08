package StrategyOrdinamento;

import BaseProgetto.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StrategyTitolo implements StrategyIF{
    @Override
    public List<Film> ordina(List<Film> filmList) {
        List<Film> ordinati = new ArrayList<>(filmList);

        ordinati.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getTitolo().compareToIgnoreCase(f2.getTitolo());
            }
        });
        return ordinati;
    }

}

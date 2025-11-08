package StrategyOrdinamento;

import BaseProgetto.Film;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StrategyValutazione implements StrategyIF {
    @Override
    public List<Film> ordina(List<Film> filmList) {
        List<Film> ordinati = new ArrayList<>(filmList);

        ordinati.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                return f1.getValutazione().compareTo(f2.getValutazione());
            }
        });

        return ordinati;
    }
}
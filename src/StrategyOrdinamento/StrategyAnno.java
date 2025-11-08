package StrategyOrdinamento;

import BaseProgetto.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StrategyAnno implements StrategyIF{
    @Override
    public List<Film> ordina(List<Film> filmList) {
        List<Film> ordinati = new ArrayList<>(filmList);

        ordinati.sort(new Comparator<Film>() {
            @Override
            public int compare(Film f1, Film f2) {
                if (f1.getAnno()==null && f2.getAnno()==null)
                    return 0;
                if (f1.getAnno()==null) //f1 alla fine
                    return 1;
                if (f2.getAnno()==null) //f2 alla fine
                    return -1;
                return f1.getAnno().compareTo(f2.getAnno());
            }
        });
        return ordinati;
    }

}

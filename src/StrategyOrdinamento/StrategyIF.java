package StrategyOrdinamento;

import BaseProgetto.*;
import java.util.List;


public interface StrategyIF {
    // Ottiene una lista di film, la ordina e ne restituisce un'altra. Questo è il metodo che
    // tutte le classi dovranno rispettare: un contratto.
    List<Film> ordina(List<Film> filmList);
}

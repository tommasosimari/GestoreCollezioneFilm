package test;

import BaseProgetto.*;
import BaseProgetto.Enum.*;
import BaseProgetto.Singleton.CollezioneFilm;
import FactoryPersistenza.*;
import StrategyOrdinamento.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

//questa classe serve a testare la classe CollezioneFilm, i pattern Builder, Singleton e Strategy

class CollezioneFilmTest {

    private CollezioneFilm collezione;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        collezione = CollezioneFilm.getCollezione();
        collezione.svuota();

        Film nuovoFilm1 = new Film.Builder("Cado dalle nubi", "Gennaro Nuziante").setAnno(2009).
                setGenere(GenereCinematografico.COMMEDIA).setStato(Stato.DA_VEDERE).
                setValutazione(Valutazione.CINQUE_STELLE).build();
        collezione.aggiungiFilm(nuovoFilm1);

        Film nuovoFilm2 = new Film.Builder("La vita è bella", "Roberto Benigni").setAnno(1997).
                setGenere(GenereCinematografico.DRAMMATICO).setStato(Stato.VISTO).
                setValutazione(Valutazione.QUATTRO_STELLE).build();
        collezione.aggiungiFilm(nuovoFilm2);

        Film nuovoFilm3 = new Film.Builder("Titanic", "James Cameron").setAnno(1997).
                setGenere(GenereCinematografico.DRAMMATICO).setStato(Stato.IN_VISIONE).build();
        collezione.aggiungiFilm(nuovoFilm3);

        Film nuovoFilm4 = new Film.Builder("ABC", "CIAO").setGenere(GenereCinematografico.COMMEDIA).
                setStato(Stato.DA_VEDERE).build();
        collezione.aggiungiFilm(nuovoFilm4);

        System.out.println("Nuovo file");
    }

    @org.junit.jupiter.api.Test
    void testAggiungiFilm() {
        System.out.println("test aggiungi film");
        int dimensione_Collezione = collezione.getTuttiIFilm().size();

        Film nuovoFilm = new Film.Builder("Sono leggenda", "Una persona").
                setGenere(GenereCinematografico.ALTRO_O_IBRIDI).setValutazione(Valutazione.UNA_STELLA).build();
        collezione.aggiungiFilm(nuovoFilm);

        int nuova_dimensione = collezione.getTuttiIFilm().size();

        assertEquals(dimensione_Collezione + 1, nuova_dimensione);
        collezione.getTuttiIFilm();
    }

    @org.junit.jupiter.api.Test
    void testAggiungiFilm1() {
        System.out.println("test2 aggiungi film");
        int dimensione_Collezione = collezione.getTuttiIFilm().size();
        CollezioneFilm.getCollezione().aggiungiFilm("Sono schifezza", "Un'altra persona");
        int nuova_dimensione = collezione.getTuttiIFilm().size();

        assertEquals(dimensione_Collezione + 1, nuova_dimensione);
        collezione.getTuttiIFilm();
    }

    @org.junit.jupiter.api.Test
    void rimuoviFilm() {
        System.out.println("test rimuovi film");
        int dimensione_Collezione = collezione.getTuttiIFilm().size();
        collezione.rimuoviFilm(collezione.cercaPerTitolo("ABC").getFirst());
        int nuova_dimensione = collezione.getTuttiIFilm().size();
        assertEquals(dimensione_Collezione - 1, nuova_dimensione);
        System.out.println(collezione.getTuttiIFilm());
    }


    @org.junit.jupiter.api.Test
    void cercaPerTitolo() {
        System.out.println("test cerca per titolo");
        assertEquals(1, collezione.cercaPerTitolo("Cado dalle nubi").size());
        System.out.println(collezione.cercaPerTitolo("Cado dalle nubi"));
        assertEquals(0, collezione.cercaPerTitolo("esse3").size());
        System.out.println(collezione.cercaPerTitolo("esse3"));
    }

    @org.junit.jupiter.api.Test
    void cercaPerAnno() {
        System.out.println("test cerca per anno");
        assertEquals(2, collezione.cercaPerAnno(1997).size());
        System.out.println(collezione.cercaPerAnno(1997));
        assertEquals(0, collezione.cercaPerAnno(1890).size());
        System.out.println(collezione.cercaPerAnno(1890));
        assertEquals(1, collezione.cercaPerAnno(null).size());
        System.out.println(collezione.cercaPerAnno(null));
    }

    @org.junit.jupiter.api.Test
    void cercaPerGenere() {
        System.out.println("test cerca per genere");
        assertEquals(2, collezione.cercaPerGenere(GenereCinematografico.DRAMMATICO).size());
        System.out.println(collezione.cercaPerGenere(GenereCinematografico.DRAMMATICO));
        assertEquals(0, collezione.cercaPerGenere(GenereCinematografico.AZIONE).size());
        System.out.println(collezione.cercaPerGenere(GenereCinematografico.AZIONE));
        assertEquals(1, collezione.cercaPerAnno(null).size());
        System.out.println(collezione.cercaPerAnno(null));
    }

    @org.junit.jupiter.api.Test
    void cercaPerRegista() {
        System.out.println("test cerca per regista");
        assertEquals(1, collezione.cercaPerRegista("ciao").size());
        System.out.println(collezione.cercaPerRegista("ciao"));
        assertEquals(0, collezione.cercaPerRegista(null).size());
        System.out.println(collezione.cercaPerRegista(null));
    }

    @org.junit.jupiter.api.Test
    void cercaPerStato() {
        System.out.println("test cerca per stato");
        assertEquals(2, collezione.cercaPerStato(Stato.DA_VEDERE).size());
        assertEquals(1, collezione.cercaPerStato(Stato.IN_VISIONE).size());
        assertEquals(0, collezione.cercaPerStato(null).size());
    }

    @org.junit.jupiter.api.Test
    void cercaPerValutazione() {
        System.out.println("test cerca per valutazione");
        assertEquals(1, collezione.cercaPerValutazione(Valutazione.CINQUE_STELLE).size());
        assertEquals(2, collezione.cercaPerValutazione(Valutazione.NA).size());
        System.out.println(collezione.cercaPerValutazione(Valutazione.NA));
    }

    @org.junit.jupiter.api.Test
    void getFilmOrdinati() {
        System.out.println("test film ordinati per titolo");
        collezione.setStrategy(new StrategyTitolo());
        System.out.println(collezione.getFilmOrdinati());
        System.out.println("test film ordinati per anno");
        collezione.setStrategy(new StrategyAnno());
        System.out.println(collezione.getFilmOrdinati());
        System.out.println("test film ordinati per stato");
        collezione.setStrategy(new StrategyStato());
        System.out.println(collezione.getFilmOrdinati());
        System.out.println("test film ordinati per genere");
        collezione.setStrategy(new StrategyGenere());
        System.out.println(collezione.getFilmOrdinati());
        System.out.println("test film ordinati per valutazione");
        collezione.setStrategy(new StrategyValutazione());
        System.out.println(collezione.getFilmOrdinati());
    }
}
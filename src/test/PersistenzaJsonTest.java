package test;

import BaseProgetto.Enum.*;
import BaseProgetto.Film;
import FactoryPersistenza.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistenzaJsonTest {

    //Questo è un file di test; non quello che useremo alla fine
    private String file1 = "collezione_test.json";
    private File file2 = new File("C:\\Users\\simar\\IdeaProjects\\GestoreCollezioneFilm\\src\\test\\test.json");

    private PersistenzaIF persistenza;
    private List<Film> listaDiTest;

    @BeforeEach
    void setUp() throws Exception {
        persistenza = new PersistenzaJson(file1);
        List<Film> listaDiTest = new ArrayList<>();
        Film film1 = new Film.Builder("Il Signore degli Anelli", "Peter Jackson")
                .setAnno(2001).setGenere(GenereCinematografico.FANTASY).build();
        Film film2 = new Film.Builder("Inception", "Christopher Nolan")
                .setAnno(2010).setStato(Stato.VISTO).build();
        listaDiTest.add(film1);
        listaDiTest.add(film2);
        persistenza.salva(listaDiTest);
        System.out.println(listaDiTest.toString());
    }


    /*@Test
    void testCaricaESalva() throws Exception {
        PersistenzaJson persistenza = new PersistenzaJson(file1);
        List<Film> listaCaricata = persistenza.carica();
        assertEquals(, listaCaricata.size());
        Film film1 = new Film.Builder("Il Signore degli Anelli", "Peter Jackson")
                .setAnno(2001).setGenere(GenereCinematografico.FANTASY).build();
        Film film2 = new Film.Builder("Inception", "Christopher Nolan")
                .setAnno(2010).setStato(Stato.VISTO).build();
        List<Film> test_salvataggio = new ArrayList<>(listaCaricata);
        test_salvataggio.add(film1);
        test_salvataggio.add(film2);

        persistenza.salva(test_salvataggio);
    }*/

    @Test
    void testCaricaESalva() throws Exception {
        PersistenzaJson persistenza = new PersistenzaJson(file1);
        List<Film> listaCaricata = persistenza.carica();
        assertEquals(2, listaCaricata.size());
        Film film1 = new Film.Builder("Cado dalle nubi", "Gennaro Nuziante").setAnno(2009).
                setGenere(GenereCinematografico.COMMEDIA).setStato(Stato.DA_VEDERE).
                setValutazione(Valutazione.CINQUE_STELLE).build();
        Film film2 = new Film.Builder("La vita è bella", "Roberto Benigni").setAnno(1997).
                setGenere(GenereCinematografico.DRAMMATICO).setStato(Stato.VISTO).
                setValutazione(Valutazione.QUATTRO_STELLE).build();
        List<Film> test_salvataggio = new ArrayList<>(listaCaricata);
        test_salvataggio.add(film1);
        test_salvataggio.add(film2);
        System.out.println(test_salvataggio.toString());
        persistenza.salva(test_salvataggio);
    }

    @Test
    void carica() throws Exception {
        //il salvataggio avviene già in setup(). Carico soltanto
        List<Film> listaCaricata = persistenza.carica();
        //controllo la lunghezza
        assertEquals(2, listaCaricata.size());
        //controllo i singoli film
        assertEquals("Il Signore degli Anelli", listaCaricata.get(0).getTitolo());
        assertEquals("Inception", listaCaricata.get(1).getTitolo());
        System.out.println(listaCaricata.toString());
    }
}
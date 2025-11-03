package BaseProgetto.Singleton;

import BaseProgetto.Film; // Importa la tua classe Film
import java.util.ArrayList;
import java.util.List;

 // Questa classe gestisce la collezione di film e serve ad assicurarsi una sola istanza della collezione in tutta
 // l'applicazione.
public class CollezioneFilm {

    // Questa è l'istanza che verrà restituira; è statica e privata.
    private static CollezioneFilm ListaUnica;

    // Questa è la lista che contiene i film su cui lavoreremo.
    private final List<Film> ListaFilm;


    // Il costruttore è privato così da non poter creare nuove istanze con una nuove new.
    // Quel new viene usato alla creazione del singleton e non verrà più richiamato.
    private CollezioneFilm() {
        ListaFilm = new ArrayList<>();
    }

    // Questo è il metodo statico pubblico che tutti useranno per ottenere l'unica istanza della classe.
    public static CollezioneFilm getInstance() {
        if (ListaUnica == null) {
            ListaUnica = new CollezioneFilm();
        }
        return ListaUnica;
    }


    public void aggiungiFilm(Film film) {
        if (film != null) {
            ListaFilm.add(film);
        }
    }

    public void rimuoviFilm(Film film) {
        if (film != null) {
            ListaFilm.remove(film);
        }
    }

    public List<Film> getTuttiIFilm() {
        return new ArrayList<>(ListaFilm);
    }

    public List<Film> getFilmPerAnno(String anno) {
        List<Film> filmPerAnno = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getAnno().equalsIgnoreCase(anno)){
                filmPerAnno.add(film);
            }
        }
        return filmPerAnno;
    }

    public List<Film> getFilmPerGenere(String genere) {
        List<Film> filmPerGenere = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getGenereCinematografico().toString().equalsIgnoreCase(genere)) {
                filmPerGenere.add(film);
            }
        }
        return filmPerGenere;
    }

    public List<Film> getFilmPerRegista(String regista) {
        List<Film> filmPerRegista = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getRegista().equalsIgnoreCase(regista)) {
                filmPerRegista.add(film);
            }
        }
        return filmPerRegista;
    }

    public List<Film> getFilmPerStato(String stato) {
        List<Film> filmPerStato = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getStato().toString().equalsIgnoreCase(stato)) {
                filmPerStato.add(film);
            }
        }
        return filmPerStato;
    }

}

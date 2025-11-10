package BaseProgetto.Singleton;

import BaseProgetto.Enum.GenereCinematografico;
import BaseProgetto.Enum.Stato;
import BaseProgetto.Enum.Valutazione;
import BaseProgetto.Film;
import FactoryPersistenza.*;
import StrategyOrdinamento.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Questa classe gestisce la collezione di film e serve ad assicurarsi una sola istanza della collezione in tutta
// l'applicazione.
public class CollezioneFilm {

    //Questa è l'istanza che verrà restituira; è statica e privata.
    private static CollezioneFilm ListaUnica;

    //Questa è la lista che contiene i film su cui lavoreremo.
    private final List<Film> ListaFilm;

    //Di default imposto l'ordinamento per Stato
    private StrategyIF strategy = new StrategyStato();

    //Integro la parte della persistenza
    private PersistenzaIF persistenza = new PersistenzaJson();

    //Il costruttore è privato così da non poter creare nuove istanze con nuove new.
    //Quel new viene usato alla creazione del singleton e non verrà più richiamato.
    //Update: Aggiungo il controllo con la persistenza, in questo modo quando ho bisogno di una collezione,
    //se esiste, ce l'ho già caricata.
    private CollezioneFilm() {
        this.ListaFilm = new ArrayList<>();
    }

    public void caricaCollezione(){
        try {
            List<Film> listaCaricata = this.persistenza.carica();
            this.ListaFilm.clear(); // Svuota la lista corrente
            this.ListaFilm.addAll(listaCaricata); // Aggiungi i film caricati
            System.out.println("Dati caricati con successo.");
        } catch (Exception e) {
            System.err.println("Impossibile caricare i dati. La lista rimane vuota.");
            e.printStackTrace();
            this.ListaFilm.clear(); // La svuoto per sicurezza
        }
    }

    public void setPersistenza(String tipo) {
        this.persistenza = FactoryPersistenza.Persistenza.creaRepository(tipo);
    }

    public void setPersistenza(PersistenzaIF persistenza){
        this.persistenza = persistenza;
    }

    public void salvaCollezione() {
        try {
            this.persistenza.salva(this.ListaFilm);
            System.out.println("Collezione salvata con successo.");
        } catch (Exception e) {
            System.err.println("Impossibile salvare i dati.");
            e.printStackTrace();
        }
    }

    // Questo è il metodo statico pubblico che tutti useranno per ottenere l'unica istanza della classe.
    public static CollezioneFilm getCollezione() {
        if (ListaUnica == null) {
            ListaUnica = new CollezioneFilm();
        }
        return ListaUnica;
    }

    public void setStrategy (StrategyIF strategy){
        this.strategy = strategy;
    }

    public void aggiungiFilm(String titolo, String regista) {
        Film film = new Film.Builder(titolo, regista).build();
        aggiungiFilm(film);
    }

    public void aggiungiFilm(Film film) {
        if (film != null && !(ListaFilm.contains(film))) {
            ListaFilm.add(film);
            System.out.println("Film aggiunto con successo: " + film.getTitolo());
        }
    }

     public void rimuoviFilm(Film film) {
         if (film == null) {
             System.out.println("Operazione annullata: parametro non valido");
         }
         boolean rimosso = ListaFilm.remove(film);
         if (rimosso) {
             System.out.println("Film rimosso con successo: " + film.getTitolo());
         } else {
             System.out.println("Il film '" + film.getTitolo() + "' non è presente nella lista.");
         }
     }

    public List<Film> getTuttiIFilm() {
        System.out.println(ListaFilm);
        return ListaFilm;
    }

    public List<Film> cercaPerTitolo(String titolo) {
        List<Film> filmPerTitolo = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getTitolo().equalsIgnoreCase(titolo)) {
                filmPerTitolo.add(film);
            }
        }
        return filmPerTitolo;
    }

    public List<Film> cercaPerAnno(Integer anno) {
        List<Film> filmPerAnno = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (Objects.equals(film.getAnno(), anno)){
                filmPerAnno.add(film);
            }
        }
        return filmPerAnno;
    }

    public List<Film> cercaPerGenere(GenereCinematografico genere) {
        List<Film> filmPerGenere = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getGenereCinematografico() == genere) {
                filmPerGenere.add(film);
            }
        }
        return filmPerGenere;
    }

    public List<Film> cercaPerRegista(String regista) {
        List<Film> filmPerRegista = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getRegista().equalsIgnoreCase(regista)) {
                filmPerRegista.add(film);
            }
        }
        return filmPerRegista;
    }

    public List<Film> cercaPerStato(Stato stato) {
        List<Film> filmPerStato = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getStato() == stato) {
                filmPerStato.add(film);
            }
        }
        return filmPerStato;
    }

    public List<Film> cercaPerValutazione(Valutazione valutazione) {
        List<Film> filmPerValutazione = new ArrayList<>();
        for (Film film : ListaFilm) {
            if (film.getValutazione() == valutazione) {
                filmPerValutazione.add(film);
            }
        }
        return filmPerValutazione;
    }

    public List<Film> getFilmOrdinati() {
        List<Film> listaOrdinata = this.strategy.ordina(ListaFilm);
        return listaOrdinata;
    }

    public CollezioneFilm svuota(){
        this.ListaFilm.clear();
        return ListaUnica;
    }

    public static void Main(String[] args) {
        System.out.println("hello");
    }

}
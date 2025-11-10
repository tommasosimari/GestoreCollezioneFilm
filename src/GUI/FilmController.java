package GUI;

import BaseProgetto.Enum.*;
import BaseProgetto.Film;
import BaseProgetto.Singleton.CollezioneFilm;
import StrategyOrdinamento.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import java.util.List;

public class FilmController {

    // --- Collegamenti FXML ---
    // Questi campi sono collegati automaticamente da JavaFX ai componenti
    // definiti nel file main-view.fxml (grazie a fx:id)
    @FXML
    private TableView<Film> tabellaFilm;
    @FXML
    private TableColumn<Film, String> colTitolo;
    @FXML
    private TableColumn<Film, String> colRegista;
    @FXML
    private TableColumn<Film, Integer> colAnno;
    @FXML
    private TableColumn<Film, GenereCinematografico> colGenere;
    @FXML
    private TableColumn<Film, Stato> colStato;
    @FXML
    private TableColumn<Film, Valutazione> colValutazione;

    // --- Riferimento al Backend ---
    // Questo è il tuo Singleton! L'unico punto di accesso alla logica.
    private CollezioneFilm collezione = CollezioneFilm.getCollezione();

    // --- Logica Interna GUI ---
    // Una lista "osservabile" che la tabella guarda.
    // Se questa lista cambia, la tabella si aggiorna automaticamente.
    private ObservableList<Film> observableListFilm;


    /**
     * Metodo speciale chiamato da JavaFX dopo che il file FXML è stato caricato.
     * È l'equivalente del "main" per la finestra.
     */
    @FXML
    public void initialize() {
        // 1. Collega le colonne della tabella ai campi della classe Film
        // "titolo" deve corrispondere al metodo getTitolo() in Film.java
        colTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        colRegista.setCellValueFactory(new PropertyValueFactory<>("regista"));
        colAnno.setCellValueFactory(new PropertyValueFactory<>("anno"));
        colGenere.setCellValueFactory(new PropertyValueFactory<>("genereCinematografico"));
        colStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        colValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));

        // 2. Carica i dati dal file di default (JSON)
        collezione.caricaCollezione();

        // 3. Popola la tabella
        refreshTabella();
    }

    /**
     * Metodo helper per aggiornare la tabella con i dati
     * presenti nel Singleton.
     */
    private void refreshTabella() {
        // Prende la lista di film dal singleton
        List<Film> filmDalSingleton = collezione.getTuttiIFilm();
        // La converte in una lista che la tabella può "osservare"
        observableListFilm = FXCollections.observableArrayList(filmDalSingleton);
        // Collega la lista osservabile alla tabella
        tabellaFilm.setItems(observableListFilm);
    }

    // --- GESTIONE PATTERN FACTORY (PERSISTENZA) ---

    @FXML
    void onCaricaJson() {
        collezione.setPersistenza("JSON"); // Usa la tua Factory!
        collezione.caricaCollezione();
        refreshTabella();
    }

    @FXML
    void onSalvaJson() {
        collezione.setPersistenza("JSON"); // Usa la tua Factory!
        collezione.salvaCollezione();
        // (Aggiungi un popup di "Salvato!" qui)
    }

    @FXML
    void onCaricaCsv() {
        collezione.setPersistenza("CSV"); // Usa la tua Factory!
        collezione.caricaCollezione();
        refreshTabella();
    }

    @FXML
    void onSalvaCsv() {
        collezione.setPersistenza("CSV"); // Usa la tua Factory!
        collezione.salvaCollezione();
        // (Aggiungi un popup di "Salvato!" qui)
    }

    // --- GESTIONE PATTERN STRATEGY (ORDINAMENTO) ---

    @FXML
    void onOrdinaPerTitolo() {
        collezione.setStrategy(new StrategyTitolo()); // Usa la tua Strategy!
        List<Film> filmOrdinati = collezione.getFilmOrdinati();
        tabellaFilm.setItems(FXCollections.observableArrayList(filmOrdinati));
    }

    @FXML
    void onOrdinaPerAnno() {
        collezione.setStrategy(new StrategyAnno()); // Usa la tua Strategy!
        List<Film> filmOrdinati = collezione.getFilmOrdinati();
        tabellaFilm.setItems(FXCollections.observableArrayList(filmOrdinati));
    }

    @FXML
    void onOrdinaPerGenere() {
        collezione.setStrategy(new StrategyGenere()); // Usa la tua Strategy!
        List<Film> filmOrdinati = collezione.getFilmOrdinati();
        tabellaFilm.setItems(FXCollections.observableArrayList(filmOrdinati));
    }

    @FXML
    void onOrdinaPerStato() {
        collezione.setStrategy(new StrategyStato()); // Usa la tua Strategy!
        List<Film> filmOrdinati = collezione.getFilmOrdinati();
        tabellaFilm.setItems(FXCollections.observableArrayList(filmOrdinati));
    }

    @FXML
    void onOrdinaPerValutazione() {
        collezione.setStrategy(new StrategyValutazione()); // Usa la tua Strategy!
        List<Film> filmOrdinati = collezione.getFilmOrdinati();
        tabellaFilm.setItems(FXCollections.observableArrayList(filmOrdinati));
    }

    @FXML
    void onAggiungiFilm() {
        try {
            // 1. Carica il file FXML per il dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("film-dialog.fxml"));
            Parent page = loader.load();

            // 2. Crea la finestra (Stage) per il dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Aggiungi Nuovo Film");
            dialogStage.initModality(Modality.WINDOW_MODAL); // Blocca la finestra principale
            dialogStage.initOwner(tabellaFilm.getScene().getWindow()); // Collega alla finestra principale
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 3. Passa lo stage al controller del dialog
            FilmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // 4. Mostra il dialog e aspetta che l'utente lo chiuda
            dialogStage.showAndWait();

            // 5. Se l'utente ha cliccato OK...
            if (controller.isOkClicked()) {
                Film nuovoFilm = controller.getNuovoFilm(); // Prendi il film creato
                if (nuovoFilm != null) {
                    collezione.aggiungiFilm(nuovoFilm); // Aggiungi al singleton
                    refreshTabella(); // Aggiorna la tabella
                    collezione.salvaCollezione(); // Salva su file
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Mostra un alert di errore
            showAlertErrore("Errore", "Impossibile caricare la finestra di dialogo.");
        }
    }

    // --- METODO onModificaFilm() NUOVO ---
    @FXML
    void onModificaFilm() {
        // Prende il film selezionato dall'utente nella tabella
        Film filmSelezionato = tabellaFilm.getSelectionModel().getSelectedItem();

        // Se l'utente non ha selezionato nulla, mostra un avviso
        if (filmSelezionato == null) {
            showAlertAvviso("Nessuna selezione", "Per favore, seleziona un film da modificare.");
            return;
        }

        try {
            // 1. Carica il file FXML (è lo stesso di "Aggiungi")
            FXMLLoader loader = new FXMLLoader(getClass().getResource("film-dialog.fxml"));
            Parent page = loader.load();

            // 2. Crea lo stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Film");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tabellaFilm.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 3. Passa il film da MODIFICARE al controller
            FilmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFilmPerModifica(filmSelezionato); // <- Questo è il passaggio chiave

            // 4. Mostra e aspetta
            dialogStage.showAndWait();

            // 5. Se l'utente ha cliccato OK, i dati sono GIA' stati modificati
            // nel filmSelezionato (grazie al controller del dialog).
            // Dobbiamo solo aggiornare la tabella e salvare.
            if (controller.isOkClicked()) {
                refreshTabella(); // Ricarica la tabella con i dati modificati
                collezione.salvaCollezione(); // Salva le modifiche su file
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlertErrore("Errore", "Impossibile caricare la finestra di dialogo.");
        }
    }


    @FXML
    void onRimuoviFilm() {
        // Prende il film selezionato dall'utente nella tabella
        Film filmSelezionato = tabellaFilm.getSelectionModel().getSelectedItem();
        if (filmSelezionato != null) {
            collezione.rimuoviFilm(filmSelezionato);
            refreshTabella(); // Aggiorna la vista
            collezione.salvaCollezione(); // Salva dopo la rimozione
        } else {
            // (Aggiungi un popup di "Seleziona un film" qui)
            showAlertAvviso("Nessuna selezione", "Per favore, seleziona un film da rimuovere.");
        }
    }

    // --- METODI HELPER PER I POPUP (opzionali ma utili) ---

    private void showAlertAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void showAlertErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}

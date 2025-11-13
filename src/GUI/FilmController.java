package GUI;

import BaseProgetto.Enum.*;
import BaseProgetto.Film;
import BaseProgetto.Singleton.CollezioneFilm;
import StrategyOrdinamento.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class FilmController {

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
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<GenereCinematografico> filtroGenereBox;
    @FXML
    private ComboBox<Stato> filtroStatoBox;

    private CollezioneFilm collezione = CollezioneFilm.getCollezione();

    private ObservableList<Film> masterFilmList = FXCollections.observableArrayList();

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

        filtroGenereBox.getItems().add(null);
        filtroGenereBox.getItems().addAll(GenereCinematografico.values());

        filtroStatoBox.getItems().add(null);
        filtroStatoBox.getItems().addAll(Stato.values());

        collezione.caricaCollezione();

        refreshMasterList();
        aggiornaTabellaDaCollezione();

        //filtri
        filtroGenereBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> aggiornaTabellaDaCollezione()
        );
        filtroStatoBox.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldValue, newValue) -> aggiornaTabellaDaCollezione()
        );
        searchField.textProperty().addListener(
                (obs, oldValue, newValue) -> aggiornaTabellaDaCollezione()
        );
        tabellaFilm.setItems(masterFilmList);
    }

    private void aggiornaTabellaDaCollezione() {

        String keyword = searchField.getText().trim().toLowerCase();
        GenereCinematografico genere = filtroGenereBox.getValue();
        Stato stato = filtroStatoBox.getValue();

        List<Film> risultati = new ArrayList<>(collezione.getTuttiIFilm());

        if (!keyword.isEmpty()) {
            List<Film> perTitolo = collezione.cercaPerTitolo(keyword);
            List<Film> perRegista = collezione.cercaPerRegista(keyword);

            Set<Film> unione = new HashSet<>();
            unione.addAll(perTitolo);
            unione.addAll(perRegista);

            try {
                List<Film> perAnno = collezione.cercaPerAnno(Integer.valueOf(keyword));
                unione.addAll(perAnno);
            }catch (NumberFormatException e){
            }

            risultati.retainAll(unione);
        }
        if (genere != null) {
            risultati.retainAll(collezione.cercaPerGenere(genere));
        }
        if (stato != null) {
            risultati.retainAll(collezione.cercaPerStato(stato));
        }
        masterFilmList.setAll(risultati);
    }


    private void refreshMasterList() {
       masterFilmList.setAll(collezione.getTuttiIFilm());
    }

    //persistenza
    @FXML
    void onCaricaJson() {
        collezione.setPersistenza("JSON");
        collezione.caricaCollezione();
        refreshMasterList();
        aggiornaTabellaDaCollezione();
    }

    @FXML
    void onSalvaJson() {
        collezione.setPersistenza("JSON");
        collezione.salvaCollezione();
        refreshMasterList();
        aggiornaTabellaDaCollezione();
    }

    @FXML
    void onCaricaCsv() {
        collezione.setPersistenza("CSV");
        collezione.caricaCollezione();
        refreshMasterList();
        aggiornaTabellaDaCollezione();
    }

    @FXML
    void onSalvaCsv() {
        collezione.setPersistenza("CSV");
        collezione.salvaCollezione();
        refreshMasterList();
        aggiornaTabellaDaCollezione();
    }

    //ordinamento
    @FXML
    void onOrdinaPerTitolo() {
        tabellaFilm.getSortOrder().clear();
        tabellaFilm.getSortOrder().add(colTitolo);
    }

    @FXML
    void onOrdinaPerAnno() {
        tabellaFilm.getSortOrder().clear();
        tabellaFilm.getSortOrder().add(colAnno);
    }

    @FXML
    void onOrdinaPerGenere() {
        tabellaFilm.getSortOrder().clear();
        tabellaFilm.getSortOrder().add(colGenere);
    }

    @FXML
    void onOrdinaPerStato() {
        tabellaFilm.getSortOrder().clear();
        tabellaFilm.getSortOrder().add(colStato);
    }

    @FXML
    void onOrdinaPerValutazione() {
        tabellaFilm.getSortOrder().clear();
        tabellaFilm.getSortOrder().add(colValutazione);
    }

    @FXML
    void onAggiungiFilm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("film-dialog.fxml"));
            Parent page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Aggiungi Nuovo Film");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tabellaFilm.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            FilmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                Film nuovoFilm = controller.getNuovoFilm();
                if (nuovoFilm != null) {
                    collezione.aggiungiFilm(nuovoFilm);
                    collezione.salvaCollezione();
                    refreshMasterList();
                    aggiornaTabellaDaCollezione();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlertErrore("Errore", "Impossibile caricare la finestra di dialogo.");
        }
    }

    @FXML
    void onModificaFilm() {
        Film filmSelezionato = tabellaFilm.getSelectionModel().getSelectedItem();
        if (filmSelezionato == null) {
            showAlertAvviso("Nessuna selezione", "Per favore, seleziona un film da modificare.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("film-dialog.fxml"));
            Parent page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Film");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tabellaFilm.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            FilmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFilmPerModifica(filmSelezionato);
            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                collezione.salvaCollezione();
                refreshMasterList();
                aggiornaTabellaDaCollezione();
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlertErrore("Errore", "Impossibile caricare la finestra di dialogo.");
        }
    }

    @FXML
    void onRimuoviFilm() {
        Film filmSelezionato = tabellaFilm.getSelectionModel().getSelectedItem();
        if (filmSelezionato != null) {
            collezione.rimuoviFilm(filmSelezionato);
            collezione.salvaCollezione();
            refreshMasterList();
            aggiornaTabellaDaCollezione();
        } else {
            showAlertAvviso("Nessuna selezione", "Per favore, seleziona un film da rimuovere.");
        }
    }

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
package GUI;

import BaseProgetto.Enum.GenereCinematografico;
import BaseProgetto.Enum.Stato;
import BaseProgetto.Enum.Valutazione;
import BaseProgetto.Film;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilmDialogController {

    @FXML private TextField titoloField;
    @FXML private TextField registaField;
    @FXML private TextField annoField;
    @FXML private ComboBox<GenereCinematografico> genereBox;
    @FXML private ComboBox<Stato> statoBox;
    @FXML private ComboBox<Valutazione> valutazioneBox;

    private Stage dialogStage;
    private Film film; // Il film che stiamo modificando
    private boolean isOkClicked = false;
    private boolean isModalitaModifica = false;

    @FXML
    public void initialize() {
        // Popola i menu a tendina con i valori degli Enum
        genereBox.setItems(FXCollections.observableArrayList(GenereCinematografico.values()));
        statoBox.setItems(FXCollections.observableArrayList(Stato.values()));
        valutazioneBox.setItems(FXCollections.observableArrayList(Valutazione.values()));
    }

    /**
     * Imposta lo stage (la finestra) per questo dialog.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Imposta il film da modificare (per la modalità Modifica).
     * Questo popola i campi con i dati esistenti.
     */
    public void setFilmPerModifica(Film film) {
        this.film = film;
        this.isModalitaModifica = true;

        // Pre-compila i campi
        titoloField.setText(film.getTitolo());
        registaField.setText(film.getRegista());
        annoField.setText(film.getAnno() != null ? film.getAnno().toString() : "");
        genereBox.setValue(film.getGenereCinematografico());
        statoBox.setValue(film.getStato());
        valutazioneBox.setValue(film.getValutazione());
    }

    /**
     * Ritorna true se l'utente ha cliccato OK, false altrimenti.
     */
    public boolean isOkClicked() {
        return isOkClicked;
    }

    /**
     * Chiamato quando l'utente clicca OK.
     * Valida i dati e poi o modifica il film esistente o ne crea uno nuovo.
     */
    @FXML
    private void onOk() {
        if (isInputValid()) {
            if (isModalitaModifica) {
                // Modalità Modifica: aggiorna il film esistente
                film.setTitolo(titoloField.getText());
                film.setRegista(registaField.getText());
                film.setGenereCinematografico(genereBox.getValue());
                film.setStato(statoBox.getValue());
                film.setValutazione(valutazioneBox.getValue());
                try {
                    film.setAnno(Integer.parseInt(annoField.getText()));
                } catch (NumberFormatException e) {
                    film.setAnno(null); // Gestisce anno non valido/vuoto
                }
            } else {
                // Modalità Aggiungi: crea un nuovo film (lo lasciamo fare
                // a getNuovoFilm() per pulizia)
            }

            isOkClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Ritorna un NUOVO film (solo per la modalità Aggiungi).
     */
    public Film getNuovoFilm() {
        if (isModalitaModifica || !isOkClicked) {
            return null; // Non siamo in modalità aggiungi
        }

        Integer anno = null;
        try {
            anno = Integer.parseInt(annoField.getText());
        } catch (NumberFormatException e) {
            // L'anno rimane null
        }

        return new Film.Builder(titoloField.getText(), registaField.getText())
                .setAnno(anno)
                .setGenere(genereBox.getValue())
                .setStato(statoBox.getValue())
                .setValutazione(valutazioneBox.getValue())
                .build();
    }

    /**
     * Chiamato quando l'utente clicca Annulla.
     */
    @FXML
    private void onAnnulla() {
        dialogStage.close();
    }

    /**
     * Valida l'input dell'utente nei campi (minimo indispensabile).
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (titoloField.getText() == null || titoloField.getText().isEmpty()) {
            errorMessage += "Titolo non valido!\n";
        }
        if (registaField.getText() == null || registaField.getText().isEmpty()) {
            errorMessage += "Regista non valido!\n";
        }
        if (!annoField.getText().isEmpty()) {
            try {
                Integer.parseInt(annoField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Anno non valido (deve essere un numero)!\n";
            }
        }
        // I ComboBox non hanno bisogno di validazione se hanno un default

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Mostra un pop-up di errore
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore nell'Input");
            alert.setHeaderText("Per favore, correggi i campi non validi");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}

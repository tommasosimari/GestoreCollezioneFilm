package FactoryPersistenza;

import BaseProgetto.*;
import BaseProgetto.Enum.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PersistenzaCSV implements PersistenzaIF {

    private String file;
    private Gson gson;

    public PersistenzaCSV() {
        this.file = "collezione_film.csv";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public PersistenzaCSV(String nomeFile) {
        this.file = nomeFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public PersistenzaCSV(File file_path){
        this.file = file_path.getPath();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salva(List<Film> ListaFilm) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            String[] intestazione = {"Titolo", "Regista", "Anno", "Genere", "Stato", "Valutazione"};
            writer.writeNext(intestazione);

            // Ogni film è un array di stringhe e iteriamo sulla lista di film
            for (Film film : ListaFilm) {
                String[] riga = {
                        film.getTitolo(),
                        film.getRegista(),
                        film.getAnno() != null ? film.getAnno().toString() : "",
                        film.getGenereCinematografico() != null ? film.getGenereCinematografico().toString() : "",
                        film.getStato() != null ? film.getStato().toString() : "",
                        film.getValutazione() != null ? film.getValutazione().toString() : ""
                };
                writer.writeNext(riga);
            }
        }
    }

    @Override
    public List<Film> carica() throws Exception {
        List<Film> filmList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.readNext();
            String[] riga;
            while ((riga = reader.readNext()) != null) {
                try {
                    //riconvertiamo l'array di stringhe in un oggetto Film
                    String titolo = riga[0];
                    String regista = riga[1];

                    Integer anno = riga[2].isEmpty() ? null : Integer.parseInt(riga[2]);

                    //qui prendiamo i valori degli enum
                    GenereCinematografico genere = GenereCinematografico.valueOf(riga[3]);
                    Stato stato = Stato.valueOf(riga[4]);
                    Valutazione valutazione = Valutazione.valueOf(riga[5]);

                    //creiamo l'oggetto Film
                    Film film = new Film.Builder(titolo, regista)
                            .setAnno(anno)
                            .setGenere(genere)
                            .setStato(stato)
                            .setValutazione(valutazione)
                            .build();

                    filmList.add(film);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    // Se una riga è corrotta, la saltiamo e la salviamo
                    System.err.println("Errore nel parsing di una riga CSV: " + String.join(",", riga));
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Il file non è stato trovato. Il percorso potrebbe essere errato");
        }
        return filmList;
    }
}
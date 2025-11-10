package FactoryPersistenza;

import BaseProgetto.Film;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersistenzaJson implements PersistenzaIF{

    private String file;
    private Gson gson;

    public PersistenzaJson() {
        this.file = "collezione_film.json";
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public PersistenzaJson (String nomeFile){
        this.file = nomeFile;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public PersistenzaJson(File file_path){
        this.file = file_path.getPath();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salva(List<Film> ListaFilm) throws Exception {
        try (Writer writer = new FileWriter(file)) {
            //Gson converte l'intera List<Film> in una stringa JSON
            gson.toJson(ListaFilm, writer);
        }
    }

    @Override
    public List<Film> carica() throws Exception {
        try (Reader reader = new FileReader(file)) {

            // In questo modo dico a Gson che non voglio un film ma una lista di film.
            Type tipoLista = new TypeToken<ArrayList<Film>>() {}.getType();
            // Gson converte il testo JSON negli oggetti Java che vogliamo
            List<Film> filmList = gson.fromJson(reader, tipoLista);

            // Qui controlliamo se il file è vuoto
            if (filmList == null) {
                return new ArrayList<>();
            }
            return filmList;

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Il file non è stato trovato. Il percorso potrebbe essere errato");
        }
    }
}

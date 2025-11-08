package FactoryPersistenza;

import BaseProgetto.*;

import java.io.IOException;
import java.util.List;

public interface PersistenzaIF {

    void salva(List<Film> ListaFilm) throws Exception;

    List<Film> carica() throws Exception;
}

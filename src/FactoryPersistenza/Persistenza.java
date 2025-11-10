package FactoryPersistenza;

public class Persistenza {

    public static PersistenzaIF creaRepository(String tipo) {

        if ("JSON".equalsIgnoreCase(tipo)) {
            return new PersistenzaJson();

        }
        else if ("CSV".equalsIgnoreCase(tipo)) {
            return new PersistenzaCSV();
        }

        else {
            throw new IllegalArgumentException("Inserisci il tipo corretto: JSON o CSV");
        }
    }
}

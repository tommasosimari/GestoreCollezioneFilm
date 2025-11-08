package FactoryPersistenza;

public class Persistenza {

    public static PersistenzaIF creaRepository(String tipo) {

        if ("JSON".equalsIgnoreCase(tipo)) {
            System.out.println("Il formato scelto è JSON");
            return new PersistenzaJson();

        }
        else if ("CSV".equalsIgnoreCase(tipo)) {
            System.out.println("Il formato scelto è CSV");
            return new PersistenzaCSV();
        }

        else {
            System.out.println("Tipo non riconosciuto. Di default si usa JSON");
            return new PersistenzaJson();
        }
    }
}

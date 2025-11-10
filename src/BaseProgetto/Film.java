package BaseProgetto;

import BaseProgetto.Enum.Valutazione;
import BaseProgetto.Enum.Stato;
import BaseProgetto.Enum.GenereCinematografico;

import java.util.Objects;

public class Film {

    private String titolo;
    private String regista;
    private Integer anno;
    private GenereCinematografico genereCinematografico;
    private Stato stato;
    private Valutazione valutazione;

    private Film(Builder builder) {
        this.titolo = builder.titolo;
        this.regista = builder.regista;
        this.anno = builder.anno;
        this.genereCinematografico = builder.genereCinematografico;
        this.stato = builder.stato;
        this.valutazione = builder.valutazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getRegista() {
        return regista;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public Integer getAnno() {
        return anno;
    }

    public void setAnno(Integer anno) {
        this.anno = anno;
    }

    public GenereCinematografico getGenereCinematografico() {
        return genereCinematografico;
    }

    public void setGenereCinematografico(GenereCinematografico genereCinematografico) {
        this.genereCinematografico = genereCinematografico;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    public Valutazione getValutazione() {
        return valutazione;
    }

    public void setValutazione(Valutazione valutazione) {
        this.valutazione = valutazione;
    }

    @Override
    public String toString() {
        return "Film{" +
                "titolo='" + titolo + '\'' +
                ", regista='" + regista + '\'' +
                ", anno='" + anno + '\'' +
                ", genereCinematografico=" + genereCinematografico +
                ", stato=" + stato +
                ", valutazione=" + valutazione +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Film film)) return false;
        return Objects.equals(titolo, film.titolo) && Objects.equals(regista, film.regista) &&
                Objects.equals(anno, film.anno) && genereCinematografico == film.genereCinematografico &&
                stato == film.stato && valutazione == film.valutazione;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, regista, anno, genereCinematografico, stato, valutazione);
    }


    public static class Builder {

        // Qui indichiamo i campi obbligatori
        private String titolo;
        private String regista;

        // Qui indichiamo i valori di default che inseriamo se non specificati
        private Integer anno = null;
        private GenereCinematografico genereCinematografico = GenereCinematografico.ALTRO_O_IBRIDI;
        private Stato stato = Stato.DA_VEDERE;
        private Valutazione valutazione = Valutazione.NA;

        // Costruttore del Builder obbligatorio
        public Builder(String titolo, String regista) {
            this.titolo = titolo;
            this.regista = regista;
        }

        // Metodi "setter" per gli attributi non obbligatori da aggiungere in seguito. Il return serve alla concatenazione.
        public Builder setAnno(Integer anno) {
            this.anno = anno;
            return this;
        }

        public Builder setGenere(GenereCinematografico genere) {
            this.genereCinematografico = genere;
            return this;
        }

        public Builder setStato(Stato stato) {
            this.stato = stato;
            return this;
        }

        public Builder setValutazione(Valutazione valutazione) {
            this.valutazione = valutazione;
            return this;
        }

        // Il metodo finale crea l'oggetto completo Film tramite il costruttore esterno.
        public Film build() {
            return new Film(this);
        }
    }
}
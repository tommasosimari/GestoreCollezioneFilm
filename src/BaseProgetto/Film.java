package BaseProgetto;

import BaseProgetto.Enum.Valutazione;
import BaseProgetto.Enum.Stato;
import BaseProgetto.Enum.GenereCinematografico;

public class Film {

    private String titolo;
    private String regista;
    private String anno;
    private GenereCinematografico genereCinematografico;
    private Stato stato;
    private Valutazione valutazione;

    public Film(String titolo, String regista, String anno, GenereCinematografico genereCinematografico, Stato stato, Valutazione valutazione) {
        this.titolo = titolo;
        this.regista = regista;
        this.anno = anno;
        this.genereCinematografico = genereCinematografico;
        this.stato = stato;
        this.valutazione = valutazione;
        System.out.println("Film creato");
    }

    public Film(String titolo, String regista, String anno, GenereCinematografico genereCinematografico) {
        this.titolo = titolo;
        this.regista = regista;
        this.anno = anno;
        this.genereCinematografico = genereCinematografico;
        this.stato = Stato.DA_VEDERE;
        this.valutazione = Valutazione.NA;
        System.out.println("Film creato");
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

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
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
}

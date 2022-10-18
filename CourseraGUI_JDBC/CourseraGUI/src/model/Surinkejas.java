package model;

import java.io.Serializable;

public class Surinkejas implements Serializable {

    Integer SURINKEJO_NR;
    String SURINKEJO_VARDAS;
    String SURINKEJO_PAVARDE;
    Integer SURINKEJO_TELEFONAS;
    String SURINKEJO_APRASYMAS;
    Float SURINKEJO_KAINA;

    public Surinkejas(Integer SURINKEJO_NR, String SURINKEJO_VARDAS, String SURINKEJO_PAVARDE, Integer SURINKEJO_TELEFONAS, String SURINKEJO_APRASYMAS, Float SURINKEJO_KAINA) {
        this.SURINKEJO_NR = SURINKEJO_NR;
        this.SURINKEJO_VARDAS = SURINKEJO_VARDAS;
        this.SURINKEJO_PAVARDE = SURINKEJO_PAVARDE;
        this.SURINKEJO_TELEFONAS = SURINKEJO_TELEFONAS;
        this.SURINKEJO_APRASYMAS = SURINKEJO_APRASYMAS;
        this.SURINKEJO_KAINA = SURINKEJO_KAINA;
    }

    public Integer getSURINKEJO_NR() {
        return SURINKEJO_NR;
    }

    public void setSURINKEJO_NR(Integer SURINKEJO_NR) {
        this.SURINKEJO_NR = SURINKEJO_NR;
    }

    public String getSURINKEJO_VARDAS() {
        return SURINKEJO_VARDAS;
    }

    public void setSURINKEJO_VARDAS(String SURINKEJO_VARDAS) {
        this.SURINKEJO_VARDAS = SURINKEJO_VARDAS;
    }

    public String getSURINKEJO_PAVARDE() {
        return SURINKEJO_PAVARDE;
    }

    public void setSURINKEJO_PAVARDE(String SURINKEJO_PAVARDE) {
        this.SURINKEJO_PAVARDE = SURINKEJO_PAVARDE;
    }

    public Integer getSURINKEJO_TELEFONAS() {
        return SURINKEJO_TELEFONAS;
    }

    public void setSURINKEJO_TELEFONAS(Integer SURINKEJO_TELEFONAS) {
        this.SURINKEJO_TELEFONAS = SURINKEJO_TELEFONAS;
    }

    public String getSURINKEJO_APRASYMAS() {
        return SURINKEJO_APRASYMAS;
    }

    public void setSURINKEJO_APRASYMAS(String SURINKEJO_APRASYMAS) {
        this.SURINKEJO_APRASYMAS = SURINKEJO_APRASYMAS;
    }

    public Float getSURINKEJO_KAINA() {
        return SURINKEJO_KAINA;
    }

    public void setSURINKEJO_KAINA(Float SURINKEJO_KAINA) {
        this.SURINKEJO_KAINA = SURINKEJO_KAINA;
    }
}

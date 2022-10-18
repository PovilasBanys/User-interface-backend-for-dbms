package model;

import java.io.Serializable;

public class Kurjeris implements Serializable {

    String KURJERIO_VARDAS;
    String KURJERIO_PAVARDE;
    Integer KURJERIO_NR;
    Integer KURJERIO_TELEFONAS;
    Float KURJERIO_KAINA;

    public Kurjeris(String KURJERIO_VARDAS, String KURJERIO_PAVARDE, Integer KURJERIO_NR, Integer KURJERIO_TELEFONAS, Float KURJERIO_KAINA) {
        this.KURJERIO_VARDAS = KURJERIO_VARDAS;
        this.KURJERIO_PAVARDE = KURJERIO_PAVARDE;
        this.KURJERIO_NR = KURJERIO_NR;
        this.KURJERIO_TELEFONAS = KURJERIO_TELEFONAS;
        this.KURJERIO_KAINA = KURJERIO_KAINA;
    }

    public String getKURJERIO_VARDAS() {
        return KURJERIO_VARDAS;
    }

    public void setKURJERIO_VARDAS(String KURJERIO_VARDAS) {
        this.KURJERIO_VARDAS = KURJERIO_VARDAS;
    }

    public String getKURJERIO_PAVARDE() {
        return KURJERIO_PAVARDE;
    }

    public void setKURJERIO_PAVARDE(String KURJERIO_PAVARDE) {
        this.KURJERIO_PAVARDE = KURJERIO_PAVARDE;
    }

    public Integer getKURJERIO_NR() {
        return KURJERIO_NR;
    }

    public void setKURJERIO_NR(Integer KURJERIO_NR) {
        this.KURJERIO_NR = KURJERIO_NR;
    }

    public Integer getKURJERIO_TELEFONAS() {
        return KURJERIO_TELEFONAS;
    }

    public void setKURJERIO_TELEFONAS(Integer KURJERIO_TELEFONAS) {
        this.KURJERIO_TELEFONAS = KURJERIO_TELEFONAS;
    }

    public Float getKURJERIO_KAINA() {
        return KURJERIO_KAINA;
    }

    public void setKURJERIO_KAINA(Float KURJERIO_KAINA) {
        this.KURJERIO_KAINA = KURJERIO_KAINA;
    }
}

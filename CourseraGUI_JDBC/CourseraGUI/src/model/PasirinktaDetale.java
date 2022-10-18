package model;

import java.io.Serializable;

public class PasirinktaDetale implements Serializable {

    Integer KIEKIS;
    Integer PASIRINKTOS_DETALES_ID;
    Integer DETALES_ID;
    Integer UZSAKYMO_NR;
    String SURINKIMAS;

    public PasirinktaDetale(Integer KIEKIS, Integer PASIRINKTOS_DETALES_ID, Integer DETALES_ID, Integer UZSAKYMO_NR, String SURINKIMAS) {
        this.KIEKIS = KIEKIS;
        this.PASIRINKTOS_DETALES_ID = PASIRINKTOS_DETALES_ID;
        this.DETALES_ID = DETALES_ID;
        this.UZSAKYMO_NR = UZSAKYMO_NR;
        this.SURINKIMAS = SURINKIMAS;
    }

    public Integer getKIEKIS() {
        return KIEKIS;
    }

    public void setKIEKIS(Integer KIEKIS) {
        this.KIEKIS = KIEKIS;
    }

    public Integer getPASIRINKTOS_DETALES_ID() {
        return PASIRINKTOS_DETALES_ID;
    }

    public void setPASIRINKTOS_DETALES_ID(Integer PASIRINKTOS_DETALES_ID) {
        this.PASIRINKTOS_DETALES_ID = PASIRINKTOS_DETALES_ID;
    }

    public Integer getDETALES_ID() {
        return DETALES_ID;
    }

    public void setDETALES_ID(Integer DETALES_ID) {
        this.DETALES_ID = DETALES_ID;
    }

    public Integer getUZSAKYMO_NR() {
        return UZSAKYMO_NR;
    }

    public void setUZSAKYMO_NR(Integer UZSAKYMO_NR) {
        this.UZSAKYMO_NR = UZSAKYMO_NR;
    }

    public String getSURINKIMAS() {
        return SURINKIMAS;
    }

    public void setSURINKIMAS(String SURINKIMAS) {
        this.SURINKIMAS = SURINKIMAS;
    }
}

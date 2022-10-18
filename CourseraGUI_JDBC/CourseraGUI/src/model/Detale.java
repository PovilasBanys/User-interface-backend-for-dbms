package model;

import java.io.Serializable;

public class Detale implements Serializable {

    Integer DETALES_ID;
    String PAVADINIMAS;
    String APRASAS;
    Float KAINA;
    Integer KIEKIS_SANDELYJE;
    String TIPAS;

    public Detale(Integer DETALES_ID, String PAVADINIMAS, String APRASAS, Float KAINA, Integer KIEKIS_SANDELYJE, String TIPAS) {
        this.DETALES_ID = DETALES_ID;
        this.PAVADINIMAS = PAVADINIMAS;
        this.APRASAS = APRASAS;
        this.KAINA = KAINA;
        this.KIEKIS_SANDELYJE = KIEKIS_SANDELYJE;
        this.TIPAS = TIPAS;
    }

    public Integer getDETALES_ID() {
        return DETALES_ID;
    }

    public void setDETALES_ID(Integer DETALES_ID) {
        this.DETALES_ID = DETALES_ID;
    }

    public String getPAVADINIMAS() {
        return PAVADINIMAS;
    }

    public void setPAVADINIMAS(String PAVADINIMAS) {
        this.PAVADINIMAS = PAVADINIMAS;
    }

    public String getAPRASAS() {
        return APRASAS;
    }

    public void setAPRASAS(String APRASAS) {
        this.APRASAS = APRASAS;
    }

    public Float getKAINA() {
        return KAINA;
    }

    public void setKAINA(Float KAINA) {
        this.KAINA = KAINA;
    }

    public Integer getKIEKIS_SANDELYJE() {
        return KIEKIS_SANDELYJE;
    }

    public void setKIEKIS_SANDELYJE(Integer KIEKIS_SANDELYJE) {
        this.KIEKIS_SANDELYJE = KIEKIS_SANDELYJE;
    }

    public String getTIPAS() {
        return TIPAS;
    }

    public void setTIPAS(String TIPAS) {
        this.TIPAS = TIPAS;
    }
}

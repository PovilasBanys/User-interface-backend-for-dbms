package model;

import java.io.Serializable;
import java.util.Date;

public class Pirkejas implements Serializable {

    Integer REGISTRACIJOS_NR;
    String VARDAS;
    String PAVARDE;
    Date GIMIMO_DATA;
    String SASKAITOS_NR;
    Integer TELEFONO_NR;
    String ADRESAS;

    public Pirkejas(Integer REGISTRACIJOS_NR, String VARDAS, String PAVARDE, Date GIMIMO_DATA, String SASKAITOS_NR, Integer TELEFONO_NR, String ADRESAS) {
        this.REGISTRACIJOS_NR = REGISTRACIJOS_NR;
        this.VARDAS = VARDAS;
        this.PAVARDE = PAVARDE;
        this.GIMIMO_DATA = GIMIMO_DATA;
        this.SASKAITOS_NR = SASKAITOS_NR;
        this.TELEFONO_NR = TELEFONO_NR;
        this.ADRESAS = ADRESAS;
    }

    public Integer getREGISTRACIJOS_NR() {
        return REGISTRACIJOS_NR;
    }

    public void setREGISTRACIJOS_NR(Integer REGISTRACIJOS_NR) {
        this.REGISTRACIJOS_NR = REGISTRACIJOS_NR;
    }

    public String getVARDAS() {
        return VARDAS;
    }

    public void setVARDAS(String VARDAS) {
        this.VARDAS = VARDAS;
    }

    public String getPAVARDE() {
        return PAVARDE;
    }

    public void setPAVARDE(String PAVARDE) {
        this.PAVARDE = PAVARDE;
    }

    public Date getGIMIMO_DATA() {
        return GIMIMO_DATA;
    }

    public void setGIMIMO_DATA(Date GIMIMO_DATA) {
        this.GIMIMO_DATA = GIMIMO_DATA;
    }

    public String getSASKAITOS_NR() {
        return SASKAITOS_NR;
    }

    public void setSASKAITOS_NR(String SASKAITOS_NR) {
        this.SASKAITOS_NR = SASKAITOS_NR;
    }

    public Integer getTELEFONO_NR() {
        return TELEFONO_NR;
    }

    public void setTELEFONO_NR(Integer TELEFONO_NR) {
        this.TELEFONO_NR = TELEFONO_NR;
    }

    public String getADRESAS() {
        return ADRESAS;
    }

    public void setADRESAS(String ADRESAS) {
        this.ADRESAS = ADRESAS;
    }
}

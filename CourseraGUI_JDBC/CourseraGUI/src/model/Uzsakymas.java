package model;

import java.io.Serializable;
import java.util.Date;

public class Uzsakymas implements Serializable {

    Integer UZSAKYMO_NR;
    Integer KURJERIO_NR;
    Integer SURINKEJO_NR;
    Integer REGISTRACIJOS_NR;
    Date DATA_IKI;

    public Uzsakymas(Integer UZSAKYMO_NR, Integer KURJERIO_NR, Integer SURINKEJO_NR, Integer REGISTRACIJOS_NR, Date DATA_IKI) {
        this.UZSAKYMO_NR = UZSAKYMO_NR;
        this.KURJERIO_NR = KURJERIO_NR;
        this.SURINKEJO_NR = SURINKEJO_NR;
        this.REGISTRACIJOS_NR = REGISTRACIJOS_NR;
        this.DATA_IKI = DATA_IKI;
    }

    public Integer getUZSAKYMO_NR() {
        return UZSAKYMO_NR;
    }

    public void setUZSAKYMO_NR(Integer UZSAKYMO_NR) {
        this.UZSAKYMO_NR = UZSAKYMO_NR;
    }

    public Integer getKURJERIO_NR() {
        return KURJERIO_NR;
    }

    public void setKURJERIO_NR(Integer KURJERIO_NR) {
        this.KURJERIO_NR = KURJERIO_NR;
    }

    public Integer getSURINKEJO_NR() {
        return SURINKEJO_NR;
    }

    public void setSURINKEJO_NR(Integer SURINKEJO_NR) {
        this.SURINKEJO_NR = SURINKEJO_NR;
    }

    public Integer getREGISTRACIJOS_NR() {
        return REGISTRACIJOS_NR;
    }

    public void setREGISTRACIJOS_NR(Integer REGISTRACIJOS_NR) {
        this.REGISTRACIJOS_NR = REGISTRACIJOS_NR;
    }

    public Date getDATA_IKI() {
        return DATA_IKI;
    }

    public void setDATA_IKI(Date DATA_IKI) {
        this.DATA_IKI = DATA_IKI;
    }
}

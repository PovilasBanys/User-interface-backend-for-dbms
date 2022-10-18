package fxControl;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Detale;
import model.Pirkejas;
import utils.DbOperations;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PirkejasInfo implements Initializable {

//    @FXML
//    private JFXTextField idField;
    @FXML
    private JFXTextField vardasField;
    @FXML
    private JFXTextField pavardeField;
    @FXML
    private JFXTextField dataField;
    @FXML
    private JFXTextField saskaitaField;
    @FXML
    private JFXTextField telefonasField;
    @FXML
    private JFXTextField adresasField;

    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Pirkejas pirkejas = null;

    private boolean update;
    int pirkejasId;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            con = DbOperations.connectToDb();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to database");
            alert.showAndWait();
        }
    }

    @FXML
    public void save(javafx.scene.input.MouseEvent mouseEvent) {
        //connection = DbConnect.getConnect();
        //String DETALES_ID = idField.getText();
        String VARDAS = vardasField.getText();
        String PAVARDE = pavardeField.getText();
        String GIMIMO_DATA = dataField.getText();
        String SASKAITOS_NR = saskaitaField.getText();
        String TELEFONO_NR = telefonasField.getText();
        String ADRESAS = adresasField.getText();

        if (/*DETALES_ID.isEmpty() || */VARDAS.isEmpty() || PAVARDE.isEmpty() || GIMIMO_DATA.isEmpty() || SASKAITOS_NR.isEmpty() || TELEFONO_NR.isEmpty() || ADRESAS.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Klaida. Įveskite visą informaciją");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }
    }

    @FXML
    private void clean() {
//        idField.setText(null);
        vardasField.setText(null);
        pavardeField.setText(null);
        dataField.setText(null);
        saskaitaField.setText(null);
        telefonasField.setText(null);
        adresasField.setText(null);
    }

    private void getQuery() {

        if (update == false) {

//            query = "INSERT INTO DETALE( DETALES_ID, PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?,?)";
            query = "INSERT INTO PIRKEJAS( VARDAS, PAVARDE, GIMIMO_DATA, SASKAITOS_NR, TELEFONO_NR, ADRESAS) VALUES (?,?,?,?,?,?)";

        }else{
            query = "UPDATE PIRKEJAS SET "
    //                + "DETALES_ID=?,"
                    + "VARDAS=?,"
                    + "PAVARDE=?,"
                    + "GIMIMO_DATA=?,"
                    + "SASKAITOS_NR=?,"
                    + "TELEFONO_NR=?,"
                    + "ADRESAS= ? WHERE REGISTRACIJOS_NR = '"+pirkejasId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = con.prepareStatement(query);
   //         preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(1, vardasField.getText());
            preparedStatement.setString(2, pavardeField.getText());
            preparedStatement.setString(3, dataField.getText());
            preparedStatement.setString(4, saskaitaField.getText());
            preparedStatement.setString(5, telefonasField.getText());
            preparedStatement.setString(6, adresasField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(PirkejasInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    void setTextField(Integer REGISTRACIJOS_NR, String VARDAS, String PAVARDE, LocalDate toLocalDate, String SASKAITOS_NR, Integer TELEFONO_NR, String ADRESAS) {
//
//        pirkejasId = REGISTRACIJOS_NR;
////        idField.setText(String.valueOf(DETALES_ID));
//        vardasField.setText(VARDAS);
//        pavardeField.setText(PAVARDE);
//        dataField.setValue(toLocalDate);
//        priceField.setText(String.valueOf(KAINA));
//        quantityField.setText(String.valueOf(KIEKIS_SANDELYJE));
//        typeField.setText(TIPAS);
//
//    }

    void setTextField(Integer REGISTRACIJOS_NR, String VARDAS, String PAVARDE, Date GIMIMO_DATA, String SASKAITOS_NR, Integer TELEFONO_NR, String ADRESAS) {

        pirkejasId = REGISTRACIJOS_NR;
//        idField.setText(String.valueOf(DETALES_ID));
        vardasField.setText(VARDAS);
        pavardeField.setText(PAVARDE);
        dataField.setText(String.valueOf(GIMIMO_DATA));
        saskaitaField.setText(SASKAITOS_NR);
        telefonasField.setText(String.valueOf(TELEFONO_NR));
        adresasField.setText(ADRESAS);


    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

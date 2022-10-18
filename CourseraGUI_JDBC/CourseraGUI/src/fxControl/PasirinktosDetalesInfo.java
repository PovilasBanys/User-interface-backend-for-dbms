package fxControl;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Kurjeris;
import model.PasirinktaDetale;
import utils.DbOperations;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasirinktosDetalesInfo implements Initializable {

    @FXML
    private JFXTextField kiekisField;
    @FXML
    private JFXTextField detalesIdField;
    @FXML
    private JFXTextField uzsakymoNumerisField;
    @FXML
    private JFXTextField surinkimasField;
//    @FXML
//    private JFXTextField kainaField;

    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PasirinktaDetale pasirinktaDetale = null;

    private boolean update;
    int pasirinktaDetaleId;


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

        //String DETALES_ID = idField.getText();
        String KIEKIS = kiekisField.getText();
        String DETALES_ID = detalesIdField.getText();
        String UZSAKYMO_NR = uzsakymoNumerisField.getText();
        String SURINKIMAS = surinkimasField.getText();

        if (/*KURJERIO_NR.isEmpty() || */KIEKIS.isEmpty() || DETALES_ID.isEmpty() || UZSAKYMO_NR.isEmpty() || SURINKIMAS.isEmpty()) {
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
       // numerisField.setText(null);
        kiekisField.setText(null);
        detalesIdField.setText(null);
        uzsakymoNumerisField.setText(null);
        surinkimasField.setText(null);
    }

    private void getQuery() {

        if (update == false) {

//            query = "INSERT INTO DETALE( DETALES_ID, PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?,?)";
            query = "INSERT INTO PASIRINKTOS_DETALES( KIEKIS, DETALES_ID, UZSAKYMO_NR, SURINKIMAS) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE PASIRINKTOS_DETALES SET "
    //                + "KURJERIO_NR=?,"
                    + "KIEKIS=?,"
                    + "DETALES_ID=?,"
                    + "UZSAKYMO_NR=?,"
                    + "SURINKIMAS= ? WHERE PASIRINKTOS_DETALES_ID = '"+pasirinktaDetaleId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = con.prepareStatement(query);
   //         preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(1, kiekisField.getText());
            preparedStatement.setString(2, detalesIdField.getText());
            preparedStatement.setString(3, uzsakymoNumerisField.getText());
            preparedStatement.setString(4, surinkimasField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(PasirinktosDetalesInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Integer KIEKIS, Integer PASIRINKTOS_DETALES_ID, Integer DETALES_ID, Integer UZSAKYMO_NR, String SURINKIMAS) {

        pasirinktaDetaleId = PASIRINKTOS_DETALES_ID;
//        idField.setText(String.valueOf(DETALES_ID));
        kiekisField.setText(String.valueOf(KIEKIS));
        detalesIdField.setText(String.valueOf(DETALES_ID));
        uzsakymoNumerisField.setText(String.valueOf(UZSAKYMO_NR));
        surinkimasField.setText(SURINKIMAS);


    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

package fxControl;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Detale;
import utils.DbOperations;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DetaleInfo implements Initializable {

    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField descriptionField;
    @FXML
    private JFXTextField priceField;
    @FXML
    private JFXTextField quantityField;
    @FXML
    private JFXTextField typeField;

    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Detale detale = null;

    private boolean update;
    int detaleId;


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
        String PAVADINIMAS = nameField.getText();
        String APRASAS = descriptionField.getText();
        String KAINA = priceField.getText();
        String KIEKIS_SANDELYJE = quantityField.getText();
        String TIPAS = typeField.getText();

        if (/*DETALES_ID.isEmpty() || */PAVADINIMAS.isEmpty() || APRASAS.isEmpty() || KAINA.isEmpty() || KIEKIS_SANDELYJE.isEmpty() || TIPAS.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Klaida. Įveskite visą informaciją");
            alert.showAndWait();

        }
//        else if ((KAINA instanceof Integer == false)){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setHeaderText(null);
//            alert.setContentText("Klaida");
//            alert.showAndWait();
//
//        }

        else {
            getQuery();
            insert();
            clean();

        }
    }

    @FXML
    private void clean() {
        idField.setText(null);
        nameField.setText(null);
        descriptionField.setText(null);
        priceField.setText(null);
        quantityField.setText(null);
        typeField.setText(null);
    }

    private void getQuery() {

        if (update == false) {

//            query = "INSERT INTO DETALE( DETALES_ID, PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?,?)";
            query = "INSERT INTO DETALE( PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?)";

        }else{
            query = "UPDATE DETALE SET "
    //                + "DETALES_ID=?,"
                    + "PAVADINIMAS=?,"
                    + "APRASAS=?,"
                    + "KAINA=?,"
                    + "KIEKIS_SANDELYJE=?,"
                    + "TIPAS= ? WHERE DETALES_ID = '"+detaleId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = con.prepareStatement(query);
   //         preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, descriptionField.getText());
            preparedStatement.setString(3, priceField.getText());
            preparedStatement.setString(4, quantityField.getText());
            preparedStatement.setString(5, typeField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DetaleInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Integer DETALES_ID, String PAVADINIMAS, String APRASAS, Float KAINA, Integer KIEKIS_SANDELYJE, String TIPAS) {

        detaleId = DETALES_ID;
//        idField.setText(String.valueOf(DETALES_ID));
        nameField.setText(PAVADINIMAS);
        descriptionField.setText(APRASAS);
        priceField.setText(String.valueOf(KAINA));
        quantityField.setText(String.valueOf(KIEKIS_SANDELYJE));
        typeField.setText(TIPAS);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

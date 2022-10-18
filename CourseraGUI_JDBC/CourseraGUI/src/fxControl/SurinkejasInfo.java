package fxControl;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Surinkejas;
import utils.DbOperations;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SurinkejasInfo implements Initializable {

    @FXML
    private JFXTextField vardasField;
    @FXML
    private JFXTextField pavardeField;
    @FXML
    private JFXTextField telefonasField;
    @FXML
    private JFXTextField aprasymasField;
    @FXML
    private JFXTextField kainaField;

    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Surinkejas surinkejas = null;

    private boolean update;
    int surinkejasId;


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
        String SURINKEJO_VARDAS = vardasField.getText();
        String SURINKEJO_PAVARDE = pavardeField.getText();
        String SURINKEJO_TELEFONAS = telefonasField.getText();
        String SURINKEJO_APRASYMAS = aprasymasField.getText();
        String SURINKEJO_KAINA = kainaField.getText();

        if (/*DETALES_ID.isEmpty() || */SURINKEJO_VARDAS.isEmpty() || SURINKEJO_PAVARDE.isEmpty() || SURINKEJO_TELEFONAS.isEmpty()
                || SURINKEJO_APRASYMAS.isEmpty() || SURINKEJO_KAINA.isEmpty()) {
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
        vardasField.setText(null);
        pavardeField.setText(null);
        telefonasField.setText(null);
        aprasymasField.setText(null);
        kainaField.setText(null);
    }

    private void getQuery() {

        if (update == false) {

//            query = "INSERT INTO DETALE( DETALES_ID, PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?,?)";
            query = "INSERT INTO SURINKEJAS( SURINKEJO_VARDAS, SURINKEJO_PAVARDE, SURINKEJO_TELEFONAS, " +
                    "SURINKEJO_APRASYMAS, SURINKEJO_KAINA) VALUES (?,?,?,?,?)";

        }else{
            query = "UPDATE SURINKEJAS SET "
    //                + "DETALES_ID=?,"
                    + "SURINKEJO_VARDAS=?,"
                    + "SURINKEJO_PAVARDE=?,"
                    + "SURINKEJO_TELEFONAS=?,"
                    + "SURINKEJO_APRASYMAS=?,"
                    + "SURINKEJO_KAINA= ? WHERE SURINKEJO_NR = '"+surinkejasId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = con.prepareStatement(query);
   //         preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(1, vardasField.getText());
            preparedStatement.setString(2, pavardeField.getText());
            preparedStatement.setString(3, telefonasField.getText());
            preparedStatement.setString(4, aprasymasField.getText());
            preparedStatement.setString(5, kainaField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(SurinkejasInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Integer SURINKEJO_NR, String SURINKEJO_VARDAS, String SURINKEJO_PAVARDE, Integer SURINKEJO_TELEFONAS,
                      String SURINKEJO_APRASYMAS, Float SURINKEJO_KAINA) {

        surinkejasId = SURINKEJO_NR;
//        idField.setText(String.valueOf(DETALES_ID));
        vardasField.setText(SURINKEJO_VARDAS);
        pavardeField.setText(SURINKEJO_PAVARDE);
        telefonasField.setText(String.valueOf(SURINKEJO_TELEFONAS));
        aprasymasField.setText(SURINKEJO_APRASYMAS);
        kainaField.setText(String.valueOf(SURINKEJO_KAINA));


    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

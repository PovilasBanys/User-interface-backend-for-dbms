package fxControl;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Detale;
import model.Kurjeris;
import utils.DbOperations;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KurjerisInfo implements Initializable {

    @FXML
    private JFXTextField numerisField;
    @FXML
    private JFXTextField vardasField;
    @FXML
    private JFXTextField pavardeField;
    @FXML
    private JFXTextField telefonasField;
    @FXML
    private JFXTextField kainaField;

    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Kurjeris kurjeris = null;

    private boolean update;
    int kurjerisId;


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
        String KURJERIO_VARDAS = vardasField.getText();
        String KURJERIO_PAVARDE = pavardeField.getText();
       // String KURJERIO_NR = numerisField.getText();
        String KURJERIO_TELEFONAS = telefonasField.getText();
        String KURJERIO_KAINA = kainaField.getText();

        if (/*KURJERIO_NR.isEmpty() || */KURJERIO_VARDAS.isEmpty() || KURJERIO_PAVARDE.isEmpty() || KURJERIO_TELEFONAS.isEmpty() || KURJERIO_KAINA.isEmpty()) {
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
        vardasField.setText(null);
        pavardeField.setText(null);
        telefonasField.setText(null);
        kainaField.setText(null);
    }

    private void getQuery() {

        if (update == false) {

//            query = "INSERT INTO DETALE( DETALES_ID, PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?,?)";
            query = "INSERT INTO KURJERIS( KURJERIO_VARDAS, KURJERIO_PAVARDE, KURJERIO_TELEFONAS, KURJERIO_KAINA) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE KURJERIS SET "
    //                + "KURJERIO_NR=?,"
                    + "KURJERIO_VARDAS=?,"
                    + "KURJERIO_PAVARDE=?,"
                    + "KURJERIO_TELEFONAS=?,"
                    + "KURJERIO_KAINA= ? WHERE KURJERIO_NR = '"+kurjerisId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = con.prepareStatement(query);
   //         preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(1, vardasField.getText());
            preparedStatement.setString(2, pavardeField.getText());
            preparedStatement.setString(3, telefonasField.getText());
            preparedStatement.setString(4, kainaField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(KurjerisInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(String KURJERIO_VARDAS, String KURJERIO_PAVARDE, Integer KURJERIO_NR, Integer KURJERIO_TELEFONAS, Float KURJERIO_KAINA) {

        kurjerisId = KURJERIO_NR;
//        idField.setText(String.valueOf(DETALES_ID));
        vardasField.setText(KURJERIO_VARDAS);
        pavardeField.setText(KURJERIO_PAVARDE);
        telefonasField.setText(String.valueOf(KURJERIO_TELEFONAS));
        kainaField.setText(String.valueOf(KURJERIO_KAINA));


    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

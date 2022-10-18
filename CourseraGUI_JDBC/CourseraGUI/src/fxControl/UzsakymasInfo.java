package fxControl;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Uzsakymas;
import utils.DbOperations;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UzsakymasInfo implements Initializable {

    @FXML
    private JFXTextField kurjerisField;
    @FXML
    private JFXTextField surinkejasField;
    @FXML
    private JFXTextField pirkejasField;
    @FXML
    private JFXTextField dataField;

    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Uzsakymas uzsakymas = null;

    private boolean update;
    int uzsakymasId;


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
        String KURJERIO_NR = kurjerisField.getText();
        String SURINKEJO_NR = surinkejasField.getText();
        String PIRKEJO_NR = pirkejasField.getText();
        String DATA_IKI = dataField.getText();

        if (/*DETALES_ID.isEmpty() || */KURJERIO_NR.isEmpty() || SURINKEJO_NR.isEmpty() || PIRKEJO_NR.isEmpty() || DATA_IKI.isEmpty()) {
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
        kurjerisField.setText(null);
        surinkejasField.setText(null);
        pirkejasField.setText(null);
        dataField.setText(null);

    }

    private void getQuery() {

        if (update == false) {

//            query = "INSERT INTO DETALE( DETALES_ID, PAVADINIMAS, APRASAS, KAINA, KIEKIS_SANDELYJE, TIPAS) VALUES (?,?,?,?,?,?)";
            query = "INSERT INTO UZSAKYMAS( KURJERIO_NR, SURINKEJO_NR, REGISTRACIJOS_NR, DATA_IKI) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE UZSAKYMAS SET "
    //                + "DETALES_ID=?,"
                    + "KURJERIO_NR=?,"
                    + "SURINKEJO_NR=?,"
                    + "REGISTRACIJOS_NR=?,"
                    + "DATA_IKI= ? WHERE UZSAKYMO_NR = '"+uzsakymasId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = con.prepareStatement(query);
   //         preparedStatement.setString(1, idField.getText());
            preparedStatement.setString(1, kurjerisField.getText());
            preparedStatement.setString(2, surinkejasField.getText());
            preparedStatement.setString(3, pirkejasField.getText());
            preparedStatement.setString(4, dataField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(UzsakymasInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Integer UZSAKYMO_NR, Integer KURJERIO_NR, Integer SURINKEJO_NR, Integer REGISTRACIJOS_NR, Date DATA_IKI) {

        uzsakymasId = UZSAKYMO_NR;
//        idField.setText(String.valueOf(DETALES_ID));
        kurjerisField.setText(String.valueOf(KURJERIO_NR));
        surinkejasField.setText(String.valueOf(SURINKEJO_NR));
        pirkejasField.setText(String.valueOf(REGISTRACIJOS_NR));
        dataField.setText(String.valueOf(DATA_IKI));


    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}

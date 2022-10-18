package fxControl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.DbOperations;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

//import java.awt.event.ActionEvent;

public class MainPage implements Initializable {

    // Main window
    @FXML private ListView<String> allCustomersListView;
    @FXML private ComboBox customers;
   // public ComboBox customers;

    private Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            con = DbOperations.connectToDb();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to database");
            alert.showAndWait();
        }

    }


    public void loadCustomers(ActionEvent actionEvent) {
        String query = "SELECT * FROM [db teisinga su data].INFORMATION_SCHEMA.TABLES";
        try {
            Statement st = con.createStatement();
            ResultSet result = st.executeQuery(query);
            ObservableList data = FXCollections.observableArrayList();

            while (result.next()) {

                data.add(new String(result.getString(3)));
            }
            customers.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainPageNext (ActionEvent actionEvent) throws IOException {

        if (customers.getValue().equals("KURJERIS")) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/KurjerisPage.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else if (customers.getValue().equals("DETALE")) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/DetalePage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/DetalePage.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

       }

        else if (customers.getValue().equals("PASIRINKTOS_DETALES")) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/PasirinktosDetalesPage.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else if (customers.getValue().equals("PIRKEJAS")) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/PirkejasPage.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else if (customers.getValue().equals("SURINKEJAS")) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/SurinkejasPage.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else if (customers.getValue().equals("UZSAKYMAS")) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

            Parent root = FXMLLoader.load(getClass().getResource("../fxml/UzsakymasPage.fxml"));
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

}


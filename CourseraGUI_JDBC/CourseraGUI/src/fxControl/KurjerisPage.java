package fxControl;

//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.util.Callback;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import model.Detale;
import model.Kurjeris;
import utils.DbOperations;

import java.awt.*;
import java.awt.event.MouseEvent;
import javafx.geometry.Insets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;



import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import helpers.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jdk.nashorn.internal.objects.annotations.Property;

public class KurjerisPage implements Initializable {

    @FXML private TableView<Kurjeris> kurjerisTable;
    @FXML private TableColumn<Kurjeris, String>  kurjerisNrCol;
    @FXML private TableColumn<Kurjeris, String>  vardasCol;
    @FXML private TableColumn<Kurjeris, String>  pavardeCol;
    @FXML private TableColumn<Kurjeris, String>  telefonasCol;
    @FXML private TableColumn<Kurjeris, String>  kainaCol;
    @FXML private TableColumn<Kurjeris, String>  editCol;

    @FXML private ChoiceBox kainaChoiceBox;

    @FXML private TextField searchField;

private String[] kainaChoice = {"5.99", "6.99", "7.5", "6.5", "5.7", "visos kainos"};


    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Kurjeris kurjeris = null;

    ObservableList<Kurjeris> KurjerisList = FXCollections.observableArrayList();

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

        kainaChoiceBox.getItems().addAll(kainaChoice);

    loadKurjeris();

    }


    private void refreshTable() {
        try {
            KurjerisList.clear();

            query = "SELECT * FROM KURJERIS ";

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                KurjerisList.add(new Kurjeris(
                        resultSet.getString("KURJERIO_VARDAS"),
                        resultSet.getString("KURJERIO_PAVARDE"),
                        resultSet.getInt("KURJERIO_NR"),
                        resultSet.getInt("KURJERIO_TELEFONAS"),
                        resultSet.getFloat("KURJERIO_KAINA")
                ));

                kurjerisTable.setItems(KurjerisList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(KurjerisPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadKurjeris() {

        refreshTable();

        vardasCol.setCellValueFactory(new PropertyValueFactory<>("KURJERIO_VARDAS"));
        pavardeCol.setCellValueFactory(new PropertyValueFactory<>("KURJERIO_PAVARDE"));
        kurjerisNrCol.setCellValueFactory(new PropertyValueFactory<>("KURJERIO_NR"));
        telefonasCol.setCellValueFactory(new PropertyValueFactory<>("KURJERIO_TELEFONAS"));
        kainaCol.setCellValueFactory(new PropertyValueFactory<>("KURJERIO_KAINA"));

        //add cell of button edit
        Callback<TableColumn<Kurjeris, String>, TableCell<Kurjeris, String>> cellFoctory = (TableColumn<Kurjeris, String> param) -> {
            // make cell containing buttons
            final TableCell<Kurjeris, String> cell = new TableCell<Kurjeris, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );


                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                            try {
                                kurjeris = kurjerisTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM KURJERIS WHERE KURJERIO_NR  ="+kurjeris.getKURJERIO_NR();
                                con = DbOperations.connectToDb();
                                preparedStatement = con.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(KurjerisPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });


                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            kurjeris = kurjerisTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/fxml/KurjerisInfo.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(KurjerisPage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            KurjerisInfo kurjerisInfo = loader.getController();
                            kurjerisInfo.setUpdate(true);
                            kurjerisInfo.setTextField(kurjeris.getKURJERIO_VARDAS(), kurjeris.getKURJERIO_PAVARDE(),
                                    kurjeris.getKURJERIO_NR(),kurjeris.getKURJERIO_TELEFONAS(), kurjeris.getKURJERIO_KAINA());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        kurjerisTable.setItems(KurjerisList);
    }

//
//    public void ackBtn(ActionEvent actionEvent) throws IOException {
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
////        Parent root = loader.load();
////        Stage stage = new Stage();
////        stage.setScene(new Scene(root));
////        stage.show();
//
//        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
//        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void ewBtn(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisInfo.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();
//    }
//
//
//    public void efreshBtn(ActionEvent actionEvent) throws SQLException {
//
//        KurjerisList.clear();
//        query = "SELECT * FROM KURJERIS";
//        preparedStatement = con.prepareStatement(query);
//        resultSet = preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            KurjerisList.add(new Kurjeris(
//                    resultSet.getString("KURJERIO_VARDAS"),
//                    resultSet.getString("KURJERIO_PAVARDE"),
//                    resultSet.getInt("KURJERIO_NR"),
//                    resultSet.getInt("KURJERIO_TELEFONAS"),
//                    resultSet.getFloat("KURJERIO_KAINA")
//            ));
//
//            kurjerisTable.setItems(KurjerisList);
//        }
//    }

    public void earch(ActionEvent actionEvent) {

        try {
            KurjerisList.clear();

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (kainaChoiceBox.getValue() == "5.99"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '5.99' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }
                else if (kainaChoiceBox.getValue() == "6.99"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '6.99' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "7.5"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '7.5' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "6.5"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '6.5' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "5.7"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '5.7' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "visos kainos"){
                    query = "SELECT * FROM KURJERIS ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(KurjerisPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void backBtn(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void newBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/KurjerisInfo.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void refreshBtn(ActionEvent actionEvent) throws SQLException {
        KurjerisList.clear();
        query = "SELECT * FROM KURJERIS";
        preparedStatement = con.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            KurjerisList.add(new Kurjeris(
                    resultSet.getString("KURJERIO_VARDAS"),
                    resultSet.getString("KURJERIO_PAVARDE"),
                    resultSet.getInt("KURJERIO_NR"),
                    resultSet.getInt("KURJERIO_TELEFONAS"),
                    resultSet.getFloat("KURJERIO_KAINA")
            ));

            kurjerisTable.setItems(KurjerisList);
        }
    }

//    public void loadCustomers(ActionEvent actionEvent) {
//        String query = "SELECT * FROM [db teisinga su data].INFORMATION_SCHEMA.TABLES";
//        try {
//            Statement st = con.createStatement();
//            ResultSet result = st.executeQuery(query);
//            ObservableList data = FXCollections.observableArrayList();
//
//            while (result.next()) {
//
//                data.add(new String(result.getString(3)));
//            }
//            customers.setItems(data);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void search(ActionEvent actionEvent) {

        try {
            KurjerisList.clear();

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (kainaChoiceBox.getValue() == "5.99"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '5.99' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }
                else if (kainaChoiceBox.getValue() == "6.99"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '6.99' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "7.5"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '7.5' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "6.5"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '6.5' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "5.7"){
                    query = "SELECT * FROM KURJERIS WHERE KURJERIO_KAINA = '5.7' ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

                else if (kainaChoiceBox.getValue() == "visos kainos"){
                    query = "SELECT * FROM KURJERIS ";
                    KurjerisList.add(new Kurjeris(
                            resultSet.getString("KURJERIO_VARDAS"),
                            resultSet.getString("KURJERIO_PAVARDE"),
                            resultSet.getInt("KURJERIO_NR"),
                            resultSet.getInt("KURJERIO_TELEFONAS"),
                            resultSet.getFloat("KURJERIO_KAINA")
                    ));

                    kurjerisTable.setItems(KurjerisList);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(KurjerisPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML private void report() throws DocumentException, IOException, SQLException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("Kurjeris.pdf"));
        doc.open();

        Paragraph paragraph = new Paragraph("Kurjeriu ataskaita");
        doc.add(paragraph);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));

//        String query="select * from DETALE";
//        preparedStatement=con.prepareStatement(query);
//        resultSet=preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            Paragraph para=new Paragraph(resultSet.getString("DETALES_ID")
//                    +" "+resultSet.getString("PAVADINIMAS")
//                    +" "+resultSet.getString("APRASAS")
//                    +" "+resultSet.getString("KAINA")
//                    +" "+resultSet.getString("KIEKIS_SANDELYJE")
//                    +" "+resultSet.getString("TIPAS"));
//            doc.add(para);
//            doc.add(new Paragraph(" "));
//        }

//        String query1="select SUM(KIEKIS_SANDELYJE) as kiekis from DETALE";
//        preparedStatement=con.prepareStatement(query1);
//        resultSet=preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            Paragraph para1 = new Paragraph(resultSet.getString("kiekis"));
//            doc.add(para1);
//            doc.add(new Paragraph(" "));
//        }
//
//        String query2=" select DETALE.DETALES_ID as dd, DETALE.PAVADINIMAS as dp, PASIRINKTOS_DETALES.KIEKIS as pdk,\n" +
//                "                UZSAKYMAS.UZSAKYMO_NR as uu\n" +
//                "        from DETALE\n" +
//                "        INNER JOIN PASIRINKTOS_DETALES on DETALE.DETALES_ID=PASIRINKTOS_DETALES.DETALES_ID\n" +
//                "        INNER JOIN UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR=UZSAKYMAS.UZSAKYMO_NR;";
//        preparedStatement=con.prepareStatement(query2);
//        resultSet=preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            Paragraph para2=new Paragraph(resultSet.getString("dd")
//                    +" "+resultSet.getString("dp")
//                    +" "+resultSet.getString("pdk")
//                    +" "+resultSet.getString("uu"));
//            doc.add(para2);
//            doc.add(new Paragraph(" "));
//        }





        PdfPTable table1 = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("Kurjerio nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Vardas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Pavarde"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Telefonas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Kaina"));
        table1.addCell(c1);

        table1.setHeaderRows(1);

        String query1="select * from KURJERIS";
        preparedStatement=con.prepareStatement(query1);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table1.addCell(resultSet.getString("KURJERIO_NR"));
            table1.addCell(resultSet.getString("KURJERIO_VARDAS"));
            table1.addCell(resultSet.getString("KURJERIO_PAVARDE"));
            table1.addCell(resultSet.getString("KURJERIO_TELEFONAS"));
            table1.addCell(resultSet.getString("KURJERIO_KAINA"));
        }

        Paragraph paragraph1 = new Paragraph("Kurjeriu sarasas");
        doc.add(paragraph1);
        doc.add(new Paragraph(" "));

        doc.add(table1);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table2 = new PdfPTable(8);

        PdfPCell c2 = new PdfPCell(new Phrase("Kurjerio nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Kurjerio vardas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Kurjerio pavarde"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales ID"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Kiekis"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Pabaigos data"));
        table2.addCell(c2);

        table2.setHeaderRows(1);


        String query2="select KURJERIS.KURJERIO_NR as kkn, KURJERIS.KURJERIO_VARDAS as kkv, KURJERIS.KURJERIO_PAVARDE as kkp, UZSAKYMAS.UZSAKYMO_NR as uun,\n" +
                "DETALE.DETALES_ID as ddi, DETALE.PAVADINIMAS as dp, PASIRINKTOS_DETALES.KIEKIS as pdk, UZSAKYMAS.DATA_IKI as ud\n" +
                "from DETALE\n" +
                "inner join PASIRINKTOS_DETALES on DETALE.DETALES_ID = PASIRINKTOS_DETALES.DETALES_ID\n" +
                "inner join UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR = UZSAKYMAS.UZSAKYMO_NR\n" +
                "inner join KURJERIS on UZSAKYMAS.KURJERIO_NR = KURJERIS.KURJERIO_NR\n" +
                "order by KURJERIS.KURJERIO_NR";
        preparedStatement=con.prepareStatement(query2);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table2.addCell(resultSet.getString("kkn"));
            table2.addCell(resultSet.getString("kkv"));
            table2.addCell(resultSet.getString("kkp"));
            table2.addCell(resultSet.getString("uun"));
            table2.addCell(resultSet.getString("ddi"));
            table2.addCell(resultSet.getString("dp"));
            table2.addCell(resultSet.getString("pdk"));
            table2.addCell(resultSet.getString("ud"));

        }

        Paragraph paragraph2 = new Paragraph("Kurjeriui priklausantis pirkejas, uzsakymas, detales ir baigimo datos");
        doc.add(paragraph2);
        doc.add(new Paragraph(" "));

        doc.add(table2);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));



        String query5="select count(KURJERIS.KURJERIO_NR) as kkn\n" +
                "from UZSAKYMAS\n" +
                "inner join KURJERIS on UZSAKYMAS.KURJERIO_NR = KURJERIS.KURJERIO_NR ";
        preparedStatement=con.prepareStatement(query5);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            Paragraph para=new Paragraph("Visu gabenimu skaicius:" + " " + resultSet.getString("kkn"));
            doc.add(para);
            doc.add(new Paragraph(" "));
        }


        doc.close();

        File myFile = new File("C:\\Users\\Dalius\\Desktop\\final mssql nuo nulio old jdk\\CourseraGUI_JDBC\\Kurjeris.pdf");
        Desktop.getDesktop().open(myFile);

    }
}

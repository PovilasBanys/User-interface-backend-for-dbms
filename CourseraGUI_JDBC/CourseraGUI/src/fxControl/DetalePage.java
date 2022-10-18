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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Detale;
import utils.DbOperations;

import java.awt.*;
import java.awt.event.MouseEvent;
import javafx.geometry.Insets;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

public class DetalePage implements Initializable {

    @FXML private TableView<Detale> detailsTable;
    @FXML private TableColumn<Detale, String>  detailIdCol;
    @FXML private TableColumn<Detale, String>  nameCol;
    @FXML private TableColumn<Detale, String>  descriptionCol;
    @FXML private TableColumn<Detale, String>  priceCol;
    @FXML private TableColumn<Detale, String>  quantityCol;
    @FXML private TableColumn<Detale, String>  typeCol;
    @FXML private TableColumn<Detale, String>  editCol;

    @FXML private ChoiceBox typeChoiceBox;

    @FXML private TextField searchField;

private String[] typeChoice = {"privaloma", "neprivaloma", "visi tipai"};


    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Detale detale = null;

    ObservableList<Detale> DetailList = FXCollections.observableArrayList();


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

        typeChoiceBox.getItems().addAll(typeChoice);


//        FilteredList<Detale> filteredData = new FilteredList<>(DetailList, b -> true);
//
//        // 2. Set the filter Predicate whenever the filter changes.
//        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//            filteredData.setPredicate(detale -> {
//                // If filter text is empty, display all persons.
//
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//
//                // Compare first name and last name of every person with filter text.
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (detale.getTIPAS().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
//                    return true; // Filter matches first name.
//                }
//                else
//                    return false; // Does not match.
//            });
//        });
//
//        // 3. Wrap the FilteredList in a SortedList.
//        SortedList<Detale> sortedData = new SortedList<>(filteredData);
//
//        // 4. Bind the SortedList comparator to the TableView comparator.
//        // 	  Otherwise, sorting the TableView would have no effect.
//        sortedData.comparatorProperty().bind(detailsTable.comparatorProperty());
//
//        // 5. Add sorted (and filtered) data to the table.
//        detailsTable.setItems(sortedData);






//







//        searchDetale();


    loadDetails();
//        try {
//            report();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }



//    @FXML
//    void searchDetale(){
//        FilteredList<Detale> flDetale = new FilteredList(DetailList, p -> true);//Pass the data to a filtered list
//        detailsTable.setItems(flDetale);//Set the table's items using the filtered list
//        detailsTable.getColumns().addAll();
//
//        //Adding ChoiceBox and TextField here!
//        typeChoiceBox.getItems().addAll();
//        typeChoiceBox.setValue("PAVADINIMAS");
//
//
//        searchField.setPromptText("Search here!");
//        searchField.textProperty().addListener((obs, oldValue, newValue) -> {
//            Object value = typeChoiceBox.getValue();//Switch on choiceBox value
////                case "DETALES_ID":
////                    flDetale.setPredicate(p -> p.getDETALES_ID().contains(newValue.toLowerCase().trim()));//filter table by first name
////                    break;
//            if ("PAVADINIMAS".equals(value)) {
//                flDetale.setPredicate(p -> p.getPAVADINIMAS().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by last name
//            } else if ("APRASAS".equals(value)) {
//                flDetale.setPredicate(p -> p.getAPRASAS().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by email
//            }
//        });
//
//        typeChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
//                -> {//reset table and textfield when new choice is selected
//            if (newVal != null) {
//                searchField.setText("");
//            }
//        });
//    }

//    @FXML
//    private void refreshTable() {
//        try {
//            DetailList.clear();
//
//            query = "SELECT * FROM DETALE ";
//
//            preparedStatement = con.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()){
//                DetailList.add(new Detale(
//                        resultSet.getInt("DETALES_ID"),
//                        resultSet.getString("PAVADINIMAS"),
//                        resultSet.getString("APRASAS"),
//                        resultSet.getFloat("KAINA"),
//                        resultSet.getInt("KIEKIS_SANDELYJE"),
//                        resultSet.getString("TIPAS")
//                ));
//
//                detailsTable.setItems(DetailList);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(DetalePage.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }


    private void refreshTable() {
        try {
            DetailList.clear();

            query = "SELECT * FROM DETALE ";

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                    DetailList.add(new Detale(
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getString("PAVADINIMAS"),
                            resultSet.getString("APRASAS"),
                            resultSet.getFloat("KAINA"),
                            resultSet.getInt("KIEKIS_SANDELYJE"),
                            resultSet.getString("TIPAS")
                    ));

                detailsTable.setItems(DetailList);

        }

        } catch (SQLException ex) {
            Logger.getLogger(DetalePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void search() {
        try {
            DetailList.clear();

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (typeChoiceBox.getValue() == "privaloma"){
                    query = "SELECT * FROM DETALE WHERE TIPAS = 'privaloma' ";
                    DetailList.add(new Detale(
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getString("PAVADINIMAS"),
                            resultSet.getString("APRASAS"),
                            resultSet.getFloat("KAINA"),
                            resultSet.getInt("KIEKIS_SANDELYJE"),
                            resultSet.getString("TIPAS")
                    ));

                    detailsTable.setItems(DetailList);
                }
               else if (typeChoiceBox.getValue() == "neprivaloma"){
                    query = "SELECT * FROM DETALE WHERE TIPAS = 'neprivaloma' ";
                    DetailList.add(new Detale(
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getString("PAVADINIMAS"),
                            resultSet.getString("APRASAS"),
                            resultSet.getFloat("KAINA"),
                            resultSet.getInt("KIEKIS_SANDELYJE"),
                            resultSet.getString("TIPAS")
                    ));

                    detailsTable.setItems(DetailList);
                }
                else if (typeChoiceBox.getValue() == "visi tipai"){
                    query = "SELECT * FROM DETALE ";
                    DetailList.add(new Detale(
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getString("PAVADINIMAS"),
                            resultSet.getString("APRASAS"),
                            resultSet.getFloat("KAINA"),
                            resultSet.getInt("KIEKIS_SANDELYJE"),
                            resultSet.getString("TIPAS")
                    ));

                    detailsTable.setItems(DetailList);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(DetalePage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private void loadDetails() {

        refreshTable();

        detailIdCol.setCellValueFactory(new PropertyValueFactory<>("DETALES_ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("PAVADINIMAS"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("APRASAS"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("KAINA"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("KIEKIS_SANDELYJE"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("TIPAS"));

        //add cell of button edit
        Callback<TableColumn<Detale, String>, TableCell<Detale, String>> cellFoctory = (TableColumn<Detale, String> param) -> {
            // make cell containing buttons
            final TableCell<Detale, String> cell = new TableCell<Detale, String>() {
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
                                detale = detailsTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM DETALE WHERE DETALES_ID  ="+detale.getDETALES_ID();
                                con = DbOperations.connectToDb();
                                preparedStatement = con.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(DetalePage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });





//                        editIcon.setOnMouseClicked((MouseEvent event) -> {
//
//                            detale = detailsTable.getSelectionModel().getSelectedItem();
//                            FXMLLoader loader = new FXMLLoader ();
//                            loader.setLocation(getClass().getResource("/fxml/DetaleInfo.fxml"));
//                            try {
//                                loader.load();
//                            } catch (IOException ex) {
//                                Logger.getLogger(DetalePage.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//
//                            DetaleInfo detaleInfo = loader.getController();
//                            detaleInfo.setUpdate(true);
//                            detaleInfo.setTextField(detale.getDETALES_ID(), detale.getPAVADINIMAS(),
//                                    detale.getAPRASAS(),detale.getKAINA(), detale.getKIEKIS_SANDELYJE(), detale.getTIPAS());
//                            Parent parent = loader.getRoot();
//                            Stage stage = new Stage();
//                            stage.setScene(new Scene(parent));
//                            stage.initStyle(StageStyle.UTILITY);
//                            stage.show();
//
//                        });




                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            detale = detailsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/fxml/DetaleInfo.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(DetalePage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            DetaleInfo detaleInfo = loader.getController();
                            detaleInfo.setUpdate(true);
                            detaleInfo.setTextField(detale.getDETALES_ID(), detale.getPAVADINIMAS(),
                                    detale.getAPRASAS(),detale.getKAINA(), detale.getKIEKIS_SANDELYJE(), detale.getTIPAS());
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
        detailsTable.setItems(DetailList);
    }



    public void newBtn (ActionEvent actionEvent) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/DetaleInfo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/tableView/addStudent.fxml"));
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initStyle(StageStyle.UTILITY);
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

  @FXML  private void refreshBtn (ActionEvent actionEvent) throws IOException, SQLException {

            DetailList.clear();
            query = "SELECT * FROM DETALE";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                DetailList.add(new Detale(
                        resultSet.getInt("DETALES_ID"),
                        resultSet.getString("PAVADINIMAS"),
                        resultSet.getString("APRASAS"),
                        resultSet.getFloat("KAINA"),
                        resultSet.getInt("KIEKIS_SANDELYJE"),
                        resultSet.getString("TIPAS")
                        ));

                detailsTable.setItems(DetailList);
            }
    }


    public void backBtn (ActionEvent actionEvent) throws IOException {

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void search (ActionEvent actionEvent) throws IOException {
//
//
//
//    }

    @FXML private void report() throws DocumentException, IOException, SQLException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("Detale.pdf"));
        doc.open();

        Paragraph paragraph = new Paragraph("Detaliu ataskaita");
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





        PdfPTable table1 = new PdfPTable(6);

        PdfPCell c1 = new PdfPCell(new Phrase("Detales id"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Pavadinimas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Aprasas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Kaina"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Kiekis sandelyje"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Tipas"));
        table1.addCell(c1);

        table1.setHeaderRows(1);

        String query1="select * from DETALE";
        preparedStatement=con.prepareStatement(query1);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table1.addCell(resultSet.getString("DETALES_ID"));
            table1.addCell(resultSet.getString("PAVADINIMAS"));
            table1.addCell(resultSet.getString("APRASAS"));
            table1.addCell(resultSet.getString("KAINA"));
            table1.addCell(resultSet.getString("KIEKIS_SANDELYJE"));
            table1.addCell(resultSet.getString("TIPAS"));
        }

        Paragraph paragraph1 = new Paragraph("Detaliu sarasas");
        doc.add(paragraph1);
        doc.add(new Paragraph(" "));

        doc.add(table1);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table2 = new PdfPTable(3);

        PdfPCell c2 = new PdfPCell(new Phrase("Detales id"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Uzsakymo numeris"));
        table2.addCell(c2);

        table2.setHeaderRows(1);


        String query2="select DETALE.DETALES_ID as ddi, DETALE.PAVADINIMAS as dp, UZSAKYMAS.UZSAKYMO_NR as un\n" +
                "from DETALE\n" +
                "inner join PASIRINKTOS_DETALES on DETALE.DETALES_ID = PASIRINKTOS_DETALES.DETALES_ID\n" +
                "inner join UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR = UZSAKYMAS.UZSAKYMO_NR\n" +
                "order by ddi;";
        preparedStatement=con.prepareStatement(query2);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table2.addCell(resultSet.getString("ddi"));
            table2.addCell(resultSet.getString("dp"));
            table2.addCell(resultSet.getString("un"));

        }

        Paragraph paragraph2 = new Paragraph("Detaliu priklausymas uzsakymams");
        doc.add(paragraph2);
        doc.add(new Paragraph(" "));

        doc.add(table2);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table3 = new PdfPTable(5);

        PdfPCell c3 = new PdfPCell(new Phrase("Detales id"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Surinkejo numeris"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Surinkejo pavarde"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Surinkejo vardas"));
        table3.addCell(c3);

        table3.setHeaderRows(1);


        String query3="select DETALE.DETALES_ID as ddi, DETALE.PAVADINIMAS as dp, SURINKEJAS.SURINKEJO_NR as ssn,\n" +
                "SURINKEJAS.SURINKEJO_PAVARDE as ssp,SURINKEJAS.SURINKEJO_VARDAS as ssv\n" +
                "from DETALE\n" +
                "inner join PASIRINKTOS_DETALES on DETALE.DETALES_ID = PASIRINKTOS_DETALES.DETALES_ID\n" +
                "inner join UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR = UZSAKYMAS.UZSAKYMO_NR\n" +
                "inner join SURINKEJAS on UZSAKYMAS.SURINKEJO_NR = SURINKEJAS.SURINKEJO_NR\n" +
                "order by ddi;";
        preparedStatement=con.prepareStatement(query3);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table3.addCell(resultSet.getString("ddi"));
            table3.addCell(resultSet.getString("dp"));
            table3.addCell(resultSet.getString("ssn"));
            table3.addCell(resultSet.getString("ssp"));
            table3.addCell(resultSet.getString("ssv"));

        }

        Paragraph paragraph3 = new Paragraph("Detaliu priklausymas surinkejams");
        doc.add(paragraph3);
        doc.add(new Paragraph(" "));

        doc.add(table3);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));



        PdfPTable table4 = new PdfPTable(5);

        PdfPCell c4 = new PdfPCell(new Phrase("Detales id"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("Kurjerio numeris"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("Kurjerio pavarde"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("Kurjerio vardas"));
        table4.addCell(c4);

        table4.setHeaderRows(1);


        String query4="select DETALE.DETALES_ID as ddi, DETALE.PAVADINIMAS as dp, KURJERIS.KURJERIO_NR as kkn,\n" +
                "KURJERIS.KURJERIO_PAVARDE as kkp ,KURJERIS.KURJERIO_VARDAS as kkv\n" +
                "from DETALE\n" +
                "inner join PASIRINKTOS_DETALES on DETALE.DETALES_ID = PASIRINKTOS_DETALES.DETALES_ID\n" +
                "inner join UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR = UZSAKYMAS.UZSAKYMO_NR\n" +
                "inner join KURJERIS on UZSAKYMAS.KURJERIO_NR = KURJERIS.KURJERIO_NR\n" +
                "order by ddi;";
        preparedStatement=con.prepareStatement(query4);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table4.addCell(resultSet.getString("ddi"));
            table4.addCell(resultSet.getString("dp"));
            table4.addCell(resultSet.getString("kkn"));
            table4.addCell(resultSet.getString("kkp"));
            table4.addCell(resultSet.getString("kkv"));

        }

        Paragraph paragraph4 = new Paragraph("Detaliu priklausymas kurjeriams");
        doc.add(paragraph4);
        doc.add(new Paragraph(" "));

        doc.add(table4);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));



        String query5="select COUNT(DETALE.DETALES_ID) as ddi\n" +
                "from DETALE";
        preparedStatement=con.prepareStatement(query5);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            Paragraph para=new Paragraph("Detaliu rusiu skaicius:" + " " + resultSet.getString("ddi"));
            doc.add(para);
            doc.add(new Paragraph(" "));
        }


        doc.close();

        File myFile = new File("C:\\Users\\Dalius\\Desktop\\final mssql nuo nulio old jdk\\CourseraGUI_JDBC\\Detale.pdf");
        Desktop.getDesktop().open(myFile);

    }

}

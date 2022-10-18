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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Detale;
import model.Pirkejas;
import utils.DbOperations;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PirkejasPage implements Initializable {

    @FXML private TableView<Pirkejas> pirkejuTable;
    @FXML private TableColumn<Pirkejas, String>  registracijosNumerisCol;
    @FXML private TableColumn<Pirkejas, String>  vardasCol;
    @FXML private TableColumn<Pirkejas, String>  pavardeCol;
    @FXML private TableColumn<Pirkejas, String>  dataCol;
    @FXML private TableColumn<Pirkejas, String>  saskaitaCol;
    @FXML private TableColumn<Pirkejas, String>  telefonasCol;
    @FXML private TableColumn<Pirkejas, String>  adresasCol;
    @FXML private TableColumn<Pirkejas, String>  editCol;

    //@FXML private ChoiceBox typeChoiceBox;

    @FXML private TextField searchField;

//private String[] typeChoice = {"privaloma", "neprivaloma", "visi tipai"};


    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Pirkejas pirkejas = null;

    ObservableList<Pirkejas> PirkejasList = FXCollections.observableArrayList();


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

//        typeChoiceBox.getItems().addAll(typeChoice);


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

    }




    private void refreshTable() {
        try {
            PirkejasList.clear();

            query = "SELECT * FROM PIRKEJAS ";

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                    PirkejasList.add(new Pirkejas(
                            resultSet.getInt("REGISTRACIJOS_NR"),
                            resultSet.getString("VARDAS"),
                            resultSet.getString("PAVARDE"),
                            resultSet.getDate("GIMIMO_DATA"),
                            resultSet.getString("SASKAITOS_NR"),
                            resultSet.getInt("TELEFONO_NR"),
                            resultSet.getString("ADRESAS")
                    ));

                pirkejuTable.setItems(PirkejasList);

        }

        } catch (SQLException ex) {
            Logger.getLogger(PirkejasPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


//    @FXML
//    private void search() {
//        try {
//            DetailList.clear();
//
//            preparedStatement = con.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                if (typeChoiceBox.getValue() == "privaloma"){
//                    query = "SELECT * FROM DETALE WHERE TIPAS = 'privaloma' ";
//                    DetailList.add(new Detale(
//                            resultSet.getInt("DETALES_ID"),
//                            resultSet.getString("PAVADINIMAS"),
//                            resultSet.getString("APRASAS"),
//                            resultSet.getFloat("KAINA"),
//                            resultSet.getInt("KIEKIS_SANDELYJE"),
//                            resultSet.getString("TIPAS")
//                    ));
//
//                    detailsTable.setItems(DetailList);
//                }
//               else if (typeChoiceBox.getValue() == "neprivaloma"){
//                    query = "SELECT * FROM DETALE WHERE TIPAS = 'neprivaloma' ";
//                    DetailList.add(new Detale(
//                            resultSet.getInt("DETALES_ID"),
//                            resultSet.getString("PAVADINIMAS"),
//                            resultSet.getString("APRASAS"),
//                            resultSet.getFloat("KAINA"),
//                            resultSet.getInt("KIEKIS_SANDELYJE"),
//                            resultSet.getString("TIPAS")
//                    ));
//
//                    detailsTable.setItems(DetailList);
//                }
//                else if (typeChoiceBox.getValue() == "visi tipai"){
//                    query = "SELECT * FROM DETALE ";
//                    DetailList.add(new Detale(
//                            resultSet.getInt("DETALES_ID"),
//                            resultSet.getString("PAVADINIMAS"),
//                            resultSet.getString("APRASAS"),
//                            resultSet.getFloat("KAINA"),
//                            resultSet.getInt("KIEKIS_SANDELYJE"),
//                            resultSet.getString("TIPAS")
//                    ));
//
//                    detailsTable.setItems(DetailList);
//                }
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(PirkejasPage.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }



    private void loadDetails() {

        refreshTable();

        registracijosNumerisCol.setCellValueFactory(new PropertyValueFactory<>("REGISTRACIJOS_NR"));
        vardasCol.setCellValueFactory(new PropertyValueFactory<>("VARDAS"));
        pavardeCol.setCellValueFactory(new PropertyValueFactory<>("PAVARDE"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("GIMIMO_DATA"));
        saskaitaCol.setCellValueFactory(new PropertyValueFactory<>("SASKAITOS_NR"));
        telefonasCol.setCellValueFactory(new PropertyValueFactory<>("TELEFONO_NR"));
        adresasCol.setCellValueFactory(new PropertyValueFactory<>("ADRESAS"));

        //add cell of button edit
        Callback<TableColumn<Pirkejas, String>, TableCell<Pirkejas, String>> cellFoctory = (TableColumn<Pirkejas, String> param) -> {
            // make cell containing buttons
            final TableCell<Pirkejas, String> cell = new TableCell<Pirkejas, String>() {
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
                                pirkejas = pirkejuTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM PIRKEJAS WHERE REGISTRACIJOS_NR  ="+pirkejas.getREGISTRACIJOS_NR();
                                con = DbOperations.connectToDb();
                                preparedStatement = con.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(PirkejasPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });



                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            pirkejas = pirkejuTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/fxml/PirkejasInfo.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(PirkejasPage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            PirkejasInfo pirkejasInfo = loader.getController();
                            pirkejasInfo.setUpdate(true);
                            pirkejasInfo.setTextField(pirkejas.getREGISTRACIJOS_NR(), pirkejas.getVARDAS(),
                                    pirkejas.getPAVARDE(),pirkejas.getGIMIMO_DATA(), pirkejas.getSASKAITOS_NR(), pirkejas.getTELEFONO_NR(),
                                    pirkejas.getADRESAS());
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
        pirkejuTable.setItems(PirkejasList);
    }



    public void newBtn (ActionEvent actionEvent) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/PirkejasInfo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
    }

  @FXML  private void refreshBtn (ActionEvent actionEvent) throws IOException, SQLException {

            PirkejasList.clear();
            query = "SELECT * FROM PIRKEJAS";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                PirkejasList.add(new Pirkejas(
                        resultSet.getInt("REGISTRACIJOS_NR"),
                        resultSet.getString("VARDAS"),
                        resultSet.getString("PAVARDE"),
                        resultSet.getDate("GIMIMO_DATA"),
                        resultSet.getString("SASKAITOS_NR"),
                        resultSet.getInt("TELEFONO_NR"),
                        resultSet.getString("ADRESAS")
                ));

                pirkejuTable.setItems(PirkejasList);
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
        PdfWriter.getInstance(doc, new FileOutputStream("Pirkejas.pdf"));
        doc.open();

        Paragraph paragraph = new Paragraph("Pirkeju ataskaita");
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





        PdfPTable table1 = new PdfPTable(7);

        PdfPCell c1 = new PdfPCell(new Phrase("Pirkejo nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Vardas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Pavarde"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Gimimo data"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Saskaitos nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Telefonas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Adresas"));
        table1.addCell(c1);

        table1.setHeaderRows(1);

        String query1="select * from PIRKEJAS";
        preparedStatement=con.prepareStatement(query1);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table1.addCell(resultSet.getString("REGISTRACIJOS_NR"));
            table1.addCell(resultSet.getString("VARDAS"));
            table1.addCell(resultSet.getString("PAVARDE"));
            table1.addCell(resultSet.getString("GIMIMO_DATA"));
            table1.addCell(resultSet.getString("SASKAITOS_NR"));
            table1.addCell(resultSet.getString("TELEFONO_NR"));
            table1.addCell(resultSet.getString("ADRESAS"));
        }

        Paragraph paragraph1 = new Paragraph("Pirkeju sarasas");
        doc.add(paragraph1);
        doc.add(new Paragraph(" "));

        doc.add(table1);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table2 = new PdfPTable(7);

        PdfPCell c2 = new PdfPCell(new Phrase("Pirkejo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Vardas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Pavarde"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Uzsakymo numeris"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales ID"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Kiekis"));
        table2.addCell(c2);

        table2.setHeaderRows(1);


        String query2="select PIRKEJAS.REGISTRACIJOS_NR as prn, PIRKEJAS.VARDAS as pv, PIRKEJAS.PAVARDE as pp,\n" +
                "UZSAKYMAS.UZSAKYMO_NR as uun, DETALE.PAVADINIMAS as dp, DETALE.DETALES_ID as ddi, PASIRINKTOS_DETALES.KIEKIS as pdk\n" +
                "from PIRKEJAS\n" +
                "inner join UZSAKYMAS on PIRKEJAS.REGISTRACIJOS_NR = UZSAKYMAS.REGISTRACIJOS_NR\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join DETALE on PASIRINKTOS_DETALES.DETALES_ID = DETALE.DETALES_ID\n" +
                "order by PIRKEJAS.REGISTRACIJOS_NR;";
        preparedStatement=con.prepareStatement(query2);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table2.addCell(resultSet.getString("prn"));
            table2.addCell(resultSet.getString("pv"));
            table2.addCell(resultSet.getString("pp"));
            table2.addCell(resultSet.getString("uun"));
            table2.addCell(resultSet.getString("dp"));
            table2.addCell(resultSet.getString("ddi"));
            table2.addCell(resultSet.getString("pdk"));

        }

        Paragraph paragraph2 = new Paragraph("Pirkejams priklausantys uzsakymai ir detales");
        doc.add(paragraph2);
        doc.add(new Paragraph(" "));

        doc.add(table2);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table3 = new PdfPTable(6);

        PdfPCell c3 = new PdfPCell(new Phrase("Pirkejo nr."));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("P. vardas"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("P. pavarde"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Kurjerio nr."));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("K. vardas"));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("K. pavarde"));
        table3.addCell(c3);

        table3.setHeaderRows(1);


        String query3="select PIRKEJAS.REGISTRACIJOS_NR as prn, PIRKEJAS.VARDAS as pv, PIRKEJAS.PAVARDE as pp,\n" +
                "KURJERIS.KURJERIO_NR as kkn, KURJERIS.KURJERIO_VARDAS as kkv, KURJERIS.KURJERIO_PAVARDE as kkp\n" +
                "from PIRKEJAS\n" +
                "inner join UZSAKYMAS on PIRKEJAS.REGISTRACIJOS_NR = UZSAKYMAS.REGISTRACIJOS_NR\n" +
                "inner join KURJERIS on UZSAKYMAS.KURJERIO_NR = KURJERIS.KURJERIO_NR\n" +
                "order by PIRKEJAS.REGISTRACIJOS_NR;";
        preparedStatement=con.prepareStatement(query3);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table3.addCell(resultSet.getString("prn"));
            table3.addCell(resultSet.getString("pv"));
            table3.addCell(resultSet.getString("pp"));
            table3.addCell(resultSet.getString("kkn"));
            table3.addCell(resultSet.getString("kkv"));
            table3.addCell(resultSet.getString("kkp"));

        }

        Paragraph paragraph3 = new Paragraph("Pirkejams priskirti kurjeriai");
        doc.add(paragraph3);
        doc.add(new Paragraph(" "));

        doc.add(table3);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));



        PdfPTable table4 = new PdfPTable(6);

        PdfPCell c4 = new PdfPCell(new Phrase("Pirkejo nr."));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("P. vardas"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("P. pavarde"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("Surinkejo nr."));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("S. vardas"));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("S. pavarde"));
        table4.addCell(c4);

        table4.setHeaderRows(1);


        String query4="select PIRKEJAS.REGISTRACIJOS_NR as prn, PIRKEJAS.VARDAS as pv, PIRKEJAS.PAVARDE as pp,\n" +
                "SURINKEJAS.SURINKEJO_NR as ssn, SURINKEJAS.SURINKEJO_VARDAS as ssv, SURINKEJAS.SURINKEJO_PAVARDE as ssp\n" +
                "from PIRKEJAS\n" +
                "inner join UZSAKYMAS on PIRKEJAS.REGISTRACIJOS_NR = UZSAKYMAS.REGISTRACIJOS_NR\n" +
                "inner join SURINKEJAS on UZSAKYMAS.SURINKEJO_NR = SURINKEJAS.SURINKEJO_NR\n" +
                "order by PIRKEJAS.REGISTRACIJOS_NR;";
        preparedStatement=con.prepareStatement(query4);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table4.addCell(resultSet.getString("prn"));
            table4.addCell(resultSet.getString("pv"));
            table4.addCell(resultSet.getString("pp"));
            table4.addCell(resultSet.getString("ssn"));
            table4.addCell(resultSet.getString("ssv"));
            table4.addCell(resultSet.getString("ssp"));

        }

        Paragraph paragraph4 = new Paragraph("Pirkejams priskirti surinkejai");
        doc.add(paragraph4);
        doc.add(new Paragraph(" "));

        doc.add(table4);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));



        doc.close();

        File myFile = new File("C:\\Users\\Dalius\\Desktop\\final mssql nuo nulio old jdk\\CourseraGUI_JDBC\\Pirkejas.pdf");
        Desktop.getDesktop().open(myFile);

    }

}
